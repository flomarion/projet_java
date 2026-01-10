package wargame;
import java.io.Serializable;
import java.util.Random;

public class Carte implements IConfig,Serializable{
	// Exception pour déplacement impossible
	public class DeplacementException extends Exception {
	    public DeplacementException(String message) {
	        super(message);
	    }
	}

	// Délcarations
	private static final long serialVersionUID = 1L;
	private Element[][] grille;
	private Random random;
	private Heros[] lHeros;
	private Monstre[] lMonstres;
	private Obstacle[] lObstacles;
	Carte(){
		int i;
		grille=new Element[IConfig.LARGEUR_CARTE][IConfig.HAUTEUR_CARTE];
		random = new Random();
		lHeros = new Heros[IConfig.NB_HEROS];
		lMonstres = new Monstre[IConfig.NB_MONSTRES];
		lObstacles = new Obstacle[IConfig.NB_OBSTACLES];
		// ici on initialise notre grille avec des valeurs null
		for (int x=0 ; x<IConfig.LARGEUR_CARTE ; x++ ) {
			for (int y=0 ; y<IConfig.HAUTEUR_CARTE ; y++) {
				grille[x][y] = new ElementVide(new Position(x,y));
			}
		}
		for(i=0;i<IConfig.NB_OBSTACLES;i++) {
			Obstacle o=new Obstacle(Obstacle.TypeObstacle.getObstacleAlea());
			Position pos=new Position();
			do{
				pos=new Position((int)(Math.random()*(IConfig.LARGEUR_CARTE)),(int)(Math.random()*(IConfig.HAUTEUR_CARTE)));
			}
			while(!(this.getElement(pos) instanceof ElementVide));
			o.setPos(pos);
			this.setElement(o, pos);
			lObstacles[i]=o;
		}
		for(i=0;i<IConfig.NB_HEROS;i++) {
			Heros h=new Heros();
			Position pos=new Position();
			do{
				pos=new Position((int)(Math.random()*((IConfig.LARGEUR_CARTE-1)*0.2)),(int)(Math.random()*(IConfig.HAUTEUR_CARTE-1)));
			}
			while(!(this.getElement(pos) instanceof ElementVide));
			h.setPos(pos);
			this.setElement(h, pos);
			lHeros[i]=h;
		}
		for(i=0;i<IConfig.NB_MONSTRES;i++) {
			Monstre m=new Monstre();
			Position pos=new Position();
			do{
				pos=new Position(IConfig.LARGEUR_CARTE-1-(int)(Math.random()*(IConfig.LARGEUR_CARTE*0.2)),(int)(Math.random()*(IConfig.HAUTEUR_CARTE-1)));
			}
			while(!(this.getElement(pos) instanceof ElementVide));
			m.setPos(pos);
			this.setElement(m, pos);
			lMonstres[i]=m;
		}
	}
	
	//2: Les méthodes
	//Permet d'afficher la carte dans terminal pour debuguer (plus utile)
	public void afficheCarte() {
		for (int y=0 ; y<IConfig.HAUTEUR_CARTE ; y++ ) {
			for (int x=0 ; x<IConfig.LARGEUR_CARTE ; x++ ) {	
				if (grille[x][y]  instanceof ElementVide) {
					System.out.print("0 ");					
				}			
				else if (grille[x][y] instanceof Heros) {
					System.out.print("H ");					
				}
				else if (grille[x][y] instanceof Monstre) {
					System.out.print("M ");					
				}	
				else if (grille[x][y] instanceof Obstacle) {
					System.out.print(grille[x][y].toString().charAt(0) + " ");					
				}
			}
			System.out.println("");
		}
	}
	
	//Retourne l'element à la position
	public Element getElement(Position pos) {
			return this.grille[pos.getX()][pos.getY()];

	}
	
	//Set un element e en position pos
	public void setElement(Element e, Position pos) {
		this.grille[pos.getX()][pos.getY()] = e;
	}

	
	/*
	Pour cette fonction, on va faire un tableau ou on va récuperer toutes les 
 	positions adjacentes et ensuite on en récupère une aléatoirement parmis toutes celle présente
	*/
	public Position trouvePositionVide(Position pos) {
	    Position adjacentes[] = new Position[8];
	    int lg = 0;
	    
	    for (int x = -1; x < 2; x++) {
	        for (int y = -1; y < 2; y++) {
	            if (!(x == 0 && y == 0)) {
	                // On crée la position potentielle
	                Position poss = new Position(pos.getX() + x, pos.getY() + y);
	                
	                // On vérifie si elle est valide AVANT d'accéder à la grille
	                if (poss.estValide()) {
	                    // On utilise les coordonnées de l'objet 'poss' pour être sûr
	                    if (grille[poss.getX()][poss.getY()] instanceof ElementVide) {
	                        adjacentes[lg] = poss;
	                        lg++;
	                    }
	                }
	            }
	        }
	    }
	    
	    if (lg == 0) {
	        return null;
	    }
	    
	    int r = random.nextInt(lg);
	    return adjacentes[r];
	}
	public Heros trouveHeros() {
		/*
		Ici on est obligé de compter le nombre de héros
		pour ensuite créer un tableau de ce nombre
	 	on pourrait aussi initialiser un tableau avec un nombre plus grand que le max de héros
	 	possible
		*/
		int count = 0;
	    for (int x = 0; x < IConfig.LARGEUR_CARTE; x++) {
	        for (int y = 0; y < IConfig.HAUTEUR_CARTE; y++) {
	            if (grille[x][y] instanceof Heros) {
	                count++;
	            }
	        }
	    }
	    if (count == 0) {
	    	return null;
	    }
	    Heros tabHeros[] = new Heros[count];
	    count=0;
	    for (int x = 0; x < IConfig.LARGEUR_CARTE; x++) {
	        for (int y = 0; y < IConfig.HAUTEUR_CARTE; y++) {
	            if (grille[x][y] instanceof Heros) {
	                tabHeros[count] = (Heros) grille[x][y];
	                count++;
	            }
	        }
	    }
	    int r = random.nextInt(count);
	    return tabHeros[r];
	}
	
