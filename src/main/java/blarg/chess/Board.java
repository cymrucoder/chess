package blarg.chess;

import blarg.chess.view.BoardView;
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
    
    public static void main(String args[]) {
        Board board = new Board();
    }
    
    BoardView bv;

    public Board() {
        
        pieces = new Piece[8][8];
        setupPieces();
        
        
        this.bv = new BoardView();
        bv.drawBoard(generateIntBoard());

        nextPlay();
        bv.drawBoard(generateIntBoard());
    }
    
    private void setupPieces() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                pieces[i][j] = null;
            }
        }
        
        for (int i = 0; i < 8; i++) {
            pieces[i][1] = new Piece(Piece.PAWN, Piece.BLACK, i, 1);
           // pieces[i][1].move(i, 1);
            pieces[i][6] = new Piece(Piece.PAWN, Piece.WHITE, i, 6);
            //pieces[i][6].move(i, 6);
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
        List<Move> moves = pieces[0][6].getMoves();
        for (Move move : moves) {
            System.out.println("" + move.getX() + " " + move.getY());
        }
        
        if (moves.size() == 1) {
            System.out.println("Moving to only move");
            pieces[0][6].move(0);
            Piece tmpPiece = pieces[0][6];
            pieces[0][6] = null;
            pieces[moves.get(0).getX()][moves.get(0).getY()] = tmpPiece;
        }
    }
    
    private int[][] generateIntBoard() {
        int intBoard[][] = new int[8][8];
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieces[i][j] != null) {
                    if (pieces[i][j].getColor() == Piece.BLACK) {
                        intBoard[i][j] = BLACK_PAWN;
                    } else {
                        intBoard[i][j] = WHITE_PAWN;
                    }
                } else {
                    intBoard[i][j] = NONE;
                }
            }
        }
        
        return intBoard;
    }
}
