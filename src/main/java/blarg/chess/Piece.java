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
    
    public static final String PAWN = "pawn";
    public static final String BISHOP = "bishop";
    public static final String KNIGHT = "knight";
    public static final String ROOK = "rook";
    public static final String QUEEN = "queen";
        public static final String KING = "king";
    
    public static int BLACK = 0;
    public static int WHITE = 1;
    
    private String type;
    private PieceBehavior behavior;
    private int color;
    
    private int x;
    private int y;
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    private Board board;
    
    public Piece(Board board, String name, int color, int x, int y) {
        this.board = board;
        this.type = name;
        behavior = PieceBehavior.getBehaviorForName(this, name);
        this.color = color;
        this.x = x;
        this.y = y;
    }
        
    public List<Move> getMoves() {
        return behavior.getMoves(board);
    }
    
    public void move(Move move) {
        x = move.getNewX();
        y = move.getNewY();
    }
    
    public int getColor() {
        return color;
    }
    
//    public int getIntValue() {
//        if ()
//    }
    // Eh maybe

    public String getType() {
        return type;
    }
}
