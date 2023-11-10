package com.coding.challenge.sumup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Job {
    List<Task> tasks;
}
