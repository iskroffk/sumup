package com.coding.challenge.sumup.service;

import com.coding.challenge.sumup.dto.Command;
import com.coding.challenge.sumup.dto.Job;
import com.coding.challenge.sumup.dto.Task;
import com.coding.challenge.sumup.graph.CommandExecutorNode;
import com.coding.challenge.sumup.graph.Graph;
import com.coding.challenge.sumup.graph.Node;

import java.util.*;

public final class JobProcessingService {

    public static List<Command> executeJob(Job job) {
        try {
            List<String> output = new ArrayList<>();
            List<Command> dtos = new ArrayList<>();
            Map<String, CommandExecutorNode> tasksNameMapping = createTaskNodes(job, output, dtos);
            setUpTaskDependencies(job, tasksNameMapping);
            List<Graph> graphs = createGraphsForRootTasks(tasksNameMapping);

            graphs.sort(Comparator.comparing(g -> ((CommandExecutorNode) g.getRoot()).getName()));

            for (Graph graph : graphs) {
                graph.traverseDFS();
            }

            Map<String, Integer> outputIndexMap = new HashMap<>();
            for (int i = 0; i < output.size(); i++) {
                outputIndexMap.put(output.get(i), i);
            }

            dtos.sort(Comparator.comparingInt(command -> outputIndexMap.get(command.getCommand())));

            return dtos;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    private static Map<String, CommandExecutorNode> createTaskNodes(Job job, List<String> output, List<Command> commandDtoList) {
        Map<String, CommandExecutorNode> tasksNameMapping = new HashMap<>();
        for (Task task : job.getTasks()) {
            String taskName = task.getName();
            String command = task.getCommand();

            CommandExecutorNode node = new CommandExecutorNode(taskName, command, output);
            tasksNameMapping.put(taskName, node);

            Command commandDto = new Command();
            commandDto.setName(taskName);
            commandDto.setCommand(command);
            commandDtoList.add(commandDto);
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

