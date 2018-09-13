/*
 * This class uses the console I/O to interact with the user
 * It stores an instance of the GymAPI class and
 * allows the user to navigate (a subset of) the system's
 * features through a series of menus
 */
import java.util.*;

public class MenuController {

    // Used to capture user input
    private Scanner input = new Scanner(System.in);
    private GymAPI gymAPI;
    // Stores the user currently logged in
    private Person loggedInUser;
    // The packages available to members
    private HashMap<String, String> packages;

    public static void main(String[] args) {
        MenuController menuController = new MenuController();
        menuController.welcomeMenu();
    }

    /*
     * Constructor for MenuController
     */
    public MenuController() {
        gymAPI = new GymAPI();

        // Populate the packages hash map
        packages = new HashMap<String, String>();
        packages.put("Package 1", "Allowed access anytime to gym.\nFree access to all classes.\nAccess to all changing areas including deluxe changing rooms.");
        packages.put("Package 2", "Allowed access anytime to gym.\n€3 fee for all classes.\nAccess to all changing areas including deluxe changing rooms");
        packages.put("Package 3", "Allowed access to gym at off-peak times.\n€5 fee for all classes.\nNo access to deluxe changing rooms.");
        packages.put("WIT", "Allowed access to gym during term time.\n€4 fee for all classes.\nNo access to deluxe changing rooms.");

        // Load gym data (trainers and members) from XML file
        try {
            gymAPI.load();
        }
        catch (Exception e) {
            System.err.println("Error loading from file: " + e);
        }
    }

    /*
     * First 'screen' the user sees when the app starts
     * Presents the user with a series of choices and determines a course of
     * action based on those choices
     */
    private void welcomeMenu() {
        // Used to determine if the user has entered an acceptable input
        boolean goodInput = false;
        // Used to store whether the user wants to login or register
        char loginRegisterChoice = ' ';
        // Used to store whether the user is a member or a trainer
        char memberTrainerChoice = ' ';
        // Keep looping until the user enters an acceptable input
        while (!goodInput) {
            System.out.println("Welcome to the GYM");
            System.out.println("Would you like to Login(l) or Register(r)?");
            loginRegisterChoice = input.next().charAt(0);
            // Check if user entered an acceptable input
            if ((loginRegisterChoice == 'l') || (loginRegisterChoice == 'L') ||
                    (loginRegisterChoice == 'r') || (loginRegisterChoice == 'R')) {
                goodInput = true;
            }
            else {
                System.out.println("Please choose a valid response. (l/r)");
            }
        }
        goodInput = false;
        // Keep looping until the user enters an acceptable input
        while (!goodInput) {
            System.out.println("Are you a member(m) or a trainer(t)?");
            memberTrainerChoice = input.next().charAt(0);
            // Check if user entered an acceptable input
            if ((memberTrainerChoice == 'm') || (memberTrainerChoice == 'M') ||
                    (memberTrainerChoice == 't') || (memberTrainerChoice == 'T')) {
                goodInput = true;
            }
            else {
                System.out.println("Please choose a valid response. (m/t)");
            }
        }
        // User chose to login
        if (loginRegisterChoice == 'l' || loginRegisterChoice == 'L') {
            login(memberTrainerChoice);
        }
        // User chose to register
        else if (loginRegisterChoice == 'r' || loginRegisterChoice == 'R'){
            register(memberTrainerChoice);
        }


    }

    /*
     * Prints the login 'screen' when the user chooses to login
     * Asks the user for their email
     * Searches the relevant array list based on the userType passed as a parameter
     * Upon successful login, runs the relevant menu based on userType
     * Exits the application if no matching email is found
     */
    private void login(char userType) {
        // Dummy read
        input.nextLine();
        System.out.println("Please enter your email address");
        String email = input.nextLine();
        // Check if the user indicated that they are a member
        if (userType == 'm' || userType == 'M') {
            // searchMemberByEmail will return the member that matches the email entered
            Member member = gymAPI.searchMembersByEmail(email);
            // If member is not null then a match was found
            if (member != null) {
                System.out.println("Login successful. Welcome " + member.getName());
                // Set loggedInUser to be the instance of this member
                loggedInUser = member;
                // Run the member menu
                runMemberMenu();
            }
        }
        // Check if the user indicated that they are a trainer
        else if (userType == 't' || userType == 'T') {
            // searchTrainersByEmail will return the trainer that matches the email entered
            Trainer trainer = gymAPI.searchTrainersByEmail(email);
            // If trainer is not null then a match was found
            if (trainer != null) {
                System.out.println("Login successful. Welcome " + trainer.getName());
                // Set loggedInUser to be the instance of this trainer
                loggedInUser = trainer;
                // Run the trainer menu
                runTrainerMenu();
            }
        }
        System.out.println("Access denied.");
        System.out.println("Exiting... bye");
        System.exit(0);

    }

