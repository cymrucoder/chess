package blarg.chess;

import blarg.chess.view.BoardView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author cymrucoder
 */
public class Board {

    public static final int NONE = -1;
    public static final int BLACK_PAWN = 0;
    public static final int BLACK_BISHOP = 1;
    public static final int BLACK_KNIGHT = 2;
    public static final int BLACK_ROOK = 3;
    public static final int BLACK_QUEEN = 4;
    public static final int BLACK_KING = 5;
    public static final int WHITE_PAWN = 6;
    public static final int WHITE_BISHOP = 7;
    public static final int WHITE_KNIGHT = 8;
    public static final int WHITE_ROOK = 9;
    public static final int WHITE_QUEEN = 10;
    public static final int WHITE_KING = 11;

    private Piece pieces[][];
    private Player whitePlayer;
    private Player blackPlayer;

    public static void main(String args[]) throws InterruptedException {
        Board board = new Board();
        board.setupView();
        
        for (int i = 0; i < 200; i++) {
            board.runGames(1000);
            board.notifyWinRates();
            //TimeUnit.SECONDS.sleep(1);
        }
    }

    private BoardView bv;
    private boolean isWhitesTurn;
    private boolean onePlayerStuck;
    private int kingCaptured;
    private int kingCaps;
    private int pawnCount;
    private int[][] gameOneTurnAgo;
    private int[][] gameTwoTurnsAgo;
    private int[][] gameThreeTurnsAgo;
    private int[][] gameFourTurnsAgo;

    public Board() {

        whitePlayer = new Player(this, "random");
        blackPlayer = new Player(this, "newnetwork");
        setupBoard();
    }
    
    public void notifyWinRates() {
        double whiteWinRate;
        double blackWinRate;
        
        if (blackWins == 0) {// Avoid divide by 0
            whiteWinRate = 1000000000.0;
            blackWinRate = 0.0;
        } else if (whiteWins == 0) {
            blackWinRate = 1000000000.0;
            whiteWinRate = 0.0;
        } else {
            whiteWinRate = ((double) whiteWins / (double) blackWins) * 100.0;
            blackWinRate = ((double) blackWins / (double) whiteWins) * 100.0;
        }
        
        whitePlayer.notifyWinRate(whiteWinRate);
        blackPlayer.notifyWinRate(blackWinRate);
        whiteWins = 0;
        blackWins = 0;
        totalGames = 0;
    }
    
    public void setupBoard() {
        pieces = new Piece[8][8];
        setupPieces();
        isWhitesTurn = true;
        onePlayerStuck = false;
        kingCaptured = 0;
        gameOneTurnAgo = null;
        gameTwoTurnsAgo = null;
        gameThreeTurnsAgo = null;
        gameFourTurnsAgo = null;
    }
    
    public void setupView() {
        this.bv = new BoardView(this);
        bv.drawBoard(generateIntBoard());
    }

    public Piece[][] getPieces() {
        return pieces;
    }
    
    public int getCurrentTurnColor() {
        if (isWhitesTurn) {
            return Piece.WHITE;
        } else {
            return Piece.BLACK;
        }
    }
    
    private void setupPieces() {
        clearBoard();
        setupPawns();
        setupKings();
    }
    
