package com.kangdroid.wordui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddWordUI extends JDialog {
    // Root
    private MainMenu mMain;

    // The Main Panel
    private JPanel mMainPanel;
    private JPanel mBtnPanel;

    // Text Input
    private JTextField mWordInput;
    private JTextField mMeaningInput;

    // Buttons
    private JButton mCloseBtn;
    private JButton mSubmitWords;

    public AddWordUI(MainMenu mm) {
        this.mMain = mm;
        this.setModal(true);
        init();
    }

    private void init() {
        this.setSize(500, 200);

        mMainPanel = new JPanel(new GridLayout(5, 1));
        this.add(mMainPanel);

        mMainPanel.add(new JLabel("Words:"));
        mWordInput = new JTextField(30);
        mMainPanel.add(mWordInput);

        mMainPanel.add(new JLabel("Meaning:"));
        mMeaningInput = new JTextField(30);
        mMeaningInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mSubmitWords.doClick();
            }
        });
        mMainPanel.add(mMeaningInput);

        mBtnPanel = new JPanel(new FlowLayout());
        mMainPanel.add(mBtnPanel);

        mCloseBtn = new JButton("Close");
        mCloseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
        mBtnPanel.add(mCloseBtn);

        mSubmitWords = new JButton("Submit");
        mSubmitWords.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String words = mWordInput.getText().trim();
                String meaning = mMeaningInput.getText().trim();
                if (mMain.getWg().addWordWrapperTwo(words, meaning)) {
                    JOptionPane.showMessageDialog(null, "Adding words to game successfull!");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add words!", "Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        mBtnPanel.add(mSubmitWords);
        this.setVisible(true);
    }
}