    /*
     * Allows the user to register in the system
     * Returns to the welcome menu after the registration process is complete
     */
    private void register(char userType) {
        // Asks the user the relevant questions based on the userType passed as a parameter
        addPerson(userType);
        // Return to the welcome menu
        welcomeMenu();
    }

    /*
     * Prints the trainer menu screen
     * Asks the user to pick a menu option using an integer
     * and returns the integer entered
     */
    private int trainerMenu() {
        System.out.println("Trainer Menu");
        System.out.println("------------");
        System.out.println("  1) Add a new member");
        System.out.println("  2) List all members");
        System.out.println("  3) Search for a member by email");
        System.out.println("  4) Search for members by name");
        System.out.println("  5) List members with an ideal weight");
        System.out.println("  6) List members by specific BMI category");
        System.out.println("  7) List all member details in metric and imperial");
        System.out.println("  8) Assessment sub-menu");
        System.out.println("------------");
        System.out.println("  0) Exit");
        int option = input.nextInt();
        return option;
    }

    /*
     * Executes the relevant method based on the option the user picked
     * in the trainerMenu method
     */
    private void runTrainerMenu() {
        // Print the trainer menu and retrieve the option chosen
        int option = trainerMenu();
        // Keep looping while the user dosen't pick option 0
        while (option != 0) {
            switch (option) {
                case 1:
                    // Add a member. userType = 'm' so that we know to add a member
                    addPerson('m');
                    break;
                case 2:
                    // List all members registered to the gym
                    System.out.println(listMembers());
                    break;
                case 3:
                    // Search for a member by email
                    System.out.println(searchMembersByEmail());
                    break;
                case 4:
                    // Dummy read
                    input.nextLine();
                    System.out.println("Please enter a name to search for: ");
                    // Search for members that either partially or completely match the name entered
                    ArrayList<String> memberNames = gymAPI.searchMembersByName(input.nextLine());
                    // If memberNames list is greater than 0 then at least one match was found
                    if (memberNames.size() > 0) {
                        // Print out the matching names
                        for (String name : memberNames) {
                            System.out.println(name);
                        }
                    }
                    // No matches were found
                    else {
                        System.out.println("There are no members matching that name");
                    }
                    break;
                case 5:
                    // List members with an ideal weight
                    // Create an array list of members with an ideal body weight
                    ArrayList<Member> membersWithIdealWeight = gymAPI.listMembersWithIdealWeight();
                    // if membersWithIdealWeight list is greater than 0 then at least one member with ideal weight exists
                    if (membersWithIdealWeight.size() > 0) {
                        // Print out members with an ideal body weight
                        System.out.println("Members with an ideal weight:");
                        for (Member member : membersWithIdealWeight) {
                            System.out.println(member.getName());
                        }
                    }
                    // No members with an ideal body weight exist
                    else {
                        System.out.println("There are currently no members with and ideal weight.");
                    }
                    break;
                case 6:
                    // List members by specific BMI category
                    listMembersBySpecificCategory();
                    break;
                case 7:
                    // List all member details in imperial and metric
                    System.out.println(gymAPI.listMemberDetailsImperialAndMetric());
                    break;
                case 8:
                    // Run the assessment sub menu
                    runTrainerSubMenu();
                    break;
                default:
                    System.out.println("Invalid option entered: " + option);
                    break;
            }

            // Pause the program so that the user can read what was printed to the terminal window
            System.out.println("\nPress any key to continue...");
            input.nextLine();
            // Dummy read
            input.nextLine();

            // Display the trainer menu again
            option = trainerMenu();
        }

        // The user chose option 0, so exit the program
        // Store new member/trainer in XML fle
        try {
            gymAPI.store();
        }
        catch (Exception e) {
            System.err.println("Error writing to file: " + e);
        }
        System.out.println("Exiting... bye");
        System.exit(0);
    }

