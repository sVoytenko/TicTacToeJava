/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.Scanner;

/**
 *
 * @author admin
 */
public class Game {

    /* параметры игры */
    // символы клеток
    public String cellEmpty = " ";
    public String cellX = "x";
    public String cellO = "o";
    // масштаб доски и количество оставшихся ходов
    // public int boardScale;
    public int movesLeft;
    // сама доска
    private String gameBoard[][];
    private boolean isGameFinished = false;

    // конструктор заполняет игровое поле по умолчанию
    public Game(int scale) {

        String table[][] = new String[scale][scale];

        for (int i = 0; i < scale; i++) {
            for (int j = 0; j < scale; j++) {
                table[i][j] = this.cellEmpty;
            }
        }

        this.gameBoard = table;
        this.movesLeft = scale * scale;
    }

    // выводит в консоль игровое поле
    private void printTable() {

        String line = " _ ";

        for (int i = 0; i < this.gameBoard.length; i++) {

            System.out.println(line);

            for (int j = 0; j < this.gameBoard[i].length; j++) {
                System.out.print("|" + this.gameBoard[i][j]);
                if (j == this.gameBoard[i].length - 1) {
                    System.out.println("|");
                }
            }
        }

        System.out.println(line);
    }

    // ход компьютера
    private void computerMove() {
        System.out.println("Ход компьютера");

        boolean isMovePossible = false;

        int max = this.gameBoard.length;
        int horizontalMove = 0;
        int verticalMove = 0;

        while (isMovePossible == false) {
            horizontalMove = 0 + (int) (Math.random() * max);
            verticalMove = 0 + (int) (Math.random() * max);

            if (this.gameBoard[horizontalMove][verticalMove] == this.cellEmpty) {
                isMovePossible = true;
            }
        }

        this.gameBoard[horizontalMove][verticalMove] = this.cellO;
        this.movesLeft -= 1;

        this.printTable();

        this.checkWin(this.cellX);

        if (this.isGameFinished) {
            System.err.println("Победил игрок!");
        }
    }
    // запускает игру

    public void runGame() {

        this.printTable();

        while (this.movesLeft > 0 && !this.isGameFinished) {
            System.out.println("Ход игрока");
            System.out.println("Ходов осталось - " + this.movesLeft);

            System.out.println("выберите клетку по горизонтали:");
            int horizontal = new Scanner(System.in).nextInt() - 1;
            System.out.println("выберите клетку по вертикали:");
            int vertical = new Scanner(System.in).nextInt() - 1;

            if (horizontal > this.gameBoard.length || vertical > this.gameBoard.length) {
                System.out.println("Превышена размерность доски! Сделайте ход повторно!");
            } else if (this.gameBoard[horizontal][vertical] == this.cellO
                    || this.gameBoard[horizontal][vertical] == this.cellX) {
                System.out.println("Клетка уже занята! Сделайте ход повторно!");
            } else {
                this.gameBoard[horizontal][vertical] = this.cellX;
                this.movesLeft -= 1;
                System.out.println("Ходов осталось - " + this.movesLeft);
                this.printTable();

                this.checkWin(this.cellX);

                if (this.isGameFinished) {
                    System.err.println("Победил игрок!");
                    break;
                }

                if (this.movesLeft > 0 && !this.isGameFinished) {
                    this.computerMove();
                }
            }
        }

    }

    //проверка, победил ли кто-то из игроков
    private void checkWin(String cellSymbol) {

        //проверка ходов по горизонтали
        boolean isVictory = false;

        int max = this.gameBoard.length;

        for (int i = 0; i < max; i++) {

            boolean horizontalVictory = true;

            for (int j = 0; j < max; j++) {
                if (this.gameBoard[i][j] != cellSymbol) {
                    horizontalVictory = false;
                    break;
                }
            }

            if (horizontalVictory == true) {
                this.isGameFinished = true;
                System.out.println("победа");
                break;
            }
        }

        if (this.isGameFinished == true) {
            return;
        }

        //проверка колонок на победную серию
        boolean verticalVictory = true;

        for (int i = 0; i < this.gameBoard.length; i++) {
            if (this.gameBoard[i][0] != cellSymbol) {
                verticalVictory = false;
                break;
            }
        }

        if (verticalVictory) {
            this.isGameFinished = true;
            return;
        }

        //проверка наискось
        boolean crossVictory = true;

        int hMove = 0;
        for (int i = 0; i < this.gameBoard.length; i++) {
            System.out.println(i + " " + hMove);
            if (this.gameBoard[i][hMove] != cellSymbol) {
                crossVictory = false;
                break;
            }
            hMove += 1;
        }

        if (crossVictory) {
            this.isGameFinished = true;
            return;
        }

        //проверка наисколь в обратную сторону, надо бы подумать как объединить это одним циклом
        crossVictory = true;

        hMove = 0;
        for (int i = (this.gameBoard.length - 1); i > 0; i--) {
            if (this.gameBoard[i][hMove] != cellSymbol) {
                crossVictory = false;
                break;
            }
            hMove += 1;
        }

        if (crossVictory) {
            this.isGameFinished = true;
            return;
        }

    }
}
