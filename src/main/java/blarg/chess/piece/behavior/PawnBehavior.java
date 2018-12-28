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

        if (y + moveOffset < intBoard[x].length && y + moveOffset >= 0) {// Check if we're at the far ends of the board to prevent null pointer exceptions.  I think this can't actually happen in chess because promotions but we'll keep it anyway
            if (intBoard[x][y + moveOffset] == Board.NONE) {// Can always move one forward if there's an empty square
                moves.add(new Move(x, y, x, y + moveOffset));
            }
            
            // Don't look for captures left/right if we're on the left/right side of the board
            if (x > 0) {
                if (intBoard[x - 1][y + moveOffset] != Board.NONE && piece.getColor() != board.getPieces()[x - 1][y + moveOffset].getColor()) {// We can capture any opposing piece other than a king
                    moves.add(new Move(x, y, x - 1, y + moveOffset));
                }
            }
            if (x < intBoard[x].length - 1) {
                if (intBoard[x + 1][y + moveOffset] != Board.NONE && piece.getColor() != board.getPieces()[x + 1][y + moveOffset].getColor()) {
                    moves.add(new Move(x, y, x + 1, y + moveOffset));
                }
            }
        }

        if ((piece.getColor() == Piece.BLACK && y == 1) || (piece.getColor() == Piece.WHITE && y == 6)) {// Can move two if at the start
            if (intBoard[x][y + moveOffset] == Board.NONE && intBoard[x][y + (moveOffset * 2)] == Board.NONE) {// Path must be clear
                moves.add(new Move(x, y, x, y + (moveOffset * 2)));
            }
        }        
        return moves;
    }
}
