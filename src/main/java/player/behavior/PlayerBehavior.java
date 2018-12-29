package player.behavior;

import blarg.chess.Board;
import blarg.chess.Move;
import java.util.List;

/**
 *
 * @author cymrucoder
 */
public abstract class PlayerBehavior {
    protected Board board;    
    
    public static PlayerBehavior getBehaviorForName(Board board, String behaviorName) {
        switch (behaviorName) {
            case "random":
                return new RandomBehavior(board);
            case "network": 
                return new NetworkBehavior(board);
            case "newnetwork": 
                return new NewNetworkBehavior(board);
            case "alwayscap": 
                return new AlwaysCaptureBehavior(board);
            default:
                System.exit(1);
                return null;
        }
    }
    
    public abstract Move decideMove(List<Move> candidateMoves);
    
    public void notifyWinRate(double winRate) {
        
    }
}