    /*
     * Prints the trainer assessment menu screen
     * Asks the user to pick a menu option using an integer
     * and returns the integer entered
     */
    private int trainerSubMenu() {
        System.out.println("Trainer Assessment Menu");
        System.out.println("-----------------------");
        System.out.println("  1) Add an assessment for a member");
        System.out.println("  2) Update comment on an assessment for a member");
        System.out.println("-----------------------");
        System.out.println("  3) Back to previous menu");
        System.out.println("-----------------------");
        System.out.println("  0) Exit");
        int option = input.nextInt();
        return option;
    }

    /*
     * Executes the relevant method based on the option the user picked
     * in the trainerSubMenu method
     */
    private void runTrainerSubMenu() {
        // Print the trainer assessment menu and retrieve the option chosen
        int option = trainerSubMenu();
        // Keep looping while the user dosen't pick option 0
        while (option != 0) {
            switch (option) {
                case 1:
                    // Add an assessment for a member
                    addAssessment();
                    break;
                case 2:
                    // Update comment on an assessment for a member
                    editAssessmentComment();
                    break;
                case 3:
                    // Return to the trainer menu
                    runTrainerMenu();
                    break;
                default:
                    System.out.println("Invalid option entered: " + option);
                    break;
            }

            System.out.println("\nPress any key to continue...");
            input.nextLine();
            // Dummy read
            input.nextLine();

            // Display the trainer assessment menu again
            option = trainerSubMenu();
        }

        // The user chose option 0, so exit the program
        // Store new member/trainer in XML fle
        try {
            gymAPI.store();
        }
        catch (Exception e) {
            System.err.println("Error writing to file: " + e);
        }
        System.out.println("Exiting... bye");
        System.exit(0);
    }

    /*
     * Prints the member menu screen
     * Asks the user to pick a menu option using an integer
     * and returns the integer entered
     */
    private int memberMenu() {
        System.out.println("Member Menu");
        System.out.println("-----------");
        System.out.println("  1) View profile");
        System.out.println("  2) Update profile");
        System.out.println("  3) View assessments");
        System.out.println("  4) View Progress sub-menu");
        System.out.println("-----------");
        System.out.println("  0) Exit");
        int option = input.nextInt();
        return option;
    }

    /*
     * Executes the relevant method based on the option the user picked
     * in the memberMenu method
     */
    private void runMemberMenu() {
        // Print the member menu and retrieve the option chosen
        int option = memberMenu();
        // Keep looping while the user dosen't pick option 0
        while (option != 0) {
            switch (option) {
                case 1:
                    // View the profile of the currently logged in user
                    System.out.println(loggedInUser.toString());
                    break;
                case 2:
                    // Update the profile of the currently logged in user
                    editMember();
                    break;
                case 3:
                    // View list of assessments of the currently logged in user
                    if (loggedInUser instanceof Member) {
                        listAssessments((Member)loggedInUser);
                    }
                    break;
                case 4:
                    // Run the member progress sub menu
                    runMemberSubMenu();
                    break;
                default:
                    System.out.println("Invalid option entered: " + option);
                    break;
            }

            // Pause the program so that the user can read what was printed to the terminal window
            System.out.println("\nPress any key to continue");
            input.nextLine();
            // Dummy read
            input.nextLine();

            // Display the member menu again
            option = memberMenu();
        }

        // The user chose option 0, so exit the program
        // Store new member/trainer in XML fle
        try {
            gymAPI.store();
        }
        catch (Exception e) {
            System.err.println("Error writing to file: " + e);
        }
        System.out.println("Exiting... bye");
        System.exit(0);
    }

