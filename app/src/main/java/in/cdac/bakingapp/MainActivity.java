package in.cdac.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity implements FragmentMain.TaskHandler, AdapterRecipe.ClickHandler, FragmentIngredients.TaskIngredientHandler, FragmentSteps.TaskStepHandler {

    FragmentManager manager;
    FragmentMain fragmentMain;
    final String TAG = "fragment_main";
    Fragment f;
    FragmentTransaction transaction;
    RecipePojo recipePojo;
    List<RecipePojo> pojoList;
    SharedPreferences preferences;
    public static final String mypreferences = "in.cdac.bakingapp";
    String titleName = "";
    boolean mTwoPane;
    FragmentIngredients fragmentIngredients1;
    FragmentSteps fragmentSteps;
    String TAG2 = "fragmentIngredients1";
    String TAG3 = "fragmentSteps";
    List<IngredientsPojo> ingredientsPojoList;
    List<StepsPojo> stepsPojoList;
    ActionBar actionBar;
    FragmentPlayerRecipe playerRecipe;
    String TAG5 = "fragment_player_recipe";
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        preferences = getApplicationContext().getSharedPreferences(mypreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("title", titleName);
        editor.apply();
        manager = getSupportFragmentManager();
        fragmentMain = (FragmentMain) manager.findFragmentById(R.id.fragment_main);

        if (findViewById(R.id.linear_twopane) != null) {
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }

        recipePojo = new RecipePojo();
        pojoList = new ArrayList<>();


        if (savedInstanceState == null) {

            fragmentMain = new FragmentMain();
            f = manager.findFragmentByTag(TAG);
            manager.findFragmentById(R.id.fragment_main);
            transaction = manager.beginTransaction();
            transaction.replace(R.id.fragment_main, fragmentMain).addToBackStack("fragmentStackMain");
            transaction.commit();
            Log.e("", "zeroFragment");
        }

        if (mTwoPane) {
            fragmentMain = new FragmentMain();
            f = manager.findFragmentByTag(TAG);
            manager.findFragmentById(R.id.fragment_main);
            transaction = manager.beginTransaction();
            transaction.replace(R.id.fragment_main, fragmentMain).addToBackStack("fragmentStack1");
            transaction.commit();

        }
        actionBarSetup();
    }

    public void actionBarSetup() {
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(false);
        preferences = getSharedPreferences(mypreferences, Context.MODE_PRIVATE);
        actionBar.setTitle("Baking App");
        titleName = preferences.getString("title", null);
        actionBar.setSubtitle(titleName);
        Log.e("title", "" + titleName);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("fragmentMain", String.valueOf(fragmentMain));

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.get("fragmentMain");

    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {

        RecipePojo recipePojo = new RecipePojo();
        return super.onRetainCustomNonConfigurationInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecipePojo recipePojo = new RecipePojo();
        FragmentMain fragmentMain = new FragmentMain();
        manager.findFragmentById(R.id.fragment_main);
        fragmentMain.setRetainInstance(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FragmentMain fragmentMain = new FragmentMain();
        fragmentMain.setRetainInstance(true);
    }

    @Override
    public void mCallBack(int pos, List<RecipePojo> recipePojoList) {

        int id = 1;
        recipePojo = recipePojoList.get(pos);
        ingredientsPojoList = new ArrayList<>();
        stepsPojoList = new ArrayList<>();


        if (mTwoPane) {


            if (recipePojo != null) {
                ingredientsPojoList = recipePojo.getIngredientsList();
                stepsPojoList = recipePojo.getStepsPojoList();
            }
            Bundle bundleIng = new Bundle();
            if (ingredientsPojoList != null) {
                bundleIng.putParcelableArrayList("ing", (ArrayList<? extends Parcelable>) ingredientsPojoList);
                bundleIng.putAll(bundleIng);
                fragmentIngredients1 = new FragmentIngredients();
                FragmentTransaction transaction2 = manager.beginTransaction();
                manager.findFragmentByTag(TAG2);
                fragmentIngredients1.setArguments(bundleIng);
                transaction2.add(R.id.frame_one, fragmentIngredients1);
                transaction2.addToBackStack("STACK_FRAGMENT_DETAIL2");
                transaction2.commit();
            }
            Bundle bundleStep = new Bundle();

            if (stepsPojoList != null) {
                bundleStep.putParcelableArrayList("step", (ArrayList<? extends Parcelable>) stepsPojoList);
                bundleStep.putAll(bundleStep);
                fragmentSteps = new FragmentSteps();
                manager = getSupportFragmentManager();
                manager.findFragmentByTag(TAG3);
                FragmentTransaction transaction1 = manager.beginTransaction();
                fragmentSteps.setArguments(bundleStep);
                transaction1.add(R.id.frame_two, fragmentSteps);
                transaction1.addToBackStack("STACK_FRAGMENT_DETAIL");
                transaction1.commit();
            }

        } else {
            Intent intent = new Intent(MainActivity.this, DetailActivityOne.class);
            Bundle bundlePojo = new Bundle();
            bundlePojo.putParcelable("pojo", recipePojo);
            bundlePojo.putAll(bundlePojo);
            intent.putExtras(bundlePojo);
            startActivity(intent);
            Log.e("Main", "" + recipePojo);

        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        manager = getSupportFragmentManager();
        manager.popBackStack();
        manager.popBackStack(0, 0);
    }

    @Override
    public void mclick(int index, List<RecipePojo> pojoList) {

        Log.e("Main", "" + recipePojo);
    }

    @Override
    public void CallBack(int index, List<IngredientsPojo> pojoList) {

        Toast.makeText(this, "" + index, Toast.LENGTH_LONG).show();
    }

    @Override
    public void mCallStep(int itemClicked, List<StepsPojo> pojoList) {

        StepsPojo pojo = stepsPojoList.get(itemClicked);
        String video = pojo.getVideoURL();

        if (video != null) {
            actionBar.hide();
            Uri uri = Uri.parse(video);
            Bundle bundleVideo = new Bundle();
            bundleVideo.putString("video", uri.toString());
            manager = getSupportFragmentManager();
            FragmentTransaction t = manager.beginTransaction();
            playerRecipe = new FragmentPlayerRecipe();
            if (bundleVideo != null) {
                playerRecipe.setArguments(bundleVideo);
            }
            manager.findFragmentByTag(TAG5);
            t.add(android.R.id.content, playerRecipe, TAG5);
            t.addToBackStack("STACK_FRAGMENT2");
            t.commit();
            Log.e("video", "uri==");
        }
    }
}
