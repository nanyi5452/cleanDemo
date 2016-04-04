package testWithDagger.main.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import testWithDagger.main.data.MyDataSource;
import testWithDagger.main.data.NetDataSource;

/**
 * Created by Administrator on 16-4-1.
 */
@Module
public class DataModule {

    @Provides
    @Singleton
    public MyDataSource getDataSource(){return new NetDataSource();}

//    @Provides
//    @Singleton
//    @Named("local")
//    public MyDataSource getLocalDataSource(){return new LocalDataSource();}

}
