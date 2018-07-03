package pl.com.mantykora.kultrjmiasto.utils;


import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.squareup.picasso.Transformation;

import pl.com.mantykora.kultrjmiasto.R;

public class ImageTransformation implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth());
        int y = (source.getHeight());

        int bitmapWidth = R.dimen.bitmapWidth;
        int bitmapHeidht = R.dimen.bitmapHeight;

        float scaleWidth = 0.5f;
        float scaleHeight = 0.5f;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap result;

        if (x > 50 + bitmapWidth && y > 50 + bitmapHeidht) {

            result = Bitmap.createBitmap(source, 50, 50, bitmapWidth, bitmapHeidht);

            //TODO use createBitmap with Martix

            if (result != source) {
                source.recycle();
            }


        }

        else result = Bitmap.createBitmap(source,0, 0, x, y);
        return result;
    }

    @Override
    public String key() {
        return "square()";
    }
}
