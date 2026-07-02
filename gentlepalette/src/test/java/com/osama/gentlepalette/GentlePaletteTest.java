package com.osama.gentlepalette;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class GentlePaletteTest {

    private static final String[] BLACK_AND_WHITE = {"#000000", "#FFFFFF"};

    @Test
    public void exactPaletteColorRoundsToItself() {
        assertEquals(0xFFF44336, GentlePalette.round(0xFFF44336, Palettes.MATERIAL_COLORS));
    }

    @Test
    public void everyMaterialColorRoundsToItself() {
        for (String hex : Palettes.MATERIAL_COLORS) {
            int color = 0xFF000000 | Integer.parseInt(hex.substring(1), 16);
            assertEquals(hex, color, GentlePalette.round(color, Palettes.MATERIAL_COLORS));
        }
    }

    @Test
    public void inputAlphaIsIgnoredAndResultIsOpaque() {
        int opaque = GentlePalette.round(0xFFF44336, Palettes.MATERIAL_COLORS);
        int translucent = GentlePalette.round(0x80F44336, Palettes.MATERIAL_COLORS);
        int noAlpha = GentlePalette.round(0x00F44336, Palettes.MATERIAL_COLORS);
        assertEquals(opaque, translucent);
        assertEquals(opaque, noAlpha);
        assertEquals(0xFF000000, opaque & 0xFF000000);
    }

    @Test
    public void blackAndWhiteRoundToThemselves() {
        assertEquals(0xFF000000, GentlePalette.round(0xFF000000, BLACK_AND_WHITE));
        assertEquals(0xFFFFFFFF, GentlePalette.round(0xFFFFFFFF, BLACK_AND_WHITE));
    }

    @Test
    public void midGrayRoundsToWhite() {
        // #808080 is one step closer to white (channel delta 127) than to
        // black (channel delta 128).
        assertEquals(0xFFFFFFFF, GentlePalette.round(0xFF808080, BLACK_AND_WHITE));
    }

    @Test
    public void redmeanWeightsGreenDifferencesMoreThanBlue() {
        // Input #808080 differs from #800080 only in green and from #808000
        // only in blue, by 128 either way. Plain Euclidean sees a tie; redmean
        // weights green 4x versus ~2.5x for blue, so the green-matching
        // candidate #808000 must win.
        String[] palette = {"#800080", "#808000"};
        assertEquals(0xFF808000, GentlePalette.round(0xFF808080, palette));
    }

    @Test
    public void firstEntryWinsTies() {
        // Both candidates differ from the input only by ±10 in green, so their
        // redmean distances are identical; the first entry must win.
        String[] palette = {"#007600", "#008A00"};
        assertEquals(0xFF007600, GentlePalette.round(0xFF008000, palette));

        String[] reversed = {"#008A00", "#007600"};
        assertEquals(0xFF008A00, GentlePalette.round(0xFF008000, reversed));
    }

    @Test
    public void lowercaseAndUppercaseHexAreEquivalent() {
        assertEquals(GentlePalette.round(0xFF1A2B3C, new String[]{"#ABCDEF"}),
                GentlePalette.round(0xFF1A2B3C, new String[]{"#abcdef"}));
    }

    @Test
    public void nullPaletteIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> GentlePalette.round(0xFF000000, null));
    }

    @Test
    public void emptyPaletteIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> GentlePalette.round(0xFF000000, new String[0]));
    }

    @Test
    public void malformedPaletteEntriesAreRejected() {
        String[][] badPalettes = {
                {"#FFF"},
                {"red"},
                {""},
                {"FF0000"},
                {"#GG0000"},
                {"#-12345"},
                {null},
                {"#FF0000", "#12345"},
        };
        for (String[] palette : badPalettes) {
            assertThrows(IllegalArgumentException.class,
                    () -> GentlePalette.round(0xFF000000, palette));
        }
    }

    @Test
    public void allPredefinedPalettesAreWellFormed() {
        String[][] palettes = {
                Palettes.FLAT_COLORS,
                Palettes.MATERIAL_COLORS,
                Palettes.FLUENT_COLORS,
                Palettes.METRO_COLORS,
                Palettes.SOCIAL_COLORS,
        };
        for (String[] palette : palettes) {
            GentlePalette.round(0xFF000000, palette);
        }
    }
}
