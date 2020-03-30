package tracks.singlePlayer.advanced.rhneat.test;

import RHEA.utils.ParameterSet;
import core.ArcadeMachine;
import core.competition.CompetitionParameters;
import core.game.ExperimentalGame;
import core.game.Game;
import core.player.Player;
import core.vgdl.VGDLFactory;
import core.vgdl.VGDLParser;
import core.vgdl.VGDLRegistry;
import ontology.Types;
import tools.ElapsedCpuTimer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA. User: Diego Date: 06/11/13 Time: 11:24 This is a
 * Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class ExperimentalMachine {

	private static int RH_NEAT = 0;
	private static int MCTS = 1;
	private static int RHEA = 2;

	private static final boolean VERBOSE = false;

	private static ArrayList<Double> allScores;
	private static ArrayList<Types.ACTIONS> allActions;
	private static String evoFile, actionFile;
	private static BufferedWriter evoWriter, actionWriter;

	public static double[] runOneGame(int playerId, String game_file, String level_file, boolean visuals, String actionFile, String evoFile, long randomSeed) {
		VGDLFactory.GetInstance().init(); //This always first thing to do.
		VGDLRegistry.GetInstance().init();

		if (VERBOSE)
			System.out.println(" ** Playing game " + game_file + ", level " + level_file + " **");

		// First, we create the game to be played.
		Game toPlay = new VGDLParser().parseGame(game_file);
		toPlay.buildLevel(level_file, (int)randomSeed);

		// Warm the game up.
		ArcadeMachine.warmUp(toPlay, CompetitionParameters.WARMUP_TIME);

		// Create the player.

		// Determine the time due for the controller creation.
		ElapsedCpuTimer ect = new ElapsedCpuTimer();
		ect.setMaxTimeMillis(CompetitionParameters.INITIALIZATION_TIME);

		Player player;
		if(playerId == RH_NEAT)
		{
			player = new tracks.singlePlayer.advanced.rhneat.Agent(toPlay.getObservation(), ect);
		}else if (playerId == MCTS)
		{
			ParameterSet mctsParms = new ParameterSet();
			mctsParms.individual_length = 15;
			mctsParms.fm_budget = 1000;
			player = new tracks.singlePlayer.advanced.sampleMCTS.Agent(toPlay.getObservation(), ect, mctsParms);
		}else if (playerId == RHEA)
		{
			ParameterSet rheaParms = new ParameterSet();
			rheaParms.individual_length = 15;
			rheaParms.fm_budget = 1000;
			rheaParms.population_size = 10;
			rheaParms.offspring_count = rheaParms.population_size;
			player = new RHEA.Agent(toPlay.getObservation(), ect, rheaParms);
		} else return null;

		player.setPlayerID(0);
		player.setup(null, (int)randomSeed, false);
		setup(evoFile, actionFile);

		// Then, play the game.
		Player[] playerArray = new Player[]{player};
		if (visuals)
			ExperimentalGame.playGame(toPlay, playerArray, (int)randomSeed);
		else
			ExperimentalGame.runGame(toPlay, playerArray, (int)randomSeed);

		//Finally, when the game is over, we need to tear the players down.
		ArcadeMachine.tearPlayerDown(toPlay, playerArray, actionFile, (int)randomSeed, true);
		teardown();

		//This, the last thing to do in this method, always:
		toPlay.handleResult();
		printExpResult(toPlay);

		return toPlay.getFullResult();
	}

	//Result, [WIN] [SCORE] [TICK], [ENTROPY_C] [ACTIONS CHOSEN]
	private static void printExpResult(Game toPlay) {
//		System.out.println("Result " + toPlay.getAvatar().getWinState().key() + " " + toPlay.getAvatar().getScore() + " "
//				+ toPlay.getGameTick() + " " + actionsToString(EvoAnalyzer.entropyC));
		System.out.println("Result " + toPlay.getAvatar().getWinState().key() + " " + toPlay.getAvatar().getScore() + " "
				+ toPlay.getGameTick() + " " + toPlay.getRandomSeed());
	}

	private static void setup(String evoFile, String actionFile) {
		ExperimentalMachine.evoFile = evoFile;
		ExperimentalMachine.actionFile = actionFile;

		ExperimentalMachine.allScores = new ArrayList<>();
		ExperimentalMachine.allActions = new ArrayList<>();

		try {
			if((ExperimentalMachine.evoFile != null && !ExperimentalMachine.evoFile.equals(""))) {
				ExperimentalMachine.evoWriter = new BufferedWriter(new FileWriter(new File(ExperimentalMachine.evoFile))); // override previous file
			}
			if((ExperimentalMachine.actionFile != null && !ExperimentalMachine.actionFile.equals(""))) {
				ExperimentalMachine.actionWriter = new BufferedWriter(new FileWriter(new File(ExperimentalMachine.actionFile))); // override previous file
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void teardown() {
		try {
			if(ExperimentalMachine.evoFile != null && !ExperimentalMachine.evoFile.equals("")) {
				ExperimentalMachine.evoWriter.close();
			}
			if (ExperimentalMachine.actionFile != null && !ExperimentalMachine.actionFile.equals("")) {
				ExperimentalMachine.actionWriter.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	static public void logAction(Types.ACTIONS action) {
		try {
			String score = "";
			if (!ExperimentalMachine.allScores.isEmpty()) score += ExperimentalMachine.allScores.get(ExperimentalMachine.allScores.size()-1);

			if(ExperimentalMachine.actionFile != null)
			{
				ExperimentalMachine.allActions.add(action);
				ExperimentalMachine.actionWriter.write(action.toString() + " " + score + "\n");
				ExperimentalMachine.actionWriter.flush();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static public void logScore(double score) {
		ExperimentalMachine.allScores.add(score);
	}

	static public BufferedWriter getEvoWriter() { return evoWriter; }
}