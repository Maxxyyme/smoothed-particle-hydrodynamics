/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package colormap;

import FenetreGraphique.FenetreGraphique;

/**
 *
 * @author guillaume.laurent
 */
public class TestColormap {

    private final static int HEIGHT = 300;
    private final static int WIDTH = 400;

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        FenetreGraphique fenetre = new FenetreGraphique("Colormaps", WIDTH, HEIGHT);

        for (int type = 0; type < 9; type++) {
            
            Colormap colormap = new Colormap(type);

            for (int x = 0; x < WIDTH; x++) {
                fenetre.getGraphics2D().setColor(colormap.getColor(x * 1.0 / WIDTH));
                fenetre.getGraphics2D().drawLine(x, type * HEIGHT / 9, x, (type + 1) * HEIGHT / 9);
            }
        }
        fenetre.actualiser();

    }

}
