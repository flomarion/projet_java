package wargame;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class PanneauJeu extends JPanel implements MouseListener{
	private static final long serialVersionUID = 1L;
	JButton bouton;
	JLabel label;
	JPanel carre[][] = new JPanel[IConfig.HAUTEUR_CARTE][IConfig.LARGEUR_CARTE];
	private Carte c;
	private Element el;
	int cmp = 0;
	public PanneauJeu(Carte c) {
		this.c = c;
		int i,j;
		addMouseListener(this);
		for(i = 0 ; i < IConfig.HAUTEUR_CARTE ; i++) {
			for(j = 0 ; j < IConfig.LARGEUR_CARTE ; j++) {

				
					carre[i][j] = new JPanel();
					carre[i][j].setBackground(IConfig.COULEUR_INCONNU);;
					
					carre[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
					add(carre[i][j]);
			}
		}
		rafraichirAffichage();
		setLayout(new GridLayout(IConfig.HAUTEUR_CARTE, IConfig.LARGEUR_CARTE));
	}
	private void rafraichirAffichage() {
		int i,j,k,l;
		Position p = new Position();
		Position p_tmp= new Position();
		Element e;
	    for(i = 0 ; i < IConfig.HAUTEUR_CARTE ; i++) {
			for(j = 0 ; j < IConfig.LARGEUR_CARTE ; j++) {
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
	    
	    revalidate();
	    repaint();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method 
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		int cellW = getWidth() / IConfig.LARGEUR_CARTE;
		int cellH = getHeight() / IConfig.HAUTEUR_CARTE;
		Element el = new Element();
		int j = e.getX() / cellW; // colonne
		int i = e.getY() / cellH; // ligne
		
		Position p = new Position(j,i);

		el = this.c.getElement(p);
		
		if(el instanceof Heros) {
			this.el = el;
			this.c.setElement(new ElementVide(p), p);
		}
		
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	    if (this.el != null) {

	        int cellW = getWidth() / IConfig.LARGEUR_CARTE;
	        int cellH = getHeight() / IConfig.HAUTEUR_CARTE;

	        int j = e.getX() / cellW;  
	        int i = e.getY() / cellH;

	        Position p = new Position(j, i);
	        Element caseArrivee = this.c.getElement(p);

	        int dx = this.el.getPos().getX() - p.getX();
	        int dy = this.el.getPos().getY() - p.getY();

	        if (Math.abs(dx) <= 1 && Math.abs(dy) <= 1 && caseArrivee instanceof ElementVide) {

	            // OK → appliquer déplacement
	            this.c.setElement(this.el, p);
	            this.el.setPos(p);

	        } else {
	            this.c.setElement(this.el, this.el.getPos());
	        }

	        rafraichirAffichage();
	        this.el = null;
	    }
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
