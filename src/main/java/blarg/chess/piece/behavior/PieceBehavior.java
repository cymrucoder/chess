package blarg.chess.piece.behavior;

import blarg.chess.Board;
import blarg.chess.Move;
import blarg.chess.Piece;
import java.util.List;

/**
 *
 * @author cymrucoder
 */
public abstract class PieceBehavior {
    protected Piece piece;
    
    public PieceBehavior(Piece piece) {
        this.piece = piece;
    }
    
    public abstract List<Move> getMoves(Board board);
    
    public static PieceBehavior getBehaviorForName(Piece piece, String name) {
        switch (name) {
            case Piece.KING:
                return new KingBehavior(piece);
            case Piece.PAWN:
                return new PawnBehavior(piece);
            default:
                System.exit(1);
                return new PawnBehavior(piece);
        }                
    }
    
    protected boolean isInBounds(int x, int y) {
        return (x < 8 && x >= 0 && y < 8 && y >= 0);
    }
}
