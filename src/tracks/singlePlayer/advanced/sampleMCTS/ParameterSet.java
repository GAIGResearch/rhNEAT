package tracks.singlePlayer.advanced.sampleMCTS;

import tools.Pair;

import java.text.DecimalFormat;
import java.util.*;

import static tracks.singlePlayer.advanced.sampleMCTS.Constants.*;

public class ParameterSet {

    public int representation = REP_ACTION_PLAN;
    public double perc_focus = 0.5;
    public int evaluation_model = EVAL_WIN_SCORE;
    public int diversity = DIVERSITY_NONE;
    public int n_phenotype_slices = 3;

    // EA structure modules settings
    public int genetic_operator = MUTATION_AND_CROSSOVER;
    public int mutation_type = MUTATION_UNIFORM;
    public int selection_type = SELECT_TOURNAMENT;
    public int crossover_type = CROSS_ONE_POINT;
    public boolean shift_buffer = true;
    public boolean keep_parents_next_gen = true;

    // Budget restrictions
    public int budget_type = FM_BUDGET;
    public int iteration_budget = 200;
    public int fm_budget = 1000;
    private double mcts_budget_perc = 0.5;

    // Efficiency settings
    public int frame_skip = 0;
    public int frame_skip_type = SKIP_SEQUENCE;

    // EA parameters
    public int population_size = 10;
    public int individual_length = 10;
    public int offspring_count = 10;
    public int no_elites = 1;
    private double tournament_size_perc = 0.4;  // Percent of population that would take part in tournament selection

    // Evaluation settings
    public int evaluate_act = EVALUATE_ACT_LAST;
    public int evaluate_update = EVALUATE_UPDATE_RAW;
    public double evaluate_discount = 0.9;

    // Other settings
    public double shift_discount = 0.99;
    public double diversity_weight = 0;  // Weight given to diversity in fitness

    // Don't change these directly. Use updateDependentVariables method instead.
    public int mutation_gene_count = mutation_type == MUTATION_BIT2 ? 2 : (mutation_type == MUTATION_BIT3? 3 : 1); // 1;
    public int mcts_fm_budget = (int) (fm_budget * mcts_budget_perc);
    public int mcts_iteration_budget = (int) (iteration_budget * mcts_budget_perc);
    public int tournament_size = (int) Math.min(2, population_size * tournament_size_perc);
    public double mutation_rate = 1.0 * mutation_gene_count / population_size;
    public int max_individual_length = fm_budget / 2;
    public int min_individual_length = 1;

