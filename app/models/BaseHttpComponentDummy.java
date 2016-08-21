package models;

/*
 *
 */
public class BaseHttpComponentDummy implements BaseHttpComponent {

    @Override
    public String facebookMeRouting(String access_token) {

        String result = "Mock" + access_token;

        return result;
    }
}
