/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package simulateur;

import FenetreGraphique.FenetreGraphique;
import java.awt.Color;

/**
 *
 * @author guillaume.laurent
 */
public class TestSimulation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        FenetreGraphique fenetre = new FenetreGraphique("Simulateur", Simulation.X_MIN + Simulation.X_MAX, Simulation.Y_MIN + Simulation.Y_MAX);

        double x = Simulation.X_MIN;

        for (int k = 0; k < 5000; k++) {

            Simulation.dessinerMurs(fenetre.getGraphics2D());
            fenetre.getGraphics2D().setColor(Color.RED);
            fenetre.getGraphics2D().fillOval((int) (x - Simulation.RAYON), (int) (100 - Simulation.RAYON), (int) (2 * Simulation.RAYON), (int) (2 * Simulation.RAYON));
            fenetre.actualiser();
            System.out.println("t = " + Simulation.PAS_INTEGRATION * k + " secondes,    x = " + x + " pixels");
                        
            x += 0.1;
            
        }

        System.out.println("Abscisse minimum = " + Simulation.X_MIN + " pixels");
        System.out.println("Abscisse maximum = " + Simulation.X_MAX + " pixels");
        System.out.println("Ordonnee minimum = " + Simulation.Y_MIN + " pixels");
        System.out.println("Ordonnee maximum = " + Simulation.Y_MAX + " pixels");
        System.out.println("Gravite = " + Simulation.GRAVITE + " pixels/secondes^2");
        System.out.println("Celerite = " + Simulation.CELERITE + " pixels/secondes");
        System.out.println("Pas d'integration = " + Simulation.PAS_INTEGRATION + " secondes");

        fenetre.fermer();

    }

}
