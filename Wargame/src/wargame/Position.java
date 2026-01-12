package wargame;

import java.io.Serializable;

/**
 * Evite la surcharge de code en ramenant x,y a un objet
 */

public class Position implements IConfig, Serializable{
	
	// 1: Déclarations
	private static final long serialVersionUID = 1L;
	private int x, y;
	
	Position(){
		this.setX((int)(Math.random()*LARGEUR_CARTE));
		this.setY((int)(Math.random()*HAUTEUR_CARTE));
	}
	
	/*
	Position(int x, int y){
		if(x<0 || x>LARGEUR_CARTE || y<0 || y>HAUTEUR_CARTE) {
			System.out.println("Position : position invalide");
		}
		else{
			this.setX(x);
			this.setY(y);
		}
	} */
	
	Position(int x, int y) {
	    // On utilise >= car si LARGEUR_CARTE est 25, l'index max est 24
	    if (x < 0 || x >= LARGEUR_CARTE || y < 0 || y >= HAUTEUR_CARTE) {
	        // En cas d'erreur, on met des valeurs négatives pour que estValide() renvoie false
	        this.x = -1;
	        this.y = -1;
	    } else {
	        this.x = x;
	        this.y = y;
	    }
	}
	
	public int getX() {
		 return x; 
	}
	
	public int getY() { 
		return y; 
	}
	
	public void setX(int x) { 
		this.x = x; 
	}
	
	public void setY(int y) { 
		this.y = y; 
	}
	
	/*
	public boolean estValide() {
		if (x<0 || x>=LARGEUR_CARTE || y<0 || y>=HAUTEUR_CARTE) {
			return false; 
		}
		else {
			return true;
		}
	} */
	
	public boolean estValide() {
	    // Une position est valide seulement si elle est dans les bornes du tableau
	    return (x >= 0 && x < LARGEUR_CARTE && y >= 0 && y < HAUTEUR_CARTE);
	}
	
	public String toString() { 
		return "("+x+","+y+")"; 
	}
	
	public boolean estVoisine(Position pos) {
		return ((Math.abs(x-pos.x)<=1) && (Math.abs(y-pos.y)<=1));

	}
}