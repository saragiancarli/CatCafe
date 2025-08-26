package entity;

public class Adoption {

    private String  nameCat;
    private String  phoneNumber;
    private String  name;
    private String  surname;
    private String  email;
    private String  address ;
    private boolean  stateAdoption =false;

    /* ---------------- getter/setter/property ---------------- */
    public String getNameCat() {
        return nameCat;
    }

    public void setNameCat(String nameCat) {
        this.nameCat = nameCat;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean getStateAdoption() {
        return stateAdoption;
    }

    public void setStateAdoption(boolean stateAdoption) {
        this.stateAdoption = stateAdoption;
    }

    @Override
    public String toString() {
        return "Adoption[" +
                "nameCat='" + nameCat + '\'' +
                ", name='" + name + " " + surname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", status=" + (stateAdoption ? "Adottato" : "Da adottare") +
                ']';
    }
}