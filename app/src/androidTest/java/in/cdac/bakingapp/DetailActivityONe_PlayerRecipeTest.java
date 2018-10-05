package in.cdac.bakingapp;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class DetailActivityONe_PlayerRecipeTest {

    @Rule
    public final ActivityTestRule<DetailActivityOne> TestRule = new ActivityTestRule<>(DetailActivityOne.class);

    @Before
    public void init() {
        TestRule.getActivity()
                .getSupportFragmentManager().beginTransaction()

                .replace(android.R.id.content, new FragmentPlayerRecipe(), "fragment_palyer")
                .commit();

    }

    @Test
    public void checkPlayerViewIsVisible_DetailActivityOne() {

        onView(withId(R.id.exoplayer1))
                .check(matches(isDisplayed()));
    }

}
