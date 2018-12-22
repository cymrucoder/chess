package blarg.chess.piece.behavior;

import blarg.chess.Move;
import java.util.List;

/**
 *
 * @author cymrucoder
 */
public abstract class PieceBehavior {
    public abstract List<Move> getMoves();
    
    public static PieceBehavior getBehaviorForName(String name) {
        switch (name) {
            case "pawn":
                return new PawnBehavior();
            default:
                return new PawnBehavior();
        }                
    }
}
