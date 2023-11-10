package com.coding.challenge.sumup.controller;

import com.coding.challenge.sumup.dto.Job;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class TaskExecutorController {

    @PostMapping(path = "/execute")
    public ResponseEntity<List<String>> processTasks(@RequestBody Job job) {
        List<String> result = new ArrayList<>();

        return ResponseEntity.ok(result);
    }
}
