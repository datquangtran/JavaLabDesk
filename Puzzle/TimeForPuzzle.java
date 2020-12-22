/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package time;

import view.View;

/**
 *
 * @author Administrator
 */
public class TimeForPuzzle extends Thread {

    View v;

    public TimeForPuzzle(View view) {
        this.v=view;
    }

    @Override
    public void run() {
        int time = 0;
        while (true) {
            try {
                this.v.getJLbTime().setText("Elapsed: " + time + " sec");
                time++;
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
