/*
 * Subclass of Member
 * Stores no additional data
 */

public class PremiumMember extends Member  {

    /*
     * Constructor of PremiumMember
     */
    public PremiumMember(String email, String name, String address, String gender,
                         float height, float startWeight, String chosenPackage) {
        // Call to super class constructor
        super(email, name, address, gender, height, startWeight, chosenPackage);
    }

    /*
     * Concrete implementation of Member's chosenPackage method
     * chosenPackage is set to the value passed as a parameter
     * There is no validation on the entered data (at this stage)
     */
    public void chosenPackage(String chosenPackage) {
        this.chosenPackage = chosenPackage;
    }

    /*
     * Returns a String containing each field of a PremiumMember
     * Plus each field of a Person
     */
    @Override
    public String toString() {
        String str = super.toString();
        str += "\n";
        return str;
    }
}
