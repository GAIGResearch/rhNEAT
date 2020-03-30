package tracks.singlePlayer.advanced.rhneat.test;

import tools.Utils;

import java.util.Random;

public class RHneatExecutor {

    public static void main(String[] args) {

        // Experiment settings
        int agentId = Integer.parseInt(args[0]); // See ExperimentalMachine constants.
        int gameIdx = Integer.parseInt(args[1]); // game index

        // Get the games collection
        String spGamesCollection =  "examples/exp_games_sp.csv";
        String[][] games = Utils.readGames(spGamesCollection);

        // Number of levels to play, L. Number of level repetitions, M.
        int L = Integer.parseInt(args[2]);
        int M = Integer.parseInt(args[3]);

        // Get the game
        String gameName = games[gameIdx][1];
        String game = games[gameIdx][0];

        // Run L levels
        for (int i = 0; i < L; i++) {

            // Get the level
            String level = game.replace(gameName, gameName + "_lvl" + i);

            // Run M repetitions of level_i
            for (int j = 0; j < M; j++) {

                // Seed and controller to run
                int seed = new Random().nextInt();

                //Play games with params
                ExperimentalMachine.runOneGame(agentId, game, level, false, null, null, seed);
            }
        }
    }
}
