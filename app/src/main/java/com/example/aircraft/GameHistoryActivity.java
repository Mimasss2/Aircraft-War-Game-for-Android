package com.example.aircraft;

import static com.example.aircraft.LoginActivity.settingsPath;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraft.conn.DAO.GameRecord;
import com.example.aircraft.conn.service.GameRecordService;
import com.example.aircraft.conn.service.Impl.GameRecordServiceImpl;
import com.example.aircraft.record.RecordAdapter;
import java.util.List;

public class GameHistoryActivity extends AppCompatActivity {

    ListView listView;
    List<GameRecord> gameRecords;
    private DisplayMetrics displayMetrics;
    private int textWidthUnit;
    private int seletedPosition;
    RecordAdapter adapter;
    GameRecordService gameRecordService;
    TextView difficultyTextView;
    TextView internetModeTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameRecordHandler gameRecordHandler = new GameRecordHandler();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        textWidthUnit = displayMetrics.widthPixels/8;

        //new database service
        gameRecordService = new GameRecordServiceImpl(GameHistoryActivity.this);

        Intent i = getIntent();
        int score = i.getIntExtra("score", 0);
        int mode = i.getIntExtra("mode", 0);

        new Thread(() -> {
            Message message = Message.obtain();
            int userId = getUserId();
            String name = getUserName();
            gameRecordService.insertGameRecord(new GameRecord(userId,score,name,mode),mode);
            gameRecords = gameRecordService.getAllGameRecords(mode);
            message.what = 1;
            gameRecordHandler.sendMessage(message);
        }).start();

        setContentView(R.layout.activity_record);
        setDifficultyTextView(mode);

        listView = findViewById(R.id.prop_list_view);
        Button deleteButton = (Button)findViewById(R.id.select_prop_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo: add delete function
//                adapter.remove(seletedPosition);
//                playerRecordDao.deleteRecord(seletedPosition,1);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //todo: add select item
//                seletedPosition = position;
//                PlayerRecord playerRecord = playerRecords.get(position);
//                Toast.makeText(GameHistoryActivity.this,"选中记录："+playerRecord.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public int getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences(settingsPath, 0);
        int userId = sharedPreferences.getInt("userId",0);
        return userId;
    }
    public String getUserName() {
        SharedPreferences sharedPreferences = getSharedPreferences(settingsPath, 0);
        return sharedPreferences.getString("name","");
    }

    class GameRecordHandler extends Handler {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //load data success
                case 1: {
                    adapter = new RecordAdapter(GameHistoryActivity.this, R.layout.record_layout, gameRecords);
                    adapter.setTextwidth(textWidthUnit);
                    listView.setAdapter(adapter);
                }
            }
        }
    }

    public void setDifficultyTextView(int mode) {
        difficultyTextView = findViewById(R.id.difficultyMode);
        switch (mode) {
            case 1: {
                difficultyTextView.setText("难度：简单");
                break;
            }
            case 2: {
                difficultyTextView.setText("难度：中等");
                break;
            }
            case 3: {
                difficultyTextView.setText("难度：困难");
                break;
            }
            case 4: {
                difficultyTextView.setText("难度：简单");
                break;
            }
        }
    }
    public void setInternetModeTextView(int mode) {
        internetModeTextView = findViewById(R.id.internetMode);
        if(mode == 4) {
            internetModeTextView.setText("模式：联网");
        }
        else {
            internetModeTextView.setText("模式：单机");
        }
    }
}
