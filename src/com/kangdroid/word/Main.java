package com.kangdroid.word;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("201911148 강현우\n201911204 천송현\n201911205 이현령\n201911219 이주형\n");

        String title = ""
                + "┌──────────────────────┐ \n"
                + "│  좀 대단한 영단어장  │ \n"
                + "└──────────────────────┘ ";
        System.out.println(title);

        Scanner scanner = new Scanner(System.in);
        WordGame game = new WordGame();
        int select;

        do {
            System.out.print("\n1. 주관식\n2. 객관식\n3. 종료\n\n>>> ");
            select = scanner.nextInt();
            if (select == 1) {
                game.shortAnswer();
            } else if (select == 2) {
                game.multipleChoice();
            } else if (select == 3) {
                game.addWord();
            } else if (select == 4) {
                game.printW();
            } else if (select == 5) {
                game.frequencyWrapper();
            }
        } while (select != 6);
        scanner.close();
    }
}
