package com.kangdroid.word;

import com.kangdroid.wordui.ThreadShared;

import javax.swing.*;
import java.util.Random;
import java.util.Scanner;

public class WordGame {
	private WordManager words;

	public WordGame() {
		words = new WordManager();
		words.load();
	}

	public String getWordList() {
		return words.getWordList();
	}

	public void addWord() {
		String word = null;
		String meaning = null;
		Scanner sc = new Scanner(System.in);
		System.out.println("단어를 입력하세요: ");
		word = sc.nextLine();
		System.out.println("뜻을 입력하세요: ");
		meaning = sc.nextLine();
		words.add(word, meaning);
	}

	public void printW() {
		words.printTst();
	}

	public void shortAnswerUI(JTextArea jta, ThreadShared ts, JTextArea answerArea) {
		//int correct = 0;
		Scanner in = new Scanner(System.in);
		Random r = new Random();
		Word[] questions = words.getRandomWords(5);
		for (int i = 0; i < questions.length; i++) {
			questions[i].addCount();
		}

		Runnable testRunnable = new Runnable() {
			@Override
			public void run() {
				int correct = 0;
				jta.setText("# Short Answer\n");
				for (int i = 0; i < 5; i++) {
					if (!ts.getThreadInturrpted()) {
						ts.setFinished(false);
						jta.append("Q" + (i + 1) + ". What does '" + questions[i].getWord() + "' means?");
						while (!ts.getFinished()) Thread.yield();
						String answer = answerArea.getText().trim();
						answerArea.setText("");
						if (questions[i].isCorrect(answer)) {
							correct++;
							jta.append("\n# CORRECT!!!");
						} else {
							jta.append("\n# WRONG!!!");
						}
						jta.append("# " + questions[i] + "\n");
					}
				}
				jta.append("You got " + correct + "/5 right word.");
			}
		};
		Thread t = new Thread(testRunnable);
		t.start();
	}

	public void shortAnswer() {
		int correct = 0;
		Scanner in = new Scanner(System.in);
		Random r = new Random();
		Word[] questions = words.getRandomWords(5);
		for (int i = 0; i < questions.length; i++) {
			questions[i].addCount();
		}
		String answer;

		System.out.println("# Short Answer");
		for (int i = 0; i < 5; i++) {
			System.out.print("Q" + (i + 1) + ". What does '" + questions[i].getWord() + "' means?\n>>> ");
			answer = in.nextLine().trim();
			if (questions[i].isCorrect(answer)) {
				correct++;
				System.out.println("# CORRECT!!!");
			} else {
				System.out.println("# WRONG!!!");
			}
			System.out.print("# " + questions[i]);
			System.out.println("\n");
		}
		System.out.println("You got " + correct + "/5 right word.");
	}

	public void frequencyWrapper() {
		words.printFrequency();
	}

	public void frequencyAdder(JTextArea mTop, JTextArea mBottom) {
		mTop.setText(words.printTopFrequencyUI());
		mBottom.setText(words.printBottomFrequencyUI());
	}

	public void multipleChoice() {
		Scanner uin = new Scanner(System.in);
		Random rand = new Random();
		int rightAnswer, answer;
		int howManyGot = 0;
		Word[] choices;
		for (int i = 0; i < 5; i++) {
			rightAnswer = rand.nextInt(4);
			choices = words.getRandomWords(4);
			choices[rightAnswer].addCount();
			System.out.println("Word: " + choices[rightAnswer].getWord() + "\n");

			for (int j = 0; j < 4; j++) {
				System.out.println((j + 1) + ". " + choices[j].getWordMeaning());
			}
			System.out.println("\nChoose Number: ");
			answer = uin.nextInt() - 1;
			if (answer == rightAnswer) {
				howManyGot++;
				System.out.println("정답입니다!");
			} else {
				System.out.println("틀렸습니다 ㅠㅠ");
			}
		}
		System.out.println("총 5문제 중 " + howManyGot + "만큼 맞았습니다!");
	}

	public void multipleChoiceUI(JTextArea wordShowArea, JRadioButton[] jr_choices, ThreadShared ts) {
		Runnable rn = new Runnable() {
			@Override
			public void run() {
				Random rand = new Random();
				int rightAnswer, answer;
				int howManyGot = 0;
				Word[] choices;
				for (int i = 0; i < 5; i++) {
					if (!ts.getThreadInturrpted()) {
						ts.setMCQFinished(false);
						rightAnswer = rand.nextInt(4);
						choices = words.getRandomWords(4);
						choices[rightAnswer].addCount();
						wordShowArea.append("Word: " + choices[rightAnswer].getWord() + "\n");

						for (int j = 0; j < 4; j++) {
							jr_choices[j].setText((j + 1) + ". " + choices[j].getWordMeaning());
						}
						while(!ts.getMCQFinished()) Thread.yield();
						answer = ts.getMcqValue();
						if (answer == rightAnswer) {
							howManyGot++;
							wordShowArea.append("정답입니다!\n\n");
						} else {
							wordShowArea.append("틀렸습니다! ㅠㅠ\n\n");
						}
					}
				}
				wordShowArea.append("총 5문제 중 " + howManyGot + "만큼 맞았습니다!\n");
			}
		};
		Thread thr = new Thread(rn);
		thr.start();
	}
}
