package com.newproject.aliessa.bakingapp.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newproject.aliessa.bakingapp.R;
import com.newproject.aliessa.bakingapp.adapter.RecipeRecycle;
import com.newproject.aliessa.bakingapp.model.BakingData;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.newproject.aliessa.bakingapp.R.id.main_RecipeRecycler;

/**
 * Created by Ali Essa on 5/8/2017.
 */

public class MainActivityFragment extends Fragment implements RecipeRecycle.ListItemClickListener, LoaderManager.LoaderCallbacks<ArrayList<BakingData>> {
    public final String TAG = "tage";
    public static ArrayList<BakingData> bakingDatas = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecipeRecycle recipeRecycle;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(22, null, this);

        view = inflater.inflate(R.layout.main_fragmint, container, false);
if (MainActivity.tablet_on){
    if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
        recyclerView = (RecyclerView) view.findViewById(main_RecipeRecycler);
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);
    }else {
        recyclerView = (RecyclerView) view.findViewById(main_RecipeRecycler);
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
    }
}else {
    if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
    {
        recyclerView = (RecyclerView) view.findViewById(main_RecipeRecycler);
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

    }else {
    recyclerView = (RecyclerView) view.findViewById(main_RecipeRecycler);
    RecyclerView.LayoutManager layoutManager;
    layoutManager = new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(layoutManager);
}
}

    return view;
    }


    @Override
    public Loader<ArrayList<BakingData>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<ArrayList<BakingData>>(getContext()) {
            @Override
            protected void onStartLoading() {
                forceLoad();
            }

            @Override
            public ArrayList<BakingData> loadInBackground() {
                System.out.println("ArrayList<BakingData> loadInBackground()");
                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;

                try {
                    Uri builtUri = Uri.parse("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json")
                            .buildUpon()
                            .build();

                    URL url = new URL(builtUri.toString());

                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
                    if (buffer.length() == 0) {
                        return null;
                    }
                    JSONArray bakingJArray = new JSONArray(buffer.toString());
                    bakingDatas = new ArrayList<>();
                    for (int i = 0; i < bakingJArray.length(); i++) {
                        System.out.println();
                        bakingDatas.add(new BakingData(bakingJArray.getJSONObject(i)));
                        Log.e("name: ", bakingDatas.get(i).getSteps().get(i).getVideoURL());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (urlConnection != null) {
                            urlConnection.disconnect();
                        }
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }

                return bakingDatas;

            }
        };


    }

    @Override
    public void onLoadFinished(Loader<ArrayList<BakingData>> loader, ArrayList<BakingData> data) {


        recipeRecycle = new RecipeRecycle(this, data);
        recyclerView.setAdapter(recipeRecycle);

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<BakingData>> loader) {

    }


    @Override
    public void onListItemClick(int clickedItemIndex) {

        Intent intent = new Intent(getActivity(), StepsActivity.class);
        intent.putExtra(getString(R.string.extra), clickedItemIndex);
        startActivity(intent);
    }
}
