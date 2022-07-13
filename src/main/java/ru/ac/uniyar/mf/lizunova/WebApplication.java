package ru.ac.uniyar.mf.lizunova;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Web-приложение в котором регистрируются все ресурсы.
 */
public class WebApplication extends Application {

    private Node tree = new Node("Root");

    public WebApplication() {
        tree.add("List1", "Root");
        tree.add("List1.1", "List1");
        tree.add("List2", "Root");
        tree.add("List2.1", "List2");
    }

    /**
     * Возвращает список всех ресурсов web-приложения.
     * @return список всех ресурсов web-приложения.
     */
    @Override
    public Set<Object> getSingletons() {
        Set<Object> resources = new HashSet<>();
        resources.add(new TreePresentationController(tree));
        return resources;
    }
}
