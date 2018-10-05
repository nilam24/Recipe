package in.cdac.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("ALL")
public class DetailActivityOne extends AppCompatActivity implements FragmentIngredients.TaskIngredientHandler, FragmentSteps.TaskStepHandler {

    @BindView(R.id.frame_one)
    FrameLayout frameLayout1;
    @BindView(R.id.frame_two)
    FrameLayout frameLayout2;
    FragmentManager manager;
    FragmentSteps fragmentSteps;
    FragmentIngredients fragmentIngredients;
    final String TAG2 = "fragment_ingredients";
    final String TAG3 = "fragment_steps";
    FragmentTransaction fragmentTransaction;
    RecipePojo recipePojo1;
    IngredientsPojo ingredientsPojo;
    StepsPojo stepsPojo;
    List<RecipePojo> pojoList;
    List<IngredientsPojo> ingredientsPojoList;
    List<StepsPojo> stepsPojoList;
    Bundle b, bStep;
    FragmentPlayerRecipe playerRecipe;
    final String TAG5 = "fragment_palyer";
    String nameTitle;
    SharedPreferences preferences;
    public static final String mypreferences = "in.cdac.bakingapp";
    ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
        preferences = getApplicationContext().getSharedPreferences(mypreferences, Context.MODE_PRIVATE);
        ButterKnife.bind(this);
        manager = getSupportFragmentManager();
        manager.findFragmentById(R.id.frame_one);
        manager.findFragmentById(R.id.frame_two);
        pojoList = new ArrayList<>();
        ingredientsPojoList = new ArrayList<>();
        ingredientsPojo = new IngredientsPojo();
        Intent intent = getIntent();
        recipePojo1 = getIntent().getParcelableExtra("pojo");
        if (recipePojo1 != null) {
            ingredientsPojoList = recipePojo1.getIngredientsList();

            Log.e("Detail==", "bundle" + recipePojo1);
            Log.e("quant==" + ingredientsPojo, " " + ingredientsPojoList);

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("subTitle", recipePojo1.getName());
            editor.apply();
        }

        b = new Bundle();
        b.putParcelableArrayList("ing", (ArrayList<? extends Parcelable>) ingredientsPojoList);
        b.putAll(b);
        Log.e("*******", "" + b);

        if (b != null) {

            fragmentIngredients = new FragmentIngredients();
            FragmentTransaction transaction = manager.beginTransaction();
            manager.findFragmentByTag(TAG2);
            fragmentIngredients.setArguments(b);
            transaction.replace(R.id.frame_one, fragmentIngredients);
            transaction.addToBackStack("STACK_FRAGMENT_DETAIL1");
            transaction.commit();
            stepsPojo = new StepsPojo();
            stepsPojoList = new ArrayList<>();
            if (recipePojo1 != null) {
                stepsPojoList = recipePojo1.getStepsPojoList();
            }
            bStep = new Bundle();
            bStep.putParcelableArrayList("step", (ArrayList<? extends Parcelable>) stepsPojoList);
            bStep.putAll(bStep);
            Log.e("bstepppp", "" + bStep);
            fragmentSteps = new FragmentSteps();
            manager = getSupportFragmentManager();
            manager.findFragmentByTag(TAG3);
            FragmentTransaction transaction1 = manager.beginTransaction();
            fragmentSteps.setArguments(bStep);
            transaction1.replace(R.id.frame_two, fragmentSteps);
            transaction1.addToBackStack("STACK_FRAGMENT_DETAIL");
            transaction1.commit();

        }

        actionBarSetup();

    }

    public void actionBarSetup() {
        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        preferences = getSharedPreferences(mypreferences, Context.MODE_PRIVATE);
        nameTitle = preferences.getString("subTitle", null);
        actionBar.setTitle(nameTitle);

        Log.e("actionbar", "" + nameTitle);
    }

    @Override
    public void CallBack(int index, List<IngredientsPojo> pojoList) {

        IngredientsPojo pojo = pojoList.get(index);
        frameLayout1.setVisibility(View.GONE);
        frameLayout2.setVisibility(View.GONE);
        FragmentIngredients fragmentIngredients1 = new FragmentIngredients();
        FragmentTransaction transaction2 = manager.beginTransaction();
        manager.findFragmentByTag(TAG2);
        fragmentIngredients1.setArguments(b);
        transaction2.replace(android.R.id.content, fragmentIngredients1);
        transaction2.addToBackStack("STACK_FRAGMENT_DETAIL2");
        transaction2.commit();
        Toast.makeText(this, "" + pojo, Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("recipePojo1", recipePojo1);

    }

    @Override
    public void mCallStep(int itemClicked, List<StepsPojo> stepsPojoList) {

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        manager = getSupportFragmentManager();
        manager.popBackStack(0, 0);
        manager.popBackStackImmediate();
    }

}
