package http;
import cache.BaseCacheComponent;
import errors.BadRequestException;
import errors.InternalServerErrorException;
import errors.InputDataException;
import requests.UserGetRequest;
import response.CustomResponse;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import play.libs.F.*;
import play.libs.F.RedeemablePromise;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.mockito.Mockito.*;


/**
 *
 * Simple (JUnit) tests that can call all parts of a play app.
 * If you are interested in mocking a whole application, see the wiki for more details.
 *
 */

public class ProdHttpComponentTest {
    private WSRequest mockWsRequest = mock(WSRequest.class);
    private WSClient mockWS = mock(WSClient.class);
    private WSResponse mockResponse = mock(WSResponse.class);
    private UserGetRequest mockUserRequest = mock(UserGetRequest.class);
    private BaseCacheComponent mockBaseCacheComponent = mock(BaseCacheComponent.class);
    private CustomResponse mockCustomResponse = mock(CustomResponse.class);

    private ProdHttpComponent testHttp = new ProdHttpComponent(mockWS, mockBaseCacheComponent);
    private final String URL_FACEBOOK_ME = "https://graph.facebook.com/v2.7/me/";

    private void setConfigMockWS(String requestBody, int requestCode, String access_token) {
        RedeemablePromise<WSResponse> mockPromiseResponse  = RedeemablePromise.empty();
        when(mockResponse.getBody()).thenReturn(requestBody);
        when(mockResponse.getStatus()).thenReturn(requestCode);
        mockPromiseResponse.success(mockResponse);

        when(mockWsRequest.get()).thenReturn(mockPromiseResponse);
        when(mockWsRequest.setQueryParameter("access_token", access_token)).thenReturn(mockWsRequest);

        when(mockWS.url(URL_FACEBOOK_ME)).thenReturn(mockWsRequest);
    }

    private void setMockBaseCacheComponentFull(String access_token) throws Throwable{
        Promise<String> responseBody = Promise.<String>pure("Test body");
        when(mockBaseCacheComponent.getResponseBody(access_token)).thenReturn(responseBody);
        when(mockBaseCacheComponent.putResponse(access_token, mockCustomResponse)).thenReturn(null);
    }

    private void setMockBaseCacheComponentEmpty(String access_token) throws Throwable{
        when(mockBaseCacheComponent.getResponseBody(access_token)).thenReturn(Promise.<String>pure(null));
        when(mockBaseCacheComponent.putResponse(eq(access_token), any(CustomResponse.class))).thenReturn(Promise.<Throwable>pure(null));
    }

    private void setMockBaseCacheComponentException(String access_token) throws Throwable{
        Promise<String> mockResponseBody  = Promise.<String>pure(null);
        CustomResponse customResponse = new CustomResponse(mockResponse);
        when(mockBaseCacheComponent.getResponseBody("some_token")).thenReturn(mockResponseBody);
        when(mockBaseCacheComponent.putResponse(access_token, customResponse)).thenThrow(Throwable.class);
    }

    private void setConfigExceptionWS() throws Throwable{
        when(mockWsRequest.setQueryParameter("access_token", "access_token")).thenThrow(Throwable.class);
    }


    private UserGetRequest getMockUserReguest(String access_token){
        when(mockUserRequest.getAccess_token()).thenReturn(access_token);
        return mockUserRequest;
    }

    @Test
    public void responseMessageTest() throws Throwable {
        setConfigMockWS("Test body", 200, "some_token");
        setMockBaseCacheComponentEmpty("some_token");
        mockUserRequest = getMockUserReguest("some_token");
        Promise<String> resultPromise = testHttp.facebookMeRouting(mockUserRequest);
        String testReturn = resultPromise.get(1000);
        assertEquals("Test body", testReturn);
    }

    @Test (expected = BadRequestException.class)
    public void badRequestExceptionTest() throws Throwable{
        setMockBaseCacheComponentEmpty("some_token");
        setConfigMockWS("Test body", 400, "some_token");
        mockUserRequest = getMockUserReguest("some_token");
        Promise<String> resultPromise = testHttp.facebookMeRouting(mockUserRequest);
        resultPromise.get(1000);
    }

    @Test (expected = Throwable.class)
    public void CacheExceptionTest() throws Throwable{
        setMockBaseCacheComponentException("some_token");
        setConfigMockWS("Test body", 400, "some_token");
        mockUserRequest = getMockUserReguest("some_token");
        Promise<String> resultPromise = testHttp.facebookMeRouting(mockUserRequest);
        resultPromise.get(1000);
    }

    @Test (expected = InternalServerErrorException.class)
    public void InternalServerErrorExceptionTest() throws Throwable{
        setMockBaseCacheComponentEmpty("some_token");
        setConfigMockWS("Test body", 500, "some_token");
        mockUserRequest = getMockUserReguest("some_token");
        Promise<String> resultPromise = testHttp.facebookMeRouting(mockUserRequest);
        resultPromise.get(1000);
    }

    @Test (expected = InputDataException.class)
    public void userGetRequestNULLTest() throws Throwable {
        setConfigMockWS("Test body", 200, "some token");
        Promise<String> resultPromise = testHttp.facebookMeRouting(null);
        resultPromise.get(1000);
    }

    @Test (expected = Throwable.class)
    public void wsServerErrorExeptionTest() throws Throwable {
        setMockBaseCacheComponentEmpty("some_token");
        setConfigExceptionWS();
        mockUserRequest = getMockUserReguest("some_token");
        Promise<String> resultPromise = testHttp.facebookMeRouting(mockUserRequest);
        resultPromise.get(1000);
    }

    @Test
    public void cacheWorkTest()throws Throwable{
        setMockBaseCacheComponentFull("some_token");
        setConfigMockWS("Test body", 200, "some_token");
        mockUserRequest = getMockUserReguest("some_token");
        Promise<String> resultPromise = testHttp.facebookMeRouting(mockUserRequest);
        String result = resultPromise.get(1000);
        assertEquals("Test body", result);
        verify(mockWsRequest, never()).get();

    }
}
