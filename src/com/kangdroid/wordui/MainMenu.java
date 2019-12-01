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
        centreDivider = new JPanel(new FlowLayout());
        this.add(centreDivider, BorderLayout.SOUTH);

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
        centreDivider.add(showWordList);

        wordGameSL = new JButton("OE Question!");
        wordGameSL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                topDivider.setVisible(false);
                centreDivider.setVisible(false);
                new WordSQDialog().initSQDialog();
            }
        });
        centreDivider.add(wordGameSL);

        wordGameMCQ = new JButton("MCQ Questions");
        wordGameMCQ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                topDivider.setVisible(false);
                centreDivider.setVisible(false);
                new WordGameMCQDialog().init();
            }
        });
        centreDivider.add(wordGameMCQ);

        showWordFrequency = new JButton("Show Frequency");
        showWordFrequency.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                wg.frequencyWrapper();
            }
        });
        centreDivider.add(showWordFrequency);

        this.setVisible(true);
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
