package cache;

import hash.MurmurHashComponent;
import response.CustomResponse;
import play.libs.F.Promise;
import models.CacheData;
import play.db.ebean.Transactional;
import javax.inject.Inject;


public class CachePostgreSQLComponent implements BaseCacheComponent {

    @Inject
    MurmurHashComponent hash;

    private final  CacheData cacheDB = new CacheData();

    @Override
    public Promise<String> getResponseBody(String access_token) throws Throwable {
        if (access_token == null ) throw new NullPointerException("Access_token = null!");

        return Promise.promise(() -> {
            CacheData dbResponseBody = cacheDB.find.byId(hash.getHashAsInt(access_token));
            if (dbResponseBody == null){
                return  null;
            }
            return dbResponseBody.responseBody;
        });
    }

    @Override
    @Transactional
    public Promise<Throwable> putResponse(String access_token, CustomResponse response) throws Throwable {

        return Promise.promise(() -> {
            cacheDB.access_token = hash.getHashAsInt(access_token);
            cacheDB.responseBody = response.getBody();
            cacheDB.save();
            return null;
        });
    }

}
