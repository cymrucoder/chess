package blarg.chess.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author cymrucoder
 */
public class BoardView extends JFrame {
    
    private static final int BOARD_WIDTH = 800;
    private static final int BOARD_HEIGHT = 800;
    
    public BoardView() {
        DrawCanvas dc = new DrawCanvas();
        dc.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setContentPane(dc);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setTitle("Probably chess");
        setVisible(true);
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
                        g.setColor(Color.BLACK);
                    }
                    g.fillRect((BOARD_WIDTH / 8) * j, (BOARD_HEIGHT / 8) * i, (BOARD_WIDTH / 8), (BOARD_HEIGHT / 8));
                     isWhite = !isWhite;
                }
                startSquareIsWhite = !startSquareIsWhite;
            }
        }
    }
}
