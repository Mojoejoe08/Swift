package com.example.swift;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Point3;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PreviewImage extends AppCompatActivity {
    private static String LOGTAG = "OpenCV_Log";
    Mat mFromFile;
    Mat mGray;
    Mat mBlurred;
    Mat mEdged;
    Mat mDilated;
    Mat mEroded;
    Mat kernel;
    Mat hierarchy, binary;
    int section_id, student_id, exam_id;
    String section_name, student_name;
    ArrayList<String> myArray;
    ArrayList<String> myArray2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_image);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Preview Image");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006A38")));
        }
        myArray = new ArrayList<>();
        myArray2 = new ArrayList<>();
        section_id = getIntent().getIntExtra("Section_ID", 0);
        section_name = getIntent().getStringExtra("Section_Name");
        student_id = getIntent().getIntExtra("Student_ID", 0);
        student_name = getIntent().getStringExtra("Student_Name");
        exam_id = getIntent().getIntExtra("Exam_ID", 0);

        if(OpenCVLoader.initDebug()){
            Log.wtf(LOGTAG, "OpenCV initialized");
        }
        ImageView image = findViewById(R.id.imageView);

        //get file path uri from intent
        String absolute_path = getIntent().getStringExtra("file_path");

        //get file and set to Mat type
        mFromFile = Imgcodecs.imread(absolute_path);

        //declare empty Mat with dimensions similar to image
        mGray = new Mat();
        mBlurred = new Mat();
        mEdged = new Mat();
        mDilated = new Mat();
        mEroded = new Mat();
        kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size((2*2) + 1, (2*2)+1));
        hierarchy = new Mat();
        binary = new Mat();
        //convert srcimage Mat to gray
        Imgproc.cvtColor(mFromFile, mGray, Imgproc.COLOR_RGB2GRAY);
        //apply blur to reduce noise
        Imgproc.GaussianBlur(mGray,mBlurred, new Size(5,5),1);
        //Imgproc.threshold(mBlurred, mEdged, 181, 255, Imgproc.THRESH_BINARY);
        Imgproc.threshold(mBlurred, binary, 0, 255, Imgproc.THRESH_BINARY+Imgproc.THRESH_OTSU);
        //Imgproc.dilate(mEdged, mDilated, kernel, new Point(-1,-1), 1);
        //Imgproc.erode(mDilated, mEroded, kernel, new Point(-1,-1), 1);
        //apply canny to detect edges
        Imgproc.Canny(binary, mEdged, 50, 100);
        //apply dilate and erode to further reduce noise
 //       Imgproc.dilate(mEdged, mDilated, kernel, new Point(-1,-1), 1);
