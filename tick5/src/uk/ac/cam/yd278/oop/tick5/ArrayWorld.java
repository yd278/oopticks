package uk.ac.cam.yd278.oop.tick5;


/**
 * Created by Anchor on 2016/11/14.
 */
public class ArrayWorld extends World implements Cloneable{
    private boolean[][] mWorld;
    private boolean[] mDeadRow;
    private void compDeadRow(){
        for(int i = 0;i<getHeight();i++){
            boolean flag = false;
            for(int j = 0; j< getWidth();j++){
                flag |= mWorld[i][j];
            }
            if(!flag) mWorld[i] = mDeadRow;
        }
    }
    public ArrayWorld(String serial) throws PatternFormatException {
        super(serial);
        mWorld = new boolean[getHeight()][getWidth()];
        mDeadRow = new boolean[getWidth()];
        getPattern().initialise(this);
        compDeadRow();
    }

    public ArrayWorld(Pattern pattern) throws PatternFormatException {
        super(pattern);
        mWorld = new boolean[getHeight()][getWidth()];
        mDeadRow = new boolean[getWidth()];
        getPattern().initialise(this);
        compDeadRow();
    }

    public boolean[] getDead(){
        return mDeadRow;
    }
    public void setDead(boolean[] newDeadRow){
        mDeadRow = newDeadRow;
    }
    public void setWorld(boolean[][] newWorld){
        mWorld = newWorld;
    }
    @Override
    public void nextGenerationImpl() {
        boolean[][] next = new boolean[getHeight()][getWidth()];
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                next[i][j] = computeCell(j, i);
            }
        }
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                setCell(j, i, next[i][j]);
            }

        }
        mWorld = next;
    }

    @Override
    public boolean getCell(int col, int row) {
        if ((col < 0) || (col >= getWidth())) return false;
        if ((row < 0) || (row >= getHeight())) return false;
        return mWorld[row][col];
    }

    @Override
    public void setCell(int col, int row, boolean val) {
        if ((col < 0) || (col > getWidth())) return;
        if ((row < 0) || (row > getHeight())) return;
        mWorld[row][col] = val;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ArrayWorld copy = (ArrayWorld) super.clone();
        boolean[][] newWorld = new boolean[getHeight()][getWidth()];
        for(int i = 0; i < getHeight(); i++){
            for(int j = 0; j < getWidth(); j++){
                newWorld[i][j] = mWorld[i][j];
            }
        }
        copy.setWorld(newWorld);
        return copy;
    }

}
