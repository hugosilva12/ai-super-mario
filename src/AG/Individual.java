package AG;


import java.util.ArrayList;
import java.util.Random;

public class Individual implements Comparable<Individual> {

    private ArrayList<Integer> commands;
    private double fitness = -1;
    private String type = "";
    private int numberCommands = 0;
    private int timeLeft = -1;
    private int coins = 0;
    private boolean isFinish = false;
    private int score = 0;
    private String marioStatus = "";

    public Individual(int numberCommands) {
        commands = new ArrayList<>();
        this.numberCommands = numberCommands;

        for (int i = 0; i < numberCommands; i++) {

            int random = new Random().nextInt(100);
            int length = new Random().nextInt(5) + 5;
            if (random < 10) {
                int command = Conf.less_used[new Random().nextInt(Conf.less_used.length)];
                insertCommands(command, length);

            } else if (random >= 10 && random < 40) {
                int command = Conf.moderate_use[new Random().nextInt(Conf.moderate_use.length)];
                insertCommands(command, length);

            } else {
                int command = Conf.most_used[new Random().nextInt(Conf.most_used.length)];
                insertCommands(command, length);
            }
            type = "ALEATORY";
        }

    }

    public void insertCommands(int command, int length) {
        for (int j = 0; j < length; j++) {
            if (commands.size() < numberCommands)
                commands.add(command);
        }
    }

    public Individual(ArrayList<Integer> listCommands, double fitness, String type, int timeLeft) {
        this.commands = listCommands;
        this.fitness = fitness;
        this.type = type;
        this.timeLeft = timeLeft;
    }


    public void generateFitness(int position, int timeLeft, int numberCommands, String finish, int coins, int score, String marioStatus) {
        this.numberCommands = numberCommands;
        this.timeLeft = timeLeft;
        this.coins = coins;
        this.score = score;
        this.marioStatus = marioStatus;

        if (Conf.game_mode == Conf.GameMode.speed_run) {

            this.fitness = position + (8 * timeLeft);

        } else if (Conf.game_mode == Conf.GameMode.coins) {

            this.fitness = position + timeLeft + (2000 * coins) + (12 * timeLeft);

        } else {
            this.fitness = position + (10 * score) + (12 * timeLeft);

            if (marioStatus.equals("tall") && !finish.equals("win")) {
                this.fitness += 500;
            } else if (marioStatus.equals("fireball")) {
                this.fitness += 1000;
            }

        }
        if (finish.equals("win")) {
            this.isFinish = true;
        }

    }

    public Integer[] generateRequest() {
        Integer[] solution = new Integer[commands.size()];
        solution = commands.toArray(solution);
        return solution;
    }

    @Override
    public int compareTo(Individual o) {
        if (o.getFitness() < this.getFitness())
            return 1;
        else if (o.getFitness() > this.getFitness())
            return -1;
        else return 0;
    }

    /*
     * Getter Setters
     */
    public int getCommandByIndex(int i) {
        return this.commands.get(i);
    }

    public ArrayList<Integer> getCommands() {
        return commands;
    }

    public double getFitness() {
        return fitness;
    }

    public void setCommands(ArrayList<Integer> commands) {
        this.commands = commands;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumberCommands() {
        return numberCommands;
    }

    public void setNumberCommands(int numberCommands) {
        this.numberCommands = numberCommands;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public int getCoins() {
        return coins;
    }

    public int getScore() {
        return score;
    }

    public void setMarioStatus(String marioStatus) {
        this.marioStatus = marioStatus;
    }
}
