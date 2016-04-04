package testWithDagger.main.dagger.demo.consInjection;

import javax.inject.Inject;

import testWithDagger.main.data.MyDataSource;

/**
 * Created by Administrator on 16-4-2.
 */

public class LocalDataSource implements MyDataSource {

    @Inject
    public LocalDataSource(){}

    @Override
    public String getStringData(String str_input) {
        return "hello dagger2--- local data source";
    }

}
