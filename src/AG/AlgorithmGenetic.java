package AG;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Generic algorithm
 */
public class AlgorithmGenetic {

    private static Random r = new Random();

    private Roulette roulette = new Roulette();

    /**
     * Cross Population
     */
    public Population crossover(Population population) throws Exception {
        Population newPopulation = new Population(population.getSizePopulation());

        Population resPopulation = new Population(population.getSizePopulation());

        roulette.cleanRoullete();
        roulette.insertPopulationInRoullete(population.getListIndividuals());
        roulette.calculatePercentage();
        roulette.generateIntervals();

        int index = 0;
        Individual[] parents = new Individual[2];
        for (int i = 0; i < population.getSizePopulation(); i++) {
            if (r.nextInt(100) < Conf.crossover_rate) {
                parents[0] = population.getIndividual(index);
                if (Conf.selection_mode == Conf.SelectionMode.tournament) {
                    parents[1] = tournamentSelection(population, parents[0]);
                } else {
                    parents[1] = roulette.getRandomElement();
                }

                Individual children = crossoverIndividual(parents[0], parents[1]);

                newPopulation.addIndividual(children);
            } else {
                newPopulation.addIndividual(population.getIndividual(index));
            }
        }

        newPopulation.submitSolutionsToServer();

        for (int i = 0; i < population.getSizePopulation(); i++)
            newPopulation.addIndividual(population.getIndividual(i));

        newPopulation.orderPopulation();

        for (int i = 0; i < population.getSizePopulation(); i++)
            resPopulation.addIndividual(newPopulation.getIndividual(i));

        return resPopulation;
    }

