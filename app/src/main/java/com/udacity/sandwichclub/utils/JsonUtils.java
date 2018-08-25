package com.udacity.sandwichclub.utils;

import android.widget.Toast;

import com.udacity.sandwichclub.DetailActivity;
import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static final String JSON_IMAGE_PARAM = "image";
    public static final String JSON_NAME_PARAM = "name";
    public static final String JSON_MAIN_NAME_PARAM = "mainName";
    public static final String JSON_ALSO_KNOWN_PARAM = "alsoKnownAs";
    public static final String JSON_PLACE_OF_ORIGIN_PARAM = "placeOfOrigin";
    public static final String JSON_DESCRIPTION_PARAM = "description";
    public static final String JSON_INGREDIENTS_PARAM = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        try {
            Sandwich sandwich = new Sandwich();
            JSONObject object = new JSONObject(json);

            // Load image
            String imagePath = object.optString(JSON_IMAGE_PARAM);
            sandwich.setImage(imagePath);

            // Load name
            JSONObject name = object.getJSONObject(JSON_NAME_PARAM);
            String sandwichName = name.optString(JSON_MAIN_NAME_PARAM);
            sandwich.setMainName(sandwichName);

            // Load known names
            JSONArray alsoKnownAs = name.getJSONArray(JSON_ALSO_KNOWN_PARAM);
            sandwich.setAlsoKnownAs(jsonToList(alsoKnownAs));

            // Load origin
            String placeOfOrigin = object.optString(JSON_PLACE_OF_ORIGIN_PARAM);
            sandwich.setPlaceOfOrigin(placeOfOrigin);

            // Load description
            String description = object.optString(JSON_DESCRIPTION_PARAM);
            sandwich.setDescription(description);

            // Load ingredients
            JSONArray ingredients = object.getJSONArray(JSON_INGREDIENTS_PARAM);
            sandwich.setIngredients(jsonToList(ingredients));

            return sandwich;

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(DetailActivity.detailContext, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private static ArrayList<String> jsonToList(JSONArray jsonArray){
        ArrayList<String> list = new ArrayList<>();

        for (int i=0; i<jsonArray.length(); i++){
            try {
                list.add(jsonArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }
}
