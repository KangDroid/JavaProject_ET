package com.kangdroid.word;

import java.util.ArrayList;

public class Word implements Comparable<Word> {
	private String word;
	private ArrayList<String> means;
	private int wordCounter;

	public Word(String word) {
		this.word = word;
		means = new ArrayList<>();
		this.wordCounter = 0;
	}

	public String getWord() {
		return word;
	}

	public void addMean(String mean) {
		this.means.add(mean);
	}

	public void addCount() {
		this.wordCounter++;
	}

	public boolean isCorrect(String mean) {
		return means.contains(mean);
	}

	@Override
	public boolean equals(Object word) {
		return this.word.equals(((Word) word).getWord());
	}

	@Override
	public int compareTo(Word wd) {
		return this.wordCounter - wd.wordCounter;
	}

	public String getWordMeaning() {
		String t = "";
		for (int i = 0; i < this.means.size(); i++) {
			if (i == this.means.size() - 1) {
				t += this.means.get(i);
			} else {
				t += this.means.get(i) + "/";
			}
		}
		return t;
	}

	@Override
	public int hashCode() {
		return word.hashCode();
	}

	@Override
	public String toString() {
		String s = word + " : ";
		for (int i = 0; i < means.size(); i++) {
			s += means.get(i) + (i == means.size() - 1 ? "" : " / ");
		}
		return s;
	}
}
