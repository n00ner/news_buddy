package com.n00ner.newsbuddy;

import java.security.Key;

public class Constants {

    public static final String DATABASE_THEMES = "labels";

    /* Themes */

    public static final String TABLE_THEMES = "themes";
    public static final String COL_THEMES_ID = "theme_id";
    public static final String COL_THEMES_NAME = "theme_name";

    /* Tags */

    public static final String TABLE_TAGS = "tags";
    public static final String COL_TAGS_ID = "tag_id";
    public static final String COL_TAGS_THEME_ID = "tag_theme_id";
    public static final String COL_TAG_NAME = "tag_name";

    public static final String QUERY_CREATE_THEMES_TABLE = "CREATE TABLE if not exists " + TABLE_THEMES
            + " (" + COL_THEMES_ID + " TEXT, " + COL_THEMES_NAME + " TEXT )";

    public static final String QUERY_CREATE_TAGS_TABLE = "CREATE TABLE if not exists " + TABLE_TAGS
            + " (" + COL_TAGS_ID + " TEXT, " + COL_TAGS_THEME_ID + " TEXT, " + COL_TAG_NAME + " TEXT )";

    public static String getAllRecordsQuery(String table){
        return "SELECT  * FROM " + table;
    }

    public static String getAllRecordsQueryById(String table, String key){
        return "SELECT  * FROM " + table + " WHERE " + key + "= ?";
    }



}
