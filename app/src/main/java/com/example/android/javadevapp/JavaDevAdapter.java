package com.example.android.javadevapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by GOZMAN VICTOR on 9/15/2017.
 */

public class JavaDevAdapter extends ArrayAdapter<Developer>{

    public JavaDevAdapter(Context context, ArrayList<Developer> developers) {
        super(context, 0, developers);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Developer currentDeveloper = getItem(position);
       String username = currentDeveloper.getUsername();
        TextView usernameTexView = (TextView) listItemView.findViewById(R.id.user_name);
        usernameTexView.setText(username);

        ImageView userImageView = (ImageView) listItemView.findViewById(R.id.user_image);
        Picasso.with(getContext())
                .load(currentDeveloper.getImageUrl())
                .resize(50, 50)
                .into(userImageView);

        return listItemView;
    }
}
