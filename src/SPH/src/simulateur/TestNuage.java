/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package simulateur;

import FenetreGraphique.FenetreGraphique;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author mmunier
 */
public class TestNuage {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FenetreGraphique fenetre = new FenetreGraphique("Simulateur", Simulation.X_MIN + Simulation.X_MAX, Simulation.Y_MIN + Simulation.Y_MAX);

        Nuage n1 = new Nuage(10, 10);
        
        // Ajout de particule la ou on clique
        fenetre.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                Particule nouvelleParticule = new Particule(x, y);
                n1.ajouterParticule(nouvelleParticule);
                fenetre.repaint();
            }
        });

        for (int k = 0; k < 30000; k++) {

            Simulation.dessinerMurs(fenetre.getGraphics2D());

            n1.renduNuage(fenetre.getGraphics2D());

            n1.majNuage();
            fenetre.actualiser(10e-4);

        }

        fenetre.fermer();
    }

}
