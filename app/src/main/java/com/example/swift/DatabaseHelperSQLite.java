package com.example.swift;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseHelperSQLite extends SQLiteOpenHelper {

    public static final String COLUMN_QUESTION_ID = "QUESTION_ID";
    public static final String QUESTIONS_TABLE = "QUESTIONS_TABLE";
    public static final String COLUMN_QUESTION_TEXT = "QUESTION_TEXT";
    public static final String COLUMN_OPTION_ONE = "OPTION_ONE";
    public static final String COLUMN_OPTION_TWO = "OPTION_TWO";
    public static final String COLUMN_OPTION_THREE = "OPTION_THREE";
    public static final String COLUMN_OPTION_FOUR = "OPTION_FOUR";
    public static final String COLUMN_QUESTION_ANSWER = "QUESTION_ANSWER";
    public static final String SECTIONS_TABLE = "SECTIONS_TABLE";
    public static final String COLUMN_SECTION_ID = "SECTION_ID";
    public static final String COLUMN_SECTION_NAME = "SECTION_NAME";
    public static final String STUDENTS_TABLE = "STUDENTS_TABLE";
    public static final String COLUMN_STUDENT_ID = "STUDENT_ID";
    public static final String COLUMN_STUDENT_LASTNAME = "STUDENT_LASTNAME";
    public static final String COLUMN_STUDENT_FIRSTNAME = "STUDENT_FIRSTNAME";
    public static final String COLUMN_STUDENT_SECTION = "STUDENT_SECTION";
    public static final String EXAMS_TABLE = "EXAMS_TABLE";
    public static final String COLUMN_EXAM_ID = "EXAM_ID";
    public static final String COLUMN_EXAM_TITLE = "EXAM_TITLE";
    public static final String COLUMN_EXAM_SUBJECT = "EXAM_SUBJECT";
    public static final String COLUMN_EXAM_TEACHER = "EXAM_TEACHER";
    public static final String COLUMN_EXAM_SECTION = "EXAM_SECTION";
    public static final String COLUMN_EXAM_ITEM_COUNT = "EXAM_ITEM_COUNT";
    public static final String EXAM_ITEMS_TABLE = "EXAM_ITEMS_TABLE";
    public static final String COLUMN_EXAM_ITEMS_EXAM_ID = "EXAM_ITEM_EXAM_ID";
    public static final String COLUMN_EXAM_ITEMS_ITEM_ID = "EXAM_ITEM_ITEM_ID";

    public static final String EXAM_STUDENT_TABLE = "EXAM_STUDENT_TABLE";
    public static final String COLUMN_EXAM_STUDENT_ID = "EXAM_STUDENT_ID";
    public static final String COLUMN_EXAM_STUDENT_EXAM_ID = "EXAM_STUDENT_EXAM_ID";
    public static final String COLUMN_EXAM_STUDENT_STUDENT_ID = "EXAM_STUDENT_STUDENT_ID";
    public static final String COLUMN_EXAM_STUDENT_SCORE = "EXAM_STUDENT_SCORE";

    public static final String EXAM_STUDENT_QUESTION_TABLE = "EXAM_STUDENT_QUESTION";
    public static final String COLUMN_EXAM_STUDENT_QUESTION_ID = "EXAM_STUDENT_QUESTION_ID";
    public static final String COLUMN_EXAM_STUDENT_QUESTION_EXAM_STUDENT_ID = "EXAM_STUDENT_QUESTION_EXAM_STUDENT_ID";
    public static final String COLUMN_EXAM_STUDENT_QUESTION_QUESTION_ID = "EXAM_STUDENT_QUESTION_QUESTION_ID";
    public static final String COLUMN_EXAM_STUDENT_QUESTION_CORRECT = "EXAM_STUDENT_QUESTION_CORRECT";

    public DatabaseHelperSQLite(@Nullable Context context) {
        super(context, "swift_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createQuestions = "CREATE TABLE IF NOT EXISTS " + QUESTIONS_TABLE + " (" + COLUMN_QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_QUESTION_TEXT + " TEXT, "
                + COLUMN_OPTION_ONE + " TEXT, "
                + COLUMN_OPTION_TWO + " TEXT, "
                + COLUMN_OPTION_THREE + " TEXT, "
                + COLUMN_OPTION_FOUR + " TEXT, "
                + COLUMN_QUESTION_ANSWER + " TEXT)";

        String createSection = "CREATE TABLE IF NOT EXISTS " + SECTIONS_TABLE + " (" + COLUMN_SECTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_SECTION_NAME + " TEXT)";

        String createStudent = "CREATE TABLE IF NOT EXISTS " + STUDENTS_TABLE + " (" + COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_STUDENT_LASTNAME + " TEXT, "
                + COLUMN_STUDENT_FIRSTNAME + " TEXT, "
                + COLUMN_STUDENT_SECTION + " INTEGER)";

        String createExam = "CREATE TABLE IF NOT EXISTS " + EXAMS_TABLE + " (" + COLUMN_EXAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EXAM_TITLE + " TEXT, "
                + COLUMN_EXAM_TEACHER + " TEXT, "
                + COLUMN_EXAM_SUBJECT + " TEXT, "
                + COLUMN_EXAM_SECTION + " INTEGER, "
                + COLUMN_EXAM_ITEM_COUNT + " INTEGER)";

        String createExamItems = "CREATE TABLE IF NOT EXISTS " + EXAM_ITEMS_TABLE + " (" + COLUMN_EXAM_ITEMS_EXAM_ID + " INTEGER , "
                + COLUMN_EXAM_ITEMS_ITEM_ID + " INTEGER)";

        String createExamStudent = "CREATE TABLE IF NOT EXISTS " + EXAM_STUDENT_TABLE + " (" + COLUMN_EXAM_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EXAM_STUDENT_EXAM_ID + " INTEGER, "
                + COLUMN_EXAM_STUDENT_STUDENT_ID + " INTEGER, "
                + COLUMN_EXAM_STUDENT_SCORE + " INTEGER)";

        String createExamStudentQuestion = "CREATE TABLE IF NOT EXISTS " + EXAM_STUDENT_QUESTION_TABLE + " (" + COLUMN_EXAM_STUDENT_QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EXAM_STUDENT_QUESTION_EXAM_STUDENT_ID + " INTEGER, "
                + COLUMN_EXAM_STUDENT_QUESTION_QUESTION_ID + " INTEGER, "
                + COLUMN_EXAM_STUDENT_QUESTION_CORRECT + " TEXT)";

        sqLiteDatabase.execSQL(createQuestions);
        sqLiteDatabase.execSQL(createSection);
        sqLiteDatabase.execSQL(createStudent);
        sqLiteDatabase.execSQL(createExam);
        sqLiteDatabase.execSQL(createExamItems);
        sqLiteDatabase.execSQL(createExamStudent);
        sqLiteDatabase.execSQL(createExamStudentQuestion);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addItem(QuestionModel questionModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_QUESTION_TEXT, questionModel.getQuestion());
        cv.put(COLUMN_OPTION_ONE, questionModel.getOption_1());
        cv.put(COLUMN_OPTION_TWO, questionModel.getOption_2());
        cv.put(COLUMN_OPTION_THREE, questionModel.getOption_3());
        cv.put(COLUMN_OPTION_FOUR, questionModel.getOption_4());
        cv.put(COLUMN_QUESTION_ANSWER, questionModel.getAnswer());

        long inserted = db.insert(QUESTIONS_TABLE, null, cv);
        db.close();
        if(inserted == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean deleteItem(QuestionModel questionModel){

        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + QUESTIONS_TABLE + " WHERE " + COLUMN_QUESTION_ID + " = " + questionModel.getQuestion_id();
        Cursor cursor = db.rawQuery(deleteQuery, null);
        if(cursor.moveToFirst()){
            cursor.close();
            db.close();
            return true;
        }else{
            cursor.close();
            db.close();
            return false;
        }
    }

    public boolean hasResult(int student_id, int exam_id){
        String queryString = "SELECT * FROM " + EXAM_STUDENT_TABLE + " WHERE " + COLUMN_EXAM_STUDENT_EXAM_ID + " = " + exam_id + " AND "
                + COLUMN_EXAM_STUDENT_STUDENT_ID  + " = " + student_id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            db.close();
            return false;
        }
        cursor.close();
        db.close();
        return true;
    }

    public int getScore(int student_id, int exam_id){
        int score = 1;
        String queryString = "SELECT " + COLUMN_EXAM_STUDENT_SCORE + " FROM " + EXAM_STUDENT_TABLE + " WHERE " + COLUMN_EXAM_STUDENT_EXAM_ID + " = " + exam_id + " AND "
                + COLUMN_EXAM_STUDENT_STUDENT_ID  + " = " + student_id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            score = cursor.getInt(0);
        }
        return score;
    }

    public ArrayList<QuestionModel> getAllQuestions(String filterString){
        ArrayList<QuestionModel> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + QUESTIONS_TABLE + " WHERE " + COLUMN_QUESTION_TEXT + " LIKE '%" + filterString + "%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                int questionID = cursor.getInt(0);
                String question = cursor.getString(1);
                String option_a = cursor.getString(2);
                String option_b = cursor.getString(3);
                String option_c = cursor.getString(4);
                String option_d = cursor.getString(5);
                String answer = cursor.getString(6);
                QuestionModel newQuestion = new QuestionModel(questionID, question, option_a, option_b, option_c, option_d, answer);
                returnList.add(newQuestion);
            }while(cursor.moveToNext());
        }else{

        }

        cursor.close();
        db.close();
        return returnList;
    }

    public ArrayList<MeritOrderModel> getMeritOrder (int examID){
        ArrayList<MeritOrderModel> returnList = new ArrayList<>();
        String queryString = "SELECT " + COLUMN_EXAM_STUDENT_ID + ", "
                + COLUMN_STUDENT_FIRSTNAME + ", "
                + COLUMN_STUDENT_LASTNAME
                + ", " + COLUMN_EXAM_STUDENT_SCORE
                + " FROM " + EXAM_STUDENT_TABLE + " INNER JOIN " + STUDENTS_TABLE + " ON "
                + EXAM_STUDENT_TABLE + "." + COLUMN_EXAM_STUDENT_STUDENT_ID + " = " + STUDENTS_TABLE + "." + COLUMN_STUDENT_ID
                + " WHERE " + COLUMN_EXAM_STUDENT_EXAM_ID + " = " + examID + " ORDER BY " + COLUMN_EXAM_STUDENT_SCORE + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                int studentID = cursor.getInt(0);
                String studentName = cursor.getString(2) + ", " + cursor.getString(1);
                int score = cursor.getInt(3);
                MeritOrderModel newStudent = new MeritOrderModel(studentName, studentID, score);
                returnList.add(newStudent);
            }while(cursor.moveToNext());
        }else{

        }

        cursor.close();
        db.close();
        return returnList;
    }

    public ArrayList<MeritOrderModel> getHighOrder (int examID){
        ArrayList<MeritOrderModel> returnList = new ArrayList<>();
        String queryString = "SELECT COUNT(" + COLUMN_EXAM_STUDENT_ID + ") "
            + " FROM " + EXAM_STUDENT_TABLE + " INNER JOIN " + STUDENTS_TABLE + " ON "
                + EXAM_STUDENT_TABLE + "." + COLUMN_EXAM_STUDENT_STUDENT_ID + " = " + STUDENTS_TABLE + "." + COLUMN_STUDENT_ID
                + " WHERE " + COLUMN_EXAM_STUDENT_EXAM_ID + " = " + examID + " ORDER BY " + COLUMN_EXAM_STUDENT_SCORE + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToFirst();
        double high_order_count = cursor.getInt(0) * 0.27;
        int high_count = (int) Math.round(high_order_count);
        cursor.close();
        db.close();

        queryString = "SELECT " + COLUMN_EXAM_STUDENT_ID + ", "
                + COLUMN_STUDENT_FIRSTNAME + ", "
                + COLUMN_STUDENT_LASTNAME
                + ", " + COLUMN_EXAM_STUDENT_SCORE
                + " FROM " + EXAM_STUDENT_TABLE + " INNER JOIN " + STUDENTS_TABLE + " ON "
                + EXAM_STUDENT_TABLE + "." + COLUMN_EXAM_STUDENT_STUDENT_ID + " = " + STUDENTS_TABLE + "." + COLUMN_STUDENT_ID
                + " WHERE " + COLUMN_EXAM_STUDENT_EXAM_ID + " = " + examID + " ORDER BY " + COLUMN_EXAM_STUDENT_SCORE + " DESC LIMIT " + high_count;

        db = this.getReadableDatabase();
        cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                int studentID = cursor.getInt(0);
                String studentName = cursor.getString(2) + ", " + cursor.getString(1);
                int score = cursor.getInt(3);
                MeritOrderModel newStudent = new MeritOrderModel(studentName, studentID, score);
                returnList.add(newStudent);
            }while(cursor.moveToNext());
        }else{

        }

        cursor.close();
        db.close();
        return returnList;
    }

    public ArrayList<MeritOrderModel> getLowOrder (int examID){
        ArrayList<MeritOrderModel> returnList = new ArrayList<>();
        String queryString = "SELECT COUNT(" + COLUMN_EXAM_STUDENT_ID + ") "
                + " FROM " + EXAM_STUDENT_TABLE + " INNER JOIN " + STUDENTS_TABLE + " ON "
                + EXAM_STUDENT_TABLE + "." + COLUMN_EXAM_STUDENT_STUDENT_ID + " = " + STUDENTS_TABLE + "." + COLUMN_STUDENT_ID
                + " WHERE " + COLUMN_EXAM_STUDENT_EXAM_ID + " = " + examID + " ORDER BY " + COLUMN_EXAM_STUDENT_SCORE + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToFirst();
        double high_order_count = cursor.getInt(0) * 0.27;
        int high_count = (int) Math.round(high_order_count);
        cursor.close();
        db.close();

        queryString = "SELECT " + COLUMN_EXAM_STUDENT_ID + ", "
                + COLUMN_STUDENT_FIRSTNAME + ", "
                + COLUMN_STUDENT_LASTNAME
                + ", " + COLUMN_EXAM_STUDENT_SCORE
                + " FROM " + EXAM_STUDENT_TABLE + " INNER JOIN " + STUDENTS_TABLE + " ON "
                + EXAM_STUDENT_TABLE + "." + COLUMN_EXAM_STUDENT_STUDENT_ID + " = " + STUDENTS_TABLE + "." + COLUMN_STUDENT_ID
                + " WHERE " + COLUMN_EXAM_STUDENT_EXAM_ID + " = " + examID + " ORDER BY " + COLUMN_EXAM_STUDENT_SCORE + " ASC LIMIT " + high_count;

        db = this.getReadableDatabase();
        cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                int studentID = cursor.getInt(0);
                String studentName = cursor.getString(2) + ", " + cursor.getString(1);
                int score = cursor.getInt(3);
                MeritOrderModel newStudent = new MeritOrderModel(studentName, studentID, score);
                returnList.add(newStudent);
            }while(cursor.moveToNext());
        }else{

        }

        cursor.close();
        db.close();
        return returnList;
    }

    public ArrayList<ItemAnalysisModel> getDifficultyIndex (int examID){
        ArrayList<ItemAnalysisModel> returnList = new ArrayList<>();

        String queryString = "SELECT COUNT(" + COLUMN_EXAM_STUDENT_ID + ") "
                + " FROM " + EXAM_STUDENT_TABLE + " INNER JOIN " + STUDENTS_TABLE + " ON "
                + EXAM_STUDENT_TABLE + "." + COLUMN_EXAM_STUDENT_STUDENT_ID + " = " + STUDENTS_TABLE + "." + COLUMN_STUDENT_ID
                + " WHERE " + COLUMN_EXAM_STUDENT_EXAM_ID + " = " + examID + " ORDER BY " + COLUMN_EXAM_STUDENT_SCORE + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToFirst();
        double high_order_count = cursor.getInt(0) * 0.27;

        int high_count = (int) Math.round(high_order_count);
        int total_n = high_count * 2;

        queryString = "SELECT " + COLUMN_QUESTION_ID
                + " FROM " + EXAM_ITEMS_TABLE + " INNER JOIN " + QUESTIONS_TABLE
                + " ON " + EXAM_ITEMS_TABLE + "." + COLUMN_EXAM_ITEMS_ITEM_ID + " = " + QUESTIONS_TABLE + "." + COLUMN_QUESTION_ID
                + " WHERE " + COLUMN_EXAM_ITEMS_EXAM_ID + " = " + examID;
        cursor = db.rawQuery(queryString, null);


        queryString = "SELECT " + COLUMN_STUDENT_ID
                + " FROM " + EXAM_STUDENT_TABLE + " INNER JOIN " + STUDENTS_TABLE + " ON "
                + EXAM_STUDENT_TABLE + "." + COLUMN_EXAM_STUDENT_STUDENT_ID + " = " + STUDENTS_TABLE + "." + COLUMN_STUDENT_ID
                + " WHERE " + COLUMN_EXAM_STUDENT_EXAM_ID + " = " + examID + " ORDER BY " + COLUMN_EXAM_STUDENT_SCORE + " DESC LIMIT " + high_count;
        Cursor cursor2 = db.rawQuery(queryString, null);
        ArrayList<Integer> highList = new ArrayList<>();
        if(cursor2.moveToFirst()){
            do{
                int studentID = cursor2.getInt(0);
                highList.add(studentID);
            }while(cursor2.moveToNext());
        }
        int[] values = highList.stream().mapToInt(Integer::intValue).toArray();
        String inHighOrder = Arrays.toString(values).replace("[", "").replace("]", "");

        queryString = "SELECT " + COLUMN_STUDENT_ID
                + " FROM " + EXAM_STUDENT_TABLE + " INNER JOIN " + STUDENTS_TABLE + " ON "
                + EXAM_STUDENT_TABLE + "." + COLUMN_EXAM_STUDENT_STUDENT_ID + " = " + STUDENTS_TABLE + "." + COLUMN_STUDENT_ID
                + " WHERE " + COLUMN_EXAM_STUDENT_EXAM_ID + " = " + examID + " ORDER BY " + COLUMN_EXAM_STUDENT_SCORE + " ASC LIMIT " + high_count;
        Cursor cursor3 = db.rawQuery(queryString, null);

        ArrayList<Integer> lowList = new ArrayList<>();
        if(cursor3.moveToFirst()){
            do{
                int studentID = cursor3.getInt(0);
                lowList.add(studentID);
            }while(cursor3.moveToNext());
        }

        values = lowList.stream().mapToInt(Integer::intValue).toArray();
        String inLowOrder = Arrays.toString(values).replace("[", "").replace("]", "");

        if(cursor.moveToFirst()){
            do{
                int questionID = cursor.getInt(0);
                String correct = "YES";
                queryString = "SELECT COUNT(" + COLUMN_EXAM_STUDENT_ID + ")"
                        + " FROM " + EXAM_STUDENT_TABLE + " INNER JOIN " + EXAM_STUDENT_QUESTION_TABLE + " ON "
                        + EXAM_STUDENT_TABLE + "." + COLUMN_EXAM_STUDENT_ID + " = " + EXAM_STUDENT_QUESTION_TABLE + "." + COLUMN_EXAM_STUDENT_QUESTION_EXAM_STUDENT_ID
                        + " WHERE " + COLUMN_EXAM_STUDENT_EXAM_ID + " = " + examID + " AND " + COLUMN_EXAM_STUDENT_QUESTION_QUESTION_ID + " = " + questionID
                        + " AND " + COLUMN_EXAM_STUDENT_STUDENT_ID + " IN (" + inHighOrder + ") AND " + COLUMN_EXAM_STUDENT_QUESTION_CORRECT + " = '" + correct + "'";
                cursor2 = db.rawQuery(queryString, null);
                cursor2.moveToFirst();
                int highCountCorrect = cursor2.getInt(0);


                queryString = "SELECT COUNT(" + COLUMN_EXAM_STUDENT_ID + ")"
                        + " FROM " + EXAM_STUDENT_TABLE + " INNER JOIN " + EXAM_STUDENT_QUESTION_TABLE + " ON "
                        + EXAM_STUDENT_TABLE + "." + COLUMN_EXAM_STUDENT_ID + " = " + EXAM_STUDENT_QUESTION_TABLE + "." + COLUMN_EXAM_STUDENT_QUESTION_EXAM_STUDENT_ID
                        + " WHERE " + COLUMN_EXAM_STUDENT_EXAM_ID + " = " + examID + " AND " + COLUMN_EXAM_STUDENT_QUESTION_QUESTION_ID + " = " + questionID
                        + " AND " + COLUMN_EXAM_STUDENT_STUDENT_ID + " IN (" + inLowOrder + ") AND " + COLUMN_EXAM_STUDENT_QUESTION_CORRECT + " = '" + correct + "'";
                cursor3 = db.rawQuery(queryString, null);
                cursor3.moveToFirst();
                int lowCountCorrect = cursor3.getInt(0);

                float difficulty_index = ((highCountCorrect + lowCountCorrect) / ((float)total_n)) * 100;
                String formatted_difficulty_index = String.format("%.2f", difficulty_index);
                String remarks = "";
                if(difficulty_index <= 29f){
                    remarks = "Difficult";
                }else if (difficulty_index<=49f){
                    remarks = "Acceptable";
                }else if (difficulty_index<=60f){
                    remarks = "Good (Recommended)";
                }else if (difficulty_index<=70f){
                    remarks = "Acceptable";
                }else{
                    remarks = "Easy";
                }

                ItemAnalysisModel newAnalysis = new ItemAnalysisModel(1, formatted_difficulty_index, remarks);
                returnList.add(newAnalysis);
            }while(cursor.moveToNext());
        }else{

        }

        cursor.close();
        cursor2.close();
        cursor3.close();
        db.close();
        return returnList;
    }


    public ArrayList<ItemAnalysisModel> getDiscriminationIndex (int examID){
        ArrayList<ItemAnalysisModel> returnList = new ArrayList<>();

        String queryString = "SELECT COUNT(" + COLUMN_EXAM_STUDENT_ID + ") "
                + " FROM " + EXAM_STUDENT_TABLE + " INNER JOIN " + STUDENTS_TABLE + " ON "
                + EXAM_STUDENT_TABLE + "." + COLUMN_EXAM_STUDENT_STUDENT_ID + " = " + STUDENTS_TABLE + "." + COLUMN_STUDENT_ID
                + " WHERE " + COLUMN_EXAM_STUDENT_EXAM_ID + " = " + examID + " ORDER BY " + COLUMN_EXAM_STUDENT_SCORE + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToFirst();
        double high_order_count = cursor.getInt(0) * 0.27;

        int high_count = (int) Math.round(high_order_count);
        int total_n = high_count * 2;

        queryString = "SELECT " + COLUMN_QUESTION_ID
                + " FROM " + EXAM_ITEMS_TABLE + " INNER JOIN " + QUESTIONS_TABLE
                + " ON " + EXAM_ITEMS_TABLE + "." + COLUMN_EXAM_ITEMS_ITEM_ID + " = " + QUESTIONS_TABLE + "." + COLUMN_QUESTION_ID
                + " WHERE " + COLUMN_EXAM_ITEMS_EXAM_ID + " = " + examID;
        cursor = db.rawQuery(queryString, null);


        queryString = "SELECT " + COLUMN_STUDENT_ID
                + " FROM " + EXAM_STUDENT_TABLE + " INNER JOIN " + STUDENTS_TABLE + " ON "
                + EXAM_STUDENT_TABLE + "." + COLUMN_EXAM_STUDENT_STUDENT_ID + " = " + STUDENTS_TABLE + "." + COLUMN_STUDENT_ID
                + " WHERE " + COLUMN_EXAM_STUDENT_EXAM_ID + " = " + examID + " ORDER BY " + COLUMN_EXAM_STUDENT_SCORE + " DESC LIMIT " + high_count;
        Cursor cursor2 = db.rawQuery(queryString, null);
        ArrayList<Integer> highList = new ArrayList<>();
        if(cursor2.moveToFirst()){
            do{
                int studentID = cursor2.getInt(0);
                highList.add(studentID);
            }while(cursor2.moveToNext());
        }
        int[] values = highList.stream().mapToInt(Integer::intValue).toArray();
        String inHighOrder = Arrays.toString(values).replace("[", "").replace("]", "");

        queryString = "SELECT " + COLUMN_STUDENT_ID
                + " FROM " + EXAM_STUDENT_TABLE + " INNER JOIN " + STUDENTS_TABLE + " ON "
                + EXAM_STUDENT_TABLE + "." + COLUMN_EXAM_STUDENT_STUDENT_ID + " = " + STUDENTS_TABLE + "." + COLUMN_STUDENT_ID
                + " WHERE " + COLUMN_EXAM_STUDENT_EXAM_ID + " = " + examID + " ORDER BY " + COLUMN_EXAM_STUDENT_SCORE + " ASC LIMIT " + high_count;
        Cursor cursor3 = db.rawQuery(queryString, null);

        ArrayList<Integer> lowList = new ArrayList<>();
        if(cursor3.moveToFirst()){
            do{
                int studentID = cursor3.getInt(0);
                lowList.add(studentID);
            }while(cursor3.moveToNext());
        }

        values = lowList.stream().mapToInt(Integer::intValue).toArray();
        String inLowOrder = Arrays.toString(values).replace("[", "").replace("]", "");

        if(cursor.moveToFirst()){
            do{
                int questionID = cursor.getInt(0);
                String correct = "YES";
                queryString = "SELECT COUNT(" + COLUMN_EXAM_STUDENT_ID + ")"
                        + " FROM " + EXAM_STUDENT_TABLE + " INNER JOIN " + EXAM_STUDENT_QUESTION_TABLE + " ON "
                        + EXAM_STUDENT_TABLE + "." + COLUMN_EXAM_STUDENT_ID + " = " + EXAM_STUDENT_QUESTION_TABLE + "." + COLUMN_EXAM_STUDENT_QUESTION_EXAM_STUDENT_ID
                        + " WHERE " + COLUMN_EXAM_STUDENT_EXAM_ID + " = " + examID + " AND " + COLUMN_EXAM_STUDENT_QUESTION_QUESTION_ID + " = " + questionID
                        + " AND " + COLUMN_EXAM_STUDENT_STUDENT_ID + " IN (" + inHighOrder + ") AND " + COLUMN_EXAM_STUDENT_QUESTION_CORRECT + " = '" + correct + "'";
                cursor2 = db.rawQuery(queryString, null);
                cursor2.moveToFirst();
                int highCountCorrect = cursor2.getInt(0);


                queryString = "SELECT COUNT(" + COLUMN_EXAM_STUDENT_ID + ")"
                        + " FROM " + EXAM_STUDENT_TABLE + " INNER JOIN " + EXAM_STUDENT_QUESTION_TABLE + " ON "
                        + EXAM_STUDENT_TABLE + "." + COLUMN_EXAM_STUDENT_ID + " = " + EXAM_STUDENT_QUESTION_TABLE + "." + COLUMN_EXAM_STUDENT_QUESTION_EXAM_STUDENT_ID
                        + " WHERE " + COLUMN_EXAM_STUDENT_EXAM_ID + " = " + examID + " AND " + COLUMN_EXAM_STUDENT_QUESTION_QUESTION_ID + " = " + questionID
                        + " AND " + COLUMN_EXAM_STUDENT_STUDENT_ID + " IN (" + inLowOrder + ") AND " + COLUMN_EXAM_STUDENT_QUESTION_CORRECT + " = '" + correct + "'";
                cursor3 = db.rawQuery(queryString, null);
                cursor3.moveToFirst();
                int lowCountCorrect = cursor3.getInt(0);

                float discrimination_index = (2* (highCountCorrect - lowCountCorrect)) / ((float) total_n);
                String formatted_discrimination_index = String.format("%.2f", discrimination_index);
                String remarks = "";
                if(discrimination_index <= 0.14){
                    remarks = "Poor Question";
                }else if (discrimination_index<=0.24){
                    remarks = "Good Question";
                }else if (discrimination_index<=0.34){
                    remarks = "Marginal Question";
                }else{
                    remarks = "Excellent Question";
                }

                ItemAnalysisModel newAnalysis = new ItemAnalysisModel(1, formatted_discrimination_index, remarks);
                returnList.add(newAnalysis);
            }while(cursor.moveToNext());
        }else{

        }

        cursor.close();
        cursor2.close();
        cursor3.close();
        db.close();
        return returnList;
    }

    public ArrayList<SectionModel> getAllSections(String filterString){
        ArrayList<SectionModel> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + SECTIONS_TABLE + " WHERE " + COLUMN_SECTION_NAME + " LIKE '%" + filterString + "%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                int sectionID = cursor.getInt(0);
                String sectionName = cursor.getString(1);
                SectionModel newSection = new SectionModel(sectionID, sectionName);
                returnList.add(newSection);
            }while(cursor.moveToNext());
        }else{

        }

        cursor.close();
        db.close();
        return returnList;
    }

    public boolean addSection(SectionModel sectionModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_SECTION_NAME, sectionModel.getSection_name());

        long inserted = db.insert(SECTIONS_TABLE, null, cv);
        db.close();
        if(inserted == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean addStudent(StudentModel studentModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_STUDENT_FIRSTNAME, studentModel.getStudent_firstname());
        cv.put(COLUMN_STUDENT_LASTNAME, studentModel.getStudent_lastname());
        cv.put(COLUMN_STUDENT_SECTION, studentModel.getSection_id());

        long inserted = db.insert(STUDENTS_TABLE, null, cv);
        db.close();
        if(inserted == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean addExam (ArrayList<QuestionModel> questionModels, int questionCount, String teacherName, String examTitle, String subjectName, int sectionID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_EXAM_TITLE, examTitle);
        cv.put(COLUMN_EXAM_TEACHER, teacherName);
        cv.put(COLUMN_EXAM_SUBJECT, subjectName);
        cv.put(COLUMN_EXAM_SECTION, sectionID);
        cv.put(COLUMN_EXAM_ITEM_COUNT, questionCount);

        long inserted = db.insert(EXAMS_TABLE, null, cv);
        if(inserted == -1){
            return false;
        }else{
            db.close();
            db = this.getReadableDatabase();
            String selectQuery = "SELECT  * FROM " + EXAMS_TABLE + " ORDER BY " + COLUMN_EXAM_ID + " DESC LIMIT 1";
            Cursor cursor = db.rawQuery(selectQuery, null);
            cursor.moveToLast();
            int examID = cursor.getInt(0);
            db.close();
            db = this.getWritableDatabase();
            for(int i = 0; i < questionCount; i++){
                cv = new ContentValues();
                cv.put(COLUMN_EXAM_ITEMS_EXAM_ID, examID);
                cv.put(COLUMN_EXAM_ITEMS_ITEM_ID, questionModels.get(i).getQuestion_id());
                db.insert(EXAM_ITEMS_TABLE, null, cv);
            }
            cursor.close();
            db.close();
            return true;
        }
    }

    public boolean addExamResult (ArrayList<ExamStudentQuestionModel> examStudentQuestionModels, ExamStudentModel examStudentModel ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_EXAM_STUDENT_EXAM_ID, examStudentModel.getExam_id());
        cv.put(COLUMN_EXAM_STUDENT_STUDENT_ID,examStudentModel.getStudent_id());
        cv.put(COLUMN_EXAM_STUDENT_SCORE, examStudentModel.getScore());

        long inserted = db.insert(EXAM_STUDENT_TABLE, null, cv);
        if(inserted == -1){
            return false;
        }else {
            db.close();
            db = this.getReadableDatabase();
            String selectQuery = "SELECT  * FROM " + EXAM_STUDENT_TABLE + " ORDER BY " + COLUMN_EXAM_STUDENT_ID + " DESC LIMIT 1";
            Cursor cursor = db.rawQuery(selectQuery, null);
            cursor.moveToLast();
            int examStudentID = cursor.getInt(0);
            db.close();
            db = this.getWritableDatabase();
            for (int i = 0; i < examStudentQuestionModels.size(); i++) {
                cv = new ContentValues();
                cv.put(COLUMN_EXAM_STUDENT_QUESTION_EXAM_STUDENT_ID, examStudentID);
                cv.put(COLUMN_EXAM_STUDENT_QUESTION_QUESTION_ID, examStudentQuestionModels.get(i).getQuestion_id());
                cv.put(COLUMN_EXAM_STUDENT_QUESTION_CORRECT, examStudentQuestionModels.get(i).getCorrect());
                db.insert(EXAM_STUDENT_QUESTION_TABLE, null, cv);
            }
            cursor.close();
            db.close();
            return true;
        }
    }

    public ArrayList<StudentModel> getAllStudents(int filterString){
        ArrayList<StudentModel> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + STUDENTS_TABLE + " WHERE " + COLUMN_STUDENT_SECTION + " = " + filterString;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                int studentID = cursor.getInt(0);
                String studentLastName = cursor.getString(1);
                String studentFirstName = cursor.getString(2);
                int studentSection = cursor.getInt(3);
                StudentModel newStudent = new StudentModel(studentID, studentSection, studentLastName, studentFirstName);
                returnList.add(newStudent);
            }while(cursor.moveToNext());
        }else{

        }

        cursor.close();
        db.close();
        return returnList;
    }

    public ArrayList<ExamModel> getAllExam(int filterString){
        ArrayList<ExamModel> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + EXAMS_TABLE + " WHERE " + COLUMN_EXAM_SECTION + " = " + filterString;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                int examID = cursor.getInt(0);
                String examTitle = cursor.getString(1);
                String examTeacher = cursor.getString(2);
                String examSubject = cursor.getString(3);
                int examSection = cursor.getInt(4);
                int examCount = cursor.getInt(5);
                ExamModel newExam = new ExamModel(examID, examTitle, examTeacher, examSubject, examCount, examSection);
                returnList.add(newExam);
            }while(cursor.moveToNext());
        }else{

        }

        cursor.close();
        db.close();
        return returnList;
    }

    public ArrayList<QuestionModel> getAllExamQuestion(int filterString){

        ArrayList<QuestionModel> returnList = new ArrayList<>();
        String queryString = "SELECT " + COLUMN_QUESTION_ID + ", "+ COLUMN_QUESTION_TEXT + ", " + COLUMN_OPTION_ONE + ", "
                + COLUMN_OPTION_TWO + ", " + COLUMN_OPTION_THREE + ", " + COLUMN_OPTION_FOUR  + ", " + COLUMN_QUESTION_ANSWER + " FROM " + EXAM_ITEMS_TABLE + " INNER JOIN "
                + QUESTIONS_TABLE + " ON " + EXAM_ITEMS_TABLE + "." + COLUMN_EXAM_ITEMS_ITEM_ID + " = " + QUESTIONS_TABLE + "." + COLUMN_QUESTION_ID
                + " WHERE " + COLUMN_EXAM_ITEMS_EXAM_ID + " = " + filterString;
        Log.wtf("Query", queryString);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                int questionID = cursor.getInt(0);
                String question = cursor.getString(1);
                String option_a = cursor.getString(2);
                String option_b = cursor.getString(3);
                String option_c = cursor.getString(4);
                String option_d = cursor.getString(5);
                String answer = cursor.getString(6);
                QuestionModel newQuestion = new QuestionModel(questionID, question, option_a, option_b, option_c, option_d, answer);
                returnList.add(newQuestion);
            }while(cursor.moveToNext());
        }else{

        }
        cursor.close();
        db.close();
        return returnList;
    }

    public int getExamCount(int filterString){

        int examCount = 0;
        String queryString = "SELECT " + COLUMN_EXAM_ITEM_COUNT + " FROM " + EXAMS_TABLE + " WHERE " + COLUMN_EXAM_ID + " = " + filterString;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                examCount = cursor.getInt(0);
            }while(cursor.moveToNext());
        }else{

        }
        cursor.close();
        db.close();
        return examCount;
    }

    public String getExamTitle(int filterString){

        String examTitle = "";
        String queryString = "SELECT " + COLUMN_EXAM_TITLE + " FROM " + EXAMS_TABLE + " WHERE " + COLUMN_EXAM_ID + " = " + filterString;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                examTitle = cursor.getString(0);
            }while(cursor.moveToNext());
        }else{

        }
        cursor.close();
        db.close();
        return examTitle;
    }
}
