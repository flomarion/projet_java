package wargame;

import java.awt.Color;

public class Heros extends Soldat{
	TypesH type;
	public Heros() {
		super('H');
		type = TypesH.getTypeHAlea();
		this.setPoints(type.getPoints());
	}
	public void afficheHeros(){
		System.out.println("TEST Heros Aleatoire :\nPoints de vies : "+this.type.getPoints()+"\nPortee: "+this.type.getPortee()+"\nPuissance : "+this.type.getPuissance()+"\nTir : "+this.type.getTir());
	}
	public Color getCouleur() {
		return COULEUR_HEROS;
	}
}
