package in.cdac.bakingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class DetailActivityOneTest {

//find the view  :: listview
//perform action on view  clickevent
//find the result    outResult display items or play video or detail view

    @Rule
    public final ActivityTestRule<DetailActivityOne> detailActivityOneActivityTestRule = new ActivityTestRule<>(DetailActivityOne.class);
    @Before
    public void init(){
        detailActivityOneActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction()

                .replace(R.id.frame_one, new FragmentIngredients(), "fragmentIngredients")
                .commit();

        detailActivityOneActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_two, new FragmentSteps(),"fragmentSteps")
                .commit();

    }

    @Test
    public void TestAutoCompleteLoad_DetailActivityOne() {

        onView(withId(R.id.recycle)).perform(click());
        onView(withId(R.id.recyclerView3)).perform(click());
        Log.e("test", "test");
    }

}
