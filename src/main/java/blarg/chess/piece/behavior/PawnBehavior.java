package blarg.chess.piece.behavior;

import blarg.chess.Board;
import blarg.chess.Move;
import blarg.chess.Piece;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cymrucoder
 */
public class PawnBehavior extends PieceBehavior {

    public PawnBehavior(Piece piece) {
        super(piece);
    }

    @Override
    public List<Move> getMoves(Board board) {
        List<Move> moves = new ArrayList<>();

        int[][] intBoard = board.generateIntBoard();
        int x = piece.getX();
        int y = piece.getY();

        int moveOffset = 1;// Black pawns start on row 1, white on row 6 (which is backwards compared to most chess but whatever) so we just need to know which direction to move in

        if (piece.getColor() == Piece.WHITE) {
            moveOffset = -1;
        }

        if (intBoard[x][y + moveOffset] == Board.NONE) {// Nothing one in front
            moves.add(new Move(x, y + moveOffset));
        }

        if ((piece.getColor() == Piece.BLACK && y == 1) || (piece.getColor() == Piece.WHITE && y == 6)) {// Can move two if at the start
            if (intBoard[x][y + (moveOffset * 2)] == Board.NONE) {
                moves.add(new Move(x, y + (moveOffset * 2)));
            }
        }
        return moves;
    }
}
