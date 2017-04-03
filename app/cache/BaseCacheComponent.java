package cache;

import response.CustomResponse;
import play.libs.F.Promise;

public interface BaseCacheComponent {

    public Promise<String> getResponseBody(String access_token) throws Throwable;

    public Promise<Throwable> putResponse(String access_token, CustomResponse response) throws Throwable;

}
