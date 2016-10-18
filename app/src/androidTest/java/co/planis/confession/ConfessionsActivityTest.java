package co.planis.confession;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;



import co.planis.confession.ui.feed.ConfessionsActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


/**
 * Created by janpluta on 18.10.16.
 */
@RunWith(AndroidJUnit4.class)
public class ConfessionsActivityTest {

    @Rule
    public ActivityTestRule<ConfessionsActivity> mActivityRule = new ActivityTestRule<ConfessionsActivity>(ConfessionsActivity.class);


    @Test
    public void addConfessionClick(){

        onView(withId(R.id.confessionListAddFab)).perform(click());
        onView(withId(R.id.addConfessionAuthorEt)).perform(typeText("Anonim"),closeSoftKeyboard());


        onView(withId(R.id.addConfessionPreviewEt)).perform(replaceText("Jestem aniołem"),closeSoftKeyboard());


        onView(withId(R.id.addConfessionPreviewEt)).check(matches(isDisplayed()));
    }
}
