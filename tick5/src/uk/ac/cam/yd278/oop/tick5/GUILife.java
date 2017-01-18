package uk.ac.cam.yd278.oop.tick5;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Anchor on 2016/12/18.
 */
public class GUILife extends JFrame {

    private World mWorld;
    private PatternStore mStore;
    private List<World> mCachedWorlds = null;
    private GamePanel mGamePanel;
    private boolean mPlaying;
    private JButton mPlayButton;
    private Timer mTimer = new Timer();

    private GUILife(PatternStore ps) {
        super("Game of Life");
        mStore = ps;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1024, 768);

        add(createPatternsPanel(), BorderLayout.WEST);
        add(createControlPanel(), BorderLayout.SOUTH);
        add(createGamePanel(), BorderLayout.CENTER);

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

    private void addBorder(JComponent component, String title) {
        Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Border tb = BorderFactory.createTitledBorder(etch, title);
        component.setBorder(tb);
    }

    private JPanel createGamePanel() {
        mGamePanel = new GamePanel();
        addBorder(mGamePanel, "Game Panel");
        return mGamePanel;
    }

    private JPanel createPatternsPanel() {
        JPanel patt = new JPanel();
        addBorder(patt, "Patterns");
        patt.setLayout(new BorderLayout());
        JList<Pattern> pattList = new JList<>();
        int storeSize = mStore.getSize();
        pattList.setListData(mStore.getPatternsNameSorted().toArray(new Pattern[storeSize]));
        pattList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(mPlaying){
                    runOrPause();
                }
                JList<Pattern> list = (JList<Pattern>) e.getSource();
                Pattern p = (Pattern) list.getSelectedValue();
                try {
                    if (p.getHeight() * p.getWidth() <= 64) {
                        mWorld = new PackedWorld(p);
                    } else {
                        mWorld = new ArrayWorld(p);
                    }
                } catch (PatternFormatException e1) {
                    e1.printStackTrace();
                }
                mCachedWorlds = new ArrayList<>();
                mCachedWorlds.add(mWorld);
                mGamePanel.display(mWorld);
            }
        });
        JScrollPane jScrollPane = new JScrollPane(pattList);
        patt.add(jScrollPane);
        return patt;
    }

    private JPanel createControlPanel() {
        JPanel ctrl = new JPanel();
        addBorder(ctrl, "Controls");
        ctrl.setLayout(new GridLayout());
        JButton back = new JButton("< Back");
        back.addActionListener(e -> {
            if (mWorld != null) {
                if (mPlaying) {
                    mTimer.cancel();
                    mPlaying = false;
                    mPlayButton.setText("Play");
                }
                moveBack();
            }
        });
        mPlayButton = new JButton("play");
        mPlayButton.addActionListener(e -> {
            if (mWorld != null) runOrPause();
        });
        JButton forward = new JButton("Forward >");
        forward.addActionListener(e -> {
            if (mWorld != null) {
                if (mPlaying) {
                    mTimer.cancel();
                    mPlaying = false;
                    mPlayButton.setText("Play");
                }
                moveForward();
            }
        });
        ctrl.add(back);
        ctrl.add(mPlayButton);
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
        int generation = mWorld.getGenerationCount();
        if (generation != 0) {
            mWorld = mCachedWorlds.get(generation - 1);
        }
        mGamePanel.display(mWorld);

    }

    private void moveForward() {
        int generation = mWorld.getGenerationCount();
        if (generation + 1 < mCachedWorlds.size()) {
            mWorld = mCachedWorlds.get(generation + 1);
        } else {
            mWorld = copyWorld();
            mWorld.nextGeneration();
            mCachedWorlds.add(mWorld);
        }
        mGamePanel.display(mWorld);

    }

    private void runOrPause() {
        if (mPlaying) {
            mTimer.cancel();
            mPlaying = false;
            mPlayButton.setText("Play");
        } else {
            mPlaying = true;
            mPlayButton.setText("Stop");
            mTimer = new Timer(true);
            mTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    moveForward();
                }
            }, 0, 500);
        }
    }
}
