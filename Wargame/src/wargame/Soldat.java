package wargame;

public class Soldat implements ISoldat {
	private static int nb_heros = 0;
	private static int nb_monstre = 0;
	private Position pos;
    private int pv;
	private int id;

    public Soldat(char type) { 	
		if(type == 'H'){
			nb_heros++;
			id = nb_heros;
		}
		else{
			nb_monstre++;
			id = nb_monstre;
		}
		pos = new Position();
    }
	public int getPoints() {
		return this.pv;
	}
	public void setPoints(int points) {
        this.pv = points ;
    }

	public void joueTour(int tour) {
		
	}
	public void seDeplace(Position newPos) {		
	}
            
}
