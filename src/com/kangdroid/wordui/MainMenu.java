package com.kangdroid.wordui;

import com.kangdroid.word.WordGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    private JPanel topDivider;
    private JPanel centreDivider;
    private WordGame wg;

    // GBC
    private GridBagConstraints gbc;

    // The Constructor
    public MainMenu() {
        wg = new WordGame();
        init();
    }

    public void restoreStatus() {
        this.setTitle("Word Game: " + ((wg.getDBStatus()) ? "Using DB" : "Using TXT"));
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
        this.setTitle("Word Game: " + ((wg.getDBStatus()) ? "Using DB" : "Using TXT"));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 400);

        // Main Top Divider
        topDivider = new JPanel();
        this.add(topDivider, BorderLayout.NORTH);

        // Label
        // The Labels
        JLabel mainTitle = new JLabel("Word Game(" + ((wg.getDBStatus()) ? "Using DB)" : "Using TXT)"));
        topDivider.add(mainTitle, BorderLayout.CENTER);

        // Main Centre Divider
        centreDivider = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        this.add(centreDivider, BorderLayout.CENTER);

        // Left Panel

        attachUI(0, 0, 2, 1);
        centreDivider.add(new JLabel("Word Related Menu"), gbc);

        // The Buttons
        // The Buttons
        JButton showWordList = new JButton("Show words for game!");
        int width = 170;// Button Size
        int height = 23;
        showWordList.setPreferredSize(new Dimension(width, height));
        showWordList.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    topDivider.setVisible(false);
                    centreDivider.setVisible(false);
                    new WordListDialog(getInstsance());
                }
            }
        );
        attachUI(0, 2, 2, 1);
        centreDivider.add(showWordList, gbc);

        JButton mFindWord = new JButton("Find Meaning!");
        mFindWord.setPreferredSize(new Dimension(width, height));
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
        attachUI(0, 4, 2, 1);
        centreDivider.add(mFindWord, gbc);

        JButton showWordFrequency = new JButton("Show Frequency");
        showWordFrequency.setPreferredSize(new Dimension(width, height));
        showWordFrequency.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                topDivider.setVisible(false);
                centreDivider.setVisible(false);
                new WordFrequencyUI(getInstsance());
            }
        });
        attachUI(0, 6, 2, 1);
        centreDivider.add(showWordFrequency, gbc);

        JButton addWordBtn = new JButton("Add Words");
        addWordBtn.setPreferredSize(new Dimension(width, height));
        addWordBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new AddWordUI(getInstsance());
            }
        });
        attachUI(0, 8, 2, 1);
        centreDivider.add(addWordBtn, gbc);

        JButton mRemoveWord = new JButton("Remove Words");
        mRemoveWord.setPreferredSize(new Dimension(width, height));
        mRemoveWord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String finder = JOptionPane.showInputDialog("Input word you want to delete.");
                if (finder != null) {
                    if (finder.equals("")) {
                        JOptionPane.showMessageDialog(null, "You need to enter the word!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        int what = JOptionPane.showConfirmDialog(null, wg.searchWrapper(finder) + "\nDo you want to delete this word?");
                        if (what == 0) {
                            wg.removeWord();
                        }
                    }
                }
            }
        });
        attachUI(0, 10, 2, 1);
        centreDivider.add(mRemoveWord, gbc);

        // Right Panel
        attachUI(4, 0, 2, 1);
        centreDivider.add(new JLabel("Game Related Menu"), gbc);

        JButton wordGameSL = new JButton("OE Question!");
        wordGameSL.setPreferredSize(new Dimension(width, height));
        wordGameSL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                topDivider.setVisible(false);
                centreDivider.setVisible(false);
                new WordSQDialog(getInstsance());
            }
        });
        attachUI(4, 2, 2, 1);
        centreDivider.add(wordGameSL, gbc);

        JButton wordGameMCQ = new JButton("MCQ Questions");
        wordGameMCQ.setPreferredSize(new Dimension(width, height));
        wordGameMCQ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                topDivider.setVisible(false);
                centreDivider.setVisible(false);
                new WordGameMCQDialog(getInstsance());
            }
        });
        attachUI(4, 4, 2, 1);
        centreDivider.add(wordGameMCQ, gbc);

        JButton mWordSettings = new JButton("Word Settings");
        mWordSettings.setPreferredSize(new Dimension(width, height));
        mWordSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                topDivider.setVisible(false);
                centreDivider.setVisible(false);
                new WordSettingsUI(getInstsance());
            }
        });

        attachUI(4, 6, 2, 1);
        centreDivider.add(mWordSettings, gbc);

        this.setVisible(true);
    }
}
