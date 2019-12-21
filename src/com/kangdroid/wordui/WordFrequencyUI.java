package com.kangdroid.wordui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WordFrequencyUI {

    // Root
    private MainMenu mMain;

    // Panel
    private GridBagConstraints gbc;
    private JPanel mPanelMain;
    private JPanel mButtonArea;

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

    public void attachUI(int gridx, int gridy, int gridwidth, int gridheight) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
    }

    private void init() {
        mButtonArea = new JPanel(new FlowLayout());
        mPanelMain = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        mMain.add(mPanelMain, BorderLayout.CENTER);
        mMain.add(mButtonArea, BorderLayout.SOUTH);

        attachUI(0, 0, 2, 1);
        mPanelMain.add(new JLabel("Top 10:"), gbc);
        attachUI(0, 2, 2, 1);
        mPanelMain.add(new JLabel("Bottom 10:"), gbc);

        mTopList = new JTextArea(8, 30);
        mTopList.setEditable(false);
        mTopScroll = new JScrollPane(mTopList);
        attachUI(3, 0, 2, 1);
        mPanelMain.add(mTopScroll, gbc);

        mBottomList = new JTextArea(8, 30);
        mBottomList.setEditable(false);
        mBottomScroll = new JScrollPane(mBottomList);
        attachUI(3, 2, 2, 1);
        mPanelMain.add(mBottomScroll, gbc);

        mPrevBtn = new JButton("Back to Main Menu");
        mPrevBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mPanelMain.setVisible(false);
                mButtonArea.setVisible(false);
                mMain.restoreStatus();
            }
        });
        mButtonArea.add(mPrevBtn);
        mMain.getWg().frequencyAdder(mTopList, mBottomList);
    }
}
