import cache.BaseCacheComponent;
import cache.CacheComponentOnMap;
import com.google.inject.AbstractModule;
import http.BaseHttpComponent;
import http.DummyHttpComponent;

public class DummyMolule extends AbstractModule {

    public void configure(){
        bind(BaseHttpComponent.class).to(DummyHttpComponent.class);
        bind(BaseCacheComponent.class).to(CacheComponentOnMap.class);
    }
}
