package in.cdac.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


@SuppressWarnings("ALL")
public class AdapterIngredientsRecipe extends RecyclerView.Adapter<AdapterIngredientsRecipe.RecipeViewHolder> {

    final listClickHandler mListClick;
    final List<IngredientsPojo>ingredientsList;

    interface listClickHandler {

        void mClick(int index,List<IngredientsPojo>list);
    }

    AdapterIngredientsRecipe(Context context, List<IngredientsPojo> ingredientsList, listClickHandler mListClick) {
        this.ingredientsList=ingredientsList;
        this.mListClick = mListClick;
        Log.e("adapter ING",""+ingredientsList);

    }
    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_fragment, parent, false);
        }

        return new RecipeViewHolder(view);
    }
    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position)throws  ClassCastException{

        int quant=0;
        RecipePojo pojo=new RecipePojo();
        IngredientsPojo pojoIng = new IngredientsPojo();
        pojoIng = ingredientsList.get(position);
        Log.e("bind",""+ pojoIng);
        quant= pojoIng.getQuantity();
        if(quant!=0) {
            holder.quant.setText(String.valueOf(pojoIng.getQuantity()));
            Log.e("bind",""+quant);
        }
        String measure= pojoIng.getMeasure();
        if(measure!=null) {
            holder.measure.setText(pojoIng.getMeasure());
        }
        String ingredient= pojoIng.getIngredientValue();
        if(ingredient!=null) {
            holder.ingredient.setText(pojoIng.getIngredientValue());
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {

        Log.e("size", "" + ingredientsList.size());
        return ingredientsList.size();
    }
    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

          @BindView(R.id.quantText)TextView quant;
          @BindView(R.id.measureText)TextView measure;
          @BindView(R.id.ingValueText)TextView ingredient;

        public RecipeViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int pos=getAdapterPosition();
            mListClick.mClick(pos,ingredientsList);

        }
    }
}
