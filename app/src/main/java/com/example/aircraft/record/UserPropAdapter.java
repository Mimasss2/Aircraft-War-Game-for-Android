package com.example.aircraft.record;

import static com.example.aircraft.UserPropActivity.getPropBitmap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.aircraft.R;
import com.example.aircraft.conn.DAO.Prop;

import java.util.List;

public class UserPropAdapter extends ArrayAdapter<Prop> {
    private List<Prop> propList;
    private int textwidth;

    public UserPropAdapter(@NonNull Context context, int resource, @NonNull List<Prop> objects) {
        super(context, resource, objects);
        this.propList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Prop prop = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.prop_layout,parent,false);
        ImageView propImageView = view.findViewById(R.id.propImage);
        TextView propNameView = view.findViewById(R.id.propNameTextView);
        TextView propDescriptionView = view.findViewById(R.id.propDescriptionTextView);
        TextView propCreditTextView = view.findViewById(R.id.propCreditTextView);
        propImageView.setMaxWidth(2*textwidth);
        propNameView.setWidth(textwidth);
        propDescriptionView.setWidth(4*textwidth);
        propCreditTextView.setWidth(textwidth);
        propNameView.setText(prop.getPropName());
        propDescriptionView.setText(prop.getDescription());
        propCreditTextView.setText(String.valueOf(prop.getPropCredit()));
        propImageView.setImageBitmap(getPropBitmap(prop.getResourceId()));
        return view;
    }
    public void remove(int position) {
        propList.remove(position);
        notifyDataSetChanged();
    }

    public void setTextwidth(int textwidth) {
        this.textwidth = textwidth;
    }
}
