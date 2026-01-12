package wargame;

/**
 * Classe utilisant le terminal pour les tests
 */

public class Main{
    public static void main(String[] args) {
        Heros h1 = new Heros();
        Monstre m1 = new Monstre();
        Position pos = new Position();

        /*Test sur les positions */
        System.out.println("TEST Position Aleatoire : X: "+pos.getX()+" Y: "+pos.getY());

        /*Test sur les soldats */
        h1.afficheHeros();

        /*Test de la carte */
        Carte c = new Carte();
        c.afficheCarte();
        System.out.println("héros : "+h1.getPoints());
        System.out.println("monstre : "+m1.getPoints());

        

    }
}