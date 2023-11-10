package com.coding.challenge.sumup;

import com.coding.challenge.sumup.dto.Command;
import com.coding.challenge.sumup.dto.Job;
import com.coding.challenge.sumup.dto.Task;
import com.coding.challenge.sumup.service.JobProcessingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JobProcessingServiceTest {

    private Job simpleJob;
    private Job complexJob;
    private Job emptyJob;

    @BeforeEach
    void setUp() {
        List<Task> simpleTasks = Arrays.asList(
                new Task("task-1", "touch /tmp/file1", null),
                new Task("task-2", "rm /tmp/file1", Collections.singletonList("task-1"))
        );
        simpleJob = new Job(simpleTasks);

        List<Task> complexTasks = Arrays.asList(
                new Task("task-1", "touch /tmp/file1", null),
                new Task("task-2", "cat /tmp/file1", Collections.singletonList("task-3")),
                new Task("task-3", "echo 'Hello World!' > /tmp/file1", Collections.singletonList("task-1")),
                new Task("task-4", "rm /tmp/file1", Arrays.asList("task-2", "task-3"))
        );
        complexJob = new Job(complexTasks);

        emptyJob = new Job(Collections.emptyList());
    }

    @Test
    void testExecuteSimpleJob() {
        List<Command> result = JobProcessingService.executeJob(simpleJob);
        assertNotNull(result);
        assertEquals(2, result.size());

        // Check the order of commands
        assertEquals("task-1", result.get(0).getName());
        assertEquals("touch /tmp/file1", result.get(0).getCommand());
        assertEquals("task-2", result.get(1).getName());
        assertEquals("rm /tmp/file1", result.get(1).getCommand());
    }

    @Test
    void testExecuteComplexJob() {
        List<Command> result = JobProcessingService.executeJob(complexJob);
        assertNotNull(result);
        assertEquals(4, result.size());

        // Check the order of commands
        assertEquals("task-1", result.get(0).getName());
        assertEquals("touch /tmp/file1", result.get(0).getCommand());
        assertEquals("task-3", result.get(1).getName());
        assertEquals("echo 'Hello World!' > /tmp/file1", result.get(1).getCommand());
        assertEquals("task-2", result.get(2).getName());
        assertEquals("cat /tmp/file1", result.get(2).getCommand());
        assertEquals("task-4", result.get(3).getName());
        assertEquals("rm /tmp/file1", result.get(3).getCommand());
    }

    @Test
    void testExecuteEmptyJob() {
        List<Command> result = JobProcessingService.executeJob(emptyJob);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
