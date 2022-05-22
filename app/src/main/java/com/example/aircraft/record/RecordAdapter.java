package com.example.aircraft.record;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.aircraft.R;

import java.util.List;

public class RecordAdapter extends ArrayAdapter<GameRecord> {
    public RecordAdapter(@NonNull Context context, int resource, @NonNull List<GameRecord> objects) {
        super(context, resource, objects);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        GameRecord gameRecord = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.record_layout,parent,false);
        TextView rankView = view.findViewById(R.id.rankTextView);
        TextView userNameView = view.findViewById(R.id.userNameTextView);
        TextView scoreView = view.findViewById(R.id.scoreTextView);
        TextView timeView = view.findViewById(R.id.timeTextView);
        rankView.setText(String.valueOf(position+1));
        userNameView.setText(gameRecord.getUserName());
        scoreView.setText(String.valueOf(gameRecord.getScore()));
        timeView.setText(gameRecord.getDate().toString());
        return view;

    }
}
