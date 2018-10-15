package com.example.prate.secretsanta;

import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;
import static android.support.test.espresso.action.ViewActions.typeText;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityUITest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    /*
    These UI tests  test the functionality of the Secret Santa Application
    The scenarios tested are user click events for the add family button, submit family button, and
    reset button. They also check that the displayed output is correct for normal usage
     */

    // This test checks if the add family button empties the add families edit text
    @Test
    public void addOneFamilyTest() {
        onView(withId(R.id.family_members_edit_text)).perform(ViewActions.closeSoftKeyboard(), click());
        onView(withId(R.id.family_members_edit_text)).perform(typeText("1, 1, 1, 1, 1, 1"));
        onView(withId(R.id.add_family_button)).perform(click());
        onView(withId(R.id.family_members_edit_text)).check(matches(withText("")));
    }

    // This test checks that the secret santa text gives further instructions if no familes were added
    @Test
    public void testSubmitButton() {
        onView(withId(R.id.family_members_edit_text)).perform(ViewActions.closeSoftKeyboard(), click());
        onView(withId(R.id.submit_family_button)).perform(click());
        onView(withId(R.id.secret_santa_text_view)).check(matches(withText(containsString("You forgot to add names"))));
    }

    // This test checks that the reset button clears the secret santa text view
    @Test
    public void testResetButton() {
        onView(withId(R.id.family_members_edit_text)).perform(ViewActions.closeSoftKeyboard(), click());
        onView(withId(R.id.family_members_edit_text)).perform(typeText("1, 1, 1, 1, 1, 1"));
        onView(withId(R.id.add_family_button)).perform(click());
        onView(withId(R.id.family_members_edit_text)).perform(typeText("2, 2, 2, 2, 2, 2"));
        onView(withId(R.id.add_family_button)).perform(click());
        onView(withId(R.id.submit_family_button)).perform(click());
        onView(withId(R.id.reset_button)).perform(click());
        onView(withId(R.id.secret_santa_text_view)).check(matches(withText(containsString(""))));
    }

    // This test checks if family members can gift one another. (It uses ones and twos because
    // it is simpler to check with single numbers rather than names)
    @Test
    public void testFamiliesCantGiftOneAnother() {
        onView(withId(R.id.family_members_edit_text)).perform(ViewActions.closeSoftKeyboard(), click());
        onView(withId(R.id.family_members_edit_text)).perform(typeText("1, 1, 1, 1, 1, 1"));
        onView(withId(R.id.add_family_button)).perform(click());
        onView(withId(R.id.family_members_edit_text)).perform(typeText("2, 2, 2, 2, 2, 2"));
        onView(withId(R.id.add_family_button)).perform(click());
        onView(withId(R.id.submit_family_button)).perform(click());
        onView(withId(R.id.family_members_edit_text)).check(matches(not(withText("1 --> 1"))));
        onView(withId(R.id.family_members_edit_text)).check(matches(not(withText("2 --> 2"))));
    }

    // This text checks that no solution is printed if one family is larger
    // than the other families combined
    @Test
    public void testFamilySizeEdgeCase() {
        onView(withId(R.id.family_members_edit_text)).perform(ViewActions.closeSoftKeyboard(), click());
        onView(withId(R.id.family_members_edit_text)).perform(typeText("1"));
        onView(withId(R.id.add_family_button)).perform(click());
        onView(withId(R.id.family_members_edit_text)).perform(typeText("2"));
        onView(withId(R.id.add_family_button)).perform(click());
        onView(withId(R.id.family_members_edit_text)).perform(typeText("3, 3, 3"));
        onView(withId(R.id.add_family_button)).perform(click());
        onView(withId(R.id.submit_family_button)).perform(click());
        onView(withId(R.id.secret_santa_text_view)).check(matches(withText(containsString("No Solution"))));

    }

    // This tests that there is no solution if there are fewer participants than there are years
    // constraints. Example two families with two participants.
    @Test
    public void testFewerNamesThanYearsConstraintEdgeCase() {

        onView(withId(R.id.family_members_edit_text)).perform(ViewActions.closeSoftKeyboard(), click());
        onView(withId(R.id.family_members_edit_text)).perform(typeText("1, 1"));
        onView(withId(R.id.add_family_button)).perform(click());
        onView(withId(R.id.family_members_edit_text)).perform(typeText("2, 2"));
        onView(withId(R.id.add_family_button)).perform(click());
        // Click twice because the first result is a valid solution
        onView(withId(R.id.submit_family_button)).perform(click(), click());
        onView(withId(R.id.secret_santa_text_view)).check(matches(withText(containsString("No Solution"))));
    }

}