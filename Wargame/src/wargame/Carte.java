package wargame;
import java.util.Random;

public class Carte implements IConfig{
	private Element[][] grille;
	private Random random;
	Carte(){
		grille=new Element[IConfig.LARGEUR_CARTE][IConfig.HAUTEUR_CARTE];
		random = new Random();
		// ici on initialise notre grille avec des valeurs null
		for (int x=0 ; x<IConfig.LARGEUR_CARTE ; x++ ) {
			for (int y=0 ; y<IConfig.HAUTEUR_CARTE ; y++) {
				grille[x][y] = null;
			}
		}
	}
	
	public Element getElement(Position pos) {
		if (pos.estValide() == true ) {
			return this.grille[pos.getX()][pos.getY()];
		}
		return null;
	}
	public Position trouvePositionVide() {
		int arret = 0; // si arret = 1 ca veut dire que on a trouvé la pos
		int essais = 0; // si les essais valent 375 ca veut dire que y'a plus de place sur la carte
		Position pos = null;
		while (arret == 0) {
			int x = random.nextInt(IConfig.LARGEUR_CARTE);
			int y = random.nextInt(IConfig.HAUTEUR_CARTE);
			pos = new Position(x,y);
			if (grille[x][y] == null) {
				arret = 1;
			}
			essais++;
			if (essais == 375) {
				return null;
			}
			
		}
		return pos;
				
	}
	// pour cette fonction, on va faire un tableau ou on va récuperer toutes les
	// positions adjacentes et ensuites on en récupère une aléatoirement parmis toutes celle présente
	public Position trouvePositionVide(Position pos) {
		Position adjacentes[] = new Position[8];
		int lg = 0;
		Position poss = null;
		for (int x = -1 ; x < 2; x++ ) {
			for (int y = -1 ; y < 2; y++ ) {
				if (!(x == 0 && y == 0)) {
					int posx = pos.getX() + x;
					int posy = pos.getY() + y;
					poss = new Position(posx, posy);
					if (poss.estValide() == true) {
						if (grille[posx][posy] == null) {
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
		/* ici on est obligé de compter le nombre de héros
		 * pour ensuite créer un tableau de ce nombre
		 * on pourrait aussi initialiser un tableau avec un nombre plus grand que le max de héros
		 * possible*/
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
	
	public boolean deplaceSoldat(Position pos, Soldat soldat) {
		/* ici on vérifie que la pos est valide*/
		if (pos.estValide() == true) {
			return false;
		}
		if (grille[pos.getX()][pos.getY()] != null) {
			return false;
		}
		/*on enleve le soldat de son ancienne positon */
		grille[soldat.getPos().getX()][soldat.getPos().getY()] = null;
		/* on met le soldat sur sa nouvelle position*/
		grille[pos.getX()][pos.getY()] = soldat;
		soldat.setPos(pos);
		return true;
		
	}
	public void mort(Soldat perso) {
	    Position pos = perso.getPos();
	    if (pos != null && pos.estValide() == true) {
	        grille[pos.getX()][pos.getY()] = null; // on enleve le soldat de sa grille
	    }
	    perso.setPos(null); // et la on met la pos a null car il n'a plus de pos
	}
}

