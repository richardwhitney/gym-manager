/*
 * Subclass of Person
 * Stores the Trainer's specialty
 */

public class Trainer  extends Person{

    private String speciality;

    /*
     * Constructor for Trainer
     */
    public Trainer(String email, String name, String address, String gender, String speciality) {
        // Call to super class constructor
        super(email, name, address, gender);
        setSpeciality(speciality);
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    /*
     * Returns a String containing each field of a Trainer
     * Plus each field of a Person
     */
    @Override
    public String toString() {
        String str = super.toString();
        str += "Speciality: " + speciality + "\n";

        return str;
    }
}
