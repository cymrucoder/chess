package blarg.chess.piece.behavior;

import blarg.chess.Move;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cymrucoder
 */
public class PawnBehavior extends PieceBehavior {

    @Override
    public List<Move> getMoves() {
        List<Move> moves = new ArrayList<>();
        moves.add(new Move(0, 5));        
        return moves;
    }

}
