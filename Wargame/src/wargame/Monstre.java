package wargame;

import java.awt.Color;

public class Monstre extends Soldat{
	private static final long serialVersionUID = 1L;
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
		System.out.println("Monstre "+ this.getNomType()+" :\nPoints de vies : "+this.getPoints()+"\nPortee: "+this.type.getPortee()+"\nPuissance : "+this.type.getPuissance()+"\nTir : "+this.type.getTir());
	}
	public String toString() {
		return "Monstre "+ this.getNomType()+" : Points de vies : "+this.getPoints()+" Portee: "+this.type.getPortee()+" Puissance : "+this.type.getPuissance()+" Tir : "+this.type.getTir();
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