package wargame;
public interface ISoldat {
   static enum TypesH {
	   //Les déclarations
	   // le magicien peut se déplacer sur les 24 cases adjacentes 
	   // le healer peux soigner un héros allié par la fonction du glissé déposé
      HUMAIN ( 40,4,10,2), NAIN (80,2,20,0), ELF (70,5,10,6), HOBBIT (20,3,5,2), HEALER (15,4,5,0), 
      MAGICIEN (25, 5, 2, 4);
      private final int POINTS_DE_VIE, PORTEE_VISUELLE, PUISSANCE, TIR;
      TypesH(int points, int portee, int puissance, int tir) {
         POINTS_DE_VIE = points; 
         PORTEE_VISUELLE = portee;
         PUISSANCE = puissance; 
         TIR = tir;
      }
      
      // Les getters
      public int getPoints() { 
    	  return POINTS_DE_VIE; 
      }
      public int getPortee() { 
    	  return PORTEE_VISUELLE; 
      }
      public int getPuissance() { 
    	  return PUISSANCE; 
      }
      public int getTir() { 
    	  return TIR; 
      }
      
      // Les setteurs
      
      public static TypesH getTypeHAlea() {
         return values()[(int)(Math.random()*values().length)];
      }
      
   }
   public static enum TypesM {
	   //Les déclarations
      TROLL (100,2,30,3), ORC (40,3,10,3), GOBELIN (20,5,5,2);
      private final int POINTS_DE_VIE, PORTEE_VISUELLE, PUISSANCE, TIR;
      TypesM(int points, int portee, int puissance, int tir) {
    	  POINTS_DE_VIE = points; 
    	  PORTEE_VISUELLE = portee;
    	  PUISSANCE = puissance; 
    	  TIR = tir;
      }
      
      // Les getters
      public int getPoints() { 
         return POINTS_DE_VIE; 
      }
      public int getPortee() { 
         return PORTEE_VISUELLE; 
      }
      public int getPuissance() { 
         return PUISSANCE; 
      }
      public int getTir() { 
         return TIR; 
      } 
        
      // Les setters

      public static TypesM getTypeMAlea() {
          return values()[(int)(Math.random()*values().length)];
       }
   }
   
   // Méthodes de l'interface
   int getPoints();
   int getTour(); 
   void setPoints(int points);
   void joueTour();
   void seDeplace(Position newPos);
   int getPuissance();
   int getTir();
   int getPortee();
}