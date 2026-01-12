	package wargame;
	
	import java.awt.*;
	import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import wargame.Carte.DeplacementException;

import java.io.*;
	
	/**
	 * Classe de depart des graphisme
	 */
	
	public class FenetreJeu {
		
		/**
		 * Point d'entrée du jeu
		 * @param args
		 */

	    public static void main(String[] args) {
	    	
	    	try {
	    		UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
	    	} catch (Exception e) {
	    	    System.out.println("Impossible de charger le thème.");
	    	}
	    	
	        final Carte[] c = { new Carte() };
	        ImageIcon style = new ImageIcon(FenetreJeu.class.getResource("/images/boutonParchemin.png"));
	        int largeur = 200;
	        int hauteur = 60;

	        Image img = style.getImage().getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);

	        ImageIcon stylebouton = new ImageIcon(img);
	        JFrame frame = new JFrame("WarGame - Prototype");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setLayout(new BorderLayout());
	
	        // --- MENU HAUT ---
	        JMenuBar menuBar = new JMenuBar();
	
	        JMenu menuFichier = new JMenu("Fichier");
	        // ajout des 2 options sauvegarder et charger
	        JMenuItem sauvegarder = new JMenuItem("Sauvegarder");
	        JMenuItem charger = new JMenuItem("Charger");
	        JMenuItem quitter = new JMenuItem("Quitter");
	        menuFichier.add(sauvegarder);
	        menuFichier.add(charger);
	        quitter.addActionListener(e -> System.exit(0));
	        menuFichier.add(quitter);
	
	        JMenu menuJeu = new JMenu("Jeu");
	        JMenuItem reset = new JMenuItem("Réinitialiser");
	        
	        menuJeu.add(reset);
	
	        menuBar.add(menuFichier);
	        menuBar.add(menuJeu);
	
	        frame.setJMenuBar(menuBar);
	        
	        JPanel panelControleMonstres = new JPanel();
	        panelControleMonstres.setLayout(new BoxLayout(panelControleMonstres, BoxLayout.Y_AXIS));
	        JLabel labelMonstres = new JLabel("Monstres : " + c[0].getNbMonstres());
	        labelMonstres.setAlignmentX(Component.CENTER_ALIGNMENT);
	        panelControleMonstres.add(labelMonstres);
	        JPanel panelViesMonstres = new JPanel();
	        panelViesMonstres.setLayout(new BoxLayout(panelViesMonstres, BoxLayout.Y_AXIS));
	        panelViesMonstres.setBorder(BorderFactory.createTitledBorder("Armée des Monstres"));
	        panelControleMonstres.add(panelViesMonstres);
	        frame.add(panelControleMonstres, BorderLayout.EAST);
	        miseAJourBarreVieMonstre(panelViesMonstres, c[0]);
	        
	        
	        JPanel panelControle = new JPanel();
	        
	        panelControle.setLayout(new BoxLayout(panelControle, BoxLayout.Y_AXIS));
	        panelControle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	        // Affichage des scores dynamiques
	        JButton btnFinTour = new JButton("Fin du Tour");
	        boutonParchemin(stylebouton, btnFinTour);
	        JButton btnSauvegarder = new JButton("Sauvegarder");
	        boutonParchemin(stylebouton, btnSauvegarder);
	        JButton btnCharger = new JButton("Charger");
	        boutonParchemin(stylebouton, btnCharger);
	        JButton btnReset = new JButton("Réinitialiser");
	        boutonParchemin(stylebouton, btnReset);
	        
	        // Alignement
	        btnFinTour.setAlignmentX(Component.CENTER_ALIGNMENT);
	        btnSauvegarder.setAlignmentX(Component.CENTER_ALIGNMENT);
	        btnCharger.setAlignmentX(Component.CENTER_ALIGNMENT);
	        btnReset.setAlignmentX(Component.CENTER_ALIGNMENT);
	        panelControle.add(btnSauvegarder);
	        panelControle.add(Box.createRigidArea(new Dimension(0, 20))); // Grand espace
	        panelControle.add(btnCharger);
	        panelControle.add(Box.createRigidArea(new Dimension(0, 20))); // Grand espace
	        panelControle.add(btnReset);
	        panelControle.add(Box.createRigidArea(new Dimension(0, 20))); // Grand espace
	        panelControle.add(btnFinTour);
	        panelControle.add(Box.createRigidArea(new Dimension(0, 20))); // Grand espace
	        
	        
	        // pour les barres de vie dynamiques
	        JLabel labelHeros = new JLabel("Héros : " + c[0].getNbHeros());
	        labelHeros.setAlignmentX(Component.CENTER_ALIGNMENT);
	        labelHeros.setFont(new Font("Arial", Font.BOLD, 14));
	        panelControle.add(labelHeros);
	        panelControle.add(Box.createRigidArea(new Dimension(0, 10)));
	        JPanel panelVies = new JPanel();
	        panelVies.setLayout(new BoxLayout(panelVies, BoxLayout.Y_AXIS));
	        panelVies.setBorder(BorderFactory.createTitledBorder("Armée des Héros"));
	        panelControle.add(panelVies);
	        frame.add(panelControle, BorderLayout.WEST);
	        
	        // premier appel 
	        miseAJourBarreVie(panelVies, c[0]);
	        // pour les barres dynamiques
	        
	        /* création de notre bouton d'aide
	        il résume les différents raccourcis
	        et certaines fonctionnalités*/
	        JButton btnAide = new JButton("Aide");
	        btnAide.setAlignmentX(Component.CENTER_ALIGNMENT);
	        btnAide.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                // Construction du message d'aide
	                String message = "Raccourcis clavier :\n -------------------\n- F : Fin du Tour\n"+
	                "- S : Sauvegarder la partie\n- C : Charger une partie \n- R : Reset \n\n" +
	                "Commandes souris :\n-----------------\n- Glisser Deposer : Déplace/Attaque\n" +
	                "- Survol d'un soldat : Voir les stats";

	                JOptionPane.showMessageDialog(frame, message, "Aide", JOptionPane.INFORMATION_MESSAGE);
	            }
	        });

	        // ajout de notre bouton d'aide avec un espace avant pour que ça soit plus joli
	        panelControle.add(Box.createRigidArea(new Dimension(0, 20))); 
	        panelControle.add(btnAide);
	 
	        // --- PANEL JEU ---
	        JLabel barreInfo = new JLabel("Bienvenue dans WarGame.", SwingConstants.CENTER);
	        barreInfo.setPreferredSize(new Dimension(192, 30));
	        barreInfo.setFont(new Font("Arial", Font.BOLD, 14));
	        barreInfo.setOpaque(true);
	        barreInfo.setBackground(Color.DARK_GRAY);
	        barreInfo.setForeground(Color.WHITE);
	
	        PanneauJeu panel = new PanneauJeu(c[0], barreInfo, panelVies, panelViesMonstres);
	        
	        // AbstractAction pour les raccourcis
	        Action actionSauvegarder = new AbstractAction() {
	            public void actionPerformed(ActionEvent e) {
	                try {
	                    FileOutputStream fileOut = new FileOutputStream("wargame.ser");
	                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
	                    out.writeObject(c[0]); // Sauvegarde de l'objet Carte actuel
	                    out.close();
	                    fileOut.close();
	                    JOptionPane.showMessageDialog(frame, "Partie sauvegardée dans wargame.ser !");
	                } catch (IOException ex) {
	                    //ex.printStackTrace();
	                	System.err.println("ERREUR actionSauvegarder : Erreur lors de la sauvegarde : "+ex.getMessage());
	                    JOptionPane.showMessageDialog(frame, "Erreur lors de la sauvegarde !");
	                }
	            }
	        };
	        
	        // AbstractAction pour les raccourcis
	        Action actionCharger = new AbstractAction() {
	            public void actionPerformed(ActionEvent e) {
	                try {
	                    FileInputStream fileIn = new FileInputStream("wargame.ser");
	                    ObjectInputStream in = new ObjectInputStream(fileIn);
	                    
	                    // Chargement et remplacement de la carte
	                    c[0] = (Carte) in.readObject();
	                    in.close();
	                    fileIn.close();

	                    // Mise à jour graphique après chargement
	                    panel.setCarte(c[0]); 
	                    labelHeros.setText("Héros : " + c[0].getNbHeros());
	                    labelMonstres.setText("Monstres : " + c[0].getNbMonstres());
	        	        miseAJourBarreVie(panelVies, c[0]);
		    	        miseAJourBarreVieMonstre(panelViesMonstres, c[0]);
	                    barreInfo.setText("Partie chargée avec succès !");
	                    
	                    panel.repaint();
	                    JOptionPane.showMessageDialog(frame, "Partie chargé !!");
	                } catch (FileNotFoundException ex) {
	                    JOptionPane.showMessageDialog(frame, "Aucun fichier de sauvegarde trouvé.!!");
	                } catch (IOException | ClassNotFoundException ex) {
	                    //ex.printStackTrace();
	                	System.err.println("ERREUR actionSauvegarder : Erreur lors de la sauvegarde : "+ex.getMessage());
	                    JOptionPane.showMessageDialog(frame, "Erreur lors du chargement!!");
	                }
	            }
	        };
	        
	        
	        //ActionListener actionReinitialiser = new ActionListener() {
	        // AbstractAction pour les raccourcis et non pas Listener 
	        Action actionReinitialiser = new AbstractAction(){
	            public void actionPerformed(ActionEvent e) {
	            	
	                /* ici on crée une nouvelle carte en faisant attention de mettre a jour le panneau
	                 et les compteurs et on met un text pour dire que la partie a était réinit
	                 et on repaint */
	            	Soldat.resetCompteurs();
	                c[0] = new Carte(); 
	                
	                panel.setCarte(c[0]); 
	                
	                labelHeros.setText("Héros : " + c[0].getNbHeros());
	                labelMonstres.setText("Monstres : " + c[0].getNbMonstres());
	    	        miseAJourBarreVie(panelVies, c[0]);
	    	        miseAJourBarreVieMonstre(panelViesMonstres, c[0]);
	                barreInfo.setText("La partie a été réinitialisée !");
	                
	                panel.repaint();
	            }
	        };
	        sauvegarder.addActionListener(actionSauvegarder);
	        btnSauvegarder.addActionListener(actionSauvegarder);
	        btnCharger.addActionListener(actionCharger);
	        btnReset.addActionListener(actionReinitialiser);
	        charger.addActionListener(actionCharger);
	        reset.addActionListener(actionReinitialiser);
	        
	        // on passe par un abstractAction pour les raccourcis claviers
	        Action actionFinTour = new AbstractAction() {
	        	
	        	public void actionPerformed(ActionEvent e) {
	        		// on éxécute la fonction de fin de tour
	        		
	        		//Exception nommé exep car e déjà utilisé pour action event
	        		try {
						c[0].jouerSoldats();
					} catch (Exception exep) {
						//exep.printStackTrace();
					} 
	            
	        		// on raffraichait lorsque les monstres ont étaient joué
	        		panel.repaint(); 
	            
	        		// mise a jour des compteurs
	        		labelHeros.setText("Héros : " + c[0].getNbHeros());
	        		labelMonstres.setText("Monstres : " + c[0].getNbMonstres());
	            
	        		// on vérif si l'un des 2 camps gagne
	        		if (c[0].getNbMonstres() == 0) {
	        			JOptionPane.showMessageDialog(frame, "Bravo ! Vous avez gagné !x");
	        			System.exit(0);
	        		} else if (c[0].getNbHeros() == 0) {
	        			JOptionPane.showMessageDialog(frame, "Vous avez perdu !");
	        			System.exit(0);
	        		}
	            
	        		barreInfo.setText("Nouveau tour ! À vous.");
	    	        miseAJourBarreVie(panelVies, c[0]);
	    	        miseAJourBarreVieMonstre(panelViesMonstres, c[0]);
	        	}
	        };
	
	        frame.add(panel, BorderLayout.CENTER);
	        frame.add(panelControle, BorderLayout.WEST); 
	        frame.add(barreInfo, BorderLayout.SOUTH);
	        frame.setSize(192*8, 108*8);
	        frame.setLocationRelativeTo(null);
	        
	        // liaison du bouton fin de tour avec le listener en question
	        btnFinTour.addActionListener(actionFinTour);
	        
	        // ici on bind nos touches 
	        InputMap im = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	        ActionMap am = panel.getActionMap();
	        im.put(KeyStroke.getKeyStroke('f'), "finTour");
	        
	        // ici on dit que a l'action de la touche f on y associe le nom fintour
	        am.put("finTour", actionFinTour);
	        // et ici on dit que au nom fin de tour, on y associe l'action actionFinTour
	        
	        // idem pour les 3 autres
	        im.put(KeyStroke.getKeyStroke('s'), "sauvegarder");
	        am.put("sauvegarder", actionSauvegarder);

	        im.put(KeyStroke.getKeyStroke('r'), "reinit");
	        am.put("reinit", actionReinitialiser);

	        im.put(KeyStroke.getKeyStroke('c'), "charger");
	        am.put("charger", actionCharger);
	        frame.setVisible(true);
	    }
	    
	    /**
	     * donne a un bouton l'image parchemin
	     * @param stylebouton image
	     * @param btn bouton
	     */
	    
	    // 1: Les méthodes 
		private static void boutonParchemin(ImageIcon stylebouton, JButton btn) {
			// On rend invisible le bouton de base et on insere l'icon a la place
			
			btn.setIcon(stylebouton);
	        btn.setFont(new Font("Serif", Font.BOLD, 18));
	        btn.setForeground(new Color(90, 60, 30)); // couleur encre

	        btn.setBorderPainted(false);
	        btn.setFocusPainted(false);
	        btn.setContentAreaFilled(false);
	        btn.setIcon(stylebouton);
	        
	        btn.setHorizontalTextPosition(SwingConstants.CENTER);
	        btn.setVerticalTextPosition(SwingConstants.CENTER);
	        btn.setIcon(stylebouton);
		}
		
		/**
		 * Met a jour les graphique de barre de vie de héros
		 * @param container JPanel
		 * @param carte Carte du jeu
		 */
		
	    public static void miseAJourBarreVie(JPanel container, Carte carte) {
	    	
	    	// on efface ce qu'on avait fait avant
	    	container.removeAll();
	    	Heros[] liste = carte.getLHeros();
	    	int i;
	    	for (i=0 ; i< IConfig.NB_HEROS ; i++) {
	    		Heros h = liste[i];
	    		if (h != null && h.getPos() != null) {
	    			
	                /* pour l'affichage on a besoin de savoir le nom du héros. ici A,B....
	    			on recuperera aussi le type (ELF etc... )*/
	    			char lettre = (char)('A' + h.getId()-1);
	                int pvActuels = h.getPoints(); // les pv réel du héros
	                int pvMax = h.type.getPoints(); // les pv de base du héros
	                
	                // création jlabel pour nom
	                JLabel label = new JLabel(h.type + " (" + lettre + ")");
	                label.setAlignmentX(Component.CENTER_ALIGNMENT);
	                
	                // Création de la barre de progression
	                JProgressBar barre = new JProgressBar(0, pvMax);
	                barre.setValue(pvActuels);
	                barre.setStringPainted(true); // Affiche le texte sur la barre
	                barre.setString(pvActuels + " / " + pvMax + " PV");
	                
	                // Couleur de la barre selon la santé
	                if (pvActuels<pvMax / 5) {
	                	barre.setForeground(Color.RED);
	                }
	                else if (pvActuels< pvMax / 2) {
	                	barre.setForeground(Color.ORANGE);
	                }
	                else {
	                	barre.setForeground(Color.GREEN); 
	                }
	                container.add(label);
	                container.add(barre);
	                container.add(Box.createRigidArea(new Dimension(0, 8)));
	            
	                
	    		}
	                
	    	}
	    	
	    	// ca c pour recalculer la mise en page sinon parfois y'a des pbs
	    	container.revalidate();
	        container.repaint();
	    	
	    }
	    
	    /**
	     * Mise a jour de la barre de vie de monstre
	     * @param container JPanel
	     * @param carte Carte du jeu
	     */
	    
	    // même principe que la barre pour la vie des héros
	    public static void miseAJourBarreVieMonstre(JPanel container, Carte carte) {
	    	container.removeAll();
	        Monstre[] liste = carte.getLMonstres();
	        int i;
	        for (i=0 ; i < IConfig.NB_MONSTRES ; i++) {
	        	Monstre m =liste[i];
	        	if (m != null && m.getPos() != null) {
	        		
	                /* on regarde si c un troll, un orc ...
	        		et on recupere l'ID associé */
	                String nom = m.getNomType() + " " + m.getId();
	                int pvActuels = m.getPoints(); // les pv reel du monstre
	                int pvMax = m.type.getPoints(); // les pv de base du monstre

	                JLabel label = new JLabel(nom);
	                label.setFont(new Font("Arial", Font.PLAIN, 11));
	                label.setAlignmentX(Component.CENTER_ALIGNMENT);
	                
	                /* pareil ici on utilise un objet prédéfini en java
	                une barre progressive */
	                JProgressBar barre = new JProgressBar(0, pvMax);
	                barre.setValue(pvActuels);
	                barre.setStringPainted(true);
	                barre.setString(pvActuels + " PV");
	                
	                
	                
	                // on ne fait pas différente couleur comme pour les héros
	                barre.setForeground(Color.BLACK); 
	                barre.setBackground(Color.DARK_GRAY);

	                container.add(label);
	                container.add(barre);
	               
	        	
	            }
	        }
	        container.revalidate();
	        container.repaint();
	        
	        
	    }
	}
	
	