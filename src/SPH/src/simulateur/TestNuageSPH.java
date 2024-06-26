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
public class TestNuageSPH {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FenetreGraphique fenetre = new FenetreGraphique("Simulateur", Simulation.X_MIN + Simulation.X_MAX, Simulation.Y_MIN + Simulation.Y_MAX);
        NuageSPH nuage = new NuageSPH(Simulation.NB_LIGNE, Simulation.NB_COLONNE);
        nuage.NuageSPH2(15, 15, 250, 50);

        
        SimulationPanel panel = new SimulationPanel(nuage);
        panel.setVisible(true);
        
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

        while (true) {
            Simulation.dessinerMurs(fenetre.getGraphics2D());
            nuage.renduNuage(fenetre.getGraphics2D());
            fenetre.actualiser(1e-3);
            nuage.majNuageSPH();

        }
    }
}
