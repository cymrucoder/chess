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
                
                CandidateMoveCheck:
                if (!(candidateX == x && candidateY == y)) {// Can't move onto square already on
                    if (isInBounds(candidateX, candidateY)) {
                        Piece candidateSquarePiece = board.getPieces()[candidateX][candidateY];
                        if (candidateSquarePiece == null || piece.getColor() != candidateSquarePiece.getColor()) {// We can capture any opposing piece
                            // TODO What if you're trying to move onto a king?  Has to be allowed, ignore the will be in check move because you'll have won

                            //Board resultingBoard = board;                            
                            Move move = new Move(x, y, candidateX, candidateY);                            
//                            board.makeMove(move);
//                            List<Move> moveCandidates = board.getMoveCandidates(piece.getColor() == Piece.WHITE ? Piece.BLACK : Piece.WHITE);
//                            
//                            for (Move moveCandidate: moveCandidates) {
//                                if (moveCandidate.getNewX() == candidateX && moveCandidate.getNewY() == candidateY) {
//                                    Move reverseMove = new Move(candidateX, candidateY, x, y);                            
//                                    board.makeMove(reverseMove);
//                                    break CandidateMoveCheck;
//                                }
//                            }
//                            Move reverseMove = new Move(candidateX, candidateY, x, y);
//                            board.makeMove(reverseMove);
                            moves.add(move);
                        }
                    }
                }
            }
        }     
        return moves;
    }
}
