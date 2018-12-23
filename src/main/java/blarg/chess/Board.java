package blarg.chess;

import blarg.chess.view.BoardView;
import java.util.ArrayList;
import java.util.List;

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

    public static void main(String args[]) {
        Board board = new Board();
        //board.setupView();
        
        board.runGames(100000);
    }

    private BoardView bv;
    private boolean isWhitesTurn;
    private boolean onePlayerStuck;

    public Board() {

        whitePlayer = new Player(this, "random");
        blackPlayer = new Player(this, "random");
        setupBoard();
    }
    
    public void setupBoard() {
        pieces = new Piece[8][8];
        setupPieces();
        isWhitesTurn = true;
        onePlayerStuck = false;
    }
    
    public void setupView() {
        this.bv = new BoardView(this);
        bv.drawBoard(generateIntBoard());
    }

    public Piece[][] getPieces() {
        return pieces;
    }
    
    private void setupPieces() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                pieces[i][j] = null;
            }
        }

        for (int i = 0; i < 8; i++) {
            pieces[i][1] = new Piece(this, Piece.PAWN, Piece.BLACK, i, 1);
            pieces[i][6] = new Piece(this, Piece.PAWN, Piece.WHITE, i, 6);
        }

//        pieces[0][0] = ROOK;
//        pieces[7][0] = ROOK;
//
//        pieces[0][7] = ROOK * 20;
//        pieces[7][7] = ROOK * 20;
//
//        pieces[1][0] = KNIGHT;
//        pieces[6][0] = KNIGHT;
//
//        pieces[1][7] = KNIGHT * 20;
//        pieces[6][7] = KNIGHT * 20;
//
//        pieces[2][0] = BISHOP;
//        pieces[5][0] = BISHOP;
//
//        pieces[2][7] = BISHOP * 20;
//        pieces[5][7] = BISHOP * 20;
//
//        pieces[4][0] = QUEEN;
//        pieces[3][0] = KING;
//
//        pieces[3][7] = QUEEN * 20;
//        pieces[4][7] = KING * 20;
    }

    private void nextPlay() {

        int colorTurn = Piece.WHITE;

        if (!isWhitesTurn) {
            colorTurn = Piece.BLACK;
        }

        List<Move> moveCandidates = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieces[i][j] != null) {
                    if (pieces[i][j].getColor() == colorTurn) {
                        List<Move> moves = pieces[i][j].getMoves();

                        for (Move move : moves) {
                            moveCandidates.add(move);
                        }
                    }
                }
            }
        }

        if (moveCandidates.isEmpty()) {
            //System.out.println("");
            if (onePlayerStuck) {
                //System.out.println(calculateScore());
                //setupBoard();
                notifyGameEnd(calculateScore());
            } else {
                onePlayerStuck = true;
                isWhitesTurn = !isWhitesTurn;
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

        if (pieces[newX][newY] != null) {// If moving onto a piece, capture it
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
                        intBoard[j][i] = BLACK_PAWN;
                    } else {
                        intBoard[j][i] = WHITE_PAWN;
                    }
                } else {
                    intBoard[j][i] = NONE;
                }
            }
        }

        return intBoard;
    }

    public void notifyKeyPress() {
        //nextPlay();
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

    public void runPhessGames() {
        
    }

    private int whiteWins = 0;
    private int blackWins = 0;
    private int totalGames = 0;
    
    private void notifyGameEnd(int score) {
       // System.out.println(score);
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
        
        System.out.println("White wins " + whiteWins + " black wins " + blackWins + " black won " + ((float)blackWins / (float)whiteWins) * 100 + "% of what white won");
    }
}
