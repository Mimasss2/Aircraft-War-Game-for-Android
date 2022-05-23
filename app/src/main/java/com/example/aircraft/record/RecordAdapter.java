package com.example.aircraft.record;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.aircraft.R;

import java.util.List;

public class RecordAdapter extends ArrayAdapter<PlayerRecord> {
    private List<PlayerRecord> playerRecords;
    private int textwidth;
    public RecordAdapter(@NonNull Context context, int resource, @NonNull List<PlayerRecord> objects) {
        super(context, resource, objects);
        this.playerRecords = objects;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PlayerRecord playerRecord = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.record_layout,parent,false);
        TextView rankView = view.findViewById(R.id.rankTextView);
        TextView userNameView = view.findViewById(R.id.userNameTextView);
        TextView scoreView = view.findViewById(R.id.scoreTextView);
        TextView timeView = view.findViewById(R.id.timeTextView);
        rankView.setWidth(textwidth);
        userNameView.setWidth(3*textwidth);
        scoreView.setWidth(textwidth);
        timeView.setWidth(3*textwidth);
        rankView.setText(String.valueOf(position+1));
        userNameView.setText(playerRecord.getUserName());
        scoreView.setText(String.valueOf(playerRecord.getScore()));
        timeView.setText(playerRecord.getDate().toString());
        return view;
    }
    public void remove(int position) {
        playerRecords.remove(position);
        notifyDataSetChanged();
    }
    public void setTextwidth(int textwidth) {
        this.textwidth = textwidth;
    }
}
