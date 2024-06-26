/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simulateur;

import colormap.Colormap;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import static simulateur.Noyau.valeur;

/**
 *
 * @author mmunier
 */
public class NuageHashTable {

    private ArrayList<Particule>[] cellules;

    public NuageHashTable(int nbLigne, int nbColonne) {
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

    public void gestionCollisionEntreParticule() {
        int n = this.cellules.length;
        for (int i = 0; i < n; i++) {
            for (Particule p : cellules[i]) {
                int h = p.hashCode();
                for (int j = -1; j < 2; j++) {
                    for (int k = -1; k < 2; k++) {
                        int cases = h + j + k * Simulation.NB_COLONNE;
                        ArrayList<Particule> voisins = this.getParticules(cases);
                        for (Particule q : voisins) {
                            if (!q.equals(p)) {
                                Particule pk = p;
                                Particule pj = q;
                                double distance = pk.distance(pj);
                                if (distance < 2 * Simulation.RAYON) {
                                    double ux = (pj.getRx() - pk.getRx()) / distance;
                                    double uy = (pj.getRy() - pk.getRy()) / distance;

                                    double fx = (-Simulation.RAIDEUR_COLLISION / Simulation.MASSE) * (2 * Simulation.RAYON - distance) * ux;
                                    double fy = (-Simulation.RAIDEUR_COLLISION / Simulation.MASSE) * (2 * Simulation.RAYON - distance) * uy;

                                    pk.addAx(fx);

                                    pk.addAy(fy);

                                }

                            }
                        }
                    }
                }

            }
        }
    }

    public void majNuageHashTable() {
        for (int i = 0; i < cellules.length; i++) {
            for (Particule p : cellules[i]) {
                p.calculAcceleration();

            }

        }
        this.gestionCollisionEntreParticule();
        for (int i = 0; i < cellules.length; i++) {
            for (Particule p : cellules[i]) {

                p.integrationEuler(Simulation.PAS_INTEGRATION);
                p.gestionCollisionsBord();
            }

        }
    }

    public void renduNuage(Graphics2D contexte) {
        contexte.setColor(Color.RED);
        for (int i = 0; i < cellules.length; i++) {
            for (Particule p : cellules[i]) {
                Colormap colormap = new Colormap(8);
                contexte.setColor(colormap.getColor(p.energieCinetique()));
                contexte.fillOval((int) (p.getRx() - Simulation.RAYON), (int) (p.getRy() - Simulation.RAYON), (int) (2 * Simulation.RAYON), (int) (2 * Simulation.RAYON));
            }

        }
    }
}
