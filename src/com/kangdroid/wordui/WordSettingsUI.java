package com.kangdroid.wordui;

import com.kangdroid.word.WordSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WordSettingsUI {
    private MainMenu mMain;

    private JPanel mMainPanelWS;
    private JTextField mOECtr;
    private JTextField mMCQCtr;

    private JButton mClose;
    private JButton mSubmit;

    public WordSettingsUI(MainMenu mm) {
        this.mMain = mm;
        init();
    }

    private void init() {
        mMainPanelWS = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        mMain.add(mMainPanelWS, BorderLayout.CENTER);

        // Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        mMainPanelWS.add(new JLabel("OE Question Limit"), gbc);

        // OE Field
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
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
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        mMainPanelWS.add(new JLabel("MCQ Question Limit"), gbc);

        // MCQ Field
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        mMCQCtr = new JTextField(10);
        mMCQCtr.setText(Integer.toString(WordSettings.mMCQCount));
        mMCQCtr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mSubmit.doClick();
            }
        });
        mMainPanelWS.add(mMCQCtr, gbc);

        // close
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
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
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        mSubmit = new JButton("Submit");
        mSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WordSettings.mMCQCount = Integer.parseInt(mMCQCtr.getText());
                WordSettings.mOECount = Integer.parseInt(mOECtr.getText());
                JOptionPane.showMessageDialog(null, "Successfully changed Value!");
            }
        });
        mMainPanelWS.add(mSubmit, gbc);
    }
}
