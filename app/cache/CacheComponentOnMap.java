package cache;

import play.libs.F;
import play.libs.F.Promise;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import response.CustomResponse;


public class CacheComponentOnMap implements BaseCacheComponent {
    private final Map<String, CustomResponse> cacheResponseMap = new ConcurrentHashMap<String,CustomResponse>();

    public Promise<String> getResponseBody(String access_token){

        return Promise.promise(()->{
            if (cacheResponseMap.containsKey(access_token)) {
                CustomResponse response = cacheResponseMap.get(access_token);
                return response.getBody();
            }
            String emptyCache = null;
            return emptyCache;
        });
    }

    public F.Promise<Throwable> putResponse(String access_token, CustomResponse response)throws Throwable{

        return Promise.promise(() -> {
            cacheResponseMap.put(access_token, response);
            return null;
        });
    }

}
