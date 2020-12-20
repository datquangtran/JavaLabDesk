/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import Controll.ControllerPuzzle;
import View.ViewPuzzle;

/**
 *
 * @author Dung
 */
public class Menu {

    public static void main(String[] args) {
        ViewPuzzle view = new ViewPuzzle();
        ControllerPuzzle controll = new ControllerPuzzle(view);
        
        view.setVisible(true);
        view.setLocationRelativeTo(null);
        
        controll.comboBox();
        controll.runGame();
        
    }
}
