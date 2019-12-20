package com.kangdroid.wordui;

import com.kangdroid.word.WordSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WordSettingsUI {

    private GridBagConstraints gbc;

    private MainMenu mMain;

    private JPanel mMainPanelWS;
    private JTextField mOECtr;
    private JTextField mMCQCtr;
    private JTextField mMCQChoiceCT;
    private JTextField mMCQTimeLimit;
    private JTextField mOETimeLimit;

    private JButton mClose;
    private JButton mSubmit;

    public WordSettingsUI(MainMenu mm) {
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
        mMainPanelWS = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        mMain.add(mMainPanelWS, BorderLayout.CENTER);

        // Label
        attachUI(0, 0, 2, 1);
        mMainPanelWS.add(new JLabel("OE Question Limit"), gbc);

        // OE Field
        attachUI(4, 0, 2, 1);
        mOECtr = new JTextField(10);
        mOECtr.setText(Integer.toString(WordSettings.mOECount));
        mOECtr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mSubmit.doClick();
            }
        });
        mMainPanelWS.add(mOECtr, gbc);

        //Label
        attachUI(0, 2, 2, 1);
        mMainPanelWS.add(new JLabel("MCQ Question Limit"), gbc);

        // MCQ Field
        attachUI(4, 2, 2, 1);
        mMCQCtr = new JTextField(10);
        mMCQCtr.setText(Integer.toString(WordSettings.mMCQCount));
        mMCQCtr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mSubmit.doClick();
            }
        });
        mMainPanelWS.add(mMCQCtr, gbc);

        // Label
        attachUI(0, 4, 2, 1);
        mMainPanelWS.add(new JLabel("MCQ Choice Limit"), gbc);

        // MCQ Choice Label
        attachUI(4, 4, 2, 1);
        mMCQChoiceCT = new JTextField(10);
        mMCQChoiceCT.setText(Integer.toString(WordSettings.mMCQChoiceCount));
        mMCQChoiceCT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mSubmit.doClick();
            }
        });
        mMainPanelWS.add(mMCQChoiceCT, gbc);

        // MCQ Time Limit Label
        attachUI(0, 6, 2, 1);
        mMainPanelWS.add(new JLabel("MCQ Time limit per question(In millis)"), gbc);

        // MCQ Time Limit field
        attachUI(4, 6, 2, 1);
        mMCQTimeLimit = new JTextField(10);
        mMCQTimeLimit.setText(Long.toString(WordSettings.mMCQTimeLimit));
        mMCQTimeLimit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mSubmit.doClick();
            }
        });
        mMainPanelWS.add(mMCQTimeLimit, gbc);

        attachUI(0, 8, 2, 1);
        mMainPanelWS.add(new JLabel("OE Time limit per question(In millis)"), gbc);

        attachUI(4, 8, 2, 1);
        mOETimeLimit = new JTextField(10);
        mOETimeLimit.setText(Long.toString(WordSettings.mOETimeLimit));
        mOETimeLimit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mSubmit.doClick();
            }
        });
        mMainPanelWS.add(mOETimeLimit, gbc);

        // close
        attachUI(0, 10, 2, 1);
        mClose = new JButton("Close");
        mClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mMainPanelWS.setVisible(false);
                mMain.restoreStatus();
            }
        });
        mMainPanelWS.add(mClose, gbc);

        // Submit
        gbc.gridx = 4;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        mSubmit = new JButton("Submit");
        mSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int mcqCount = Integer.parseInt(mMCQCtr.getText());
                int moeCount = Integer.parseInt(mOECtr.getText());
                int mcqchoice = Integer.parseInt(mMCQChoiceCT.getText());
                long mcqlimit = Long.parseLong(mMCQTimeLimit.getText());
                long oelimit = Long.parseLong(mOETimeLimit.getText());

                if (mcqCount <= 0 || mcqCount >= 30) {
                    JOptionPane.showMessageDialog(null, "MCQ Question cannot be under 0 or more than 30.", "Error Message", JOptionPane.ERROR_MESSAGE);
                    restoreString();
                } else if (moeCount <= 0 || moeCount >= 30) {
                    JOptionPane.showMessageDialog(null, "OE Question cannot be under 0 or more than 30.", "Error Message", JOptionPane.ERROR_MESSAGE);
                    restoreString();
                } else if (mcqchoice <= 0 || mcqchoice >= 10) {
                    JOptionPane.showMessageDialog(null, "MCQ Choice cannot be under 0 or more than 10.", "Error Message", JOptionPane.ERROR_MESSAGE);
                    restoreString();
                } else if (mcqlimit <= 1000 || mcqlimit >= 1000000) {
                    JOptionPane.showMessageDialog(null, "MCQ Time Limit should not less than 1s or should not greater than 100s", "Error Message", JOptionPane.ERROR_MESSAGE);
                    restoreString();
                } else if (oelimit <= 1000 || oelimit >= 1000000) {
                    JOptionPane.showMessageDialog(null, "OE Time Limit should not less than 1s or should not greater than 100s", "Error Message", JOptionPane.ERROR_MESSAGE);
                    restoreString();
                } else {
                    WordSettings.mMCQCount = Integer.parseInt(mMCQCtr.getText());
                    WordSettings.mOECount = Integer.parseInt(mOECtr.getText());
                    WordSettings.mMCQChoiceCount = Integer.parseInt(mMCQChoiceCT.getText());
                    WordSettings.mMCQTimeLimit = Long.parseLong(mMCQTimeLimit.getText());
                    WordSettings.mOETimeLimit = Long.parseLong(mOETimeLimit.getText());
                    JOptionPane.showMessageDialog(null, "Successfully changed Value!");
                }
            }
        });
        mMainPanelWS.add(mSubmit, gbc);
    }

    public void restoreString() {
        mOECtr.setText(Integer.toString(WordSettings.mOECount));
        mMCQCtr.setText(Integer.toString(WordSettings.mMCQCount));
        mMCQChoiceCT.setText(Integer.toString(WordSettings.mMCQChoiceCount));
        mMCQTimeLimit.setText(Long.toString(WordSettings.mMCQTimeLimit));
        mOETimeLimit.setText(Long.toString(WordSettings.mOETimeLimit));
    }
}
