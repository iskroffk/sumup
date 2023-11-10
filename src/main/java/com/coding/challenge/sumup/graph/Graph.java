package com.coding.challenge.sumup.graph;

import lombok.Getter;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;

public class Graph {
    @Getter
    private Node root;

    public Graph(Node root) {
        this.root = root;
    }

    public void traverseDFS() {
        HashSet<Node> visited = new HashSet<>();
        Deque<Node> stack = new ArrayDeque<>();

        stack.add(root);
        while (!stack.isEmpty()) {
            Node currentNode = stack.peek();
            if (!visited.contains(currentNode)) {
                for (Node child : currentNode.getChildren()) {
                    if (!visited.contains(child)) {
                        if (stack.contains(child)) {
                            throw new IllegalArgumentException("Cyclic dependency detected");
                        }
                        stack.push(child);
                    }
                }

                if (stack.peek().equals(currentNode)) {
                    currentNode.visit();
                    visited.add(currentNode);
                    stack.pop();
                }
            } else {
                stack.pop();
            }
        }
    }
}