    public void clearBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                pieces[i][j] = null;
            }
        }
    }
    
    public void setupPawns() {
        for (int i = 0; i < 8; i++) {
            pieces[i][1] = new Piece(this, Piece.PAWN, Piece.BLACK, i, 1);
            pieces[i][6] = new Piece(this, Piece.PAWN, Piece.WHITE, i, 6);
        }
    }
    
    public void setupKings() {
        pieces[4][0] = new Piece(this, Piece.KING, Piece.BLACK, 4, 0);
        pieces[4][7] = new Piece(this, Piece.KING, Piece.WHITE, 4, 7);
    }

    public List<Move> getMoveCandidates(int color) {
        List<Move> moveCandidates = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieces[i][j] != null) {
                    if (pieces[i][j].getColor() == color) {
                        List<Move> moves = pieces[i][j].getMoves();

                        for (Move move : moves) {
                            moveCandidates.add(move);
                        }
                    }
                }
            }
        }
        return moveCandidates;
    }
    
    private void nextPlay() {

        int colorTurn = Piece.WHITE;

        if (!isWhitesTurn) {
            colorTurn = Piece.BLACK;
        }

        // Prevent repeated boards
        if (gameThreeTurnsAgo != null) {
            gameFourTurnsAgo = new int[gameThreeTurnsAgo.length][];
            for(int i = 0; i < gameThreeTurnsAgo.length; i++) {
                gameFourTurnsAgo[i] = gameThreeTurnsAgo[i].clone();
            }
        }
        if (gameTwoTurnsAgo != null) {            
            gameThreeTurnsAgo = new int[gameTwoTurnsAgo.length][];
            for(int i = 0; i < gameTwoTurnsAgo.length; i++) {
                gameThreeTurnsAgo[i] = gameTwoTurnsAgo[i].clone();
            }
        }
        if (gameOneTurnAgo != null) {            
            gameTwoTurnsAgo = new int[gameOneTurnAgo.length][];
            for(int i = 0; i < gameOneTurnAgo.length; i++) {
                gameTwoTurnsAgo[i] = gameOneTurnAgo[i].clone();
            }
        }
        
        gameOneTurnAgo = generateIntBoard();
        
        List<Move> moveCandidates = getMoveCandidates(colorTurn);

        boolean canAnyPawnsMove = false;
        
        for (Move move : moveCandidates) {
            if (Piece.PAWN.equals(pieces[move.getOldX()][move.getOldY()].getType())) {
                canAnyPawnsMove = true;
            }
        }
        
        if (moveCandidates.isEmpty() || !canAnyPawnsMove) {
            if (onePlayerStuck) {
                //System.out.println(calculateScore());
                //setupBoard();
                pawnCount++;
                notifyGameEnd(calculateScore());
            } else {
                onePlayerStuck = true;
                isWhitesTurn = !isWhitesTurn;
                
                if (Arrays.deepEquals(generateIntBoard(), gameFourTurnsAgo)) {
                    notifyGameEnd(0);                    
//                    System.out.println("Four turns ago board");
//                    printArray(gameFourTurnsAgo);
//                    System.out.println("Three turns ago board");
//                    printArray(gameThreeTurnsAgo);
//                    System.out.println("Two turns ago board");
//                    printArray(gameTwoTurnsAgo);
//                    System.out.println("One turn ago board");
//                    printArray(gameOneTurnAgo);
//                    System.out.println("Current board");
//                    printArray(generateIntBoard());
                }
            }
            
        } else {
            onePlayerStuck = false;
            Move chosenMove;
            
            if (isWhitesTurn) {
                chosenMove = whitePlayer.decideMove(moveCandidates);
            } else {
                chosenMove = blackPlayer.decideMove(moveCandidates);
            }            
            makeMove(chosenMove);            
            if (bv != null) {
                bv.drawBoard(generateIntBoard());
            }
            isWhitesTurn = !isWhitesTurn;
            
            if (kingCaptured != 0) {
                kingCaps++;
                notifyGameEnd(kingCaptured);
            }
        }
    }
    
    public void makeMove(Move move) {
        int oldX = move.getOldX();
        int oldY = move.getOldY();
        int newX = move.getNewX();
        int newY = move.getNewY();
        
        pieces[oldX][oldY].move(move);

        Piece tmpPiece = pieces[oldX][oldY];
        pieces[oldX][oldY] = null;

        Piece pieceInNewSquare = pieces[newX][newY];
        
        if (pieceInNewSquare != null) {// If moving onto a piece, capture it
            if (Piece.KING.equals(pieceInNewSquare.getType())) {
                if (pieceInNewSquare.getColor() == Piece.WHITE) {
                    kingCaptured = -1;
                } else {
                    kingCaptured = 1;
                }
            }
            pieces[newX][newY] = null;            
        }

        pieces[newX][newY] = tmpPiece;
    }

    public int[][] generateIntBoard() {
        int intBoard[][] = new int[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieces[j][i] != null) {
                    if (pieces[j][i].getColor() == Piece.BLACK) {
                        String type = pieces[j][i].getType();
                        switch (type) {
                            case Piece.KING:
                                intBoard[j][i] = BLACK_KING;
                                break;
                            case Piece.QUEEN:
                                intBoard[j][i] = BLACK_QUEEN;
                                break;
                            case Piece.ROOK:
                                intBoard[j][i] = BLACK_ROOK;
                                break;
                            case Piece.KNIGHT:
                                intBoard[j][i] = BLACK_KNIGHT;
                                break;
                            case Piece.BISHOP:
                                intBoard[j][i] = BLACK_BISHOP;
                                break;
                            case Piece.PAWN:
                                intBoard[j][i] = BLACK_PAWN;
                                break;                                
                        }
                    } else {
                        String type = pieces[j][i].getType();
                        switch (type) {
                            case Piece.KING:
                                intBoard[j][i] = WHITE_KING;
                                break;
                            case Piece.QUEEN:
                                intBoard[j][i] = WHITE_QUEEN;
                                break;
                            case Piece.ROOK:
                                intBoard[j][i] = WHITE_ROOK;
                                break;
                            case Piece.KNIGHT:
                                intBoard[j][i] = WHITE_KNIGHT;
                                break;
                            case Piece.BISHOP:
                                intBoard[j][i] = WHITE_BISHOP;
                                break;
                            case Piece.PAWN:
                                intBoard[j][i] = WHITE_PAWN;
                                break;
                        }
                    }
                } else {
                    intBoard[j][i] = NONE;
                }
            }
        }
        return intBoard;
    }

    public void notifyKeyPress() {
        nextPlay();
    }
    
    public int calculateScore() {
        int score = 0;
        
        for (int i = 0; i < 8; i++) {
            if (generateIntBoard()[i][0] == WHITE_PAWN) {
                score++;
            } if (generateIntBoard()[i][7] == BLACK_PAWN) {
                score--;
            }
        }
        return score;
    }

    private int whiteWins = 0;
    private int blackWins = 0;
    private int totalGames = 0;
    
    private void notifyGameEnd(int score) {
        totalGames++;
        if (score > 0) {
            whiteWins++;
        } else if (score < 0) {
            blackWins++;
        }        
        setupBoard();
    }
    
    private void runGames(int count) {
        while (totalGames < count) {
            nextPlay();
        }
        
        System.out.println("White wins " + whiteWins + " black wins " + blackWins + " black won " + ((float)blackWins / (float)whiteWins) * 100.0 + "% of what white won.  King caps " + ": " + kingCaps + ", pawn counts: " + pawnCount);
        kingCaps = 0;
        pawnCount = 0;
        setupBoard();
    }
    
    private void printArray(int pieces[][]) {
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                if (pieces[j][i] >= 0) {// Board is flipped if print i,j so print j,i
                    System.out.print("" + pieces[j][i] + " ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println("");
        }
        System.out.println("");
    }
}
