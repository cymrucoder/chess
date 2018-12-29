package player.behavior;

import blarg.chess.Board;
import blarg.chess.Move;
import blarg.newnet.Layer;
import blarg.newnet.Network;
import blarg.newnet.Neuron;
import blarg.newnet.ValueTracker;
import java.util.List;
import java.util.Random;

/**
 *
 * @author cymrucoder
 */
public class NetworkBehavior extends PlayerBehavior {

    private Network network;
    private double bestWinRate;
    Random rand;
    
    public NetworkBehavior(Board board) {
        super();
        this.board = board;
        setupNetwork();
        bestWinRate = 0.0;
        rand = new Random();
    }
    
    private void setupNetwork() {
        network = new Network();
        
        Layer inputLayer = new Layer();
        Neuron friendlyPawnCountNeuron = new Neuron();
        friendlyPawnCountNeuron.addConnection(0);
        friendlyPawnCountNeuron.setUseActivationFunction(true);
        Neuron enemyPawnCountNeuron = new Neuron();
        enemyPawnCountNeuron.addConnection(1);
        enemyPawnCountNeuron.setUseActivationFunction(true);
        inputLayer.addNeuron(friendlyPawnCountNeuron);
        inputLayer.addNeuron(enemyPawnCountNeuron);
        
//        Layer middleLayer = new Layer();
//        Neuron middleNeuronUpper = new Neuron(0.5, 0.5, 0.5);
//        Neuron middleNeuronLower = new Neuron(0.5, 0.5, 0.5);
//        middleLayer.addNeuron(middleNeuronUpper);
//        middleLayer.addNeuron(middleNeuronLower);
        
        Layer outputLayer = new Layer();
        //Neuron pieceToMoveNeuron = new Neuron(0.5, 0.5, 0.5);
        //Neuron moveToMakeNeuron = new Neuron(0.5, 0.5, 0.5);
        //outputLayer.addNeuron(pieceToMoveNeuron);
        //outputLayer.addNeuron(moveToMakeNeuron );
        Neuron shouldCaptureNeuron = new Neuron();
        shouldCaptureNeuron.addConnection(0);
        shouldCaptureNeuron.addConnection(1);
        shouldCaptureNeuron.setUseActivationFunction(true);
        outputLayer.addNeuron(shouldCaptureNeuron);
        
        network.addLayer(inputLayer);
        //network.addLayer(middleLayer);
        network.addLayer(outputLayer);
    }
    
    @Override
    public Move decideMove(List<Move> candidateMoves) {
        int friendlyPawns = 0;
        int enemyPawns = 0;
        
        int[][] intBoard = board.generateIntBoard();
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (intBoard[i][j] == Board.WHITE_PAWN) {
                    friendlyPawns++;
                } else if (intBoard[i][j] == Board.WHITE_PAWN) {
                    enemyPawns++;
                }
            }
        }
        
        ValueTracker inputs = new ValueTracker();
        inputs.add(0, (double) friendlyPawns / 8.0);
        inputs.add(1, (double) enemyPawns / 8.0);
        
        ValueTracker outputs = network.process(inputs);
        //System.out.println("Output " + outputs.get(0));
        
        if (outputs.get(0) > 0.0) {
            for (Move move : candidateMoves) {
                if (board.getPieces()[move.getNewX()][move.getNewY()] != null) {
                    return move;
                }
            }
        }
        return candidateMoves.get(rand.nextInt(candidateMoves.size()));
    }
    
    @Override
    public void notifyWinRate(double winRate) {
        if (winRate > bestWinRate) {
            System.out.println("New best win rate  " + winRate);
            System.out.println("Values are: \n" + network.toString());
            bestWinRate = winRate;
        } else {
            network.undoAdjust();
        }
        network.adjustForError(winRate);
    }

}
