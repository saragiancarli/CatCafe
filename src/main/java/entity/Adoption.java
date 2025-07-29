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

    /* ---------------- helpers ---------------- */

    private boolean isValidString(String value, int maxLength) {
        return value != null && !value.trim().isEmpty() && value.length() <= maxLength;
    }

    private boolean isValidPhone(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("\\d{10,15}");
    }

    private boolean isValidEmail(String emailValue) {
        return emailValue != null &&
                emailValue.length() <= 254 &&
                emailValue.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
    /* =================== METODI DI VALIDAZIONE =================== */

    public boolean hasValidName() {
        return isValidString(getName(), 100); // 100 chars max for name
    }

    public boolean hasValidSurname() {
        return isValidString(getSurname(), 100); // 100 chars max for surname
    }

    public boolean hasValidPhoneNumber() {
        return isValidPhone(getPhoneNumber());
    }

    public boolean hasValidEmail() {
        return isValidEmail(getEmail());
    }
    public boolean hasValidAddress() {
        return isValidString(getAddress(), 200); // 200 chars max for address
    }

    public boolean hasValidStatus() {
        return !getStateAdoption();
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