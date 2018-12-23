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
            case "pawn":
                return new PawnBehavior(piece);
            default:
                return new PawnBehavior(piece);
        }                
    }
}
