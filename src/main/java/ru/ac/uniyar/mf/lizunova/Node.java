package ru.ac.uniyar.mf.lizunova;

import java.util.*;

public class Node {
    private String name;
    private List<Node> child;

    // Конструкторы.
    public Node() {}
    public Node(String name) {
        this.name = name;
    }

    // Метод добавляет узел.
    public void add(String name, String parentName) {
       add(this, name, parentName);
    }
    private void add(String name) {
        if(child == null) {
            child = new ArrayList<>();
        }
        child.add(new Node(name));
    }
    private void add(Node pos, String name, String parentName) {
        if (pos.name.equals(parentName)) {
            pos.add(name);
            return;
        }
        if (pos.child == null) {
           return;
        }
        for (Node node : pos.child) {
            add(node, name, parentName);
        }
    }

    // Метод для преобразования дерева в строку.
    @Override
    public String toString() {
        return toString(this, 0);
    }
    private String toString(Node pos, int lvl) {
        if (pos.name == null) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < lvl; i++) {
            result.append("\t");
        }
        result.append(pos.name).append("\n");
        if (pos.child == null) {
            return result.toString();
        }
        for (Node child : pos.child) {
            result.append(toString(child, lvl + 1));
        }
        return result.toString();
    }

    // Геттеры.
    public String getName() {
        return name;
    }
    public List<Node> getChild() {
        return child;
    }
}
