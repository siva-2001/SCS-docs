package com.example.volleylead;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Bundle;

public class TeamActivity extends AppCompatActivity {
    ListView userList;
    DatabaseHelperT databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    String date, prName;
    int Comp_id;
    TextView tv;
    String teams[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            date = extras.getString("date");
            Comp_id = extras.getInt("Comp_id");
            prName = extras.getString("prName");
        }
        tv = findViewById(R.id.textView);
        tv.setText("Соревнование " + date);
        //tv.setText(Integer.toString(Comp_id));
        userList = findViewById(R.id.list);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), UserActivityT.class);
                /*Button button1 = findViewById(R.id.addButton);
                button1.setText(Integer.toString(Comp_id));*/
                Cursor selectedItem = (Cursor)parent.getItemAtPosition(position);
                @SuppressLint("Range") int team_id = selectedItem.getInt(selectedItem.getColumnIndex(DatabaseHelperT.COLUMN_ID));
                intent.putExtra("id", id);
                intent.putExtra("Comp_id", Comp_id);
                intent.putExtra("team_id", team_id);
                startActivity(intent);
            }
        });

        databaseHelper = new DatabaseHelperT(getApplicationContext());
        // создаем базу данных
        databaseHelper.create_db();
    }

    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = databaseHelper.open();
        //получаем данные из бд в виде курсора
        userCursor = db.rawQuery("select * from " + DatabaseHelperT.TABLE + " where " +
                             DatabaseHelperT.COLUMN_CID + "=?", new String[]{String.valueOf(Comp_id)});
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{DatabaseHelperT.COLUMN_NAME};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                userCursor, headers, new int[]{android.R.id.text1}, 0);
        userList.setAdapter(userAdapter);
    }

    // по нажатию на кнопку запускаем UserActivityT для добавления данных
    public void addT(View view) {
        Intent intent = new Intent(this, UserActivityT.class);
        intent.putExtra("Comp_id", Comp_id);
        startActivity(intent);
    }
    @SuppressLint("Range")
    public void schedule(View view) {
        Intent intent = new Intent(this, ScheduleActivity.class);
        teams = new String[userList.getCount()];
        for(int i = 0; i < userList.getCount(); i++){
            TextView teamCurs = (TextView) userList.getChildAt(i);
            teams[i] = (String) teamCurs.getText();
        }
        intent.putExtra("teams", teams);
        intent.putExtra("date", date);
        intent.putExtra("Comp_id", Comp_id);
        intent.putExtra("prName", prName);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
    }
}