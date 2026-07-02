package com.osama.gentlepalette;

public class GentlePalette {

    private GentlePalette() {
    }

    /**
     * Rounds a color to its nearest match in the given palette.
     *
     * <p>Similarity is measured with the redmean-weighted squared distance in RGB
     * space, a low-cost approximation of perceived color difference. When two
     * palette entries are equally close, the one that appears first wins.
     *
     * <p>The alpha channel of {@code color} is ignored and the returned color is
     * always fully opaque.
     *
     * @param color      the color to round, as an ARGB color int
     * @param baseColors the palette to round against; each entry must be a
     *                   {@code #RRGGBB} hex string (case-insensitive)
     * @return the nearest palette color, as an opaque ARGB color int
     * @throws IllegalArgumentException if the palette is null, empty, or contains
     *                                  an entry that is not a {@code #RRGGBB} hex string
     */
    public static int round(int color, String[] baseColors) {
        if (baseColors == null || baseColors.length == 0) {
            throw new IllegalArgumentException("baseColors must contain at least one color");
        }

        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        int nearest = 0;
        double nearestDistance = Double.POSITIVE_INFINITY;

        for (String baseColor : baseColors) {
            int base = parseHex(baseColor);
            int baseR = (base >> 16) & 0xFF;
            int baseG = (base >> 8) & 0xFF;
            int baseB = base & 0xFF;

            int dr = r - baseR;
            int dg = g - baseG;
            int db = b - baseB;

            double rMean = (r + baseR) / 2.0;
            double distance = (2 + rMean / 256) * dr * dr
                    + 4.0 * dg * dg
                    + (2 + (255 - rMean) / 256) * db * db;

            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearest = base;
            }
        }

        return 0xFF000000 | nearest;
    }

    private static int parseHex(String color) {
        if (color == null || color.length() != 7 || color.charAt(0) != '#') {
            throw new IllegalArgumentException("Invalid palette color \"" + color + "\": expected #RRGGBB");
        }
        int value = 0;
        for (int i = 1; i < 7; i++) {
            int digit = Character.digit(color.charAt(i), 16);
            if (digit < 0) {
                throw new IllegalArgumentException("Invalid palette color \"" + color + "\": expected #RRGGBB");
            }
            value = (value << 4) | digit;
        }
        return value;
    }
}
