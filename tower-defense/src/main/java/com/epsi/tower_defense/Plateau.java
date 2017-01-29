package com.epsi.tower_defense;

import com.sun.xml.internal.bind.v2.TODO;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javax.swing.plaf.SliderUI;

/**
 * Classe Plateau
 *
 */
public class Plateau {
	/** terrain de jeu */
	ArrayList<Case> terrain = new ArrayList<>();
	
	/** liste des chemins */
	ArrayList<Case> casesChemin = new ArrayList<>();

	ArrayList<Ennemi> listEnnemi = new ArrayList<>();
	
	/** taille du terrain */
	int taille;
	
	/** pv du plateau */
	int pv;
	
	/** numéro de vague */
	int vague = 1;

	/** Quantitée d'or */
	int or = 100;


	
	/**
	 * Constructeur du plateau qui prend en paramètre le chemin vers le fichier correspondant au terrain
	 * @param path
	 */
	public Plateau(String path) {

		this.pv = 1;
		//lecture du fichier texte	
		try{
			InputStream ips=new FileInputStream(path); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne, map = "";
			while ((ligne=br.readLine())!=null){
				map += ligne;
			}
			
			br.close();
			
			//Map carré
			taille = (int) Math.sqrt(map.length());
			int x =0 , y = 0;
			
			for(char s: map.toCharArray()){
				int tailleCase = 100;
				Case c;
				switch (s) {
					case 'x':
						c= new Case(x*tailleCase,y*tailleCase,false,false,false,false, 'x');
						casesChemin.add(c);
						break;

					case '3':
						c = new Case(x*tailleCase,y*tailleCase,true, true, false, true, 'D');
						casesChemin.add(c);
						break;
						
					case '4':
						c = new Case(x*tailleCase,y*tailleCase,true, false, true, true, 'x');
						casesChemin.add(c);
						break;
					case '0':
						c = new Case(x*tailleCase,y*tailleCase,false, false, false, false, 'x');
						terrain.add(c);
						break;
					default:

						c = new Case(x*tailleCase,y*tailleCase,true, false, false,true, s);
						casesChemin.add(c);
						break;
				}
				y++;
				if(y == taille){
					x++;
					y = 0;
				}

			}
			determinerChemin();
			//Graph.creerPlateau(terrain);
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
	

	
	/**
	 * Génère de nouveaux ennemis et les ajoutes au plateau
	 */
	public void genererNouveauxEnnemis(){

			Case caseDepart = casesChemin.get(0);

			//TODO: géréer l'affichage hors cadfre en fonction de l'emplacement de la fenêtre
			Ennemi ennemi = new Ennemi("bob", 1, 30 + vague, 20, 10,caseDepart.x , caseDepart.y-100, caseDepart.direction);
			listEnnemi.add(ennemi);

	}
	
	/**
	 * Permet aux tours d'attaquer un ennemi à portée
	 */
	public void attaquer(){


		for (Case caseTerrain:terrain) {
			if(caseTerrain.getTour() != null){
				Tour tour = caseTerrain.getTour();
				tour.attaque = false;
				int nombreTire = 1;
				int coorTourX = caseTerrain.getX() ;
				int coorTourY = caseTerrain.getY();

				for (Ennemi ennemi:listEnnemi) {

					if (!tour.attaque && ennemi.vivant == true) {

						//savoir si les coordonnées de l'ennemi sont dans le cercle
						//racine_carre((x_point - x_centre)² + (y_centre - y_point)) < rayon
						if ((Math.sqrt((Math.pow((ennemi.coorX - coorTourX), 2)) + (Math.pow((coorTourY - ennemi.coorY), 2)))) < tour.getPortee()) {
							nombreTire++;
							ennemi.pv = ennemi.pv - tour.degat;
							tour.attaque=true;
							if (ennemi.pv < 0) {

								ennemi.vivant = false;
								or += ennemi.prix;
							}
						}
					}
				}

			}

		}
		/*ArrayList<Case> reverse =  (ArrayList<Case>) casesChemin.clone();
		Collections.reverse(reverse);
		
		for(Case actuelle: terrain){
			if(actuelle.getTour() != null){
				Tour tour = actuelle.getTour();
				int portee = tour.getPortee();
				
				for(Case caseChemin : reverse){
					int xMoins = actuelle.getX() - portee;
					int yMoins = actuelle.getY() - portee;
					int xPlus = actuelle.getX() + portee;
					int yPlus = actuelle.getY() + portee;
										
					if ((caseChemin.getX() > xMoins &&  caseChemin.getX() < xPlus) 
					 && (caseChemin.getY() > yMoins &&  caseChemin.getY() < yPlus)
					 && (!caseChemin.getListEnnemis().isEmpty())){
						System.out.println(caseChemin.getListEnnemis().get(0).pv);
						
						if(caseChemin.getListEnnemis().get(0).perdrePV(tour.getDegat())){

							//On vérifie si la vague est terminer uniquement après un coup pour ne pas incrémenter en permanance
							boolean vagueTerminee = true;
							for(Case caseC: casesChemin){
								if (!caseC.getListEnnemis().isEmpty()){
									vagueTerminee = false;
									break;

								}
								if(vagueTerminee) {
									vague++;
								}
							}
							or += 20;
							caseChemin.getListEnnemis().remove(0);
							
						}
					}
				}
			}
		}*/
	}
	
	/**
	 * Déplace tous les ennemis d'une case
	 */
	public void deplacerEnnemis(){
		char direction = 'x';
		for (Ennemi ennemi :listEnnemi) {
			if(ennemi.vivant) {


				direction = ennemi.direction;
				for (Case caseC : casesChemin) {

					//on verrifie que la case sur la quels est l'ennemi n'éguille pas
					if (caseC.aiguilleur && (caseC.getX() == ennemi.coorX && caseC.getY() == ennemi.coorY)) {
						//si c'est la base
						if (caseC.arrive) {
							ennemi.vivant = false;
							pv = pv - ennemi.degat;
						} else {
							direction = caseC.direction;
						}


					}


				}
			}

			switch (direction) {
				case 'H':
					ennemi.coorX += 4;
					break;
				case 'B':
					ennemi.coorX += 4;
					break;
				case 'D':
					ennemi.coorY +=4;
					break;
				case 'G':
					ennemi.coorY +=4;
					break;
			}
			ennemi.direction = direction;
			if (ennemi.sprite <4){
				ennemi.sprite++;
			}
			else{
				ennemi.sprite = 1;
			}

		}

		//casesChemin.get(0).listEnnemis = new ArrayList<Ennemi>();

	}



	/**
	 * permet de trier le chemin
	 */
	public void determinerChemin(){
		ArrayList<Case> ordonnee = new ArrayList<>();
		for(Case c: casesChemin){
			if(c.isDepart()){
				ordonnee.add(c);
				casesChemin.remove(c);
				
				break;
			}	
		}
		
		while(casesChemin.size() > 1 ){
			Case actuelle = ordonnee.get(ordonnee.size()-1);			
			ArrayList<Case> voisins = getCasesVoisine(actuelle);
			
			if(voisins.size() == 2){
				Case precedente = ordonnee.get(ordonnee.size()-2);
				Case suivante = getCaseSuivante(voisins, precedente);
				ordonnee.add(suivante);
				casesChemin.remove(suivante);
			}
			else if (voisins.size() == 1){
				ordonnee.add(voisins.get(0));
				casesChemin.remove(voisins.get(0));
			}
			
		}
		ordonnee.addAll(casesChemin);
		this.casesChemin = ordonnee;
	}
	
	/**
	 * Récupère les cases voisines d'une case passée en paramètre
	 * @param _case la case à chercher
	 * @return une liste des cases voisines
	 */
	private ArrayList<Case> getCasesVoisine(Case _case){
		int x = _case.getX();
		int y = _case.getY();
		ArrayList<Case> cheminVoisins = new ArrayList<>();
		
		for(Case actuelle : casesChemin){
			if ((actuelle.getX() == x && (actuelle.getY() == y-100 || actuelle.getY() == y+100))
			 || (actuelle.getY() == y && (actuelle.getX() == x-100 || actuelle.getX() == x+100)))
			{
				cheminVoisins.add(actuelle);
			}
		}
		
		return cheminVoisins;
	}
	
	/**
	 * Récupère la case suivante du chemin d'une case donnée
	 * @param voisin les voisins de la case
	 * @param precedente la case à chercher
	 * @return la case suivante du chemin
	 */
	public Case getCaseSuivante(ArrayList<Case> voisin, Case precedente ){
		Case rt = null;
		for(Case c : voisin){
			if(!c.equals(precedente)){
				rt = c;
				break;
			}
		}
		return rt;
	}
	
	/**
	 * récupère la liste des cases chemins
	 * @return la liste des cases chemins
	 */
	public ArrayList<Case> getChemin(){
		return casesChemin;
	}

	@Override
	public String toString() {
		return "Plateau [terrain=" + terrain +", Chemin=" + casesChemin + ", taille=" + taille + "]";
	}
}