    /*
     * Prints the member progress menu screen
     * Asks the user to pick a menu option using an integer
     * and returns the integer entered
     */
    private int memberSubMenu() {
        System.out.println("Member Progress Menu");
        System.out.println("--------------------");
        System.out.println("  1) View progress by weight");
        System.out.println("  2) View progress by waist measurement");
        System.out.println("--------------------");
        System.out.println("  3) Back to previous menu");
        System.out.println("--------------------");
        System.out.println("  0) Exit");
        int option = input.nextInt();
        return option;
    }

    /*
     * Executes the relevant method based on the option the user picked
     * in the memberMenu method
     */
    private void runMemberSubMenu() {
        // Print the member progress menu and retrieve the option chosen
        int option = memberSubMenu();
        // Keep looping while the user dosen't pick option 0
        while (option != 0) {
            switch (option) {
                case 1:
                    // Confirm that loggedInUser is member so that we can cast it as a member
                    if (loggedInUser instanceof Member) {
                        // View progress by weight
                        System.out.println(gymAPI.assessmentProgressByWeight((Member)loggedInUser));
                    }
                    break;
                case 2:
                    // Confirm that loggedInUser is member so that we can cast it as a member
                    if (loggedInUser instanceof Member) {
                        // View progress by waist
                        System.out.println(gymAPI.assessmentProgressByWaist((Member)loggedInUser));
                    }
                    break;
                case 3:
                    // Return to the member menu
                    runMemberMenu();
                    break;
                default:
                    System.out.println("Invalid option entered: " + option);
                    break;
            }

            // Pause the program so that the user can read what was printed to the terminal window
            System.out.println("\nPress any key to continue...");
            input.nextLine();
            // Dummy read
            input.nextLine();

            // Display the member progress menu again
            option = memberSubMenu();
        }

        // The user chose option 0, so exit the program
        // Store new member/trainer in XML fle
        try {
            gymAPI.store();
        }
        catch (Exception e) {
            System.err.println("Error writing to file: " + e);
        }
        System.out.println("Exiting... bye");
        System.exit(0);
    }

