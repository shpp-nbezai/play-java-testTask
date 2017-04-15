package requests;

import play.data.validation.Constraints;
import javax.inject.*;

@Singleton
public class UserGetRequest {

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