package testWithDagger.main;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

import testWithDagger.main.app.MockDemoApplication;

/**
 * Created by Administrator on 16-4-1.
 */
public class MockTestRunner extends AndroidJUnitRunner {

    @Override
    public Application newApplication(ClassLoader cl, String className, Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(cl, MockDemoApplication.class.getName(), context);
    }
}
