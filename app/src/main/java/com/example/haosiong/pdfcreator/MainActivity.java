package com.example.haosiong.pdfcreator;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
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

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test.pdf");
                if(!file.exists()){
                    Toast.makeText(getApplicationContext(), " test.pdf not found!",Toast.LENGTH_LONG).show();
                }else{
                    Uri path = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".provider",file);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(path, "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                }

            }
        });
    }

    public void generate(){
        PDFWriter writer = new PDFWriter(PaperSize.A4_WIDTH, PaperSize.A4_HEIGHT);
        writer.setFont(StandardFonts.SUBTYPE, StandardFonts.TIMES_BOLD, StandardFonts.WIN_ANSI_ENCODING);

        int topMargin = 25;
        int bottomMargin = 25;
        int leftMargin = 25;
        int rightMargin = 25;

        //Header
        drawColumn(writer,2, leftMargin,PaperSize.A4_HEIGHT - topMargin - 60,400,30);
        drawColumn(writer, 2, leftMargin+400, PaperSize.A4_HEIGHT -topMargin - 60, 145, 30);
        //Header END

        //General Information
        writer.addText(leftMargin + 5, PaperSize.A4_HEIGHT - 132, 20, "General Information");
        writer.addText(385, PaperSize.A4_HEIGHT -132, 20, "Product");
        writer.addLine(leftMargin, PaperSize.A4_HEIGHT - 135, PaperSize.A4_WIDTH-rightMargin, PaperSize.A4_HEIGHT - 135);
        drawColumn(writer, 5, leftMargin, PaperSize.A4_HEIGHT -305, 200, 30 );
        drawColumn(writer, 5, leftMargin + 200, PaperSize.A4_HEIGHT - 305, 145, 30);


        //Image of car
//        AssetManager assetManager = getAssets();
////        Bitmap car = null;
//        String imageName = "";
//        File dir = new File(Environment.getExternalStorageDirectory().getPath() + "/databases/");
//        File image = new File(dir,imageName);
//        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
////            car = BitmapFactory.decodeStream(assetManager.open("car.png"));
//        int left = PaperSize.A4_WIDTH - 221;
//        int bottom = PaperSize.A4_HEIGHT - 305;
//        writer.addImage(left, bottom ,bitmap);
        //END of Image

        //List of error
        writer.addText(30, PaperSize.A4_HEIGHT - 367, 20 , "List of errors:");
        writer.addLine(25, PaperSize.A4_HEIGHT - 370, PaperSize.A4_WIDTH - rightMargin, PaperSize.A4_HEIGHT - 370);
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

        outputToFile("test.pdf",writer.asString(),"ISO-8859-1");
    }

    /**
     * This method draws cell
     * @param writer PDFwriter object
     * @param fromLeft starting horizontal coordinate X
     * @param fromBottom starting vertical  coordinate Y
     * @param width width of the cell
     * @param height height of the cell
     */
    public void drawCell(PDFWriter writer, int fromLeft, int fromBottom, int width, int height){
        writer.setFont(StandardFonts.SUBTYPE, StandardFonts.TIMES_BOLD, StandardFonts.WIN_ANSI_ENCODING);

        //Horizontal lines
        writer.addLine(fromLeft, fromBottom, fromLeft + width ,fromBottom); //bottom
        writer.addLine(fromLeft,fromBottom + height, fromLeft+width ,fromBottom + height); //top
        //Vertical lines
        writer.addLine(fromLeft , fromBottom, fromLeft,fromBottom + height); //left
        writer.addLine(fromLeft + width, fromBottom, fromLeft + width,fromBottom + height); //right
    }

    /**
     * This method draws a row of cells
     * @param writer
     * @param num number of cells
     * @param fromLeft starting horizontal coordinate X
     * @param fromBottom starting vertical  coordinate Y
     * @param width width of the cell
     * @param height height of the cell
     */
    public void drawRow(PDFWriter writer ,int num, int fromLeft, int fromBottom, int width, int height){
        int Left = fromLeft;

        for(int i=0;i<num;i++){
            drawCell(writer, Left, fromBottom, width, height);
            Left += width;
        }
    }

    /**
     * This method draws a column of cells
     * @param writer
     * @param num number of cells
     * @param fromLeft starting horizontal coordinate X
     * @param fromBottom starting vertical  coordinate Y
     * @param width width of the cell
     * @param height height of the cell
     */
    public void drawColumn(PDFWriter writer, int num, int fromLeft, int fromBottom, int width, int height){
        int Bottom = fromBottom;

        for(int i=0;i<num;i++){
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
