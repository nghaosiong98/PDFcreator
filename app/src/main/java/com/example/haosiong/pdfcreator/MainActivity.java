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
import java.util.ArrayList;

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

        //Paper Setting
        int topMargin = 25;
        int bottomMargin = 25;
        int leftMargin = 25;
        int rightMargin = 25;

        //Car information
        String ID = "na";
        String name = "na";
        String suffix = "na";
        String chassis = "na";
        String spec = "na";
        String imgPath = "na";

        //Error
        ArrayList<String> errors = new ArrayList<>();

        //Report detail
        int reportNo;
        int pageNo;

        //Header
        drawColumn(writer,2, leftMargin,PaperSize.A4_HEIGHT - topMargin - 60,400,30);
        writer.setFont(StandardFonts.SUBTYPE, StandardFonts.TIMES_BOLD, StandardFonts.WIN_ANSI_ENCODING);
        writer.addText(leftMargin + 5, PaperSize.A4_HEIGHT - topMargin - 22, 20, "Inspection Report");
        writer.addText(leftMargin + 5,PaperSize.A4_HEIGHT - topMargin - 52, 20, "Date : ");
        drawColumn(writer, 2, leftMargin+400, PaperSize.A4_HEIGHT -topMargin - 60, 145, 30);
        writer.addText(leftMargin + 405, PaperSize.A4_HEIGHT - topMargin - 22, 20, "Report No");
        writer.addText( leftMargin + 405, PaperSize.A4_HEIGHT - topMargin - 52, 20, "Page");
        writer.addText( leftMargin + 405 + 60, PaperSize.A4_HEIGHT - topMargin - 52, 15, "1 out of 1");

        //Header END

        //General Information
        writer.addText(leftMargin + 5, PaperSize.A4_HEIGHT - 132, 20, "General Information");
        writer.addText(385, PaperSize.A4_HEIGHT -132, 20, "Product");
        writer.addLine(leftMargin, PaperSize.A4_HEIGHT - 135, PaperSize.A4_WIDTH-rightMargin, PaperSize.A4_HEIGHT - 135);

        //Label
        drawColumn(writer, 5, leftMargin, PaperSize.A4_HEIGHT -305, 200, 30 );
        writer.addText(leftMargin + 5, PaperSize.A4_HEIGHT - 177, 20 ,"ID" );
        writer.addText(leftMargin + 5, PaperSize.A4_HEIGHT - 207, 20 ,"Name" );
        writer.addText(leftMargin + 5, PaperSize.A4_HEIGHT - 237, 20 ,"Suffix" );
        writer.addText(leftMargin + 5, PaperSize.A4_HEIGHT - 267, 20 ,"Chassis" );
        writer.addText(leftMargin + 5, PaperSize.A4_HEIGHT - 297, 20 ,"N/A" );

        //TODO car information enter here
        drawColumn(writer, 5, leftMargin + 200, PaperSize.A4_HEIGHT - 305, 145, 30);
        writer.setFont(StandardFonts.SUBTYPE, StandardFonts.COURIER, StandardFonts.WIN_ANSI_ENCODING);
        writer.addText(leftMargin + 5 + 200, PaperSize.A4_HEIGHT - 177, 18 ,ID );
        writer.addText(leftMargin + 5 + 200, PaperSize.A4_HEIGHT - 207, 18 ,name );
        writer.addText(leftMargin + 5 + 200, PaperSize.A4_HEIGHT - 237, 18 ,suffix );
        writer.addText(leftMargin + 5 + 200, PaperSize.A4_HEIGHT - 267, 18 ,chassis );
        writer.addText(leftMargin + 5 + 200, PaperSize.A4_HEIGHT - 297, 18 ,"N/A" );


        //Image of car
        drawCell(writer, 390, PaperSize.A4_HEIGHT - 305, 150,150);
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
        writer.addText(30, PaperSize.A4_HEIGHT - 352, 20 , "List of errors:");
        writer.addLine(25, PaperSize.A4_HEIGHT - 355, PaperSize.A4_WIDTH - rightMargin, PaperSize.A4_HEIGHT - 355);

        errors.add("abc");
        errors.add("abc");
        if(errors.size()>0) {
            int textAlignment = 380;
            for (int i = 0; i < errors.size(); i++) {
                writer.addText(leftMargin + 5, PaperSize.A4_HEIGHT - textAlignment, 15,  String.format("%d. %s",i+1,errors.get(i)));
                textAlignment+=30;
            }
        }else{
            //Do nothing i guess
        }

        //Loop start here to print the errors
        //END loop

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
