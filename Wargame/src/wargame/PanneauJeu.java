package wargame;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class PanneauJeu extends JPanel implements MouseListener, MouseMotionListener {
    private static final long serialVersionUID = 1L;

    private Carte c;
    private Element el;
    private Position provient;
    private boolean isDragged = false;
    JLabel barreInfo;
    private String infoTexte = "";

    public PanneauJeu(Carte c,JLabel barreInfo) {
        this.c = c;

        addMouseListener(this);
        addMouseMotionListener(this);

        setPreferredSize(new Dimension(600, 600));
        this.barreInfo = barreInfo;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int cellW = getWidth() / IConfig.LARGEUR_CARTE;
        int cellH = getHeight() / IConfig.HAUTEUR_CARTE;

        // Dessin du brouillard + éléments visibles
        for(int i = 0; i < IConfig.HAUTEUR_CARTE; i++) {
            for(int j = 0; j < IConfig.LARGEUR_CARTE; j++) {

                Color couleur = IConfig.COULEUR_INCONNU;
                Position pos = new Position(j,i);
                Element e = c.getElement(pos);

                if(estVisible(pos)) couleur = e.getCouleur();

                // surbrillance si drag
                if(isDragged && provient != null) {
                    int dx = Math.abs(j - provient.getX());
                    int dy = Math.abs(i - provient.getY());
                    if(dx <= 1 && dy <= 1 && c.getElement(pos) instanceof ElementVide)
                        couleur = Color.YELLOW;
                }

                g.setColor(couleur);
                g.fillRect(j * cellW, i * cellH, cellW, cellH);

                g.setColor(Color.BLACK);
                g.drawRect(j * cellW, i * cellH, cellW, cellH);
            }
        }

        //texte Soldat barre dessous
        if(infoTexte != null && !infoTexte.isEmpty()) {
        	this.barreInfo.setText(infoTexte);
        }
    }


    /*brouillard*/
    private boolean estVisible(Position p) {
        for(int i = 0; i < IConfig.HAUTEUR_CARTE; i++) {
            for(int j = 0; j < IConfig.LARGEUR_CARTE; j++) {

                Element e = c.getElement(new Position(j,i));
                if(e instanceof Heros h) {

                    int portee = h.type.getPortee();
                    int dx = Math.abs(j - p.getX());
                    int dy = Math.abs(i - p.getY());

                    if(dx + dy <= portee) return true;
                }
            }
        }
        return false;
    }




    @Override
    public void mousePressed(MouseEvent e) {
        int cellW = getWidth() / IConfig.LARGEUR_CARTE;
        int cellH = getHeight() / IConfig.HAUTEUR_CARTE;

        int j = e.getX() / cellW;
        int i = e.getY() / cellH;

        Position p = new Position(j,i);
        el = c.getElement(p);

        if(el instanceof Heros) {
            isDragged = true;
            provient = p;
        }
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(isDragged) repaint();
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        if(el != null && provient != null) {

            int cellW = getWidth() / IConfig.LARGEUR_CARTE;
            int cellH = getHeight() / IConfig.HAUTEUR_CARTE;

            int j = e.getX() / cellW;
            int i = e.getY() / cellH;

            Position cible = new Position(j,i);

            int dx = Math.abs(j - provient.getX());
            int dy = Math.abs(i - provient.getY());

            if(dx <= 1 && dy <= 1 && c.getElement(cible) instanceof ElementVide) {
                c.setElement(el, cible);
                c.setElement(new ElementVide(provient), provient);
                el.setPos(cible);
            }
        }

        isDragged = false;
        el = null;
        provient = null;
        repaint();
    }


    @Override public void mouseMoved(MouseEvent e) {
        int cellW = getWidth() / IConfig.LARGEUR_CARTE;
        int cellH = getHeight() / IConfig.HAUTEUR_CARTE;

        int j = e.getX() / cellW;
        int i = e.getY() / cellH;
        if(i < IConfig.HAUTEUR_CARTE && j < IConfig.LARGEUR_CARTE) {
	        Element eCase = c.getElement(new Position(j,i));
	
	        if(eCase instanceof Heros || eCase instanceof Monstre) {
	            infoTexte = eCase.toString();
	        } else {
	            infoTexte = "";
	        }
	        repaint();
        }
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) { infoTexte=""; repaint(); }
}
