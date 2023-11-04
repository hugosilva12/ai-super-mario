package AG;

import java.util.*;


public class Roulette {


    private static Map<Individual, Double> elements = new HashMap<Individual, Double>();

    private static List<ElementRandom> elementsByIntervals = new ArrayList<ElementRandom>();

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

    public void calculePercentage() {
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
        int randow = new Random().nextInt(100);
        Double random = randow * 0.01;
        for (ElementRandom elementRandom : elementsByIntervals) {
            if (elementRandom.getInferiorLimit() >= random && random < elementRandom.getUpperLimit()) {
                return elementRandom.getIndividual();
            }
        }
        return elementsByIntervals.get(0).getIndividual();
    }
}
