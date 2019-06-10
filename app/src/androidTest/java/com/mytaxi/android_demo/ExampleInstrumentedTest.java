package com.mytaxi.android_demo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.action.ViewActions;
import androidx.test.rule.ActivityTestRule;

import com.mytaxi.android_demo.activities.MainActivity;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import androidx.test.runner.AndroidJUnit4;

import android.support.test.filters.LargeTest;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;

import androidx.test.rule.GrantPermissionRule;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");
    @Before
    public void logout() {
        activityTestRule.getActivity().deleteSharedPreferences("MytaxiPrefs");
    }

    @Test
    public void uiLoginFields() {
        onView(withId(R.id.edt_username))
                .check(matches(isDisplayed()));
        onView(withId(R.id.edt_password))
                .check(matches(isDisplayed()));
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
    }

    @Test
    public void wrongUsername() {
        // wait till the mobile
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.edt_username))
                .perform(typeText("wrong"));
        onView(withId(R.id.edt_password))
                .perform(typeText("venture"), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(ViewActions.click());

        // wait till the toast message is displayed
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void wrongPassword(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.edt_username))
                .perform(typeText("crazydog335"));
        onView(withId(R.id.edt_password))
                .perform(typeText("wrong"), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(ViewActions.click());
        // wait till the toast message is displayed
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void loginSuccessfully() {
        // Check valid username and password will cause successfully login
        onView(withId(R.id.edt_username))
                .perform(typeText("crazydog335"));
        onView(withId(R.id.edt_password))
                .perform(typeText("venture"));
        onView(withId(R.id.btn_login)).perform(ViewActions.click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));

        onView(withText(R.string.app_name)).check(matches(withParent(withId(R.id.toolbar))));

        // Delete the shared storage as the emulator keeps the last state of login
        activityTestRule.getActivity().deleteSharedPreferences("MytaxiPrefs");
    }
}
