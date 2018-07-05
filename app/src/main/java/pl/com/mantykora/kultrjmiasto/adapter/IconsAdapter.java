package pl.com.mantykora.kultrjmiasto.adapter;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import pl.com.mantykora.kultrjmiasto.R;

public class IconsAdapter extends BaseAdapter {

    private Context context;

    public IconsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageIds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
            return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(imageIds[position]);
        return imageView;
    }

    private Integer[] imageIds = {
            R.drawable.baseline_theaters_black_48,
            R.drawable.baseline_videocam_black_48,
            R.drawable.baseline_portrait_black_48,
            R.drawable.baseline_music_note_black_48,
            R.drawable.baseline_hdr_strong_black_48,
            R.drawable.baseline_local_library_black_48,
            R.drawable.baseline_local_bar_black_48,
            R.drawable.baseline_terrain_black_48,
            R.drawable.baseline_more_black_48,
            R.drawable.baseline_select_all_black_48
    };
}
