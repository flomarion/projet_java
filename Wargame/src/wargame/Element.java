package wargame;

public class Element {
	protected Position pos;
	
	public Element() {
		this.pos = new Position();
	}
	public Element(Position pos) {
		if(pos.estValide()){
			this.pos = pos;		
		}
		else {
			System.out.println("Element : position invalide");
		}
	}
	public void setPos(Position pos) {
		this.pos = pos;
	}
}
