package com.epsi.tower_defense;

/**
 * Classe Ennemi
 *
 */
public class Ennemi {
    
    /** les pv */
    private int pv;
    
    /** les dégats */
    private int degat;
    
    /** l'argent gagné lorsque l'ennemi est tué */
    private int prix;

    int coorX = 0;
    int coorY = 0;

    private Boolean vivant = true;

    int sprite = 1;

    char direction;

    /**
     * Constructeur d'un ennemi
     * @param nom son nom
     * @param vitesse sa vitesse
     * @param pv ses pv
     * @param degat ses degats
     * @param prix son prix
     */
    public Ennemi ( int pv, int degat, int prix, int coorX, int coorY, char direction){

        this.pv = pv;
        this.degat = degat;
        this.prix = prix;
        this.coorX = coorX;
        this.coorY = coorY;
        this.direction = direction;


    }
    

    /**
     * Faire perdre de la vie à un ennemi et renvoie un boolean pour savoir si l'ennemi a encore de la vie
     * @param degat
     * @return True si l'ennemi doit mourir
     */
    public Boolean perdrePV(int degat){
    	this.pv -= degat;
    	if(pv <= 0){
    		vivant = false;
    	}
    	return pv <= 0;
    }

	public int getPv() {
		return pv;
	}


	public int getDegat() {
		return degat;
	}


	public int getPrix() {
		return prix;
	}


	public int getCoorX() {
		return coorX;
	}


	public int getCoorY() {
		return coorY;
	}


	public Boolean getVivant() {
		return vivant;
	}
	
	public void setVivant(Boolean vivant){
		this.vivant = vivant;
	}


	public int getSprite() {
		return sprite;
	}


	public char getDirection() {
		return direction;
	}
    
    
    
      
}
