package com.example.recipebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//SONIA MUBASHER
//20129528

public class MainActivity extends AppCompatActivity {

    //declaring variables
    Button Add; //to add recipe
    Button View_Rec; //to view recipes
    Button View_Ing; //to view ingredients

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Add = findViewById(R.id.button_Add);

        //intent for addRecipe
        final Intent AddRecipe = new Intent(MainActivity.this, AddRecipe.class);
        //on click listener for Add Button
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AddRecipe); //starting add Recipe activity
            }
        });
        View_Rec = findViewById(R.id.button_view);
        //Intent for Recipe list
        final Intent List = new Intent(MainActivity.this, RecipeList.class);
        //on click listener for view button
        View_Rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                startActivity(List); //starting List activity
            }
        });
        View_Ing = findViewById(R.id.button_ing);

        //intent for ingredient list
        final Intent List_ING = new Intent(MainActivity.this, IngredientActivity.class);
        //on click listener to view unique ingredients
        View_Ing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(List_ING);
            }
        });


    }


}