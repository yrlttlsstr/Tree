package ru.ac.uniyar.mf.lizunova;

import java.util.*;

public class Node {
    private String name;
    private List<Node> child;

    // Конструкторы.
    public Node() {
    }

    public Node(String name) {
        this.name = name;
    }

    // Метод добавляет узел.
    public void add(String name, String parentName) {
        add(this, name, parentName);
    }
    private void add(String name) {
        if (child == null) {
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

    // Метод удаляет узел.
    public void remove(String name) {
        if (this.name.equals(name)) {
            this.name = null;
            if (this.child != null) {
                this.child.clear();
            }
            return;
        }
        remove(this, name);
    }
    private void remove(Node pos, String name) {
        if (pos.child == null) {
            return;
        }
        for (Node child : pos.child) {
            if (child.name.equals(name)) {
                pos.child.remove(child);
                return;
            }
        }
        for (Node child : pos.child) {
            remove(child, name);
        }
    }

    // Метод удаления всех детей у узла.
    public void removeAllChild(String parentName) {
        if (this.name.equals(parentName)) {
            if (this.child != null) {
                child.clear();
            }
            return;
        }
        removeAllChild(this, parentName);
    }
    private void removeAllChild(Node pos, String parentName) {
        if (pos.child == null) {
            return;
        }
        for (int i = 0; i < pos.child.size(); i++) {
            if ( child.get(i).name.equals(parentName)) {
                pos.child.get(i).removeAllChild(child.get(i).name);
                return;
            }
        }
        for (Node child : pos.child) {
            remove(child, name);
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

    // Сеттер.
    public void setName(String name, String newName) {
        if (this.name.equals(name)) {
            this.name = newName;
            return;
        }
        setName(this, name, newName);
    }
    private void setName(Node pos, String name, String newName) {
        if (pos.child == null) {
            return;
        }
        for (int i = 0; i < pos.child.size(); i++) {
            if (pos.child.get(i).name.equals(name)) {
                pos.child.get(i).setName(name, newName);
                return;
            }
        }
        for (Node child : pos.child) {
            setName(child, name, newName);
        }
    }
}
