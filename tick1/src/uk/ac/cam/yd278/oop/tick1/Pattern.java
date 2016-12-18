package uk.ac.cam.yd278.oop.tick1;

public class Pattern {

    private String mName;
    private String mAuthor;
    private int mWidth;
    private int mHeight;
    private int mStartCol;
    private int mStartRow;
    private String mCells;

    public String getName() {
        return mName;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public int getStartCol() {
        return mStartCol;
    }

    public int getStartRow() {
        return mStartRow;
    }

    public String getCells() {
        return mCells;
    }

    public Pattern(String format) throws PatternFormatException {
        String[] formats = format.split(":");
        if (formats.length != 7)
            throw new PatternFormatException("Invalid pattern format: Incorrect number of fields in pattern (found " + formats.length + ").");
        mName = formats[0];
        mAuthor = formats[1];
        try {
            mWidth = Integer.parseInt(formats[2]);
        } catch (Exception e) {
            throw new PatternFormatException("Invalid pattern format: Could not interpret the width field as a number (\'" + formats[2] + "\' given).");
        }
        try {
            mHeight = Integer.parseInt(formats[3]);
        } catch (Exception e) {
            throw new PatternFormatException("Invalid pattern format: Could not interpret the height field as a number (\'" + formats[3] + "\' given).");
        }
        try {
            mStartCol = Integer.parseInt(formats[4]);
        } catch (Exception e) {
            throw new PatternFormatException("Invalid pattern format: Could not interpret the startX field as a number (\'" + formats[4] + "\' given).");
        }
        try {
            mStartRow = Integer.parseInt(formats[5]);
        } catch (Exception e) {
            throw new PatternFormatException("Invalid pattern format: Could not interpret the startY field as a number (\'" + formats[5] + "\' given).");
        }
        mCells = formats[6];
    }

    public void initialise(boolean[][] world) throws PatternFormatException {

        String[] cells = mCells.split(" ");
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length(); j++) {

                if (cells[i].charAt(j) == '1')
                    world[i + mStartRow][j + mStartCol] = true;
                else if (cells[i].charAt(j) == '0')
                    world[i + mStartRow][j + mStartCol] = false;
                else throw new PatternFormatException("Malformed pattern \'" + mCells + "\'");

            }
        }
    }
}
//