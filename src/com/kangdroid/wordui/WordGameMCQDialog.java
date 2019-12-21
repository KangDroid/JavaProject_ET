package com.kangdroid.wordui;

import com.kangdroid.word.WordSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WordGameMCQDialog {

    private MainMenu mMain;

    // Panel
    private JPanel bottomArea;
    private JPanel centreArea;
    private JPanel mcqArea;

    // JRadioButton Container
    ButtonGroup radioGroup;

    // JRadioButton
    private JRadioButton[] mcqShow;

    public WordGameMCQDialog(MainMenu mm) {
        this.mMain = mm;
        if (WordSettings.mMCQChoiceCount >= 5) {
            mMain.setSize(900, 500);
        }
        init();
    }

    private void init() {
        // Root
        ThreadShared ts = new ThreadShared();
        mMain.setTitle("MCQ Game!");

        mcqShow = new JRadioButton[WordSettings.mMCQChoiceCount];

        // Bottom Panel
        bottomArea = new JPanel(new FlowLayout());
        mMain.add(bottomArea, BorderLayout.SOUTH);

        // Centre Panel
        centreArea = new JPanel();
        mMain.add(centreArea, BorderLayout.WEST);

        // MCQ Panel
        mcqArea = new JPanel();
        mcqArea.setLayout(new GridLayout(5, 2, 5, 5));
        mMain.add(mcqArea, BorderLayout.EAST);

        // MCQ Show Text Area
        // JTextArea
        JTextArea mcqShowArea = new JTextArea(20, 20);
        mcqShowArea.setEditable(false);
        centreArea.add(new JScrollPane(mcqShowArea), BorderLayout.WEST);

        // JRadioButton Group
        radioGroup = new ButtonGroup();

        for (int i = 0; i < WordSettings.mMCQChoiceCount; i++) {
            mcqShow[i] = new JRadioButton();
            radioGroup.add(mcqShow[i]);
            mcqArea.add(mcqShow[i]);
        }

        // Cancel(Previous Button)
        JButton mCancelBtn = new JButton("Go back to Main Menu");
        mCancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ts.setThreadInturrpted(true);
                ts.setMCQFinished(true);
                bottomArea.setVisible(false);
                centreArea.setVisible(false);
                mcqArea.setVisible(false);
                mMain.restoreStatus();
            }
        });
        bottomArea.add(mCancelBtn);

        // Submit Button
        // JButton
        JButton submitBtn = new JButton("Submit");
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                for (int i = 0; i < WordSettings.mMCQChoiceCount; i++) {
                    if (mcqShow[i].isSelected()) {
                        ts.setMcqValue(i);
                        break;
                    } else {
                        ts.setMcqValue(-1);
                    }
                }
                radioGroup.clearSelection();
                ts.setMCQFinished(true);
            }
        });
        bottomArea.add(submitBtn);

        // THIS SHOULD COMES LAST
        mMain.getWg().multipleChoiceUI(mcqShowArea, mcqShow, ts);
    }
}
