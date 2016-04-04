package testWithDagger.main.dagger.demo.consInjection;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import testWithDagger.main.data.MyDataSource;

/**
 * Created by Administrator on 16-4-1.
 */
@Module
public class ConstructorModule {

    @Provides
    @Singleton
    public MyDataSource getDataSource(){return new LocalDataSource();}

}
