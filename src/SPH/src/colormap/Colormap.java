/** This file is part of PeakML.
 *
 * PeakML is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * PeakML is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with PeakML; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package colormap;
import java.awt.Color;

/**
 * This class is a fork of the Colomap class from mzMatch/PeakML
 * https://mzmatch.sourceforge.net/peakml/peakml/graphics/Colormap.html
 *
 * It defines different color models for visualization purposes (like heatmaps
 * and graphs). A specific colormap can be created by using one of the
 * enumerated integers defined in the class. An example of the use:
 *
 * <pre>
 * // create the colormap
 * Colormap colormap = new Colormap(Colormap.JET);
 *
 * // print all the colors
 * for (int i=0; i<colormap.getNrColors(); ++i)
 * {
 *    int color = colormap.getColor(i);
 *    // print the red, green and blue component
 *    System.out.println(((color>>16)&0xFF) + ", " + ((color>>8)&0xFF) + ", " + (color&0xFF));
 * }
 * </pre>
 */
public class Colormap {

    /**
     * A JET color model as used in Matlab.
     */
    public static final int JET = 0;
    /**
     * A Hue-Saturation-Value colormodel.
     */
    public static final int HSV = 1;
    /**
     * A heat color model used to create heatmaps.
     */
    public static final int HEAT = 2;
    /**
     * A standard excel color model, where each color tries to be as different
     * from the rest as possible.
     */
    public static final int EXCEL = 3;
    /**
     * A greyscale color model.
     */
    public static final int GRAYSCALE = 4;
    /**
     * A color model ranging from red to white to green.
     */
    public static final int REDGREEN = 5;
    /**
     * A color model ranging from red to black to green.
     */
    public static final int REDBLACKGREEN = 6;
    /**
     * A rainbow color model.  
     */
    public static final int RAINBOW = 7;
    /**
     * A water color model.
     */
    public static final int WATER = 8;
    
    
    private int type;

    private Color colors[] = null;

    /**
     * Constructor for creating the specified color model.
     *
     * @param type	The type of color-model to be created.
     */
    public Colormap(int type) throws RuntimeException {
        this.type = type;
        if (type == JET) {
            createJetColors();
        } else if (type == HSV) {
            createHSVColors();
        } else if (type == HEAT) {
            createHeatColors();
        } else if (type == EXCEL) {
            createExcelColors();
        } else if (type == GRAYSCALE) {
            createGrayscaleColors();
        } else if (type == REDGREEN) {
            createRedGreenColors();
        } else if (type == REDBLACKGREEN) {
            createRedBlackGreenColors();
        } else if (type == RAINBOW) {
            createRainbowColors();
        } else if (type == WATER) {
            createWaterColors();
        } else {
            throw new RuntimeException("Unknown colormodel '" + type + "'");
        }
    }

    /**
     * Returns the type of color model stored in the instance.
     *
     * @return	The type of the color model.
     */
    public int getType() {
        return type;
    }

    /**
     * Returns the number of colors stored in this color model. The number of
     * colors is dependent on the color model chosen, so this value needs to be
     * checked before retrieving colors.
     *
     * @return	The number of colors stored in the color model.
     */
    public int getNbColors() {
        return colors.length;
    }

    /**
     * Returns a color at the given index. The index should be larger than 0 and
     * smaller than {@link Colormap#getNrColors()}-1.
     *
     * @param index	The index where the color is located.
     * @return	The color at the given index represented in integer format.
     * @throws IndexOutOfBoundsException Thrown when the index is out of bounds.
     */
    public Color getColor(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= colors.length) {
            throw new IndexOutOfBoundsException("Index of color out of bounds");
        }

