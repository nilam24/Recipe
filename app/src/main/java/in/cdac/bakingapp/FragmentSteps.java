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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("ALL")
public class FragmentSteps extends Fragment implements LoaderManager.LoaderCallbacks<List<RecipePojo>>, AdapterSteps.StepClickHandler {

    StepsPojo stepsPojo;
    List<StepsPojo> stepsPojoList;
    final int RECIPELOADERID = 3;
    @BindView(R.id.recycle)
    RecyclerView recyclerViewStep;
    AdapterSteps adapter;
    TaskStepHandler taskStepHandler;

    interface TaskStepHandler {

        void mCallStep(int itemClicked, List<StepsPojo> pojoList);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, root);
        stepsPojo = new StepsPojo();
        stepsPojoList = new ArrayList<>();
        Bundle bundleArg=getArguments();
        if(bundleArg!=null) {
            stepsPojoList = getArguments().getParcelableArrayList("step");
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if ((info != null) && (info.isConnected())) {
            getLoaderManager().initLoader(RECIPELOADERID, null, this);
        }
        recyclerViewStep.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewStep.setHasFixedSize(true);
        adapter = new AdapterSteps(getContext(), stepsPojoList, this);
        recyclerViewStep.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return root;
    }
    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        taskStepHandler = new DetailActivityOne();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        taskStepHandler = null;
    }
    @Override
    public Loader<List<RecipePojo>> onCreateLoader(int id, Bundle args) {
        return new RecipeLoader(getContext(), stepsPojoList, id);
    }
    @Override
    public void onLoadFinished(@NonNull Loader<List<RecipePojo>> loader, List<RecipePojo> data) {
        if (stepsPojoList != null) {

            adapter = new AdapterSteps(getContext(), stepsPojoList, this);
            recyclerViewStep.setHasFixedSize(true);
            recyclerViewStep.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onLoaderReset(@NonNull Loader<List<RecipePojo>> loader) {
        adapter.notifyDataSetChanged();
    }
    @Override
    public void mClickSteps(int pos, List<StepsPojo> list) {

        ((TaskStepHandler) getActivity()).mCallStep(pos, list);
    }
}
