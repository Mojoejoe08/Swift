package com.example.swift;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ExamStudentList extends AppCompatActivity {
    ArrayList<StudentModel> studentModels = new ArrayList<>();
    ArrayList<QuestionModel> questionModels = new ArrayList<>();
    int questionCount;
    String examTitle;
    DatabaseHelperSQLite dbhelper;
    ExamStudentsRecyclerViewAdapter adapter;
    int exam_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Check Exam > Student List");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006A38")));
        }

        setContentView(R.layout.activity_exam_student_list);

        String section_name = getIntent().getStringExtra("Section_Name");
        int exam_id = getIntent().getIntExtra("Exam_ID", 0);
        int section_id = getIntent().getIntExtra("Section_ID", 0);

        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        //setUpQuestionModels();
        dbhelper = new DatabaseHelperSQLite(ExamStudentList.this);
        studentModels = dbhelper.getAllStudents(section_id);
        questionModels = dbhelper.getAllExamQuestion(exam_id);
        questionCount = dbhelper.getExamCount(exam_id);
        examTitle = dbhelper.getExamTitle(exam_id);
        adapter = new ExamStudentsRecyclerViewAdapter(this, studentModels, exam_id, section_name, section_id);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, PackageManager.PERMISSION_GRANTED);

        ImageView printPDF = findViewById(R.id.printQuestionnaire);
        printPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ExamStudentList.this, String.valueOf(questionCount), Toast.LENGTH_LONG).show();
                createPDF();
            }
        });

        ImageView printAnswerSheet = findViewById(R.id.printAnswerSheet);
        printAnswerSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ExamStudentList.this, String.valueOf(questionCount), Toast.LENGTH_LONG).show();
                downloadPdf(view, questionCount);
            }
        });

        Button btnItemAnalysis = findViewById(R.id.btn_item_analysis);
        btnItemAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createItemAnalysis();
            }
        });
    }

    private void createPDF(){
        PdfDocument myPDFDocument = new PdfDocument();
        Paint myPaint = new Paint();
        PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(595, 842, 1).create();;
        PdfDocument.Page myPage1 = myPDFDocument.startPage(myPageInfo1);
        Canvas canvas = myPage1.getCanvas();
        myPaint.setTextSize(9);
        int y_coords = 50;
        Log.wtf("Page 1", "SUCCESS");
        for(int ctr = 0; ctr<questionCount; ctr++){

            if(ctr % 15 == 0 && ctr != 0){
                myPDFDocument.finishPage(myPage1);
                myPageInfo1 = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
                myPage1 = myPDFDocument.startPage(myPageInfo1);
                canvas = myPage1.getCanvas();
                myPaint.setTextSize(9);
                y_coords = 50;
            }

            canvas.drawText((ctr + 1) + ".) " + questionModels.get(ctr).getQuestion(), 40, y_coords, myPaint);
            y_coords = y_coords + 15;
            canvas.drawText("a.) " + questionModels.get(ctr).getOption_1(), 50, y_coords, myPaint);
            canvas.drawText("c.) " + questionModels.get(ctr).getOption_3(), 300, y_coords, myPaint);
            y_coords = y_coords + 15;
            canvas.drawText("b.) " + questionModels.get(ctr).getOption_2(), 50, y_coords, myPaint);
            canvas.drawText("d.) " + questionModels.get(ctr).getOption_4(), 300, y_coords, myPaint);
            y_coords = y_coords + 20;


        }
        myPDFDocument.finishPage(myPage1);

        Date currentTime = Calendar.getInstance().getTime();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/" + examTitle + "-" + String.valueOf(currentTime.getTime()) +".pdf");

        try{
            myPDFDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(ExamStudentList.this, "Successfully generated questionnaire in Downloads folder", Toast.LENGTH_LONG).show();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void createItemAnalysis(){
        // Create a new PDF document
        PdfDocument document = new PdfDocument();

        // Create a new page
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        // Define the table dimensions
        int cols = 3;

        // Define the column widths
        int availableWidth = pageInfo.getPageWidth() - (2 * 72); // Subtract 2 inches of margin
        int columnWidth = availableWidth / cols;

        // Define the row heights
        int rowHeight = 15;

        // Create a new paint object for drawing text
        Paint paint = new Paint();

        // Set the font size and color
        paint.setTextSize(9);
        paint.setColor(Color.BLACK);

        // Define the table origin (top-left corner)
        int tableX = 72;
        int tableY = 72;
        page.getCanvas().drawText("ORDER OF MERIT", tableX + columnWidth, tableY, paint);
        page.getCanvas().drawLine(tableX, tableY +10, tableX + (3 * columnWidth), tableY + 10, paint);
        // Draw the column headers
        page.getCanvas().drawText("Rank", tableX, tableY + 20, paint);
        page.getCanvas().drawText("Student Name", tableX + columnWidth, tableY + 20, paint);
        page.getCanvas().drawText("Score", tableX + (2 * columnWidth), tableY + 20, paint);

        // Draw the horizontal separator line
        int separatorY = tableY + 20 + rowHeight;
        page.getCanvas().drawLine(tableX, separatorY, tableX + (3 * columnWidth), separatorY, paint);

        int exam_id = getIntent().getIntExtra("Exam_ID", 0);
        ArrayList<MeritOrderModel> meritOrderModels = dbhelper.getMeritOrder(exam_id);

        for(int ctr = 0; ctr<meritOrderModels.size(); ctr++) {
        // Draw the data rows
            page.getCanvas().drawText(String.valueOf(ctr+1), tableX, separatorY + ((ctr+1) * rowHeight), paint);
            page.getCanvas().drawText(meritOrderModels.get(ctr).getStudentName(), tableX + columnWidth, separatorY + ((ctr+1)* rowHeight), paint);
            page.getCanvas().drawText(String.valueOf(meritOrderModels.get(ctr).getScore()), tableX + (2 * columnWidth), separatorY + ((ctr+1) * rowHeight), paint);
        }
        // Finish the page and document
        document.finishPage(page);




        PdfDocument.PageInfo pageInfo2 = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
        PdfDocument.Page page2 = document.startPage(pageInfo2);

        // Define the table dimensions
        cols = 3;

        // Define the column widths
        availableWidth = pageInfo2.getPageWidth() - (2 * 72); // Subtract 2 inches of margin
       columnWidth = availableWidth / cols;

        // Define the row heights
        rowHeight = 15;

        // Create a new paint object for drawing text
        paint = new Paint();

        // Set the font size and color
        paint.setTextSize(9);
        paint.setColor(Color.BLACK);

        // Define the table origin (top-left corner)
        tableX = 72;
        tableY = 72;
        page2.getCanvas().drawText("HIGH GROUP", tableX + columnWidth, tableY, paint);
        page2.getCanvas().drawLine(tableX, tableY +10, tableX + (3 * columnWidth), tableY + 10, paint);
        // Draw the column headers
        page2.getCanvas().drawText("Rank", tableX, tableY + 20, paint);
        page2.getCanvas().drawText("Student Name", tableX + columnWidth, tableY + 20, paint);
        page2.getCanvas().drawText("Score", tableX + (2 * columnWidth), tableY + 20, paint);

        // Draw the horizontal separator line
        separatorY = tableY + 20 + rowHeight;
        page2.getCanvas().drawLine(tableX, separatorY, tableX + (3 * columnWidth), separatorY, paint);

        ArrayList<MeritOrderModel> meritHighModels = dbhelper.getHighOrder(exam_id);

        for(int ctr = 0; ctr<meritHighModels.size(); ctr++) {
        // Draw the data rows
            page2.getCanvas().drawText(String.valueOf(ctr+1), tableX, separatorY + ((ctr+1) * rowHeight), paint);
            page2.getCanvas().drawText(meritOrderModels.get(ctr).getStudentName(), tableX + columnWidth, separatorY + ((ctr+1)* rowHeight), paint);
            page2.getCanvas().drawText(String.valueOf(meritOrderModels.get(ctr).getScore()), tableX + (2 * columnWidth), separatorY + ((ctr+1) * rowHeight), paint);
        }
        // Finish the page and document
        document.finishPage(page2);


        PdfDocument.PageInfo pageInfo3 = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
        PdfDocument.Page page3 = document.startPage(pageInfo3);

        // Define the table dimensions
        cols = 3;

        // Define the column widths
        availableWidth = pageInfo3.getPageWidth() - (2 * 72); // Subtract 2 inches of margin
        columnWidth = availableWidth / cols;

        // Define the row heights
        rowHeight = 15;

        // Create a new paint object for drawing text
        paint = new Paint();

        // Set the font size and color
        paint.setTextSize(9);
        paint.setColor(Color.BLACK);

        // Define the table origin (top-left corner)
        tableX = 72;
        tableY = 72;
        page3.getCanvas().drawText("LOW GROUP", tableX + columnWidth, tableY, paint);
        page3.getCanvas().drawLine(tableX, tableY +10, tableX + (3 * columnWidth), tableY + 10, paint);
        // Draw the column headers
        page3.getCanvas().drawText("Rank", tableX, tableY + 20, paint);
        page3.getCanvas().drawText("Student Name", tableX + columnWidth, tableY + 20, paint);
        page3.getCanvas().drawText("Score", tableX + (2 * columnWidth), tableY + 20, paint);

        // Draw the horizontal separator line
        separatorY = tableY + 20 + rowHeight;
        page3.getCanvas().drawLine(tableX, separatorY, tableX + (3 * columnWidth), separatorY, paint);

        ArrayList<MeritOrderModel> meritLowModels = dbhelper.getLowOrder(exam_id);

        for(int ctr = 0; ctr<meritLowModels.size(); ctr++) {
        // Draw the data rows
            page3.getCanvas().drawText(String.valueOf(ctr+1), tableX, separatorY + ((ctr+1) * rowHeight), paint);
            page3.getCanvas().drawText(meritLowModels.get(ctr).getStudentName(), tableX + columnWidth, separatorY + ((ctr+1)* rowHeight), paint);
            page3.getCanvas().drawText(String.valueOf(meritLowModels.get(ctr).getScore()), tableX + (2 * columnWidth), separatorY + ((ctr+1) * rowHeight), paint);
        }
        // Finish the page and document
        document.finishPage(page3);


        PdfDocument.PageInfo pageInfo4 = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
        PdfDocument.Page page4 = document.startPage(pageInfo4);

        // Define the table dimensions
        cols = 3;

        // Define the column widths
        availableWidth = pageInfo4.getPageWidth() - (2 * 72); // Subtract 2 inches of margin
        columnWidth = availableWidth / cols;

        // Define the row heights
        rowHeight = 15;

        // Create a new paint object for drawing text
        paint = new Paint();

        // Set the font size and color
        paint.setTextSize(9);
        paint.setColor(Color.BLACK);

        // Define the table origin (top-left corner)
        tableX = 72;
        tableY = 72;
        page4.getCanvas().drawText("DIFFICULTY INDEX", tableX + columnWidth, tableY, paint);
        page4.getCanvas().drawLine(tableX, tableY +10, tableX + (3 * columnWidth), tableY + 10, paint);
        // Draw the column headers
        page4.getCanvas().drawText("Item No.", tableX, tableY + 20, paint);
        page4.getCanvas().drawText("Difficulty Index", tableX + columnWidth, tableY + 20, paint);
        page4.getCanvas().drawText("Remarks", tableX + (2 * columnWidth), tableY + 20, paint);

        // Draw the horizontal separator line
        separatorY = tableY + 20 + rowHeight;
        page4.getCanvas().drawLine(tableX, separatorY, tableX + (3 * columnWidth), separatorY, paint);

        ArrayList<ItemAnalysisModel> itemAnalysisModels = dbhelper.getDifficultyIndex(exam_id);

        for(int ctr = 0; ctr<itemAnalysisModels.size(); ctr++) {
        // Draw the data rows
            page4.getCanvas().drawText(String.valueOf(ctr+1), tableX, separatorY + ((ctr+1) * rowHeight), paint);
            page4.getCanvas().drawText(itemAnalysisModels.get(ctr).getValues(), tableX + columnWidth, separatorY + ((ctr+1)* rowHeight), paint);
            page4.getCanvas().drawText(itemAnalysisModels.get(ctr).getRemarks(), tableX + (2 * columnWidth), separatorY + ((ctr+1) * rowHeight), paint);
        }
        // Finish the page and document
        document.finishPage(page4);



        PdfDocument.PageInfo pageInfo5 = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
        PdfDocument.Page page5 = document.startPage(pageInfo5);

        // Define the table dimensions
        cols = 3;

        // Define the column widths
        availableWidth = pageInfo5.getPageWidth() - (2 * 72); // Subtract 2 inches of margin
        columnWidth = availableWidth / cols;

        // Define the row heights
        rowHeight = 15;

        // Create a new paint object for drawing text
        paint = new Paint();

        // Set the font size and color
        paint.setTextSize(9);
        paint.setColor(Color.BLACK);

        // Define the table origin (top-left corner)
        tableX = 72;
        tableY = 72;
        page5.getCanvas().drawText("DISCRIMINATION INDEX", tableX + columnWidth, tableY, paint);
        page5.getCanvas().drawLine(tableX, tableY +10, tableX + (3 * columnWidth), tableY + 10, paint);
        // Draw the column headers
        page5.getCanvas().drawText("Item No.", tableX, tableY + 20, paint);
        page5.getCanvas().drawText("Discrimination Index", tableX + columnWidth, tableY + 20, paint);
        page5.getCanvas().drawText("Remarks", tableX + (2 * columnWidth), tableY + 20, paint);

        // Draw the horizontal separator line
        separatorY = tableY + 20 + rowHeight;
        page5.getCanvas().drawLine(tableX, separatorY, tableX + (3 * columnWidth), separatorY, paint);

        itemAnalysisModels = dbhelper.getDiscriminationIndex(exam_id);

        for(int ctr = 0; ctr<itemAnalysisModels.size(); ctr++) {
        // Draw the data rows
            page5.getCanvas().drawText(String.valueOf(ctr+1), tableX, separatorY + ((ctr+1) * rowHeight), paint);
            page5.getCanvas().drawText(itemAnalysisModels.get(ctr).getValues(), tableX + columnWidth, separatorY + ((ctr+1)* rowHeight), paint);
            page5.getCanvas().drawText(itemAnalysisModels.get(ctr).getRemarks(), tableX + (2 * columnWidth), separatorY + ((ctr+1) * rowHeight), paint);
        }
        // Finish the page and document
        document.finishPage(page5);

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/Item Analysis - " + examTitle + ".pdf");

        try{
            document.writeTo(new FileOutputStream(file));
            Toast.makeText(ExamStudentList.this, "See analysis result in Downloads folder", Toast.LENGTH_LONG).show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void downloadPdf(View view, int questionCount) {
        AssetManager assetManager = getAssets();
        InputStream in = null;
        OutputStream out = null;

        try {
            if(questionCount == 25){
                in = assetManager.open("AnswerSheet.pdf");
            } else if (questionCount == 10) {
                in = assetManager.open("AnswerSheet10.pdf");
            } else if (questionCount == 20){
                in = assetManager.open("AnswerSheet20.pdf");
            } else if (questionCount == 50){
                in = assetManager.open("AnswerSheet50.pdf");
            }

            File outFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "AnswerSheet.pdf");
            out = new FileOutputStream(outFile);
            copyFile(in, out);
            Toast.makeText(this, "Answer sheet downloaded successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error downloading PDF", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }
}