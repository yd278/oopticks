package uk.ac.cam.yd278.oop.tick2;

import java.io.IOException;

/**
 * Created by Anchor on 2016/11/14.
 */
public class GameOfLife {

    private World mWorld;

    public GameOfLife(World w) {
        mWorld = w;
    }

    public void play() throws java.io.IOException {
        int userResponse = 0;
        while (userResponse != 'q') {
            print();
            userResponse = System.in.read();
            mWorld.nextGeneration();
        }
    }

    public void print() {
        System.out.println("- " + mWorld.getGenerationCount());
        for (int row = 0; row < mWorld.getHeight(); row++) {
            for (int col = 0; col < mWorld.getWidth(); col++) {
                System.out.print(mWorld.getCell(col, row) ? "#" : "_");
            }
            System.out.println();
        }
    }

    public static void main(String args[]) throws IOException {

        try {
            World w = null;

            switch (args[0]) {
                case "--packed":
                    w = new PackedWorld(args[1]);
                    break;
                case "--array":
                    w = new ArrayWorld(args[1]);
                    break;
                default:
                    w = new ArrayWorld(args[0]);

            }
            GameOfLife gol = new GameOfLife(w);
            gol.play();
        } catch (PatternFormatException e) {
            System.out.println(e.getMessage());
        }
    }


}