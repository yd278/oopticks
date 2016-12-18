package uk.ac.cam.yd278.oop.tick1;

public class ArrayLife {

    private boolean[][] mWorld;
    private Pattern mPattern;

    public ArrayLife(String format) throws PatternFormatException {
        mPattern = new Pattern(format);
        mWorld = new boolean[mPattern.getHeight()][mPattern.getWidth()];

        for (int i = 0; i < mPattern.getHeight(); i++)
            for (int j = 0; j < mPattern.getWidth(); j++)
                mWorld[i][j] = false;
        mPattern.initialise(mWorld);


    }

    public boolean getCell(int col, int row) {
        if (row < 0 || row >= mPattern.getHeight()) return false;
        if (col < 0 || col >= mPattern.getWidth()) return false;
        return mWorld[row][col];
    }

    public void setCell(int col, int row, boolean value) {
        mWorld[row][col] = value;
    }

    public void print() {
        System.out.println("-");
        for (int row = 0; row < mPattern.getHeight(); row++) {
            for (int col = 0; col < mPattern.getWidth(); col++) {
                System.out.print(getCell(col, row) ? "#" : "_");
            }
            System.out.println();
        }
    }

    private int countNeighbours(int col, int row) {
        int neighbours = 0;
        if (getCell(col - 1, row)) neighbours++;
        if (getCell(col + 1, row)) neighbours++;
        if (getCell(col, row - 1)) neighbours++;
        if (getCell(col, row + 1)) neighbours++;
        if (getCell(col - 1, row - 1)) neighbours++;
        if (getCell(col - 1, row + 1)) neighbours++;
        if (getCell(col + 1, row - 1)) neighbours++;
        if (getCell(col + 1, row + 1)) neighbours++;
        return neighbours;
    }


    private boolean computeCell(int col, int row) {
        int neighbours = countNeighbours(col, row);
        if (getCell(col, row)) {
            if (neighbours < 2) return false;
            if (neighbours > 3) return false;
            return true;
        } else {
            if (neighbours == 3) return true;
            else return false;
        }
    }


    public void nextGeneration() {
        boolean[][] nextGeneration = new boolean[mPattern.getHeight()][];
        for (int y = 0; y < mPattern.getHeight(); y++) {
            nextGeneration[y] = new boolean[mPattern.getWidth()];
            for (int x = 0; x < mPattern.getWidth(); x++) {
                boolean nextCell = computeCell(x, y);
                nextGeneration[y][x] = nextCell;
            }
        }
        mWorld = nextGeneration;
    }

    public void play() throws java.io.IOException {
        int userResponse = 0;
        while (userResponse != 'q') {
            print();
            userResponse = System.in.read();
            nextGeneration();
        }
    }



    public static void main(String[] args) throws Exception {
        try {
            ArrayLife al = new ArrayLife(args[0]);
            al.play();
        } catch (PatternFormatException e) {
            System.out.println(e.getMessage());
        }
    }
}
