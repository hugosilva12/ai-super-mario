package AG;

public class Best {
    private int generation;
    private double value;


    public Best(int generation, double value){
        this.generation = generation;
        this.value = value;
    }

    public int getGeneration() {
        return generation;
    }


    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
