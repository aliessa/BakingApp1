package com.newproject.aliessa.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newproject.aliessa.bakingapp.R;
import com.newproject.aliessa.bakingapp.adapter.IngredientRecycle;
import com.newproject.aliessa.bakingapp.adapter.StepsRecycle;
import com.newproject.aliessa.bakingapp.model.Step;

import java.util.ArrayList;

/**
 * Created by Ali Essa on 5/29/2017.
 */

public class StepsActivityFragment extends Fragment implements StepsRecycle.ListItemClickListener {


    private RecyclerView stepsRecyclerView;
    private RecyclerView ingredientRecyclerView;
    private StepsRecycle stepsRecycle;
    private IngredientRecycle ingredientRecycle;
    View view;
    private int mNumberOFitem = 0;
    public static ArrayList<Step> steps = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.steps_activity_fragment,container,false);
        stepsRecyclerView = (RecyclerView) view.findViewById(R.id.stepslistRV) ;
        ingredientRecyclerView = (RecyclerView) view.findViewById(R.id.ingredientsRV) ;
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mNumberOFitem = getActivity().getIntent().getExtras().getInt(getString(R.string.extra));
        steps = MainActivityFragment.bakingDatas.get(mNumberOFitem).getSteps();

        stepsRecycle = new StepsRecycle(this, steps);
        ingredientRecycle = new IngredientRecycle(MainActivityFragment.bakingDatas.get(mNumberOFitem).getIngredients());
        stepsRecyclerView.setAdapter(stepsRecycle);
        ingredientRecyclerView.setAdapter(ingredientRecycle);
        return view;

        }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        if (!MainActivity.tablet_on) {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra("item", clickedItemIndex);
            startActivity(intent);
        } else {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            DetailsActivityFragment stepsDetailsFragment = new DetailsActivityFragment();
            stepsDetailsFragment.index = clickedItemIndex;
            fragmentManager.beginTransaction()
                    .replace(R.id.steps_details_layout, stepsDetailsFragment)
                    .commit();
        }
    }
}
