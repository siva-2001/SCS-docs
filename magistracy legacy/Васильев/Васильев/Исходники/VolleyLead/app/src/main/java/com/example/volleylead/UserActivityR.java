package com.example.volleylead;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class UserActivityR extends AppCompatActivity {
    ListView userList, userList2;
    DatabaseHelperG sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor, userCursor1;
    SimpleCursorAdapter userAdapter, userAdapter1;
    int Comp_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_r);
        userList = findViewById(R.id.resultList1);
        userList2 = findViewById(R.id.resultList2);
        sqlHelper = new DatabaseHelperG(getApplicationContext());
        // создаем базу данных
        sqlHelper.create_db();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Comp_id = extras.getInt("Comp_id");
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = sqlHelper.open();
        //получаем данные из бд в виде курсора
        userCursor = db.rawQuery("select " + DatabaseHelperG.TABLE + "." + DatabaseHelperG.COLUMN_ID + ", "
                + DatabaseHelperG.TABLE + "." + DatabaseHelperG.COLUMN_TID1 + ", "
                + DatabaseHelperG.TABLE + "." + DatabaseHelperG.COLUMN_RESULT1 + ", " + DatabaseHelperT.TABLE + "." + DatabaseHelperT.COLUMN_NAME
                + " from " + DatabaseHelperG.TABLE
                + " Join " + DatabaseHelperT.TABLE + " on " + DatabaseHelperT.TABLE + "." + DatabaseHelperT.COLUMN_ID + " = "
                + DatabaseHelperG.TABLE + "." + DatabaseHelperG.COLUMN_TID1 + " AND "
                + DatabaseHelperT.TABLE + "." + DatabaseHelperT.COLUMN_CID + " = " + Comp_id, null);
        userCursor1 = db.rawQuery("select " + DatabaseHelperG.TABLE + "."
                + DatabaseHelperG.COLUMN_ID + ", " + DatabaseHelperG.TABLE + "."
                + DatabaseHelperG.COLUMN_TID2 + ", "
                + DatabaseHelperG.TABLE + "." + DatabaseHelperG.COLUMN_RESULT2 + ", " + DatabaseHelperT.TABLE + "." + DatabaseHelperT.COLUMN_NAME
                + " from " + DatabaseHelperG.TABLE
                + " Join " + DatabaseHelperT.TABLE + " on " + DatabaseHelperT.TABLE + "." + DatabaseHelperT.COLUMN_ID + " = "
                + DatabaseHelperG.TABLE + "." + DatabaseHelperG.COLUMN_TID2 + " AND "
                + DatabaseHelperT.TABLE + "." + DatabaseHelperT.COLUMN_CID + " = " + Comp_id, null);
        //+ " inner join " + DatabaseHelperT.TABLE + " on " + DatabaseHelperG.
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{DatabaseHelperT.COLUMN_NAME, DatabaseHelperG.COLUMN_RESULT1};
        String[] headers1 = new String[]{DatabaseHelperT.COLUMN_NAME, DatabaseHelperG.COLUMN_RESULT2};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        userAdapter1 = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor1, headers1, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        userList.setAdapter(userAdapter);
        userList2.setAdapter(userAdapter1);
    }
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
        userCursor1.close();
    }
}