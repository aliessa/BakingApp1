package com.newproject.aliessa.bakingapp.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ali Essa on 5/8/2017
 */

public class BakingData {


    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;
    private String servings;
    private String image;

    public BakingData(JSONObject bake_jason) {
        try {
            this.name = bake_jason.getString("name");
            this.ingredients = new ArrayList<>();
            JSONArray ingredientsJA = bake_jason.getJSONArray("ingredients");
            for (int i = 0; i < ingredientsJA.length(); i++) {
                ingredients.add(new Ingredient(ingredientsJA.getJSONObject(i)));
            }
            this.steps = new ArrayList<>();
            JSONArray stepsJA = bake_jason.getJSONArray("steps");
            for (int i = 0; i < stepsJA.length(); i++) {
                steps.add(new Step(stepsJA.getJSONObject(i)));
            }
            this.servings = bake_jason.getString("servings");
            this.image = bake_jason.getString("image");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public String getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }
}