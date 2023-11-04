package AG;

import luigi.MarioUtils;

public class Conf {
    /*
     * Game Configs
     */
    enum GameMode {
        speed_run,
        coins,
        score
    }

    enum SelectionMode {
        tournament,
        roulette,

    }
    public static GameMode game_mode = GameMode.speed_run;

    /*
     * Algorithm Genetic Configs
     */
    public static int pop_size = 20;

    public static int crossover_rate = 95;

    public static SelectionMode selection_mode = SelectionMode.tournament;

    public static int max_generation = 200;

    public static int mutation_rate = 45;

    public static int mutation_rate_after_finish = 1;

    public static int selected_tournament = 6;

    public static int remove_commands_percentage = 2;

    public static int mutation_limit = 3;

    public static int percentage_crossover = 60;

    public static int genes_number = 15000;

    public static int[] most_used = {1, 2, 3, 4};

    public static int[] moderate_use = {11, 4, 10}; //7

    public static int[] less_used = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};

    public static int last_commands_to_mut = 150;

    public static double percentage_of_population_win_for_mut = 0.3;

    /*
     * Server Configs
     */

    //hugo ip
    public static MarioUtils mu = new MarioUtils("192.168.8.134");

    public static String conf_level = "SuperMarioBros-1-1-v1";

    //File name
    public static int level = 1;
}