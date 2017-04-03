package http;

import requests.UserGetRequest;
import play.libs.F.RedeemablePromise;
import play.libs.F.Promise;

public class DummyHttpComponent implements BaseHttpComponent {

    private final String MOCK =  "{\n" +
            "  \"id\": \"100000618003384\",\n" +
            "  \"name\": \"Andrii Martynov Dummy conf\"\n" +
            "}";
    private final RedeemablePromise<String> promise = RedeemablePromise.empty();

    @Override
    public Promise<String> facebookMeRouting(UserGetRequest request) {
        promise.success(MOCK);
        return promise;
    }
}
