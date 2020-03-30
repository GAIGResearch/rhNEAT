package core.game;

import core.competition.CompetitionParameters;
import core.player.Player;
import core.vgdl.VGDLViewer;
import ontology.Types;
import tools.JEasyFrame;
import tracks.singlePlayer.advanced.rhneat.test.ExperimentalMachine;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ExperimentalGame {

    public static void runGame(Game game, Player[] players, int randomSeed) {
        // Prepare some structures and references for this game.
        game.prepareGame(players, randomSeed, -1);

        // Play until the game is ended
        while (!game.isEnded) {
            gameCycle(game); // Execute a game cycle.
        }

        // Update the forward model for the game state sent to the controller.
        game.fwdModel.update(game);
    }

    private static void gameCycle(Game game) {
        game.gameTick++; // next game tick.

        // Update our state observation (forward model) with the information of the current game state.
        game.fwdModel.update(game);

        // Execute a game cycle:
        game.tick(); // update for all entities.
        game.eventHandling(); // handle events such collisions.
        game.clearAll(game.fwdModel); // clear all additional data, including dead sprites.
        game.terminationHandling(); // check for game termination.
        game.checkTimeOut(); // Check for end of game by time steps.

        ExperimentalMachine.logScore(game.getScore());
        ExperimentalMachine.logAction(game.avatarLastAction[0]);
    }

    public static void playGame(Game game, Player[] players, int randomSeed) {
        // Prepare some structures and references for this game.
        game.prepareGame(players, randomSeed, -1);

        // Create and initialize the panel for the graphics.
        VGDLViewer view = new VGDLViewer(game, players[0]);
        JFrame frame = new JEasyFrame(view, "Java-vgdl");

        frame.addKeyListener(Game.ki);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == Types.ACTIONS.ACTION_PAUSE.getKey()[0]) {
                    game.pause();
                }
            }
        });
        frame.addWindowListener(Game.wi);
        Game.wi.windowClosed = false;

        boolean firstRun = true;

        // Play until the game is ended
        while (!game.isEnded && !Game.wi.windowClosed) {
            // Determine the time to adjust framerate.
            long then = System.currentTimeMillis();

            if (!game.isPaused) {
                gameCycle(game); // Execute a game cycle.

                // Get the remaining time to keep fps.
                long now = System.currentTimeMillis();
                int remaining = (int) Math.max(0, CompetitionParameters.LONG_DELAY - (now - then));

                // Wait until de next cycle.
                game.waitStep(remaining);

                // Draw all sprites in the panel.
                view.paint(game.spriteGroups);

                // Update the frame title to reflect current score and tick.
                game.setTitle(frame);
            } else {
                frame.setTitle("PAUSED");
            }

            if (firstRun) {
                if (CompetitionParameters.dialogBoxOnStartAndEnd) {
                    JOptionPane.showMessageDialog(frame, "Click OK to start.");
                }

                firstRun = false;
            }
        }

        if (!Game.wi.windowClosed && CompetitionParameters.killWindowOnEnd) {
            if (CompetitionParameters.dialogBoxOnStartAndEnd) {
                String sb = "GAMEOVER: YOU LOSE.";
                if (game.avatars[0] != null) {
                    sb = "GAMEOVER: YOU "
                            + ((game.avatars[0].getWinState() == Types.WINNER.PLAYER_WINS) ? "WIN." : "LOSE.");
                }
                JOptionPane.showMessageDialog(frame, sb);
            }
            frame.dispose();
        }

        // Update the forward model for the game state sent to the controller.
        game.fwdModel.update(game);
    }
}
