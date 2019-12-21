package com.kangdroid.wordui;

import com.kangdroid.word.Word;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

public class WordListDialog {
    MainMenu mMain;

    JPanel topDividerWLD; // Child Panel - Title Area
    JPanel bottomDividerWLD; // Child Panel - Button(Bottom) Area

    // Buttons
    JButton closeBtn;

    // The Label
    JLabel topTitle; // Title

    // Text Area
    JScrollPane scrollPane;

    // Table
    JTable mShowingTable;
    String[] columnNames = {"Word", "Meaning"};
    Object[][] mRowData;

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

        // Child Panel - Title
        topTitle = new JLabel("Word List for word game!");
        topDividerWLD.add(topTitle);

        // Word Show Area with Scroll Pane
        mRowData = new Object[mMain.getWg().getWordCount()][2];
        Iterator<Word> mIterTmp = mMain.getWg().getIter();
        for (int i = 0; i < mRowData.length; i++) {
            if (mIterTmp.hasNext()) {
                Word tmp = mIterTmp.next();
                mRowData[i][0] = tmp.getWord(); // words
                mRowData[i][1] = tmp.getWordMeaning(); // meaning
            }
        }
        DefaultTableModel dfm = new DefaultTableModel(mRowData, columnNames);
        mShowingTable = new JTable(dfm);
        scrollPane = new JScrollPane(mShowingTable);
        mMain.setPreferredSize(new Dimension(450, 100));
        mMain.add(scrollPane, BorderLayout.CENTER);

    }
}
