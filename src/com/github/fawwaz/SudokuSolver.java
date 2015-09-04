/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.fawwaz;

import java.util.*;
import java.lang.*;
import java.io.*;

/**
 *
 * @author Asus
 */
public class SudokuSolver {
    Integer[][][] sudokus;
    Integer[][] temp_sudoku;
    public void readFile(String filename){
        
        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            String currentLine;
            
            // Parse the file 
            int i = 0;
            int l = 0; // to mark which sudoku grup, increasing everytime contcatenated
            while((currentLine = br.readLine())!=null){
                if(i==0){
                    // first line is about how many sudokus inside the txt file
                    sudokus = new Integer[Integer.valueOf(currentLine)][9][9];
                }else{
                    if(!currentLine.trim().equals("")){
                        String[] cline  = currentLine.trim().split(" ");
                        Integer tempy   = (i-2-l)%9;
                        for (int j = 0; j < cline.length; j++) {
                            temp_sudoku[tempy][j] = Integer.valueOf(cline[j]);
                        }
                        System.out.println(i+"Bukan Spasi");
                    }else{
                        if(i!=1){
                            for (int j = 0; j < 9; j++) {
                                for (int k = 0; k < 9; k++) {
                                    sudokus[l][j][k] = temp_sudoku[j][k];
                                }
                            }
                            l++; // increased to mark which current sudoku
                        }else{
                            // Kalau pertama kali buat array Integer
                            temp_sudoku = new Integer[9][9];
                        }
                    }
                }
                //System.out.println(i+" "+ currentLine);
                i++;
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        SudokuSolver solver = new SudokuSolver();
        solver.readFile("sudoku_testcase.txt");
    }
    
}
