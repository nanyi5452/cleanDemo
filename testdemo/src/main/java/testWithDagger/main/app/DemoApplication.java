package testWithDagger.main.app;

import android.app.Application;

import testWithDagger.main.di.components.DaggerApplicationComponent;
import testWithDagger.main.di.components.DataComponent;
import testWithDagger.main.di.modules.DataModule;

/**
 * Created by Administrator on 16-4-1.
 */
public class DemoApplication extends Application {


    DataComponent dataComponent=createComponent();

    public DataComponent getDataComponent() {
        return dataComponent;
    }
    // setter method exposed for DI injection in test
    public void setDataComponent(DataComponent dataComponent) {
        this.dataComponent=dataComponent;
    }

    protected DataComponent createComponent(){
        return DaggerApplicationComponent.builder().dataModule(new DataModule()).build();
    }


}
