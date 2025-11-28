package wargame;

public class Soldat extends Element implements ISoldat {
	private static int nb_heros = 0;
	private static int nb_monstre = 0;
    private int pv;
	private int id;
	private int tour;

    public Soldat(char type) { 	
		super();
		if(type == 'H'){
			nb_heros++;
			id = nb_heros;
		}
		else{
			nb_monstre++;
			id = nb_monstre;
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
	public void joueTour(int tour) {	
		this.tour++;
	}
	public void seDeplace(Position newPos) {		
		this.setPos(newPos);
	}         
	
}
