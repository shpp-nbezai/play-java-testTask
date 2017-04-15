package cache;

import play.libs.F;
import play.libs.F.Promise;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import response.CustomResponse;
import javax.inject.*;

@Singleton
public class CacheComponentOnMap implements BaseCacheComponent {
    private final Map<String, CustomResponse> cacheResponseMap = new ConcurrentHashMap<String,CustomResponse>();

    public Promise<String> getResponseBody(String access_token){
        if (cacheResponseMap.containsKey(access_token)) {
            CustomResponse response = cacheResponseMap.get(access_token);
            String responseBody = response.getBody();
            return Promise.<String>pure(responseBody);
        }
        return Promise.<String>pure(null);
    }

    public F.Promise<Throwable> putResponse(String access_token, CustomResponse response)throws Throwable{
        cacheResponseMap.put(access_token, response);
        return Promise.<Throwable>pure(null);
    }

}
