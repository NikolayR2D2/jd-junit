import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Converter {
    final Type listType = new TypeToken<List<Employee>>() {}.getType();

    public String listToJson(List<Employee> list) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(list, listType);
    }

    public List<Employee> jsonToList(String json) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(json, listType);
    }
}
