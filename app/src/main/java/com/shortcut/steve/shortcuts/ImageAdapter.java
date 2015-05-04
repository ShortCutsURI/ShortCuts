package com.shortcut.steve.shortcuts;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by Steve on 4/17/2015.
 */
public class ImageAdapter extends BaseAdapter
{
    private Context mContext;

    // Storing all Images in an array (for now)
    // Eventually need to populate all images according to account
    public Integer[] mThumbIds = {
            R.drawable.boba1, R.drawable.boba2,
            R.drawable.boba3, R.drawable.boba4,
            R.drawable.darth1, R.drawable.darth2
    };

    // Constructor
    public ImageAdapter(Context c)
    {
        mContext = c;
    }

    @Override
    public int getCount()
    {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position)
    {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(70, 70)); // <-- This doesn't seem to be working properly for some reason
        return imageView;
    }

}
