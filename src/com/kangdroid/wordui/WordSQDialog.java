package com.kangdroid.wordui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WordSQDialog {

    MainMenu mMain;

    JPanel questionListShow;
    JPanel mQuestionInputPanel;
    JPanel mButtonArea;

    JTextArea questionMain;
    JTextField mQuestionInputMain;
    JButton mQuestionSubmit;
    JButton mPrevButton;

    public WordSQDialog(MainMenu mm) {
        this.mMain = mm;
        initSQDialog();
    }

    private void initSQDialog() {
        ThreadShared ts = new ThreadShared();

        mMain.setLayout(new BorderLayout());
        mMain.setSize(500, 500);

        // Main Panel
        questionListShow = new JPanel();
        questionListShow.setLayout(new BorderLayout());
        mMain.add(questionListShow, BorderLayout.CENTER);

        // Bottom Panel
        mQuestionInputPanel = new JPanel();
        mQuestionInputPanel.setLayout(new BorderLayout());
        mMain.add(mQuestionInputPanel, BorderLayout.SOUTH);

        // Button Area
        mButtonArea = new JPanel();
        mButtonArea.setLayout(new FlowLayout());
        mQuestionInputPanel.add(mButtonArea, BorderLayout.SOUTH);

        //title - top
        JLabel jlb = new JLabel("SLA");
        questionListShow.add(jlb, BorderLayout.NORTH);

        // Title - Bottom
        JLabel mBottomInput = new JLabel("Input Answers");
        mQuestionInputPanel.add(mBottomInput, BorderLayout.NORTH);

        // Question INPUT
        mQuestionInputMain = new JTextField(30);
        mQuestionInputMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mQuestionSubmit.doClick();
            }
        });
        mQuestionInputPanel.add(mQuestionInputMain, BorderLayout.CENTER);

        // Buttons
        mPrevButton = new JButton("Back to Main Menu");
        mQuestionSubmit = new JButton("Submit");

        // Add Action Listener
        mPrevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ts.setThreadInturrpted(true);
                ts.setMCQFinished(true);
                questionListShow.setVisible(false);
                mQuestionInputPanel.setVisible(false);
                mButtonArea.setVisible(false);

                mMain.restoreStatus();
            }
        });
        mButtonArea.add(mPrevButton);
        mQuestionSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Runnable testRunnable = new Runnable() {
                    @Override
                    public void run() {
                        // Something can run
                        ts.setFinished(true); // SHOULD BE SYNCHRONIZED
                    }
                };
                Thread thr = new Thread(testRunnable);
                thr.start();
            }
        });
        mButtonArea.add(mQuestionSubmit);

        // Showing some question area
        questionMain = new JTextArea(5, 30);
        questionMain.setEditable(false);
        questionListShow.add(questionMain, BorderLayout.CENTER);

        mMain.getWg().shortAnswerUI(questionMain, ts, mQuestionInputMain);
    }
}
