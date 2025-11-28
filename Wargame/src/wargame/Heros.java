package wargame;

public class Heros extends Soldat{
	TypesH type;
	public Heros() {
		super('H');
		type = TypesH.getTypeHAlea();
		this.setPoints(type.getPoints());
	}
	void afficheHeros(){
		System.out.println("TEST Heros Aleatoire :\nPoints de vies : "+this.type.getPoints()+"\nPortee: "+this.type.getPortee()+"\nPuissance : "+this.type.getPuissance()+"\nTir : "+this.type.getTir());
	}
}
