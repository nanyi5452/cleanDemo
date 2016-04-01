package testWithDagger.main.app;

import testWithDagger.main.DaggerTestWithMockInjected2_TestComponent2;
import testWithDagger.main.di.components.DataComponent;
import testWithDagger.main.modules.TestDataModule;

/**
 * Created by Administrator on 16-4-1.
 */
public class MockDemoApplication extends DemoApplication {

    @Override
    protected DataComponent createComponent() {
        return DaggerTestWithMockInjected2_TestComponent2.builder().testDataModule(new TestDataModule()).build();
    }

}
