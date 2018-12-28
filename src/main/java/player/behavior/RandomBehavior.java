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

    Random rand;
    
    public RandomBehavior(Board board) {
        super();
        this.board = board;
        rand = new Random();
    }
    
    @Override
    public Move decideMove(List<Move> candidateMoves) {
        return candidateMoves.get(rand.nextInt(candidateMoves.size()));        
    }    
}
