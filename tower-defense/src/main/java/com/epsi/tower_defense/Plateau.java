package com.epsi.tower_defense;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Plateau {
	
	ArrayList<Case> terrain=new ArrayList<>();
	ArrayList<Case> chemin=new ArrayList<>();
	
	int taille;
	
	int pv;
	
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
					case '0':
						c= new Case(x,y,false,false,false);
						terrain.add(c);
						break;
						
					case '1': 
						 c = new Case(x,y,true, false, false);
						chemin.add(c);
						break;
						
					case '3':
						 c = new Case(x,y,true, true, false);
						chemin.add(c);
						break;
						
					case '4':
						 c = new Case(x,y,true, false, true);
						chemin.add(c);
						break;
						
					default:
						throw new Exception("Case non valide");
				}
				y++;
				if(y == taille){
					x++;
					y = 0;
				}

			}
			determinerChemin();
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
	
	public void run(){
		while(pv > 0){
			deplacerEnnemis();
		}
		System.out.println("Vous avez perdu");
	}
	
	public void deplacerEnnemis(){
		
		for(int i = chemin.size()-1; i > 0; i--){
			chemin.get(i).setListEnnemis(chemin.get(i-1).getListEnnemis());
		}
		System.out.println(chemin);
		chemin.get(0).listEnnemis = new ArrayList<Ennemi>();
		verifierArrive();
	}
	
	public void verifierArrive(){
		Case arrive = chemin.get(chemin.size()-1);
		for(Ennemi e: arrive.listEnnemis){
			this.pv -= e.degat;
		}
	}

	public void determinerChemin(){
		ArrayList<Case> ordonnee = new ArrayList<>();
		for(Case c: chemin){
			if(c.isDepart()){
				ordonnee.add(c);
				chemin.remove(c);
				
				break;
			}	
		}
		
		while(chemin.size() > 1 ){
			Case actuelle = ordonnee.get(ordonnee.size()-1);			
			ArrayList<Case> voisins = getCaseVoisin(actuelle);
			
			if(voisins.size() == 2){
				System.out.println(voisins.get(0));
				System.out.println(voisins.get(1));
				Case precedente = ordonnee.get(ordonnee.size()-2);
				Case suivante = getCaseSuivante(voisins, precedente);
				ordonnee.add(suivante);
				chemin.remove(suivante);
			}
			else if (voisins.size() == 1){
				ordonnee.add(voisins.get(0));
				chemin.remove(voisins.get(0));
			}
			
		}
		ordonnee.addAll(chemin);
		this.chemin = ordonnee;
	}
	
	private ArrayList<Case> getCaseVoisin(Case ennemi){
		int x = ennemi.getX();
		int y = ennemi.getY();
		ArrayList<Case> cheminVoisins = new ArrayList<>();
		
		for(Case c : chemin){
			if ((c.getX() == x && (c.getY() == y-1 || c.getY() == y+1)) 
			 || (c.getY() == y && (c.getX() == x-1 || c.getX() == x+1)))
			{
				cheminVoisins.add(c);
			}
		}
		
		return cheminVoisins;
	}
	
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
	
	public ArrayList<Case> getChemin(){
		return chemin;
	}

	@Override
	public String toString() {
		return "Plateau [terrain=" + terrain +", Chemin=" + chemin + ", taille=" + taille + "]";
	}
}
