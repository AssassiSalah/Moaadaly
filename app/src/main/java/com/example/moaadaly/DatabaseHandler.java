package com.example.moaadaly;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ModulesDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table and columns for Module_Only_Exame
    private static final String TABLE_MODULE = "Module";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ID_SAVE = "id_save";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_COUF = "couf";
    private static final String COLUMN_CRED = "cred";
    private static final String COLUMN_EXAM_NOTE = "exam_note";
    private static final String COLUMN_TD_EXIST = "td_exist";
    private static final String COLUMN_TP_EXIST = "tp_exist";

    // Additional columns for Module_With_TD_TP
    private static final String COLUMN_TD_NOTE = "td_note";
    private static final String COLUMN_TD_PERCENT = "td_percent";
    private static final String COLUMN_TP_NOTE = "tp_note";
    private static final String COLUMN_TP_PERCENT = "tp_percent";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_MODULE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ID_SAVE + " INTEGER, " // Make this column unique
                + COLUMN_NAME + " TEXT, "
                + COLUMN_COUF + " INTEGER, "
                + COLUMN_CRED + " INTEGER, "
                + COLUMN_EXAM_NOTE + " REAL, "
                + COLUMN_TD_EXIST + " INTEGER, "
                + COLUMN_TP_EXIST + " INTEGER, "
                + COLUMN_TD_NOTE + " REAL, "
                + COLUMN_TD_PERCENT + " REAL, "
                + COLUMN_TP_NOTE + " REAL, "
                + COLUMN_TP_PERCENT + " REAL"
                +")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MODULE);
        onCreate(db);
    }

    public boolean moduleExists(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MODULE + " WHERE " + COLUMN_NAME + "=?", new String[]{name});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }


    // Insert a Module_Only_Exame
    public void addModule(Module_Only_Exame module, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        //onUpgrade(db,0, 0);
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_SAVE, id);//TODO for now
        values.put(COLUMN_NAME, module.getName_Module());
        values.put(COLUMN_COUF, module.getCouf());
        values.put(COLUMN_CRED, module.getCred());
        values.put(COLUMN_EXAM_NOTE, module.getExam_Note());
        values.put(COLUMN_TD_EXIST, module.isTD_Exist() ? 1 : 0);
        values.put(COLUMN_TP_EXIST, module.isTP_Exist() ? 1 : 0);
        db.insert(TABLE_MODULE, null, values);
        db.close();
    }

    // Insert a Module_With_TD_TP
    public void addModuleWithTDTP(Module_With_TD_TP module, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_SAVE, id);//TODO for now
        values.put(COLUMN_NAME, module.getName_Module());
        values.put(COLUMN_COUF, module.getCouf());
        values.put(COLUMN_CRED, module.getCred());
        values.put(COLUMN_EXAM_NOTE, module.getExam_Note());
        values.put(COLUMN_TD_EXIST, module.isTD_Exist() ? 1 : 0);
        values.put(COLUMN_TP_EXIST, module.isTP_Exist() ? 1 : 0);
        values.put(COLUMN_TD_NOTE, module.getTD_Note());
        values.put(COLUMN_TD_PERCENT, module.getTD_Percent());
        values.put(COLUMN_TP_NOTE, module.getTP_Note());
        values.put(COLUMN_TP_PERCENT, module.getTP_Percent());
        db.insert(TABLE_MODULE, null, values);
        db.close();
    }

    // Insert a Module_With_TD
    public void addModuleWithTD(Module_With_TD module, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_SAVE, id);//TODO for now
        values.put(COLUMN_NAME, module.getName_Module());
        values.put(COLUMN_COUF, module.getCouf());
        values.put(COLUMN_CRED, module.getCred());
        values.put(COLUMN_EXAM_NOTE, module.getExam_Note());
        values.put(COLUMN_TD_EXIST, module.isTD_Exist() ? 1 : 0);
        values.put(COLUMN_TP_EXIST, module.isTP_Exist() ? 1 : 0);
        values.put(COLUMN_TD_NOTE, module.getTD_Note());
        values.put(COLUMN_TD_PERCENT, module.getTD_Percent());
        db.insert(TABLE_MODULE, null, values);
        db.close();
    }

    // Insert a Module_With_TP
    public void addModuleWithTP(Module_With_TP module, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_SAVE, id);//TODO for now
        values.put(COLUMN_NAME, module.getName_Module());
        values.put(COLUMN_COUF, module.getCouf());
        values.put(COLUMN_CRED, module.getCred());
        values.put(COLUMN_EXAM_NOTE, module.getExam_Note());
        values.put(COLUMN_TD_EXIST, module.isTD_Exist() ? 1 : 0);
        values.put(COLUMN_TP_EXIST, module.isTP_Exist() ? 1 : 0);
        values.put(COLUMN_TP_NOTE, module.getTP_Note());
        values.put(COLUMN_TP_PERCENT, module.getTP_Percent());
        db.insert(TABLE_MODULE, null, values);
        db.close();
    }

    // Fetch all modules
    public ArrayList<Module_Only_Exame> getAllModules() {
        ArrayList<Module_Only_Exame> moduleList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to fetch all modules
        String selectQuery = "SELECT * FROM " + TABLE_MODULE;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            // Fetch column indexes
            int nameIndex = cursor.getColumnIndexOrThrow(COLUMN_NAME);
            int coufIndex = cursor.getColumnIndexOrThrow(COLUMN_COUF);
            int credIndex = cursor.getColumnIndexOrThrow(COLUMN_CRED);
            int examNoteIndex = cursor.getColumnIndexOrThrow(COLUMN_EXAM_NOTE);
            int tdExistIndex = cursor.getColumnIndexOrThrow(COLUMN_TD_EXIST);
            int tpExistIndex = cursor.getColumnIndexOrThrow(COLUMN_TP_EXIST);

            int tdNoteIndex = cursor.getColumnIndexOrThrow(COLUMN_TD_NOTE);
            int tdPercentIndex = cursor.getColumnIndexOrThrow(COLUMN_TD_PERCENT);
            int tpNoteIndex = cursor.getColumnIndexOrThrow(COLUMN_TP_NOTE);
            int tpPercentIndex = cursor.getColumnIndexOrThrow(COLUMN_TP_PERCENT);
            do {
                try {

                    // Fetch common fields for Module_Only_Exame
                    String name = cursor.getString(nameIndex);
                    int couf = cursor.getInt(coufIndex);
                    int cred = cursor.getInt(credIndex);
                    float examNote = cursor.getFloat(examNoteIndex);
                    boolean tdExist = cursor.getInt(tdExistIndex) == 1;
                    boolean tpExist = cursor.getInt(tpExistIndex) == 1;

                    // Check if it’s a Module_With_TD_TP
                    if (tdExist && tpExist) {
                        float tdNote = cursor.getFloat(tdNoteIndex);
                        float tdPercent = cursor.getFloat(tdPercentIndex);
                        float tpNote = cursor.getFloat(tpNoteIndex);
                        float tpPercent = cursor.getFloat(tpPercentIndex);

                        // Create a Module_With_TD_TP object
                        Module_With_TD_TP moduleWithTDTP = new Module_With_TD_TP(name, couf, cred, tdPercent, tpPercent);
                        moduleWithTDTP.setEXAM_Note(examNote);
                        moduleWithTDTP.setTD_Note(tdNote);
                        moduleWithTDTP.setTP_Note(tpNote);

                        // Add to the list
                        moduleList.add(moduleWithTDTP);
                    } else {
                        if (tdExist) {
                            float tdNote = cursor.getFloat(tdNoteIndex);
                            float tdPercent = cursor.getFloat(tdPercentIndex);

                            // Create a Module_With_TD object
                            Module_With_TD moduleWithTD = new Module_With_TD(name, couf, cred, tdPercent);
                            moduleWithTD.setEXAM_Note(examNote);
                            moduleWithTD.setTD_Note(tdNote);

                            // Add to the list
                            moduleList.add(moduleWithTD);
                        } else {
                            if (tpExist) {
                                float tpNote = cursor.getFloat(tpNoteIndex);
                                float tpPercent = cursor.getFloat(tpPercentIndex);

                                // Create a Module_With_TP object
                                Module_With_TP moduleWithTP = new Module_With_TP(name, couf, cred, tpPercent);
                                moduleWithTP.setEXAM_Note(examNote);
                                moduleWithTP.setTP_Note(tpNote);

                                // Add to the list
                                moduleList.add(moduleWithTP);
                            } else {
                                // Create a Module_Only_Exame object
                                Module_Only_Exame module = new Module_Only_Exame(name, couf, cred);
                                module.setEXAM_Note(examNote);

                                // Add to the list
                                moduleList.add(module);
                            }
                        }
                    }
                } catch (IllegalArgumentException e) {
                    Log.e("Database", "Column not found: " + e.getMessage());
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return moduleList;
    }

    public void updateModuleByName(Module_Only_Exame module, int idSave) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ID_SAVE, idSave);//TODO for now
        values.put(COLUMN_NAME, module.getName_Module());
        values.put(COLUMN_COUF, module.getCouf());
        values.put(COLUMN_CRED, module.getCred());
        values.put(COLUMN_EXAM_NOTE, module.getExam_Note());
        values.put(COLUMN_TD_EXIST, module.isTD_Exist() ? 1 : 0);
        values.put(COLUMN_TP_EXIST, module.isTP_Exist() ? 1 : 0);

        // If the module is an instance of Module_With_TD_TP, add TD and TP specific values
        if (module instanceof Module_With_TD_TP) {
            Module_With_TD_TP moduleWithTDTP = (Module_With_TD_TP) module;
            values.put(COLUMN_TD_NOTE, moduleWithTDTP.getTD_Note());
            values.put(COLUMN_TD_PERCENT, moduleWithTDTP.getTD_Percent());
            values.put(COLUMN_TP_NOTE, moduleWithTDTP.getTP_Note());
            values.put(COLUMN_TP_PERCENT, moduleWithTDTP.getTP_Percent());
        } else {
            // For Module_Only_Exame, clear TD and TP related columns
            values.put(COLUMN_TD_NOTE, (Float) null);
            values.put(COLUMN_TD_PERCENT, (Float) null);
            values.put(COLUMN_TP_NOTE, (Float) null);
            values.put(COLUMN_TP_PERCENT, (Float) null);
        }

        // Update the database
        db.update(TABLE_MODULE, values, COLUMN_NAME + "=?", new String[]{module.getName_Module()});
        db.close();
    }

    // Delete a module by ID
    public void deleteModule(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MODULE, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void reCreateTable() {
        dropTable();
        createTableIfNotExists();
    }

    public void dropTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MODULE);
        db.close();
    }

    public void createTableIfNotExists() {
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_MODULE + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ID_SAVE + " INTEGER, " // Ensuring COLUMN_ID_SAVE is unique
                + COLUMN_NAME + " TEXT, "
                + COLUMN_COUF + " INTEGER, "
                + COLUMN_CRED + " INTEGER, "
                + COLUMN_EXAM_NOTE + " REAL, "
                + COLUMN_TD_EXIST + " INTEGER, "
                + COLUMN_TP_EXIST + " INTEGER, "
                + COLUMN_TD_NOTE + " REAL, "
                + COLUMN_TD_PERCENT + " REAL, "
                + COLUMN_TP_NOTE + " REAL, "
                + COLUMN_TP_PERCENT + " REAL"
                + ")";
        db.execSQL(CREATE_TABLE);
        db.close();
    }
}

