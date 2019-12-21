package com.kangdroid.db;

import com.kangdroid.word.Word;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

public class DBManager {
    private Connection conn;
    private PreparedStatement psmt;
    private ResultSet rs;
    private HashMap<String, Integer> tmp;

    public Connection getConn() {
        return conn;
    }

    public boolean connectDB() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mariadb://kangdroid.iptime.org:9095/KangDroidWord",
                    "greenjoa",
                    "greenjoa");
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            System.out.println("JDBC Driver Load Failed");
            System.out.println("PLEASE CHECK WHETHER JDBC DRIVER ADDED TO LIBRARY PATH.");
            System.out.println("PLEASE REFER TO REPORT FOR MORE DETAILS.");
            JOptionPane.showMessageDialog(null, "PLEASE CHECK WHETHER JDBC DRIVER ADDED TO LIBRARY PATH. \nPLEASE REFER TO REPORT FOR MORE DETAILS.\nFALLING BACK TO TXT-BASED DICTIONARY", "JDBC Driver Load Failed", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (SQLException e) {
            System.out.println("DB Connection Failed");
            System.out.println("PLEASE CHECK WHETHER SERVER IS ONLINE/OFFLINE. CONTACT TO ADMIN FOR DETAILS");
            System.out.println("FALLING BACK TO TXT-BASED DICTIONARY. SOME FEATURE WILL BE LIMITED.");
            JOptionPane.showMessageDialog(null, "PLEASE CHECK WHETHER SERVER IS ONLINE/OFFLINE. CONTACT TO ADMIN FOR DETAILS \nFALLING BACK TO TXT-BASED DICTIONARY. SOME FEATURE WILL BE LIMITED.", "DB Connection Failed", JOptionPane.ERROR_MESSAGE);
            //e.printStackTrace();
            return false;
        }
        if (conn != null) {
            return true;
        } else {
            return false;
        }
    }

    private int getLastIndex() {
        int idx = 0;
        String sql = "select * from kangdroidword";
        if (conn == null) System.out.println("Conn is NULL");
        try {
            PreparedStatement tmp = conn.prepareStatement(sql);
            ResultSet rs_two = tmp.executeQuery();
            while (rs_two.next()) { //1, 2, 3
                idx = rs_two.getInt(1);
            }
            //tmp.close();
            //rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idx;
    }

    public void registerWord(String words, String meaning) throws SQLException {

        String sql = "insert into kangdroidword values(?,?,?)";
        connectDB();
        psmt = conn.prepareStatement(sql);
        psmt.setInt(1, getLastIndex()+1);
        psmt.setString(2, words);
        psmt.setString(3, meaning);
        psmt.execute();
    }

    /**
     * For Creating word database.
     */
    public void createString() {
        tmp = new HashMap<>();

        System.out.println("Using DB");
        ResultSet rs;

        String sql = "select * from kangdroidword";
        try {
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();
            while (rs.next()) { //1, 2, 3
                String name = rs.getString(2);
                tmp.put(name, 10);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadWord() {
        Scanner file_sc = null;
        StringTokenizer tokens = null;
        File file_input = new File("C:\\engdict.txt");
        //File file_input = new File("C:\\voc_test.txt");
        String sql = "insert into kangdroidword values(?,?,?)";
        try {
            file_sc = new Scanner(file_input, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            psmt = conn.prepareStatement(sql);
            while (file_sc.hasNextLine()) {
                //tokens = new StringTokenizer(file_sc.nextLine(), ":/");
                //tokens = new StringTokenizer(file_sc.nextLine(), "\t,");
                tokens = new StringTokenizer(file_sc.nextLine(), "\t.,");
                String tr_val = tokens.nextToken();
                System.out.println(tr_val);
                String word = tokens.nextToken().trim();
                if (tmp.containsKey(word)) {
                    System.out.println("We already have: " + word + ", Skipping..");
                    continue;
                }
                psmt.setInt(1, getLastIndex()+1);
                psmt.setString(2, word);
                String meaning = "";
                int a = tokens.countTokens();
                for (int i = 0; i < a; i++) {
                    if (i == a - 1) {
                        meaning += (tokens.nextToken().trim());
                    } else {
                        meaning += (tokens.nextToken().trim()) + ", ";
                    }
                }
                psmt.setString(3, meaning);
                psmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeDB() {
        try {
            psmt.close();
            rs.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}