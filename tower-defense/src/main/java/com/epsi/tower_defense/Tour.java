package com.epsi.tower_defense;

/**
 * Created by Jacques on 09/11/2016.
 */
public class Tour {
    String nom;
    int portee;
    boolean ralentis;
    int degat;
    int prix;
    int niveau;
    
    public Tour(String nom, int portee, boolean ralentis, int degat, int prix, int niveau){
        this.nom = nom;
        this.portee=portee;
        this.ralentis=ralentis;
        this.degat=degat;
        this.prix=prix;
        this.niveau=niveau;
        
    }
}
