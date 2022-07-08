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
        String path = "C:\\Users\\АРИША\\IdeaProjects\\Tree\\target\\file.txt";
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
        String path = "C:\\Users\\АРИША\\IdeaProjects\\Tree\\target\\file.txt";
        Files.write(Paths.get(path), contentWrite.getBytes());
    }

    @Test
    void FileRead() throws IOException {
        Node tree = new Node("Корень");
        tree.add("Лист", "Корень");

        String path = "C:\\Users\\АРИША\\IdeaProjects\\Tree\\target\\file.txt";
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        String contentRead = new String(bytes);

        assertEquals("Корень\n" + "\tЛист\n", contentRead);
    }

    @Test
    void WriteFileJSON() throws IOException {
        Node tree = new Node("Корень");
        tree.add("Лист", "Корень");

        String path = "C:\\Users\\АРИША\\IdeaProjects\\Tree\\target\\file.json";

        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        String nodeAsString = objectMapper.writeValueAsString(tree);

        Files.write(Paths.get(path), nodeAsString.getBytes());
    }

    @Test
    void ReadFileJSON() throws IOException {
        Node tree = new Node("Корень");
        tree.add("Лист", "Корень");

        String path = "C:\\Users\\АРИША\\IdeaProjects\\Tree\\target\\file.json";

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

        String path = "C:\\Users\\АРИША\\IdeaProjects\\Tree\\target\\file.json";

        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        String nodeAsString = objectMapper.writeValueAsString(tree);

        Files.write(Paths.get(path), nodeAsString.getBytes());

        byte[] bytes = Files.readAllBytes(Paths.get(path));
        String jsonString = new String(bytes);

        Node treeNew = objectMapper.readValue(jsonString, Node.class);

        assertEquals("Корень\n" + "\tЛист\n", treeNew.toString());
    }

    @Test
    void remove_1() {
        Node tree = new Node("Корень");
        tree.add("Лист1", "Корень");
        tree.add("Лист2", "Корень");
        tree.add("Лист2.1", "Лист2");
        tree.remove("Лист2");
        assertEquals("Корень\n" + "\tЛист1\n", tree.toString());
    }

    @Test
    void remove_2() {
        Node tree = new Node("Корень");
        tree.remove("Корень");
        assertEquals("", tree.toString());
    }

    @Test
    void removeAllChild_1() {
        Node tree = new Node("Корень");
        tree.add("Лист1", "Корень");
        tree.add("Лист2", "Корень");
        tree.add("Лист2.1", "Лист2");
        tree.add("Лист2.2", "Лист2");
        tree.add("Лист2.3", "Лист2");
        tree.removeAllChild("Лист2");
        assertEquals("Корень\n" + "\tЛист1\n" + "\tЛист2\n", tree.toString());
    }

    @Test
    void removeAllChild_2() {
        Node tree = new Node("Корень");
        tree.add("Лист1", "Корень");
        tree.add("Лист2", "Корень");
        tree.add("Лист2.1", "Лист2");
        tree.add("Лист2.2", "Лист2");
        tree.add("Лист2.3", "Лист2");
        tree.add("Лист1.1", "Лист1");
        tree.add("Лист1.2", "Лист1");
        tree.removeAllChild("Лист2");
        tree.removeAllChild("Лист1");
        assertEquals("Корень\n" + "\tЛист1\n" + "\tЛист2\n", tree.toString());
    }

    @Test
    void setName_1() {
        Node tree = new Node("Корень");
        tree.add("Лист1", "Корень");
        tree.add("Лист2", "Корень");
        tree.setName("Лист1", "Лист3");
        assertEquals("Корень\n" + "\tЛист3\n" + "\tЛист2\n", tree.toString());
    }

    @Test
    void setName_2() {
        Node tree = new Node("Корень");
        tree.add("Лист1", "Корень");
        tree.add("Лист2", "Корень");
        tree.add("Лист1.1", "Лист1");
        tree.add("Лист2.1", "Лист2");
        tree.setName("Лист1", "Лист3");
        tree.setName("Лист2.1", "Лист2.2");
        assertEquals("Корень\n" + "\tЛист3\n"+ "\t\tЛист1.1\n" + "\tЛист2\n"+ "\t\tЛист2.2\n", tree.toString());
    }

    @Test
    void setName_3() {
        Node tree = new Node("Корень");
        tree.add("Лист1", "Корень");
        tree.add("Лист2", "Корень");
        tree.add("Лист1.1", "Лист1");
        tree.add("Лист2.1", "Лист2");
        tree.setName("Лист3", "Лист4");
        tree.setName("Лист2.1", "Лист2.2");
        assertEquals("Корень\n" + "\tЛист1\n"+ "\t\tЛист1.1\n" + "\tЛист2\n"+ "\t\tЛист2.2\n", tree.toString());
    }

    @Test
    void getNode_1() {
        Node tree = new Node("Корень");
        tree.add("Лист1", "Корень");
        tree.add("Лист2", "Корень");
        tree.add("Лист1.1", "Лист1");
        tree.add("Лист2.1", "Лист2");
        Node node = tree.getNode("Лист2");
        assertEquals("Лист2\n" + "\tЛист2.1\n", node.toString());
    }

    @Test
    void getNode_2() {
        Node tree = new Node("Корень");
        tree.add("Лист1", "Корень");
        tree.add("Лист2", "Корень");
        tree.add("Лист1.1", "Лист1");
        tree.add("Лист2.1", "Лист2");
        Node node = tree.getNode("Лист1.1");
        assertEquals("Лист1.1\n", node.toString());
    }
}
