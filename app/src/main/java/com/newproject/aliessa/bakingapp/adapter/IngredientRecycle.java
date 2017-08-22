package com.newproject.aliessa.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newproject.aliessa.bakingapp.R;
import com.newproject.aliessa.bakingapp.model.Ingredient;

import java.util.ArrayList;

/**
 * Created by Ali Essa on 5/30/2017
 */

public class IngredientRecycle extends RecyclerView.Adapter<IngredientRecycle.IngredientViewHolder> {

    final private ArrayList<Ingredient> ingredients;

    public IngredientRecycle(ArrayList<Ingredient> ingredientArrayList) {
        ingredients = ingredientArrayList;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.ingredient_list_item;

        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        IngredientViewHolder viewHolder = new IngredientViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        holder.setdata(position);

    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView ingredient;
        TextView quantity;
        TextView measure;

        public IngredientViewHolder(View itemView) {
            super(itemView);

            ingredient = (TextView) itemView.findViewById(R.id.Ingredient);
            measure = (TextView) itemView.findViewById(R.id.measure);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
        }

        void setdata(int position) {
            if (!ingredients.isEmpty()) {
                ingredient.setText(ingredients.get(position).getIngredient());
                measure.setText(ingredients.get(position).getMeasure());
                quantity.setText(""+ingredients.get(position).getQuantity());
            }
        }
    }
}