    public ArrayList<Integer> generateMask(Integer size) {
        int[] binary = {0, 1};
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int parent = binary[new Random().nextInt(binary.length)];
            list.add(parent);

        }
        return list;
    }

    public Individual crossoverMask(Individual individual1, Individual individual2) {

        ArrayList<Integer> genesChoice = generateMask(individual1.getCommands().size());
        List<Integer> newGene = new ArrayList<>();

        for (int i = 0; i < individual2.getCommands().size(); i++) {
            if (genesChoice.get(i) == 0) {
                newGene.add(individual2.getCommands().get(i));
            } else {
                newGene.add(individual1.getCommands().get(i));
            }
        }

        return new Individual(new ArrayList<>(newGene), 0, "cross", 0);
    }

    public Individual crossoverIndividual(Individual individual1, Individual individual2) {

        int randomFirst = new Random().nextInt(individual1.getNumberCommands());
        int resRandom = individual1.getCommands().size() - randomFirst;

        List<Integer> newGene = new ArrayList<>();


        Individual[] sons = new Individual[2];

        newGene = individual1.getCommands().stream().limit(randomFirst).collect(Collectors.toList());

        for (int i = randomFirst; i < individual2.getCommands().size(); i++) {
            newGene.add(individual2.getCommandByIndex(i));
        }
        return new Individual(new ArrayList<>(newGene), 0, "cross", 0);

    }

    public Individual tournamentSelection(Population population, Individual individual2) {
        Population popAUx = new Population(Conf.selected_tournament);
        for (int i = 0; i < Conf.selected_tournament; i++) {
            popAUx.addIndividual(population.getIndividual(r.nextInt(population.getNumIndividuals())));
        }
        popAUx.orderPopulation();
        if (individual2.getCommands().equals(popAUx.getIndividual(0).getCommands()))
            return popAUx.getIndividual(1);

        return popAUx.getIndividual(0);
    }

    /**
     * Mutate Population
     */
    public Population mutate(Population population) throws Exception {
        boolean isGoodPopulation = population.checkPopulation();
        Population newPopulation = new Population(population.getSizePopulation());

        for (int i = 0; i < population.getNumIndividuals(); i++) {
            if (population.getIndividual(i).isFinish() && isGoodPopulation && Conf.game_mode == Conf.GameMode.speed_run) {
                if (new Random().nextInt(100) < 50) {
                    newPopulation.addIndividual(removeCommands(population.getIndividual(i)));
                } else {
                    newPopulation.addIndividual(mutateCommandsFinishSolution(population.getIndividual(i)));
                }

            } else if (population.getIndividual(i).isFinish() && isGoodPopulation && Conf.game_mode == Conf.GameMode.score) {
                newPopulation.addIndividual(mutateCommandsFinishSolution(population.getIndividual(i)));

            } else if (population.getIndividual(i).isFinish() && isGoodPopulation && Conf.game_mode == Conf.GameMode.coins) {
                newPopulation.addIndividual(mutateCommandsFinishSolution(population.getIndividual(i)));

            } else {
                newPopulation.addIndividual(mutateIndividual(population.getIndividual(i)));
            }

        }
        newPopulation.submitSolutionsToServer();

        for (int i = 0; i < population.getSizePopulation(); i++)
            newPopulation.addIndividual(population.getIndividual(i));

        newPopulation.orderPopulation();

        Population res = new Population(Conf.pop_size);

        for (int i = 0; i < Conf.pop_size; i++)
            res.addIndividual(newPopulation.getIndividual(i));


        return res;
    }


    /**
     * Remove commands
     */
    public Individual mutateCommandsFinishSolution(Individual individual) {
        ArrayList<Integer> newGene = new ArrayList<>();
        int position = 0;

        for (int j = position; j < individual.getNumberCommands(); j++) {

            int length = new Random().nextInt(5);

            if (new Random().nextInt(100) < Conf.mutation_rate_after_finish) {
                int random = new Random().nextInt(100);
                if (random < 25) {
                    int command = Conf.less_used[new Random().nextInt(Conf.less_used.length)];
                    for (int y = 0; y < length; y++) {
                        if (j < individual.getNumberCommands()) {
                            newGene.add(command);
                            j++;
                        }
                    }
                    j--;
                } else if (random >= 25 && random < 50) {

                    int command = Conf.moderate_use[new Random().nextInt(Conf.moderate_use.length)];
                    for (int y = 0; y < length; y++) {
                        if (j < individual.getNumberCommands()) {
                            newGene.add(command);
                            j++;
                        }
                    }
                    j--;
                } else {
                    int command = Conf.most_used[new Random().nextInt(Conf.most_used.length)];
                    for (int y = 0; y < length; y++) {
                        if (j < individual.getNumberCommands()) {
                            newGene.add(command);
                            j++;
                        }
                    }
                    j--;
                }

            } else {
                newGene.add(individual.getCommandByIndex(j));
            }
        }
        for (int j = individual.getNumberCommands(); j < individual.getCommands().size(); j++) {
            newGene.add(individual.getCommandByIndex(j));
        }
        return new Individual(newGene, 0, "mutAfterWin", 0);
    }

    /**
     * Remove commands
     */
    public Individual removeCommands(Individual individual) {
        ArrayList<Integer> newGene = new ArrayList<>();

        for (int i = 0; i < individual.getNumberCommands(); i++) {
            newGene.add(individual.getCommandByIndex(i));
        }

        ArrayList<Integer> positionsToRemove = new ArrayList<>();
        for (int j = 0; j < individual.getNumberCommands(); j++) {
            if (new Random().nextInt(100) < Conf.remove_commands_percentage) {
                int length = new Random().nextInt(2);
                for (int y = 0; y < length; y++) {
                    if (j < individual.getCommands().size()) {
                        positionsToRemove.add(j);
                        j++;
                    }
                }
                j--;
            }
        }
        for (int i = 0; i < positionsToRemove.size(); i++) {
            int pos = positionsToRemove.get(i);
            if (pos < newGene.size())
                newGene.remove(pos);
        }

        return new Individual(newGene, 0, "remove10-100commands", 0);
    }

    /**
     * Mutate commands from individual
     *
     * @param individual individual to be mutated
     */
    public Individual mutateIndividual(Individual individual) {
        ArrayList<Integer> newGene = new ArrayList<>();

        int position = individual.getNumberCommands() - Conf.last_commands_to_mut;

        for (int i = 0; i < position; i++) {
            newGene.add(individual.getCommandByIndex(i));
        }

        if (individual.getTimeLeft() == 0 && !individual.isFinish()) {
            ArrayList<Integer> positionsToMut = new ArrayList<>();
            int length = new Random().nextInt(5) + 5;
            for (int j = 0; j < individual.getCommands().size(); j++) {
                if (new Random().nextInt(100) < Conf.mutation_rate + 10) {
                    for (int y = 0; y < length; y++) {
                        if ((y + j) < individual.getCommands().size()) {
                            positionsToMut.add(j);
                        }

                    }
                }
            }

            for (int j = 0; j < positionsToMut.size(); j++) {
                int pos = positionsToMut.get(j);
                if (pos < newGene.size())
                    newGene.remove(pos);
            }
            position = newGene.size();
        }

        if (position >= 0) {

            for (int j = position; j < individual.getCommands().size(); j++) {

                int length = new Random().nextInt(5) + 5;

                if (new Random().nextInt(100) < Conf.mutation_rate) {
                    int random = new Random().nextInt(100);
                    if (random < 25) {
                        int command = Conf.less_used[new Random().nextInt(Conf.less_used.length)];
                        for (int y = 0; y < length; y++) {
                            if (j < individual.getCommands().size()) {
                                newGene.add(command);
                                j++;
                            }
                        }
                        j--;
                    } else if (random >= 25 && random < 50) {

                        int command = Conf.moderate_use[new Random().nextInt(Conf.moderate_use.length)];
                        for (int y = 0; y < length; y++) {
                            if (j < individual.getCommands().size()) {
                                newGene.add(command);
                                j++;
                            }
                        }
                        j--;
                    } else {
                        int command = Conf.most_used[new Random().nextInt(Conf.most_used.length)];
                        for (int y = 0; y < length; y++) {
                            if (j < individual.getCommands().size()) {
                                newGene.add(command);
                                j++;
                            }
                        }
                        j--;
                    }

                } else {
                    newGene.add(individual.getCommandByIndex(j));
                }
            }

        } else {
            int size = newGene.size();
            for (int j = size; j < individual.getCommands().size(); j++) {
                newGene.add(individual.getCommandByIndex(j));
            }

        }

        return new Individual(newGene, 0, "mutfinal", 0);
    }
}

