package com.epsi.tower_defense;

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

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getPortee() {
		return portee;
	}

	public void setPortee(int portee) {
		this.portee = portee;
	}

	public boolean isRalentis() {
		return ralentis;
	}

	public void setRalentis(boolean ralentis) {
		this.ralentis = ralentis;
	}

	public int getDegat() {
		return degat;
	}

	public void setDegat(int degat) {
		this.degat = degat;
	}

	public int getPrix() {
		return prix;
	}

	public void setPrix(int prix) {
		this.prix = prix;
	}

	public int getNiveau() {
		return niveau;
	}

	public void setNiveau(int niveau) {
		this.niveau = niveau;
	}
    
    
}
