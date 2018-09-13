/*
 * Subclass of Member
 * Stores studentId and collegeName
 */

public class StudentMember extends Member  {

    private String studentid;
    private String collegeName;

    /*
     * Constructor for StudentMember
     */
    public StudentMember(String email, String name, String address, String gender, float height,
                         float startWeight, String chosenPackage, String studentid, String collegeName) {
        // Call to super constructor
        super(email, name, address, gender, height, startWeight, chosenPackage);
        setStudentid(studentid);
        setCollegeName(collegeName);
        chosenPackage(chosenPackage);
    }

    /*
     * Concrete implementation of Member's chosenPackage method
     * The chosenPackage is set to the package associated with
     * their collegeName. If there is no package associated with their college,
     * default to "Package 3"
     */
    public void chosenPackage(String chosenPackage) {
        if (collegeName.toUpperCase().equals("WIT")) {
            this.chosenPackage = "WIT";
        }
        else {
            this.chosenPackage = "Package 3";
        }
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    /*
     * Returns a String containing each field of a StudentMember
     * Plus each field of a member and Person
     */
    @Override
    public String toString() {
        String str = super.toString();
        str +=  "Student id: " + studentid + "\n" +
                "College Name: " + collegeName + "\n\n";

        return str;
    }
}
