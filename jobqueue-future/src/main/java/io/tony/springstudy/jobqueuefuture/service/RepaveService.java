package io.tony.springstudy.jobqueuefuture.service;

import com.google.common.collect.Maps;
import io.tony.springstudy.jobqueuefuture.model.Notebook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RepaveService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RepaveService.class);
    private static final Object SYNC_ROOT = new Object();
    private Map<String, RepaveTask> waitQueue;
    private Map<String, RepaveTask> processingQueue;

    @Autowired
    private NotebookService notebookService;
    @Autowired
    private StateService stateService;

    @PostConstruct
    public void init() {
        waitQueue = Collections.synchronizedMap(Maps.newLinkedHashMap());
        processingQueue = Collections.synchronizedMap(Maps.newLinkedHashMap());
    }

    @Scheduled(fixedDelay = 60 * 1000)
    public void checkUnexpectedTermination() {
        List<Notebook> notebooks = notebookService.findAllTerminated();
        StringBuilder sb = new StringBuilder();
        notebooks.forEach(n -> sb.append(n.getId()));
        LOGGER.info("All terminated notebooks {}, they're {}", notebooks.size(), sb.toString());
        for (Notebook notebook : notebooks) {
            processRepaveQueue(notebook);
        }
    }

    private void processRepaveQueue(Notebook notebook) {
        if (waitQueue.containsKey(notebook.getId())) {
            LOGGER.info("notebook {} is already in waitqueue, wait for detect repave", notebook.getName());
        } else if (processingQueue.containsKey(notebook.getId())) {
            LOGGER.info("notebook {} is in processing queue, will clean out once done", notebook.getName());
        } else {
            LOGGER.info("notebook {} is a candidate, time to put into wait queue", notebook.getName());
            waitQueue.put(notebook.getId(), new RepaveTask().withNotebook(notebook).withCreateTime(Instant.now()));
        }
    }

    @Scheduled(fixedDelay = 20 * 1000)
    public void dispatchRepavingTasks() {
        int sz = waitQueue.size();
        if (sz == 0) {
            LOGGER.info("waitQueue is empty, nothing to dispatch");
            return;
        }
        LOGGER.info("======== I'm back ..., waitQueue size {}", sz);
        while (true) {
            Map.Entry<String, RepaveTask> entry = null;
            Iterator<Map.Entry<String, RepaveTask>> it = waitQueue.entrySet().iterator();
            synchronized (SYNC_ROOT) {
                if (it.hasNext()) {
                    entry = it.next();
                    processingQueue.put(entry.getKey(), entry.getValue().withState("dispatched").withDispathTime(Instant.now()));
                    it.remove();
                }
            }
            if (entry != null) {
                LOGGER.info("Dispatch repaving for notebook {}", entry.getKey());
                Notebook notebook = entry.getValue().getNotebook();
                long unixTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
                new Thread(
                        () -> {
                            try {
                                kickoffRepaving(notebook);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }, "repav-" + notebook.getName() + "_" + unixTime).start();
            } else {
                LOGGER.info("No more wait queue items found, bye");
                break;
            }
        }
    }

    private void kickoffRepaving(Notebook notebook) throws InterruptedException {
        notebook.setStatus(String.valueOf(NotebookStatus.DETECTED));
        stateService.repavedDetected(notebook.getId());
        LOGGER.info("Repaving notebook {}, wait for 10sec...", notebook.getName());
        Thread.sleep(10 * 1000);
        notebook.setStatus(String.valueOf(NotebookStatus.REPAVED));
        stateService.repaveDone(notebook.getId());
        LOGGER.info("\t\tRepaved notebook {}!", notebook.getName());
    }

    @Scheduled(fixedDelay = 20 * 1000)
    public void cleanupProcessingQueue() {
        LOGGER.info("===== Cleanup processing queue, current size {}", processingQueue.size());
        Iterator<Map.Entry<String, RepaveTask>> it = processingQueue.entrySet().iterator();
        Map.Entry<String, RepaveTask> entry = null;
        while (it.hasNext()) {
            entry = it.next();
            if ("dispatched".equals(entry.getValue().getState()) && notebookService.isStatus(entry.getKey(), NotebookStatus.REPAVED)) {
                LOGGER.info("******* Remove item from the processing queue {}", entry.getValue());
                it.remove();
            }
        }
    }
}
