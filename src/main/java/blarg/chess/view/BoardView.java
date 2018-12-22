package blarg.chess.view;

import blarg.chess.Board;
import blarg.chess.Piece;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author cymrucoder
 */
public class BoardView extends JFrame {
    
    private static final int BOARD_WIDTH = 800;
    private static final int BOARD_HEIGHT = 800;
    
    private int board[][];
    
    public BoardView() {
        DrawCanvas dc = new DrawCanvas();
        dc.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setContentPane(dc);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setTitle("Probably chess");
        setVisible(true);
    }
    
    public void drawBoard(int board[][]) {
        this.board = board;
        repaint();
    }
    
    private class DrawCanvas extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            boolean startSquareIsWhite = true;
            
            for (int i = 0; i < 8; i++) {                
                boolean isWhite = startSquareIsWhite;
                for (int j = 0; j < 8; j++) {
                    if (isWhite) {
                        g.setColor(Color.WHITE);
                    } else {
                        g.setColor(Color.GRAY);
                    }
                    g.fillRect((BOARD_WIDTH / 8) * j, (BOARD_HEIGHT / 8) * i, (BOARD_WIDTH / 8), (BOARD_HEIGHT / 8));
                     isWhite = !isWhite;
                }
                startSquareIsWhite = !startSquareIsWhite;
            }
            
            Image image = null;
            
            try {
                image = ImageIO.read(new File("images\\mydonechessbits.png"));
            } catch (IOException ex) {
                Logger.getLogger(BoardView.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(1);
            }
            
            if (board != null) {
                if (board.length != 0) {
                    if (board[0].length != 0) {
                        for (int i = 0; i < board.length; i++) {
                            for (int j = 0; j < board[i].length; j++) {
                                if (board[i][j] != Board.NONE) {
                                    int piece = board[i][j];
                                    int color = Piece.BLACK;
                                    if (piece > Board.BLACK_KING) {
                                        color = Piece.WHITE;
                                    }
                                    
                                    piece = piece % Board.WHITE_PAWN;
                                    
                                    //int adjustedPiece = piece - 1;// 0 is NONE/no piece but the image starts with pawns so just need to take one off to line them up
                                    
                                    g.drawImage(image, 100 * i, 100 * j, (100 * i) + 100, (100 * j) + 100, (piece * 200), (color * 200), (piece * 200) + 200, (color * 200) + 200, rootPane);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
