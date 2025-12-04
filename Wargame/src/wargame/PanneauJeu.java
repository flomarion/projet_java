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
					carre[i][j].setBackground(IConfig.COULEUR_INCONNU);;
					
					carre[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
					add(carre[i][j]);
			}
		}
		for(i = 0 ; i < IConfig.HAUTEUR_CARTE ; i++) {
			for(j = 0 ; j < IConfig.LARGEUR_CARTE ; j++) {
				p.setX(j);
				p.setY(i);
				e = c.getElement(p);
				
				add(carre[i][j]);
				if (e instanceof Heros) {
					int portee;
					Heros h;
					Element e_tmp;
					carre[i][j].setBackground(e.getCouleur());
					h = (Heros) e;
					portee = h.type.getPortee();
					for(k = -portee ; k < portee+1 ; k++) {
						for(l = -portee ; l < portee+1 ; l++) {
							if( (j+l) >= 0 && (i+k) >= 0 && (i+k) < IConfig.HAUTEUR_CARTE && (j+l) < IConfig.LARGEUR_CARTE) {
								p_tmp.setX(j+l);
								p_tmp.setY(i+k);
								e_tmp = c.getElement(p_tmp);
								if(Math.abs(k)+Math.abs(l) <= portee) {
									carre[i+k][j+l].setBackground(e_tmp.getCouleur());
								}
							}
							
						}
					}
				}	
				/* Vue monstre pour debuggage*/
				/*
				if (e instanceof Monstre) {
					int portee;
					Monstre h;
					Element e_tmp;
					carre[i][j].setBackground(e.getCouleur());
					h = (Monstre) e;
					portee = h.type.getPortee();
					for(k = -portee ; k < portee+1 ; k++) {
						for(l = -portee ; l < portee+1 ; l++) {
							if( (j+l) >= 0 && (i+k) >= 0 && (i+k) < IConfig.HAUTEUR_CARTE && (j+l) < IConfig.LARGEUR_CARTE) {
								p_tmp.setX(j+l);
								p_tmp.setY(i+k);
								e_tmp = c.getElement(p_tmp);
								if(Math.abs(k)+Math.abs(l) <= portee) {
									carre[i+k][j+l].setBackground(e_tmp.getCouleur());
								}
							}
							
						}
					}
				}
				*/
				/*fin Vue monstre*/
			}
		}
	}
}