    /*
     * Adds a person to the relevant array list
     * based on the userType passed as a parameter
     */
    private void addPerson(char userType) {
        // Dummy read
        input.nextLine();
        // Used to determine if the user has entered an acceptable input
        boolean goodInput = false;
        // Used to store the email entered by the user
        String email = "";
        // Keep looping until the user enters an acceptable input
        while (!goodInput) {
            System.out.println("Please enter your email address");
            email = input.nextLine();
            // Check that the email entered does not exist in either members of trainers array list
            if ((gymAPI.searchMembersByEmail(email) == null) && (gymAPI.searchTrainersByEmail(email) == null)) {
                goodInput = true;
            }
            else {
                System.out.println("This email address is already in use. Please enter a new one.");
            }
        }
        // Store name entered by user
        System.out.println("Please enter your name");
        String name = input.nextLine();

        // Store address entered by user
        System.out.println("Please enter your address");
        String address = input.nextLine();

        // Store gender entered by user
        System.out.println("Please enter your gender (M/F )");
        String gender = input.next();

        // If the user is a member
        if (userType == 'm' || userType == 'M') {
            goodInput = false;
            // Used to store whether the member is a premium or student member
            char memberType = ' ';
            // Keep looping until the user enters an acceptable input
            while (!goodInput) {
                System.out.println("Would you like to register as a premium(p) or student(s) member?");
                memberType = input.next().charAt(0);
                // Check if user entered an acceptable input
                if (memberType == 'P' || memberType == 'p' ||
                        memberType == 'S' || memberType == 's') {
                    goodInput = true;
                }
                else {
                    System.out.println("Please choose a valid response. (p/s)");
                }
            }
            goodInput = false;
            float height = 0.0f;
            // Keep looping until the user enters an acceptable input
            while (!goodInput) {
                // Catch any exceptions that could be thrown by the user entering an incorrect type
                try {
                    System.out.println("Please enter your height in meters (Must be between 1m and 3m)");
                    // Store the height entered by the user
                    height = input.nextFloat();
                    // Height must be between 1 and 3 inclusively
                    if (height >= 1.0f && height <= 3.0f) {
                        goodInput = true;
                    }
                }
                // An incorrect type was entered
                catch (Exception e) {
                    input.nextLine();
                    System.err.println("Error number expected");
                }
            }
            goodInput = false;
            float weight = 0.0f;
            // Keep looping until the user enters an acceptable input
            while (!goodInput) {
                // Catch any exceptions that could be thrown by the user entering an incorrect type
                try {
                    System.out.println("Please enter your weight in kgs (Must be between 35kg and 250kg");
                    // Store the weight entered by the user
                    weight = input.nextFloat();
                    // Weight must be between 35 and 250 inclusively
                    if (weight >= 35.0f && weight <= 250.0f) {
                        goodInput = true;
                    }
                }
                // An incorrect type was entered
                catch (Exception e) {
                    input.nextLine();
                    System.err.println("Error number expected");
                }
            }
            // Dummy read
            input.nextLine();
            // If the user is a premium member
            if (memberType == 'P' || memberType == 'p') {
                goodInput = false;
                String chosenPackage = "";
                // Keep looping until the user enters an acceptable input
                while (!goodInput) {
                    System.out.println("Available packages:");
                    // List the available packages except for the WIT package
                    // because that is only available to student members
                    for (String key : packages.keySet()) {
                        if (!key.toUpperCase().equals("WIT")) {
                            System.out.println(key);
                        }
                    }
                    System.out.println("Please enter your chosen package");
                    // Store the chosen package entered by the user
                    chosenPackage = input.nextLine();
                    // Iterate through each key in the packages hashMap
                    for (String key : packages.keySet()) {
                        // Check if package that user entered matches any of the available packages in the packages hashMap
                        if (chosenPackage.toUpperCase().equals(key.toUpperCase())) {
                            goodInput = true;
                        }
                    }
                    // Package entered by user dose not exist
                    if (!goodInput) {
                        System.out.println("Sorry the package entered does not exist.\nPlease enter the name of a valid package");
                    }
                }
                // Add the premium member to the members array list
                gymAPI.addMember(new PremiumMember(email, name, address, gender, height, weight, chosenPackage));
            }
            // If the user is a student member
            else if (memberType == 'S' || memberType == 's') {
                System.out.println("Please enter your student number: ");
                // Store the student number entered by the user
                String studentNumber = input.nextLine();

                System.out.println("Please enter your college name: ");
                // Store the college name entered by the user
                String collegeName = input.nextLine();
                // Add the student member to the members array list
                gymAPI.addMember(new StudentMember(email, name, address, gender, height, weight, collegeName, studentNumber, collegeName));
            }
        }
        // If the user is a member
        else if (userType == 't' || userType == 'T') {
            // Dummy read
            input.nextLine();
            System.out.println("Please enter your speciality");
            // Store the speciality entered by the user
            String speciality = input.nextLine();

            // Add the trainer to the trainers array list
            gymAPI.addTrainer(new Trainer(email, name, address, gender, speciality));
        }
    }

