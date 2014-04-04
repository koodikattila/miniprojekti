package koodikattila.viitehallinta;

import koodikattila.viitehallinta.gui.Gui;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Gui().setVisible(true);
            }
        });

    }
}
