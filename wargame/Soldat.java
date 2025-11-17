package wargame;

public class Soldat implements ISoldat {
    void setPoints(int POINTS_DE_VIE){
        this.POINTS_DE_VIE = POINTS_DE_VIE;
    }
            
    void combat(Soldat soldat){
        this.setPoints(this.getPoints()-soldat.getPuissance());
        soldat.setPoints(soldat.getPoints()-this.getPuissance());
    }
            
}
