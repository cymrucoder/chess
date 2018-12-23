package blarg.chess;

import blarg.chess.piece.behavior.PieceBehavior;
import java.util.List;

/**
 *
 * @author cymrucoder
 */
public class Piece {

    private static final int BLACK_PAWN = 0;
    private static final int BLACK_BISHOP = 1;
    private static final int BLACK_KNIGHT = 2;
    private static final int BLACK_ROOK = 3;
    private static final int BLACK_QUEEN = 4;
    private static final int BLACK_KING = 5;
    private static final int WHITE_PAWN = 6;
    private static final int WHITE_BISHOP = 7;
    private static final int WHITE_KNIGHT = 8;
    private static final int WHITE_ROOK = 9;
    private static final int WHITE_QUEEN = 10;
    private static final int WHITE_KING = 11;
    
    public static String PAWN = "pawn";
    
    public static int BLACK = 0;
    public static int WHITE = 1;
    
    private PieceBehavior behavior;
    private int color;
    
    private int x;
    private int y;
    
    public Piece(String name, int color, int x, int y) {
        behavior = PieceBehavior.getBehaviorForName(name);
        this.color = color;
        this.x = x;
        this.y = y;
    }
        
    public List<Move> getMoves() {
        return behavior.getMoves();
    }
    
    public void move(int moveIndex) {
        Move move = getMoves().get(moveIndex);
        x = move.getX();
        y = move.getY();
    }
    
    public int getColor() {
        return color;
    }
    
//    public int getIntValue() {
//        if ()
//    }
    // Eh maybe
}
