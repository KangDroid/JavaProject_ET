package com.kangdroid.test;

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

    public void connectDB() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/KangDroidWord",
                    "KangDroid",
                    "greenjoa");
            if (conn != null) {
                System.out.println("DB Connection Succeed");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("JDBC Driver Load Failed");
        } catch (SQLException e) {
            System.out.println("DB Connection Failed");
            e.printStackTrace();
        }
    }

    public void printDBSpecific() {
        String sql = "select * from student where name = ?";
        try {
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, "Jason Kang");
            rs = psmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                int kor = rs.getInt(2);
                int eng = rs.getInt(3);
                System.out.println(name + ", " + kor + "," + eng);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

    public void printDB() {
        String sql = "select * from kangdroidword";
        try {
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();
            while (rs.next()) { //1, 2, 3
                int index = rs.getInt(1);
                String name = rs.getString(2);
                String meaning = rs.getString(3);
                System.out.println("Index: " + index + "\nWord: " + name + "\nMeaning: " + meaning);
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
        try {
            PreparedStatement tmp = conn.prepareStatement(sql);
            rs = tmp.executeQuery();
            while (rs.next()) { //1, 2, 3
                idx = rs.getInt(1);
            }
            tmp.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idx;
    }

    public void registerWord() {
        Scanner sc = new Scanner(System.in);
        System.out.println("단어를 입력해 주세요.");
        String name = sc.nextLine();

        System.out.println("단어의 뜻을 입력해주세요.");
        String meaning = sc.nextLine();

        String sql = "insert into kangdroidword values(?,?,?)";
        try {
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, getLastIndex()+1);
            psmt.setString(2, name);
            psmt.setString(3, meaning);
            psmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteQuery() {
        Scanner sc = new Scanner(System.in);
        System.out.println("지울 학생의 이름을 입력해 주세요:");
        String name = sc.nextLine();
        String sql = "delete from student where name = ?";
        try {
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, name);
            psmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}