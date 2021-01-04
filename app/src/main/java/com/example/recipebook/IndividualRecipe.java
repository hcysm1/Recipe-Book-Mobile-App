package com.example.recipebook;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

//SONIA MUBASHER
//20129528

public class IndividualRecipe extends AppCompatActivity {

    //Declaring Variables
    DBHandler db;
    int ID;
    String Rating;
    TextView RecipeTitleView;
    TextView InstructionsView;
    RecipeClass Recipe;
    RatingBar ratingBar;
    ArrayList<String> ingredients;
    ArrayAdapter adapter;
    ListView list_ingredients;
    String FinalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_recipe);
        ratingBar = findViewById(R.id.ratingBar); //rating bar
        RecipeTitleView = findViewById(R.id.textViewTitle); //to display title
        ID = getIntent().getIntExtra("RecipeID", 0); //getting id of recipe from recipe list activity
        InstructionsView = findViewById(R.id.textViewInstructions); //to display instructions
        db = new DBHandler(this, "RecipeBook.db", null, 1); //database
        list_ingredients = findViewById(R.id.list_ing); //to display list of ingredients
        ingredients = new ArrayList<>(); //array of ingredients

        getIngredients();
        Recipe = new RecipeClass();
        Recipe = db.findRecipe(ID); //find recipe by passing the recipe id
        RecipeTitleView.setText(Recipe.getTitle());
        InstructionsView.setText(Recipe.getInstructions());
        if (Recipe.getRating() != null) {
            Rating = Recipe.getRating(); //get rating from user
            ratingBar.setRating(Float.parseFloat(Rating)); //set the rating
        }

        //TO SET THE RATING OF AN ITEM
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Recipe.setRating(String.valueOf(rating));
                db.setRating(Recipe);
            }
        });


    }

    //getting list of ingredients
    public void getIngredients() {

        Cursor cursor = db.specific_Ing(ID); //getting ingredients for specific recipe by passing the recipe id
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "List Is Empty!", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                FinalList = cursor.getString(3); //get the 4th column of the table
                ingredients.add(FinalList);
            }
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ingredients);
            list_ingredients.setAdapter(adapter); //set the adapter
        }
    }
}