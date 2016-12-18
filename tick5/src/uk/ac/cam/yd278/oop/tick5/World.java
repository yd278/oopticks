package uk.ac.cam.yd278.oop.tick5;


/**
 * Created by Anchor on 2016/11/14.
 */
public abstract class World implements Cloneable {
    private int mGeneration = 0;
    private Pattern mPattern;

    World(String pattern) throws PatternFormatException {
        mPattern = new Pattern(pattern);
    }

    World(Pattern pattern) throws PatternFormatException {
        mPattern = pattern;
    }


    int getWidth() {
        return mPattern.getWidth();
    }

    int getHeight() {
        return mPattern.getHeight();
    }

    int getGenerationCount() {
        return mGeneration;
    }

    private void incrementGenerationCount() {
        mGeneration++;
    }

    Pattern getPattern() {
        return mPattern;
    }

    private void setPattern(Pattern newPattern) {
        mPattern = newPattern;
    }

    void nextGeneration() {
        nextGenerationImpl();
        incrementGenerationCount();
    }

    protected abstract void nextGenerationImpl();

    public abstract boolean getCell(int col, int row);

    public abstract void setCell(int col, int row, boolean val);

    @Override
    public Object clone() throws CloneNotSupportedException {
        World copy = (World) super.clone();
        copy.setPattern((Pattern)copy.getPattern().clone());
        return copy;
    }

    private int countNeighbours(int x, int y) {
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

    boolean computeCell(int x, int y) {


        int neighbours = countNeighbours(x, y);

        if (getCell(x, y)) {
            return neighbours >= 2 && neighbours <= 3;
        } else {
            return neighbours == 3;
        }

    }

}
