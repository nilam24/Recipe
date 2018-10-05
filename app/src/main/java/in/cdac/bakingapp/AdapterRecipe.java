package in.cdac.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

@SuppressWarnings("ALL")
public class AdapterRecipe extends RecyclerView.Adapter<AdapterRecipe.viewHolderRecipe> {

    final List<RecipePojo> pojoList;
    RecipePojo recipePojo;
    String name;
    int count = 0;
    final ClickHandler mClickHandler;

    interface ClickHandler {

        void mclick(int index, List<RecipePojo> pojoList);
    }

    public AdapterRecipe(Context context, List<RecipePojo> pojoList, ClickHandler mClickHandler) {
        this.pojoList = pojoList;
        this.mClickHandler = mClickHandler;
    }

    @Override
    public viewHolderRecipe onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_item_layout, parent, false);

        }
        return new viewHolderRecipe(view);
    }

    @Override
    public void onBindViewHolder(final viewHolderRecipe holder, final int position) {

        recipePojo = new RecipePojo();
        recipePojo = pojoList.get(position);
        name = recipePojo.getName();
        int id = recipePojo.getId();
        holder.recipeName.setText(name);
        count++;
        Log.e(" count " + count, " " + id + " , " + name);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return pojoList.size();
    }

    class viewHolderRecipe extends RecyclerView.ViewHolder implements View.OnClickListener {

        //@BindView is commented because it gives execution exception
        final TextView recipeName;
        //@BindView(R.id.textName)TextView recipeName;

        public viewHolderRecipe(View itemView) {

            super(itemView);
            //noinspection RedundantCast
            recipeName = (TextView) itemView.findViewById(R.id.textName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            mClickHandler.mclick(pos, pojoList);
            Log.e("handler", "$$" + pojoList);
        }
    }
}


