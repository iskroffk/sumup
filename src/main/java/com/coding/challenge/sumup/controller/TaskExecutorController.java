package com.coding.challenge.sumup.controller;

import com.coding.challenge.sumup.dto.Command;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.coding.challenge.sumup.dto.Job;

import com.coding.challenge.sumup.service.JobProcessingService;

import java.util.List;


@RestController
@RequestMapping("/api")
public class TaskExecutorController {

    @PostMapping(path = "/execute", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<?> processTasks(@RequestBody Job job, @RequestHeader(value = "User-Agent", required = false) String userAgent) {
        List<Command> result = JobProcessingService.executeJob(job);

        if (userAgent != null && userAgent.toLowerCase().contains("curl")) {
            String plainTextResponse = generatePlainTextResponse(result);
            return ResponseEntity.ok(plainTextResponse);
        } else {
            return ResponseEntity.ok(result);
        }
    }

    private String generatePlainTextResponse(List<Command> commands) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Command command : commands) {
            stringBuilder.append(command.getCommand()).append("\n");
        }
        return stringBuilder.toString();
    }
}
