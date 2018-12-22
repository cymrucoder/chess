package blarg.chess;

import blarg.chess.view.BoardView;

/**
 *
 * @author cymrucoder
 */
public class Board {

    private static final int NONE = 0;
    private static final int PAWN = 1;
    private static final int BISHOP = 2;
    private static final int KNIGHT = 3;
    private static final int ROOK = 4;
    private static final int QUEEN = 5;
    private static final int KING = 6;
    
    private int pieces[][];
    
    public static void main(String args[]) {
        Board board = new Board();
    }
    
    BoardView bv;

    public Board() {
        
        pieces = new int[8][8];
        setupPieces();
        
        
        this.bv = new BoardView();
        bv.drawBoard(pieces);

    }
    
    private void setupPieces() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                pieces[i][j] = NONE;
            }
        }
        
        for (int i = 0; i < 8; i++) {
            pieces[i][1] = PAWN;
            pieces[i][6] = PAWN;
        }
    }
}
