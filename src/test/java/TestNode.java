import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import ru.ac.uniyar.mf.lizunova.Node;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestNode {
    @Test
    void add_1() {
        Node tree = new Node();
        assertEquals("",  tree.toString());
    }

    @Test
    void add_2() {
        Node tree = new Node("Корень");
        tree.add("Лист", "Корень");
        assertEquals("Корень\n" + "\tЛист\n", tree.toString());
    }

    @Test
    void add_3() {
        Node tree = new Node("Корень");
        tree.add("Лист1", "Корень");
        tree.add("Лист2", "Корень");
        tree.add("Лист2.1", "Лист2");
        assertEquals("Корень\n" + "\tЛист1\n"+ "\tЛист2\n"+ "\t\tЛист2.1\n", tree.toString());
    }

    @Test
    void toFile() throws IOException {
        Node tree = new Node("Корень");
        tree.add("Лист", "Корень");

        String contentWrite = tree.toString();
        String path = "file.txt";
        Files.write(Paths.get(path), contentWrite.getBytes());

        byte[] bytes = Files.readAllBytes(Paths.get(path));
        String contentRead = new String(bytes);

        assertEquals("Корень\n" + "\tЛист\n", contentRead);
    }

    @Test
    void FileWrite() throws IOException {
        Node tree = new Node("Корень");
        tree.add("Лист", "Корень");

        String contentWrite = tree.toString();
        String path = "file.txt";
        Files.write(Paths.get(path), contentWrite.getBytes());
    }

    @Test
    void FileRead() throws IOException {
        Node tree = new Node("Корень");
        tree.add("Лист", "Корень");

        String path = "/home/user/IdeaProjects/tree/target/file.txt";
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        String contentRead = new String(bytes);

        assertEquals("Корень\n" + "\tЛист\n", contentRead);
    }

    @Test
    void WriteFileJSON() throws IOException {
        Node tree = new Node("Корень");
        tree.add("Лист", "Корень");

        String path = "/home/user/IdeaProjects/tree/target/file.json";

        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        String nodeAsString = objectMapper.writeValueAsString(tree);

        Files.write(Paths.get(path), nodeAsString.getBytes());
    }

    @Test
    void ReadFileJSON() throws IOException {
        Node tree = new Node("Корень");
        tree.add("Лист", "Корень");

        String path = "/home/user/IdeaProjects/tree/target/file.json";

        String jsonString = "{\n" +
                "  \"name\" : \"Корень\",\n" +
                "  \"child\" : [ {\n" +
                "    \"name\" : \"Лист\",\n" +
                "    \"child\" : null\n" +
                "  } ]\n" +
                "}";

        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        Node treeNew = objectMapper.readValue(jsonString, Node.class);

        assertEquals("Корень\n" + "\tЛист\n", treeNew.toString());
    }

    @Test
    void FileJSON() throws IOException {
        Node tree = new Node("Корень");
        tree.add("Лист", "Корень");

        String path = "/home/user/IdeaProjects/tree/target/file.json";

        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        String nodeAsString = objectMapper.writeValueAsString(tree);

        Files.write(Paths.get(path), nodeAsString.getBytes());

        byte[] bytes = Files.readAllBytes(Paths.get(path));
        String jsonString = new String(bytes);

        Node treeNew = objectMapper.readValue(jsonString, Node.class);

        assertEquals("Корень\n" + "\tЛист\n", treeNew.toString());
    }
}
