/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simulateur;

import colormap.Colormap;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import static simulateur.Noyau.valeur;

/**
 *
 * @author mmunier
 */
public class NuageSPH {

    private ArrayList<Particule>[] cellules;
    private double valueGravite;
    private int color;

    public NuageSPH(int nbLigne, int nbColonne) {
        this.valueGravite = Simulation.GRAVITE;
        this.color = 8;
        this.cellules = new ArrayList[nbLigne * nbColonne];
        for (int i = 0; i < this.cellules.length; i++) {
            cellules[i] = new ArrayList<Particule>();
        }
    }

    @Override
    public String toString() {
        return "NuageHashTable{" + "cellules=" + cellules + '}';
    }

    public String toString2() {
        String texte = "";
        for (int i = 0; i < this.cellules.length; i++) {
            texte += cellules[i] + ";";
        }
        return texte;
    }

    public void NuageSPH2(int colonnes, int lignes, double xdepart, double ydepart) {
        for (int k = 0; k < colonnes; k++) {
            for (int j = 0; j < lignes; j++) {
                double modulo = (double) (Math.floorMod(j, 2));
                int xpart = (int) (xdepart + (k + modulo / 2) * (2.5 * Simulation.RAYON) + (Math.random() - 0.5) * 2);
                int ypart = (int) (ydepart + j * (2.5 * Simulation.RAYON) + (Math.random() - 0.5) * 2);
                Particule uneParticule = new Particule(xpart, ypart);
                this.ajoutParticule(uneParticule);
            }
        }
        this.miseAJour();

    }

    public void setValueGravite(double valueGravite) {
        this.valueGravite = valueGravite;
    }

    public void setColor(int color) {
        this.color = color;
    }
    
    

    public void ajoutParticule(Particule p) {
        int hsh = p.hashCode() % this.cellules.length;
        if (hsh < 0) {
            hsh += this.cellules.length; // Corrige le cas où le modulo est négatif
        }
        this.cellules[(hsh + cellules.length) % cellules.length].add(p);
    }

    public ArrayList<Particule> getParticules(int hashCode) {
        return this.cellules[(hashCode + cellules.length) % cellules.length];
    }

    public void miseAJour() {
        for (int j = 0; j < this.cellules.length; j++) {
            int n = this.cellules[j].size();
            int i = 0;
            while (i < n) {
                Particule p = this.cellules[j].get(i);
                if (p.hashCode() != j) {
                    this.cellules[j].remove(i);
                    this.ajoutParticule(p);
                    n--;
                } else {
                    i++;
                }
            }
        }

    }

    public void calculeDensite(Particule p) {
        double densite = 0;
        int h = p.hashCode();
        for (int k = -1; k < 2; k++) {
            for (int j = -1; j < 2; j++) {
                int cases = h + j + k * Simulation.NB_COLONNE;
                for (Particule q : this.getParticules(cases)) {
                    densite = densite + Simulation.MASSE * Noyau.valeur(p.distance(q));
                }
            }
        }
        p.setDensite(densite);
    }

    public void calculerPression(Particule p) {
        double puissance = Math.pow((p.getDensite() / Simulation.DENSITE_MAX), Simulation.GAMMA);
        p.setPression((Simulation.DENSITE_MAX * Simulation.CELERITE * Simulation.CELERITE / Simulation.GAMMA) * (puissance - 1));
    }

    public void accelerationPression(Particule p) {
        double fx = 0;
        double fy = Simulation.MASSE * this.valueGravite;
        int hsh = p.hashCode();
        for (int k = -1; k < 2; k++) {
            for (int j = -1; j < 2; j++) {
                int cases = hsh + j + k * Simulation.NB_COLONNE;
                for (Particule q : this.getParticules(cases)) {
                    if (!p.equals(q)) {

                        double d = p.distance(q);
                        double gradient = Noyau.derivee(d);
                        double ux = -(q.getRx() - p.getRx()) / d;
                        double uy = -(q.getRy() - p.getRy()) / d;
                        double deltaVx = (p.getVx() - q.getVx()) * ux;
                        double deltaVy = (p.getVy() - q.getVy()) * uy;

                        // Force de pression
                        double rapport = (p.getPression() / (p.getDensite() * p.getDensite())) + (q.getPression() / (q.getDensite() * q.getDensite()));
                        fx = fx - Simulation.MASSE * Simulation.MASSE * rapport * gradient * ux;
                        fy = fy - Simulation.MASSE * Simulation.MASSE * rapport * gradient * uy;

                        // Force de viscosité
                        if (deltaVx < 0 && deltaVy < 0) {
                            fx = fx + Simulation.MASSE * Simulation.MASSE * Simulation.VISCOSITE * Simulation.RAYON * Simulation.CELERITE * ((p.getDensite() + q.getDensite()) / 2) * deltaVx * gradient * ux;
                            fy = fy + Simulation.MASSE * Simulation.MASSE * Simulation.VISCOSITE * Simulation.RAYON * Simulation.CELERITE * ((p.getDensite() + q.getDensite()) / 2) * deltaVy * gradient * uy;
                        }

                    }
                }
            }
        }
        p.setAx(fx / Simulation.MASSE);
        p.setAy(fy / Simulation.MASSE);

    }

    public void majNuageSPH() {
        for (int i = 0; i < cellules.length; i++) {
            for (Particule p : cellules[i]) {
                this.calculeDensite(p);
            }
        }
        for (int i = 0; i < cellules.length; i++) {
            for (Particule p : cellules[i]) {
                this.calculerPression(p);
            }
        }
        for (int i = 0; i < cellules.length; i++) {
            for (Particule p : cellules[i]) {
                this.accelerationPression(p);
            }
        }
        for (int i = 0; i < cellules.length; i++) {
            for (Particule p : cellules[i]) {
                p.integrationEuler(Simulation.PAS_INTEGRATION);
            }
        }
        for (int i = 0; i < cellules.length; i++) {
            for (Particule p : cellules[i]) {
                p.gestionCollisionsBord();
            }
        }
        this.miseAJour();
    }

    public void renduNuage(Graphics2D contexte) {
        contexte.setColor(Color.RED);
        for (int i = 0; i < cellules.length; i++) {
            for (Particule p : cellules[i]) {
                Colormap colormap = new Colormap(this.color);
                contexte.setColor(colormap.getColor(p.energieCinetique()));
                contexte.fillOval((int) (p.getRx() - Simulation.RAYON), (int) (p.getRy() - Simulation.RAYON), (int) (2 * Simulation.RAYON), (int) (2 * Simulation.RAYON));
            }

        }
    }
}
