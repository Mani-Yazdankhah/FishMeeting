import java.util.Random;

public class Fish {
    // Male = True, Female = False
    private boolean gender;
    private boolean isAlive = true;
    private boolean isInMeeting = false;

    public Fish(boolean gender) {
        this.gender = gender;
    }

    public Fish() {
        this.gender = new Random().nextDouble() < 0.5;
    }

    public boolean getGender() {
        return this.gender;
    }

    public void setBusy() {
        this.isInMeeting = true;
    }

    public void setFree() {
        this.isInMeeting = false;
    }

    public void die() {
        this.isAlive = false;
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    @Override
    public String toString() {
        return "[" + ((this.gender) ? "Male" : "Female") + "," + ((this.isAlive) ? "Alive" : "Dead") + "]";
    }
}
