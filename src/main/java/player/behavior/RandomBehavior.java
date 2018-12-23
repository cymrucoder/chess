package player.behavior;

import blarg.chess.Board;
import blarg.chess.Move;
import java.util.List;
import java.util.Random;

/**
 *
 * @author cymrucoder
 */
public class RandomBehavior extends PlayerBehavior {

    public RandomBehavior(Board board) {
        super();
        this.board = board;
    }
    
    @Override
    public Move decideMove(List<Move> candidateMoves) {
        Random r = new Random();
        return candidateMoves.get(r.nextInt(candidateMoves.size()));        
    }    
}
