package entity;

import javafx.beans.property.*;

public class Cat {

    private final IntegerProperty idCat = new SimpleIntegerProperty(); // 👈 ID aggiunto
    private final StringProperty nameCat = new SimpleStringProperty();
    private final StringProperty race = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final IntegerProperty age = new SimpleIntegerProperty();
    private final BooleanProperty stateAdoption = new SimpleBooleanProperty(false);

    /* ---------------- Getters/Setters/Properties ---------------- */

    public int getIdCat() {
        return idCat.get();
    }

    public void setIdCat(int idCat) {
        this.idCat.set(idCat);
    }

    public IntegerProperty idCatProperty() {
        return idCat;
    }

    public String getNameCat() {
        return nameCat.get();
    }

    public void setNameCat(String nameCat) {
        this.nameCat.set(nameCat);
    }

    public StringProperty nameCatProperty() {
        return nameCat;
    }

    public String getRace() {
        return race.get();
    }

    public void setRace(String race) {
        this.race.set(race);
    }

    public StringProperty raceProperty() {
        return race;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public int getAge() {
        return age.get();
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    public boolean isStateAdoption() {
        return stateAdoption.get();
    }

    public void setStateAdoption(boolean stateAdoption) {
        this.stateAdoption.set(stateAdoption);
    }

    public BooleanProperty stateAdoptionProperty() {
        return stateAdoption;
    }

    /* ---------------- Debug ---------------- */
    @Override
    public String toString() {
        return "Cat[" +
                "id=" + idCat.get() +
                ", name='" + nameCat.get() + '\'' +
                ", race='" + race.get() + '\'' +
                ", age=" + age.get() +
                ", adopted=" + stateAdoption.get() +
                ", description=" + description.get() + '\'' +
                ", status=" + (isStateAdoption() ? "Adottato" : "Da adottare") +
                ']';
    }
}