    public static Map<String, Object[]> getParameterValues() {
        HashMap<String, Object[]> parameterValues = new HashMap<>();
        parameterValues.put("representation", new Integer[]{REP_ACTION_PLAN, REP_ACTIONS_PARAMS, REP_ACTIONS_FOCUS});
        parameterValues.put("perc_focus", new Double[]{0.2, 0.35, 0.5, 0.75});
//        parameterValues.put("draw_code", new Integer[]{DRAW_EXPLORATION, DRAW_THINKING, DRAW_ALL});
//        parameterValues.put("ea_model", new Integer[]{PROBABILISTIC_MODEL, POPULATION_MODEL});
        parameterValues.put("genetic_operator", new Integer[]{MUTATION_AND_CROSSOVER, MUTATION_ONLY, CROSSOVER_ONLY});
        parameterValues.put("mutation_type", new Integer[]{MUTATION_UNIFORM, MUTATION_BIT, MUTATION_BIT3, MUTATION_SOFTMAX, MUTATION_DIVERSITY});
        parameterValues.put("selection_type", new Integer[]{SELECT_RANK, SELECT_TOURNAMENT, SELECT_ROULETTE});
        parameterValues.put("crossover_type", new Integer[]{CROSS_UNIFORM, CROSS_ONE_POINT, CROSS_TWO_POINT});
//        parameterValues.put("evaluation_model", new Integer[]{EVAL_WIN_SCORE, EVAL_SIMPLE_STATE});
        parameterValues.put("init_type", new Integer[]{INIT_RANDOM, INIT_1SLA, INIT_MCTS});
        parameterValues.put("shift_buffer", new Boolean[]{false, true});
//        parameterValues.put("reevaluate", new Boolean[]{false, true});
//        parameterValues.put("elitism", new Boolean[]{false, true});
        parameterValues.put("dynamic_depth", new Boolean[]{false, true});
//        parameterValues.put("bandit_mutation", new Boolean[]{false, true});
//        parameterValues.put("stat_tree", new Boolean[]{false, true});
//        parameterValues.put("use_stat_tree", new Boolean[]{false, true});
//        parameterValues.put("diversity", new Integer[]{DIVERSITY_NONE, DIVERSITY_GENOTYPE});  //, DIVERSITY_PHENOTYPE});
//        parameterValues.put("keep_parents_next_gen", new Boolean[]{false, true});

//        parameterValues.put("budget_type", new Integer[]{TIME_BUDGET, ITERATION_BUDGET, FM_BUDGET});
//        parameterValues.put("break_ms", new Integer[]{5});
//        parameterValues.put("iteration_budget", new Integer[]{100, 200, 500});
//        parameterValues.put("fm_budget", new Integer[]{500, 1000, 2000, 5000});
//        parameterValues.put("mcts_budget_perc", new Double[]{0.25, 0.5, 0.75});

        parameterValues.put("frame_skip", new Integer[]{0, 5, 10});
        parameterValues.put("frame_skip_type", new Integer[]{SKIP_REPEAT, SKIP_NULL, SKIP_RANDOM, SKIP_SEQUENCE});

        parameterValues.put("population_size", new Integer[]{1, 10, 15, 20});
        parameterValues.put("individual_length", new Integer[]{5, 10, 15, 20});
//        parameterValues.put("mcts_depth", new Integer[]{5, 10, 15, 20});
//        parameterValues.put("gene_size", new Integer[]{1, 2, 3, 4, 5});
        parameterValues.put("offspring_count", new Integer[]{5, 10, 15, 20});
        parameterValues.put("no_elites", new Integer[]{0, 1});
//        parameterValues.put("tournament_size_perc", new Double[]{0.2, 0.5, 0.7});
//        parameterValues.put("mutation_gene_count", new Integer[]{1, 2, 3});
//        parameterValues.put("prob_learning_rate", new Double[]{0.1, 0.25, 0.5, 0.75});

//        parameterValues.put("dynamic_depth_modifier", new Integer[]{1, 5, 10});
//        parameterValues.put("dynamic_check_frequency", new Integer[]{5, 15, 30});
//        parameterValues.put("dynamic_fitness_sd_limit_low", new Double[]{0.05, 0.07, 1.0});
//        parameterValues.put("dynamic_fitness_sd_limit_high", new Double[]{0.4, 0.43, 0.45});

        parameterValues.put("mc_rollouts_length_perc", new Double[]{0.0, 0.5, 1.0, 2.0});
        parameterValues.put("mc_rollouts_repeat", new Integer[]{1, 5, 10});

        parameterValues.put("evaluate_act", new Integer[]{EVALUATE_ACT_LAST, EVALUATE_ACT_DELTA, EVALUATE_ACT_AVG,
                EVALUATE_ACT_MIN, EVALUATE_ACT_MAX, EVALUATE_ACT_DISCOUNT});
//        parameterValues.put("evaluate_update", new Integer[]{EVALUATE_UPDATE_RAW, EVALUATE_UPDATE_DELTA,
//                EVALUATE_UPDATE_AVERAGE, EVALUATE_UPDATE_MIN, EVALUATE_UPDATE_MAX});
//        parameterValues.put("evaluate_discount", new Double[]{0.9, 0.99, 1.0});

        parameterValues.put("shift_discount", new Double[]{0.9, 0.99, 1.0});
        parameterValues.put("diversity_weight", new Double[]{0.0, 0.5, 1.0});
        return parameterValues;
    }

