package player.behavior;

import blarg.chess.Board;
import blarg.chess.Move;
import blarg.chess.Piece;
import blarg.newnet.Network;
import blarg.newnet.ValueTracker;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cymrucoder
 */
public class NewNetworkBehavior extends PlayerBehavior {

    private Network network;
    private double bestWinRate;
    Random rand;
    
    public NewNetworkBehavior(Board board) {
        super();
        this.board = board;
        setupNetwork();
        bestWinRate = 0.0;
        rand = new Random();
    }
    
    private void setupNetwork() {
        try {
            network = new Network(new FileReader("networks\\royalphess1.csv"));
            network.enableView();
            
//        Layer inputLayer = new Layer();
//        Neuron friendlyPawnCountNeuron = new Neuron();
//        friendlyPawnCountNeuron.addConnection(0);
//        friendlyPawnCountNeuron.setUseActivationFunction(true);
//        Neuron enemyPawnCountNeuron = new Neuron();
//        enemyPawnCountNeuron.addConnection(1);
//        enemyPawnCountNeuron.setUseActivationFunction(true);
//        inputLayer.addNeuron(friendlyPawnCountNeuron);
//        inputLayer.addNeuron(enemyPawnCountNeuron);
//        
////        Layer middleLayer = new Layer();
////        Neuron middleNeuronUpper = new Neuron(0.5, 0.5, 0.5);
////        Neuron middleNeuronLower = new Neuron(0.5, 0.5, 0.5);
////        middleLayer.addNeuron(middleNeuronUpper);
////        middleLayer.addNeuron(middleNeuronLower);
//        
//        Layer outputLayer = new Layer();
//        //Neuron pieceToMoveNeuron = new Neuron(0.5, 0.5, 0.5);
//        //Neuron moveToMakeNeuron = new Neuron(0.5, 0.5, 0.5);
//        //outputLayer.addNeuron(pieceToMoveNeuron);
//        //outputLayer.addNeuron(moveToMakeNeuron );
//        Neuron shouldCaptureNeuron = new Neuron();
//        shouldCaptureNeuron.addConnection(0);
//        shouldCaptureNeuron.addConnection(1);
//        shouldCaptureNeuron.setUseActivationFunction(true);
//        outputLayer.addNeuron(shouldCaptureNeuron);
//        
//        network.addLayer(inputLayer);
//        //network.addLayer(middleLayer);
//        network.addLayer(outputLayer);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NewNetworkBehavior.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NewNetworkBehavior.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        
        double canCapturePawns = 0.0;
        double canPawnsBeCaptured = 0.0;
        double canCaptureKing = 0.0;
        double kingInCheck = 0.0;
        
        for (Move move : candidateMoves) {
            String thisType = board.getPieces()[move.getOldX()][move.getOldY()].getType();
            String typeInNewSquare;
            
            if (board.getPieces()[move.getNewX()][move.getNewY()] == null) {
                typeInNewSquare = "";
            } else {
                typeInNewSquare = board.getPieces()[move.getNewX()][move.getNewY()].getType();
            }
            
            if (Piece.PAWN.equals(thisType) && Piece.PAWN.equals(typeInNewSquare)) {
                canCapturePawns = 1.0;
            }
            
            if (Piece.KING.equals(typeInNewSquare)) {
                canCaptureKing = 1.0;
            }
        }
        
        int moveOffset = 1;// Using this later
        int enemyColor = Piece.WHITE;
            
        if (board.getCurrentTurnColor() == Piece.WHITE) {
            enemyColor = Piece.BLACK;
            moveOffset = -11;// We're moving "down" the board numerically (in my backwards system) if we're white
        } 
            
        List<Move> enemyMoves = board.getMoveCandidates(enemyColor);
        
        for (Move move : enemyMoves) {
            String enemyType = board.getPieces()[move.getOldX()][move.getOldY()].getType();
            String typeInNewSquare;
            
            if (board.getPieces()[move.getNewX()][move.getNewY()] == null) {
                typeInNewSquare = "";
            } else {
                typeInNewSquare = board.getPieces()[move.getNewX()][move.getNewY()].getType();
            }
            
            if (Piece.PAWN.equals(enemyType) && Piece.PAWN.equals(typeInNewSquare)) {
                canPawnsBeCaptured = 1.0;
            }
            
            if (Piece.KING.equals(typeInNewSquare)) {
                kingInCheck = 1.0;
            }
        }
        
        inputs.add(2, canCapturePawns);
        inputs.add(3, canPawnsBeCaptured);
        inputs.add(4, canCaptureKing);
        inputs.add(5, kingInCheck);
        
        ValueTracker outputs = network.process(inputs);
        //System.out.println("Output " + outputs.get(0));
        
        int selectedMove = 0;
                
        for (int i = 0; i < outputs.size(); i++) {
            if (outputs.get(i) > outputs.get(selectedMove)) {
                selectedMove = i;
            }
        }
        
        for (Move move : candidateMoves) {
            String thisType = board.getPieces()[move.getOldX()][move.getOldY()].getType();
            String typeInNewSquare;
            
            if (board.getPieces()[move.getNewX()][move.getNewY()] == null) {
                typeInNewSquare = "";
            } else {
                typeInNewSquare = board.getPieces()[move.getNewX()][move.getNewY()].getType();
            }
            
            if (selectedMove == 1 && Piece.PAWN.equals(thisType) && Piece.PAWN.equals(typeInNewSquare)) {
                return move;
            }
            
            if (selectedMove == 2 && Piece.KING.equals(typeInNewSquare)) {
                return move;
            }
            
            if (selectedMove == 3 && Piece.KING.equals(thisType) && (move.getOldY() - move.getNewY() == moveOffset)) {// Last bit is "are we moving forward"
                return move;
            }
            
            if (selectedMove == 4 && Piece.KING.equals(thisType) && (move.getOldX() != move.getNewX())) {
                return move;
            }
            
            if (selectedMove == 5 && Piece.KING.equals(thisType) && (move.getOldY() - move.getNewY() == -moveOffset)) {
                return move;
            }
        }
        
//        if (outputs.get(0) > 0.0) {
//            for (Move move : candidateMoves) {
//                if (board.getPieces()[move.getNewX()][move.getNewY()] != null) {
//                    return move;
//                }
//            }
//        }

        

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
        network.updateView();
    }
}
