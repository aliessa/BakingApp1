package com.newproject.aliessa.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newproject.aliessa.bakingapp.R;
import com.newproject.aliessa.bakingapp.model.Step;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ali Essa on 5/29/2017.
 */

public class StepsRecycle extends RecyclerView.Adapter<StepsRecycle.StepsViewHolder> {
    final private ListItemClickListener mOnClickListener;
    final private ArrayList<Step> steps;


    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public StepsRecycle(ListItemClickListener listener, ArrayList<Step> stepArrayList) {
        mOnClickListener = listener;
        steps = stepArrayList;

    }

    class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView poster;
        TextView step;
        TextView servings;


        public StepsViewHolder(View itemView) {
            super(itemView);

            poster = (ImageView) itemView.findViewById(R.id.icon);
            step = (TextView) itemView.findViewById(R.id.TV_step);
            servings = (TextView) itemView.findViewById(R.id.TV_servings);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }

        void setdata(int position) {
            if (!steps.isEmpty()) {
                if (!steps.get(position).getThumbnailURL().isEmpty()) {
                    Picasso.with(itemView.getContext())
                            .load(steps.get(position).getThumbnailURL())
                            .error(R.drawable.blankimage)
                            .into(poster);
                } else {
                    poster.setImageResource(R.drawable.blankimage);
                }
                step.setText(steps.get(position).getShortDescription());
                servings.setText("" + " " + steps.get(position).getId());

            }
        }
    }


    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int listItemId = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(listItemId, parent, shouldAttachToParentImmediately);
        StepsViewHolder viewHolder = new StepsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        holder.setdata(position);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }
}
