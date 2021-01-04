package com.example.recipebook;

//SONIA MUBASHER
//20129528


//model class for Recipes
public class RecipeClass {

    //declaring variables
    private int recipe_id;
    private int ingredient_id;
    private String Ingredient;
    private String Title;
    private String Instructions;
    private String Rating;


    //constructor for ingredient
    public RecipeClass(String ingredient) {
        this.Ingredient = ingredient;
    }

    //constructor for adding title and instructions
    public RecipeClass(String title, String instructions) {
        this.Title = title;
        this.Instructions = instructions;
    }

    //constructor for recipe id and ingredient id
    public RecipeClass(int recipe_id, int ingredient_id) {
        this.recipe_id = recipe_id;
        this.ingredient_id = ingredient_id;
    }

    public RecipeClass() {
    }


    //getter and setter

    public String getIngredient() {

        return Ingredient;
    }
    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public int getIngredient_id() {
        return ingredient_id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getInstructions() {
        return Instructions;
    }

    public void setInstructions(String instructions) {
        this.Instructions = instructions;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        this.Rating = rating;
    }
}
