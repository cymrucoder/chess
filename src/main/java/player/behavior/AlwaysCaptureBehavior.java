package player.behavior;

import blarg.chess.Board;
import blarg.chess.Move;
import java.util.List;

/**
 *
 * @author cymrucoder
 */
public class AlwaysCaptureBehavior extends PlayerBehavior {

    public AlwaysCaptureBehavior(Board board) {
        super();
        this.board = board;
    }
    
    @Override
    public Move decideMove(List<Move> candidateMoves) {
        for (Move move : candidateMoves) {
            if (move.getOldX() != move.getNewX()) {
                return move;
            }
        }
        return candidateMoves.get(0);
    }

}
