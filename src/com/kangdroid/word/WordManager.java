package com.kangdroid.word;

import com.kangdroid.db.DBManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class WordManager {
	String path;
	Set<Word> st = new HashSet<>();
	DBManager dbm;
	boolean isDBAvailable;

	public WordManager() {
		this.dbm = new DBManager();
		isDBAvailable = this.dbm.connectDB();
	}

	public WordManager(String path) {
		this.path = path;
	}

	public void load() {
		if (isDBAvailable) {
			System.out.println("Using DB");
			ResultSet rs;
			StringTokenizer tokens;

			String sql = "select * from kangdroidword";
			try {
				Connection conn = dbm.getConn();
				PreparedStatement psmt = conn.prepareStatement(sql);
				rs = psmt.executeQuery();
				while (rs.next()) { //1, 2, 3
					String name = rs.getString(2);
					String meaning = rs.getString(3);
					Word word = new Word(name);
					tokens = new StringTokenizer(meaning, ",");
					while (tokens.hasMoreTokens()) {
						word.addMean(tokens.nextToken().trim());
					}
					st.add(word);
				}
				conn.close();
				psmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Using TXT");
			File file = new File("words.txt");
			Scanner scanner;
			try {
				scanner = new Scanner(file, "UTF-8");
			} catch (FileNotFoundException e) {
				System.out.println("Cannot Find File Information.");
				return;
			}
			StringTokenizer tokens;

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
	}

	public String printTopFrequencyUI() {
		String mTopFreq = "";
		List<Word> lst_word = new ArrayList<>();
		Iterator<Word> itr = st.iterator();

		for (int i = 0; i < st.size(); i++) {
			lst_word.add(itr.next());
		}

		Collections.sort(lst_word, Collections.reverseOrder());

		for (int i = 0; i < 10; i++) {
			mTopFreq += (lst_word.get(i) + "\n");
		}
		return mTopFreq;
	}

	public String printBottomFrequencyUI() {
		String mTopFreq = "";
		List<Word> lst_word = new ArrayList<>();
		Iterator<Word> itr = st.iterator();

		for (int i = 0; i < st.size(); i++) {
			lst_word.add(itr.next());
		}
		Collections.sort(lst_word);
		for (int i = 0; i < 10; i++) {
			mTopFreq += (lst_word.get(i) + "\n");
		}

		return mTopFreq;
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

	public String getWordList() {
		String t = "";
		Iterator<Word> itr = st.iterator();

		while(itr.hasNext()) {
			t += (itr.next()) + "\n";
		}
		return t;
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
