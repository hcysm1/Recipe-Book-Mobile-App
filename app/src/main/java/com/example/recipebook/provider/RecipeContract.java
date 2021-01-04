package com.example.recipebook.provider;

//SONIA MUBASHER
//20129528

import android.net.Uri;

public class RecipeContract {
    public static final String AUTHORITY = "com.example.recipebook.provider.MyContentProvider";

    public static final String TABLE_RECIPES = "recipes";
    public static final String TABLE_RECIPE_INGREDIENTS = "recipe_ingredients";
    public static final String TABLE_INGREDIENTS = "ingredients";

    public static final Uri RECIPE_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_RECIPES);
    public static final Uri INGREDIENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_INGREDIENTS);
    public static final Uri RECIPE_INGREDIENTS_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_RECIPE_INGREDIENTS);

    //field names
    public static final String RECIPE_ID = "_id";
    public static final String RECIPE_NAME = "name";
    public static final String RECIPE_INSTRUCTIONS = "instructions";
    public static final String RECIPE_RATING = "rating";

    public static final String INGREDIENT_ID = "_id";
    public static final String INGREDIENT_NAME = "ingredientname";

    public static final String RECIPE_INGREDIENTS_RECIPE_ID = "recipe_id";
    public static final String RECIPE_INGREDIENTS_INGREDIENT_ID = "ingredient_id";


}
