package bean;

import entity.Cat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ManageCatBean {

    /* ---------- campi ---------- */
    private final List<Cat> catList = new ArrayList<>();
    private Cat selected;

    /* ---------- costruttori ---------- */
    public ManageCatBean() { }

    public ManageCatBean(Cat selected) {
        this.selected = selected;
    }

    /* ---------- lista completa ---------- */
    public void setCatList(List<Cat> list) {
        catList.clear();
        if (list != null) catList.addAll(list);
    }

    public List<Cat> getCatList() {
        return Collections.unmodifiableList(catList);
    }

    /* ---------- selezione ---------- */
    public void setSelected(Cat cat) {
        this.selected = cat;
    }

    public Cat getSelected() {
        return selected;
    }

    public boolean isSelected() {
        return selected != null;
    }
}
