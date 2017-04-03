package requests;

import play.data.validation.Constraints;


public class UserGetRequest {

    private final int ACCESS_TOKEN_CUR_LENGTH = 10;

    @Constraints.Required
    @Constraints.MinLength(10)
    protected String access_token;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

}