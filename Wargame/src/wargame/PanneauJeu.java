package wargame;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import wargame.Carte.DeplacementException;

import java.awt.*;

/**
 * Gestion des raffraichissement d'écran et de l'état du jeu
 */

public class PanneauJeu extends JPanel implements MouseListener, MouseMotionListener {

	// 1: Déclarations
    private static final long serialVersionUID = 1L;
    private Image spriteHumain;
    private Image spriteNain;
    private Image spriteElf;
    private Image spriteHobbit;
    private Image spriteHealer;
    private Image spriteMagicien;
    private Image spriteOrc;
    private Image spriteTroll;
    private Image spriteGobelin;
    private Image spriteHerbe;
    private Image spriteEau;
    private Image spriteRocher;
    private Image spriteBuisson;
    private Carte c;
    private Element el;
    private Position provient;
    private boolean isDragged = false;
    JLabel barreInfo;
    private String infoTexte = "";
    private JPanel panelVies;
    private JPanel panelViesMonstres;
    private int modeAffichage = 1; // POur dire qu'on affiche les images, sinon 0

    public PanneauJeu(Carte c,JLabel barreInfo,JPanel panelVies, JPanel panelViesMonstres) {
    	// pour mettre en place l'info bulle
    	// c'est lui qui écoute les mouvements de souris pour la fonction défini plus bas
    	ToolTipManager.sharedInstance().registerComponent(this);
    	// on règle le temps de l'info bulle par défaut a 10seconde 
    	// par contre il faut faire attention lorsque on a la bulle d'activé de ne pas bouger d'un pixel
    	// car direct aprè
    	ToolTipManager.sharedInstance().setDismissDelay(10000);
        this.c = c;
        
        //Gestion de l'erreur en cas de fichier introuvable
        try {
			this.spriteHumain = ImageIO.read(new File("images/humain.png"));
			this.spriteNain = ImageIO.read(new File("images/nain.png"));
			this.spriteElf = ImageIO.read(new File("images/elf.png"));
			this.spriteHobbit = ImageIO.read(new File("images/hobbit.png"));
			this.spriteHealer = ImageIO.read(new File("images/healer.png"));
			this.spriteMagicien = ImageIO.read(new File("images/magicien.png"));

			this.spriteOrc= ImageIO.read(new File("images/orc.png"));
			this.spriteTroll= ImageIO.read(new File("images/troll.png"));
			this.spriteGobelin= ImageIO.read(new File("images/gobelin.png"));

			this.spriteHerbe= ImageIO.read(new File("images/herbe.png"));
			this.spriteBuisson= ImageIO.read(new File("images/buisson.png"));
			this.spriteEau= ImageIO.read(new File("images/eau.png"));
			this.spriteRocher= ImageIO.read(new File("images/rocher.png"));

		} catch (IOException e) {
			e.printStackTrace();
			this.modeAffichage = 0;
		}

        addMouseListener(this);
        addMouseMotionListener(this);

        setPreferredSize(new Dimension(600, 600));
        this.barreInfo = barreInfo;
        
        this.panelVies = panelVies;
        this.panelViesMonstres = panelViesMonstres;
        }

    // 2: Les méthodes
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int cellW = getWidth() / IConfig.LARGEUR_CARTE;
        int cellH = getHeight() / IConfig.HAUTEUR_CARTE;
        
