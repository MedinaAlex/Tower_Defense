package com.epsi.tower_defense;

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

		this.pv = 100;
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
				
				Case c;
				switch (s) {
					case 'x':
						c= new Case(x,y,false,false,false,false, 'x');
						casesChemin.add(c);
						break;

					case '3':
						c = new Case(x,y,true, true, false, true, 'D');
						casesChemin.add(c);
						break;
						
					case '4':
						c = new Case(x,y,true, false, true, false, 'x');
						casesChemin.add(c);
						break;
					case '0':
						c = new Case(x,y,false, false, false, false, 'x');
						terrain.add(c);
						break;
						

					default:

						c = new Case(x,y,true, false, false,true, s);
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
		for(int i =0; i<vague; i++) {
			Case caseDepart = casesChemin.get(0);
			Ennemi ennemi = new Ennemi("bob" +i, 1, 30 + vague, 20, 10,caseDepart.x*100 , caseDepart.y*100, caseDepart.direction);
			listEnnemi.add(ennemi);
		}
	}
	
	/**
	 * Permet aux tours d'attaquer un ennemi à portée
	 */
	public void attaquer(){


		for (Case caseTerrain:terrain) {
			if(caseTerrain.getTour() != null){
				Tour tour = caseTerrain.getTour();
				int nombreTire = 1;
				int coorTourX = (caseTerrain.getX() * 100);
				int coorToury = (caseTerrain.getY() * 100);

				for (Ennemi ennemi:listEnnemi) {

					if (nombreTire == 1) {

						//savoir si les coordonnées de l'ennemi sont dans le cercle
						//racine_carre((x_point - x_centre)² + (y_centre - y_point)) < rayon
						if ((Math.sqrt((Math.pow((ennemi.coorX - coorTourX), 2)) + (Math.pow((coorTourX - ennemi.coorX), 2)))) < tour.getPortee()) {
							nombreTire++;
							ennemi.pv = ennemi.pv - tour.degat;
							if (ennemi.pv < 0) {
								listEnnemi.remove(ennemi);
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
			direction = ennemi.direction;
			for (Case caseC : casesChemin) {

				//on verrifie que la case sur la quels est l'ennemi n'éguille pas
				if (caseC.aiguilleur && (caseC.getX() * 100 == ennemi.coorX && caseC.getY() * 100 == ennemi.coorY)) {
					direction = caseC.direction;


				}


			}

			switch (direction) {
				case 'H':
					ennemi.coorX--;
					break;
				case 'B':
					ennemi.coorX++;
					break;
				case 'D':
					ennemi.coorY++;
					break;
				case 'G':
					ennemi.coorY--;
					break;
			}
			ennemi.direction = direction;
		}

		//casesChemin.get(0).listEnnemis = new ArrayList<Ennemi>();
		verifierArrive();
	}
	
	/** 
	 * Faire perdre des pv au plateau si un ennemi est arrivé à la fin du chemin
	 */
	public void verifierArrive(){
		Case arrive = casesChemin.get(casesChemin.size()-1);
		for(Ennemi e: arrive.listEnnemis){
			this.pv -= e.degat;
		}
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
			if ((actuelle.getX() == x && (actuelle.getY() == y-1 || actuelle.getY() == y+1)) 
			 || (actuelle.getY() == y && (actuelle.getX() == x-1 || actuelle.getX() == x+1)))
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
