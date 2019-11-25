package com.kangdroid.word;

import java.util.Random;
import java.util.Scanner;

public class WordGame {
	private WordManager words;
	
	public WordGame() {
		words = new WordManager();
		words.load();
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

	public void shortAnswer() {
		int correct = 0;
		Scanner in = new Scanner(System.in);
		Random r = new Random();
		Word[] questions = words.getRandomWords(5);
		for (int i = 0; i < questions.length; i++) {
			questions[i].addCount();
		}
		String answer;
		int wordIndex;
		boolean duplicated = false;

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
}
