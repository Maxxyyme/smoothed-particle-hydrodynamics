/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package simulateur;

import FenetreGraphique.FenetreGraphique;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author guillaume.laurent
 */
public class TestNoyau {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        System.out.println("W(0) = " + Noyau.valeur(0));
        System.out.println("W(h) = " + Noyau.valeur(Simulation.RAYON));
        System.out.println("W(2h) = " + Noyau.valeur(2 * Simulation.RAYON));
        System.out.println("Int(Int(W)) = " + Noyau.INTEGRALE);

        FenetreGraphique fenetre = new FenetreGraphique("Noyau de lissage", 300, 300);
        Graphics2D contexte = fenetre.getGraphics2D();

        for (double d = 0; d < 2 * Simulation.RAYON; d += 0.01) {
            double v = Noyau.valeur(d);
            contexte.setColor(Color.BLUE);
            contexte.fillRect((int) (20 * d), (int) (150 - 3000 * v), 1, 1);

            double g = Noyau.derivee(d);
            contexte.setColor(Color.RED);
            contexte.fillRect((int) (20 * d), (int) (150 - 3000 * g), 1, 1);

            g = (Noyau.valeur(d + 0.1) - Noyau.valeur(d)) / 0.1;
            contexte.setColor(Color.GREEN);
            contexte.fillRect((int) (20 * d), (int) (150 - 3000 * g), 1, 1);
        }
        fenetre.actualiser();
    }

}
