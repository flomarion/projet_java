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
		int i,j,k,l;
		Position p = new Position();
		Position p_tmp= new Position();
		Element e;
		setLayout(new GridLayout(IConfig.HAUTEUR_CARTE, IConfig.LARGEUR_CARTE));
		
		for(i = 0 ; i < IConfig.HAUTEUR_CARTE ; i++) {
			for(j = 0 ; j < IConfig.LARGEUR_CARTE ; j++) {

				
					carre[i][j] = new JPanel();
			}
		}
		for(i = 0 ; i < IConfig.HAUTEUR_CARTE ; i++) {
			for(j = 0 ; j < IConfig.LARGEUR_CARTE ; j++) {
				p.setX(j);
				p.setY(i);
				e = c.getElement(p);
				
				//carre[i][j].setBackground(IConfig.COULEUR_INCONNU);;
				
				carre[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				carre[i][j].setBackground(e.getCouleur());
				add(carre[i][j]);
//				if (e instanceof Heros) {
//					int portee;
//					Heros h;
//					Element e_tmp;
//					carre[i][j].setBackground(e.getCouleur());
//					add(carre[i][j]);
//					h = (Heros) e;
//					portee = h.type.getPortee();
//					for(k = 1 ; k < portee+1 ; k++) {
//						for(l = 1 ; l < portee+1 ; l++) {
//							if((i+k < IConfig.LARGEUR_CARTE) && (j+l < IConfig.HAUTEUR_CARTE)) {
//								p_tmp.setX(i+k);
//								p_tmp.setY(j+l);
//								c.afficheCarte();
//								System.out.println((i+k)+" et "+ (j+l) + " " +portee);
//								e_tmp = c.getElement(p_tmp);
//								carre[i+k][j+l].setBackground(e_tmp.getCouleur());
//							}
//							if((i-k > 0) && (j-l > 0)) {
//								p_tmp.setX(i-k);
//								p_tmp.setY(j-l);
//								carre[i-k][j-l].setBackground(c.getElement(p_tmp).getCouleur());
//							}
//						}
//					}
//				}				
			}
		}
	}
}
