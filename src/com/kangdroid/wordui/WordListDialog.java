package com.kangdroid.wordui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WordListDialog {
    MainMenu mMain;

    JPanel topDividerWLD; // Child Panel - Title Area
    JPanel bottomDividerWLD; // Child Panel - Button(Bottom) Area

    // Buttons
    JButton closeBtn;
    JButton nextBtn;

    // The Label
    JLabel topTitle; // Title

    // Text Area
    JTextArea wordShowArea; // Word Showing Area
    JScrollPane scrollPane;

    public WordListDialog(MainMenu mm) {
        this.mMain = mm;
        initWordListDialog();
    }

    private void initWordListDialog() {
        // TODO: place custom component creation code here

        // Child Panel - Title Area
        topDividerWLD = new JPanel();
        mMain.add(topDividerWLD, BorderLayout.NORTH);

        // Child Panel - Bottom Button Area
        bottomDividerWLD = new JPanel();
        mMain.add(bottomDividerWLD, BorderLayout.SOUTH);

        // Child Panel - Button
        closeBtn = new JButton("Close");
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                topDividerWLD.setVisible(false);
                bottomDividerWLD.setVisible(false);
                scrollPane.setVisible(false);
                mMain.restoreStatus();
            }
        });
        bottomDividerWLD.add(closeBtn, BorderLayout.WEST);
        nextBtn = new JButton("Next");

        // Child Panel - Title
        topTitle = new JLabel("Word List for word game!");
        topDividerWLD.add(topTitle);

        // Word Show Area with Scroll Pane
        wordShowArea = new JTextArea(5, 30);
        scrollPane = new JScrollPane(wordShowArea);
        mMain.setPreferredSize(new Dimension(450, 100));
        mMain.add(scrollPane, BorderLayout.CENTER);

        // Get the Words from Word Manager.
        setTextWord();
    }

    private void setTextWord() {
        wordShowArea.setEditable(false);
        wordShowArea.setText(mMain.getWg().getWordList());
    }
}
