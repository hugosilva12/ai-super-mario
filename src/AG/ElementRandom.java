package AG;

public class ElementRandom {

    private Individual individual;
    private Double inferiorLimit;
    private Double upperLimit;

    public ElementRandom(Individual individual, Double inferiorLimit, Double upperLimit) {
        this.individual = individual;
        this.inferiorLimit = inferiorLimit;
        this.upperLimit = upperLimit;
    }

    public Individual getIndividual() {
        return individual;
    }

    public void setIndividual(Individual individual) {
        this.individual = individual;
    }

    public Double getInferiorLimit() {
        return inferiorLimit;
    }

    public void setInferiorLimit(Double inferiorLimit) {
        this.inferiorLimit = inferiorLimit;
    }

    public Double getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(Double upperLimit) {
        this.upperLimit = upperLimit;
    }
}