    /**
     * Parameters are in tree structure. This method returns children of given parameter.
     * Children parameters are parameters which only matter if parent has a certain value.
     * Maps values of parent to children that become relevant when parent takes that value.
     * @param parameter - given parent
     * @return - list of children
     */
    public static Map<Object,ArrayList<String>> getParameterChildren(String parameter) {
        Map<Object,ArrayList<String>> values = new HashMap<>();
        ArrayList<String> children = new ArrayList<>();

        switch(parameter) {
//            case "budget_type":
//                children.add("break_ms");
//                values.put(TIME_BUDGET, children);
//                children.clear();
//                children.add("iteration_budget");
//                values.put(ITERATION_BUDGET, children);
//                children.clear();
//                children.add("fm_budget");
//                values.put(FM_BUDGET, children);
//                break;
            case "shift_buffer":
                children.add("shift_discount");
                values.put(true, children);
                break;
            case "stat_tree":
                children.add("use_stat_tree");
                values.put(true, children);
                break;
            case "mc_rollouts_length_perc":
                children.add("mc_rollouts_repeat");
                values.put(0.5, children);
                values.put(1, children);
                values.put(2, children);
                break;
            case "evaluate_act":
                children.add("evaluate_discount");
                values.put(EVALUATE_ACT_DISCOUNT, children);
                break;
            case "frame_skip":
                children.add("frame_skip_type");
                values.put(5, children);
                values.put(10, children);
                break;
            case "dynamic_depth":
                children.add("dynamic_depth_modifier");
                children.add("dynamic_check_frequency");
                children.add("dynamic_fitness_sd_limit_low");
                children.add("dynamic_fitness_sd_limit_high");
                values.put(true, children);
                break;
//            case "ea_model":
//                children.clear();
//                children.add("genetic_operator");
//                children.add("init_type");
//                children.add("elitism");
//                children.add("diversity");
//                children.add("keep_parents_next_gen");
//                children.add("population_size");
//                children.add("offspring_count");
//                values.put(POPULATION_MODEL, children);
//                children.clear();
//                children.add("prob_learning_rate");
//                values.put(PROBABILISTIC_MODEL, children);
//                break;
            case "diversity":
                children.add("diversity_weight");
                values.put(true, children);
                break;
            case "elitism":
                children.add("no_elites");
                children.add("reevaluate");
                values.put(true, children);
                break;
            case "init_type":
                children.add("mcts_budget_perc");
                children.add("mcts_depth");
                values.put(INIT_MCTS, children);
                break;
            case "genetic_operator":
                children.add("mutation_type");
                children.add("mutation_gene_count");
                values.put(MUTATION_ONLY, children);

                children.add("selection_type");
                children.add("crossover_type");
                values.put(MUTATION_AND_CROSSOVER, children);

                children.clear();
                children.add("selection_type");
                children.add("crossover_type");
                values.put(CROSSOVER_ONLY, children);
                break;
            case "selection_type":
                children.add("tournament_size_perc");
                values.put(SELECT_TOURNAMENT, children);
                break;
        }
        return values;
    }

