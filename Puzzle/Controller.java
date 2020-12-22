package controller;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import time.TimeForPuzzle;
import view.View;

/**
 *
 * @author Hieu
 */
public class Controller {

    View v;
    int size;
    int count = 0;
    JButton[][] buttons;
    TimeForPuzzle t;
    boolean isWin;
    int RowBlank;
    int CollumBlank;

    public Controller() {
        v = new View();
        v.setVisible(true);
        newGame();
        clickNewGame();
    }

    public void clickNewGame() {
        v.getJBtnNew().addActionListener((ActionEvent e) -> {
            t.suspend();
            int Choose = (JOptionPane.showConfirmDialog(null, "Play new Game", "Choose", JOptionPane.YES_NO_OPTION));
            if (Choose == 0) {
                newGame();
            } else {
                t.resume();
            }

        });
    }

    public void newGame() {
        painJpanel();
        isWin = false;
        count = 0;
        this.v.getJLbMove().setText("Move count: " + count);
        t = new TimeForPuzzle(v);
        t.start();
    }

    public ArrayList<Integer> RandomNumber() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= size * size; i++) {
            list.add(i);
        }
        list = shuffle(list);
        return list;
    }

    public ArrayList<Integer> shuffle(ArrayList<Integer> list) {
        do {
            Collections.shuffle(list);
        } while (isSolvable(list) == false);
        return list;
    }
    public boolean isSolvable(ArrayList<Integer> listNumber)
{
    int parity = 0;
    int row = 0; // the current row we are on
    int blankRow = 0; // the row with the blank tile

    for (int i = 0; i < listNumber.size(); i++)
    {
        if (i % size == 0) { // advance to next row
            row++;
        }
        if (listNumber.get(i) == size*size) { // the blank tile
            blankRow = row; // save the row on which encountered
            continue;
        }
        for (int j = i + 1; j < listNumber.size(); j++)
        {
            if (listNumber.get(i) > listNumber.get(j) && listNumber.get(i) != size*size)
            {
                parity++;
            }
        }
    }

    if (size % 2 == 0) { // even grid
        if (blankRow % 2 == 0) { // blank on odd row; counting from bottom
            return parity % 2 == 0;
        } else { // blank on even row; counting from bottom
            return parity % 2 != 0;
        }
    } else { // odd grid
        return parity % 2 == 0;
    }
}

    public void painJpanel() {
        String s = this.v.getJCbxSize().getSelectedItem().toString();
        size = Integer.parseInt(s.split("x")[0]);
        ArrayList<Integer> list = RandomNumber();
        v.getjPanel1().removeAll();
        v.getjPanel1().setLayout(new GridLayout(size, size, 10, 10));
        buttons = new JButton[size][size];
        for (int row = 0; row < size; row++) {
            for (int collum = 0; collum < size; collum++) {
                String TextButton = list.get(row * size + collum).toString();
                if (TextButton.equals(size * size + "")) {
                    RowBlank = row;
                    CollumBlank = collum;
                    TextButton = "";
                }
                JButton b = new JButton(TextButton);
                b.setPreferredSize(new Dimension(50, 50));
                buttons[row][collum] = b;
                v.getjPanel1().add(b);
                addMoveButton(b);
            }
        }
        v.pack();
    }

    public boolean checkMove(int RowButton, int CollumButton) {
        if (isWin == true) {
            JOptionPane.showMessageDialog(null,"PLease New Game");
            return false;
        }
        //
        if ((RowButton - RowBlank) == 0 && abs(CollumButton - CollumBlank) == 1) {
            return true;
        } else if ((CollumButton - CollumBlank) == 0 && abs(RowButton - RowBlank) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void swapWithButtonBlank(int RowButtonSwap, int CollumButtonSwap) {
        JButton ButtonSwap = buttons[RowButtonSwap][CollumButtonSwap];
        JButton ButtonBlank = buttons[RowBlank][CollumBlank];
        //swap Text together;
        String TextSwap = ButtonBlank.getText();
        ButtonBlank.setText(ButtonSwap.getText());
        ButtonSwap.setText(TextSwap);
        //Get new Row and Collum;
        RowBlank = RowButtonSwap;
        CollumBlank = CollumButtonSwap;
    }

    public boolean checkWin() {
        boolean CheckWin=true;
        for (int Row = 0; Row < size; Row++) {
            for (int Collum = 0; Collum < size; Collum++) {
                String TextButtonArray = buttons[Row][Collum].getText();
                if (TextButtonArray == "") {                    
                    TextButtonArray = (size * size) + "";
                }
                String Textmatrix = (Row * size + Collum + 1) + "";
                if (TextButtonArray.equals(Textmatrix) == false) {
                    CheckWin=false;
                }
            }
        }
        return CheckWin;
    }

    public void addMoveButton(JButton button) {
        button.addActionListener((ActionEvent e) -> {
            for (int Row = 0; Row < size; Row++) {
                for (int Collum = 0; Collum < size; Collum++) {
                    if (buttons[Row][Collum] == button) {
                        if (checkMove(Row, Collum)) {
                            swapWithButtonBlank(Row, Collum);
                            count++;
                            this.v.getJLbMove().setText("Move count: " + count);
                            if (checkWin()) {
                                isWin = true;
                                t.stop();
                                JOptionPane.showMessageDialog(null, "YOU WIN");
                            }
                            break;
                        }
                    }
                }
            }
        });

    }

}
