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
public class TestNuageHashTable {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FenetreGraphique fenetre = new FenetreGraphique("Simulateur", Simulation.X_MIN + Simulation.X_MAX, Simulation.Y_MIN + Simulation.Y_MAX);

        NuageHashTable nuage = new NuageHashTable(Simulation.NB_LIGNE, Simulation.NB_COLONNE);

        int lignes = 10;
        int colonnes = 10;

        for (int k = 0; k < colonnes; k++) {
            for (int j = 0; j < lignes; j++) {
                double modulo = (double) (Math.floorMod(j, 2));
                int xpart = (int) (250 + (k + modulo / 2) * (2.5 * Simulation.RAYON) + (Math.random() - 0.5) * 2);
                int ypart = (int) (50 + j * (2.5 * Simulation.RAYON) + (Math.random() - 0.5) * 2);
                Particule uneParticule = new Particule(xpart, ypart);
                nuage.ajoutParticule(uneParticule);
            }
        }

        System.out.println(nuage.toString2());
        
        // Ajout de particule la ou on clique
        fenetre.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                Particule nouvelleParticule = new Particule(x, y);
                nuage.ajoutParticule(nouvelleParticule);
                fenetre.repaint();
            }
        });

        for (int k = 0; k < 30000; k++) {

            Simulation.dessinerMurs(fenetre.getGraphics2D());

            nuage.miseAJour(); // On met a jour la table de hachage

            nuage.renduNuage(fenetre.getGraphics2D());

            nuage.majNuageHashTable(); // On met a jour les acceleration et on gère les collisions

            fenetre.actualiser(10e-4);

        }

        fenetre.fermer();
    }

}
