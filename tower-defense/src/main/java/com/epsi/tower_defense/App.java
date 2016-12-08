package com.epsi.tower_defense;

import java.io.IOException;


public class App {
	public static void main(String[] args) throws IOException {
		Plateau plateau = new Plateau("ressources/terrainTest.json");
		
		Ennemi ennemi = new Ennemi("bob", 1, 1, 100, 1);
		plateau.chemin.get(0).listEnnemis.add(ennemi);
		
		plateau.run();
	}
}
