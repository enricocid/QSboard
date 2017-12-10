package com.qs.board.utils;

import android.graphics.Color;

import com.qs.board.R;

import java.util.Random;

public class ColorUtils {

    //create an array of colors that will populate the recycler view
    private static int[] material_colors = new int[]{

            R.color.material_red_400,
            R.color.material_pink_400,
            R.color.material_purple_400,
            R.color.material_deepPurple_400,
            R.color.material_indigo_400,
            R.color.material_blue_400,
            R.color.material_lightBlue_400,
            R.color.material_cyan_400,
            R.color.material_teal_400,
            R.color.material_green_400,
            R.color.material_amber_400,
            R.color.material_orange_400,
            R.color.material_deepOrange_400,
            R.color.material_brown_400,
            R.color.material_blueGrey_400,
            R.color.black,
            R.color.white
    };

    //get complementary color
    public static int getComplementaryColor(int colorToInvert) {

        int r = Color.red(colorToInvert);
        int g = Color.green(colorToInvert);
        int b = Color.blue(colorToInvert);
        int red = 255 - r;
        int green = 255 - g;
        int blue = 255 - b;

        return android.graphics.Color.argb(255, red, green, blue);

    }

    public static int getRandomMaterialColor() {
        int rnd = new Random().nextInt(material_colors.length);
        return material_colors[rnd];
    }
}
