package eu.mantykora.kultrjmiasto.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import eu.mantykora.kultrjmiasto.R;
import eu.mantykora.kultrjmiasto.model.CategoryEnum;

public class IconsAdapter extends BaseAdapter {

    private final Context context;

    public IconsAdapter(Context context) {
        this.context = context;
        contentDescriptions = new String[]{

                context.getResources().getString(R.string.theater),
                context.getResources().getString(R.string.cinema),
                context.getResources().getString(R.string.art),
                context.getResources().getString(R.string.music),
                context.getResources().getString(R.string.science),
                context.getResources().getString(R.string.literature),
                context.getResources().getString(R.string.entertainment),
                context.getResources().getString(R.string.recreation),
                context.getResources().getString(R.string.other),
                context.getResources().getString(R.string.all)


        };
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        TextView titleDescriptionTv;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.list_item_icon, null);


        }


        imageView = convertView.findViewById(R.id.icon_iv);
        titleDescriptionTv = convertView.findViewById(R.id.icon_tv);

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        TypedArray icons = context.getResources().obtainTypedArray(R.array.icons);
        int def = 0;
        imageView.setImageResource(icons.getResourceId(position, def));
        imageView.setContentDescription(contentDescriptions[position]);
        titleDescriptionTv.setText(contentDescriptions[position]);
        if(CategoryEnum.existsForPosition(position)) {
            convertView.setBackgroundColor(Color.RED);
        }

        return convertView;
    }


    private final String[] contentDescriptions;


}