        // Dessin du brouillard + éléments visibles
        for(int i = 0; i < IConfig.HAUTEUR_CARTE; i++) {
            for(int j = 0; j < IConfig.LARGEUR_CARTE; j++) {

                Color couleur = new Color(0, 0, 0, 0);
                Position pos = new Position(j,i);
                Element e = c.getElement(pos);
                boolean visible = estVisible(pos);
                if(this.modeAffichage == 1) {
	                if(isDragged && provient != null) {           	
	                	Heros heros = (Heros) el;
	                    int dx = Math.abs(j - provient.getX());
	                    int dy = Math.abs(i - provient.getY());
	                    int porteeAction = heros.getPortee();
	                    
	                    if(dx <= 1 && dy <= 1 && c.getElement(pos) instanceof ElementVide) {
	                        couleur = new Color(255, 255, 0, 80);
	                    }
	                    // on va faire la porté en cyan
	                    else if (dx + dy <= porteeAction) {
	                    	if (e instanceof ElementVide || !visible) {
	                    		couleur = new Color(175, 238, 238, 80); // a peut près cyan choisir entre les deux
	                    		// le premier est un bleu un peu plus pale que j'ai trouvé sur google
	                    		// demander avis groupe
	                    		//couleur = Color.CYAN;
	                    	}
	                    }
	                }
	                
	                g.drawImage(spriteHerbe, j * cellW, i * cellH, cellW, cellH, this);
	                
	                g.setColor(couleur);          
	                g.fillRect(j * cellW, i * cellH, cellW, cellH);
	                if(visible) {   	
	                	if (e instanceof Heros) {
	                	    Heros s = (Heros) e;
	                	    if (s.type == ISoldat.TypesH.HUMAIN) {
	                	        g.drawImage(spriteHumain, j * cellW, i * cellH, cellW, cellH, this);
	                	    }
	                	    else if (s.type == ISoldat.TypesH.NAIN) {
	                	        g.drawImage(spriteNain, j * cellW, i * cellH, cellW, cellH, this);
	                	    }
	                	    else if (s.type == ISoldat.TypesH.ELF) {
	                	        g.drawImage(spriteElf, j * cellW, i * cellH, cellW, cellH, this);
	                	    }
	                	    else if (s.type == ISoldat.TypesH.HOBBIT) {
	                	        g.drawImage(spriteHobbit, j * cellW, i * cellH, cellW, cellH, this);
	                	    }
	                	    else if (s.type == ISoldat.TypesH.HEALER) {
	                	        g.drawImage(spriteHealer, j * cellW, i * cellH, cellW, cellH, this);
	                	    }
	                	    else if (s.type == ISoldat.TypesH.MAGICIEN) {
	                	        g.drawImage(spriteMagicien, j * cellW, i * cellH, cellW, cellH, this);
	                	    }
	                	}
	                	else if (e instanceof Monstre) {
	                	    Monstre s = (Monstre) e;
	                	    if (s.type == ISoldat.TypesM.ORC) {
	                	        g.drawImage(spriteOrc, j * cellW, i * cellH, cellW, cellH, this);
	                	    }
	                	    else if (s.type == ISoldat.TypesM.TROLL) {
	                	        g.drawImage(spriteTroll, j * cellW, i * cellH, cellW, cellH, this);
	                	    }
	                	    else if (s.type == ISoldat.TypesM.GOBELIN) {
	                	        g.drawImage(spriteGobelin, j * cellW, i * cellH, cellW, cellH, this);
	                	    }
	                	}
	                	else if (e instanceof Obstacle) {
	                	    Obstacle o = (Obstacle) e;
	                	    if (o.getType() == Obstacle.TypeObstacle.BUISSON) {
	                	        g.drawImage(spriteBuisson, j * cellW, i * cellH, cellW, cellH, this);
	                	    }
	                	    else if (o.getType() == Obstacle.TypeObstacle.EAU) {
	                	        g.drawImage(spriteEau, j * cellW, i * cellH, cellW, cellH, this);
	                	    }
	                	    else if (o.getType() == Obstacle.TypeObstacle.ROCHER) {
	                	        g.drawImage(spriteRocher, j * cellW, i * cellH, cellW, cellH, this);
	                	    }
	                	}

	                }
	                else {
	                	
	                	int distanceMin = 100; // Valeur de base
	                	int diffX, diffY, distanceTotale;
	                	for (int k = 0; k < IConfig.NB_HEROS; k++) {
	                	    Heros h = c.getLHeros()[k];
	                	    if (h != null && h.getPos() != null) {     
	                	        // Calcul des distances
	                	        diffX = j - h.getPos().getX();
	                	        diffY = i - h.getPos().getY();
	                	        
	                	        if (diffX < 0) { 
	                	        	diffX = -diffX; 
	                	        }
	                	        if (diffY < 0) { 
	                	        	diffY = -diffY; 
	                	        }
	                	        
	                	        distanceTotale = diffX + diffY;

	                	        // On garde la distance la plus petite trouvée
	                	        if (distanceTotale < distanceMin) {
	                	            distanceMin = distanceTotale;
	                	        }
	                	    }
	                	}

	                	// Calcul de l'opacité sans dépasser 240
	                	int opacite = 60 + (distanceMin * 25);
	                	if (opacite > 240) { 
	                	    opacite = 240; 
	                	}
	                    g.setColor(new Color(0, 0, 0, opacite));                    
	            
	                    g.fillRect(j * cellW, i * cellH, cellW, cellH);
	                }
                }
                else {
	                if(estVisible(pos)) couleur = e.getCouleur();
	                // surbrillance si drag
	                // avec le rajout de la portée en cyan
	                if(isDragged && provient != null) {
	                	
	                	Heros heros = (Heros) el;
	                    int dx = Math.abs(j - provient.getX());
	                    int dy = Math.abs(i - provient.getY());
	                    int porteeAction = heros.getPortee();
	                    
	                    if(dx <= 1 && dy <= 1 && c.getElement(pos) instanceof ElementVide) {
	                        couleur = Color.YELLOW;
	                    }
	                    // on va faire la porté en cyan
	                    else if (dx + dy <= porteeAction) {
	                    	if (e instanceof ElementVide || !visible) {
	                    		couleur = new Color(175, 238, 238); // a peut près cyan choisir entre les deux
	                    		// le premier est un bleu un peu plus pale que j'ai trouvé sur google
	                    		// demander avis groupe
	                    		//couleur = Color.CYAN;
	                    	}
	                    }
	                }
	                
	                g.setColor(couleur);
	                
	                g.fillRect(j * cellW, i * cellH, cellW, cellH);
					
	                g.setColor(Color.BLACK);
	                
	                g.drawRect(j * cellW, i * cellH, cellW, cellH);
	                
	                if (visible && e instanceof Soldat) {
	                    Soldat s = (Soldat) e;
	                    g.setFont(new Font("Arial", Font.BOLD, 16)); // Police
	                    String texte;
	                    if (s instanceof Heros) {
	                    	texte = "" + (char)('A'+s.getId()-1); // -1 car on ajoute avec l'id du héros
	                    	// en question et l'id commence a 1
	                    }
	                    else {
	                    	texte = "" + s.getId();
	                    }
	                    // Calcul pour centrer le texte dans la case
	                    // On avance de 5 pixels vers la droite et on descend à peu près au milieu
	                    g.drawString(texte, j * cellW + 18, i * cellH + (cellH / 2) + 5);
	                    
	                }
                }
            }
        }

        
    }

    /*brouillard*/
    private boolean estVisible(Position p) {
        for(int i = 0; i < IConfig.HAUTEUR_CARTE; i++) {
            for(int j = 0; j < IConfig.LARGEUR_CARTE; j++) {

                Element e = c.getElement(new Position(j,i));
                if (e instanceof Heros) {
                    Heros h = (Heros) e;

                    int portee = h.type.getPortee();
                    int dx = Math.abs(j - p.getX());
                    int dy = Math.abs(i - p.getY());

                    if(dx + dy <= portee) return true;
                }
                // vision monstre
                /*
                if (e instanceof Monstre) {
                    Monstre h = (Monstre) e;

                    int portee = h.type.getPortee();
                    int dx = Math.abs(j - p.getX());
                    int dy = Math.abs(i - p.getY());

                    if(dx + dy <= portee) return true;
                }
                */
                // fin vision monstre
            }
        }
        return false;
    }
    
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

    
    public void mouseDragged(MouseEvent e) {
        if(isDragged) repaint();
    }
    
    public void mouseReleased(MouseEvent e) {
        if(el != null && provient != null) {

            int cellW = getWidth() / IConfig.LARGEUR_CARTE;
            int cellH = getHeight() / IConfig.HAUTEUR_CARTE;

            int j = e.getX() / cellW;
            int i = e.getY() / cellH;

            Position cible = new Position(j,i);
            /*
            int dx = Math.abs(j - provient.getX());
            int dy = Math.abs(i - provient.getY());
            
            if(dx <= 1 && dy <= 1 && c.getElement(cible) instanceof ElementVide) {
                c.setElement(el, cible);
                c.setElement(new ElementVide(provient), provient);
                el.setPos(cible);
            }*/
            try {
				c.actionHeros(provient, cible);
			} catch (DeplacementException exep) {
				//exep.printStackTrace();
				System.err.println(exep);
			}
            // pour mettre a jour la barre de vie après une action
            FenetreJeu.miseAJourBarreVie(panelVies, c);
	        FenetreJeu.miseAJourBarreVieMonstre(panelViesMonstres, c);

        }	

        isDragged = false;
        el = null;
        provient = null;
        repaint();
    }

    public void mouseMoved(MouseEvent e) {
        int cellW = getWidth() / IConfig.LARGEUR_CARTE;
        int cellH = getHeight() / IConfig.HAUTEUR_CARTE;

        int j = e.getX() / cellW;
        int i = e.getY() / cellH;
        if(j >= 0 && j < IConfig.LARGEUR_CARTE && i >= 0 && i < IConfig.HAUTEUR_CARTE) {
        	Position pos = new Position(j,i);
	        Element eCase = c.getElement(pos);
	        // on affiche uniquement si notre soldat n'est pas dans le brouillard de guerre !
	        if((eCase instanceof Soldat) && estVisible(pos)) {	
	            infoTexte = eCase.toString();
	        } else {
	            infoTexte = "";
	        }
	        
	        // texte de la barre du soldat
	        if (infoTexte.isEmpty()) {
	            this.barreInfo.setText(" "); // Espace pour garder la barre ouverte
	        } else {
	            this.barreInfo.setText(infoTexte);
	        }
	        repaint();
        }
    }

    public void mouseClicked(MouseEvent e) {}
    
    public void mouseEntered(MouseEvent e) {}
    
    public void mouseExited(MouseEvent e) {
    	infoTexte=""; 
    	this.barreInfo.setText(" "); 
    	repaint(); 
    	}
    
    public void setCarte(Carte nouvelleCarte) {
        this.c = nouvelleCarte;
        this.repaint(); 
    }
    
    public String getToolTipText(MouseEvent e) {
    	// c'est globablement le meme calcul des coordonnées que pour la barre
    	// juste on redéfini une fonction qui crée une info bulle au survol de la souris
        int cellW = getWidth() / IConfig.LARGEUR_CARTE;
        int cellH = getHeight() / IConfig.HAUTEUR_CARTE;

        int j = e.getX() / cellW;
        int i = e.getY() / cellH;

        if (j >= 0 && j < IConfig.LARGEUR_CARTE && i >= 0 && i < IConfig.HAUTEUR_CARTE) {
            Position pos = new Position(j, i);
            Element eCase = c.getElement(pos);

            // On affiche l'infobulle seulement si le soldat est visible (dcp pas dans le brouillard)
            if ((eCase instanceof Soldat ) && estVisible(pos)) {
                return eCase.toString();
            }
        }
        return null; // dcp ici on a pas d'info bulle si notre case est vide 
    }
}