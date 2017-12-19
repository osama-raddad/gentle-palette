package com.osama.gentlepalette;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class GentlePalette {



    static void generate(int colorInt, String baseColors[], OnColorRoundingDone onColorRoundingDone) {
        onColorRoundingDone.onDone(Color.parseColor( getSimilarColor(String.format("#%06X", (0xFFFFFF & colorInt)), baseColors)));
    }


    private static String getSimilarColor(String color, String[] baseColors) {
        //Create an empty List for the difference between the colors
        List<Double> difference = new ArrayList<>();

        color = color.replace("#", "");

        //Convert to RGB, then R, G, B
        String rgb = getRGB(color);
        int r = Integer.parseInt(rgb.split(",")[0]);
        int g = Integer.parseInt(rgb.split(",")[1]);
        int b = Integer.parseInt(rgb.split(",")[2]);

        for (String baseColor : baseColors) {
            baseColor = baseColor.replace("#", "");

            String baseRGB = getRGB(baseColor);
            int baseR = Integer.parseInt(baseRGB.split(",")[0]);
            int baseG = Integer.parseInt(baseRGB.split(",")[1]);
            int baseB = Integer.parseInt(baseRGB.split(",")[2]);

            difference.add(Math.sqrt(
                    (r - baseR) * (r - baseR) +
                            (g - baseG) * (g - baseG) +
                            (b - baseB) * (b - baseB)
            ));
        }

        return baseColors[difference.indexOf(Collections.min(difference))];
    }

    //Function to convert HEX to RGB
    private static String getRGB(String color) {
        return String.valueOf(Integer.parseInt(color.charAt(0) + "" + color.charAt(1), 16))
                + ',' +
                String.valueOf(Integer.parseInt(color.charAt(2) + "" + color.charAt(3), 16))
                + ',' +
                String.valueOf(Integer.parseInt(color.charAt(4) + "" + color.charAt(5), 16));
    }

    public interface OnColorRoundingDone {
        void onDone(int color);
    }
}
