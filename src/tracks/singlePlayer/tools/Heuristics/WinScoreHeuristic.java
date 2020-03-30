package tracks.singlePlayer.tools.Heuristics;

import core.game.StateObservation;
import ontology.Types;
import tools.Utils;

/**
 * Created with IntelliJ IDEA.
 * User: ssamot
 * Date: 11/02/14
 * Time: 15:44
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class WinScoreHeuristic extends StateHeuristic {

    private static final double HUGE_NEGATIVE = -1000000.0;
    private static final double HUGE_POSITIVE =  1000000.0;

    public WinScoreHeuristic(StateObservation stateObs) {

    }

    public double evaluateState(StateObservation stateObs) {
        Types.WINNER win = stateObs.getGameWinner();
        boolean gameOver = win != Types.WINNER.NO_WINNER;
        double rawScore = stateObs.getGameScore();

        if(gameOver && win == Types.WINNER.PLAYER_LOSES)
            return HUGE_NEGATIVE;

        if(gameOver && win == Types.WINNER.PLAYER_WINS)
            return HUGE_POSITIVE;

        return rawScore;
    }


}


