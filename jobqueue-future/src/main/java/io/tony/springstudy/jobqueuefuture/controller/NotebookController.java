package io.tony.springstudy.jobqueuefuture.controller;

import io.tony.springstudy.jobqueuefuture.model.Notebook;
import io.tony.springstudy.jobqueuefuture.service.NotebookService;
import io.tony.springstudy.jobqueuefuture.service.NotebookStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/notebooks")
@RestController
public class NotebookController {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotebookController.class);
    @Autowired
    private NotebookService notebookService;

    @PostMapping("/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    public Notebook create(@PathVariable String name) {
        return notebookService.newNotebook(name);
    }

    @PatchMapping("/{notebookId}/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    public Notebook changeState(@PathVariable String notebookId, @PathVariable NotebookStatus status) {
        return notebookService.changeStatus(notebookId, status);
    }
}
