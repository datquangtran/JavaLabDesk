package control;

import java.util.ArrayList;
import java.util.List;

public class Save {
    List<Course> list = new ArrayList<>();
    private static Save instance;
    public Save() {
    }
    public static Save getInstance() {
        if (instance == null) {
            instance = new Save();
        }
        return instance;
    }

    public List<Course> getList() {
        return list;
    }

    public void setList(List<Course> list) {
        this.list = list;
    }

    public void add(Course c) {
        list.add(c);
    }

}
