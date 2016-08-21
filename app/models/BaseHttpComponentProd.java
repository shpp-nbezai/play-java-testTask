package models;

/**
 *
 */
public class BaseHttpComponentProd implements BaseHttpComponent{

    public String facebookMeRouting(String access_token){

        String result = "Prod" + access_token;

        return result;
    }

}
