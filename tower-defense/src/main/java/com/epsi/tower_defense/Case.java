package com.epsi.tower_defense;
import java.util.ArrayList;

public class Case {
    boolean chemin = false;
    ArrayList<Ennemi> listEnnemis = new ArrayList<>();
    Tour tour = null;
    boolean depart = false;
    boolean arrive = false;
    Case suivante;
    Case precedente;
    int x;
    int y;

    public Case (int x, int y, boolean chemin, boolean depart, boolean arrive){
    	this.x = x;
    	this.y = y;
        this.chemin = chemin;
        this.depart = depart;
        this.arrive = arrive;
    }

	@Override
	public String toString() {
		return "Case [X =" + x +", Y = " + y + "ennemi size = " +listEnnemis.size()+ "]";
	}

	public ArrayList<Ennemi> getListEnnemis() {
		return listEnnemis;
	}

	public void setListEnnemis(ArrayList<Ennemi> listEnnemis) {
		this.listEnnemis = listEnnemis;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isChemin() {
		return chemin;
	}

	public boolean isDepart() {
		return depart;
	}

	public boolean isArrive() {
		return arrive;
	}
	
	
}
