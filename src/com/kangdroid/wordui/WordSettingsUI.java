package com.kangdroid.wordui;

import com.kangdroid.db.DBManager;
import com.kangdroid.word.WordSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;

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
    private JButton mPreset;
    private JButton mSavePreset;

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
        // For saving Preference
        DBManager dbm = new DBManager();
        boolean isConnected = dbm.connectForPREF();

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
                int mcqCount = 0;
                int moeCount = 0;
                int mcqchoice = 0;
                long mcqlimit = 0;
                long oelimit = 0;
                try {
                    mcqCount = Integer.parseInt(mMCQCtr.getText());
                    moeCount = Integer.parseInt(mOECtr.getText());
                    mcqchoice = Integer.parseInt(mMCQChoiceCT.getText());
                    mcqlimit = Long.parseLong(mMCQTimeLimit.getText());
                    oelimit = Long.parseLong(mOETimeLimit.getText());
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "The input must be in integer/number type.", "Nuber is not recognized!", JOptionPane.ERROR_MESSAGE);
                    restoreString();
                    return;
                }

                if (mcqCount <= 0) {
                    JOptionPane.showMessageDialog(null, "MCQ Question cannot be under 0.", "Error Message", JOptionPane.ERROR_MESSAGE);
                    restoreString();
                } else if (moeCount <= 0) {
                    JOptionPane.showMessageDialog(null, "OE Question cannot be under 0.", "Error Message", JOptionPane.ERROR_MESSAGE);
                    restoreString();
                } else if (mcqchoice <= 0) {
                    JOptionPane.showMessageDialog(null, "MCQ Choice cannot be under 0 .", "Error Message", JOptionPane.ERROR_MESSAGE);
                    restoreString();
                } else if (mcqlimit <= 100) {
                    JOptionPane.showMessageDialog(null, "MCQ Time Limit should not less than 0.1s", "Error Message", JOptionPane.ERROR_MESSAGE);
                    restoreString();
                } else if (oelimit <= 100) {
                    JOptionPane.showMessageDialog(null, "OE Time Limit should not less than 1s or should not greater than 100s", "Error Message", JOptionPane.ERROR_MESSAGE);
                    restoreString();
                } else {
                    setValue(Integer.parseInt(mOECtr.getText()), Integer.parseInt(mMCQCtr.getText()), Integer.parseInt(mMCQChoiceCT.getText()),
                            Long.parseLong(mMCQTimeLimit.getText()), Long.parseLong(mOETimeLimit.getText()));
                    JOptionPane.showMessageDialog(null, "Successfully changed Value!");
                }
            }
        });
        mMainPanelWS.add(mSubmit, gbc);

        attachUI(0, 12, 2, 1);
        mPreset = new JButton("Preset");
        mPreset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] mChoices = dbm.getChoice();
                Object mSelected = JOptionPane.showInputDialog(null, "Which preset do you want to use?", "Preset Selection",
                        JOptionPane.DEFAULT_OPTION, null, mChoices, "Easy");
                if (mSelected != null) {
                    int[] arr = dbm.getValue(mSelected.toString());
                    setValue(arr[0], arr[1], arr[2], arr[3], arr[4]);
                    JOptionPane.showMessageDialog(null, "Successfully changed Value!");
                }
            }
        });
        if (isConnected) {
            mMainPanelWS.add(mPreset, gbc);
        }

        attachUI(4, 12 ,2 ,1);
        mSavePreset = new JButton("Save Current value to server");
        mSavePreset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mUserInput = JOptionPane.showInputDialog(null, "Enter Preset Name");
                if (mUserInput != null) {
                    if (mUserInput.equals("")) {
                        JOptionPane.showMessageDialog(null, "You need to enter the word!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        if (dbm.checkExists(mUserInput)) {
                            JOptionPane.showMessageDialog(null, "Profile name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            try {
                                dbm.putValue(mUserInput, Integer.parseInt(mOECtr.getText()), Integer.parseInt(mMCQCtr.getText()), Integer.parseInt(mMCQChoiceCT.getText()),
                                        Long.parseLong(mMCQTimeLimit.getText()), Long.parseLong(mOETimeLimit.getText()));
                                JOptionPane.showMessageDialog(null, "Successfully saved to DB!");
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, "DB Failed", "Error", JOptionPane.ERROR_MESSAGE);
                                //ex.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
        if (isConnected) {
            mMainPanelWS.add(mSavePreset, gbc);
        }
    }

    public void setValue(int oectr, int mcqctr, int mcqchoicectr, long mcqtl, long oetl) {
        WordSettings.mOECount = oectr;
        WordSettings.mMCQCount = mcqctr;
        WordSettings.mMCQChoiceCount = mcqchoicectr;
        WordSettings.mMCQTimeLimit = mcqtl;
        WordSettings.mOETimeLimit = oetl;
        restoreString();
    }

    public void restoreString() {
        mOECtr.setText(Integer.toString(WordSettings.mOECount));
        mMCQCtr.setText(Integer.toString(WordSettings.mMCQCount));
        mMCQChoiceCT.setText(Integer.toString(WordSettings.mMCQChoiceCount));
        mMCQTimeLimit.setText(Long.toString(WordSettings.mMCQTimeLimit));
        mOETimeLimit.setText(Long.toString(WordSettings.mOETimeLimit));
    }
}
