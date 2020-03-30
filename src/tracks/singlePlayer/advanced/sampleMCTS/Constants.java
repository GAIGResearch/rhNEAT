package tracks.singlePlayer.advanced.sampleMCTS;

public class Constants {
    public static final double epsilon = 1e-6;
    public static final int break_ms = 5;
    public static final String NN_MODEL_FILE = "comms/nn_model.json";

    // Representation
    public final static int REP_ACTION_PLAN = 0;
    public final static int REP_ACTIONS_PARAMS = 1;
    public final static int REP_ACTIONS_FOCUS = 2;
    public final static int REP_POSITIONS = 3;

    // EA model type
    public final static int PROBABILISTIC_MODEL = 0;
    public final static int POPULATION_MODEL = 1;

    // Budget type
    public final static int TIME_BUDGET = 0;
    public final static int ITERATION_BUDGET = 1;
    public final static int FM_BUDGET = 2;

    // Genetic operators
    public final static int MUTATION_AND_CROSSOVER = 0;
    public final static int MUTATION_ONLY = 1;
    public final static int CROSSOVER_ONLY = 2;

    public final static int MUTATION_UNIFORM = 0;
    public final static int MUTATION_BIT = 1;
    public final static int MUTATION_BIT2 = 2;
    public final static int MUTATION_BIT3 = 3;
    public final static int MUTATION_SOFTMAX = 4;
    public final static int MUTATION_DIVERSITY = 5;  // TODO Phenotype

    public final static int SELECT_RANK = 0;
    public final static int SELECT_TOURNAMENT = 1;
    public final static int SELECT_ROULETTE = 2;

    public final static int CROSS_UNIFORM = 0;
    public final static int CROSS_ONE_POINT = 1;
    public final static int CROSS_TWO_POINT = 2;

    // State evaluation methods
    public final static int EVAL_WIN_SCORE = 0;
    public final static int EVAL_SIMPLE_STATE = 1;
    public final static int EVAL_KNOWLEDGE = 2;

    // Init type
    public final static int INIT_RANDOM = 0;
    public final static int INIT_1SLA = 1;
    public final static int INIT_MCTS = 2;
    public final static int INIT_NN = 3;

    // NN init policy
    public final static int INIT_NN_SOFTMAX = 0;
    public final static int INIT_NN_GREEDY = 1;
    public final static int INIT_NN_E_GREEDY = 2;

    // Skip frame types
    public final static int SKIP_REPEAT = 0;
    public final static int SKIP_NULL = 1;
    public final static int SKIP_RANDOM = 2;
    public final static int SKIP_SEQUENCE = 3;

    // Drawing
    public static final int DRAW_EXPLORATION = 0;
    public static final int DRAW_THINKING = 1;
    public static final int DRAW_ALL = 2;

    public static final int draw_code = DRAW_ALL;

    // Evaluation
    public final static int EVALUATE_ACT_LAST = 0;
    public final static int EVALUATE_ACT_DELTA = 1;
    public final static int EVALUATE_ACT_AVG = 2;
    public final static int EVALUATE_ACT_MIN = 3;
    public final static int EVALUATE_ACT_MAX = 4;
    public final static int EVALUATE_ACT_DISCOUNT = 5;

    public final static int EVALUATE_UPDATE_RAW = 0;
    public final static int EVALUATE_UPDATE_DELTA = 1;
    public final static int EVALUATE_UPDATE_AVERAGE = 2;
    public final static int EVALUATE_UPDATE_MIN = 3;  // Pessimist
    public final static int EVALUATE_UPDATE_MAX = 4;  // Optimist

    // Diversity
    public final static int DIVERSITY_NONE = 0;
    public final static int DIVERSITY_GENOTYPE = 1;
    public final static int DIVERSITY_PHENOTYPE = 2;  // TODO

    // Huge
    public final static long HUGE_POSITIVE = (long)Math.pow(10,6);
    public final static long HUGE_NEGATIVE = -(long)Math.pow(10,6);
}
