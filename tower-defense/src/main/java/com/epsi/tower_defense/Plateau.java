package com.epsi.tower_defense;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Plateau {
	
	ArrayList<Case> terrain=new ArrayList<>();
	ArrayList<Case> chemin=new ArrayList<>();
	
	int taille;
	
	public Plateau(String path) {

		
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
			//terrain = new Case[taille][taille];
			
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
				

			}
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
	}

	public int[] coorDepart(){
		int tableauEntier[] = {1,0};
		return tableauEntier;
	}
	public int[] coorArrivee(){
		int tableauEntier[] = {4,3};
		return tableauEntier;
	}

	public void determinerCase(){
		/*for(int j = 0; j < taille; j++;){
			for(int i = 0; ){
		}*/
	}


	@Override
	public String toString() {
		return "Plateau [terrain=" + terrain +", Chemin=" + chemin + ", taille=" + taille + "]";
	}
	
	
}
