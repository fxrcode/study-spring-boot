package io.tony.springstudy.jobqueuefuture.service;

import io.tony.springstudy.jobqueuefuture.model.Notebook;
import io.tony.springstudy.jobqueuefuture.repository.NotebookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class NotebookService {
    @Autowired
    private NotebookRepository notebookRepository;
    @Autowired
    private StateService stateService;

    @Transactional
    public Notebook newNotebook(String name) {
        String id = UUID.randomUUID().toString();
        Notebook notebook = stateService.initNotebookAndFirstInstance(id, name, String.valueOf(NotebookStatus.NEW));
        return notebook;
    }

    public List<Notebook> findAllTerminated() {
        return notebookRepository.findAllByStatus(String.valueOf(NotebookStatus.TERMINATED));
    }

    public Notebook changeStatus(String id, NotebookStatus status) {
        Notebook notebook = notebookRepository.findById(id).orElseThrow(() -> new RuntimeException("Notebook not found!"));
        notebook.setStatus(String.valueOf(status));
        notebookRepository.save(notebook);
        return notebook;
    }

    public boolean isStatus(String id, NotebookStatus status) {
        Notebook notebook = notebookRepository.findById(id).orElseThrow(() -> new RuntimeException("Notebook not found!"));
        return notebook.getStatus().equals(String.valueOf(status));
    }
}
