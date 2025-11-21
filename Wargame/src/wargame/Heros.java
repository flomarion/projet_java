package wargame;

public class Heros extends Soldat{
	TypesH type;
	public Heros() {
		super();
		type = TypesH.getTypeHAlea();
		this.setPoints(type.getPoints());
	}

}
