package blarg.chess.piece.behavior;

import blarg.chess.Board;
import blarg.chess.Move;
import blarg.chess.Piece;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author cymrucoder
 */
public class PawnBehaviorTest {

    PawnBehavior pb;

    public PawnBehaviorTest() {
        Board board = new Board();
        Piece piece = new Piece(board, Piece.PAWN, Piece.WHITE, 0, 6);
        pb = new PawnBehavior(piece);
    }

    @Test
    public void testGetMoves_whenAt06_shouldContainSingleStepForward() {
        Board board = new Board();
        List<Move> actualMoves = pb.getMoves(board);

        Move expectedMove = new Move(0, 6, 0, 5);
        assertTrue(actualMoves.contains(expectedMove));
    }

    @Test
    public void testGetMoves_whenAt06_shouldContainDoubleStepForward() {
        Board board = new Board();
        List<Move> actualMoves = pb.getMoves(board);

        Move expectedMove = new Move(0, 6, 0, 4);
        assertTrue(actualMoves.contains(expectedMove));
    }

    @Test
    public void testGetMoves_whenAt06TwoAheadBlocked_shouldOnlyContainSingleStepForward() {
        Board board = new Board();
        board.makeMove(new Move(0, 1, 0, 4));

        List<Move> actualMoves = pb.getMoves(board);
        Move expectedMove = new Move(0, 6, 0, 5);
        Move expectedToNotBeIncludedMove = new Move(0, 6, 0, 4);
        assertTrue(actualMoves.contains(expectedMove));
        assertFalse(actualMoves.contains(expectedToNotBeIncludedMove));
        assertEquals(1, actualMoves.size());
    }

    @Test
    public void testGetMoves_whenAt06OneAheadBlocked_shouldContainNoMoves() {
        Board board = new Board();
        board.makeMove(new Move(0, 1, 0, 5));

        List<Move> actualMoves = pb.getMoves(board);
        assertEquals(0, actualMoves.size());
    }

    @Test
    public void testGetMoves_whenAt35_shouldOnlyContainSingleStepForward() {
        Board board = new Board();
        PawnBehavior pb2;
        Piece piece = new Piece(board, Piece.PAWN, Piece.WHITE, 3, 5);
        pb2 = new PawnBehavior(piece);
        board.makeMove(new Move(0, 6, 3, 5));

        List<Move> actualMoves = pb2.getMoves(board);
        Move expectedMove = new Move(3, 5, 3, 4);
        assertTrue(actualMoves.contains(expectedMove));
        assertEquals(1, actualMoves.size());
    }
    
    @Test
    public void testGetMoves_whenAt05CanCapture_shouldContainCapture() {
        Board board = new Board();
        board.makeMove(new Move(1, 1, 1, 4));
        PawnBehavior pb2;
        Piece piece = new Piece(board, Piece.PAWN, Piece.WHITE, 0, 5);
        pb2 = new PawnBehavior(piece);
        board.makeMove(new Move(0, 6, 0, 5));

        List<Move> actualMoves = pb2.getMoves(board);
        Move expectedMove = new Move(0, 5, 1, 4);
        assertTrue(actualMoves.contains(expectedMove));
        assertEquals(2, actualMoves.size());
    }
    
    @Test
    public void testGetMoves_whenAt15CanCaptureTwo_shouldContainBothCaptures() {
        Board board = new Board();
        board.makeMove(new Move(2, 1, 2, 4));
        board.makeMove(new Move(0, 1, 0, 4));
        PawnBehavior pb2;
        Piece piece = new Piece(board, Piece.PAWN, Piece.WHITE, 1, 5);
        pb2 = new PawnBehavior(piece);
        board.makeMove(new Move(0, 6, 1, 5));

        List<Move> actualMoves = pb2.getMoves(board);
        Move expectedMoveLeft = new Move(1, 5, 0, 4);
        Move expectedMoveRight = new Move(1, 5, 2, 4);
        assertTrue(actualMoves.contains(expectedMoveLeft));
        assertTrue(actualMoves.contains(expectedMoveRight));
        assertEquals(3, actualMoves.size());
    }
}