package blarg.chess;

import java.util.List;
import player.behavior.PlayerBehavior;

/**
 *
 * @author cymrucoder
 */
public class Player {
    PlayerBehavior behavior;
    private Board board;
    
    
    public Player(Board board, String behaviorName) {
        this.board = board;
        behavior = PlayerBehavior.getBehaviorForName(board, behaviorName);
    }
    
    public Move decideMove(List<Move> candidateMoves) {
        return behavior.decideMove(candidateMoves);
    }
    
    public void notifyWinRate(double winRate) {
        behavior.notifyWinRate(winRate);
    }
}
