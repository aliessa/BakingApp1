package com.newproject.aliessa.bakingapp.widget;

/**
 * Created by Ali Essa on 6/11/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.newproject.aliessa.bakingapp.R;
import com.newproject.aliessa.bakingapp.model.BakingData;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.newproject.aliessa.bakingapp.ui.MainActivityFragment.bakingDatas;

public class WidgetRemoteViewsFactory implements RemoteViewsFactory {

    private Context mContext;

    public WidgetRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        new GetOnlinedata();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return bakingDatas.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
        try {
            rv.setImageViewBitmap(R.id.icon, BitmapFactory.decodeStream(new URL(bakingDatas.get(position).getImage()).openConnection().getInputStream()));
        } catch (IOException e) {
        }
        rv.setTextViewText(R.id.name, bakingDatas.get(position).getName());
        rv.setTextViewText(R.id.servings, mContext.getString(R.string.servings) + " " + bakingDatas.get(position).getServings());
        for (int i=0;i<bakingDatas.get(position).getIngredients().size();i++){
            RemoteViews  ing= new RemoteViews(mContext.getPackageName(), R.layout.ingredient_list_item);
            ing.setTextViewText(R.id.Ingredient,bakingDatas.get(position).getIngredients().get(i).getIngredient());
            ing.setTextViewText(R.id.measure,bakingDatas.get(position).getIngredients().get(i).getMeasure());
            ing.setTextViewText(R.id.quantity,bakingDatas.get(position).getIngredients().get(i).getQuantity()+"");
            rv.addView(R.id.ingerdient_list,ing);
        }

        Intent intent = new Intent();
        intent.putExtra("item", position);
        rv.setOnClickFillInIntent(R.id.layout_baking, intent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }



    public class GetOnlinedata implements LoaderManager.LoaderCallbacks<ArrayList<BakingData>>{

        @Override
        public Loader<ArrayList<BakingData>> onCreateLoader(int id, Bundle args) {
            return new AsyncTaskLoader<ArrayList<BakingData>>(mContext) {
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

        }

        @Override
        public void onLoaderReset(Loader<ArrayList<BakingData>> loader) {

        }
    }
}
