package models;

import javax.inject.Inject;
import javax.inject.Singleton;

/*
 *
 */

@Singleton
public interface BaseHttpComponent {

    public String facebookMeRouting(String access_token);

}
