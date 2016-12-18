package uk.ac.cam.yd278.oop.tick5;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.io.IOException;
import java.util.List;


/**
 * Created by Anchor on 2016/12/18.
 */
public class GUILife extends JFrame {

    private World mWorld;
    private PatternStore mStore;
    private List<World> mCachedWorlds = null;

    private GUILife(PatternStore ps) {
        super("Game of Life");
        mStore=ps;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1024,768);

        add(createPatternsPanel(), BorderLayout.WEST);
        add(createControlPanel(),BorderLayout.SOUTH);
        add(createGamePanel(),BorderLayout.CENTER);

    }

    private void addBorder(JComponent component, String title) {
        Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Border tb = BorderFactory.createTitledBorder(etch,title);
        component.setBorder(tb);
    }

    private JPanel createGamePanel() {
        // TODO
        return new JPanel(); // temporary return
    }

    private JPanel createPatternsPanel() {
        JPanel patt = new JPanel();
        addBorder(patt,"Patterns");
        patt.setLayout(new BorderLayout());
        JList<Object> pattList = new JList<>();
        pattList.setListData(mStore.getPatternsNameSorted().toArray());
        JScrollPane jScrollPane = new JScrollPane(pattList);
        patt.add(jScrollPane);
        return patt;
    }

    private JPanel createControlPanel() {
        JPanel ctrl =  new JPanel();
        addBorder(ctrl,"Controls");
        ctrl.setLayout(new GridLayout());
        JButton back = new JButton("< Back");
        JButton play = new JButton("play");
        JButton forward = new JButton("Forward >");
        ctrl.add(back);
        ctrl.add(play);
        ctrl.add(forward);

        return ctrl;
    }

    private World copyWorld() {
        World copy = null;
        try {
            copy = (World) mWorld.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return copy;
    }

    private void moveBack() {
        if (mWorld == null) {
            //todo: do something to handle it
        } else {
            int generation = mWorld.getGenerationCount();
            if (generation != 0) {
                mWorld = mCachedWorlds.get(generation - 1);
            }
        }

    }
    private void moveForward(){
        if (mWorld == null){
            //todo: something
        }
        else {
            int generation = mWorld.getGenerationCount();
            if (generation + 1 < mCachedWorlds.size()) {
                mWorld = mCachedWorlds.get(generation + 1);
            } else {
                mWorld = copyWorld();
                mWorld.nextGeneration();
                mCachedWorlds.add(mWorld);
            }
        }

    }

    public static void main(String[] args) {
        PatternStore ps = null;
        try {
            ps = new PatternStore("http://www.cl.cam.ac.uk/teaching/1617/OOProg/ticks/life.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        GUILife gui = new GUILife(ps);
        gui.setVisible(true);

    }
}
