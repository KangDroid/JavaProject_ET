package com.kangdroid.word;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WordManager {
	String path;
	Set<Word> st = new HashSet<>();

	public WordManager() {
		this("C:\\Users\\KangDroid\\Downloads\\words.txt");
	}

	public WordManager(String path) {
		this.path = path;
	}

	public void load() {
		File file = new File(path);
		Scanner scanner;
		try {
			scanner = new Scanner(file, "UTF-8");
		} catch (FileNotFoundException e) {
			System.out.println("���� ����");
			return;
		}
		StringTokenizer tokens;
		System.out.println(scanner.hasNextLine());

		while (scanner.hasNextLine()) {
			tokens = new StringTokenizer(scanner.nextLine(), ":/");
			Word word = new Word(tokens.nextToken().trim());
			while (tokens.hasMoreTokens()) {
				word.addMean(tokens.nextToken().trim());
			}
			//words.add(word);
			st.add(word);
		}
	}
	public void printFrequency() {
		List<Word> lst_word = new ArrayList<>();
		Iterator<Word> itr = st.iterator();

		for (int i = 0; i < st.size(); i++) {
			lst_word.add(itr.next());
		}

		Collections.sort(lst_word, Collections.reverseOrder());

		System.out.println("빈출도가 높은 Top 10");
		for (int i = 0; i < 10; i++) {
			System.out.println(lst_word.get(i));
		}
		System.out.println();
		System.out.println("빈출도가 낮은 Top 10");

		Collections.sort(lst_word);
		for (int i = 0; i < 10; i++) {
			System.out.println(lst_word.get(i));
		}
	}

	public void printTst() {
		Iterator<Word> itr = st.iterator();

		while(itr.hasNext()) {
			System.out.println(itr.next());
		}
	}

	public Word[] getRandomWords(int count) {
		Iterator<Word> itr;
		Random random = new Random();
		Word[] randomWords = new Word[count];
		Word w = null;
		int i, j;
		for(i = 0; i < count; i++) {
			itr = st.iterator();
			int ra = random.nextInt(st.size());
			for (int a = 0; a < ra; a++) {
				w = itr.next();
			}
			//System.out.println(w);
			for(j = 0; j < i; j++) {
				if(randomWords[j].equals(w)) {
					break;
				}
			}
			if(j != i) {
				i--;
				continue;
			}
			randomWords[i] = w;
		}
		return randomWords;
	}

	public void add(String word, String meaning) {
		Word tmp = new Word(word);
		tmp.addMean(meaning);
		st.add(tmp);
	}
}
