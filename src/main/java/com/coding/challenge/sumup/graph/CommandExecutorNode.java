package com.coding.challenge.sumup.graph;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CommandExecutorNode extends Node {

    private String name;
    private String command;
    private List<String> output;

    @Override
    public void visit() {
        output.add(command);
    }

    @Override
    public boolean equals(Object other) {
        CommandExecutorNode otherNode = other instanceof CommandExecutorNode ? ((CommandExecutorNode) other) : null;
        if (otherNode == null) {
            return false;
        }
        return otherNode.name == this.name;
    }
}
