import cache.BaseCacheComponent;
import cache.CachePostgreSQLComponent;
import com.google.inject.AbstractModule;
import http.BaseHttpComponent;
import http.ProdHttpComponent;

public class ProdModule extends AbstractModule{

    @Override
    public void configure(){
        bind(BaseHttpComponent.class).to(ProdHttpComponent.class);
        bind(BaseCacheComponent.class).to(CachePostgreSQLComponent.class);
    }
}
