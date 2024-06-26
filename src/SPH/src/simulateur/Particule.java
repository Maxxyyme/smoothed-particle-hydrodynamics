/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simulateur;

import FenetreGraphique.FenetreGraphique;
import java.awt.Color;
import java.awt.Graphics2D;
import static simulateur.Simulation.X_MAX;
import static simulateur.Simulation.X_MIN;
import static simulateur.Simulation.Y_MAX;
import static simulateur.Simulation.Y_MIN;

/**
 *
 * @author mmunier
 */
public class Particule {

    //Attributs
    private double m; // masse m 
    private double h; // rayon h
    private double rx; //coordonnée position selon x
    private double ry; // coordonnée position selon y 
    private double vx;
    private double vy;
    private double ax;
    private double ay;
    private double densite;
    private double pression;

    //Constructeur
    public Particule(double rx, double ry) {
        this.m = Simulation.MASSE;
        this.h = Simulation.RAYON;
        this.rx = rx;
        this.ry = ry;
        this.vx = 0;
        this.vy = 0;
        this.ax = 0;
        this.ay = 0;
        this.densite = 0;
        this.pression = 0;
    }

    //Getter & Setter
    public double getPression() {
        return pression;
    }

    public void setPression(double pression) {
        this.pression = pression;
    }

    public double getDensite() {
        return densite;
    }

    public void setDensite(double densite) {
        this.densite = densite;
    }

    public double getM() {
        return m;
    }

    public void setM(double m) {
        this.m = m;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getRx() {
        return rx;
    }

    public void setRx(double rx) {
        this.rx = rx;
    }

    public double getRy() {
        return ry;
    }

    public void setRy(double ry) {
        this.ry = ry;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public double getAx() {
        return ax;
    }

    public void setAx(double ax) {
        this.ax = ax;
    }

    public void addAx(double ax) {
        this.ax += ax;
    }

    public double getAy() {
        return ay;
    }

    public void setAy(double ay) {
        this.ay = ay;
    }

    public void addAy(double ay) {
        this.ay += ay;
    }

    //Méthodes
    public void calculAcceleration() {
        double fAirX = -((Simulation.FROTTEMENT_AIR) / this.m) * this.vx;
        double fAirY = Simulation.GRAVITE - ((Simulation.FROTTEMENT_AIR) / this.m) * this.vy;

        this.ax = fAirX;
        this.ay = fAirY;
    }

    public void integrationEuler(double tau) {
        this.vx = this.vx + tau * this.ax;
        this.vy = this.vy + tau * this.ay;
        this.rx = this.rx + tau * this.vx;
        this.ry = this.ry + tau * this.vy;
    }

    public void gestionCollisionsBord() {
        if (rx + h > Simulation.X_MAX) {
            this.vx = -Simulation.AMORTISSEMENT_REBOND * Math.abs(this.vx);
            this.rx = Simulation.X_MAX - h;
        }
        if (rx - h < Simulation.X_MIN) {
            this.vx = Simulation.AMORTISSEMENT_REBOND * Math.abs(this.vx);
            this.rx = Simulation.X_MIN + h;
        }
        if (ry + h > Simulation.Y_MAX) {
            this.vy = -Simulation.AMORTISSEMENT_REBOND * Math.abs(this.vy);
            this.ry = Simulation.Y_MAX - h;
        }
        if (ry - h < Simulation.Y_MIN) {
            this.vy = Simulation.AMORTISSEMENT_REBOND * Math.abs(this.vy);
            this.ry = Simulation.Y_MIN + h;
        }
    }

    public int hashCode() {
        int colonne = (int) (this.rx / (2 * h));
        int ligne = (int) (this.ry / (2 * h));
        return colonne + ligne * Simulation.NB_COLONNE;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!obj.getClass().equals(this.getClass())) {
            return false;
        }
        Particule p = (Particule) obj;
        if (Double.doubleToLongBits(this.rx) != Double.doubleToLongBits(p.rx)) {
            return false;
        }
        if (Double.doubleToLongBits(this.ry) != Double.doubleToLongBits(p.ry)) {
            return false;
        }
        if (Double.doubleToLongBits(this.vx) != Double.doubleToLongBits(p.vx)) {
            return false;
        }
        if (Double.doubleToLongBits(this.vy) != Double.doubleToLongBits(p.vy)) {
            return false;
        }
        if (Double.doubleToLongBits(this.ax) != Double.doubleToLongBits(p.ax)) {
            return false;
        }
        if (Double.doubleToLongBits(this.ay) != Double.doubleToLongBits(p.ay)) {
            return false;
        }
        return true;
    }

    public double distance(Particule p2) {
        return Math.sqrt((this.rx - p2.getRx()) * (this.rx - p2.getRx()) + (this.ry - p2.getRy()) * (this.ry - p2.getRy()));
    }

    public double energieCinetique() {
        return 0.5 * this.m * (this.vx * this.vx + this.vy * this.vy);
    }

    public void rendu(Graphics2D contexte) {
        contexte.setColor(Color.RED);
        contexte.fillOval((int) (this.rx - Simulation.RAYON), (int) (this.ry - Simulation.RAYON), (int) (2 * Simulation.RAYON), (int) (2 * Simulation.RAYON));
    }

}
