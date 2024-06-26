package simulateur;

import java.awt.Color;
import java.awt.Graphics2D;

public class Simulation {

    // Parametres des frontieres
    final static public int X_MIN = 10;
    final static public int X_MAX = 530;
    final static public int Y_MIN = 10;
    final static public int Y_MAX = 330;


    // Parametres des particules
    final static public double RAYON = 5;
    final static public double MASSE = 0.002;
    final static public double AMORTISSEMENT_REBOND = 0.7;
    final static public double FROTTEMENT_AIR = 1e-4;

    // Parametres globaux
    final static public double GRAVITE = 9.8;
    final static public double CELERITE = 10.0 * Math.sqrt(2 * GRAVITE * (Y_MAX - Y_MIN));
    final static public double PAS_INTEGRATION = RAYON / CELERITE;
    
    final static public int NB_COLONNE = (X_MAX-X_MIN)/(int)(2*RAYON);

    
    // Parametres du modele de collision simple
    final static public double RAIDEUR_COLLISION = 0.5;

    // Parametres de la methode SPH
    final static public double DENSITE_MAX = Simulation.MASSE / Simulation.RAYON / Simulation.RAYON / Math.PI;
    final static public double GAMMA = 7.0;
    final static public double VISCOSITE = 5000000000.0;
    final static public int NB_LIGNE = (Y_MAX-Y_MIN)/(int)(2*RAYON);

    static public void dessinerMurs(Graphics2D contexte) {
        contexte.setColor(Color.LIGHT_GRAY);
        contexte.fillRect(0, 0, X_MIN + X_MAX, Y_MIN + Y_MAX);
        contexte.setColor(Color.BLACK);
        contexte.fillRect(X_MIN, Y_MIN, X_MAX - X_MIN, Y_MAX - Y_MIN);
    }

}
