package Advanced.json.problem;

import java.util.Objects;

public class JsonString implements JsonType {

    private String value;
    public JsonString(String value){
        Objects.requireNonNull(value);
        this.value = value;
    }

    @Override
    public JsonType parseJson(String json) {
        return new JsonString(json);
    }
}
