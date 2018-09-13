/*
 * Super class of Member and Trainer
 * Stores a person's email, name, address and gender
 * Email is used to uniquely identify a person in the system
 */

public class Person {

    private String email;
    private String name;
    private String address;
    private String gender;

    /*
     * Constructor to Person
     */
    public Person(String email, String name, String address, String gender) {
        this.email = email;
        setName(name);
        this.address = address;
        setGender(gender);
    }

    public void setName(String name) {
        // Name is a maximum of 30 characters
        if (name.length() <= 30) {
            this.name = name;
        }
        // If name is above 30 characters, truncate it to 30 characters
        else {
            this.name = name.substring(0, 30);
        }

    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(String gender) {
        // Gender can be either 'M' or 'F'
        if ((gender.equals("m") || gender.equals("M")) || (gender.equals("f") || gender.equals("F"))) {
            this.gender = gender.toUpperCase();
        }
        // If it is not either of these then apply a default value of "Unspecified"
        else {
            this.gender = "Unspecified";
        }
    }

    public String getName() {
        return name;
    }

    public String getEmail() {

        return email;
    }

    public String getAddress() {

        return address;
    }

    public String getGender() {

        return gender;
    }

    /*
     * Returns a String containing each field of a Person
     */
    @Override
    public String toString() {
        return  "Name: " + name + "\n" +
                "Email: " + email + "\n" +
                "Address:" + address + "\n" +
                "Gender: " + gender + "\n";
    }
}
