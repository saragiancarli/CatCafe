package bean;


public class RequestAdoptionBean {

    private String nameCat;
    private String phoneNumber;
    private String name;
    private String surname;
    private String email;
    private String address;
    private boolean stateAdoption;

    // =================== Getter & Setter =================== //

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

    public boolean isStateAdoption() {
        return stateAdoption;
    }

    public void setStateAdoption(boolean stateAdoption) {
        this.stateAdoption = stateAdoption;
    }

    // =================== Coerenza e correttezza dati =================== //
    public boolean hasValidName() {
        return name != null && !name.trim().isEmpty();
    }

    public boolean hasValidSurname() {
        return surname != null && !surname.trim().isEmpty();
    }

    public boolean hasValidPhoneNumber() {
        return phoneNumber != null && phoneNumber.matches("\\d{10,}");
    }

    public boolean hasValidEmail() {
        return email != null && email.contains("@") && email.contains(".");
    }

    public boolean hasValidAddress() {
        return address != null && !address.isBlank();
    }

    public boolean hasValidStatus() {
        return !stateAdoption; //
    }
}
