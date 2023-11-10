package com.coding.challenge.sumup.service;

import com.coding.challenge.sumup.dto.Job;
import com.coding.challenge.sumup.dto.Task;
import com.coding.challenge.sumup.graph.CommandExecutorNode;
import com.coding.challenge.sumup.graph.Graph;
import com.coding.challenge.sumup.graph.Node;

import java.util.*;

public final class JobProcessingService {

    public static String executeJob(Job job) {
        try {
            List<String> output = new ArrayList<>();
            Map<String, CommandExecutorNode> tasksNameMapping = createTaskNodes(job, output);
            setUpTaskDependencies(job, tasksNameMapping);
            List<Graph> graphs = createGraphsForRootTasks(tasksNameMapping);

            graphs.sort(Comparator.comparing(g -> ((CommandExecutorNode) g.getRoot()).getName()));

            for (Graph graph : graphs) {
                graph.traverseDFS();
            }

            return String.join("\n", output);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static Map<String, CommandExecutorNode> createTaskNodes(Job job, List<String> output) {
        Map<String, CommandExecutorNode> tasksNameMapping = new HashMap<>();
        for (Task task : job.getTasks()) {
            tasksNameMapping.put(task.getName(), new CommandExecutorNode(task.getName(), task.getCommand(), output));
        }
        return tasksNameMapping;
    }

    private static void setUpTaskDependencies(Job job, Map<String, CommandExecutorNode> tasksNameMapping) {
        for (Task task : job.getTasks()) {
            CommandExecutorNode currentNode = tasksNameMapping.get(task.getName());
            if (task.getRequires() != null) {
                for (String requiredTask : task.getRequires()) {
                    CommandExecutorNode childNode = tasksNameMapping.get(requiredTask);
                    currentNode.setChild(childNode);
                }
            }
        }
    }

    private static List<Graph> createGraphsForRootTasks(Map<String, CommandExecutorNode> tasksNameMapping) {
        Map<String, Boolean> taskRequiredCount = new HashMap<>();
        for (String taskName : tasksNameMapping.keySet()) {
            taskRequiredCount.put(taskName, false);
        }

        for (CommandExecutorNode task : tasksNameMapping.values()) {
            for (Node child : task.getChildren()) {
                CommandExecutorNode childExecutorNode = (CommandExecutorNode) child;
                taskRequiredCount.put(childExecutorNode.getName(), true);
            }
        }

        List<Graph> graphs = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : taskRequiredCount.entrySet()) {
            if (!entry.getValue()) {
                CommandExecutorNode rootNode = tasksNameMapping.get(entry.getKey());
                graphs.add(new Graph(rootNode));
            }
        }
        return graphs;
    }
}