//        Imgproc.erode(mDilated, mEroded, kernel, new Point(-1,-1), 1);
        //Imgproc.threshold(mEroded, binary, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
        Mat circles = new Mat();
       /* Imgproc.HoughCircles(mEroded, circles, Imgproc.HOUGH_GRADIENT, 1,
                mEroded.rows()/8, // minimum distance between the centers of the detected circles
                100, // higher threshold of the Canny edge detector
                100, // accumulator threshold for circle detection
                10, 50 // minimum and maximum radius of the circles
        );

        for (int i = 0; i < circles.cols(); i++) {
            double[] circle = circles.get(0, i);
            Point center = new Point(Math.round(circle[0]), Math.round(circle[1]));
            int radius = (int) Math.round(circle[2]);
            Imgproc.circle(mFromFile, center, radius, new Scalar(0, 0, 255), 2);
        }
        */
        //Imgproc.adaptiveThreshold(mEroded, binary, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 11, 4);

        Imgproc.HoughCircles(mEdged, circles, Imgproc.HOUGH_GRADIENT, 1, 20, 250, 30, 5, 40);

        ArrayList<Point3> circlesList = new ArrayList<>();
        for (int i = 0; i < circles.cols(); i++) {
            double[] circleData = circles.get(0, i);
            Point3 circle = new Point3(circleData[0], circleData[1], circleData[2]);
            circlesList.add(circle);
        }

        Collections.sort(circlesList, new Comparator<Point3>() {
            @Override
            public int compare(Point3 p1, Point3 p2) {
                return Double.compare(p1.y, p2.y);
            }
        });
        ArrayList<ArrayList<Point3>> circlesByRow = new ArrayList<ArrayList<Point3>>();
        double rowTolerance = 10.0; // Adjust as needed
        ArrayList<Point3> currentRow = new ArrayList<Point3>();
        double currentRowY = circlesList.get(0).y;
        for (Point3 circle : circlesList) {
            if (Math.abs(circle.y - currentRowY) <= rowTolerance) {
                currentRow.add(circle);
            } else {
                circlesByRow.add(currentRow);
                currentRow = new ArrayList<Point3>();
                currentRow.add(circle);
                currentRowY = circle.y;
            }
        }
        circlesByRow.add(currentRow);

        /*double rowTolerance = 15;
        Collections.sort(circlesList, new Comparator<Point3>() {
            public int compare(Point3 p1, Point3 p2) {
                if (Math.abs(p1.y - p2.y) <= rowTolerance) {
                    return Double.compare(p1.x, p2.x);
                } else {
                    return Double.compare(p1.y, p2.y);
                }
            }
        });*/

        // Sort circles in each row by x-coordinate (column)
        for (ArrayList<Point3> row : circlesByRow) {
            Collections.sort(row, new Comparator<Point3>() {
                @Override
                public int compare(Point3 p1, Point3 p2) {
                    return Double.compare(p1.x, p2.x);
                }
            });
        }

        myArray = new ArrayList<>();
        myArray2 = new ArrayList<>();
        int row_count = 0;
        int column_count = 0;
        for (ArrayList<Point3> row : circlesByRow) {
            // Loop through each circle in the row
            column_count = 0;
            for (Point3 circle : row) {
                // Perform some operation on the circle
                //System.out.println("Circle at (" + circle.x + ", " + circle.y + ")");
                Point center = new Point(Math.round(circle. x), Math.round(circle.y));
                int radius = (int) Math.round(circle.z);
                Rect roi = new Rect((int) (center.x - radius), (int) (center.y - radius), (int) (radius * 2), (int) (radius * 2));
                Mat mask = new Mat(binary.size(), CvType.CV_8UC1, new Scalar(0));
                Imgproc.circle(mask, center, radius, new Scalar(255), -1);
                double mean = Core.mean(binary.submat(roi), mask.submat(roi)).val[0];
                String answer = "";
                String answer2 = "";
                if (mean < 80) {
                    // The circle is shaded, draw it in blue
                    if(column_count == 0){
                        answer = "A";
                    } else if (column_count == 1) {
                        answer = "B";
                    } else if (column_count == 2) {
                        answer = "C";
                    } else if (column_count == 3) {
                        answer = "D";
                    } else if (column_count == 4){
                        answer2 = "A";
                    } else if (column_count == 5){
                        answer2 = "B";
                    } else if (column_count == 6){
                        answer2 = "C";
                    } else if (column_count == 7){
                        answer2 = "D";
                    }
                    Imgproc.circle(mFromFile, center, radius, new Scalar(0, 255, 0), 2);
                    if(column_count <= 3){
                        myArray.add(answer);
                        Imgproc.putText(mFromFile, answer, new Point((center.x - 7), (center.y + 7)), Imgproc.FONT_HERSHEY_SIMPLEX, 1, new Scalar(255, 255, 255));
                    }else{
                        myArray2.add(answer2);
                        Imgproc.putText(mFromFile, answer2, new Point((center.x - 7), (center.y + 7)), Imgproc.FONT_HERSHEY_SIMPLEX, 1, new Scalar(255, 255, 255));
                    }

                } else {
                    // The circle is not shaded, draw it in green
                    Imgproc.circle(mFromFile, center, radius, new Scalar(255, 0, 0), 2);
                    //Imgproc.putText(mFromFile, , new Point((center.x - 5), (center.y + 5)), Imgproc.FONT_HERSHEY_SIMPLEX, 1, new Scalar(255, 255, 255));
                }
                column_count++;
            }
            row_count++;
        }

        /*for (int i = 0; i < circlesList.size(); i++) {
            Point3 circle = circlesList.get(i);
            Point center = new Point(Math.round(circle. x), Math.round(circle.y));
            int radius = (int) Math.round(circle.z);
            Rect roi = new Rect((int) (center.x - radius), (int) (center.y - radius), (int) (radius * 2), (int) (radius * 2));
            Mat mask = new Mat(binary.size(), CvType.CV_8UC1, new Scalar(0));
            Imgproc.circle(mask, center, radius, new Scalar(255), -1);
            double mean = Core.mean(binary.submat(roi), mask.submat(roi)).val[0];

            if (mean < 80) {
                // The circle is shaded, draw it in blue
                Imgproc.circle(mFromFile, center, radius, new Scalar(0, 255, 0), 2);
                Imgproc.putText(mFromFile, String.valueOf(i), center, Imgproc.FONT_HERSHEY_SIMPLEX, 0.5, new Scalar(255, 255, 255));
            } else {
                // The circle is not shaded, draw it in green
                Imgproc.circle(mFromFile, center, radius, new Scalar(255, 0, 0), 2);
                Imgproc.putText(mFromFile, String.valueOf(i), center, Imgproc.FONT_HERSHEY_SIMPLEX, 0.5, new Scalar(255, 255, 255));
            }
        }*/

        Toast.makeText(PreviewImage.this, String.valueOf(circlesByRow.size()), Toast.LENGTH_LONG).show();
