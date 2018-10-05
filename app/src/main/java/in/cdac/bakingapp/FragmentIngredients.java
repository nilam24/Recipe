package in.cdac.bakingapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("ALL")
public class FragmentIngredients extends Fragment implements AdapterIngredientsRecipe.listClickHandler, LoaderManager.LoaderCallbacks<List<RecipePojo>> {

    @BindView(R.id.recyclerView3)
    RecyclerView recyclerViewIng;
    AdapterIngredientsRecipe adapterIngredientsRecipe;
    RecipePojo recipePojo;
    IngredientsPojo ingredientsPojo;
    List<IngredientsPojo> ingredientsPojoList;
    final int RECIPELOADERID = 2;
    TaskIngredientHandler taskIngredientHandler;

    interface TaskIngredientHandler {

        void CallBack(int index, List<IngredientsPojo> pojoList);
    }

    public FragmentIngredients() {
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        taskIngredientHandler = new DetailActivityOne();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_ingredient_layout, container, false);

        ButterKnife.bind(this, root);
        recipePojo = new RecipePojo();
        ingredientsPojo = new IngredientsPojo();
        ingredientsPojoList = new ArrayList<>();
        Bundle bArg=getArguments();
        if(bArg!=null) {
            ingredientsPojoList = getArguments().getParcelableArrayList("ing");
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if ((info != null) && (info.isConnected())) {
            getLoaderManager().initLoader(RECIPELOADERID, null, this);
        }
        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewIng.setLayoutManager(linearLayout);
        adapterIngredientsRecipe = new AdapterIngredientsRecipe(getContext(), ingredientsPojoList, this);
        recyclerViewIng.setAdapter(adapterIngredientsRecipe);
        adapterIngredientsRecipe.notifyDataSetChanged();
        return root;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        taskIngredientHandler = null;
    }
    @NonNull
    @Override
    public Loader<List<RecipePojo>> onCreateLoader(int id, Bundle args) {

        RecipeLoader recipeLoader = new RecipeLoader(getContext(), ingredientsPojoList, id);
        Log.e("Loader create====", "" + ingredientsPojoList);
        return recipeLoader;
    }
    @Override
    public void onLoadFinished(Loader<List<RecipePojo>> loader, List<RecipePojo> data) {

        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewIng.setLayoutManager(linearLayout);
        recyclerViewIng.setHasFixedSize(true);
        adapterIngredientsRecipe = new AdapterIngredientsRecipe(getContext(), ingredientsPojoList, this);
        recyclerViewIng.setAdapter(adapterIngredientsRecipe);
        adapterIngredientsRecipe.notifyDataSetChanged();
    }
    @Override
    public void onLoaderReset(Loader<List<RecipePojo>> loader) {

        adapterIngredientsRecipe.notifyDataSetChanged();
    }
    @Override
    public void mClick(int index, List<IngredientsPojo> pojoList) {

        ((TaskIngredientHandler) getActivity()).CallBack(index, pojoList);
    }
}
