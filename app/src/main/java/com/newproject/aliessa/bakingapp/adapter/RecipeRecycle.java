package com.newproject.aliessa.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newproject.aliessa.bakingapp.R;
import com.newproject.aliessa.bakingapp.model.BakingData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ali Essa on 5/8/2017
 */

public class RecipeRecycle extends RecyclerView.Adapter<RecipeRecycle.RecycleViewHolder> {
    final private ListItemClickListener mOnClickListener;
    final private ArrayList<BakingData> bakingDatas;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public RecipeRecycle(ListItemClickListener listener, ArrayList<BakingData> bakingDatas) {
        mOnClickListener = listener;
        this.bakingDatas = bakingDatas;
    }


    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int list_item_layout = R.layout.list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(list_item_layout, parent, false);
        RecycleViewHolder viewHolder = new RecycleViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecycleViewHolder holder, int position) {
        holder.setdata(position);
    }

    @Override
    public int getItemCount() {
        return bakingDatas.size();
    }

    class RecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView poster;
        TextView step;
        TextView servings;

        public RecycleViewHolder(View itemView) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.icon);
            step = (TextView) itemView.findViewById(R.id.TV_step);
            servings = (TextView) itemView.findViewById(R.id.TV_servings);

            itemView.setOnClickListener(this);
        }

        void setdata(int position) {
            if (bakingDatas != null) {
                if (bakingDatas.get(position).getImage() != null && !bakingDatas.get(position).getImage().isEmpty())
                    Picasso.with(itemView.getContext()).load(bakingDatas.get(position).getImage()).into(poster);
                step.setText(bakingDatas.get(position).getName());
                servings.setText(bakingDatas.get(position).getServings());
            }
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}

