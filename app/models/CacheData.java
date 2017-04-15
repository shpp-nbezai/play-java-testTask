package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;
import javax.persistence.*;

@Entity
public class CacheData extends Model{

    @Id
    @Constraints.Required
    public int access_token;

    @Constraints.Required
    public String responseBody;

    public static Finder<Integer, CacheData> find = new Finder<Integer,CacheData>(CacheData.class);

}
