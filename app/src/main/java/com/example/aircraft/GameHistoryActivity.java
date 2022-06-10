package com.example.aircraft;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.aircraft.record.PlayerRecord;
import com.example.aircraft.record.PlayerRecordDao;
import com.example.aircraft.record.PlayerRecordDaoImpl;
import com.example.aircraft.record.RecordAdapter;
import java.util.List;

public class GameHistoryActivity extends AppCompatActivity {

    ListView listView;
    List<PlayerRecord> playerRecords;
    private DisplayMetrics displayMetrics;
    private int textWidthUnit;
    private PlayerRecordDao playerRecordDao;
    private int seletedPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        textWidthUnit = displayMetrics.widthPixels/8;

        //test fake record
        playerRecordDao = new PlayerRecordDaoImpl(this);
        //playerRecordDao.clearAll(1);
        Intent i = getIntent();
        String name = i.getStringExtra("name");
        int score = i.getIntExtra("score", 0);
        int mode = i.getIntExtra("mode", 0);
        playerRecordDao.addRecord(new PlayerRecord(name, score),mode);
//        playerRecordDao.addRecord(new PlayerRecord("localUser",30),1);
//        playerRecordDao.addRecord(new PlayerRecord("localUser",1000),1);
//        playerRecordDao.addRecord(new PlayerRecord("localUser",10100),1);
//        playerRecordDao.addRecord(new PlayerRecord("localUser",12000),1);
//        playerRecordDao.addRecord(new PlayerRecord("localUser",10200),1);
//        playerRecordDao.addRecord(new PlayerRecord("localUser",14000),1);
//        playerRecordDao.addRecord(new PlayerRecord("localUser",100),1);
        playerRecords = playerRecordDao.getAllRecords(mode);

        setContentView(R.layout.activity_record);
        listView = findViewById(R.id.list_view);
        RecordAdapter adapter = new RecordAdapter(this, R.layout.record_layout, playerRecords);
        adapter.setTextwidth(textWidthUnit);
        listView.setAdapter(adapter);
        Button deleteButton = (Button)findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.remove(seletedPosition);
                playerRecordDao.deleteRecord(seletedPosition,1);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seletedPosition = position;
                PlayerRecord playerRecord = playerRecords.get(position);
                Toast.makeText(GameHistoryActivity.this,"选中记录："+playerRecord.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
