package wargame;

import java.awt.*;
import javax.swing.*;

public class FenetreJeu {

    public static void main(String[] args) {

        Carte c = new Carte();

        JFrame frame = new JFrame("WarGame - Prototype");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // --- MENU HAUT ---
        JMenuBar menuBar = new JMenuBar();

        JMenu menuFichier = new JMenu("Fichier");
        JMenuItem quitter = new JMenuItem("Quitter");
        quitter.addActionListener(e -> System.exit(0));
        menuFichier.add(quitter);

        JMenu menuJeu = new JMenu("Jeu");
        JMenuItem reset = new JMenuItem("Réinitialiser");
        menuJeu.add(reset);

        menuBar.add(menuFichier);
        menuBar.add(menuJeu);

        frame.setJMenuBar(menuBar);


        // --- PANEL JEU ---
        JLabel barreInfo = new JLabel("Bienvenue dans WarGame.", SwingConstants.CENTER);
        barreInfo.setPreferredSize(new Dimension(192, 30));
        barreInfo.setFont(new Font("Arial", Font.BOLD, 14));
        barreInfo.setOpaque(true);
        barreInfo.setBackground(Color.DARK_GRAY);
        barreInfo.setForeground(Color.WHITE);

        PanneauJeu panel = new PanneauJeu(c,barreInfo);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(barreInfo, BorderLayout.SOUTH);

        frame.setSize(192*8, 108*8);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
