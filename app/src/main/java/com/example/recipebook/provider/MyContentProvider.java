package com.example.recipebook.provider;

import com.example.recipebook.DBHandler;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.text.TextUtils;

//SONIA MUBASHER
//20129528


public class MyContentProvider extends ContentProvider {
    private DBHandler myDB;

    public static final int RECIPES = 1;
    public static final int RECIPE_ID = 2;
    public static final int INGREDIENTS = 3;
    public static final int INGREDIENT_ID = 4;
    public static final int RecIng = 5;
    public static final int RecIng_ID = 6;


    private static final UriMatcher sURIMatcher =
            new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(RecipeContract.AUTHORITY, RecipeContract.TABLE_RECIPES, RECIPES);
        sURIMatcher.addURI(RecipeContract.AUTHORITY, RecipeContract.TABLE_RECIPES + "/#", RECIPE_ID);
        sURIMatcher.addURI(RecipeContract.AUTHORITY, RecipeContract.TABLE_INGREDIENTS, INGREDIENTS);
        sURIMatcher.addURI(RecipeContract.AUTHORITY, RecipeContract.TABLE_INGREDIENTS + "/#", INGREDIENT_ID);
        sURIMatcher.addURI(RecipeContract.AUTHORITY, RecipeContract.TABLE_RECIPE_INGREDIENTS, RecIng);
        sURIMatcher.addURI(RecipeContract.AUTHORITY, RecipeContract.TABLE_RECIPE_INGREDIENTS + "/#", RecIng_ID);
    }

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = myDB.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case RECIPES:
                id = sqlDB.insert(RecipeContract.TABLE_RECIPES,
                        null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return Uri.parse(RecipeContract.TABLE_RECIPES + "/" + id);
            case INGREDIENTS:
                id = sqlDB.insert(RecipeContract.TABLE_INGREDIENTS,
                        null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return Uri.parse(RecipeContract.TABLE_INGREDIENTS + "/" + id);
            case RecIng:
                id = sqlDB.insert(RecipeContract.TABLE_RECIPE_INGREDIENTS,
                        null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return Uri.parse(RecipeContract.TABLE_RECIPE_INGREDIENTS + "/" + id);
            default:
                throw new IllegalArgumentException("Unknown URI: "
                        + uri);
        }


    }

    @Override
    public boolean onCreate() {
        myDB = new DBHandler(getContext(), null, null, 1);
        return false;

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case RECIPE_ID:
                queryBuilder.setTables(RecipeContract.TABLE_RECIPES);
                queryBuilder.appendWhere(RecipeContract.RECIPE_ID + "="
                        + uri.getLastPathSegment());
                break;
            case RECIPES:
                queryBuilder.setTables(RecipeContract.TABLE_RECIPES);
                break;
            case INGREDIENT_ID:
                queryBuilder.setTables(RecipeContract.TABLE_INGREDIENTS);
                queryBuilder.appendWhere(RecipeContract.INGREDIENT_ID + "="
                        + uri.getLastPathSegment());
                break;
            case INGREDIENTS:
                queryBuilder.setTables(RecipeContract.TABLE_INGREDIENTS);
                break;
            case RecIng:
                queryBuilder.setTables(RecipeContract.TABLE_RECIPE_INGREDIENTS);
                break;
            case RecIng_ID:
                queryBuilder.setTables(RecipeContract.TABLE_RECIPE_INGREDIENTS);
                queryBuilder.appendWhere(RecipeContract.RECIPE_ID + "="
                        + uri.getLastPathSegment());
                break;

            default:
                throw new IllegalArgumentException("Unknown URI");
        }
        Cursor cursor = queryBuilder.query(myDB.getReadableDatabase(),
                projection, selection, selectionArgs, null, null,
                sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),
                uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = myDB.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case RECIPES:
                rowsUpdated =
                        sqlDB.update(RecipeContract.TABLE_RECIPES,
                                values,
                                selection,
                                selectionArgs);
                break;
            case RECIPE_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated =
                            sqlDB.update(RecipeContract.TABLE_RECIPES,
                                    values,
                                    RecipeContract.RECIPE_ID + "=" + id,
                                    null);
                } else {
                    rowsUpdated =
                            sqlDB.update(RecipeContract.TABLE_RECIPES,
                                    values,
                                    RECIPE_ID + "=" + id
                                            + " and "
                                            + selection,
                                    selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: "
                        + uri);

        }

        getContext().getContentResolver().notifyChange(uri,
                null);
        return rowsUpdated;

    }
}
