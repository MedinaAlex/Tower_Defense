package com.epsi.tower_defense;

public class Tour {
	
	/** Son nom */
    String nom;
    
    /** sa portée */
    int portee;
    
    /** ralentis */
    boolean ralentis;
    
    /** ses dégats */
    int degat;
    
    /** son prix */
    int prix;
    
    /** son niveau */
    int niveau;

    boolean attaque = false;
    
    /**
     * Constructeur d'une tour
     * @param nom son nom
     * @param portee sa portée
     * @param ralentis ralentis
     * @param degat ses dégats
     * @param prix son prix
     * @param niveau son niveau
     */
    public Tour(String nom, int portee, boolean ralentis, int degat, int prix, int niveau){
        this.nom = nom;
        this.portee = portee;
        this.ralentis = ralentis;
        this.degat = degat;
        this.prix = prix;
        this.niveau = niveau;
    }
    
    /**
     * Permet d'augmenter d'un niveau la tour
     */
    public void upgrade(){
    	niveau++;
    	if(niveau % 5 == 0){
    		portee++;
    		ralentis = true;	
    	}
    	
    	degat += niveau + 2;
    	prix += 20;
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
