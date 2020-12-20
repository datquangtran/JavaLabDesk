/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controll;

import View.ViewPuzzle;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Dung
 */
public class ControllerPuzzle {

    ViewPuzzle view;
    private int time = 1; // dem time bat dau chay
    private ArrayList<JButton> button; //list button
    Thread t;
    private int size = 3; //size 3*3
    private int sizeButton = 60; //kich co button
    private int pos; //vi tri cua nut button rong
    private int countClick = 0; //dem so lan bam

    public ControllerPuzzle(ViewPuzzle view) {
        this.view = view;
    }

    public void runGame() {
        runPuzzle();
        eventNewGameButTon();
    }

    /**
     * GridLayout khoang cach giua 2 nut button loadButton
     */
    public void runPuzzle() {
        view.getjPanel1().setLayout(new GridLayout(size, size, 5, 5));
        LoadButton();
    }

    public void runTime() {
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    view.getLbTimeCount().setText(time + " Sec");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    }
                    time++;
                }
            }
        });
    }

    /**
     * load ra button de choi
     *
     */
    public void LoadButton() {
        button = new ArrayList<>();
        view.getjPanel1().removeAll();
        for (int i = 1; i <= size * size; i++) {
            JButton bt = new JButton(i + ""); //tao button moi 
            if (bt.getText().equals(size * size + "")) { //neu button vua tao bang 9 thi button +"";
                bt.setText("");
            }
            Event(bt);
            button.add(bt); //add bt vao list
            view.getjPanel1().add(bt);//hien button vao panel
        }
        setsizeWindown();
        pos = size * size - 1; //pos vi tri cuoi cung trong list button
        changeButton(size);
    }

    /**
     * tao random, cho random 1000 lan
     *
     * random vi tri cua nut pos
     */
    public void changeButton(int size) {
        Random r = new Random(); //tao bien ramdom
        for (int i = 0; i < 1000; i++) { //random vi tri nut rong (pos)
            int a = r.nextInt(4) + 1; //ramdon tu 0-3, sau cong 1 thanh 1-4 //chay switch case
            //khi o dong tren cung roi thi case 1 se k thoa man nua nen bo qua vong 1 for
            if (pos >= 0 && pos < size && a == 1) { // vi tri nut rong dang dong dau
                continue; //bo qua 1 vong for
            }
            if (pos >= size * size - size && pos < size * size && a == 2) { //dong cuoi
                continue;
            }
            if (pos % size == 0 && a == 3) { //0,3,6 cot ben trai ngoai cung
                continue;
            }
            if (pos % size == size - 1 && a == 4) { //2,4,8: cot ben phai ngoai cung
                continue;
            }
            switch (a) {
                case 1://doi cho nut rong voi thang ben tren
                    changeBut(pos, pos - size);
                    pos = pos - size; //lay index luon cua pos
                    break;
                case 2://doi cho nut rong voi thang ben duoi
                    changeBut(pos, pos + size);
                    pos = pos + size;

                    break;
                case 3://doi cho nut rong voi thang ben trai
                    changeBut(pos, pos - 1);
                    pos = pos - 1;
                    break;
                case 4://doi cho nut rong voi thang ben phai
                    changeBut(pos, pos + 1);
                    pos = pos + 1;

                    break;
                default:
                    break;
            }
        }
    }

    /**
     * doi cho 2 button (doi text trong button)0
     */
    public void changeBut(int a, int b) {
        String index = button.get(a).getText(); //lay text trong button
        button.get(a).setText(button.get(b).getText());//button tai vi tri a
        button.get(b).setText(index);
    }

    /**
     * @param dim: lay chieu dai+chieu rong cua man hinh
     * @param sizeCom: lay kich thuoc cua trong combobox
     */
    public void comboBox() {
        Toolkit toolkit = Toolkit.getDefaultToolkit(); //lay size cua man hinh
        Dimension dim = toolkit.getScreenSize();
        int sizeCom = dim.height / (sizeButton + 5); //size man hinh/size button
        view.getjPanel1().setSize(dim);
        ArrayList<String> com = new ArrayList<>();
        for (int i = 3; i < sizeCom; i++) { //lay size load vao combobox, mac dinh 3*3
            String a = i + "x" + i;
            com.add(a);
        }
        DefaultComboBoxModel cbx = new DefaultComboBoxModel(com.toArray());
        view.getjComboBox1().setModel(cbx);
    }

    /**
     * nut new game
     */
    public void eventNewGameButTon() {
        view.getjButton1().addActionListener((ActionEvent e) -> {
            int confirm = JOptionPane.showConfirmDialog(view, "Do you really want to start new game?",
                    "Confirm Dialog", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                countClick = 0;
                view.getLbMoveCount().setText(countClick + "");
                t.stop();
                time = 0;
                view.getLbTimeCount().setText(time + " Sec");
                String[] value = view.getjComboBox1().getSelectedItem().toString().split("x");
                size = Integer.parseInt(value[0]);
                runPuzzle();
            }
        });
    }

    //set size cho panel
    //khi tawng kich thuoc button-->tang kich thuoc panel
    public void setsizeWindown() {
        view.getjPanel1().setPreferredSize(new Dimension(sizeButton * size, sizeButton * size));
        view.pack();
    }

    /**
     * neu vi tri click doi vi chi cua pos i la vi tri nut bam
     */
    private void clicked(int i) {
        if (i + size <= size * size - 1 && button.get(i + size).getText().equals("")) {
            changeBut(i, i + size); //bam ben tren
            System.out.println("ben tren");
        } else if (i - size >= 0 && button.get(i - size).getText().equals("")) {
            changeBut(i, i - size);// bam ben duoi
            System.out.println("ben duoi");
        } else if (i % size != (size - 1) && button.get(i + 1).getText().equals("")) {
            changeBut(i, i + 1); //bam ben trai
            System.out.println("ben trai");
        } else if (i % size != 0 && button.get(i - 1).getText().equals("")) {
            changeBut(i, i - 1);
            System.out.println("ben phai");
        }
        checkwin();
    }

    public void Event(JButton a) {
        a.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (countClick == 0) {
                    runTime();
                    t.start();
                }
                countClick++;
                view.getLbMoveCount().setText(countClick + "");
                for (int i = 0; i < button.size(); i++) {
                    if (evt.getSource().equals(button.get(i))) {
                        clicked(i);
                        break;
                    }
                }
            }
        });
    }

    /**
     * moi lan click checkwin 1 lan
     */
    public void checkwin() {
        int checkwin = 1;
        for (int i = 0; i < button.size() - 1; i++) {
            if (button.get(i).getText().equals(checkwin + "")) {
                checkwin++;
            } else {
                break;
            }
        }
        if (checkwin == size * size) {
            t.stop();
            JOptionPane.showMessageDialog(view, "You Win \n Move Count: " + view.getLbMoveCount().getText()
                    + "\nTime: " + view.getLbTimeCount().getText() + "");
            for (JButton jButton : button) {
                jButton.setVisible(false);
            }
        }
    }
}
