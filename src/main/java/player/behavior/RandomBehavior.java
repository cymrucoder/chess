package player.behavior;

import blarg.chess.Move;
import java.util.List;
import java.util.Random;

/**
 *
 * @author cymrucoder
 */
public class RandomBehavior extends PlayerBehavior {

    @Override
    public Move decideMove(List<Move> candidateMoves) {
        Random r = new Random();
        return candidateMoves.get(r.nextInt(candidateMoves.size()));        
    }    
}