    /**
     * Parameters are in tree structure. This method returns parent of given parameter.
     * Parent parameters are parameters which makes given child parameter matter only if parent has a certain value.
     * Maps parent to values which would make this child relevant.
     * Reverse of previous method
     * @param parameter - given child
     * @return - parent
     */
    public static Pair<String, ArrayList<Object>> getParameterParent(String parameter) {
        ArrayList<Object> values = new ArrayList<>();

        switch(parameter) {
//            case "break_ms":
//                values.add(TIME_BUDGET);
//                return new Pair<>("budget_type", values);
//            case "iteration_budget":
//                values.add(ITERATION_BUDGET);
//                return new Pair<>("budget_type", values);
//            case "fm_budget":
//                values.add(FM_BUDGET);
//                return new Pair<>("budget_type", values);
            case "shift_discount":
                values.add(true);
                return new Pair<>("shift_buffer", values);
            case "use_stat_tree":
                values.add(true);
                return new Pair<>("stat_tree", values);
            case "mc_rollouts_repeat":
                values.add(0.5);
                values.add(1);
                values.add(2);
                return new Pair<>("mc_rollouts_length_perc", values);
            case "evaluate_discount":
                values.add(EVALUATE_ACT_DISCOUNT);
                return new Pair<>("evaluate_act", values);
            case "frame_skip_type":
                values.add(5);
                values.add(10);
                return new Pair<>("frame_skip", values);
            case "dynamic_fitness_sd_limit_high":
                values.add(true);
                return new Pair<>("dynamic_depth", values);
            case "dynamic_fitness_sd_limit_low":
                values.add(true);
                return new Pair<>("dynamic_depth", values);
            case "dynamic_check_frequency":
                values.add(true);
                return new Pair<>("dynamic_depth", values);
            case "dynamic_depth_modifier":
                values.add(true);
                return new Pair<>("dynamic_depth", values);
//            case "offspring_count":
//                values.add(POPULATION_MODEL);
//                return new Pair<>("ea_model", values);
//            case "population_size":
//                values.add(POPULATION_MODEL);
//                return new Pair<>("ea_model", values);
//            case "keep_parents_next_gen":
//                values.add(POPULATION_MODEL);
//                return new Pair<>("ea_model", values);
//            case "diversity":
//                values.add(POPULATION_MODEL);
//                return new Pair<>("ea_model", values);
//            case "elitism":
//                values.add(POPULATION_MODEL);
//                return new Pair<>("ea_model", values);
//            case "init_type":
//                values.add(POPULATION_MODEL);
//                return new Pair<>("ea_model", values);
//            case "genetic_operator":
//                values.add(POPULATION_MODEL);
//                return new Pair<>("ea_model", values);
//            case "prob_learning_rate":
//                values.add(PROBABILISTIC_MODEL);
//                return new Pair<>("ea_model", values);
            case "diversity_weight":
                values.add(true);
                return new Pair<>("diversity", values);
            case "no_elites":
                values.add(true);
                return new Pair<>("elitism", values);
            case "reevaluate":
                values.add(true);
                return new Pair<>("elitism", values);
            case "tournament_size_perc":
                values.add(SELECT_TOURNAMENT);
                return new Pair<>("selection_type", values);
            case "mcts_budget_perc":
                values.add(INIT_MCTS);
                return new Pair<>("init_type", values);
            case "mcts_depth":
                values.add(INIT_MCTS);
                return new Pair<>("init_type", values);
            case "selection_type":
                values.add(CROSSOVER_ONLY);
                values.add(MUTATION_AND_CROSSOVER);
                return new Pair<>("genetic_operator", values);
            case "crossover_type":
                values.add(CROSSOVER_ONLY);
                values.add(MUTATION_AND_CROSSOVER);
                return new Pair<>("genetic_operator", values);
            case "mutation_type":
                values.add(MUTATION_ONLY);
                values.add(MUTATION_AND_CROSSOVER);
                return new Pair<>("genetic_operator", values);
            case "mutation_gene_count":
                values.add(MUTATION_ONLY);
                values.add(MUTATION_AND_CROSSOVER);
                return new Pair<>("genetic_operator", values);
        }
        return null;
    }

    public void setParameterValues(HashMap<String, Object> parameterValues) {
        for (Map.Entry<String, Object> e : parameterValues.entrySet()) {
            setParameter(e.getKey(), e.getValue());
        }
    }

    public void setParameter(String name, Object value) {
        switch(name) {
            case "representation": representation = (int) value; break;
            case "perc_focus": perc_focus = (double) value; break;
            case "genetic_operator": genetic_operator = (int) value; break;
            case "mutation_type": mutation_type = (int) value; break;
            case "selection_type": selection_type = (int) value; break;
            case "crossover_type": crossover_type = (int) value; break;
            case "evaluation_model": evaluation_model = (int) value; break;
            case "shift_buffer": shift_buffer = (boolean) value; break;
            case "diversity": diversity = (int) value; break;
            case "keep_parents_next_gen": keep_parents_next_gen = (boolean) value; break;
            case "budget_type": budget_type = (int) value; break;
            case "iteration_budget": iteration_budget = (int) value; break;
            case "fm_budget": fm_budget = (int) value; break;
            case "mcts_budget_perc": mcts_budget_perc = (double) value; break;
            case "frame_skip": frame_skip = (int) value; break;
            case "frame_skip_type": frame_skip_type = (int) value; break;
            case "population_size": population_size = (int) value; break;
            case "individual_length": individual_length = (int) value; break;
            case "offspring_count": offspring_count = (int) value; break;
            case "no_elites": no_elites = (int) value; break;
            case "tournament_size_perc": tournament_size_perc = (double) value; break;
            case "mutation_gene_count": mutation_gene_count = (int) value; break;
            case "evaluate_act": evaluate_act = (int) value; break;
            case "evaluate_update": evaluate_update = (int) value; break;
            case "evaluate_discount": evaluate_discount = (double) value; break;
            case "shift_discount": shift_discount = (double) value; break;
            case "diversity_weight": diversity_weight = (double) value; break;
        }
        updateDependentVariables();
    }


