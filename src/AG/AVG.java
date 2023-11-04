package AG;

public class AVG {

    private int generation;

    private double avg;

    public AVG(int generation , double avg){
        this.avg = avg;
        this.generation = generation;

    }
    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }
}
