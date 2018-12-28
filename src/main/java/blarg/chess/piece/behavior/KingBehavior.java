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
public class KingBehavior extends PieceBehavior  {

    public KingBehavior(Piece piece) {
        super(piece);
    }

    @Override
    public List<Move> getMoves(Board board) {
        List<Move> moves = new ArrayList<>();

        int[][] intBoard = board.generateIntBoard();
        int x = piece.getX();
        int y = piece.getY();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int candidateX = x + i;
                int candidateY = y + j;
                
                if (!(candidateX == x && candidateY == y)) {// Can't move onto square already on
                    if (candidateX < intBoard.length && candidateX >= 0) {// Don't move off sides of board
                        if (candidateY < intBoard[x].length && candidateY >= 0) {//Don't move off ends of board
                            Piece candidateSquarePiece = board.getPieces()[candidateX][candidateY];
                            if (candidateSquarePiece == null || piece.getColor() != candidateSquarePiece.getColor()) {// We can capture any opposing piece
                                // TODO eventually kings shouldn't be able to move into check, make a copy of board move the piece then call getMoves or whatever
                                moves.add(new Move(x, y, candidateX, candidateY));
                            }
                        }
                    }
                }
            }
        }     
        return moves;
    }
}
