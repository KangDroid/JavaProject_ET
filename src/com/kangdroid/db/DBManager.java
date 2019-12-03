package com.kangdroid.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class DBManager {
    private Connection conn;
    private PreparedStatement psmt;
    private ResultSet rs;

    public Connection getConn() {
        return conn;
    }

    public boolean connectDB() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/KangDroidWord",
                    "KangDroid",
                    "greenjoa");
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            System.out.println("JDBC Driver Load Failed");
            return false;
        } catch (SQLException e) {
            System.out.println("DB Connection Failed");
            //e.printStackTrace();
            return false;
        }
        if (conn != null) {
            return true;
        } else {
            return false;
        }
    }

    public void loadWord() {
        Scanner file_sc = null;
        StringTokenizer tokens = null;
        File file_input = new File("C:\\Users\\KangDroid\\Downloads\\words.txt");
        String sql = "insert into kangdroidword values(?,?,?)";
        try {
            file_sc = new Scanner(file_input, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            psmt = conn.prepareStatement(sql);
            while (file_sc.hasNextLine()) {
                psmt.setInt(1, getLastIndex()+1);
                tokens = new StringTokenizer(file_sc.nextLine(), ":/");
                String word = tokens.nextToken().trim();
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
}