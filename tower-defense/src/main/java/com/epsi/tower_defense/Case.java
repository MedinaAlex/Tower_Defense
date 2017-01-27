package com.epsi.tower_defense;
import java.util.ArrayList;

/**
 * La classe Case.
 */
public class Case {
    
    /** Défini si la case est un chemin. */
    boolean chemin = false;
    
    /** La liste d'ennemi sur la case */
    ArrayList<Ennemi> listEnnemis = new ArrayList<>();
    
    /** la tour présente sur la case. */
    Tour tour = null;
    
    /** Défini si la case est le départ */
    boolean depart = false;
    
    /** Défini si la case est une arrivé */
    boolean arrive = false;

	boolean aiguilleur;

	char direction;
    
    /** la case suivante */
    Case suivante;
    
    /** The precedente. */
    Case precedente;
    
    /** la coordonnée x. */
    int x;
    
    /** la coordonnée y. */
    int y;

    /**
     * Constructeur d'une case.
     *
     * @param x la coordonnée x
     * @param y la coordonnée y
     * @param chemin le chemin
     * @param depart le depart
     * @param arrive le arrive
     */
    public Case (int x, int y, boolean chemin, boolean depart, boolean arrive, boolean aiguilleur, char direction){
    	this.x = x;
    	this.y = y;
        this.chemin = chemin;
        this.depart = depart;
        this.arrive = arrive;
        this.aiguilleur = aiguilleur;
        this.direction = direction;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Case [X = " + x +", Y = " + y + ", ennemi size = " +listEnnemis.size()+ "]";
	}

	/**
	 * Récupère la liste des ennemis
	 *
	 * @return la liste des ennemis
	 */
	public ArrayList<Ennemi> getListEnnemis() {
		return listEnnemis;
	}

	/**
	 * Défini la liste des ennemis
	 *
	 * @param listEnnemis la nouvelle liste
	 */
	public void setListEnnemis(ArrayList<Ennemi> listEnnemis) {
		this.listEnnemis = listEnnemis;
	}

	/**
	 * Récupère la tour.
	 *
	 * @return la tour
	 */
	public Tour getTour() {
		return tour;
	}

	/**
	 * Défini la tour.
	 *
	 * @param tour la nouvelle tour
	 */
	public void setTour(Tour tour) {
		this.tour = tour;
	}

	/**
	 * Récupère la coordonnée x.
	 *
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Défini la coordonnée x.
	 *
	 * @param x la nouvelle coordonnée x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 *  Récupère la coordonnée y.
	 *
	 * @return y
	 */
	public int getY() {
		return y;
	}

	/**
	 *  Défini la coordonnée y.
	 *
	 * @param y  la coordonnée y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 *  Récupère le boolean chemin
	 *
	 * @return true, si c'est un chemin
	 */
	public boolean isChemin() {
		return chemin;
	}

	/**
	 *  Récupère le boolean depart
	 *
	 * @return true, si c'est un depart
	 */
	public boolean isDepart() {
		return depart;
	}

	/**
	 *  Récupère le boolean arrive
	 *
	 * @return true, si c'est une arrivé
	 */
	public boolean isArrive() {
		return arrive;
	}
	
	
}
