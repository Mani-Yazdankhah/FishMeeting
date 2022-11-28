import java.util.Random;

public class Meeting {
    private Fish first;
    private Fish second;

    public Meeting(Fish first, Fish second) {
        this.first = first;
        this.second = second;
        this.first.setBusy();
        this.second.setBusy();
    }

    public int meet() {
        int populationChange;
        if (this.first.getGender() && this.second.getGender()) {
            // Two males meeting
            first.die();
            second.die();
            populationChange = -2;
        } else if (this.first.getGender() ^ this.second.getGender()) {
            // One male and one female meeting
            populationChange = 2;
        } else {
            // Two females meeting
            if (new Random().nextDouble() < 0.5) {
                this.first.die();
            } else {
                this.second.die();
            }
            populationChange = -1;
        }
        this.first.setFree();
        this.second.setFree();
        return populationChange;
    }
}
