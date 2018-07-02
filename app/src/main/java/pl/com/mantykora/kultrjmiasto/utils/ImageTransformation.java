package pl.com.mantykora.kultrjmiasto.utils;


import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.squareup.picasso.Transformation;

public class ImageTransformation implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth());
        int y = (source.getHeight());

        float scaleWidth = 0.5f;
        float scaleHeight = 0.5f;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap result;

        if (x > 450 && y > 450) {

            result = Bitmap.createBitmap(source, 50, 50, 400, 400);

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
