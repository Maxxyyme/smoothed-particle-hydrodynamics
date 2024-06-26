package simulateur;

import java.awt.Color;
import java.awt.Graphics2D;

public class Noyau {

    final public static double INTEGRALE = 14.0 * Math.PI * Simulation.RAYON * Simulation.RAYON / 30.0;
   
    public static double valeur(double distance) {
        double q = distance / Simulation.RAYON;
        if (q < 1.0) {
            return ((2 - q) * (2 - q) * (2 - q) - 4 * (1 - q) * (1 - q) * (1 - q)) / 6.0 / INTEGRALE;
        } else if (q < 2.0) {
            return (2 - q) * (2 - q) * (2 - q) / 6.0 / INTEGRALE;
        } else {
            return 0.0;
        }
    }
    
    public static double derivee(double distance) {
        double q = distance / Simulation.RAYON;
        if (q < 1.0) {
            return q*(1.5*q-2) / INTEGRALE / Simulation.RAYON;
        } else if (q < 2.0) {
            return -0.5 * (2 - q) * (2 -q) / INTEGRALE / Simulation.RAYON;
        } else {
            return 0.0;
        }
    }
      
    public static void rendu(Graphics2D contexte, double x, double y) {
        contexte.setColor(Color.LIGHT_GRAY);
        contexte.drawOval((int) (x - 2 * Simulation.RAYON), (int) (y - 2 * Simulation.RAYON),
                (int) (4 * Simulation.RAYON), (int) (4 * Simulation.RAYON));

        for (double u = -2 * Simulation.RAYON; u < 2 * Simulation.RAYON; u += 1.0) {
            double v = Simulation.RAYON*INTEGRALE*valeur(Math.abs(u));
            contexte.fillRect((int) (x + u), (int) (y - v), 1, 1);
            double g = Math.signum(u)*Simulation.RAYON*INTEGRALE*derivee(Math.abs(u));
            contexte.fillRect((int) (x + u), (int) (y - g), 1, 1);
        }
    }

}
