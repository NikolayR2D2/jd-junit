import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        Converter converter = new Converter();
        Parser parser = new Parser();
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};

        String csvFileName = "data.csv";
        String jsonFromCsvFileName = "data.json";
        List<Employee> listFromCsv = parser.parseCSV(columnMapping, csvFileName);
        String jsonFromCsv = converter.listToJson(listFromCsv);
        writeString(jsonFromCsv, jsonFromCsvFileName);

        String xmlFileName = "data.xml";
        String jsonFromXmlFileName = "data2.json";
        List<Employee> listFromXml = parser.parseXML(xmlFileName);
        String jsonFromXml = converter.listToJson(listFromXml);
        writeString(jsonFromXml, jsonFromXmlFileName);

        String jsonFileName = "data3.json";
        String json = readString(jsonFileName);
        List<Employee> list = converter.jsonToList(json);
        list.forEach(System.out::println);
    }

    static void writeString(String json, String fileName) {
        try (FileWriter file = new FileWriter(fileName)) {
            file.write(json);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String readString(String fileName) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String s;
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