        return colors[index];
    }

    /**
     * Returns a color for a double value. The value should be between 0 and 1.
     *
     * @param value	The value of the color.
     * @return	The color corresponding to the value.
     */
    public Color getColor(double value) {
        if (value < 0.0) {
            value = 0.0;
        }
        if (value > 1.0) {
            value = 1.0;
        }
        int index = (int) Math.round(value * (colors.length - 1));
        return colors[index];
    }

    private void createJetColors() {
        // allocate and initialize the colors-array
        colors = new Color[256];

        // create the colors
        double r = 0.;
        double g = 0.;
        double b = 0.;

        int n = colors.length / 4;
        for (int i = 0; i < colors.length; ++i) {
            if (i < n / 2.) {
                r = 0.;
                g = 0.;
                b = 0.5 + (double) i / n;
            } else if (i >= n / 2. && i < 3. * n / 2.) {
                r = 0.;
                g = (double) i / n - 0.5;
                b = 1.;
            } else if (i >= 3. * n / 2. && i < 5. * n / 2.) {
                r = (double) i / n - 1.5;
                g = 1.;
                b = 1. - (double) i / n + 1.5;
            } else if (i >= 5. * n / 2. && i < 7. * n / 2.) {
                r = 1.;
                g = 1. - (double) i / n + 2.5;
                b = 0.;
            } else if (i >= 7. * n / 2.) {
                r = 1. - (double) i / n + 3.5;
                g = 0.;
                b = 0.;
            }

            colors[i] = new Color((int) (r * 255), (int) (g * 255), (int) (b * 255));
        }
    }

    private void createHSVColors() {
        // allocate and initialize the colors-array
        colors = new Color[256];

        // create the colors
        double r = 0.;
        double g = 0.;
        double b = 0.;

        for (int i = 0; i < colors.length; ++i) {
            double h = (6. / (double) colors.length) * (double) i;
            switch ((int) h) {
                case 0:
                    r = 1.;
                    g = h - (int) h;
                    b = 0.;
                    break;
                case 1:
                    r = 1. - (h - (int) h);
                    g = 1.;
                    b = 0.;
                    break;
                case 2:
                    r = 0.;
                    g = 1.;
                    b = h - (int) h;
                    break;
                case 3:
                    r = 0.;
                    g = 1. - (h - (int) h);
                    b = 1.;
                    break;
                case 4:
                    r = h - (int) h;
                    g = 0.;
                    b = 1.;
                    break;
                case 5:
                    r = 1.;
                    g = 0.;
                    b = 1. - (h - (int) h);
                    break;
            }

            colors[i] = new Color((int) (r * 255), (int) (g * 255), (int) (b * 255));
        }
    }

    private void createHeatColors() {
        // allocate and initialize the colors-array
        colors = new Color[256];

        // create the colors
        int n = (int) (3. / 8. * colors.length);
        for (int i = 0; i < colors.length; ++i) {
            double r = (1. / n) * (i + 1);
            double g = 0.;
            double b = 0.;

            if (i >= n) {
                r = 1.;
                g = (1. / n) * (i + 1 - n);
                b = 0.;
            }
            if (i >= 2 * n) {
                r = 1.;
                g = 1.;
                b = 1. / (colors.length - 2 * n) * (i + 1 - 2 * n);
            }

            colors[i] = new Color((int) (r * 255), (int) (g * 255), (int) (b * 255));
        }
    }

    private void createExcelColors() {
        // http://www.geocities.com/davemcritchie/excel/colors.htm
        colors = new Color[]{
            new Color(255, 0, 0),
            new Color(0, 255, 0),
            new Color(0, 0, 255),
            new Color(255, 255, 0),
            new Color(255, 0, 255),
            new Color(0, 255, 255),
            new Color(128, 0, 0),
            new Color(0, 128, 0),
            new Color(0, 0, 128),
            new Color(128, 128, 0),
            new Color(128, 0, 128),
            new Color(0, 128, 128),
            new Color(192, 192, 192),
            new Color(128, 128, 128),
            new Color(153, 153, 255),
            new Color(153, 51, 102),
            new Color(255, 255, 204),
            new Color(204, 255, 255),
            new Color(102, 0, 102),
            new Color(255, 128, 128),
            new Color(0, 102, 204),
            new Color(204, 204, 255),
            new Color(0, 0, 128),
            new Color(255, 0, 255),
            new Color(255, 255, 0),
            new Color(0, 255, 255),
            new Color(128, 0, 128),
            new Color(128, 0, 0),
            new Color(0, 128, 128),
            new Color(0, 0, 255),
            new Color(0, 204, 255),
            new Color(204, 255, 255),
            new Color(204, 255, 204),
            new Color(255, 255, 153),
            new Color(153, 204, 255),
            new Color(255, 153, 204),
            new Color(204, 153, 255),
            new Color(255, 204, 153),
            new Color(51, 102, 255),
            new Color(51, 204, 204),
            new Color(153, 204, 0),
            new Color(255, 204, 0),
            new Color(255, 153, 0),
            new Color(255, 102, 0),
            new Color(102, 102, 153),
            new Color(150, 150, 150),
            new Color(0, 51, 102),
            new Color(51, 153, 102),
            new Color(0, 51, 0),
            new Color(51, 51, 0),
            new Color(153, 51, 0),
            new Color(153, 51, 102),
            new Color(51, 51, 153),
            new Color(51, 51, 51)
        };
    }

    private void createGrayscaleColors() {
        // allocate and initialize the colors-array
        colors = new Color[256];
        for (int i = 0; i < colors.length; ++i) {
            colors[i] = new Color(i, i, i);
        }
    }

    private void createRedGreenColors() {
        colors = new Color[256];

        double half = colors.length / 2.;
        for (int i = 0; i <= half; ++i) {
            colors[i] = new Color(255, (int) ((i / half) * 255), (int) ((i / half) * 255));
        }
        for (int i = (int) half + 1; i < colors.length; ++i) {
            colors[i] = new Color(255 - (int) (((i - half) / half) * 255), 255, 255 - (int) (((i - half) / half) * 255));
        }
    }

    private void createRedBlackGreenColors() {
        colors = new Color[256];

        double half = colors.length / 2.;
        for (int i = 0; i <= half; ++i) {
            colors[i] = new Color(255 - (int) (((i) / half) * 255), 0, 0);
        }
        for (int i = (int) half + 1; i < colors.length; ++i) {
            colors[i] = new Color(0, (int) (((i - half) / half) * 255), 0);
        }
    }

    private void createRainbowColors() {
        colors = new Color[256];

        for (int i = 0; i < colors.length; ++i) {
            if (i <= 29) {
                colors[i] = new Color((int) (129.36 - i * 4.36), 0, 255);
            } else if (i <= 86) {
                colors[i] = new Color(0, (int) (-133.54 + i * 4.52), 255);
            } else if (i <= 141) {
                colors[i] = new Color(0, 255, (int) (665.83 - i * 4.72));
            } else if (i <= 199) {
                colors[i] = new Color((int) (-635.26 + i * 4.47), 255, 0);
            } else {
                colors[i] = new Color(255, (int) (1166.81 - i * 4.57), 0);
            }
        }
    }

    private void createWaveColors() {
        colors = new Color[256];

        for (int i = 0; i < colors.length; ++i) {
            colors[i] = new Color(
                    (int) ((Math.sin(((double) i / 40 - 3.2)) + 1) * 128),
                    (int) ((1 - Math.sin((i / 2.55 - 3.1))) * 70 + 30),
                    (int) ((1 - Math.sin(((double) i / 40 - 3.1))) * 128)
            );
        }
    }
    
    private void createWaterColors() {
        // allocate and initialize the colors-array
        colors = new Color[256];
        for (int i = 0; i < colors.length; ++i) {
            colors[i] = new Color(i, i, 255);
        }
    }


    
}
