package player.behavior;

import blarg.chess.Move;
import java.util.List;

/**
 *
 * @author cymrucoder
 */
public abstract class PlayerBehavior {

    public static PlayerBehavior getBehaviorForName(String behaviorName) {
        switch (behaviorName) {
            case "random":
                return new RandomBehavior();
            case "network": 
                return new NetworkBehavior();
            default:
                System.exit(1);
                return null;
        }
    }
    
    public abstract Move decideMove(List<Move> candidateMoves);
}
