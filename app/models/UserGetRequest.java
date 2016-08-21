package models;

import play.Logger;
import play.data.validation.ValidationError;
import play.data.validation.Constraints;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class UserGetRequest {

    private final int ACCESS_TOKEN_CUR_LENGTH = 177;

    @Constraints.Required
    protected String access_token;

    public String getAccess_token() {

        return access_token;

    }

    public void setAccess_token(String access_token) {

        this.access_token = access_token;

    }

    public Map<String, List<ValidationError>> validate() {
        Map<String, List<ValidationError>> errors = new LinkedHashMap<String, List<ValidationError>>();
        Logger.debug(access_token);
        if (access_token.length() != ACCESS_TOKEN_CUR_LENGTH) {

            if (!errors.containsKey(access_token)) {
                List<ValidationError> errorList = new ArrayList<ValidationError>();
                errorList.add(new ValidationError("access_token", "Invalid access token! Not enough characters."));
                errors.put(access_token, errorList);
            }
        }
        return errors.isEmpty() ? null : errors;
    }
}