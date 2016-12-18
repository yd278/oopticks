package uk.ac.cam.yd278.oop.tick3;



/**
 * Created by Anchor on 2016/11/14.
 */
public abstract class World {
    private int mGeneration = 0;
    private Pattern mPattern;

    public World(String pattern) throws PatternFormatException {
        mPattern = new Pattern(pattern);
    }
    public World(Pattern pattern)throws PatternFormatException{
        mPattern = pattern;
    }

    public int getWidth() {
        return mPattern.getWidth();
    }

    public int getHeight() {
        return mPattern.getHeight();
    }

    public int getGenerationCount() {
        return mGeneration;
    }

    protected void incrementGenerationCount() {
        mGeneration++;
    }

    protected Pattern getPattern() {
        return mPattern;
    }

    public void nextGeneration() {
        nextGenerationImpl();
        incrementGenerationCount();
    }

    protected abstract void nextGenerationImpl();

    public abstract boolean getCell(int col, int row);

    public abstract void setCell(int col, int row, boolean val);

    protected int countNeighbours(int x, int y) {
        int neighbours = 0;
        if (getCell(x - 1, y)) neighbours++;
        if (getCell(x + 1, y)) neighbours++;
        if (getCell(x, y - 1)) neighbours++;
        if (getCell(x, y + 1)) neighbours++;
        if (getCell(x - 1, y - 1)) neighbours++;
        if (getCell(x - 1, y + 1)) neighbours++;
        if (getCell(x + 1, y - 1)) neighbours++;
        if (getCell(x + 1, y + 1)) neighbours++;
        return neighbours;
    }

    protected boolean computeCell(int x, int y) {


        int neighbours = countNeighbours(x, y);

        if (getCell(x, y)) {
            if (neighbours < 2) return false;
            if (neighbours > 3) return false;
            return true;
        } else {
            if (neighbours == 3) return true;
            else return false;
        }

    }

}
