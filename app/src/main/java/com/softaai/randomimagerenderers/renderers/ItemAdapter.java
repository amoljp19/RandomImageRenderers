package com.softaai.randomimagerenderers.renderers;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.DiffUtil;

import com.pedrogomez.renderers.AdapteeCollection;
import com.pedrogomez.renderers.RendererBuilder;
import com.softaai.randomimagerenderers.R;
import com.softaai.randomimagerenderers.model.RandomImageResponse;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import static java.util.Collections.addAll;

/**
 * Created by Amol Pawar on 18-05-2019.
 * softAai Apps
 */

public class ItemAdapter extends PagedListAdapter<RandomImageResponse, ItemAdapter.ItemViewHolder> {

    private static DiffUtil.ItemCallback<RandomImageResponse> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<RandomImageResponse>() {
                @Override
                public boolean areItemsTheSame(RandomImageResponse oldItem, RandomImageResponse newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(RandomImageResponse oldItem, RandomImageResponse newItem) {
                    return oldItem.equals(newItem);
                }
            };
    private Context mCtx;
    private RendererBuilder<RandomImageResponse> rendererBuilder;
    private AdapteeCollection<RandomImageResponse> collection;


    public ItemAdapter(Context mCtx) {
        super(DIFF_CALLBACK);
        this.mCtx = mCtx;
    }


    public ItemAdapter(RendererBuilder<RandomImageResponse> rendererBuilder, AdapteeCollection<RandomImageResponse> collection) {
        super(DIFF_CALLBACK);
        this.rendererBuilder = rendererBuilder;
        this.collection = collection;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.random_image_renderer, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        final RandomImageResponse randomImageResponse = getItem(position);
        holder.tvDate.setText("current date " + position);
        holder.tvSha256.setText("current sha256 " + position);

        Picasso.get()
                .load(randomImageResponse.getDownloadUrl())
                .placeholder(R.drawable.placeholder)
                .into(holder.ivRandomImage);


        if (!randomImageResponse.getDownloadUrl().isEmpty()) {
            Picasso.get()
                    .load(randomImageResponse.getDownloadUrl())
                    .fit()
                    .into(holder.ivRandomImage);
        }
    }




    public void clear() {
        collection.clear();
    }

    /**
     * Allows the client code to access the AdapteeCollection from subtypes of RendererAdapter.
     *
     * @return collection used in the adapter as the adaptee class.
     */
    protected AdapteeCollection<RandomImageResponse> getCollection() {
        return collection;
    }


    public void diffUpdate(List<RandomImageResponse> newList) {
        if (getCollection().size() == 0) {
            addAll(newList);
            notifyDataSetChanged();
        } else {
            DiffCallback diffCallback = new DiffCallback(collection, newList);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
            clear();
            addAll(newList);
            diffResult.dispatchUpdatesTo(this);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate, tvSha256;
        ImageView ivRandomImage;

        public ItemViewHolder(View view) {
            super(view);
            tvDate = view.findViewById(R.id.tv_date);
            ivRandomImage = view.findViewById(R.id.iv_random_image);
            tvSha256 = view.findViewById(R.id.tv_sha256);

        }
    }


}
