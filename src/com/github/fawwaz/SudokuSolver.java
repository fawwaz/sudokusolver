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
    Integer         numsudokus;
    Integer[][][]   sudokus;
    Integer[][]     temp_sudoku;
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
                    numsudokus = Integer.valueOf(currentLine);
                }else{
                    if(!currentLine.trim().equals("")){
                        String[] cline  = currentLine.trim().split(" ");
                        Integer tempy   = (i-2-l)%9;
                        for (int j = 0; j < cline.length; j++) {
                            temp_sudoku[tempy][j] = Integer.valueOf(cline[j]);
                        }
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
    
    
    public void display_sudoku(Integer[][] the_sudoku){
        for (int r = 0; r < the_sudoku.length; r++) {
            if(r%3==0){
                System.out.println("+---+---+---+");
            }
            for (int c = 0; c < the_sudoku[r].length; c++) {
                if(c%3==0){
                    System.out.print("|");
                }
                System.out.print(the_sudoku[r][c]);
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("+---+---+---+");
    }
    
    private boolean isDuplicateExist(int[] tobechecked){
        for (int i = 0; i < tobechecked.length; i++) {
            for (int j = i+1; j < tobechecked.length; j++) {
                if((tobechecked[i]==tobechecked[j]) && (tobechecked[i] != 0)){
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean checkCurrentRow(Integer[][] the_sudoku,int currentrow){
        int[] tobechecked = new int[the_sudoku.length];
        for (int c = 0; c < the_sudoku.length; c++) {
            tobechecked[c] = the_sudoku[currentrow][c];
        }
        
        if(isDuplicateExist(tobechecked)){
            return true;
        }else{
            return false;
        }
    }
    
    private boolean checkCurrentColumn(Integer[][] the_sudoku,int currentcol){
        int[] tobechecked = new int[the_sudoku.length];
        for (int c = 0; c < the_sudoku.length; c++) {
            tobechecked[c] = the_sudoku[c][currentcol];
        }
        
        if(isDuplicateExist(tobechecked)){
            return true;
        }else{
            return false;
        }
    }
    
    private boolean checkCurrentRegion(Integer[][] the_sudoku,int currentrow, int currentcol){
        int[] tobechecked = new int[the_sudoku.length];
        currentrow = (currentrow / 3) *3;
        currentcol = (currentcol / 3) *3;
        int i = 0;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                tobechecked[i] = the_sudoku[currentrow+r][currentcol+c];
                i++;
            }
        }
        
        if(isDuplicateExist(tobechecked)){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean isConsistent(Integer[][] the_sudoku,int currentrow, int currentcol){
        boolean validrow = checkCurrentRow(the_sudoku, currentrow);
        boolean validcol = checkCurrentColumn(the_sudoku, currentcol);
        boolean validreg = checkCurrentRegion(the_sudoku, currentrow, currentcol);
        if(validrow && validcol && validreg){
            return true;
        }else{
            return false;
        }
    }
    
    private int getEmptyCells(Integer[][] the_sudoku,int[][] empty){
        int i = 0;
        int numemptycell = 0;
        for (int r = 0; r < the_sudoku.length; r++) {
            for (int c = 0; c < the_sudoku[r].length; c++) {
                if(the_sudoku[r][c] == 0){
                    empty[i][0] = r;
                    empty[i][1] = c;
                    numemptycell++;
                    i++;
                }
            }
        }
        return numemptycell;
    }
    
    private Integer[][] getSudoku(int num){
        Integer[][] the_sudoku = new Integer[9][9];
        for (int i = 0; i < the_sudoku.length; i++) {
            for (int j = 0; j < the_sudoku[i].length; j++) {
                the_sudoku[i][j] = sudokus[num][i][j];
            }
        }
        
        return the_sudoku;
    }
    
    public void solve_without_heuristic(Integer[][] the_sudoku){
        int[][] emptycells = new int[81][2];
        int num_emptycells = getEmptyCells(the_sudoku, emptycells);
        System.out.println("Jumlah emptycells : "+num_emptycells);
        for (int i = 0; i < num_emptycells; i++) {
            System.out.println("["+i+"] : X="+emptycells[i][0]+" Y ="+emptycells[i][1]);
        }
    }
    
    public void first_experiment(){
        Integer[][] current_sudoku;
        for (int i = 0; i < numsudokus; i++) {
            current_sudoku = getSudoku(i);
            System.out.println("Sudoku ke-"+i+" sebelum diselesaikan : ");
            display_sudoku(current_sudoku);
            
            // main utama
            solve_without_heuristic(current_sudoku);
            
            System.out.println("Sudoku ke-"+i+" setelah diselesaikan : ");        
        }
        
    }
    
    private class emptyCells{
        public int x,y;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        SudokuSolver solver = new SudokuSolver();
        solver.readFile("sudoku_testcase_easy.txt");
        solver.first_experiment();
    }
    
}

