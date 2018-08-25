package com.udacity.sandwichclub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    public static Context detailContext;

    private ImageView imageView;
    private TextView alsoKnownLabelTv;
    private TextView alsoKnownTv;
    private TextView originLabelTv;
    private TextView originTv;
    private TextView descriptionLabelTv;
    private TextView descriptionTv;
    private TextView ingredientsLabelTv;
    private TextView ingredientsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        detailContext = getApplicationContext();

        imageView = findViewById(R.id.image_iv);
        alsoKnownLabelTv = findViewById(R.id.also_known_label_tv);
        alsoKnownTv = findViewById(R.id.also_known_tv);
        originLabelTv = findViewById(R.id.origin_label_tv);
        originTv = findViewById(R.id.origin_tv);
        descriptionLabelTv = findViewById(R.id.description_label_tv);
        descriptionTv = findViewById(R.id.description_tv);
        ingredientsLabelTv = findViewById(R.id.ingredients_label_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(imageView);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        // Set list of know names
        StringBuilder knownNamesSB = new StringBuilder();
        List<String> names = sandwich.getAlsoKnownAs();
        if (names != null && !names.isEmpty()) {
            for (String name : names) {
                knownNamesSB.append(name + "\n");
            }
            alsoKnownTv.setText(knownNamesSB.toString());
        } else {
            alsoKnownLabelTv.setVisibility(View.GONE);
            alsoKnownTv.setVisibility(View.GONE);
        }

        // Set origin
        String origin = sandwich.getPlaceOfOrigin();
        if (!origin.isEmpty()) {
            originTv.setText(sandwich.getPlaceOfOrigin());
        } else {
            originLabelTv.setVisibility(View.GONE);
            originTv.setVisibility(View.GONE);
        }

        // Set description
        String description = sandwich.getDescription();
        if (!description.isEmpty()) {
            descriptionTv.setText(sandwich.getDescription());
        } else {
            descriptionLabelTv.setVisibility(View.GONE);
            descriptionTv.setVisibility(View.GONE);
        }

        // Set list of ingredients
        StringBuilder ingredientsSB = new StringBuilder();
        List<String> ingredients = sandwich.getIngredients();
        if (ingredients != null && !ingredients.isEmpty()) {
            for (String ingredient : ingredients) {
                ingredientsSB.append(ingredient + "\n");
            }
            ingredientsTv.setText(ingredientsSB.toString());
        } else {
            ingredientsLabelTv.setVisibility(View.GONE);
            ingredientsTv.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
