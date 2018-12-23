package blarg.chess;

import blarg.chess.view.BoardView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author cymrucoder
 */
public class Board {

    public static final int NONE = -1;
    public static final int BLACK_PAWN = 0;
    public static final int BLACK_BISHOP = 1;
    public static final int BLACK_KNIGHT = 2;
    public static final int BLACK_ROOK = 3;
    public static final int BLACK_QUEEN = 4;
    public static final int BLACK_KING = 5;
    public static final int WHITE_PAWN = 6;
    public static final int WHITE_BISHOP = 7;
    public static final int WHITE_KNIGHT = 8;
    public static final int WHITE_ROOK = 9;
    public static final int WHITE_QUEEN = 10;
    public static final int WHITE_KING = 11;

    private Piece pieces[][];

    public static void main(String args[]) {
        Board board = new Board();
    }

    BoardView bv;

    public Board() {

        pieces = new Piece[8][8];
        setupPieces();


        this.bv = new BoardView(this);
        bv.drawBoard(generateIntBoard());

        //nextPlay();
        //bv.drawBoard(generateIntBoard());
    }

    private void setupPieces() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                pieces[i][j] = null;
            }
        }

        for (int i = 0; i < 8; i++) {
            pieces[i][1] = new Piece(this, Piece.PAWN, Piece.BLACK, i, 1);
           // pieces[i][1].move(i, 1);
            pieces[i][6] = new Piece(this, Piece.PAWN, Piece.WHITE, i, 6);
            //pieces[i][6].move(i, 6);
        }

//        pieces[0][0] = ROOK;
//        pieces[7][0] = ROOK;
//
//        pieces[0][7] = ROOK * 20;
//        pieces[7][7] = ROOK * 20;
//
//        pieces[1][0] = KNIGHT;
//        pieces[6][0] = KNIGHT;
//
//        pieces[1][7] = KNIGHT * 20;
//        pieces[6][7] = KNIGHT * 20;
//
//        pieces[2][0] = BISHOP;
//        pieces[5][0] = BISHOP;
//
//        pieces[2][7] = BISHOP * 20;
//        pieces[5][7] = BISHOP * 20;
//
//        pieces[4][0] = QUEEN;
//        pieces[3][0] = KING;
//
//        pieces[3][7] = QUEEN * 20;
//        pieces[4][7] = KING * 20;
    }

//    private class MoveCandidate {
//        //int x;
//        //int y;
//        Move move;
//
//        public MoveCandidate(int x, int y, Move move) {
//            //this.piece = piece;
//            this.x = x;
//            this.y = y;
//            this.move = move;
//        }
//    }

    private void nextPlay() {

        List<String> movablePieces = new ArrayList<>();
        Map<String, List<Move>> moveCandidates = new HashMap<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieces[i][j] != null) {
                    if (pieces[i][j].getColor() == Piece.WHITE) {
                        List<Move> moves = pieces[i][j].getMoves();
                        
                        if (moves.size() > 0) {// Pieces that can't moved can just be ignored
                            movablePieces.add("" + i + "," + j);
                            moveCandidates.put("" + i + "," + j, new ArrayList<>());
                            for (Move move : moves) {
                                moveCandidates.get("" + i + "," + j).add(move);
                            }                            
                        }
                    }
                }
            }
        }

        //List<Move> moves = pieces[0][6].getMoves();
        //for (Move move : moves) {
        //    System.out.println("" + move.getX() + " " + move.getY());
       // }

        //if (moves.size() == 1) {
            //System.out.println("Moving to only move");

        if (movablePieces.isEmpty()) {
            System.out.println("Stalemate");
        } else {
            Random r = new Random();

            String chosenPiece = movablePieces.get(r.nextInt(movablePieces.size()));
            int chosenMove = r.nextInt(moveCandidates.get(chosenPiece).size());

            pieces[Integer.parseInt(chosenPiece.split(",")[0])][Integer.parseInt(chosenPiece.split(",")[1])].move(chosenMove);


            Piece tmpPiece = pieces[Integer.parseInt(chosenPiece.split(",")[0])][Integer.parseInt(chosenPiece.split(",")[1])];
            pieces[Integer.parseInt(chosenPiece.split(",")[0])][Integer.parseInt(chosenPiece.split(",")[1])] = null;
            pieces[moveCandidates.get(chosenPiece).get(chosenMove).getX()][moveCandidates.get(chosenPiece).get(chosenMove).getY()] = tmpPiece;
            //}

            bv.drawBoard(generateIntBoard());
        }
    }

    public int[][] generateIntBoard() {
        int intBoard[][] = new int[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieces[i][j] != null) {
                    if (pieces[i][j].getColor() == Piece.BLACK) {
                        intBoard[i][j] = BLACK_PAWN;
                    } else {
                        intBoard[i][j] = WHITE_PAWN;
                    }
                } else {
                    intBoard[i][j] = NONE;
                }
            }
        }

        return intBoard;
    }

    public void notifyKeyPress() {
        nextPlay();
    }
}
