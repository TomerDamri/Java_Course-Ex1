package course.java.sdm.engine.schema;

public class Location {
    private final Integer MIN_VALUE = 0;
    private final Integer MAX_VALUE = 50;
    private int y;
    private int x;

    public Location (int x, int y) {
        if (!isValidLocation(x, y)) {
            throw new IndexOutOfBoundsException(new StringBuilder("The Location (").append(x)
                                                                                   .append(",")
                                                                                   .append(y)
                                                                                   .append(") is not valid because(")
                                                                                   .append(MIN_VALUE)
                                                                                   .append("<= X,Y <=")
                                                                                   .append(MAX_VALUE)
                                                                                   .append(")")
                                                                                   .toString());
        }
        this.x = x;
        this.y = y;
    }

    public int getY () {
        return y;
    }

    public int getX () {
        return x;
    }

    private boolean isValidLocation (int x, int y) {
        return isaValidValue(x) && isaValidValue(y);
    }

    private boolean isaValidValue (int z) {
        return z >= MIN_VALUE && z <= MAX_VALUE;
    }

    @Override
    public String toString () {
        return new StringBuilder("Location: (").append(x).append(",").append(y).append(")\n").toString();
    }
}
