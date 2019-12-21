package com.kangdroid.word;

import com.kangdroid.wordui.ThreadShared;

import javax.swing.*;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class WordGame {
	private WordManager words;

	public WordGame() {
		words = new WordManager();
		words.load();
	}

	public Iterator<Word> getIter() {
		return words.getIterator();
	}

	public int getWordCount() {
		return words.getWordCount();
	}

	public boolean removeWord() {
		return words.removeWord();
	}

	public boolean getDBStatus() {
		return words.getDBAStatus();
	}

	public String searchWrapper(String input) {
		return words.searchWord(input);
	}

	public String getWordList() {
		return words.getWordList();
	}

	public boolean addWordWrapperTwo(String words, String meaning) {
		return this.words.addWordsWrapper(words, meaning);
	}

	public void shortAnswerUI(JTextArea jta, ThreadShared ts, JTextField answerArea) {
		//int correct = 0;
		Scanner in = new Scanner(System.in);
		Random r = new Random();
		Word[] questions = words.getRandomWords(WordSettings.mOECount);
		for (int i = 0; i < questions.length; i++) {
			questions[i].addCount();
		}

		Runnable testRunnable = new Runnable() {
			@Override
			public void run() {
				boolean isTimeover = false;
				int correct = 0;
				jta.setText("# Short Answer\n");
				for (int i = 0; i < WordSettings.mOECount; i++) {
					if (!ts.getThreadInturrpted()) {
						ts.setFinished(false);
						jta.append("Q" + (i + 1) + ". What does '" + questions[i].getWord() + "' means?");
						long time = System.currentTimeMillis();
						while(!ts.getMCQFinished()) {
							long ed_time = System.currentTimeMillis();
							if ((ed_time - time) >= WordSettings.mOETimeLimit) {
								isTimeover = true;
								break; //force finish
							}
							isTimeover = false;
						}
						String answer = answerArea.getText().trim();
						answerArea.setText("");
						if (questions[i].isCorrect(answer)) {
							correct++;
							jta.append("\n# CORRECT!!!");
						} else {
							if (isTimeover) {
								jta.append("\n# TIME OVER!!!");
							} else {
								jta.append("\n# WRONG!!!");
							}
						}
						jta.append("# " + questions[i] + "\n");
					}
				}
				jta.append("You got " + correct + "/" + WordSettings.mOECount + "right word.");
			}
		};
		Thread t = new Thread(testRunnable);
		t.start();
	}

	public void frequencyAdder(JTextArea mTop, JTextArea mBottom) {
		mTop.setText(words.printTopFrequencyUI());
		mBottom.setText(words.printBottomFrequencyUI());
	}

	public void multipleChoiceUI(JTextArea wordShowArea, JRadioButton[] jr_choices, ThreadShared ts) {
		Runnable rn = new Runnable() {
			@Override
			public void run() {
				boolean isTimeover = false;
				Random rand = new Random();
				int rightAnswer, answer;
				int howManyGot = 0;
				Word[] choices;
				for (int i = 0; i < WordSettings.mMCQCount; i++) {
					if (!ts.getThreadInturrpted()) {
						ts.setMCQFinished(false);
						rightAnswer = rand.nextInt(WordSettings.mMCQChoiceCount);
						choices = words.getRandomWords(WordSettings.mMCQChoiceCount);
						choices[rightAnswer].addCount();
						wordShowArea.append("Word: " + choices[rightAnswer].getWord() + "\n");

						for (int j = 0; j < WordSettings.mMCQChoiceCount; j++) {
							jr_choices[j].setText((j + 1) + ". " + choices[j].getWordMeaning());
						}
						long time = System.currentTimeMillis();
						while(!ts.getMCQFinished()) {
							long ed_time = System.currentTimeMillis();
							if ((ed_time - time) >= WordSettings.mMCQTimeLimit) {
								isTimeover = true;
								break; //force finish
							}
							isTimeover = false;
						}
						answer = ts.getMcqValue();
						if (answer == rightAnswer) {
							howManyGot++;
							wordShowArea.append("# Correct!!\n\n");
						} else {
							if (isTimeover) {
								wordShowArea.append("# TIME OVER!!\n\n");
							} else {
								wordShowArea.append("# WRONG!!!\n\n");
							}
						}
						System.out.println("Answer was" + answer + "and Right Answer was" + rightAnswer);
					}
				}
				wordShowArea.append("총 " + WordSettings.mMCQCount + "문제 중 " + howManyGot + "만큼 맞았습니다!\n");
			}
		};
		Thread thr = new Thread(rn);
		thr.start();
	}
}
