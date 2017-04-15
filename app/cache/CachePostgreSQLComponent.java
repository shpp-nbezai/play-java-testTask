package cache;

import hash.MurmurHashComponent;
import response.CustomResponse;
import play.libs.F.Promise;
import models.CacheData;
import play.db.ebean.Transactional;
import javax.inject.*;
import javax.persistence.OptimisticLockException;

@Singleton
public class CachePostgreSQLComponent implements BaseCacheComponent {

    private CacheData cacheDB;

    @Override
    public Promise<String> getResponseBody(String access_token) throws Throwable {
        MurmurHashComponent hash = new MurmurHashComponent();
        cacheDB = new CacheData();
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
            MurmurHashComponent hash = new MurmurHashComponent();
            cacheDB = new CacheData();
            cacheDB.access_token = hash.getHashAsInt(access_token);
            cacheDB.responseBody = response.getBody();
            try{
                cacheDB.save();
            }
            catch (OptimisticLockException saveException){
                return saveException;
            };
            return null;
        });
    }

}
