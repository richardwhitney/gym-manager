/*
 * Operates between the model classes and the menu driver class
 * Contains a series of methods that allow a user to interact with the database
 */

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.SortedSet;


public class GymAPI {
    // Store a list of all registered members
    private ArrayList<Member> members;
    // Store a list of all registered trainers
    private ArrayList<Trainer> trainers;

    /*
     * Constructor for GymAPI
     */
    public GymAPI() {
        // Initialise both lists
        this.members = new ArrayList<Member>();
        this.trainers = new ArrayList<Trainer>();
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public ArrayList<Trainer> getTrainers() {
        return trainers;
    }

    /*
     * Add a member to the members array
     */
    public void addMember(Member member) {
        members.add(member);
    }

    /*
     * Add a trainer to the trainers array
     */
    public void addTrainer(Trainer trainer) {
        trainers.add(trainer);
    }

    /*
     * Returns the size of the members array
     */
    public int numberOfMembers() {
        return members.size();
    }

    /*
     * Returns the size of the trainers array
     */
    public int numberOfTrainerss() {
        return trainers.size();
    }

    /*
     * Returns a boolean indicating if the index passed as a parameter
     * is a valid index for the members array list
     */
    public boolean isValidMemberIndex(int index) {
        // Check that the members is not empty so that we don't get
        // an index out of bounds exception
        if (!members.isEmpty()) {
            if (index >= 0 && index < members.size()) {
                return true;
            }
        }
        return false;
    }

    /*
     * Returns a boolean indicating if the index passed as a parameter
     * is a valid index for the trainers array list
     */
    public boolean isValidTrainerIndex(int index) {
        // Check that the members is not empty so that we don't get
        // an index out of bounds exception
        if (!trainers.isEmpty()) {
            if (index >= 0 && index < trainers.size()) {
                return true;
            }
        }
        return false;
    }

    /*
     * Returns the member object that matches the email entered
     * If no member matches, return null
     */
    public Member searchMembersByEmail(String emailEntered) {
        // Search each member in the members array for a matching email
        for (Member member : members) {
            if (emailEntered.equals(member.getEmail())) {
                // Return the member with a matching email
                return member;
            }
        }
        return null;
    }

    /*
     * Returns a list of member names that partially or entirely
     * matches the entered name. An empty array is returned when there are no matches
     */
    public ArrayList<String> searchMembersByName(String nameEntered) {
        // Create an empty array list to store member names
        ArrayList<String> memberNames = new ArrayList<String>();
        // Search each member in the members array for a partial/matching name
        for (Member member : members) {
            // Ignore the case of the members name and the entered name
            if (member.getName().toLowerCase().contains(nameEntered.toLowerCase())) {
                // A match was found so add it to the member names array list
                memberNames.add(member.getName());
            }
        }
        return memberNames;
    }

    /*
     * Returns the trainer object that matches the email entered
     * If no trainer matches, return null
     */
    public Trainer searchTrainersByEmail(String emailEntered) {
        // Search each member in the trainers array for a matching email
        for (Trainer trainer : trainers) {
            if (emailEntered.equals(trainer.getEmail())) {
                // Return the trainer with a matching email
                return trainer;
            }
        }
        return null;
    }

    /*
     * Returns a list of trainer names that partially or entirely
     * matches the entered name. An empty array is returned when there are no matches
     */
    public ArrayList<String> searchTrainersByName(String nameEntered) {
        // Create an empty array list to stroe trainers names
        ArrayList<String> trainerNames = new ArrayList<String >();
        // Search each trainer in the trainers array for a partial/matching name
        for (Trainer trainer : trainers) {
            // Ignore the case of the trainer's name and the entered name
            if (trainer.getName().toLowerCase().contains(nameEntered.toLowerCase())) {
                // A match was found so add it to the trainer names array list
                trainerNames.add(trainer.getName());
            }
        }
        return trainerNames;
    }

    /*
     * Returns a list containing all the members in the gym
     * Returns an empty list if none are found
     */
    public ArrayList<Member> listMembers() {
        return members;
    }

    /*
     * Returns a list containing all the members in the gym whose latest assessment
     * weight is an ideal weight (based on the devine method)
     * Returns an empty list if none are found
     */
    public ArrayList<Member> listMembersWithIdealWeight() {
        // Create an empty list to store members with an ideal weight
        ArrayList<Member> membersWithIdealWeight = new ArrayList<Member>();
        // Iterate through each member in the members array list
        for (Member member : members) {
            // Retrieve the member's latest assessment
            Assessment latestAssessment = member.latestAssessment();
            // Check that the member has an assessment so that we don't get a null pointer exception
            if (latestAssessment != null) {
                // GymUtility's isIdealBodyWeight method returns a boolean indicting
                // if the member has an ideal body weight based on their latest assessment
                if (GymUtility.isIdealBodyWeight(member, latestAssessment)) {
                    // Member has an ideal body weight so add it to membersWithIdealWeight array list
                    membersWithIdealWeight.add(member);
                }
            }
        }
        return membersWithIdealWeight;
    }

    /*
     * Returns a string containing all members in the gym whose BMI category
     * (based on the latest assessment weight) partially or entirely matches the entered category
     * Returns an empty list if none are found
     */
    public ArrayList<Member> listMembersBySpecificBMICategory(String category) {
        // Create an empty list to store member that partially/matches the entered category
        ArrayList<Member> membersInCategory = new ArrayList<Member>();
        // Iterate through each member in the members array list
        for (Member member : members) {
            // Retrieve the member's latest assessment
            Assessment latestAssessment = member.latestAssessment();
            // Check that the member has an assessment so that we don't get a null pointer exception
            if (latestAssessment != null) {
                // Calculate and store the member's BMI based on their latest assessment
                double bmi = GymUtility.calculateBMI(member, latestAssessment);
                // GymUtility's determineBMICategory method returns a string based on the members
                // BMI value. Check this string for matches ignoring case of entered category
                if (GymUtility.determineBMICategory(bmi).contains(category.toUpperCase())) {
                    // A match was found so add it to the membersInCategory array list
                    membersInCategory.add(member);
                }
            }
        }
        return membersInCategory;
    }

    /*
     * Returns a String of each member's latest assessment weight and height
     * both imperially and metrically.
     * Returns a custom string if there are no members in the gym
     */
    public String listMemberDetailsImperialAndMetric() {
        // Check if there are members in the gym
        if (members.size() != 0) {
            // Create an empty String to store member details
            String memberDetails = "";
            // Iterate through each member in the members array list
            for (Member member : members) {
                // Retrieve the member's latest assessment
                Assessment latestAssessment = member.latestAssessment();
                // Check that the member has an assessment so that we don't get a null pointer exception
                if (latestAssessment != null) {
                    // Retrieve the member's latest assessment weight
                    float weight = latestAssessment.getWeight();
                    float height = member.getHeight();
                    // Concatenate the memberDetails String with each member
                    memberDetails += member.getName() + ": " + Math.round(weight) + " kg(" +
                            Math.round(GymUtility.kilosToPounds(weight)) + " lbs) " +
                            GymUtility.round(height, 1) + " metres(" + Math.round(GymUtility.metersToInches(height)) +
                            " inches).\n";
                }
            }
            return memberDetails;
        }
        else {
            return "No registered members";
        }
    }

    /*
     * Returns a String containing all of members assessments (from oldest to latest)
     * with an arrow indicating if their weight has gone up, down or is unchanged
     * compared to their previous assessment
     */
    public String assessmentProgressByWeight(Member member) {
        // Create an array list from the member's SortedSet of assessment dates
        ArrayList<String> assessmentDates = new ArrayList<String>(member.sortedAssessmentDates());
        // Create an empty array list to store each assessment in chronological order
        ArrayList<Assessment> assessments = new ArrayList<Assessment>();
        // Create an empty string to store all the member's assessment details
        String weightProgressReport = "";
        // Iterate through each date String in assessmentDates
        for (String date : assessmentDates) {
            // Use the date string to get member's assessment from their assessment hashMap
            // Add the assessment to the assessments array (they will be added in chronological order
            // because the array assessmentDates is sorted in chronological order)
            assessments.add(member.getAssessments().get(date));
        }

        // Iterate through each assessment in assessments array and compare it to the previous one
        // (Used assessmentDates.size() as loop condition but could have used assessments.size() as they are the same size)
        for (int i = 0; i < assessmentDates.size(); i++) {
            // Don't compare assessment with previous one in the list if it is the first element in the list
            // as this would create an index out of bounds exception
            if (i == 0) {
                // Concatenate String with assessment date and weight
                weightProgressReport += assessmentDates.get(i) + ": " + "weight - " + assessments.get(i).getWeight()+"\n";
            }
            else {
                // Current assessment lower than previous one
                if (GymUtility.compareAssessmentByWeight(assessments.get(i), assessments.get(i-1)) == 1) {
                    // Concatenate String with assessment date, weight and a down arrow
                    weightProgressReport += assessmentDates.get(i) + ": " + "weight - " + assessments.get(i).getWeight() + " \u2193" +"\n";
                }
                // Current assessment higher than previous one
                else if (GymUtility.compareAssessmentByWeight(assessments.get(i), assessments.get(i-1)) == -1) {
                    // Concatenate String with assessment date, weight and an up arrow
                    weightProgressReport += assessmentDates.get(i) + ": " + "weight - " + assessments.get(i).getWeight() + " \u2191" +"\n";
                }
                // Current assessment equal to previous one
                else {
                    // Concatenate String with assessment date, weight and a right arrow
                    weightProgressReport += assessmentDates.get(i) + ": " + "weight - " + assessments.get(i).getWeight() + " \u2192" +"\n";
                }
            }
        }
        return weightProgressReport;
    }

    /*
     * Returns a String containing all of members assessments (from oldest to latest)
     * with an arrow indicating if their waist has gone up, down or is unchanged
     * compared to their previous assessment
     */
    public String assessmentProgressByWaist(Member member) {
        // Create an array list from the member's SortedSet of assessment dates
        ArrayList<String> assessmentDates = new ArrayList<String>(member.sortedAssessmentDates());
        // Create an empty array list to store each assessment in chronological order
        ArrayList<Assessment> assessments = new ArrayList<Assessment>();
        // Create an empty string to store all the member's assessment details
        String waistProgressReport = "";
        // Iterate through each date String in assessmentDates
        for (String date : assessmentDates) {
            // Use the date string to get member's assessment from their assessment hashMap
            // Add the assessment to the assessments array (they will be added in chronological order
            // because the array assessmentDates is sorted in chronological order)
            assessments.add(member.getAssessments().get(date));
        }
        // Iterate through each assessment in assessments array and compare it to the previous one
        // (Used assessmentDates.size() as loop condition but could have used assessments.size() as they are the same size)
        for (int i = 0; i < assessmentDates.size(); i++) {
            // Don't compare assessment with previous one in the list if it is the first element in the list
            // as this would create an index out of bounds exception
            if (i == 0) {
                // Concatenate String with assessment date and waist
                waistProgressReport += assessmentDates.get(i) + ": " + "waist - " + assessments.get(i).getWaist()+"\n";
            }
            else {
                // Current assessment lower than previous one
                if (GymUtility.compareAssessmentByWaist(assessments.get(i), assessments.get(i-1)) == 1) {
                    // Concatenate String with assessment date, waist and a down arrow
                    waistProgressReport += assessmentDates.get(i) + ": " + "waist - " + assessments.get(i).getWaist() + " \u2193" +"\n";
                }
                // Current assessment higher than previous one
                else if (GymUtility.compareAssessmentByWaist(assessments.get(i), assessments.get(i-1)) == -1) {
                    // Concatenate String with assessment date, waist and an up arrow
                    waistProgressReport += assessmentDates.get(i) + ": " + "waist - " + assessments.get(i).getWaist() + " \u2191" +"\n";
                }
                // Current assessment equal to previous one
                else {
                    // Concatenate String with assessment date, waist and a right arrow
                    waistProgressReport += assessmentDates.get(i) + ": " + "waist - " + assessments.get(i).getWaist() + " \u2192" +"\n";
                }
            }
        }
        return waistProgressReport;
    }

    /*
     * Stores contents of members array list to file
     * Stores contents of trainers array list to file
     * TODO - Store both members and trainers to one file as list of Person objects
     */
    public void store() throws Exception {
        XStream xStream = new XStream(new DomDriver());

        // Store contents of members ArrayList
        ObjectOutputStream outMembers = xStream.createObjectOutputStream(new FileWriter("members.xml"));
        outMembers.writeObject(members);
        outMembers.close();

        // Store contents of trainers ArrayList
        ObjectOutputStream outTrainers = xStream.createObjectOutputStream(new FileWriter("trainers.xml"));
        outTrainers.writeObject(trainers);
        outTrainers.close();
    }

    /*
     * Loads contents of members file to members array list
     * Loads contents of trainers file to trainers array list
     * TODO - Could load contents of persons file, check the actual type of each person and add to appropriate array
     */
    @SuppressWarnings("unchecked")
    public void load() throws Exception {
        XStream xStream = new XStream(new DomDriver());

        // Load contents of members ArrayList
        ObjectInputStream inMembers = xStream.createObjectInputStream(new FileReader("members.xml"));
        members = (ArrayList<Member>)inMembers.readObject();
        inMembers.close();

        // Load contents of trainers ArrayList
        ObjectInputStream inTrainers = xStream.createObjectInputStream(new FileReader("trainers.xml"));
        trainers = (ArrayList<Trainer>)inTrainers.readObject();
        inTrainers.close();
    }
}
