package control;

import gui.Add;
import gui.List1;
import gui.Search1;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;

public class Manager {

    private Add add;
    private Save save;
    private List1 l1;
    private Search1 s1;

    public Manager(Add add) {
        this.add = add;
        save = Save.getInstance();
    }

    public Manager(List1 l1) {
        this.l1 = l1;
        save = Save.getInstance();
    }

    public Manager(Search1 s) {
        this.s1 = s;
        save = Save.getInstance();
    }

    public void add() {
        String code = add.getTxtCode().getText();
        String name = add.getTxtName().getText();
        String credit = add.getTxtCredit().getText();
        if (checkCode(code) == false) {
            return;
        }
        for (Course c : save.getList()) {
            if (checkCodeInList(code) == false) {
                return;
            }
        }
        if (checkName(name) == false) {
            return;
        }

        if (checkCredit(credit) == false) {
            return;
        }
        String normalName = normalName(name);
        Course course = new Course(code, normalName, Integer.parseInt(credit));
        save.add(course);
        add.dispose();
    }

    public boolean checkCode(String code) {
        if (code.trim().equals("")) {
            JOptionPane.showMessageDialog(add, "code is empty");
            return false;
        }
        return true;
    }

    public boolean checkCodeInList(String code) {
        for (Course c : save.getList()) {
            if (c.getCode().equals(code)) {
                JOptionPane.showMessageDialog(add, "dublicate code");
                return false;
            }
        }
        return true;
    }

    public boolean checkCredit(String credit) {
        int number = 0;
        try {
            number = Integer.parseInt(credit);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(add, "credit from 1 -33");
            return false;
        }
        if (number <= 0 || number > 33) {
            JOptionPane.showMessageDialog(add, "credit from 1 -33");
            return false;
        }
        return true;
    }

    public boolean checkName(String name) {
        if (name.trim().equals("")) {
            JOptionPane.showMessageDialog(add, "name can not empty");
            return false;
        }
        return true;
    }

    public void display() {
        l1.getTxt().setText("");
        l1.setResizable(false);
        List<Course> list = save.getList();
        Collections.sort(list);
        for (Course course : save.getList()) {
            l1.getTxt().setText(l1.getTxt().getText() + course.toString() + "\n");
        }
    }

    private String normalName(String s) {
        // thay the tat ca cac khoang trang bang dau cach sau do trim() de viet lien
        s = s.replaceAll("\\s+", " ").trim();
        //bien doi chuoi thanh chu thuong
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s);
        // viet hoa cac chu dau va viet thuong voi cac chu sau
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        for (int i = 1; i < sb.length(); i++) {
            if (sb.charAt(i) == ' ') {
                sb.setCharAt(i + 1, Character.toUpperCase(sb.charAt(i + 1)));
            }
        }
        return sb.toString();
    }
    
    

    public void search(String code) {
        for (Course course : save.getList()) {
            if (course.getCode().equalsIgnoreCase(code)) {
                s1.getTxtName().setText(course.getName());
                s1.getTxtCredit().setText("" + course.getCredit());
                return;
            }
        }
        s1.getTxtName().setText("Not Found");
        s1.getTxtCredit().setText("Not Found");
    }

    public void clear() {
        if (add.getTxtCode().getText().trim().length() <= 0
                && add.getTxtName().getText().trim().length() <= 0
                && add.getTxtCredit().getText().trim().length() <= 0) {
            JOptionPane.showMessageDialog(add, "Nothing to clear!");
        }
        add.getTxtCode().setText("");
        add.getTxtName().setText("");
        add.getTxtCredit().setText("");
    }
}
