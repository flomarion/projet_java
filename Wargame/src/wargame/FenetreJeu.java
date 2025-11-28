package wargame;

import java.awt.*; 
import javax.swing.*;

public class FenetreJeu {
	public static void main(String[] args) {
		Obstacle o = new Obstacle(Obstacle.TypeObstacle.ROCHER, new Position(3, 5));
		Carte c = new Carte();
		c.setElement(o, o.getPos());
		// Creer la JFrame
		JFrame frame = new JFrame("Fenêtre Jeu");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Créer la JMenuBar
		
		frame.setLocation(100, 100);
		// Afficher...
		JPanel panel = new PanneauJeu(c);
		panel.setPreferredSize(new Dimension(1920,1080));
		panel.setLocation(100, 100);
		panel.setBackground(Color.GRAY);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true); // Car masqué par défaut
	}
}
