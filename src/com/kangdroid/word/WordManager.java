package com.kangdroid.word;

import com.kangdroid.db.DBManager;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class WordManager {
	Set<Word> st = new HashSet<>();
	DBManager dbm;
	boolean isDBAvailable;
	Word tmpSaver;

	public WordManager() {
		this.dbm = new DBManager();
		isDBAvailable = this.dbm.connectDB();
	}

	public boolean removeWord() {
		if (isDBAvailable) {
			try {
				dbm.removeWord(tmpSaver.getWord());
			} catch (SQLException e) {
				st.remove(tmpSaver);
				JOptionPane.showMessageDialog(null, "Cannot delete word " + tmpSaver.getWord() + "on DB. Deleting internal data!");
				return false; // Return false when registering on DB.
			}
		}
		st.remove(tmpSaver);
		JOptionPane.showMessageDialog(null, "Successfully deleted " + tmpSaver.getWord() + "!");
		return true;
	}

	public String searchWord(String target) {
		String t = "We do not have such word: " + target;
		Iterator<Word> tmpIterator = st.iterator();
		while (tmpIterator.hasNext()) {
			Word tmp = tmpIterator.next();
			if (tmp.getWord().equals(target)) {
				t = "The Word " + target + " its meaning is : " + tmp.getWordMeaning();
				this.tmpSaver = tmp;
				break;
			}
		}
		return t;
	}

	/**
	 * @return true if this program uses db, false if this program uses TXT Based Word
	 */
	public boolean getDBAStatus() {
		return this.isDBAvailable;
	}

	public boolean addWordsWrapper(String words, String meaning) {
		if (isDBAvailable) {
			try {
				dbm.registerWord(words, meaning);
				this.add(words, meaning); // register to local side as well.
			} catch (SQLException e) {
				this.add(words, meaning);
				return false; // Return false when registering on DB.
			}
		}
		this.add(words, meaning);
		return true;
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
				scanner = new Scanner(file, StandardCharsets.UTF_8);
			} catch (IOException e) {
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

	public String getWordList() {
		String t = "";
		Iterator<Word> itr = st.iterator();

		while(itr.hasNext()) {
			t += (itr.next()) + "\n";
		}
		return t;
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
