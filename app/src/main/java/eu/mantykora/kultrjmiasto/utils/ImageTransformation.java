package eu.mantykora.kultrjmiasto.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.squareup.picasso.Transformation;

import eu.mantykora.kultrjmiasto.R;

public class ImageTransformation implements Transformation {

    /* Example from http://square.github.io/picasso/ */

    private Context context;
    public ImageTransformation(Context context) {this.context = context;}

    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth());
        int y = (source.getHeight());
        float dpi = context.getResources().getDisplayMetrics().density;
        float xDp = x / dpi;
        float yDp = y / dpi;


        int bitmapWidth = 900;
        int bitmapHeight = 900;

        float scaleWidth = 0.5f;
        float scaleHeight = 0.5f;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap result;

        if (xDp > 50 + bitmapWidth && yDp > 50 + bitmapHeight) {

            result = Bitmap.createBitmap(source, 50, 50, bitmapWidth, bitmapHeight);


            if (result != source) {
                source.recycle();
            }


        } else result = Bitmap.createBitmap(source, 0, 0, x, y);

        return result;
    }

    @Override
    public String key() {
        return "square()";
    }
}
