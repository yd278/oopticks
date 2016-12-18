package uk.ac.cam.yd278.oop.tick4;


/**
 * Created by Anchor on 2016/11/14.
 */
public class PackedWorld extends World implements Cloneable {
    private long mWorld = 0;

    public PackedWorld(String serial) throws PatternFormatException {
        super(serial);
        if (getWidth() * getHeight() > 64) throw new PatternFormatException(
                getHeight() + "-by-" + getWidth() + " is too big for a packed long");
        getPattern().initialise(this);
    }

    public PackedWorld(Pattern pattern) throws PatternFormatException {
        super(pattern);
        if (getWidth() * getHeight() > 64) throw new PatternFormatException(
                getHeight() + "-by-" + getWidth() + " is too big for a packed long");
        getPattern().initialise(this);
    }

    public PackedWorld(PackedWorld mPackedWorld) {
        super(mPackedWorld);
        mWorld = mPackedWorld.getWorld();
    }

    @Override
    public void nextGenerationImpl() {
        long next = 0;
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                next = set(next, i * getWidth() + j, computeCell(j, i));
            }
        }
        mWorld = next;
    }

    public long getWorld() {
        return mWorld;
    }

    private boolean get(long world, int position) {
        long check = (world >>> position) & 1L;

        return (check == 1);
    }

    private long set(long world, int position, boolean value) {
        if (value) {
            world = world | (1L << position);
        } else {
            world = world & ~(1L << position);
        }
        return world;
    }

    @Override
    public boolean getCell(int col, int row) {
        if ((col < 0) || (col > getWidth())) return false;
        if ((row < 0) || (row > getHeight())) return false;
        return get(mWorld, row * getWidth() + col);
    }

    @Override
    public void setCell(int col, int row, boolean val) {
        if ((col < 0) || (col > getWidth())) return;
        if ((row < 0) || (row > getHeight())) return;
        mWorld = set(mWorld, row * getWidth() + col, val);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