    /*
     * Edits the current logged in member details
     */
    private void editMember() {
        // Dummy read
        input.nextLine();
        // Used to determine if the user has entered an acceptable input
        boolean goodInput = false;

        System.out.println("Please enter your name");
        // Store the name entered by the user
        String name = input.nextLine();
        // Edit the name of the logged in user
        loggedInUser.setName(name);

        System.out.println("Please enter your address");
        // Store the address entered by the user
        String address = input.nextLine();
        // Edit the address of the logged in user
        loggedInUser.setAddress(address);

        System.out.println("Please enter your gender (M/F )");
        // Store the gender entered by the user
        String gender = input.next();
        // Edit the gender of the logged in user
        loggedInUser.setGender(gender);

        float height = 0.0f;
        // Keep looping until the user enters an acceptable input
        while (!goodInput) {
            // Catch any exceptions that could be thrown by the user entering an incorrect type
            try {
                System.out.println("Please enter your height in meters (Must be between 1m and 3m)");
                // Store the height entered by the user
                height = input.nextFloat();
                // Height must be between 1 and 3 inclusively
                if (height >= 1.0f && height <= 3.0f) {
                    goodInput = true;
                    // Confirm that loggedInUser is member so that we can cast it as a member
                    if (loggedInUser instanceof Member) {
                        ((Member) loggedInUser).setHeight(height);
                    }
                }
            }
            // An incorrect type was entered
            catch (Exception e) {
                input.nextLine();
                System.err.println("Error number expected");
            }
        }
        goodInput = false;
        float weight = 0.0f;
        // Keep looping until the user enters an acceptable input
        while (!goodInput) {
            // Catch any exceptions that could be thrown by the user entering an incorrect type
            try {
                System.out.println("Please enter your weight in kgs (Must be between 35kg and 250kg");
                // Store the weight entered by the user
                weight = input.nextFloat();
                // Weight must be between 35 and 250 inclusively
                if (weight >= 35.0f && weight <= 250.0f) {
                    goodInput = true;
                    // Confirm that loggedInUser is member so that we can cast it as a member
                    if (loggedInUser instanceof Member) {
                        ((Member) loggedInUser).setStartWeight(weight);
                    }
                }
            }
            // An incorrect type was entered
            catch (Exception e) {
                input.nextLine();
                System.err.println("Error number expected");
            }
        }
        // Confirm that loggedInUser is a premium member so that we can cast it as a premium member
        if (loggedInUser instanceof PremiumMember) {
            // Dummy read
            input.nextLine();
            goodInput = false;
            // Keep looping until the user enters an acceptable input
            while (!goodInput) {
                System.out.println("Available packages:");
                // List the available packages except for the WIT package
                // because that is only available to student members
                for (String key : packages.keySet()) {
                    if (!key.toUpperCase().equals("WIT")) {
                        System.out.println(key);
                    }
                }
                System.out.println("Please enter your chosen package");
                // Store the chosen packege entered by the user
                String chosenPackage = input.nextLine();
                // Iterate through each key in the packages hashMap
                for (String key : packages.keySet()) {
                    // Check if package that user entered matches any of the available packages in the packages hashMap
                    if (chosenPackage.toUpperCase().equals(key.toUpperCase())) {
                        goodInput = true;
                        // Edit the chosen package of the premium member
                        ((PremiumMember) loggedInUser).chosenPackage(chosenPackage);
                    }
                }
                // Package entered by user dose not exist
                if (!goodInput) {
                    System.out.println("Sorry the package entered does not exist.\nPlease enter the name of a valid package");
                }
            }
        }
        if (loggedInUser instanceof StudentMember) {
            // Dummy read
            input.nextLine();
            System.out.println("Please enter your student number: ");
            // Store the student number entered by the user
            String studentNumber = input.nextLine();
            // Edit the student ID of the student member
            ((StudentMember) loggedInUser).setStudentid(studentNumber);

            System.out.println("Please enter your college name: ");
            // Store the college name entered by the user
            String collegeName = input.nextLine();
            // Edit the college name of the student member
            ((StudentMember) loggedInUser).setCollegeName(collegeName);
            // Update the student member's package based on their college name
            ((StudentMember) loggedInUser).chosenPackage(null);
        }
    }

    /*
     * Prints all the member's assessments to the console
     * Prints a custom String if the member has no assessments
     */
    private void listAssessments(Member member) {
        // Retrieve the sorted set of String dates in chronological order
        SortedSet<String> dates = member.sortedAssessmentDates();
        // Member has at least one assessment
        if (dates.size() != 0) {
            // Retrieve the member's assessment hash map
            HashMap<String, Assessment> assessmentHashMap = member.getAssessments();
            // List each date and assessment
            for (String key : dates) {
                System.out.println(key + ": " + assessmentHashMap.get(key).toString());
            }
        }
        // Member has no assessments
        else {
            System.out.println("There are currently no assessments.");
        }
    }

    /*
     * Returns a String containing every members's details
     * registered to the gym
     */
    private String listMembers() {
        // Used to store member details
        String members = "";
        // Check if the gym has at least one registered member
        if (gymAPI.listMembers().size() != 0) {
            // Concatenate the member String with each registered member's details
            for (Member member : gymAPI.listMembers()) {
                members += member.toString();
            }
            return members;
        }
        // There are no registered members
        return "There are currently no members.";
    }

