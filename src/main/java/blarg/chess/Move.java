package blarg.chess;

/**
 *
 * @author cymrucoder
 */
public class Move {
    private int oldX;
    private int oldY;

    private int newX;
    private int newY;

    public Move(int oldX, int oldY, int newX, int newY) {
        this.oldX = oldX;
        this.oldY = oldY;
        this.newX = newX;
        this.newY = newY;
    }

    public int getNewX() {
        return newX;
    }

    public int getNewY() {
        return newY;
    }

    public int getOldX() {
        return oldX;
    }

    public int getOldY() {
        return oldY;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (!Move.class.isAssignableFrom(o.getClass())) {
            return false;
        }

        Move move = (Move) o;
        return (this.oldX == move.oldX && this.oldY == move.oldY && this.newX == move.newX && this.newY == move.newY);
    }
}
