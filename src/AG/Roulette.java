package AG;

import java.util.*;


public class Roulette {
    private static Map<Individual, Double> elements = new HashMap<>();

    private static List<ElementRandom> elementsByIntervals = new ArrayList<>();

    private static Double upperMaxLimit;

    private static Double fitnessTotal;


    public void insertNewElement(Individual individual) {
        getElements().put(individual, 0.d);
    }

    public void generateIntervals() {
        Double inferiorLimit = 0.d;
        for (Individual individual : elements.keySet()) {
            if (getElementsByIntervals().size() != 0) {
                getElementsByIntervals().get(getElementsByIntervals().size() - 1).setUpperLimit(inferiorLimit);
            }
            getElementsByIntervals().add(new ElementRandom(individual, inferiorLimit, inferiorLimit + elements.get(individual)));
            inferiorLimit += elements.get(individual);
        }

        upperMaxLimit = inferiorLimit;
    }


    public void insertPopulationInRoullete(List<Individual> individuals) {
        for (Individual individual : individuals) {
            this.insertNewElement(individual);
        }
    }

    public void calculatePercentage() {
        fitnessTotal = 0.d;
        for (Individual individual : getElements().keySet()) {
            fitnessTotal += individual.getFitness();
        }
        for (Individual individual : getElements().keySet()) {
            getElements().put(individual, individual.getFitness() / fitnessTotal);
        }
    }

    public void cleanRoullete() {
        elements.clear();
        elementsByIntervals.clear();
    }

    public static Map<Individual, Double> getElements() {
        return elements;
    }

    public static List<ElementRandom> getElementsByIntervals() {
        return elementsByIntervals;
    }

    public static void setElementsByIntervals(List<ElementRandom> elementsByIntervals) {
        Roulette.elementsByIntervals = elementsByIntervals;
    }

    public Individual getRandomElement() {
        double randomValue = new Random().nextDouble();
        for (ElementRandom element : elementsByIntervals) {
            if (randomValue >= element.getInferiorLimit() && randomValue < element.getUpperLimit()) {
                return element.getIndividual();
            }
        }
        return elementsByIntervals.get(0).getIndividual();
    }
}
