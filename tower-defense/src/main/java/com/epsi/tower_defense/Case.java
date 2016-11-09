package com.epsi.tower_defense;
import java.util.ArrayList;

public class Case {
    boolean chemin = false;
    ArrayList<Ennemi> listEnnemis = new ArrayList<Ennemi>();
    Tour tour = null;
    boolean depart = false;
    boolean arrive = false;

    public Case (boolean chemin, boolean depart, boolean arrive){
        this.chemin = chemin;
        this.depart = depart;
        this.arrive = arrive;
    }
}