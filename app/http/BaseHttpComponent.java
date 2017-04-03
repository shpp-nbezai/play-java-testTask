package http;

import requests.UserGetRequest;
import play.libs.F.Promise;

public interface BaseHttpComponent {

    public Promise<String> facebookMeRouting(UserGetRequest request) throws Throwable;

}
