package com.example.recipebook;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

//SONIA MUBASHER
//20129528

public class IngredientActivity extends AppCompatActivity {

    //declaring variables
    ArrayList<String> ingredients;
    ArrayAdapter adapter;
    DBHandler db;
    ListView IngredientList;
    String FinalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        IngredientList = findViewById(R.id.list_ingredient);
        ingredients = new ArrayList<>();
        db = new DBHandler(this, "RecipeBook.db", null, 1);
        getIngredients();
    }

    public void getIngredients() {
        Cursor cursor = db.list_Ing(); //getting unique ingredients
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "List Is Empty!", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                FinalList = cursor.getString(0);
                ingredients.add(FinalList);
            }
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ingredients);
            IngredientList.setAdapter(adapter);
        }
    }
}