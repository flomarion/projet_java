package wargame;

import java.awt.Color;

public class Monstre extends Soldat{
    TypesM type;
	public Monstre() {
		super('M');
		type = TypesM.getTypeMAlea();
		this.setPoints(type.getPoints());
	}
    /*Les getters */
    public String getNomType(){
        if(type.equals(TypesM.TROLL)){
            return "TROLL";
        }
        else if(type.equals(TypesM.ORC)){
            return "ORC";
        }
        else{
            return "GOBELIN";
        }   
    }
    public Color getCouleur() {
		return COULEUR_MONSTRES;
	}
    /*Affichage */
	public void afficheMonstre(){
		System.out.println("Monstre "+ this.getNomType()+" :\nPoints de vies : "+this.type.getPoints()+"\nPortee: "+this.type.getPortee()+"\nPuissance : "+this.type.getPuissance()+"\nTir : "+this.type.getTir());
	}
}