    public Object getParameter(String name) {
        switch(name) {
            case "representation": return representation;
            case "perc_focus": return perc_focus;
            case "genetic_operator": return genetic_operator;
            case "mutation_type": return mutation_type;
            case "selection_type": return selection_type;
            case "crossover_type": return crossover_type;
            case "evaluation_model": return evaluation_model;
            case "shift_buffer": return shift_buffer;
            case "diversity": return diversity;
            case "keep_parents_next_gen": return keep_parents_next_gen;
            case "budget_type": return budget_type;
            case "iteration_budget": return iteration_budget;
            case "fm_budget": return fm_budget;
            case "mcts_budget_perc": return mcts_budget_perc;
            case "frame_skip": return frame_skip;
            case "frame_skip_type": return frame_skip_type;
            case "population_size": return population_size;
            case "individual_length": return individual_length;
            case "offspring_count": return offspring_count;
            case "no_elites": return no_elites;
            case "tournament_size_perc": return tournament_size_perc;
            case "mutation_gene_count": return mutation_gene_count;
            case "evaluate_act": return evaluate_act;
            case "evaluate_update": return evaluate_update;
            case "evaluate_discount": return evaluate_discount;
            case "shift_discount": return shift_discount;
            case "diversity_weight": return diversity_weight;
        }
        return null;
    }

    public void printParameters() {
        ArrayList<String> params = getParameterNames();
        for (String p: params) {
            Object value = getParameter(p);
            System.out.println(p + " = " + interpret(p, value));
        }
        System.out.println("----------------");
    }

    public static ArrayList<String> getParameterNames() {
        Map<String, Object[]> params = getParameterValues();
        ArrayList<String> paramList = new ArrayList<>();
        for (Map.Entry<String, Object[]> e: params.entrySet()) {
            paramList.add(e.getKey());
        }
        return paramList;
    }

    /**
     * Updates the variables used by RHEA which are set from other parameters.
     * Should call this whenever these parameters are changed.
     */
    public void updateDependentVariables() {
        mutation_gene_count = mutation_type == MUTATION_BIT2 ? 2 : (mutation_type == MUTATION_BIT3? 3 : 1); // 1;
        mcts_fm_budget = (int) (fm_budget * mcts_budget_perc);
        mcts_iteration_budget = (int) (iteration_budget * mcts_budget_perc);
        tournament_size = (int) Math.min(2, population_size * tournament_size_perc);
        mutation_rate = 1.0 * mutation_gene_count / population_size;
        max_individual_length = fm_budget / 2;
        min_individual_length = 1;

        if (population_size < no_elites) {  // Can't have more elites than population size!
            no_elites = population_size - 1;
        }
        if (offspring_count < population_size) {  // If less offspring than population size, keep parents for next gen
            keep_parents_next_gen = true;
        }
        if (population_size < 2) {  // Don't do crossover if less than 2 individuals, can't cross otherwise!
            genetic_operator = MUTATION_ONLY;
        }
    }

    /**
     * Interpret parameters with integer values from constants
     * @param parameter - parameter name
     * @param value - parameter value
     * @return - name of constant
     */
    public static Object interpret(String parameter, Object value) {
        Map<String, String[]> constants = constantNames();
        if (constants.containsKey(parameter)) {
            return constants.get(parameter)[(int)value];
        }
        return value;
    }