	public Heros trouveHeros(Position pos) {
		Heros adjacents[] = new Heros[8];
		int lg = 0;
		for(int x = -1 ; x < 2; x++ ) {
			for (int y = -1 ; y < 2; y++ ) {
				if (!(x == 0 && y == 0)) {
					int posx = pos.getX() + x;
					int posy = pos.getY() + y;
					if (posx >= 0 && posx < IConfig.LARGEUR_CARTE && posy >= 0 && posy < IConfig.HAUTEUR_CARTE) {
						if (grille[posx][posy] instanceof Heros) {
							adjacents[lg] = (Heros) grille[posx][posy];
							lg++;
						}
					}
				}
			}

			
		}
		if (lg == 0) {
			return null;
		}
		int r = random.nextInt(lg);
		return adjacents[r];
	}
	
	public boolean deplaceSoldat(Position pos, Soldat soldat) throws DeplacementException{
	    if (pos.estValide() == false) {
	    	throw new DeplacementException("ERREUR deplaceSoldat : Position invalide");
	    }

	    if (!(getElement(pos) instanceof ElementVide)) {
	    	throw new DeplacementException("ERREUR deplaceSoldat : Position déjà occupée");
	    }

	    // on récup l'ancienne position pour la vider
	    Position anciennePos = soldat.getPos();

	    // on met le soldat sur la nouvelle position
	    setElement(soldat, pos);
	    soldat.setPos(pos);

	    // et la on remet ElementVide à l'ancienne position
	    setElement(new ElementVide(anciennePos), anciennePos);

	    return true;
	}
	public void mort(Soldat perso) {
	    Position pos = perso.getPos();
	    
	    // On vérifie que le personnage a bien une position valide avant de le supprimer
	    if (pos != null && pos.estValide()) {
	        this.setElement(new ElementVide(pos), pos); 
	    }
	    
	    perso.setPos(null); 
	}
	
	public boolean actionHeros(Position pos, Position pos2) throws DeplacementException {
		// premierement, on regarde si y'a un héros a la pos
		Element elt = getElement(pos);
		if (!(elt instanceof Heros)) {
			return false;
		}
		Heros heros = (Heros) elt;
		
		// on regarde si le héros a déjà joué
		if (heros.getTour() > 0) {
			return false;
		}
		// on recupere l'élement a la pos2
		Element cible = getElement(pos2);
		
		// si c'est un element vide alors on le 
		// deplace 
		if (cible instanceof ElementVide) {
	        if (pos.estVoisine(pos2)) {
	            if (this.deplaceSoldat(pos2, heros)) {
	                heros.joueTour(); // Marque le tour pour passer au rose
	                return true;
	            }
	        }
	    }
		// si monstre alors on le combat
		else if (cible instanceof Monstre) {
	        // On calcule la distance entre le heros et le monstre
	        int dx = Math.abs(pos.getX() - pos2.getX());
	        int dy = Math.abs(pos.getY() - pos2.getY());
	        int portee = heros.getPortee();

	        // attaque possible si le monstre est dans le rayon de portée du heros
	        if (dx <= portee && dy <= portee) {
	            Monstre monstre = (Monstre) cible;
	            heros.combat(monstre);
	            heros.joueTour(); // Marque le tour pour passer au rose après l'attaque

	            // Gestion de la mort
	            if (monstre.getPoints() <= 0) {
	                this.mort(monstre);
	            }
	            if (heros.getPoints() <= 0) {
	                this.mort(heros);
	            }
	            return true;
	        }
	    }
		// si c'est un héros allié et que nous on est un soigneur alors on le soigne
		else if (cible instanceof Heros) {
			Heros cibleHeros = (Heros) cible;
			// si notre héros est bien un HEALER et que on essaie pas de se soigner soi même
			if (heros.type == ISoldat.TypesH.HEALER && heros != cibleHeros) {
				// calcule de la distance entre notre element et la cible
				int dx = Math.abs(pos.getX() - pos2.getX());
				int dy = Math.abs(pos.getY() - pos2.getY());
				if (dx <= heros.getPortee() && dy <= heros.getPortee()) {
					int pointsSoin = heros.getPuissance();
					int maxPV = cibleHeros.type.getPoints();
					int nouveauxPV= heros.getPoints() + pointsSoin;
					// on fait attention que les pv actuel + le soin ne dépasse pas les pv max !
					if (nouveauxPV > maxPV) {
	                    nouveauxPV = maxPV;
					}
					cibleHeros.setPoints(nouveauxPV);
					heros.joueTour();
					return true;
					
					
				}
			}
			
		}
	    
	    // l'action n'est ni un déplacement valide ni une attaque à portée alors on fait rien
		throw new DeplacementException("ERREUR actionHeros : Deplacement invalide");
	}
	
	
	/* pour la méthode jouerSoldats(), on doit définir une méthode qui cherche a une portée */
	
