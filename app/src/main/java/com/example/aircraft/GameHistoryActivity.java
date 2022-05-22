package com.example.aircraft;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraft.record.GameRecord;
import com.example.aircraft.record.RecordAdapter;

import java.util.ArrayList;
import java.util.List;

public class GameHistoryActivity extends AppCompatActivity {

    ListView listView;
    List<GameRecord> gameRecords;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        listView = findViewById(R.id.list_view);
        gameRecords = initializeTestRecord();
//        ArrayAdapter<GameRecord> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gameRecords);
        RecordAdapter adapter = new RecordAdapter(this, R.layout.record_layout, gameRecords);
        listView.setAdapter(adapter);

    }

    public List<GameRecord> initializeTestRecord() {
        List<GameRecord> gameRecords = new ArrayList<>();
        gameRecords.add(new GameRecord("user1",60));
        gameRecords.add(new GameRecord("user2",1000));
        gameRecords.add(new GameRecord("user3",600));
        return gameRecords;
    }
}
