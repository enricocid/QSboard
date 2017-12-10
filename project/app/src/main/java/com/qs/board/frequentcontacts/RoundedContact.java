package com.qs.board.frequentcontacts;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

import com.qs.board.utils.ColorUtils;

class RoundedContact {

    static RoundedBitmapDrawable get(Activity activity, Uri thumbnail) {

        RoundedBitmapDrawable dr = null;

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), thumbnail);
            dr = RoundedBitmapDrawableFactory.create(activity.getResources(), bitmap);
            dr.setCircular(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dr;
    }

    //create round icon enrico's style
    static Bitmap createRoundIconWithText(Context context, String letter) {

        //calculate dimensions
        //-1 to take into account the shadow layer
        int w = (int) context.getResources().getDimension(android.R.dimen.app_icon_size);
        int h = (int) context.getResources().getDimension(android.R.dimen.app_icon_size);
        int r = w / 2 - 1;

        //create bitmap
        Bitmap b = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

        //draw a circle of the same dimensions
        Canvas canvas = new Canvas(b);
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, ColorUtils.getRandomMaterialColor());
        paint.setColor(color);

        final int SHADOW_COLOR = 0x80000000;
        paint.setShadowLayer(0.5f, 1, 1, SHADOW_COLOR);
        paint.setAntiAlias(true);
        canvas.drawCircle(r, r, r, paint);

        Paint textPaint = new Paint();
        textPaint.setColor(ColorUtils.getComplementaryColor(color));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(w / 2);

        textPaint.setAntiAlias(true);
        textPaint.setTypeface(Typeface.SANS_SERIF);
        int xPos = (canvas.getWidth() / 2);
        int yPos = (int) ((canvas.getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2));

        canvas.drawText(letter, xPos, yPos, textPaint);

        return b;
    }
}
