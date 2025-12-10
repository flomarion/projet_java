package wargame;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class PanneauJeu extends JPanel implements MouseListener, MouseMotionListener {
    private static final long serialVersionUID = 1L;

    private Carte c;
    private Element el;
    private Position provient; // Position de départ du héros
    private boolean isDragged = false;

    JPanel carre[][] = new JPanel[IConfig.HAUTEUR_CARTE][IConfig.LARGEUR_CARTE];

    public PanneauJeu(Carte c) {
        this.c = c;

        addMouseListener(this);
        addMouseMotionListener(this);

        setLayout(new GridLayout(IConfig.HAUTEUR_CARTE, IConfig.LARGEUR_CARTE));

        for(int i = 0; i < IConfig.HAUTEUR_CARTE; i++) {
            for(int j = 0; j < IConfig.LARGEUR_CARTE; j++) {
                carre[i][j] = new JPanel();
                carre[i][j].setBackground(IConfig.COULEUR_INCONNU);
                carre[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                add(carre[i][j]);
            }
        }

        rafraichirAffichage();
    }

    private void rafraichirAffichage() {
        // Réinitialiser toutes les cases au brouillard
        for(int i = 0; i < IConfig.HAUTEUR_CARTE; i++) {
            for(int j = 0; j < IConfig.LARGEUR_CARTE; j++) {
                carre[i][j].setBackground(IConfig.COULEUR_INCONNU);
            }
        }

        // Affichage des héros selon leur portée
        for(int i = 0; i < IConfig.HAUTEUR_CARTE; i++) {
            for(int j = 0; j < IConfig.LARGEUR_CARTE; j++) {
                Position p = new Position(j, i);
                Element e = c.getElement(p);

                if(e instanceof Heros) {
                    Heros h = (Heros)e;
                    int portee = h.type.getPortee();

                    for(int dy = -portee; dy <= portee; dy++) {
                        for(int dx = -portee; dx <= portee; dx++) {
                            int vi = i + dy;
                            int vj = j + dx;

                            if(vi >= 0 && vi < IConfig.HAUTEUR_CARTE && vj >= 0 && vj < IConfig.LARGEUR_CARTE) {
                                if(Math.abs(dx)+Math.abs(dy) <= portee) {
                                    Element visible = c.getElement(new Position(vj, vi));
                                    carre[vi][vj].setBackground(visible.getCouleur());
                                }
                            }
                        }
                    }
                }
            }
        }

        // Si drag en cours, surbrillance des cases accessibles
        if(isDragged && el != null && provient != null) {
            int x0 = provient.getX();
            int y0 = provient.getY();

            for(int dy = -1; dy <= 1; dy++) {
                for(int dx = -1; dx <= 1; dx++) {
                    int vi = y0 + dy;
                    int vj = x0 + dx;

                    if(vi >= 0 && vi < IConfig.HAUTEUR_CARTE && vj >= 0 && vj < IConfig.LARGEUR_CARTE) {
                        if(c.getElement(new Position(vj, vi)) instanceof ElementVide) {
                            carre[vi][vj].setBackground(Color.YELLOW);
                        }
                    }
                }
            }

            // Maintenir la case d'origine visible
            carre[y0][x0].setBackground(el.getCouleur());
        }

        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int cellW = getWidth() / IConfig.LARGEUR_CARTE;
        int cellH = getHeight() / IConfig.HAUTEUR_CARTE;

        int j = e.getX() / cellW;
        int i = e.getY() / cellH;

        Position p = new Position(j, i);
        el = c.getElement(p);

        if(el instanceof Heros) {
            isDragged = true;
            provient = new Position(p.getX(),p.getY());
        }

        rafraichirAffichage();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
		if(this.isDragged) {
			int cellW = getWidth() / IConfig.LARGEUR_CARTE;
	        int cellH = getHeight() / IConfig.HAUTEUR_CARTE;

	        int j = e.getX() / cellW;  
	        int i = e.getY() / cellH;
	        if(this.carre[i][j].getBackground() == Color.white || this.carre[i][j].getBackground() == Color.yellow) {
	        	rafraichirAffichage();
	        	this.carre[i][j].setBackground(Color.cyan);
	        }
		}
		
		
	}
    @Override
    public void mouseReleased(MouseEvent e) {
        if(el != null && provient != null) {
            int cellW = getWidth() / IConfig.LARGEUR_CARTE;
            int cellH = getHeight() / IConfig.HAUTEUR_CARTE;

            int j = e.getX() / cellW;
            int i = e.getY() / cellH;

            Position p = new Position(j, i);
            int dx = Math.abs(j - provient.getX());
            int dy = Math.abs(i - provient.getY());

            if(dx <= 1 && dy <= 1 && c.getElement(p) instanceof ElementVide) {
                c.setElement(el, p);
                c.setElement(new ElementVide(this.provient), this.provient);
                el.setPos(p);
            } else {
                c.setElement(el, this.provient);
            }
        }

        isDragged = false;
        el = null;
        provient = null;
        rafraichirAffichage();
    }

    // Méthodes inutilisées mais obligatoires
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseMoved(MouseEvent e) {}
}
