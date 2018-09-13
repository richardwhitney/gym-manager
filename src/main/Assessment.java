/*
 * Model for an Assessment
 * Stores measurements for a member's weight,
 * thigh and waist. Also stores a comment made by a
 * trainer
 */

public class Assessment {

    private float weight;
    private float thigh;
    private float waist;
    private String comment;
    //private Trainer trainer;

    /*
     * Constructor for Assessment
     */
    public Assessment(float weight, float thigh, float waist, String comment /*Trainer trainer*/) {
        this.weight = weight;
        this.thigh = thigh;
        this.waist = waist;
        this.comment = comment;
        //this.trainer = trainer;
    }

    public float getWeight() {
        return weight;
    }

    public float getThigh() {
        return thigh;
    }

    public float getWaist() {
        return waist;
    }

    public String getComment() {
        return comment;
    }

    /*public Trainer getTrainer() {
        return trainer;
    }*/

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setThigh(float thigh) {
        this.thigh = thigh;
    }

    public void setWaist(float waist) {
        this.waist = waist;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    /*
     * Returns a string containing each field of an Assessment
     */
    @Override
    public String toString() {
        String str = "Weight: " + getWeight() + ", Thigh: " + getThigh() + ", Waist: " + getWaist() + ", Trainer comment: " + getComment();
        return str;
    }

    /*public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }*/
}
