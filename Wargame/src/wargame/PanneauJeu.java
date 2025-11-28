package wargame;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class PanneauJeu extends JPanel {
	private static final long serialVersionUID = 1L;
	JButton bouton;
	JLabel label;
	JPanel carre[][] = new JPanel[IConfig.HAUTEUR_CARTE][IConfig.LARGEUR_CARTE];
	
	int cmp = 0;
	public PanneauJeu(Carte c) {
		int x,y,i,j;
		Position p = new Position();
		Element e = new Element();
		setLayout(new GridLayout(IConfig.HAUTEUR_CARTE, IConfig.LARGEUR_CARTE));
		for(i = 0 ; i < IConfig.HAUTEUR_CARTE ; i++) {
			for(j = 0 ; j < IConfig.LARGEUR_CARTE ; j++) {
				x = i;
				y = j;
				
				carre[x][y] = new JPanel();
				p.setX(x);
				p.setY(y);
				e = c.getElement(p);
				
				
				carre[x][y].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				
				if(e instanceof Obstacle) {
					carre[x][y].setBackground(Color.red);
				}
				add(carre[x][y]);
			}
		}
	}
}
