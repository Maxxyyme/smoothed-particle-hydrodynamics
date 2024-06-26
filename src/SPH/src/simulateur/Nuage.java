/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simulateur;

import colormap.Colormap;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author mmunier
 */
public class Nuage {

    private ArrayList<Particule> listeParticule;

    public Nuage() {
        this.listeParticule = new ArrayList<Particule>();
    }

    public Nuage(int colonnes, int lignes) {
        this.listeParticule = new ArrayList<Particule>();
        for (int k = 0; k < colonnes; k++) {
            for (int j = 0; j < lignes; j++) {
                double modulo = (double) (Math.floorMod(j,2));
                int xpart = (int) (250 + (k + modulo /2) * (2.5*Simulation.RAYON) + (Math.random() - 0.5) * 2);
                int ypart = (int) (50 + j * (2.5 * Simulation.RAYON) + (Math.random() - 0.5) * 2);
                Particule uneParticule = new Particule(xpart, ypart);
                this.listeParticule.add(uneParticule);
            }
        }
    }

    //Méthode
    public void ajouterParticule(Particule p) {
        this.listeParticule.add(p);
    }

    public void majNuage() {
        for (Particule p : this.listeParticule) {
            p.calculAcceleration();
        }
        this.gestionCollisionEntreParticule();
        for (Particule p : this.listeParticule) {
            p.integrationEuler(Simulation.PAS_INTEGRATION);
            p.gestionCollisionsBord();
        }
    }

    public void gestionCollisionEntreParticule() {
        int n = this.listeParticule.size();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    Particule pi = this.listeParticule.get(i);
                    Particule pj = this.listeParticule.get(j);
                    double distance = pi.distance(pj);
                    if (distance < 2 * Simulation.RAYON) {
                        double ux = (pj.getRx() - pi.getRx()) / distance;
                        double uy = (pj.getRy() - pi.getRy()) / distance;

                        double fx = (-Simulation.RAIDEUR_COLLISION / Simulation.MASSE) * (2 * Simulation.RAYON - distance) * ux;
                        double fy = (-Simulation.RAIDEUR_COLLISION / Simulation.MASSE) * (2 * Simulation.RAYON - distance) * uy;

                        pi.addAx(fx);
                        pi.addAy(fy);

                    }
                }
            }
        }
    }

    public void renduNuage(Graphics2D contexte) {
        contexte.setColor(Color.RED);
        for (Particule p : this.listeParticule) {
            Colormap colormap = new Colormap(8);
            contexte.setColor(colormap.getColor(p.energieCinetique()));
            contexte.fillOval((int) (p.getRx() - Simulation.RAYON), (int) (p.getRy() - Simulation.RAYON), (int) (2 * Simulation.RAYON), (int) (2 * Simulation.RAYON));
        }
    }

}
