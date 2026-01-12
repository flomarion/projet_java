package wargame;

import java.awt.Color;
import java.io.Serializable;

public class Element implements IConfig,Serializable{
	private static final long serialVersionUID = 1L;
	protected Position pos;
	
	public Element() {
		this.pos = new Position();
	}
	public Element(Position pos) {
		if(pos.estValide()){
			this.pos = pos;		
		}
	}
	public void setPos(Position pos) {
		this.pos = pos;
	}
	public Position getPos() {
		return this.pos;
	}
	public Color getCouleur(){
		return COULEUR_VIDE;
	}
}