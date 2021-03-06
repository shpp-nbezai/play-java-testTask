package http;

import cache.BaseCacheComponent;
import errors.InternalServerErrorException;
import requests.UserGetRequest;
import response.CustomResponse;
import play.libs.ws.*;
import play.libs.F.Promise;
import play.libs.F.Function;
import errors.BadRequestException;
import errors.InputDataException;
import javax.inject.*;

@Singleton
public class ProdHttpComponent implements BaseHttpComponent {

    private final WSClient ws;
    private final BaseCacheComponent cacheComponent;
    private final String URL_FACEBOOK_ME = "https://graph.facebook.com/v2.7/me/";
    private final int OK_RESPONSE_CODE = 200;
    private final int BAD_RESPONSE_CODE = 400;
    private final int SERVER_ERROR_CODE = 500;

    @Inject
    public ProdHttpComponent(WSClient ws, BaseCacheComponent cacheComponent) {
        this.ws = ws;
        this.cacheComponent = cacheComponent;
    }

    private Promise<WSResponse> getFBResponse(String access_token){
        WSRequest wsRequest = ws.url(URL_FACEBOOK_ME).setQueryParameter("access_token", access_token);
        return wsRequest.get();
    }

    private Promise<String> cacheResponse(String accessToken, WSResponse wsresponse) throws Throwable{
        CustomResponse customResponse = new CustomResponse(wsresponse);
        Promise<Throwable> cacheException = cacheComponent.putResponse(accessToken, customResponse);
        return cacheException.flatMap(throwable -> {
            if (throwable != null) {
                throw new Throwable(throwable.getMessage());
            }
            return Promise.<String>pure(wsresponse.getBody());
        });
    }

    private boolean assertResponseCode(WSResponse wsResponse) throws Throwable{
        int wsResponseCode = wsResponse.getStatus();
        String wsResponseBody = wsResponse.getBody();

        if (wsResponseCode >= BAD_RESPONSE_CODE && wsResponseCode < SERVER_ERROR_CODE) {
            throw new BadRequestException(wsResponseBody);
        }
        if (wsResponseCode >= SERVER_ERROR_CODE) {
            throw new InternalServerErrorException(wsResponseBody);
        }
        if (wsResponseCode == OK_RESPONSE_CODE) {
            return true;
        }
        return false;
    }

    private Promise<String> getRresponseBody(Promise<WSResponse> response, String access_token){
        return response.flatMap(wsResponse -> {
            if (assertResponseCode(wsResponse)) {
                return cacheResponse(access_token, wsResponse);
            }
            return Promise.<String>pure(wsResponse.getBody());
        });
    }

    @Override
    public Promise<String> facebookMeRouting(UserGetRequest request)  throws Throwable{
        if (request == null) {
            throw new InputDataException("request = null");
        }
        String access_token = request.getAccess_token();
        Promise<String> cacheResponseBody = cacheComponent.getResponseBody(access_token);
        return cacheResponseBody.flatMap(new Function<String, Promise<String>>() {
            @Override
            public Promise<String> apply(String responseBody) {
                if (responseBody == null) {
                    Promise<WSResponse> fbResponse = getFBResponse(access_token);
                    return getRresponseBody(fbResponse, access_token);
                }

                return Promise.<String>pure(responseBody);
            }
        });
    }
}
