package entity;


public class Cat {

    private int idCat;
    private String nameCat;
    private String race;
    private String description;
    private int age;
    private boolean stateAdoption = false;

    /* ---------------- Getters/Setters/Properties ---------------- */

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat=idCat;
    }

    public String getNameCat() {
        return nameCat;
    }

    public void setNameCat(String nameCat) {
        this.nameCat=nameCat;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race=race;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description=description;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age=age;
    }

    public boolean isStateAdoption() {
        return stateAdoption;
    }

    public void setStateAdoption(boolean stateAdoption) {
        this.stateAdoption=stateAdoption;
    }

    /* ---------------- Debug ---------------- */
    @Override
    public String toString() {
        return "Cat[" +
                "id=" + idCat +
                ", name='" + nameCat + '\'' +
                ", race='" + race + '\'' +
                ", age=" + age +
                ", adopted=" + stateAdoption +
                ", description=" + description + '\'' +
                ", status=" + (isStateAdoption() ? "Adottato" : "Da adottare") +
                ']';
    }
}
