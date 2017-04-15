package json;

import play.libs.Json;
import com.fasterxml.jackson.databind.node.*;
import javax.inject.*;

@Singleton
public class JsonComponent {

    public ObjectNode getFBServerErrorJson(Throwable throwable){
        ObjectNode jsonResult = Json.newObject();
        ObjectNode error = Json.newObject();
        error.put("message", throwable.getMessage());
        error.put("type", throwable.getClass().getSimpleName());
        error.put("code", "0");
        error.put("fbtrace_id", "");
        jsonResult.set("error", error);

        return  jsonResult;
    }
}
