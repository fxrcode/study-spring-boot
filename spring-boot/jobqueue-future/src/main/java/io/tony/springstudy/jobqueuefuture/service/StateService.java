package io.tony.springstudy.jobqueuefuture.service;

import io.tony.springstudy.jobqueuefuture.model.Notebook;
import io.tony.springstudy.jobqueuefuture.repository.NotebookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Transactional
@Service
public class StateService {
    @Autowired
    private NotebookRepository notebookRepository;

    public Notebook initNotebookAndFirstInstance(String id, String name, String status) {
        Notebook nb = new Notebook();
        nb.setId(id);
        nb.setName(name);
        nb.setCreateTime(new Date());
        // do i need to gen uuid?;
        nb.setStatus(status);
        notebookRepository.save(nb);
        return nb;
    }

    public void repavedDetected(String id) {
        Notebook notebook = notebookRepository.findById(id).orElseThrow( ()-> new RuntimeException("ERROR---"));
        notebook.setStatus(String.valueOf(NotebookStatus.DETECTED));
        notebookRepository.save(notebook);
    }

    public void repaveDone(String id) {
        Notebook notebook = notebookRepository.findById(id).orElseThrow( ()-> new RuntimeException("ERROR---"));
        notebook.setStatus(String.valueOf(NotebookStatus.REPAVED));
        notebookRepository.save(notebook);
    }
}
