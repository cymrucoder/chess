package blarg.chess.piece.behavior;

import blarg.chess.Board;
import blarg.chess.Move;
import blarg.chess.Piece;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author cymrucoder
 */
public class KingBehaviorTest {

    KingBehavior kb;

    public KingBehaviorTest() {
        Board board = new Board();
        Piece piece = new Piece(board, Piece.KING, Piece.WHITE, 4, 7);
        kb = new KingBehavior(piece);
    }

    @Test
    public void testGetMove_whenAt47_shouldReturn5MovesToAdjacentSquares() {
        Board board = new Board();
        board.clearBoard();
        board.setupKings();
        List<Move> actualMoves = kb.getMoves(board);

        List<Move> expectedMoves = new ArrayList<>();
        expectedMoves.add(new Move(4, 7, 4, 6));
        expectedMoves.add(new Move(4, 7, 5, 7));
        expectedMoves.add(new Move(4, 7, 3, 7));
        expectedMoves.add(new Move(4, 7, 3, 6));
        expectedMoves.add(new Move(4, 7, 5, 6));        

        assertTrue(actualMoves.containsAll(expectedMoves));
        assertEquals(expectedMoves.size(), actualMoves.size());
    }
    
    @Test
    public void testGetMove_whenBlackAt40_shouldReturn5MovesToAdjacentSquares() {
        Board board = new Board();
        board.clearBoard();
        board.setupKings();
        Piece blackPiece = new Piece(board, Piece.KING, Piece.BLACK, 4, 0);
        KingBehavior blackKb = new KingBehavior(blackPiece);
        List<Move> actualMoves = blackKb.getMoves(board);

        List<Move> expectedMoves = new ArrayList<>();
        expectedMoves.add(new Move(4, 0, 4, 1));
        expectedMoves.add(new Move(4, 0, 5, 0));
        expectedMoves.add(new Move(4, 0, 3, 0));
        expectedMoves.add(new Move(4, 0, 3, 1));
        expectedMoves.add(new Move(4, 0, 5, 1));        

        assertTrue(actualMoves.containsAll(expectedMoves));
        assertEquals(expectedMoves.size(), actualMoves.size());
    }
    
    @Test
    public void testGetMove_whenAt47NextToFriendlyPawns_shouldReturn2MovesToAdjacentEmptySquares() {
        Board board = new Board();
        board.clearBoard();
        board.setupKings();
        board.setupPawns();
        
        List<Move> actualMoves = kb.getMoves(board);

        List<Move> expectedMoves = new ArrayList<>();
        expectedMoves.add(new Move(4, 7, 5, 7));
        expectedMoves.add(new Move(4, 7, 3, 7));

        assertTrue(actualMoves.containsAll(expectedMoves));
        assertEquals(expectedMoves.size(), actualMoves.size());
    }
}