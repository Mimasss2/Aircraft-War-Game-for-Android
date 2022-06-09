package com.example.aircraft;

import static com.example.aircraft.LoginActivity.settingsPath;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraft.conn.DAO.Prop;
import com.example.aircraft.conn.service.Impl.PropServiceImpl;
import com.example.aircraft.conn.service.PropService;
import com.example.aircraft.record.PropAdapter;
import com.example.aircraft.record.UserPropAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserPropActivity extends AppCompatActivity {
    ListView listView;
    List<Prop> propList;
    private DisplayMetrics displayMetrics;
    private int textWidthUnit;
    private int seletedPosition;
    UserPropAdapter adapter;
    PropService propService;
    Prop selectedProp;
    //图片资源
    private static final Map<Integer, Bitmap> PROP_IMAGE_MAP = new HashMap<>();
    public static Bitmap PROP_BOMB;
    public static Bitmap PROP_SHIELD;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadResources();
        int userId = getUserId();
        PropHandler handler = new PropHandler();
        propService = new PropServiceImpl();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        textWidthUnit = displayMetrics.widthPixels/8;

        new Thread(() -> {
            Message message = Message.obtain();
            propList = propService.showUserProp(userId);
            message.what = 1;
            handler.sendMessage(message);
        }).start();

        setContentView(R.layout.activity_prop_mart);
        listView = findViewById(R.id.prop_list_view);
        Button selectButton = (Button)findViewById(R.id.select_prop_button);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo: add use prop function
                Message message = Message.obtain();
                message.what = 2;
                message.obj = selectedProp.getPropName();
                handler.sendMessage(message);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seletedPosition = position;
                selectedProp = propList.get(position);
                Toast.makeText(UserPropActivity.this,"选中道具："+selectedProp.getPropName(),Toast.LENGTH_SHORT).show();
            }
        });
        //设置TextView
        TextView title = findViewById(R.id.mart_title);
        title.setText("用户道具列表");
        TextView selectProp = findViewById(R.id.select_prop_button);
        selectProp.setText("使用选择道具");
    }


    class PropHandler extends Handler {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //load data success
                case 1: {
                    adapter = new UserPropAdapter(UserPropActivity.this, R.layout.prop_layout, propList);
                    adapter.setTextwidth(textWidthUnit);
                    listView.setAdapter(adapter);
                }
                case 2: {
                    Toast.makeText(UserPropActivity.this,"使用道具："+msg.obj,Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void loadResources() {
        Resources res = this.getResources();
        PROP_BOMB = BitmapFactory.decodeResource(res, R.drawable.prop_bomb);
        PROP_SHIELD = BitmapFactory.decodeResource(res, R.drawable.prop_blood);
        PROP_IMAGE_MAP.put(1,PROP_BOMB);
        PROP_IMAGE_MAP.put(2,PROP_SHIELD);
    }
    public static Bitmap getPropBitmap(int key) {
        return PROP_IMAGE_MAP.get(key);
    }
    private int getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences(settingsPath, 0);
//        int userId = sharedPreferences.getInt("userId",0);
        int userId = sharedPreferences.getInt("userId",1);
        return userId;
    }
}
