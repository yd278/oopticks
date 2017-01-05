package uk.ac.cam.yd278.oop.tick5;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Anchor on 2016/12/19.
 */
public class GamePanel extends JPanel {
    private World mWorld = null;

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        // Paint the background white
        g.setColor(java.awt.Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Sample drawing statements
        /*
        g.setColor(Color.BLACK);
        g.drawRect(200, 200, 30, 30);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(140, 140, 30, 30);
        g.fillRect(260, 140, 30, 30);
        g.setColor(Color.BLACK);
        g.drawLine(150, 300, 280, 300);
        g.drawString("@@@", 135,120);
        g.drawString("@@@", 255,120);
        */
        if(mWorld == null) return;

        int height = getHeight();
        int width = getWidth();
        int wHeight = mWorld.getHeight();
        int wWidth = mWorld.getWidth();
        int side = Math.min(height / wHeight, width / wWidth);
        for (int i = 0; i < wHeight; i++) {
            for (int j = 0; j < wWidth; j++) {
                int x = j * side;
                int y = i * side;
                g.setColor(Color.lightGray);
                g.drawRect(x,y,side,side);
                if(mWorld.getCell(j,i)){
                    g.setColor(Color.black);
                    g.fillRect(x,y,side,side);
                }
            }
        }
        g.setColor(Color.black);
        g.drawString("Generation : " + mWorld.getGenerationCount(),20,height-20);
    }

    public void display(World w) {
        mWorld = w;
        repaint();
    }
}