    /*
     * Returns a String containing the member
     * that matches the email supplied
     * Returns a custom String if no members
     * match the email supplied
     */
    private String searchMembersByEmail() {
        // Dummy read
        input.nextLine();
        System.out.println("Please enter email of member to search for:  ");
        // Store the email that the user entered
        String email = input.nextLine();
        // Search for a member with the email entered
        Member member = gymAPI.searchMembersByEmail(email);
        // Check if a member was found
        if (member != null) {
            return member.toString();
        }
        // No member was found
        return "No member found with this email: " + email;
    }

    /*
     * Prints a String containing all the member names whose BMI category
     * partially or entirely matches the entered category.
     * Prints a custom String if none are found
     */
    private void listMembersBySpecificCategory() {
        // Dummy read
        input.nextLine();
        System.out.println("Please enter a BMI category: ");
        // List the available categories
        System.out.println("Categories: SEVERELY UNDERWEIGHT, UNDERWEIGHT, NORMAL, OVERWEIGHT, " +
                "MODERATELY OBESE, SEVERELY OBESE");
        // Store the category entered by the user
        String category = input.nextLine();
        // Retrieve the list of members that partially or entirely match the entered category
        ArrayList<Member> membersInCategory = gymAPI.listMembersBySpecificBMICategory(category);
        System.out.println("Members in " + category.toUpperCase() + " category");
        // Check that at least one member matched the category
        if (membersInCategory.size() > 0) {
            // Print each member's name that partially or entirely matched the entered category
            for (Member member : membersInCategory) {
                System.out.println(member.getName());
            }
        }
        // No members matched the entered category
        else {
            System.out.println("There are no members in that category.");
        }
    }

    /*
     * Allows a trainer to add an assessment for a member
     * of their choice
     */
    private void addAssessment() {
        // Check that the gym has at least one registered member
        if (gymAPI.listMembers().size() != 0) {
            // Retrieve the list of members
            ArrayList<Member> members = gymAPI.listMembers();
            // Create a null member object
            Member member = null;
            // Used to determine if the user has entered an acceptable input
            boolean goodInput = false;
            // Keep looping until the user enters an acceptable input
            while (!goodInput) {
                // Catch any exceptions that could be thrown by the user entering an incorrect type
                try {
                    // List all members in the gym, with an index that the user can pick
                    for (int i = 0; i < members.size(); i++) {
                        System.out.println(i + ": " + members.get(i).getName());
                    }
                    System.out.println("Please choose the member you wish to add an assessment for\n" +
                            "by entering the number that is to the left of their name: ");
                    // Store the user's index choice
                    int index = input.nextInt();
                    // Check that the user entered a valid index
                    if (gymAPI.isValidMemberIndex(index)) {
                        member = members.get(index);
                        System.out.println("Thank you, " + member.getName() + " has been selected");
                        goodInput = true;
                    }
                    // The user entered an invalid index
                    else {
                        System.out.println("Please enter a valid index");
                    }
                }
                // An incorrect type was entered
                catch (Exception e) {
                    System.err.println("Error number expected");
                }
            }
            goodInput = false;
            float weight = 0.0f;
            // Keep looping until the user enters an acceptable input
            while (!goodInput) {
                System.out.println("Please enter the member's weight: ");
                // Catch any exceptions that could be thrown by the user entering an incorrect type
                try {
                    // Store the weight entered by the user
                    weight = input.nextFloat();
                    // Weight must be between 35 and 250 inclusively
                    if (weight >= 35.0f && weight <= 250.0f) {
                        goodInput = true;
                    }
                }
                // An incorrect type was entered
                catch (Exception e) {
                    System.err.println("Error number expected");
                }
            }
            goodInput = false;
            float thigh = 0.0f;
            // Keep looping until the user enters an acceptable input
            while (!goodInput) {
                System.out.println("Please enter the member's thigh measurement: ");
                // Catch any exceptions that could be thrown by the user entering an incorrect type
                try {
                    // Store the thigh value entered by the user
                    thigh = input.nextFloat();
                    goodInput = true;
                }
                // An incorrect type was entered
                catch (Exception e) {
                    System.err.println("Error number expected");
                }
            }
            goodInput = false;
            float waist = 0.0f;
            // Keep looping until the user enters an acceptable input
            while (!goodInput) {
                System.out.println("Please enter the member's waist measurement: ");
                // Catch any exceptions that could be thrown by the user entering an incorrect type
                try {
                    // Store the waist value entered by the user
                    waist = input.nextFloat();
                    goodInput = true;
                }
                // An incorrect type was entered
                catch (Exception e) {
                    System.err.println("Error number expected");
                }
            }
            // Dummy read
            input.nextLine();
            System.out.println("Please enter a comment to add to the assessment: ");
            // Store the comment entered by the user
            String comment = input.nextLine();

            // TODO - Check that the date entered by the user is in the correct format (regex?)
            System.out.println("Please enter the date of the assessment (in the format YY/MM/DD)");
            // Store the date entered by the user
            String date = input.nextLine();
            // Create the assessment based on the values entered by the user
            Assessment assessment = new Assessment(weight, thigh, waist, comment);
            // Check that a member was found at the start of this method
            if (member != null) {
                // Retrieve the member's assessment hash map
                HashMap<String, Assessment> assessmentHashMap = member.getAssessments();
                // Insert the new assessment into the member's assessment has map
                assessmentHashMap.put(date, assessment);
            }

        }
        else {
            System.out.println("No registered members");
        }
    }

