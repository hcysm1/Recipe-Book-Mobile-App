package com.example.recipebook;

import com.example.recipebook.provider.RecipeContract;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.content.ContentResolver;

//SONIA MUBASHER
//20129528

public class DBHandler extends SQLiteOpenHelper {

    private ContentResolver myCR; //content resolver

    //database version
    public static final int DATABASE_VERSION = 1;
    //database name
    public static final String DATABASE_NAME = "RecipeBook.db";

    //constructor
    public DBHandler(Context context, String name,
                     SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        myCR = context.getContentResolver();
    }

    // creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        //sql statement for recipe table
        String CREATE_TABLE_RECIPE = "CREATE TABLE " + RecipeContract.TABLE_RECIPES + "(" +
                RecipeContract.RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RecipeContract.RECIPE_NAME + " TEXT," +
                RecipeContract.RECIPE_INSTRUCTIONS + " TEXT," +
                RecipeContract.RECIPE_RATING + " TEXT " + ")";

        //sql statement for ingredients table
        String CREATE_TABLE_INGREDIENTS = "CREATE TABLE " + RecipeContract.TABLE_INGREDIENTS + "(" +
                RecipeContract.INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RecipeContract.INGREDIENT_NAME + " TEXT " + ")";

        //sql statement for recipe_ingredients table
        String CREATE_TABLE_RECIPE_INGREDIENTS = "CREATE TABLE " + RecipeContract.TABLE_RECIPE_INGREDIENTS + "(" +
                "recipe_id INT NOT NULL,\n" +
                "ingredient_id INT NOT NULL,\n" +
                "CONSTRAINT fk1 FOREIGN KEY (recipe_id) REFERENCES recipes (_id),\n" +
                "CONSTRAINT fk2 FOREIGN KEY (ingredient_id) REFERENCES ingredients (_id),\n" +
                "CONSTRAINT _id PRIMARY KEY (recipe_id, ingredient_id)" + ")";


        //creating tables
        db.execSQL(CREATE_TABLE_RECIPE);
        db.execSQL(CREATE_TABLE_INGREDIENTS);
        db.execSQL(CREATE_TABLE_RECIPE_INGREDIENTS);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + RecipeContract.TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + RecipeContract.TABLE_INGREDIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + RecipeContract.TABLE_RECIPE_INGREDIENTS);

        // create new tables
        onCreate(db);

    }

    //ADDING RECIPE INFO
    public void addRecipeInfo(RecipeClass recipe) {
        ContentValues values = new ContentValues();
        values.put(RecipeContract.RECIPE_NAME, recipe.getTitle());
        values.put(RecipeContract.RECIPE_INSTRUCTIONS, recipe.getInstructions());
        values.put(RecipeContract.RECIPE_RATING, recipe.getRating());
        myCR.insert(RecipeContract.RECIPE_URI, values);

    }

    //ADDING INGREDIENTS
    public void addIngredients(RecipeClass ing) {
        ContentValues values = new ContentValues();
        values.put(RecipeContract.INGREDIENT_NAME, ing.getIngredient());
        myCR.insert(RecipeContract.INGREDIENT_URI, values);


    }

    //creating association of ingredients with recipes
    public void addRecIng(RecipeClass RecIng) {
        ContentValues values = new ContentValues();
        values.put(RecipeContract.RECIPE_INGREDIENTS_RECIPE_ID, RecIng.getRecipe_id());
        values.put(RecipeContract.RECIPE_INGREDIENTS_INGREDIENT_ID, RecIng.getIngredient_id());
        myCR.insert(RecipeContract.RECIPE_INGREDIENTS_URI, values);

    }


    //UPDATING RATING
    public void setRating(RecipeClass recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + RecipeContract.TABLE_RECIPES + " SET " + RecipeContract.RECIPE_RATING + " = '" + recipe.getRating() + "' WHERE " + RecipeContract.RECIPE_ID + " = " + recipe.getRecipe_id());
    }

    //FINDING RECIPE
    public RecipeClass findRecipe(int RecipeID) {

        String[] projection = {RecipeContract.RECIPE_ID,
                RecipeContract.RECIPE_NAME, RecipeContract.RECIPE_INSTRUCTIONS, RecipeContract.RECIPE_RATING};

        String selection = " _id = \"" + RecipeID + "\"";

        Cursor cursor = myCR.query(RecipeContract.RECIPE_URI, projection, selection, null, null);
        RecipeClass Recipe = new RecipeClass();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            Recipe.setRecipe_id(Integer.parseInt(cursor.getString(0)));
            Recipe.setTitle(cursor.getString(1));
            Recipe.setInstructions(cursor.getString(2));
            Recipe.setRating(cursor.getString(3));
            cursor.close();
        } else {
            Recipe = null;
        }

        return Recipe;
    }


    //TO VIEW LIST OF ALREADY STORED RECIPES
    public Cursor list() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + RecipeContract.TABLE_RECIPES;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    //TO SORT THE LIST BY RATING
    public Cursor sortedList() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + RecipeContract.TABLE_RECIPES + " ORDER BY " + RecipeContract.RECIPE_RATING + " DESC ";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    //TO SORT THE LIST BY TITLE
    public Cursor sortedTitle() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + RecipeContract.TABLE_RECIPES + " ORDER BY " + RecipeContract.RECIPE_NAME + " ASC ";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    //TO VIEW UNIQUE INGREDIENTS
    public Cursor list_Ing() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT DISTINCT " + RecipeContract.INGREDIENT_NAME + " FROM " + RecipeContract.TABLE_INGREDIENTS;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;

    }

    //TO VIEW INGREDIENTS RELATED TO SPECIFIC RECIPE
    public Cursor specific_Ing(int ID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] recipeId = {Integer.toString(ID)};
        Cursor c;
        c = db.rawQuery("select r._id as recipe_id, r.name, ri.ingredient_id, i.ingredientname " +
                "from recipes r " +
                "join recipe_ingredients ri on (r._id = ri.recipe_id)" +
                "join ingredients i on (i._id = ri.ingredient_id ) where r._id == ?", recipeId);
        return c;


    }


}

