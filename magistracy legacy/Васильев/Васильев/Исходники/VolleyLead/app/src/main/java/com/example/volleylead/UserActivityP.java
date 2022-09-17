package com.example.volleylead;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;

public class UserActivityP extends AppCompatActivity {
    EditText numberBox,nameBox, yearBox;
    Button delButton;
    Button saveButton;

    DatabaseHelperP sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    int team_id;
    long userId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_p);
        numberBox = findViewById(R.id.number);
        nameBox = findViewById(R.id.initials);
        yearBox = findViewById(R.id.yearb);
        delButton = findViewById(R.id.deleter);
        saveButton = findViewById(R.id.saver);
        sqlHelper = new DatabaseHelperP(this);
        db = sqlHelper.open();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
            team_id = extras.getInt("team_id");
        }
        if (userId > 0) {
            // получаем элемент по id из бд
            userCursor = db.rawQuery("select * from " + DatabaseHelperP.TABLE + " where " +
                    DatabaseHelperP.COLUMN_ID + "=?", new String[]{String.valueOf(userId)});
            userCursor.moveToFirst();
            int tempn, tempy;
            tempn = userCursor.getInt(2);
            tempy = userCursor.getInt(4);
            numberBox.setText(Integer.toString(tempn));
            nameBox.setText(userCursor.getString(3));
            yearBox.setText(Integer.toString(tempy));
            userCursor.close();
        } else {
            // скрываем кнопку удаления
            delButton.setVisibility(View.GONE);
        }
    }
    public void save(View view){
        ContentValues cv = new ContentValues();
        int number, yearB;
        number = Integer.valueOf(numberBox.getText().toString());
        yearB = Integer.valueOf(yearBox.getText().toString());
        cv.put(DatabaseHelperP.COLUMN_TID, team_id);
        cv.put(DatabaseHelperP.COLUMN_NUMBER, number);
        cv.put(DatabaseHelperP.COLUMN_INIT, nameBox.getText().toString());
        cv.put(DatabaseHelperP.COLUMN_YEAR, yearB);
        if (userId > 0) {
            db.update(DatabaseHelperP.TABLE, cv, DatabaseHelperP.COLUMN_ID + "=" + userId, null);
        } else {
            db.insert(DatabaseHelperP.TABLE, null, cv);
        }
        goHome();
    }
    public void delete(View view){
        db.delete(DatabaseHelperP.TABLE, "_id = ?", new String[]{String.valueOf(userId)});
        goHome();
    }
    private void goHome(){
        // закрываем подключение
        db.close();
        // переход к главной activity
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}