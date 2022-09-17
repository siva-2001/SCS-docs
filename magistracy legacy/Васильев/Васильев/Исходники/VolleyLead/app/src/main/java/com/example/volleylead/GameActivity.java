package com.example.volleylead;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.jaredrummler.android.colorpicker.ColorShape;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;


import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class GameActivity extends AppCompatActivity implements ColorPickerDialogListener {
    Button button7, button8;
    ImageView imageView, imageView2;
    int scoreT1, scoreT2, rScoreT1, rScoreT2, team_id1, team_id2, Comp_id, round = 0, partia = 0, finalScore = 0;
    private static final int firstId = 1,secondId = 2;
    String tvs, date, prName;
    DatabaseHelperT sqlHelper;
    DatabaseHelperG sqlHelperG;
    DatabaseHelperP sqlHelperP;
    SQLiteDatabase db;
    Cursor userCursor;
    File way = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    HSSFWorkbook myProtocol;
    HSSFSheet myPSheet;
    HSSFRow row;
    Cell tname;
    FileOutputStream out;
    InputStream path;
    FileInputStream pfis;
    String[] teams;
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");
        //System.setProperty("log4j2.disable.jmx", Boolean.TRUE.toString());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tvs = extras.getString("tvs");
            Comp_id = extras.getInt("Comp_id");
            date = extras.getString("date");
            prName = extras.getString("prName");
        }
        round = 1;
        partia = 1;
        TextView textView = findViewById(R.id.textView2);
        Button button1 = findViewById(R.id.buttong);
        button1.setText("+");
        Button button2 = findViewById(R.id.button2g);
        button2.setText("-");
        scoreT1 = 0;
        String scoreT1S = Integer.toString(scoreT1);
        TextView textView1 = findViewById(R.id.textView);
        textView1.setText(scoreT1S);
        TextView textView3 = findViewById(R.id.textView3g);
        teams = tvs.split(" ");
        textView.setText(teams[0]);
        textView3.setText(teams[2]);
        sqlHelper = new DatabaseHelperT(this);
        db = sqlHelper.open();
        userCursor = db.rawQuery("select * from " + DatabaseHelperT.TABLE + " where " +
                DatabaseHelperT.COLUMN_NAME + "=?", new String[]{String.valueOf(teams[0])});
        userCursor.moveToFirst();
        team_id1 = userCursor.getInt(userCursor.getColumnIndex(DatabaseHelperT.COLUMN_ID));
        userCursor.close();
        userCursor = db.rawQuery("select * from " + DatabaseHelperT.TABLE + " where " +
                DatabaseHelperT.COLUMN_NAME + "=?", new String[]{String.valueOf(teams[2])});
        userCursor.moveToFirst();
        team_id2 = userCursor.getInt(userCursor.getColumnIndex(DatabaseHelperT.COLUMN_ID));
        userCursor.close();
        db.close();
        Button button3 = findViewById(R.id.button3g);
        button3.setText("+");
        Button button4 = findViewById(R.id.button4g);
        button4.setText("-");
        scoreT2 = 0;
        String scoreT2S = Integer.toString(scoreT2);
        TextView textView4 = findViewById(R.id.textView4g);
        textView4.setText(scoreT2S);
        rScoreT1 = 0;
        rScoreT2 = 0;
        String rScoreT1S = Integer.toString(rScoreT1);
        TextView textView5 = findViewById(R.id.textView5g);
        textView5.setText(rScoreT1S);
        String rScoreT2S = Integer.toString(rScoreT2);
        TextView textView6 = findViewById(R.id.textView6g);
        textView6.setText(rScoreT2S);
        Button button5 = findViewById(R.id.button5g);
        button5.setText("V");
        Button button6 = findViewById(R.id.button6g);
        button6.setText("V");
        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);
        imageView.setImageResource(R.drawable.podpic);
        imageView2.setImageResource(R.drawable.podpic);
        imageView.setVisibility(View.INVISIBLE);
        imageView2.setVisibility(View.INVISIBLE);
        button7 = findViewById(R.id.button7g);
        button8 = findViewById(R.id.button8g);

        try {
            path = getAssets().open("Форма протокола.xls");
            myProtocol = new HSSFWorkbook(path);
            myPSheet = myProtocol.getSheetAt(0);
            row = myPSheet.getRow(2);
            tname = row.getCell(1);
            tname.setCellValue(teams[0]);
            row = myPSheet.getRow(20);
            tname = row.getCell(1);
            tname.setCellValue(teams[2]);
            row = myPSheet.getRow(51);
            tname = row.getCell(2);
            tname.setCellValue(date);
            row = myPSheet.getRow(50);
            tname = row.getCell(2);
            tname.setCellValue(prName);
            path.close();
            out = new FileOutputStream(way + "P_"+ date + "_" + teams[0] + "_" + teams[2] + ".xls");
            myProtocol.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlHelperP = new DatabaseHelperP(this);
        db = sqlHelperP.open();
        String tid1 = String.valueOf(team_id1);
        userCursor = db.rawQuery("select " + DatabaseHelperP.TABLE + "." + DatabaseHelperP.COLUMN_INIT
                + ", " + DatabaseHelperP.TABLE + "." + DatabaseHelperP.COLUMN_NUMBER
                + ", " + DatabaseHelperP.TABLE + "." + DatabaseHelperP.COLUMN_YEAR
                + " from " + DatabaseHelperP.TABLE + " where "
                + DatabaseHelperP.TABLE + "." + DatabaseHelperP.COLUMN_TID + "=" + tid1,null);
        try {
            pfis = new FileInputStream(way + "P_"+ date + "_" + teams[0] + "_" + teams[2] + ".xls") ;
            myProtocol = new HSSFWorkbook(pfis);
            myPSheet = myProtocol.getSheetAt(0);
            userCursor.moveToFirst();
            String ctemp;
            int citemp1, citemp2;
            int r = 6;
            int c1 = 1,c2 = 4,c3 = 5;
            while(!userCursor.isAfterLast()){
                row = myPSheet.getRow(r);
                tname = row.getCell(c1);
                ctemp = userCursor.getString(userCursor.getColumnIndex(DatabaseHelperP.COLUMN_INIT));
                tname.setCellValue(ctemp);
                tname = row.getCell(c2);
                citemp1 = userCursor.getInt(userCursor.getColumnIndex(DatabaseHelperP.COLUMN_NUMBER));
                tname.setCellValue(citemp1);
                tname = row.getCell(c3);
                citemp2 = userCursor.getInt(userCursor.getColumnIndex(DatabaseHelperP.COLUMN_YEAR));
                tname.setCellValue(citemp2);
                r++;
                userCursor.moveToNext();
            }
            userCursor.close();
            db.close();
            pfis.close();
            out = new FileOutputStream(way + "P_"+ date + "_" + teams[0] + "_" + teams[2] + ".xls");
            myProtocol.write(out);
            out.close();
        } catch (IOException e) {
        e.printStackTrace();
    }
        sqlHelperP = new DatabaseHelperP(this);
        db = sqlHelperP.open();
        String tid2 = String.valueOf(team_id2);
        userCursor = db.rawQuery("select " + DatabaseHelperP.TABLE + "." + DatabaseHelperP.COLUMN_INIT
                + ", " + DatabaseHelperP.TABLE + "." + DatabaseHelperP.COLUMN_NUMBER
                + ", " + DatabaseHelperP.TABLE + "." + DatabaseHelperP.COLUMN_YEAR
                + " from " + DatabaseHelperP.TABLE + " where "
                + DatabaseHelperP.TABLE + "." + DatabaseHelperP.COLUMN_TID + "=" + tid2,null);
        try {
            pfis = new FileInputStream(way + "P_"+ date + "_" + teams[0] + "_" + teams[2] + ".xls") ;
            myProtocol = new HSSFWorkbook(pfis);
            myPSheet = myProtocol.getSheetAt(0);
            userCursor.moveToFirst();
            String ctemp;
            int citemp1, citemp2;
            int r = 24;
            int c1 = 1,c2 = 4,c3 = 5;
            while(!userCursor.isAfterLast()){
                row = myPSheet.getRow(r);
                tname = row.getCell(c1);
                ctemp = userCursor.getString(userCursor.getColumnIndex(DatabaseHelperP.COLUMN_INIT));
                tname.setCellValue(ctemp);
                tname = row.getCell(c2);
                citemp1 = userCursor.getInt(userCursor.getColumnIndex(DatabaseHelperP.COLUMN_NUMBER));
                tname.setCellValue(citemp1);
                tname = row.getCell(c3);
                citemp2 = userCursor.getInt(userCursor.getColumnIndex(DatabaseHelperP.COLUMN_YEAR));
                tname.setCellValue(citemp2);
                r++;
                userCursor.moveToNext();
            }
            userCursor.close();
            db.close();
            pfis.close();
            out = new FileOutputStream(way + "P_"+ date + "_" + teams[0] + "_" + teams[2] + ".xls");
            myProtocol.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void scorePlusT1(View view) {
        scoreT1 ++;
        String scoreT1S = Integer.toString(scoreT1);
        TextView textView1 = findViewById(R.id.textView);
        textView1.setText(scoreT1S);
        imageView.setVisibility(View.VISIBLE);
        imageView2.setVisibility(View.INVISIBLE);
        try {
            pfis = new FileInputStream(way + "P_"+ date + "_" + teams[0] + "_" + teams[2] + ".xls") ;
            myProtocol = new HSSFWorkbook(pfis);
            myPSheet = myProtocol.getSheetAt(0);
            if(partia == 1){
                row = myPSheet.getRow(round + 2);
                tname = row.getCell(9);
                tname.setCellValue(scoreT1);
                tname = row.getCell(11);
                tname.setCellValue(scoreT2);
            }
            if(partia == 2){
                row = myPSheet.getRow(round + 2);
                tname = row.getCell(12);
                tname.setCellValue(scoreT1);
                tname = row.getCell(14);
                tname.setCellValue(scoreT2);
            }
            if(partia == 3){
                row = myPSheet.getRow(round + 2);
                tname = row.getCell(15);
                tname.setCellValue(scoreT1);
                tname = row.getCell(17);
                tname.setCellValue(scoreT2);
            }
            if(partia == 4){
                row = myPSheet.getRow(round + 2);
                tname = row.getCell(18);
                tname.setCellValue(scoreT1);
                tname = row.getCell(20);
                tname.setCellValue(scoreT2);
            }
            if(partia == 5){
                row = myPSheet.getRow(round + 2);
                tname = row.getCell(21);
                tname.setCellValue(scoreT1);
                tname = row.getCell(23);
                tname.setCellValue(scoreT2);
            }
            pfis.close();
            out = new FileOutputStream(way + "P_"+ date + "_" + teams[0] + "_" + teams[2] + ".xls");
            myProtocol.write(out);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        round++;
    }
    public void scoreMinusT1(View view) {
        scoreT1--;
        String scoreT1S = Integer.toString(scoreT1);
        TextView textView1 = findViewById(R.id.textView);
        textView1.setText(scoreT1S);
        imageView.setVisibility(View.INVISIBLE);
        imageView2.setVisibility(View.VISIBLE);
        round--;
    }
    public void scorePlusT2(View view) {
        scoreT2 ++;
        String scoreT2S = Integer.toString(scoreT2);
        TextView textView4 = findViewById(R.id.textView4g);
        textView4.setText(scoreT2S);
        imageView.setVisibility(View.INVISIBLE);
        imageView2.setVisibility(View.VISIBLE);
        try {
            pfis = new FileInputStream(way + "P_"+ date + "_" + teams[0] + "_" + teams[2] + ".xls") ;
            myProtocol = new HSSFWorkbook(pfis);
            myPSheet = myProtocol.getSheetAt(0);
            if(partia == 1){
                row = myPSheet.getRow(round + 2);
                tname = row.getCell(9);
                tname.setCellValue(scoreT1);
                tname = row.getCell(11);
                tname.setCellValue(scoreT2);
            }
            if(partia == 2){
                row = myPSheet.getRow(round + 2);
                tname = row.getCell(12);
                tname.setCellValue(scoreT1);
                tname = row.getCell(14);
                tname.setCellValue(scoreT2);
            }
            if(partia == 3){
                row = myPSheet.getRow(round + 2);
                tname = row.getCell(15);
                tname.setCellValue(scoreT1);
                tname = row.getCell(17);
                tname.setCellValue(scoreT2);
            }
            if(partia == 4){
                row = myPSheet.getRow(round + 2);
                tname = row.getCell(18);
                tname.setCellValue(scoreT1);
                tname = row.getCell(20);
                tname.setCellValue(scoreT2);
            }
            if(partia == 5){
                row = myPSheet.getRow(round + 2);
                tname = row.getCell(21);
                tname.setCellValue(scoreT1);
                tname = row.getCell(23);
                tname.setCellValue(scoreT2);
            }
            pfis.close();
            out = new FileOutputStream(way + "P_"+ date + "_" + teams[0] + "_" + teams[2] + ".xls");
            myProtocol.write(out);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        round++;

    }
    public void scoreMinusT2(View view) {
        scoreT2 --;
        String scoreT2S = Integer.toString(scoreT2);
        TextView textView4 = findViewById(R.id.textView4g);
        textView4.setText(scoreT2S);
        imageView.setVisibility(View.VISIBLE);
        imageView2.setVisibility(View.INVISIBLE);
        round--;
    }
    public void roundVT1(View view) {
        try {
            pfis = new FileInputStream(way + "P_"+ date + "_" + teams[0] + "_" + teams[2] + ".xls") ;
            myProtocol = new HSSFWorkbook(pfis);
            myPSheet = myProtocol.getSheetAt(0);
            if(partia == 1){
                row = myPSheet.getRow(38);
                tname = row.getCell(3);
                tname.setCellValue(scoreT1);
                tname = row.getCell(5);
                tname.setCellValue(scoreT2);
            }
            if(partia == 2){
                row = myPSheet.getRow(39);
                tname = row.getCell(3);
                tname.setCellValue(scoreT1);
                tname = row.getCell(5);
                tname.setCellValue(scoreT2);
            }
            if(partia == 3){
                row = myPSheet.getRow(40);
                tname = row.getCell(3);
                tname.setCellValue(scoreT1);
                tname = row.getCell(5);
                tname.setCellValue(scoreT2);
            }
            if(partia == 4){
                row = myPSheet.getRow(41);
                tname = row.getCell(3);
                tname.setCellValue(scoreT1);
                tname = row.getCell(5);
                tname.setCellValue(scoreT2);
            }
            if(partia == 5){
                row = myPSheet.getRow(42);
                tname = row.getCell(3);
                tname.setCellValue(scoreT1);
                tname = row.getCell(5);
                tname.setCellValue(scoreT2);
            }
            pfis.close();
            out = new FileOutputStream(way + "P_"+ date + "_" + teams[0] + "_" + teams[2] + ".xls");
            myProtocol.write(out);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        rScoreT1 ++;
        scoreT1 = 0;
        scoreT2 = 0;
        partia++;
        round = 1;
        String scoreT1S = Integer.toString(scoreT1);
        TextView textView1 = findViewById(R.id.textView);
        textView1.setText(scoreT1S);
        String scoreT2S = Integer.toString(scoreT2);
        TextView textView4 = findViewById(R.id.textView4g);
        textView4.setText(scoreT2S);
        String rScoreT1S = Integer.toString(rScoreT1);
        TextView textView5 = findViewById(R.id.textView5g);
        textView5.setText(rScoreT1S);
        imageView.setVisibility(View.INVISIBLE);
        imageView2.setVisibility(View.INVISIBLE);

    }
    public void roundVT2 (View view) {
        try {
            pfis = new FileInputStream(way + "P_"+ date + "_" + teams[0] + "_" + teams[2] + ".xls") ;
            myProtocol = new HSSFWorkbook(pfis);
            myPSheet = myProtocol.getSheetAt(0);
            if(partia == 1){
                row = myPSheet.getRow(38);
                tname = row.getCell(3);
                tname.setCellValue(scoreT1);
                tname = row.getCell(5);
                tname.setCellValue(scoreT2);
            }
            if(partia == 2){
                row = myPSheet.getRow(39);
                tname = row.getCell(3);
                tname.setCellValue(scoreT1);
                tname = row.getCell(5);
                tname.setCellValue(scoreT2);
            }
            if(partia == 3){
                row = myPSheet.getRow(40);
                tname = row.getCell(3);
                tname.setCellValue(scoreT1);
                tname = row.getCell(5);
                tname.setCellValue(scoreT2);
            }
            if(partia == 4){
                row = myPSheet.getRow(41);
                tname = row.getCell(3);
                tname.setCellValue(scoreT1);
                tname = row.getCell(5);
                tname.setCellValue(scoreT2);
            }
            if(partia == 5){
                row = myPSheet.getRow(42);
                tname = row.getCell(3);
                tname.setCellValue(scoreT1);
                tname = row.getCell(5);
                tname.setCellValue(scoreT2);
            }
            pfis.close();
            out = new FileOutputStream(way + "P_"+ date + "_" + teams[0] + "_" + teams[2] + ".xls");
            myProtocol.write(out);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        rScoreT2 ++;
        scoreT1 = 0;
        scoreT2 = 0;
        partia++;
        round = 1;
        String scoreT1S = Integer.toString(scoreT1);
        TextView textView1 = findViewById(R.id.textView);
        textView1.setText(scoreT1S);
        String scoreT2S = Integer.toString(scoreT2);
        TextView textView4 = findViewById(R.id.textView4g);
        textView4.setText(scoreT2S);
        String rScoreT2S = Integer.toString(rScoreT2);
        TextView textView6 = findViewById(R.id.textView6g);
        textView6.setText(rScoreT2S);
        imageView.setVisibility(View.INVISIBLE);
        imageView2.setVisibility(View.INVISIBLE);
    }
    private void createColorPickerDialog(int id) {
        ColorPickerDialog.newBuilder()
                .setColor(Color.RED)
                .setDialogType(ColorPickerDialog.TYPE_PRESETS)
                .setAllowCustom(true)
                .setAllowPresets(true)
                .setColorShape(ColorShape.SQUARE)
                .setDialogId(id)
                .show(this);
    }
    public void pickCol(View view) {
        switch (view.getId()) {
            case R.id.button7g:
                createColorPickerDialog(firstId);
                break;
            case R.id.button8g:
                createColorPickerDialog(secondId);
                break;
        }
    }
    @Override
    public void onColorSelected(int dialogId, int color) {
        switch (dialogId) { // смотрим, какая кнопка нажата
            case firstId:
                button7.setBackgroundColor(color);
                break;
            case secondId:
                button8.setBackgroundColor(color);
                break;
        }
    }

    @Override
    public void onDialogDismissed(int dialogId) {
        Toast.makeText(this, "Dialog dismissed", Toast.LENGTH_SHORT).show();
    }
    public void saveResults (View view) {
        try {
            pfis = new FileInputStream(way + "P_"+ date + "_" + teams[0] + "_" + teams[2] + ".xls") ;
            myProtocol = new HSSFWorkbook(pfis);
            myPSheet = myProtocol.getSheetAt(0);
            row = myPSheet.getRow(43);
            tname = row.getCell(3);
            tname.setCellValue(rScoreT1);
            tname = row.getCell(5);
            tname.setCellValue(rScoreT2);
            if(rScoreT1>rScoreT2){
                row = myPSheet.getRow(44);
                tname = row.getCell(2);
                tname.setCellValue(teams[0]);
            } else {
                row = myPSheet.getRow(44);
                tname = row.getCell(2);
                tname.setCellValue(teams[2]);
            }
            pfis.close();
            out = new FileOutputStream(way + "P_"+ date + "_" + teams[0] + "_" + teams[2] + ".xls");
            myProtocol.write(out);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        sqlHelperG = new DatabaseHelperG(this);
        db = sqlHelperG.open();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelperG.COLUMN_TID1, team_id1);
        cv.put(DatabaseHelperG.COLUMN_RESULT1, rScoreT1);
        cv.put(DatabaseHelperG.COLUMN_TID2, team_id2);
        cv.put(DatabaseHelperG.COLUMN_RESULT2, rScoreT2);
        cv.put(DatabaseHelperG.COLUMN_COMP_ID,Comp_id);
        db.insert(DatabaseHelperG.TABLE, null, cv);
        db.close();
        Intent intent = new Intent(this, ScheduleActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}