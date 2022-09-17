package com.example.volleylead;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;

public class ScheduleActivity extends AppCompatActivity {
    String date;
    ArrayList<String> tempt, tempt2, schedule;
    ListView userList;
    int Comp_id;
    TextView tv;
    String tvs, prName;
    String teams[], end[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        userList = findViewById(R.id.Slist);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            date = extras.getString("date");
            teams = extras.getStringArray("teams");
            Comp_id = extras.getInt("Comp_id");
            prName = extras.getString("prName");
        }
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                String selectedItem = (String) parent.getItemAtPosition(position);
                tvs =  selectedItem;
                /*Button button1 = findViewById(R.id.addButton);
                button1.setText(Integer.toString(Comp_id));*/
                intent.putExtra("date", date);
                intent.putExtra("tvs", tvs);
                intent.putExtra("Comp_id", Comp_id);
                intent.putExtra("prName", prName);
                startActivity(intent);
            }
        });
        tv = findViewById(R.id.textView2);
        tv.setText("Матчи " + date);
        tempt = new ArrayList<>();
        tempt2 = new ArrayList<>();
        schedule = new ArrayList<>();
        Collections.addAll(tempt, teams);
        Collections.addAll(tempt2, teams);
        while (tempt.size()>0){
            for(int i = 0; i < tempt2.size(); i++){
                String temp = tempt.get(0);
                String temp2 = tempt2.get(i);
                if (temp != temp2){
                    String temp3 = temp + " против " +temp2;
                    schedule.add(temp3);

                }
            }
            tempt.remove(0);
            tempt2.remove(0);
        }
        end = new String[schedule.size()];
        schedule.toArray(end);
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, end);
        userList.setAdapter(adapter);
    }
    public void showResult(View view){
        Intent intent = new Intent(getApplicationContext(), UserActivityR.class);
        intent.putExtra("Comp_id", Comp_id);
        startActivity(intent);
    }
    /*public void openExplorer(View View){
        Intent intent = new Intent(getApplicationContext(), ProtocolExplorerActivity.class);
        startActivity(intent);
    }*/
}