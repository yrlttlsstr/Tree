package ru.ac.uniyar.mf.lizunova;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Web-приложение в котором регистрируются все ресурсы.
 */
public class WebApplication extends Application {

    private Node tree = new Node("Корень");

    public WebApplication() {
        tree.add("Лист1", "Корень");
        tree.add("Лист1.1", "Лист1");
        tree.add("Лист2", "Корень");
        tree.add("Лист2.1", "Лист2");
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
