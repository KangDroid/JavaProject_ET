package com.kangdroid.wordui;

import com.kangdroid.word.WordGame;
import com.kangdroid.word.WordManager;

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
    private JButton wordGameSL;
    private JButton wordGameMCQ;
    private JButton showWordFrequency;
    private JButton addWordBtn;

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
        GridBagConstraints gbc = new GridBagConstraints();
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
                    MainMenu.WordListDialog wld = new WordListDialog();
                    wld.initWordListDialog();
                }
            }
        );
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        centreDivider.add(showWordList, gbc);

        wordGameSL = new JButton("OE Question!");
        wordGameSL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                topDivider.setVisible(false);
                centreDivider.setVisible(false);
                new WordSQDialog().initSQDialog();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        centreDivider.add(wordGameSL, gbc);

        wordGameMCQ = new JButton("MCQ Questions");
        wordGameMCQ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                topDivider.setVisible(false);
                centreDivider.setVisible(false);
                new WordGameMCQDialog().init();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        centreDivider.add(wordGameMCQ, gbc);

        showWordFrequency = new JButton("Show Frequency");
        showWordFrequency.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                topDivider.setVisible(false);
                centreDivider.setVisible(false);
                new WordFrequencyUI().init();
                //wg.frequencyWrapper();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        centreDivider.add(showWordFrequency, gbc);

        addWordBtn = new JButton("Add Words");
        addWordBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new AddWordUI().init();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        centreDivider.add(addWordBtn, gbc);

        this.setVisible(true);
    }

    private class AddWordUI extends JDialog {
        // The Main Panel
        private JPanel mMainPanel;
        private JPanel mBtnPanel;

        // Text Input
        private JTextField mWordInput;
        private JTextField mMeaningInput;

        // Buttons
        private JButton mCloseBtn;
        private JButton mSubmitWords;

        private void init() {
            this.setSize(500, 200);

            mMainPanel = new JPanel(new GridLayout(5, 1));
            this.add(mMainPanel);

            mMainPanel.add(new JLabel("Words:"));
            mWordInput = new JTextField(30);
            mMainPanel.add(mWordInput);

            mMainPanel.add(new JLabel("Meaning:"));
            mMeaningInput = new JTextField(30);
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
                    wg.addWordWrapperTwo(words, meaning);
                }
            });
            mBtnPanel.add(mSubmitWords);
            this.setVisible(true);
        }
    }

    private class WordFrequencyUI {
        // Panel
        private JPanel mTopTotalPanel;
        private JPanel mBottomTotalPanel;
        private JPanel mButtonPanel;

        // TextArea
        private JTextArea mTopList;
        private JTextArea mBottomList;

        // Scrollpane for TextArea
        private JScrollPane mTopScroll;
        private JScrollPane mBottomScroll;

        // Buttons
        private JButton mPrevBtn;

        private void init() {
            mTopTotalPanel = new JPanel();
            add(mTopTotalPanel, BorderLayout.NORTH);

            mBottomTotalPanel = new JPanel();
            add(mBottomTotalPanel, BorderLayout.CENTER);

            mButtonPanel = new JPanel(new FlowLayout());
            add(mButtonPanel, BorderLayout.SOUTH);

            mTopTotalPanel.add(new JLabel("Top 10:"), BorderLayout.NORTH);
            mBottomTotalPanel.add(new JLabel("Bottom 10:"), BorderLayout.NORTH);

            mTopList = new JTextArea(8, 30);
            mTopList.setEditable(false);
            mTopScroll = new JScrollPane(mTopList);
            mTopTotalPanel.add(mTopScroll, BorderLayout.CENTER);

            mBottomList = new JTextArea(8, 30);
            mBottomList.setEditable(false);
            mBottomScroll = new JScrollPane(mBottomList);
            mBottomTotalPanel.add(mBottomScroll, BorderLayout.CENTER);

            mPrevBtn = new JButton("Back to Main Menu");
            mPrevBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    mTopTotalPanel.setVisible(false);
                    mBottomTotalPanel.setVisible(false);
                    mButtonPanel.setVisible(false);
                    restoreStatus();
                }
            });
            mButtonPanel.add(mPrevBtn);
            wg.frequencyAdder(mTopList, mBottomList);
        }
    }

    private class WordGameMCQDialog {
        // Panel
        private JPanel bottomArea;
        private JPanel centreArea;
        private JPanel mcqArea;

        // JTextArea
        private JTextArea mcqShowArea;

        // JRadioButton Container
        ButtonGroup radioGroup;

        // JRadioButton
        private JRadioButton[] mcqShow;

        // JButton
        private JButton submitBtn;
        private JButton mCancelBtn;

        private void init() {
            // Root
            ThreadShared ts = new ThreadShared();
            setTitle("MCQ Game!");

            mcqShow = new JRadioButton[4];

            // Bottom Panel
            bottomArea = new JPanel(new FlowLayout());
            add(bottomArea, BorderLayout.SOUTH);

            // Centre Panel
            centreArea = new JPanel();
            add(centreArea, BorderLayout.WEST);

            // MCQ Panel
            mcqArea = new JPanel();
            mcqArea.setLayout(new GridLayout(5, 1, 5, 5));
            add(mcqArea, BorderLayout.EAST);

            // MCQ Show Text Area
            mcqShowArea = new JTextArea(5, 20);
            mcqShowArea.setEditable(false);
            centreArea.add(mcqShowArea, BorderLayout.WEST);

            // JRadioButton Group
            radioGroup = new ButtonGroup();

            for (int i = 0; i < 4; i++) {
                mcqShow[i] = new JRadioButton();
                radioGroup.add(mcqShow[i]);
                mcqArea.add(mcqShow[i]);
            }

            // Cancel(Previous Button)
            mCancelBtn = new JButton("Go back to Main Menu");
            mCancelBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    ts.setThreadInturrpted(true);
                    ts.setMCQFinished(true);
                    bottomArea.setVisible(false);
                    centreArea.setVisible(false);
                    mcqArea.setVisible(false);
                    restoreStatus();
                }
            });
            bottomArea.add(mCancelBtn);

            // Submit Button
            submitBtn = new JButton("Submit");
            submitBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    for (int i = 0; i < 4; i++) {
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
            wg.multipleChoiceUI(mcqShowArea, mcqShow, ts);
        }
    }

    private class WordSQDialog {
        JPanel questionListShow;
        JPanel mQuestionInputPanel;
        JPanel mButtonArea;

        JTextArea questionMain;
        JTextArea mQuestionInputMain;
        JButton mQuestionSubmit;
        JButton mPrevButton;

        private void initSQDialog() {
            ThreadShared ts = new ThreadShared();

            setLayout(new BorderLayout());
            setSize(500, 500);

            // Main Panel
            questionListShow = new JPanel();
            questionListShow.setLayout(new BorderLayout());
            add(questionListShow, BorderLayout.CENTER);

            // Bottom Panel
            mQuestionInputPanel = new JPanel();
            mQuestionInputPanel.setLayout(new BorderLayout());
            add(mQuestionInputPanel, BorderLayout.SOUTH);

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
            mQuestionInputMain = new JTextArea(5, 30);
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

                    restoreStatus();
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

            wg.shortAnswerUI(questionMain, ts, mQuestionInputMain);
        }
    }

    private class WordListDialog {
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

        private void initWordListDialog() {
            // TODO: place custom component creation code here

            // Child Panel - Title Area
            topDividerWLD = new JPanel();
            add(topDividerWLD, BorderLayout.NORTH);

            // Child Panel - Bottom Button Area
            bottomDividerWLD = new JPanel();
            add(bottomDividerWLD, BorderLayout.SOUTH);

            // Child Panel - Button
            closeBtn = new JButton("Close");
            closeBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    topDividerWLD.setVisible(false);
                    bottomDividerWLD.setVisible(false);
                    scrollPane.setVisible(false);
                    restoreStatus();
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
            setPreferredSize(new Dimension(450, 100));
            add(scrollPane, BorderLayout.CENTER);

            // Get the Words from Word Manager.
            setTextWord();
        }

        private void setTextWord() {
            wordShowArea.setEditable(false);
            wordShowArea.setText(wg.getWordList());
        }
    }
}