/*
        Scalar shadedColor = new Scalar(255, 0, 0);
        Scalar unshadedColor = new Scalar(0, 255, 0);
        for (int i = 0; i < circlesList.size(); i++) {
            Point3 circle = circlesList.get(i);
            Point center = new Point(Math.round(circle. x), Math.round(circle.y));
            int radius = (int) Math.round(circle.z);
            Rect roi = new Rect((int) (center.x - radius), (int) (center.y - radius), (int) (radius * 2), (int) (radius * 2));
            Mat mask = new Mat(binary.size(), CvType.CV_8UC1, new Scalar(0));
            Imgproc.circle(mask, center, radius, new Scalar(255), -1);
            double mean = Core.mean(binary.submat(roi), mask.submat(roi)).val[0];

            if (mean < 80) {
                // The circle is shaded, draw it in blue
                Imgproc.circle(mFromFile, center, radius, new Scalar(0, 255, 0), 2);
            } else {
                // The circle is not shaded, draw it in green
                Imgproc.circle(mFromFile, center, radius, new Scalar(255, 0, 0), 2);
            }
        }*/


        /*for (int x = 0; x < circles.cols(); x++) {
            //double[] c = circles.get(0, x);
            //Point center = new Point(Math.round(c[0]), Math.round(c[1]));
            // circle center
            //Imgproc.circle(mFromFile, center, 1, new Scalar(0,100,100), 3, 8, 0 );
            // circle outline
            //int radius = (int) Math.round(c[2]);
            //Imgproc.circle(mFromFile, center, radius, new Scalar(255,0,255), 3, 8, 0 );
            double[] circle = circles.get(0, x);
            Point center = new Point(Math.round(circle[0]), Math.round(circle[1]));
            int radius = (int) Math.round(circle[2]);

            // Check if the circle is shaded or not
            Rect roi = new Rect((int) (center.x - radius), (int) (center.y - radius), (int) (radius * 2), (int) (radius * 2));
            Mat mask = new Mat(binary.size(), CvType.CV_8UC1, new Scalar(0));
            Imgproc.circle(mask, center, radius, new Scalar(255), -1);
            double mean = Core.mean(binary.submat(roi), mask.submat(roi)).val[0];

            if (mean < 80) {
                // The circle is shaded, draw it in blue
                Imgproc.circle(mFromFile, center, radius, new Scalar(0, 255, 0), 2);
            } else {
                // The circle is not shaded, draw it in green
                Imgproc.circle(mFromFile, center, radius, new Scalar(255, 0, 0), 2);
            }

        }*/


        /*
            This section will contain additional algorithms to further reduce noise and
            false positives from other contours that will be detected later

         */

        //declare MatofPoint list to contain all contours detected from the image
        //List<MatOfPoint> contours = new ArrayList<MatOfPoint>();

        //find contours from the image
        //Imgproc.findContours(mEroded, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        Imgcodecs.imwrite(absolute_path, mFromFile);
        Bitmap bMap = BitmapFactory.decodeFile(absolute_path);
        image.setImageBitmap(bMap);

        Button btnConfirm = findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!myArray2.isEmpty()) {
                    myArray.addAll(myArray2);
                }
                String[] myArrayAsArray = myArray.toArray(new String[myArray.size()]);
                Bundle bundle = new Bundle();
                bundle.putStringArray("answers", myArrayAsArray);
                Intent i = new Intent(PreviewImage.this, ShowResult.class);
                i.putExtra("Section_ID", section_id);
                i.putExtra("Section_Name", section_name);
                i.putExtra("Student_ID", student_id);
                i.putExtra("Student_Name", student_name);
                i.putExtra("Exam_ID", exam_id);
                i.putExtras(bundle);
                startActivity(i);
                finish();
            }
        });

    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static void sortContoursByArea(List<MatOfPoint> contours) {
        Collections.sort(contours, Comparator.comparingDouble(Imgproc::contourArea));
        Collections.reverse(contours);
    }
}