package testWithDagger.main;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import testWithDagger.main.MainActivity;
import testWithDagger.main.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Administrator on 16-4-1.
 */
@RunWith(AndroidJUnit4.class)
public class SimpleTest {


    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(
            MainActivity.class,
            true,     // initialTouchMode
            false);   // launchActivity. False so we can customize the intent per test method

    @Before
    public void setUp() {
    }


    @Test
    public void mockData() {
        activityRule.launchActivity(new Intent());
        onView(withId(R.id.main_tv))
                .check(matches(withText("hello dagger from internet")));
    }


    @Test
    public void mockData_intent() {
        Intent testIntent=new Intent();
        testIntent.putExtra(MainActivity.INTENT_KEY,"AAAA");
        activityRule.launchActivity(testIntent);
        onView(withId(R.id.main_tv))
                .check(matches(withText("AAAA from internet")));
    }





}
