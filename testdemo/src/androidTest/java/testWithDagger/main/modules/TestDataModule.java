package testWithDagger.main.modules;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import testWithDagger.main.data.MyDataSource;
import testWithDagger.main.data.NetDataSource;

/**
 * Created by Administrator on 16-4-1.
 */
@Module
public class TestDataModule {

    @Provides
    @Singleton
    public MyDataSource getDataSource(){
        return Mockito.mock(NetDataSource.class);
    }


}
