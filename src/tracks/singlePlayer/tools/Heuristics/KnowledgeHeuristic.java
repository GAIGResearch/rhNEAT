package tracks.singlePlayer.tools.Heuristics;

import core.game.Observation;
import core.game.StateObservation;
import ontology.Types;

import java.util.ArrayList;
import java.util.HashMap;

import static RHEA.utils.Constants.HUGE_NEGATIVE;
import static RHEA.utils.Constants.HUGE_POSITIVE;

public class KnowledgeHeuristic extends StateHeuristic {

    StateObservation root;
    int[] indexes;  // Representing focus of the agent on only part of screen
    HashMap<Integer, Double> knowledge;  // Mapping from sprite id to score value

    public KnowledgeHeuristic(StateObservation root) {
        this.root = root;
        this.indexes = new int[0];
        knowledge = new HashMap<>();
    }

    public void updateFocus(int[] indexes) {
        this.indexes = indexes;
    }

    @Override
    public double evaluateState(StateObservation stateObs) {
        ArrayList<Observation>[][] grid = stateObs.getObservationGrid();
        double gameScoreDiff = stateObs.getGameScore() - root.getGameScore();

        // Calculate heuristic score based on focus
        double heuristicScore = 0;
        for (int i: indexes) {
            int x = i % grid.length;
            int y = i / grid.length;

            ArrayList<Observation> interest = grid[x][y];  // This is an interesting point, add it heuristic score
            for (Observation o: interest) {
                heuristicScore += o.itype;
            }
        }

        // Update knowledge based on focus
        for (int i: indexes) {
            int x = i % grid.length;
            int y = i / grid.length;

            ArrayList<Observation> interest = grid[x][y];
            for (Observation o: interest) {
                if (knowledge.containsKey(o.itype)) {
                    double cur = knowledge.get(o.itype);
                    knowledge.put(o.itype, (cur + gameScoreDiff) / 2);  // Average?
                } else {
                    knowledge.put(o.itype, gameScoreDiff);
                }
            }
        }

        // Check win/loss
        boolean gameOver = stateObs.isGameOver();
        Types.WINNER win = stateObs.getGameWinner();
        if(gameOver && win == Types.WINNER.PLAYER_LOSES)
            return HUGE_NEGATIVE;
        if(gameOver && win == Types.WINNER.PLAYER_WINS)
            return HUGE_POSITIVE;

        return heuristicScore;
    }
}
