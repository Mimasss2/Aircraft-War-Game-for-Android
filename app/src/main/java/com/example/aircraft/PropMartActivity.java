package com.example.aircraft;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraft.conn.DAO.Prop;
import com.example.aircraft.conn.service.Impl.PropServiceImpl;
import com.example.aircraft.conn.service.PropService;
import com.example.aircraft.record.PropAdapter;

import java.util.List;

public class PropMartActivity extends AppCompatActivity {
    ListView listView;
    List<Prop> propList;
    private DisplayMetrics displayMetrics;
    private int textWidthUnit;
    private int seletedPosition;
    PropAdapter adapter;
    PropService propService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PropHandler handler = new PropHandler();
        propService = new PropServiceImpl();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        textWidthUnit = displayMetrics.widthPixels/8;

        new Thread(() -> {
            Message message = Message.obtain();
            propList = propService.showAllProps();
            message.what = 1;
            handler.sendMessage(message);
        }).start();

        setContentView(R.layout.activity_prop_mart);
        listView = findViewById(R.id.prop_list_view);
        Button selectButton = (Button)findViewById(R.id.select_prop_button);
        selectButton.setOnClickListener(new View.OnClickListener() {
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


    class PropHandler extends Handler {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //load data success
                case 1: {
                    adapter = new PropAdapter(PropMartActivity.this, R.layout.prop_layout, propList);
                    adapter.setTextwidth(textWidthUnit);
                    listView.setAdapter(adapter);
                }
            }
        }
    }
}
