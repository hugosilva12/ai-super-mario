package AG;

import luigi.GameMode;
import luigi.Request;
import luigi.RunResult;

import java.util.ArrayList;
import java.util.Collections;

public class Population {

    /**
     * List of individuals
     */
    private ArrayList<Individual> listIndividuals;

    /**
     * Size of population
     */
    private int sizePopulation;


    /**
     * Initializes population of individuals aleatory
     *
     * @param sizeOfPopulation number of individuals in the population
     * @param numGenes         number of commands
     */
    public Population(int numGenes, int sizeOfPopulation) {
        sizePopulation = sizeOfPopulation;
        listIndividuals = new ArrayList<>();
        for (int i = 0; i < sizePopulation; i++) {
            listIndividuals.add(new Individual(numGenes));
        }
    }

    /**
     * Initialize  one population
     *
     * @param sizeOfPopulation number of individuals in the population
     */
    public Population(int sizeOfPopulation) {
        sizePopulation = sizeOfPopulation;
        listIndividuals = new ArrayList<>();
    }

    public void printResponse(RunResult res, int index) {
        System.out.println("Fitness : " + listIndividuals.get(index).getFitness());
        System.out.println("Reason to  finish : " + res.getReason_finish());
        System.out.println("Commands: " + res.getCommands_used());
        System.out.println("Time to Left : " + res.getTime_left());
        System.out.println("pos:" + res.getX_pos());
        System.out.println("coins:" + res.getCoins());
        System.out.println("status:" + res.getStatus());
        System.out.println("type:" + listIndividuals.get(index).getType());

        if (listIndividuals.get(index).isFinish()) {
            System.out.println("Yes, Mario Win : " + listIndividuals.get(index).isFinish());
            System.out.println("Yes, Mario Score : " + listIndividuals.get(index).getScore());
            if (listIndividuals.get(index).getCoins() > 5) {
                System.out.println("Commands : " + listIndividuals.get(index).getCommands());
            }
        }
    }

    /**
     * Check if it is to remove out mutate commands from winning solutions
     *
     * @return true if is good pop, false if isn't
     */
    public boolean checkPopulation() {
        int numberFinish = 0;
        for (Individual i : listIndividuals) {
            if (i.isFinish()) {
                numberFinish++;
            }
        }
        if (numberFinish > (Conf.percentage_of_population_win_for_mut * listIndividuals.size())) {
            return true;
        }

        return false;
    }


    /**
     * Submit solutions to Server
     */
    public void submitSolutionsToServer() throws Exception {
        for (int i = 0; i < getNumIndividuals(); i++) {
            RunResult res = Conf.mu.goMarioGo(new Request(listIndividuals.get(i).generateRequest(), Conf.conf_level, "false"), 8080);

            listIndividuals.get(i).generateFitness(res.getX_pos(), res.getTime_left(), res.getCommands_used(), res.getReason_finish(), res.getCoins(), res.getScore(), res.getStatus());
            printResponse(res, i);

            if (res.getFlag_get().equals("True") && res.getTime_left() > 295) {
                double fitness = listIndividuals.get(i).getFitness();
                Conf.mu.submitToLeaderboard(res, "Engenheiros da Reproducao", GameMode.SPEEDRUN);
                /*if (fitness > bestWinnerFitness) {
                    //Registar o fitness da melhor solucao que ganhou
                    bestWinnerFitness = fitness;
                    printWin(res, i);
                    //submeter para a leaderboard
                    Conf.mu.submitToLeaderboard(res, "Engenheiros da Reproducao", GameMode.SPEEDRUN);
                }*/
            }
        }

    }

    public void addIndividual(Individual individual) {
        listIndividuals.add(individual);
    }

    /**
     * Order population by fitness
     */
    public void orderPopulation() {
        Collections.sort(listIndividuals, Collections.reverseOrder());
    }

    /**
     * Return number of individuals in population
     */
    public int getNumIndividuals() {
        return listIndividuals.size();
    }

    /**
     * Return size of population
     */
    public int getSizePopulation() {
        return sizePopulation;
    }

    /**
     * Return individual in pos index
     */
    public Individual getIndividual(int pos) {
        return listIndividuals.get(pos);
    }

    /**
     * Return list of individuals
     */
    public ArrayList<Individual> getListIndividuals() {
        return listIndividuals;
    }


}
