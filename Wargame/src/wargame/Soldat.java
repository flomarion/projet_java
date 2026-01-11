package wargame;

public abstract class Soldat extends Element implements ISoldat {
	private static final long serialVersionUID = 1L;
	private static int nb_heros = 0;
	private static int nb_monstre = 0;
    private int pv;
	private int id; 
	private int tour;

    public Soldat(char type) { 	
		super();
		if(type == 'H'){
			nb_heros++;
			this.id = nb_heros;
		}
		else{
			nb_monstre++;
			this.id = nb_monstre;
		}
		tour = 0;
    }
	public int getPoints() {
		return this.pv;
	}
	public int getTour() {
		return this.tour;
	}
	public void setPoints(int points) {
        this.pv = points ;
    }
	public void joueTour() {	
		this.tour++;
	}
	public void seDeplace(Position newPos) {		
		this.setPos(newPos);
	}      
	// ici, vu que on a pas Puissance et tir 
	// dans soldat mais uniquement dans monstre et 
	// héros les rend abstrait pour pouvoir l'utiliser
	// dans soldat
	public abstract int getPuissance();
	public abstract int getTir();
	public abstract int getPortee();
	
	public void combat(Soldat soldat) {
		int p;
		// p stock la puissance ou le tir en fonction
		// si on est dans un combat a porté ou au corps a corps
		if (this.getPos().estVoisine(soldat.getPos())) {
			p = this.getPuissance();
		}
		else {
			p = this.getTir();
		}
		// donc on porte le premier coup (this)
		int degatsAttaque = (int)(Math.random() * (p + 1));
		soldat.setPoints(soldat.getPoints() - degatsAttaque);
		
		// si l'adversaire est encore vivant alors il riposte
		if (soldat.getPoints() > 0) {
			//si l'adversaire a la portee de riposter il le fait
			int xa = soldat.getPos().getX();
			int ya = soldat.getPos().getY();
			int xs = this.getPos().getX();
			int ys = this.getPos().getY();
			int distance = Math.abs(xa - xs) + (ya - ys);
			if(soldat.getPortee() >= distance) {
	            int pRiposte;
	            if (soldat.getPos().estVoisine(this.getPos())) {
	                pRiposte = soldat.getPuissance();
	            } else {
	                pRiposte = soldat.getTir();
	            }
	            int degatsRiposte = (int)(Math.random() * (pRiposte + 1)) ;
	            this.setPoints(this.getPoints() - degatsRiposte);
			}
		}
		
	}
	public void resetTour(){
	    this.tour = 0;
	}
	public static void resetCompteurs() {
        nb_heros = 0;
        nb_monstre = 0;
    }

    public int getId() {
        return this.id;
    }
	
}