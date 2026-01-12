package wargame;

import java.awt.Color;

public class Heros extends Soldat{
	
	// 1: Déclarations
	private static final long serialVersionUID = 1L;
	TypesH type;
	
	public Heros() {
		super('H');
		type = TypesH.getTypeHAlea();
		this.setPoints(type.getPoints());
	}
	
	// 2: Les méthodes
	public void afficheHeros(){
		System.out.println(this.type+"\n Points de vies : "+this.getPoints()+"\nPortee: "+this.type.getPortee()+"\nPuissance : "+this.type.getPuissance()+"\nTir : "+this.type.getTir());
	}
	
	public Color getCouleur() {
		if (this.getTour() > 0) {
	        return IConfig.COULEUR_HEROS_DEJA_JOUE; 
	    }
	    return IConfig.COULEUR_HEROS;
	}
	
	public String toString() {
		return (this.type+" Points de vies : "+this.getPoints()+" Portee: "+this.type.getPortee()+" Puissance : "+this.type.getPuissance()+" Tir : "+this.type.getTir());

	}
	
	public int getPuissance() {
		return this.type.getPuissance();
	}
	
	public int getTir() {
		return this.type.getTir();
	}
	
	public int getPortee() {
		return this.type.getPortee();
	}
	
	
}