package com.coding.challenge.sumup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor

public class Task {
    private String name;
    private String command;
    private List<String> requires;
}
