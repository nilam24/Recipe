package in.cdac.bakingapp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
class RecipeLoader extends AsyncTaskLoader<List<RecipePojo>> {

    private String req;

    //    public RecipeLoader(@NonNull Context context) {
//        super(context);
//    }
    public RecipeLoader(Context context, String req) {
        super(context);
        this.req = req;
    }
    public RecipeLoader(Context context, List<IngredientsPojo> ingredientsPojoList, int id) {
        super(context);
    }

    public RecipeLoader(Context context, List<StepsPojo> stepsPojoList, long id) {
        super(context);

    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }

    @Nullable
    @Override
    public List<RecipePojo> loadInBackground() {

        List<RecipePojo> recipePojoList = new ArrayList<>();

        if (req != null) {
            recipePojoList = QueryUtils.fetchDataFromQueryUtils(req);
            Log.e("Fetch_Result", "    " + recipePojoList);
        }
        if(recipePojoList ==null)
        {
            return null;
        }
        else return recipePojoList;
    }
}
