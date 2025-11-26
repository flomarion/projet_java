package wargame;
import java.awt.Color;
import java.awt.Graphics;
public class Obstacle extends Element {
	public enum TypeObstacle {
		ROCHER (Color.gray), FORET (Color.green), EAU (Color.blue);
		private final Color COULEUR;
		TypeObstacle(Color couleur) { COULEUR = couleur; }
		public static TypeObstacle getObstacleAlea() {
			return values()[(int)(Math.random()*values().length)];
		}
	}
	private TypeObstacle TYPE;
	public Obstacle(TypeObstacle type, Position pos) { 
		TYPE = type; this.pos = pos; 
	}
	public String toString() { return ""+TYPE; }
}