	public Heros trouveHerosAPortee(Monstre m) {
		int portee = m.getPortee();
		Position posM = m.getPos();
		int x,y;
		// ici nos 2 boucles servent a scaner le carré autour de notre position 
		// on scan en commencant par tout en bas a gauche 
		for (x = posM.getX() - portee; x <= posM.getX() + portee; x++) {
			for (y = posM.getY() - portee; y <= posM.getY() + portee; y++) {
	            Position p = new Position(x, y);
	            if (p.estValide()) {
	                Element e = getElement(p);
	                if (e instanceof Heros) {
	                    return (Heros) e;
	                }
	            }
	        }
		}
		return null;
	}
	
	
	public void jouerSoldats() throws DeplacementException {
		int i;
		// on fait le tour des monstres
		for (i = 0; i < IConfig.NB_MONSTRES; i++) {
			Monstre m = lMonstres[i];
			// ensuite on regarde si le monstre est bien encore en vie
			if (m!=null && m.getPos() != null) {
				// on choisit une cible, on la récupère avec notre fonction trouveHerosAPortee définie juste au dessus
				Heros cible = trouveHerosAPortee(m);
				if (cible != null ) { 
					m.combat(cible);
					// on le combat et si jamais il a 0 ou des points de vie négatif alors il meurt
					if (cible.getPoints()<= 0) {
						this.mort(cible);
					}
					// on verif si le monstre est mort suite a la riposte du héros
					if (m.getPoints() <= 0) {
	                    this.mort(m);
	                }
				}
				// sinon ça veut dire que il ne trouve personne et donc il joue selon son status
				else {
					//Si le monstre a moins de 10 pv il se repose
					if(m.getPoints() < 10) {
						int maxPV = m.type.getPoints();
						int nouveauxPV = m.getPoints() + 2;
						if(maxPV < nouveauxPV) {
							m.setPoints(maxPV);
						}
						else {
							m.setPoints(nouveauxPV);
						}
					}
					else {
						Position posCaseVide = trouvePositionVide(m.getPos());
		                if (posCaseVide != null) {
		                    this.deplaceSoldat(posCaseVide, m);
		                }
					}
				}
			}
		}
		// LES HEROS QUI ONT PAS BOUGE SE REPOSE
		
		for (i = 0; i < IConfig.NB_HEROS; i++) {
			Heros h = lHeros[i];
			if (h != null && h.getPos() != null) {
				if (h.getTour() == 0) {
					// on récupère les pv initiaux
					int maxPV = h.type.getPoints(); 
					int nouveauxPV= h.getPoints() + 2;
					if (nouveauxPV > maxPV) {
	                    nouveauxPV = maxPV;
					}
					h.setPoints(nouveauxPV);
				}
				h.resetTour();
			}
		}
		// REINIT DES TOURS DES MONSTRES
	    for (i = 0; i < IConfig.NB_MONSTRES; i++) {
	        if (lMonstres[i] != null) {
	            lMonstres[i].resetTour();
	        }
	    }
		
	}
	// on l'utilise pour gérer la fin de la partie 
	public int getNbHeros() {
	    int count = 0;
	    int i;
	    for (i= 0; i < IConfig.NB_HEROS; i++) {
	        if (lHeros[i] != null && lHeros[i].getPos() != null) {
	            count++;
	        }
	    }
	    return count;
	}
	
	// idem c'est pour gérer la fin de la partie en regardant si il y'a plus de monstre
	public int getNbMonstres() {
	    int count=0;
	    int i;
	    for (i= 0; i<IConfig.NB_MONSTRES; i++) {
	        if (lMonstres[i] != null && lMonstres[i].getPos() != null) {
	            count++;
	        }
	    }
	    return count;
	}
	public Heros[] getLHeros() {
	    return this.lHeros;
	}
	
	public Monstre[] getLMonstres() {
	    return this.lMonstres;
	}
	
}
	