    public static Map<String, String[]> constantNames() {
        HashMap<String, String[]> names = new HashMap<>();
        names.put("evaluate_act", new String[]{"EVALUATE_ACT_LAST", "EVALUATE_ACT_DELTA", "EVALUATE_ACT_AVG",
                "EVALUATE_ACT_MIN", "EVALUATE_ACT_MAX", "EVALUATE_ACT_DISCOUNT"});
        names.put("evaluate_update", new String[]{"EVALUATE_UPDATE_RAW", "EVALUATE_UPDATE_DELTA",
                "EVALUATE_UPDATE_AVERAGE", "EVALUATE_UPDATE_MIN", "EVALUATE_UPDATE_MAX"});
        names.put("diversity", new String[]{"DIVERSITY_NONE", "DIVERSITY_GENOTYPE", "DIVERSITY_PHENOTYPE"});
//        names.put("ea_model", new String[]{"PROBABILISTIC_MODEL", "POPULATION_MODEL"});
        names.put("budget_type", new String[]{"TIME_BUDGET", "ITERATION_BUDGET", "FM_BUDGET"});
        names.put("mutation_operator", new String[]{"MUTATION_AND_CROSSOVER", "MUTATION_ONLY", "CROSSOVER_ONLY"});
//        names.put("mutation_type", new String[]{"MUTATION_UNIFORM", "MUTATION_BIT", "MUTATION_BIT2", "MUTATION_BIT3", "MUTATION_SOFTMAX", "MUTATION_DIVERSITY"});
        names.put("mutation_type", new String[]{"MUTATION_UNIFORM", "MUTATION_BIT", "MUTATION_BIT3", "MUTATION_SOFTMAX", "MUTATION_DIVERSITY"});
        names.put("selection_type", new String[]{"SELECT_RANK", "SELECT_TOURNAMENT", "SELECT_ROULETTE"});
        names.put("crossover_type", new String[]{"CROSS_UNIFORM", "CROSS_ONE_POINT", "CROSS_TWO_POINT"});
        names.put("evaluation_model", new String[]{"EVAL_WIN_SCORE", "EVAL_SIMPLE_STATE"});
        names.put("init_type", new String[]{"INIT_RANDOM", "INIT_1SLA", "INIT_MCTS"});
        names.put("frame_skip_type", new String[]{"SKIP_REPEAT", "SKIP_NULL", "SKIP_RANDOM", "SKIP_SEQUENCE"});
//        names.put("draw_code", new String[]{"DRAW_EXPLORATION", "DRAW_THINKING", "DRAW_ALL"});
        return names;
    }

    public static ParameterSet translate(int[] values) {
        ParameterSet ps = new ParameterSet();
        ArrayList<String> paramList = getParameterNames();
        Map<String, Object[]> params = getParameterValues();
        int k = 0;
        for (String param : paramList) {
//            if (getParameterParent(param) == null) {
//                System.out.println(param + " " + values[k] + " " + Arrays.toString(params.get(param)));
                Object value = params.get(param)[values[k]];
                ps.setParameter(param, value);
                k++;
//            }
        }
        return ps;
    }

    public static void printParameterStructure(ParameterSet ps) {
        Map<String, Object[]> params = getParameterValues();
        double searchSpaceSize = 1;
        for (String p : params.keySet()) {
            if (getParameterParent(p) == null) {
                searchSpaceSize *= ps.printParameter(p, params, 0);
            }
        }
        DecimalFormat df = new DecimalFormat("0.000E0");
        System.out.println("Parameter space size: " + df.format(searchSpaceSize));
    }

    private int printParameter(String root, Map<String, Object[]> params, int depth) {
        Object[] valueArray = params.get(root);
        if (valueArray == null) {
            return 1;
        }
        int searchSpaceSize = valueArray.length;
        StringBuilder values = new StringBuilder("[");
        if (!(valueArray[0] instanceof Integer) || interpret(root, (int)valueArray[0]) == null) {
            values = new StringBuilder(Arrays.toString(valueArray));
        } else {
            for (int i = 0; i < valueArray.length; i++) {
                values.append(interpret(root, (int) valueArray[i]));
                if (i < valueArray.length - 1) {
                    values.append(", ");
                }
            }
            values.append("]");
        }

        StringBuilder stringToPrint = new StringBuilder();
        for (int i = 0; i < depth * 4; i++) {
            stringToPrint.append(" ");
        }
        stringToPrint.append("- ").append(root).append(": ").append(values);
        System.out.println(stringToPrint);

        // Recursively print children
        Map<Object,ArrayList<String>> children = getParameterChildren(root);
        HashSet<String> childSet = new HashSet<>();
        for (Map.Entry e : children.entrySet()) {
            childSet.addAll((ArrayList<String>) e.getValue());
        }
        for (String c : childSet) {
            searchSpaceSize *= printParameter(c, params, depth + 1);
        }

        return searchSpaceSize;
    }

    public static void main(String[] args) {
        printParameterStructure(new ParameterSet());
    }
}
