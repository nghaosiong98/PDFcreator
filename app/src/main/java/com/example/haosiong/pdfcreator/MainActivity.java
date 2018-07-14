package com.example.haosiong.pdfcreator;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;

import crl.android.pdfwriter.PDFWriter;
import crl.android.pdfwriter.PaperSize;
import crl.android.pdfwriter.StandardFonts;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generate();
            }
        });
    }

    public void generate(){
        PDFWriter writer = new PDFWriter(PaperSize.A4_WIDTH, PaperSize.A4_HEIGHT);
        writer.setFont(StandardFonts.SUBTYPE, StandardFonts.TIMES_BOLD, StandardFonts.WIN_ANSI_ENCODING);

        //Header
//        writer.addText(1,827,15,"MODEL :");
//        writer.addText(1,812,15,"N/A");
//        writer.addText(1,792,15,"CHASSIS NO :");
//        writer.addText(1,777,15,"N/A");
//        writer.addText(1,757,15,"SUFFIX :");
//        writer.addText(1,742,15,"N/A");
//        writer.addLine(1,726,594,727);
        //Header END

        //Image of car
//        AssetManager assetManager = getAssets();
//        Bitmap car = null;
//        try {
//            car = BitmapFactory.decodeStream(assetManager.open("car.png"));
//            int left = PaperSize.A4_WIDTH - car.getWidth() - 10;
//            int bottom = PaperSize.A4_HEIGHT - car.getHeight() - 5;
//            writer.addImage(left, bottom ,car);
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //END of Image

        //List of error
//        writer.addText(1, 710, 15, "List of errors: ");

        //Loop start here to print the errors

        //END loop

//        Bitmap diagram = null;
//        try {
//            diagram = BitmapFactory.decodeStream(assetManager.open("diagram.png"));
//            int left = 0;
//            int bottom = 302;
//            writer.addImage(left, bottom ,diagram);
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //Footer
//        writer.addLine(1,300,594,299);
//        writer.addText(1, 280, 15, "Inspection done by :");
//        writer.addText(1, 265,15, "N/A");
//        writer.addText(1,245,15,"Platform :");
//        writer.addText(1,230,15, "N/A");
//        writer.addText(1,210,15, "Time: ");
//        writer.addText(1,195,15, "N/A");
//        writer.addText(1,175,15, "Date");
//        writer.addText(1,160,15, "N/A");

        //END Footer

        drawCell(writer,100,100,200,200);
        drawColumn(writer,3,0,0,50,25);
        drawRow(writer,3,0,0,50,25);
        outputToFile("test.pdf",writer.asString(),"ISO-8859-1");
    }

    public void drawCell(PDFWriter writer, int fromLeft, int fromBottom, int width, int height){
        writer.setFont(StandardFonts.SUBTYPE, StandardFonts.TIMES_BOLD, StandardFonts.WIN_ANSI_ENCODING);

        //Horizontal lines
        writer.addLine(fromLeft, fromBottom, fromLeft + width ,fromBottom); //bottom
        writer.addLine(fromLeft,fromBottom + height, fromLeft+width ,fromBottom + height); //top
        //Vertical lines
        writer.addLine(fromLeft , fromBottom, fromLeft,fromBottom + height); //left
        writer.addLine(fromLeft + width, fromBottom, fromLeft + width,fromBottom + height); //right
    }

    public void drawRow(PDFWriter writer ,int num, int fromLeft, int fromBottom, int width, int height){
        int Left = fromLeft;

        for(int i=0;i<=num;i++){
            drawCell(writer, Left, fromBottom, width, height);
            Left += width;
        }

    }

    public void drawColumn(PDFWriter writer, int num, int fromLeft, int fromBottom, int width, int height){
        int Bottom = fromBottom;

        for(int i=0;i<=num;i++){
            drawCell(writer,fromLeft,Bottom,width,height);
            Bottom+=height;
        }
    }




    public void outputToFile(String fileName, String pdfContent, String encoding) {
        String state;
        state = Environment.getExternalStorageState();

        if(Environment.MEDIA_MOUNTED.equals(state)){
            File newFile = new File(Environment.getExternalStorageDirectory() + "/" + fileName);
            try {
                newFile.createNewFile();
                try {
                    FileOutputStream pdfFile = new FileOutputStream(newFile);
                    pdfFile.write(pdfContent.getBytes(encoding));
                    pdfFile.close();
                    Toast.makeText(getApplicationContext(),"PDF generate to " + newFile.getPath(), Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    Log.d("e: ", e.toString());
                }
            } catch (IOException e) {
                Log.d("e: ", e.toString());
            }
        }else{
            Toast.makeText(getApplicationContext(),"SD card not found!",Toast.LENGTH_LONG).show();
        }
    }
}
