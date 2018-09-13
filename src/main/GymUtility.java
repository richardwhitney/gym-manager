/*
 * GymUtility contains static methods that are used elsewhere in the application
 */

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GymUtility {

    /*
     * Returns the BMI for the member based on their latest assessment
     */
    public static double calculateBMI(Member member, Assessment assessment) {
        // BMI = weight divided by the square of the height
        double bmi = assessment.getWeight() / (member.getHeight() * member.getHeight());
        return bmi;
    }

    /*
     * Returns the category that the BMI value belongs to
     */
    public static String determineBMICategory(double bmiValue) {
        if (bmiValue < 16) {
            return "SEVERELY UNDERWEIGHT";
        }
        else if (bmiValue >= 16 && bmiValue < 18.5) {
            return "UNDERWEIGHT";
        }
        else if (bmiValue >= 18.5 && bmiValue < 25) {
            return "NORMAL";
        }
        else if (bmiValue >= 25 && bmiValue < 30) {
            return "OVERWEIGHT";
        }
        else if (bmiValue >= 30 && bmiValue < 35) {
            return "MODERATELY OBESE";
        }
        else {
            return "SEVERELY OBESE";
        }
    }

    /*
     * Returns a boolean to indicate if the member has an
     * ideal body weight based on the Devine formula
     */
    public static boolean isIdealBodyWeight(Member member, Assessment assessment) {
        // Convert member's height in meters to inches
        float heightInInches = metersToInches(member.getHeight());

        // Ideal weight starts at 50kg for males and 45kg for females
        float idealWeight;
        float actualWeight = assessment.getWeight();
        // If member is male
        if (member.getGender().equals("M")) {
            idealWeight = 50.0f;
        }
        // Member is either female or gender is not specified
        else {
            idealWeight = 45.5f;
        }
        // If member is over 5ft tall
        if (heightInInches > 60) {
            float inchesOverFiveFeet = heightInInches - 60;
            // Add 2.3kg for ever inch over 5ft
            idealWeight += inchesOverFiveFeet * 2.3f;
        }

        // Allow for +/- 0.2 margin of error
        if (actualWeight > idealWeight - 0.2f && actualWeight < idealWeight + 0.2f) {
            return true;
        }
        else {
            return false;
        }
    }

    /*
     * Returns an integer indicating if the current assessment's weight
     * is less than, greater than, or equal to the previous assessment's weight
     * 1 = less than
     * -1 = greater than
     * 0 = equal to
     */
    public static int compareAssessmentByWeight(Assessment currentAssessment, Assessment previousAssessment) {
        if (currentAssessment.getWeight() < previousAssessment.getWeight()) {
            return 1;
        }
        else if (currentAssessment.getWeight() > previousAssessment.getWeight()) {
            return -1;
        }
        else {
            return 0;
        }
    }

    /*
     * Returns an integer indicating if the current assessment's waist
     * is less than, greater than, or equal to the previous assessment's waist
     * 1 = less than
     * -1 = greater than
     * 0 = equal to
     */
    public static int compareAssessmentByWaist(Assessment currentAssessment, Assessment previousAssessment) {
        if (currentAssessment.getWaist() < previousAssessment.getWaist()) {
            return 1;
        }
        else if (currentAssessment.getWaist() > previousAssessment.getWaist()) {
            return -1;
        }
        else {
            return 0;
        }
    }

    /*
     * Returns value in inches
     * based on meters value passed as parameter
     */
    public static float metersToInches(float meters) {
        float inches = meters/0.0254f;
        return inches;
    }

    /*
     * Returns value in pounds
     * based on kilos value passed as parameter
     */
    public static float kilosToPounds(float kilos) {
        float pounds = kilos*2.20462f;
        return pounds;
    }

    /*
     * Returns value passed as parameter rounded to
     * number of places passed as parameter
     */
    public static double round(double value , int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
