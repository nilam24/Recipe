package in.cdac.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("ALL")
public class AdapterSteps extends RecyclerView.Adapter<AdapterSteps.RecipeViewHolder> {

    StepsPojo stepsPojo;
    final List<StepsPojo> pojoList;
    final StepClickHandler mItemClick;
    final Context context;

    interface StepClickHandler {
        void mClickSteps(int pos, List<StepsPojo> pojoList);
    }

    AdapterSteps(Context context, List<StepsPojo> pojoList, StepClickHandler mItemClick) {
        this.context = context;
        this.pojoList = pojoList;
        this.mItemClick = mItemClick;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_step_detail_layout, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterSteps.RecipeViewHolder holder, int position) {

        stepsPojo = new StepsPojo();
        stepsPojo = pojoList.get(position);
        int id = stepsPojo.getStepId();
        Log.e("", "ididid" + id);
        final String shortDes = stepsPojo.getShortDescription();
        if (shortDes != null) {
            holder.short_des.setText(shortDes);
        }
        String des = stepsPojo.getDescription();
        if (des != null) {
            holder.description.setText(des);
        }
        String thumb = stepsPojo.getThumbnailURL();

        if (thumb != null) {
            Uri uri = Uri.parse(thumb);
            Picasso.get().load(uri).into(holder.thumbnail);
            holder.thumbnail.setVisibility(View.GONE);
        }

        final String vedio = stepsPojo.getVideoURL();
        if (vedio != null) {
            holder.videoURL.setText(vedio);
        }
    }

    @Override
    public int getItemCount() {
        return pojoList.size();
    }

    @SuppressWarnings("EmptyMethod")
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @SuppressWarnings("EmptyMethod")
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.textShortDes)
        TextView short_des;
        @BindView(R.id.textDes)
        TextView description;
        @BindView(R.id.textVideoURL)
        TextView videoURL;
        @BindView(R.id.imageThumbnail)
        ImageView thumbnail;

        public RecipeViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();

            mItemClick.mClickSteps(pos, pojoList);

        }
    }
}



