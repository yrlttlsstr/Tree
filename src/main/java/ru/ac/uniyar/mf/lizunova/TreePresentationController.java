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
    static int counter = 3;

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

    /**
     * Пример обработки POST запроса.
     * Добавляет одну случайную запись в список и перенаправляет пользователя на основную страницу со списком.
     *
     * @return перенаправление на основную страницу со списком.
     */
    @POST
    @Path("add_item{id}{id_par}")
    @Produces("text/html")
    public Response addItem(@PathParam("id") String itemId, @PathParam("id_par") String ParentId) {
        tree.add("Лист{id}", "Лист{id_par}");
        try {
            return Response.seeOther(new URI("/")).build();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Ошибка построения URI для перенаправления");
        }
    }

    @POST
    @Path("add_root_item")
    @Produces("text/html")
    public Response addRootItem() {
        tree.add("Лист" + counter++, "Корень");
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
        String listItem = tree.getNode(itemId).toString();
        String result =
                "<html>" +
                        "  <head>" +
                        "    <title>Редактирование элемента списка</title>" +
                        "  </head>" +
                        "  <body>" +
                        "    <h1>Редактирование элемента списка</h1>" +
                        "    <form method=\"post\" action=\"/edit/" + itemId + "\">" +
                        "      <p>Значение</p>" +
                        "      <input type=\"text\" name=\"value\" value=\"" + listItem + "\"/>" +
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
        return "<html>" +
                "  <head>" +
                "    <title>Вывод дерева</title>" +
                "  </head>" +
                "  <body>" +
                "    <h1>Вывод дерева</h1>" +
                tree.toStringHTML() +
                "<form method=\"post\" action=\"add_root_item\">" +
                "        <input type=\"submit\" value=\"Add at root item\"/>" +
                "      </form>" +
                "  </body>" +
                "</html>";
    }

}
