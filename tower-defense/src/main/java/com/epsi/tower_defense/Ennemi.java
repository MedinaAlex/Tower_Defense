/**
 * Created by Jacques on 09/11/2016.
 */
package com.epsi.tower_defense;
public class Ennemi {
    String nom;
    int vitesse;
    int pv;
    int degat;
    int prix;
    public void Ennemi (String nom, int vitesse, int pv, int degat, int prix){
        this.nom = nom;
        this.vitesse = vitesse;
        this.pv = pv;
        this.degat = degat;
        this.prix = prix;
    }
}