    /*
     * Allows a trainer to edit the comment
     * of an assessment
     */
    private void editAssessmentComment() {
        // Check that the gym has at least one registered member
        if (gymAPI.listMembers().size() > 0) {
            // Retrieve the list of members
            ArrayList<Member> members = gymAPI.listMembers();
            // Create a null member object
            Member member = null;
            // Used to determine if the user has entered an acceptable input
            boolean goodInput = false;
            // Keep looping until the user enters an acceptable input
            while (!goodInput) {
                // Catch any exceptions that could be thrown by the user entering an incorrect type
                try {
                    // List all members in the gym, with an index that the user can pick
                    for (int i = 0; i < members.size(); i++) {
                        System.out.println(i + ": " + members.get(i).getName());
                    }
                    System.out.println("Please choose the member that you wish to edit the assessment comment for\n" +
                            "by entering the number that is to the left of their name: ");
                    // Store the user's index choice
                    int index = input.nextInt();
                    // Check that the user entered a valid index
                    if (gymAPI.isValidMemberIndex(index)) {
                        member = members.get(index);
                        System.out.println("Thanks, " + member.getName() + " has been selected");
                        goodInput = true;
                    }
                    // The user entered an invalid index
                    else {
                        System.out.println("Please enter a valid index");
                    }
                }
                // An incorrect type was entered
                catch (Exception e) {
                    System.err.println("Error number expected");
                }
            }
            goodInput = false;
            // Keep looping until the user enters an acceptable input
            while (!goodInput) {
                // Check that a member was found at the start of this method
                if (member != null) {
                    // Retrieve the member's assessment hash map
                    HashMap<String, Assessment> assessmentHashMap = member.getAssessments();
                    // Check that the member has at least one assessment
                    if (assessmentHashMap.size() > 0) {
                        // Print each assessment date and comment associated with the selected member to console
                        assessmentHashMap.forEach((key, value) -> System.out.println(key + ": " + value.getComment()));
                        // Dummy read
                        input.nextLine();
                        System.out.println("Please enter the date of the assessment's comment you with to update: ");
                        // TODO - Use an index for date selection
                        // Store the date entered by the user
                        String dateKey = input.nextLine();
                        // Retrieve the assessment using the date entered by the user
                        Assessment assessment = assessmentHashMap.get(dateKey);
                        // Check that the date entered is associated with an assessment
                        if (assessment != null) {
                            System.out.println("Please enter a new comment: ");
                            // Store the comment entered by the user
                            String newComment = input.nextLine();
                            // Edit the assessment comment
                            assessment.setComment(newComment);
                            goodInput = true;
                        }
                        // The date String entered by the user is not associated with any assessments
                        else {
                            System.out.println("No assessment matches the date entered");
                        }
                    }
                    // The member chosen has no assessments
                    else {
                        System.out.println(member.getName() + " has no assessments currently");
                        runTrainerSubMenu();
                    }
                }
            }
        }
        // There are no members in the members array list
        else {
            System.out.println("No registered members");
        }

    }

}
