package entity;

import javafx.beans.property.*;

public class Cat {
    private final StringProperty nameCat = new SimpleStringProperty();
    private final StringProperty race = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final IntegerProperty age = new SimpleIntegerProperty();
    //private final ObjectProperty<AdoptionStatus> adoptionStatus =
           // new SimpleObjectProperty<>(AdoptionStatus.AVAILABLE);
    /* ---------------- Getters/Setters/Properties ---------------- */
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
    }public StringProperty raceProperty() {
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
        this.age.set(age); }

    public IntegerProperty ageProperty() {
        return age;
    }

    //public AdoptionStatus getAdoptionStatus() {
      //  return adoptionStatus.get();
    }

   // public void setAdoptionStatus(AdoptionStatus status) {
      //  this.adoptionStatus.set(status);}

  //  public ObjectProperty<AdoptionStatus> adoptionStatusProperty() {
       // return adoptionStatus;}















