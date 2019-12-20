package com.kangdroid.wordui;

import com.kangdroid.word.WordGame;
import com.kangdroid.word.WordManager;
import com.kangdroid.word.WordSettings;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    private JPanel topDivider;
    private JPanel centreDivider;
    private WordGame wg;

    // The Labels
    private JLabel mainTitle;

    // The Buttons
    private JButton showWordList;
    private JButton mFindWord;
    private JButton wordGameSL;
    private JButton wordGameMCQ;
    private JButton showWordFrequency;
    private JButton addWordBtn;
    private JButton mWordSettings;

    // GBC
    private GridBagConstraints gbc;

    // The Constructor
    public MainMenu() {
        wg = new WordGame();
        init();
    }

    public void restoreStatus() {
        this.setTitle("Word Game");
        this.setSize(700, 400);
        topDivider.setVisible(true);
        centreDivider.setVisible(true);
    }

    public MainMenu getInstsance() {
        return this;
    }

    public WordGame getWg() {
        return this.wg;
    }

    public void attachUI(int gridx, int gridy, int gridwidth, int gridheight) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
    }

    private void init() {
        // Root
        this.setTitle("Word Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 400);

        // Main Top Divider
        topDivider = new JPanel();
        this.add(topDivider, BorderLayout.NORTH);

        // Label
        mainTitle = new JLabel("Word Game");
        topDivider.add(mainTitle, BorderLayout.CENTER);

        // Main Centre Divider
        centreDivider = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        this.add(centreDivider, BorderLayout.CENTER);

        // The Buttons
        showWordList = new JButton("Show words for game!");
        showWordList.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    topDivider.setVisible(false);
                    centreDivider.setVisible(false);
                    new WordListDialog(getInstsance());
                }
            }
        );
        attachUI(0, 0, 2, 1);
        centreDivider.add(showWordList, gbc);

        mFindWord = new JButton("Find Meaning!");
        mFindWord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String finder = JOptionPane.showInputDialog("Input word you want to search.");
                if (finder != null) {
                    if (finder.equals("")) {
                        JOptionPane.showMessageDialog(null, "You need to enter the word!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, wg.searchWrapper(finder));
                    }
                }
            }
        });
        attachUI(0, 2, 2, 1);
        centreDivider.add(mFindWord, gbc);

        wordGameSL = new JButton("OE Question!");
        wordGameSL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                topDivider.setVisible(false);
                centreDivider.setVisible(false);
                new WordSQDialog(getInstsance());
            }
        });
        attachUI(0, 4, 2, 1);
        centreDivider.add(wordGameSL, gbc);

        wordGameMCQ = new JButton("MCQ Questions");
        wordGameMCQ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                topDivider.setVisible(false);
                centreDivider.setVisible(false);
                new WordGameMCQDialog(getInstsance());
            }
        });
        attachUI(0, 6, 2, 1);
        centreDivider.add(wordGameMCQ, gbc);

        showWordFrequency = new JButton("Show Frequency");
        showWordFrequency.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                topDivider.setVisible(false);
                centreDivider.setVisible(false);
                new WordFrequencyUI(getInstsance());
            }
        });
        attachUI(0, 8, 2, 1);
        centreDivider.add(showWordFrequency, gbc);

        addWordBtn = new JButton("Add Words");
        addWordBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new AddWordUI(getInstsance());
            }
        });
        attachUI(0, 10, 2, 1);
        centreDivider.add(addWordBtn, gbc);

        mWordSettings = new JButton("Word Settings");
        mWordSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                topDivider.setVisible(false);
                centreDivider.setVisible(false);
                new WordSettingsUI(getInstsance());
            }
        });

        attachUI(0, 12, 2, 1);
        centreDivider.add(mWordSettings, gbc);

        this.setVisible(true);
    }
}
