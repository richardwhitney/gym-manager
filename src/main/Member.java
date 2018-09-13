/*
 * Subclass of Person
 * Stores a member's height, starting weight,
 * chosen package and a hashMap to record all the member's progress
 */

import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

public class Member extends Person {
    private float height;
    private float startWeight;
    protected String chosenPackage;
    private HashMap<String, Assessment> assessmentHashMap;

    /*
     * Constructor for Member
     */
    public Member(String email, String name, String address, String gender, float height,
                  float startWeight, String chosenPackage) {
        // Call to super class constructor
        super(email, name, address, gender);
        setHeight(height);
        setStartWeight(startWeight);
        this.chosenPackage = chosenPackage;
        assessmentHashMap = new HashMap<String, Assessment>();
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        // Height must be between 1 and 3 inclusive
        if (height >= 1.0f && height <= 3.0f) {
            this.height = height;
        }
    }

    public float getStartWeight() {
        return startWeight;
    }

    public void setStartWeight(float startWeight) {
        // Starting weight must be between 35 and 250
        if (startWeight >= 35.0f && startWeight <= 250.0f) {
            this.startWeight = startWeight;
        }
    }

    public String getChosenPackage() {
        return chosenPackage;
    }

    /*
     * A dummy implementation
     * Method is overridden in sub classes
     */
    public void chosenPackage(String chosenPackage) {

    }

    public HashMap<String, Assessment> getAssessments() {
        return assessmentHashMap;
    }

    /*
     * Returns a sorted set of date Strings
     * in chronological order
     */
    public SortedSet<String> sortedAssessmentDates() {
        // TreeSet guarantees ordering of its elements
        SortedSet<String> dates = new TreeSet<String>(getAssessments().keySet());

        return dates;
    }

    /*
     * Returns the latest assessment (based on chronological ordering)
     * in the member's assessment hashMap
     * Returns null if member has no assessments
     */
    public Assessment latestAssessment() {
        // Get date keys sorted in chronological order
        SortedSet<String> dates = sortedAssessmentDates();
        // Check that member has at least one assessment
        if (dates.size() > 0) {
            // Get the last element in the dates array (ie the latest one)
            String latestDate = dates.last();
            // Retrieve the assessment using the latestDate key
            Assessment assessment = getAssessments().get(latestDate);
            return assessment;
        }
        return null;
    }

    /*
     * Returns a String containing each field of a Member
     * Plus each field of a Person
     */
    @Override
    public String toString() {
        String str = super.toString();
        str +=  "Height: " + height + "\n" +
                "Weight: " + startWeight + "\n" +
                "Package: " + chosenPackage + "\n";

        return str;
    }
}

