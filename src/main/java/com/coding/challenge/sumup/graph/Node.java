package com.coding.challenge.sumup.graph;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public abstract class Node {

    private List<Node> children = new ArrayList<>();

    public void setChild(Node node) {
        children.add(node);
    }

    public abstract void visit();
}
