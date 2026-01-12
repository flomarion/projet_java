package wargame;
import java.awt.Color;

/**
 * Définit les élément qu'aucun soldat ne peut franchir
 */

public class Obstacle extends Element {
	
	// 1: Déclarations
	private static final long serialVersionUID = 1L;
	
	/**
	 * Different obstacle ils ont tous la meme fonction
	 */
	
	public enum TypeObstacle {
		ROCHER (COULEUR_ROCHER), BUISSON (COULEUR_BUISSON), EAU (COULEUR_EAU);
		private final Color COULEUR;
		TypeObstacle(Color couleur) { COULEUR = couleur; }
		public static TypeObstacle getObstacleAlea() {
			return values()[(int)(Math.random()*values().length)];
		}
	}
	private TypeObstacle TYPE;
	
	// 2: Les méthodes
	public Obstacle(TypeObstacle type) { 
		TYPE = type; 
	}
	
	public Obstacle(TypeObstacle type, Position pos) { 
		TYPE = type; this.pos = pos; 
	}
	
	public Color getCouleur() {
		return this.TYPE.COULEUR;
	}
	
	public TypeObstacle getType() {
		return this.TYPE;
	}
	
	public String toString() { return ""+TYPE; }
}