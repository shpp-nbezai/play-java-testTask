package response;

import play.libs.ws.*;


public class CustomResponse {

    private int responseCode;
    private String responseBody;

    public CustomResponse(WSResponse wsResponse){
        responseCode = wsResponse.getStatus();
        responseBody = wsResponse.getBody();
    }

    public String getBody(){
        return responseBody;
    }

    public int getCode(){
        return responseCode;
    }
}
