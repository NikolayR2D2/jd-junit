import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


class Testing {
    Converter converter;
    Parser parser;

    static List<Employee> list1 = new ArrayList<>();
    static String json1 = "[]";

    static List<Employee> list2 = List.of(
            new Employee(123, "John", "Doe", "USA", 42)
    );
    static String json2 = "[" +
            "{\"id\":123,\"firstName\":\"John\",\"lastName\":\"Doe\",\"country\":\"USA\",\"age\":42}" +
            "]";

    static List<Employee> list3 = List.of(
        new Employee(456,"Albert","Einstein","Germany",76),
        new Employee(789,"Nikola","Tesla","Austria",86)
    );
    static String json3 = "[" +
            "{\"id\":456,\"firstName\":\"Albert\",\"lastName\":\"Einstein\",\"country\":\"Germany\",\"age\":76}," +
            "{\"id\":789,\"firstName\":\"Nikola\",\"lastName\":\"Tesla\",\"country\":\"Austria\",\"age\":86}" +
            "]";

    @BeforeAll
    public static void startAll() {
        System.out.println("Tests started");
    }

    @BeforeEach
    public void start() {
        System.out.println("Test started");
        converter = new Converter();
        parser = new Parser();
    }

    @AfterEach
    public void finish() {
        System.out.println("Test finished");
    }

    @AfterAll
    public static void finishAll() {
        System.out.println("Tests finished");
    }

    @ParameterizedTest
    @MethodSource("listJsonSource")
    public void testListToJson(List<Employee> list, String json) {
        String result = converter.listToJson(list);

        Assertions.assertEquals(json, result);
    }

    @ParameterizedTest
    @MethodSource("listJsonSource")
    public void testJsonToList(List<Employee> list, String json) {
        List<Employee> result = converter.jsonToList(json);

        Assertions.assertEquals(list, result);
    }

    private static Stream<Arguments> listJsonSource() {
        return Stream.of(
                Arguments.of(list1, json1),
                Arguments.of(list2, json2),
                Arguments.of(list3, json3)
        );
    }

    @ParameterizedTest
    @CsvFileSource(resources = "data.csv")
    public void testLastName(long id, String firstName, String lastName, String country, int age) {
        Assertions.assertFalse(lastName.isEmpty());
    }

    @Test
    public void testEmptyList() throws ParserConfigurationException, IOException, SAXException {
        List<Employee> list = parser.parseXML("src/test/resources/data.xml");

        assertThat(list, is(not(empty())));
    }

    @Test
    public void testAge() {
        List<Employee> list = parser.parseCSV(new String[]{"id", "firstName", "lastName", "country", "age"}, "src/test/resources/data.csv");

        assertThat(list, everyItem(hasProperty("age", greaterThanOrEqualTo(18))));
    }
}