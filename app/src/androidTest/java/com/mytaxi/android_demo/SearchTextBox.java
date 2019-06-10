package com.mytaxi.android_demo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.action.ViewActions;
import androidx.test.rule.ActivityTestRule;

import com.mytaxi.android_demo.activities.MainActivity;

import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import androidx.test.runner.AndroidJUnit4;

import androidx.test.rule.GrantPermissionRule;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;


@RunWith(AndroidJUnit4.class)

public class SearchTextBox {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);
    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");
    @Before
    public void up() {
        activityTestRule.getActivity().deleteSharedPreferences("MytaxiPrefs");
    }

    private MainActivity mActivity = null;

    @Before
    public void setActivity() {
        mActivity = activityTestRule.getActivity();
    }

    @Test
    public void testAutoComplete(){

        // login with valid credentials
        onView(withId(R.id.edt_username))
                .perform(typeText("crazydog335"));
        onView(withId(R.id.edt_password))
                .perform(typeText("venture"));
        onView(withId(R.id.btn_login)).perform(ViewActions.click());
        // wait till the toast message is displayed
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Type "sa" to trigger the suggestions.
        onView(withId(R.id.textSearch))
                .perform(typeText("sa"), closeSoftKeyboard());

        // Tap on the second suggestion.
        onView(withText("Sarah Scott"))
                .inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                .perform(click());

        // Click on call button
        onView(withId(R.id.fab)).perform(ViewActions.click());
    }

}
