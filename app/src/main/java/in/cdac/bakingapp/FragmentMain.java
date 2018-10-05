package in.cdac.bakingapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
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
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("ALL")
public class FragmentMain extends Fragment implements LoaderManager.LoaderCallbacks<List<RecipePojo>> ,AdapterRecipe.ClickHandler{

    final String req="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    AdapterRecipe adapterRecipe;
    List<RecipePojo>pojoList;
    @SuppressWarnings("FieldCanBeLocal")
    private static final int RECIPELOADER_ID=1;
    int index;
    @BindView(R.id.emptyText)TextView empty;
    @BindView(R.id.recycle2)RecyclerView recyclerView;

    TaskHandler handler;

    interface TaskHandler{

        void mCallBack(int index,List<RecipePojo> recipePojoList);
    }


    public FragmentMain(){}
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        FragmentMain fragmentMain=new FragmentMain();
        fragmentMain.setRetainInstance(true);
        handler= (TaskHandler) context;
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);

        handler=new MainActivity();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       if(savedInstanceState!=null)
       {
           savedInstanceState.getParcelableArrayList("pojoList");
       }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        if(savedInstanceState!=null)
        {
            savedInstanceState.getParcelableArrayList("pojoList");
        }
        View rootView=inflater.inflate(R.layout.fragment_main_layout,container,false);

        int rootViewId=rootView.getId();

        ButterKnife.bind(this,rootView);
        pojoList=new ArrayList<>();

        handler=new MainActivity();
        Log.e(" handyyyy",""+handler+", "+rootViewId);
        ConnectivityManager connectivityManager= (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=connectivityManager.getActiveNetworkInfo();
        if((info!=null)&&(info.isConnected()))
        {
            getLoaderManager().initLoader(RECIPELOADER_ID,null,this);

        }
        adapterRecipe=new AdapterRecipe(getContext(),new ArrayList<RecipePojo>(),this);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterRecipe);
        adapterRecipe.notifyDataSetChanged();

        return rootView;
    }

    @NonNull
    @Override
    public Loader<List<RecipePojo>> onCreateLoader(int id, Bundle args) {

        RecipeLoader recipeLoader=new RecipeLoader(getContext(),req);
        Log.e("Loader create====",""+req);
        return recipeLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<RecipePojo>> loader, List<RecipePojo> data) {

        pojoList=data;
        if(data==null){
            empty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            empty.setText(getResources().getText(R.string.textView8));
        }
        if(data!=null)
      {
          adapterRecipe=new AdapterRecipe(getContext(),data,this);
          recyclerView.setHasFixedSize(true);
          recyclerView.setAdapter(adapterRecipe);
          Log.e("data====",""+data);

      }
      adapterRecipe.notifyDataSetChanged();
    }
    @Override
    public void onLoaderReset(@NonNull Loader<List<RecipePojo>> loader) {
        adapterRecipe.notifyDataSetChanged();
    }
    @Override
    public void mclick(int pos,List<RecipePojo> recipePojoList) {
      ((TaskHandler)getActivity()).mCallBack(pos,recipePojoList);
      Log.e("@@@@@",""+recipePojoList);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("pojoList", (ArrayList<? extends Parcelable>) pojoList);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        RecipePojo pojo=new RecipePojo();
    }

    @Override
    public void onResume() {
        super.onResume();
        RecipePojo pojo=new RecipePojo();

        handler= (TaskHandler) getContext();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        handler=null;
    }


}

