package com.kangdroid.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class DBTesting {
    public static void main(String[] args) {
        DBManager dbm = new DBManager();
        dbm.connectDB();
        //dbm.registerWord();
        //dbm.loadWord();
        dbm.printDB();
        dbm.closeDB();
    }
}