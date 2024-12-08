package AG;


import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void printGeneration(int generation, Population population, List<AVG> avgFitness) {
        System.out.println("Generation " + generation + " | Fitness: " + population.getIndividual(0).getFitness() + " | Best: " + population.getIndividual(0).getFitness()
                + " | Average: " + avgFitness.get(generation).getAvg());
    }


    public static void main(String[] args) throws Exception {
        ExporterFile exporterFile = new ExporterFile();
        AlgorithmGenetic algorithmGenetic = new AlgorithmGenetic();

        List<Best> best_fitness = new ArrayList<>();

        Individual best = null;

        List<AVG> avg_fitness = new ArrayList<>();

        Population population = new Population(Conf.genes_number, Conf.pop_size);

        int generation = 0;

        population.submitSolutionsToServer();

        population.orderPopulation();

        avg_fitness.add(new AVG(0, population.getListIndividuals().stream().mapToDouble(x -> x.getFitness()).average().getAsDouble()));
        best_fitness.add(new Best(0, population.getIndividual(0).getFitness()));

        printGeneration(generation, population, avg_fitness);

        while (generation < Conf.max_generation) {

            generation++;

            population = algorithmGenetic.mutate(population);

            population.orderPopulation();

            if (best == null)
                best = population.getIndividual(0);
            else if (best.getFitness() < population.getIndividual(0).getFitness()) {
                best = population.getIndividual(0);
                exporterFile.exportBest("bestSolution_" + Conf.level + "_" + Conf.game_mode.toString() + ".json", best);
            }

            best_fitness.add(new Best(generation, population.getIndividual(0).getFitness()));
            avg_fitness.add(new AVG(generation, population.getListIndividuals().stream().mapToDouble(x -> x.getFitness()).average().getAsDouble())); //calcular fitness médio

            printGeneration(generation, population, avg_fitness);

            exporterFile.exportAvg("" + Conf.level + "_" + Conf.game_mode.toString() + "_pop_size_" + Conf.pop_size + "_avg.json", avg_fitness);
            exporterFile.exportBest("" + Conf.level + "_" + Conf.game_mode.toString() + "_pop_size_" + Conf.pop_size + "_best.json", best_fitness);

        }


    }


}





