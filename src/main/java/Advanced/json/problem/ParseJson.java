package Advanced.json.problem;

import java.util.regex.Pattern;

public class ParseJson {

    public static boolean validJson(String jsonStr){
        return false;
    }

    //{"a":"b"}
    JsonType matchToJsonType(String jsonStr) {
       /* if(jsonStr == null){
            return new JsonNull();
        }else if (jsonStr.startsWith("{") && jsonStr.endsWith("}")){
            return new JsonObject();
        }else if(Pattern.matches("\\[0-9\\]", jsonStr)){
            return new JsonNumber();
        }else if (jsonStr.startsWith("[") && jsonStr.endsWith("]")){
            return new JsonArray();
        }else {
            return new JsonString();
        }*/
        return null;
    }

    public String stripBrackets(String jsonStr){
        return jsonStr.substring(1, jsonStr.length() - 1);
    }
}
