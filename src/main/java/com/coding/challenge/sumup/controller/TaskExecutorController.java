package com.coding.challenge.sumup.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.coding.challenge.sumup.dto.Job;

import com.coding.challenge.sumup.service.JobProcessingService;


@RestController
@RequestMapping("/api")
public class TaskExecutorController {

    @PostMapping(path = "/execute", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> processTasks(@RequestBody Job job) {
        String result = JobProcessingService.executeJob(job);
        return ResponseEntity.ok(result);
    }
}
