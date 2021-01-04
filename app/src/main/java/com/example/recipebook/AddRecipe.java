package com.example.recipebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//SONIA MUBASHER
//20129528

public class AddRecipe extends AppCompatActivity {

    //declaring variables
    EditText Title;
    EditText Instructions;
    EditText Ingredients;
    Button Save;
    DBHandler db;
    int recId = 0; //RECIPE ID
    int ingId = 0; //INGREDIENT ID


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        Title = findViewById(R.id.title_txt);
        Instructions = findViewById(R.id.instructions_txt);
        Ingredients = findViewById(R.id.Ingredients);
        Save = findViewById(R.id.button_save);

        //database instance
        db = new DBHandler(this, "RecipeBook.db", null, 1);
        //on click listener for save button
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String RecipeName = Title.getText().toString(); //getting title from user
                String RecipeInstructions = Instructions.getText().toString(); //getting instructions from user
                RecipeClass Recipe = new RecipeClass(RecipeName, RecipeInstructions);
                db.addRecipeInfo(Recipe); //inserting to database
                recId = recId + 1; //incrementing recipe id
                Title.setText("");
                Instructions.setText("");
                String Ing = Ingredients.getText().toString(); //getting list of ingredients from user
                String[] Ing_Array = Ing.split("\\r?\\n"); //saving list of ingredients in array as multiple individual strings
                for (String Ingredient : Ing_Array) { //for each ingredient in the array of ingredients
                    RecipeClass ing = new RecipeClass(Ingredient);
                    db.addIngredients(ing); //add it
                    Ingredients.setText("");
                    ingId = ingId + 1; //incrementing ingredient id
                    RecipeClass RecIng = new RecipeClass(recId, ingId);
                    db.addRecIng(RecIng); //adding the recipe id and ingredient id to database
                }


            }
        });

    }


}