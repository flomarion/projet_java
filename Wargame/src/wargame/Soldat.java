package wargame;

public class Soldat implements ISoldat {
	private Position pos;
    private int POINTS_DE_VIE_ACTUELS;
    private int PORTEE_VISUELLE;
    
    public Soldat() { 	
    }
	public int getPoints() {
		return this.POINTS_DE_VIE_ACTUELS;
	}
	public void setPoints(int points) {
        this.POINTS_DE_VIE_ACTUELS = points ;
    }
	@Override
	public int getTour() {
		// TODO Stub de la méthode généré automatiquement
		return 0;
	}

	@Override
	public int getPortee() {
		// TODO Stub de la méthode généré automatiquement
		return 0;
	}

	@Override
	public void joueTour(int tour) {
		// TODO Stub de la méthode généré automatiquement
		
	}

	@Override
	public void seDeplace(Position newPos) {
		// TODO Stub de la méthode généré automatiquement
		
	}
            
}
