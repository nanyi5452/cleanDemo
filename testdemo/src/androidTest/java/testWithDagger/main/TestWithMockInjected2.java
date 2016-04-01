package testWithDagger.main;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import testWithDagger.main.app.DemoApplication;
import testWithDagger.main.data.MyDataSource;
import testWithDagger.main.di.components.DataComponent;
import testWithDagger.main.modules.TestDataModule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Administrator on 16-4-1.
 */
@RunWith(AndroidJUnit4.class)
public class TestWithMockInjected2 {

    @Inject
    MyDataSource mockDataSource;

    @Singleton
    @Component(modules = TestDataModule.class)
    public interface TestComponent2 extends DataComponent {
        void inject(TestWithMockInjected2 myTest);
    }


    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(
            MainActivity.class,
            true,     // initialTouchMode
            false);   // launchActivity. False so we can customize the intent per test method

    @Before
    public void setUp() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        DemoApplication app
                = (DemoApplication) instrumentation.getTargetContext().getApplicationContext();
        TestComponent2 testComponent=(TestComponent2)app.getDataComponent();
        testComponent.inject(this);
    }


    @Test
    public void mockData() {
        Mockito.when(mockDataSource.getStringData("hello dagger")).thenReturn("this is mock string");
        activityRule.launchActivity(new Intent());
        onView(withId(R.id.main_tv))
                .check(matches(withText("this is mock string")));
    }


    @Test
    public void mockData_intent() {
        Mockito.when(mockDataSource.getStringData("AAAA")).thenReturn("AAAA this is mock string");
        Mockito.when(mockDataSource.getStringData("")).thenReturn("this is mock string");

        Intent testIntent=new Intent();
        testIntent.putExtra(MainActivity.INTENT_KEY,"AAAA");
        activityRule.launchActivity(testIntent);
        onView(withId(R.id.main_tv))
                .check(matches(withText("AAAA this is mock string")));
    }





}
