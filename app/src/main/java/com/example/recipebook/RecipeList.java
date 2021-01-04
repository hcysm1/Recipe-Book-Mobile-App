package com.example.recipebook;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

//SONIA MUBASHER
//20129528

public class RecipeList extends AppCompatActivity {

    //declaring variables
    ArrayList<String> listItem;
    ArrayList<String> sortedList;
    ArrayList<String> sortedTitle;
    ArrayAdapter adapter;
    DBHandler db;
    ListView RecipeList;
    String FinalList;
    Button sort_rating;
    Button sort_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        RecipeList = findViewById(R.id.recipe_List);
        listItem = new ArrayList<>();
        sortedList = new ArrayList<>();
        sortedTitle = new ArrayList<>();
        db = new DBHandler(this, "RecipeBook.db", null, 1);
        getData();
        if (RecipeList != null) {
            //when the user clicks on a individual recipe
            RecipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String Title = RecipeList.getItemAtPosition(position).toString();
                    char getID = Title.charAt(0);
                    int ID = getID - '0';
                    Intent RecipeIntent = new Intent(RecipeList.this, IndividualRecipe.class);
                    RecipeIntent.putExtra("RecipeID", ID);
                    startActivity(RecipeIntent);
                }
            });
        }
        sort_rating = findViewById(R.id.button_sort_rating);
        //to sort the list according to highest rating
        sort_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSorted();

            }
        });

        sort_title = findViewById(R.id.button_title);
        //to sort the list according to title
        sort_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTitleSorted();
            }
        });

    }

    //to display existing recipes
    public void getData() {
        Cursor cursor = db.list();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "List Is Empty!", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                FinalList = cursor.getString(0) + " " + cursor.getString(1);
                listItem.add(FinalList);
            }
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItem);
            RecipeList.setAdapter(adapter);
        }
    }

    //to sort according to rating
    public void getSorted() {
        Cursor cursor = db.sortedList();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "List Is Empty!", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                FinalList = cursor.getString(0) + " " + cursor.getString(1);
                sortedList.add(FinalList);
            }
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, sortedList);
            RecipeList.setAdapter(adapter);
        }
    }

    //To sort according to title
    public void getTitleSorted() {
        Cursor cursor = db.sortedTitle();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "List Is Empty!", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                FinalList = cursor.getString(0) + " " + cursor.getString(1);
                sortedTitle.add(FinalList);
            }
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, sortedTitle);
            RecipeList.setAdapter(adapter);
        }

    }


}