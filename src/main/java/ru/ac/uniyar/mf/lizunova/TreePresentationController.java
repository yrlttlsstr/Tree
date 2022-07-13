package ru.ac.uniyar.mf.lizunova;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Контроллер отвечающий за представление списка.
 */
@Path("/")
public class TreePresentationController {
    private final Node tree;
    private static int counter = 3;

    /**
     * Запоминает список, с которым будет работать.
     *
     * @param tree список, с которым будет работать контроллер.
     */
    public TreePresentationController(Node tree) {
        this.tree = tree;
    }

    /**
     * Пример вывода простого текста.
     */
    @GET
    @Path("example")
    @Produces("text/plain")
    public String getSimpleText() {
        return "hello world";
    }

    /**
     * Выводит HTML-страницу со списком, ссылками на страницы редактирования и копкой добавления записи.
     *
     * @return HTML-страница со списком.
     */

    @POST
    @Path("add_item")
    @Produces("text/html")
    public Response addItem(@FormParam("value") String itemValue) {
        if (itemValue.equals("Root")) {
            tree.add("List" + counter++, "Root");
        } else {
            int k;
            if (tree.getNode(itemValue).getChild() == null) {
                k = 1;
            } else {
                k = tree.getNode(itemValue).getChild().size() + 1;
            }
            String newNode = itemValue + "." + k;
            tree.add(newNode, itemValue);
        }
        try {
            return Response.seeOther(new URI("/")).build();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Ошибка построения URI для перенаправления");
        }
    }

    @POST
    @Path("remove_item")
    @Produces("text/html")
    public Response removeItem(@FormParam("value") String itemValue) {
        if (!itemValue.equals("Root")) {
            tree.remove(itemValue);
        }
        try {
            return Response.seeOther(new URI("/")).build();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Ошибка построения URI для перенаправления");
        }
    }

    @POST
    @Path("reboot")
    @Produces("text/html")
    public Response reboot() {
        tree.removeAllChild("Root");
        tree.add("List1", "Root");
        tree.add("List1.1", "List1");
        tree.add("List2", "Root");
        tree.add("List2.1", "List2");
        counter = 3;
        try {
            return Response.seeOther(new URI("/")).build();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Ошибка построения URI для перенаправления");
        }
    }

    /**
     * Выводит страничку для редактирования одного элемента.
     *
     * @param itemId индекс элемента списка.
     * @return страничка для редактирования одного элемента.
     */
    @GET
    @Path("/edit/{id}")
    @Produces("text/html")
    public String getEditPage(@PathParam("id") String itemId) {
        String treeItem = tree.getName(itemId);
        String result =
                "<html>" +
                        "  <head>" +
                        "    <title>Редактирование элемента дерева</title>" +
                        "  </head>" +
                        "  <body>" +
                        "    <h1>Редактирование элемента дерева</h1>" +
                        "    <form method=\"post\" action=\"/edit/" + itemId + "\">" +
                        "      <p>Значение</p>" +
                        "      <input type=\"text\" name=\"value\" value=\"" + treeItem + "\"/>" +
                        "      <input type=\"submit\"/>";
        result +=
                "            </form>" +
                        "  </body>" +
                        "</html>";
        return result;
    }

    /**
     * Редактирует элемент списка на основе полученных данных.
     *
     * @param itemId индекс элемента списка.
     * @return перенаправление на основную страницу со списком.
     */
    @POST
    @Path("/edit/{id}")
    @Produces("text/html")
    public Response editItem(@PathParam("id") String itemId, @FormParam("value") String itemValue) {
        tree.setName(itemId, itemValue);
        try {
            return Response.seeOther(new URI("/")).build();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Ошибка построения URI для перенаправления");
        }
    }

    /**
     * Пример вывода вложенного списка.
     */
    @GET
    @Path("/")
    @Produces("text/html")
    public String getTree() {
        String treeItem = "";
        return "<html>" +
                "  <head>" +
                "    <title>Вывод дерева</title>" +
                "  </head>" +
                "  <body>" +
                "    <h1>Вывод дерева</h1>" +
                tree.toStringHTML() +
                "<li>Добавление элемента к родителю: </li>" +
                "<form method=\"post\" action=\"add_item\">" +
                "      <input type=\"text\" name=\"value\" value=\"" + treeItem + "\"/>" +
                "      <input type=\"submit\"/>" +
                "      </form>" +
                "<li>Удаление элемента (корень удалять нельзя): </li>" +
                "<form method=\"post\" action=\"remove_item\">" +
                "      <input type=\"text\" name=\"value\" value=\"" + treeItem + "\"/>" +
                "      <input type=\"submit\"/>" +
                "      </form>" +
                "      <form method=\"post\" action=\"reboot\">" +
                "        <input type=\"submit\" value=\"Ребутнуть к исходнику\"/>" +
                "      </form>" +
                "  </body>" +
                "</html>";
    }

}
