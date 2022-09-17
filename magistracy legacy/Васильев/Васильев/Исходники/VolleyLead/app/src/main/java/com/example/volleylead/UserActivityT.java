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

public class UserActivityT extends AppCompatActivity {
    EditText nameBox;
    Button delButton;
    Button saveButton;

    DatabaseHelperT sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    int Comp_id, team_id;
    long userId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_t);

        nameBox = findViewById(R.id.name);
        delButton = findViewById(R.id.deleteButton);
        saveButton = findViewById(R.id.saveButton);

        sqlHelper = new DatabaseHelperT(this);
        db = sqlHelper.open();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
            Comp_id = extras.getInt("Comp_id");
            team_id = extras.getInt("team_id");
        }
        // если 0, то добавление
        if (userId > 0) {
            // получаем элемент по id из бд
            userCursor = db.rawQuery("select * from " + DatabaseHelperT.TABLE + " where " +
                    DatabaseHelperT.COLUMN_ID + "=?", new String[]{String.valueOf(userId)});
            userCursor.moveToFirst();
            nameBox.setText(userCursor.getString(2));
            userCursor.close();
        } else {
            // скрываем кнопку удаления
            delButton.setVisibility(View.GONE);
        }
    }
    public void save(View view){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelperT.COLUMN_NAME, nameBox.getText().toString());
        cv.put(DatabaseHelperT.COLUMN_CID, Comp_id);

        if (userId > 0) {
            db.update(DatabaseHelperT.TABLE, cv, DatabaseHelperT.COLUMN_ID + "=" + userId, null);
        } else {
            db.insert(DatabaseHelperT.TABLE, null, cv);
        }
        goHome();
    }
    public void delete(View view){
        db.delete(DatabaseHelperT.TABLE, "_id = ?", new String[]{String.valueOf(userId)});
        goHome();
    }
    private void goHome(){
        // закрываем подключение
        db.close();
        // переход к главной activity
        Intent intent = new Intent(this, TeamActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
    public void playerList(View view){
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra("team_id", team_id);
        startActivity(intent);
    }
}