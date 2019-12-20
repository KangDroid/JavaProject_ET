package com.kangdroid.wordui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WordFrequencyUI {

    // Root
    private MainMenu mMain;

    // Panel
    private JPanel mTopTotalPanel;
    private JPanel mBottomTotalPanel;
    private JPanel mButtonPanel;

    // TextArea
    private JTextArea mTopList;
    private JTextArea mBottomList;

    // Scrollpane for TextArea
    private JScrollPane mTopScroll;
    private JScrollPane mBottomScroll;

    // Buttons
    private JButton mPrevBtn;

    public WordFrequencyUI(MainMenu mm) {
        this.mMain = mm;
        init();
    }

    private void init() {
        mTopTotalPanel = new JPanel();
        mMain.add(mTopTotalPanel, BorderLayout.NORTH);

        mBottomTotalPanel = new JPanel();
        mMain.add(mBottomTotalPanel, BorderLayout.CENTER);

        mButtonPanel = new JPanel(new FlowLayout());
        mMain.add(mButtonPanel, BorderLayout.SOUTH);

        mTopTotalPanel.add(new JLabel("Top 10:"), BorderLayout.NORTH);
        mBottomTotalPanel.add(new JLabel("Bottom 10:"), BorderLayout.NORTH);

        mTopList = new JTextArea(8, 30);
        mTopList.setEditable(false);
        mTopScroll = new JScrollPane(mTopList);
        mTopTotalPanel.add(mTopScroll, BorderLayout.CENTER);

        mBottomList = new JTextArea(8, 30);
        mBottomList.setEditable(false);
        mBottomScroll = new JScrollPane(mBottomList);
        mBottomTotalPanel.add(mBottomScroll, BorderLayout.CENTER);

        mPrevBtn = new JButton("Back to Main Menu");
        mPrevBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mTopTotalPanel.setVisible(false);
                mBottomTotalPanel.setVisible(false);
                mButtonPanel.setVisible(false);
                mMain.restoreStatus();
            }
        });
        mButtonPanel.add(mPrevBtn);
        mMain.getWg().frequencyAdder(mTopList, mBottomList);
    }
}
