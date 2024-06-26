/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package simulateur;

import FenetreGraphique.FenetreGraphique;
import java.awt.Color;

/**
 *
 * @author mmunier
 */
public class TestParticule {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        FenetreGraphique fenetre = new FenetreGraphique("Simulateur", Simulation.X_MIN + Simulation.X_MAX, Simulation.Y_MIN + Simulation.Y_MAX);

        Particule p1 = new Particule(270, 26);
        p1.setVx(0);

        for (int k = 0; k < 10000; k++) {

            Simulation.dessinerMurs(fenetre.getGraphics2D());
            p1.rendu(fenetre.getGraphics2D());
            fenetre.actualiser(1e-3);
            System.out.println("t = " + Simulation.PAS_INTEGRATION * k + " secondes,    x = " + p1.getRx() + " pixels, y = " + p1.getRy() + "pixels");
            p1.calculAcceleration();
            p1.integrationEuler(Simulation.PAS_INTEGRATION);
            p1.gestionCollisionsBord();
            System.out.println("Energie cinétique : " + p1.energieCinetique());

        }

        fenetre.fermer();
    }

}
