package com.epsi.tower_defense;

/**
 * Classe Ennemi
 *
 */
public class Ennemi {
	/** Le nom */
    String nom;
    
    /** la vitesse */
    int vitesse;
    
    /** les pv */
    int pv;
    
    /** les dégats */
    int degat;
    
    /** l'argent gagné lorsque l'ennemi est tué */
    int prix;

    int coorX = 0;
    int coorY = 0;

    char direction;

    /**
     * Constructeur d'un ennemi
     * @param nom son nom
     * @param vitesse sa vitesse
     * @param pv ses pv
     * @param degat ses degats
     * @param prix son prix
     */
    public Ennemi (String nom, int vitesse, int pv, int degat, int prix, int coorX, int coorY, char direction){
        this.nom = nom;
        this.vitesse = vitesse;
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
    	return pv <= 0;
    }
      
}
