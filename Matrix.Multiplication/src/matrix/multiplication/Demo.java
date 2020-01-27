/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matrix.multiplication;

import java.util.Random;
import matrix.multiplication.support.ParallelMatrixMultiplier;
import matrix.multiplication.support.SequentialMatrixMultiplier;

/**
 *
 * @author evllaço
 */
public class Demo {

    public static void main(String[] args) {

        Random random = new Random();
        Matrix m1 = getRandomMatrix(300, 300, random);
        Matrix m2 = getRandomMatrix(300, 300, random);

        Matrix m1b = new Matrix(m1);
        Matrix m2b = new Matrix(m2);

        long start = System.currentTimeMillis();
        Matrix result1 = new SequentialMatrixMultiplier().multiply(m1, m2);
        long end = System.currentTimeMillis();

        System.out.println("Sequential: " + (end - start));
        System.out.println();
        System.out.println(result1);
        System.out.println();

        start = System.currentTimeMillis();
        Matrix result1b = new ParallelMatrixMultiplier().multiply(m1b, m2b);
        end = System.currentTimeMillis();

        System.out.println("Parallel: " + (end - start));
        System.out.println();
        System.out.println(result1b);
        System.out.println();

        System.out.println("Same results: " + result1.equals(result1b));
    }

    private static Matrix getRandomMatrix(int rows, int cols, Random random) {

        Matrix m = new Matrix(rows, cols);

        for (int x = 0; x < cols; ++x) {
            for (int y = 0; y < rows; ++y) {
                m.set(x, y, random.nextDouble());
            }
        }

        return m;

        /*
         Matrix m1 = new Matrix(2,2);
        
         m1.set(0, 0, 1.0);
         m1.set(0, 1, 2.0);
         m1.set(1, 0, 3.0);
         m1.set(1, 1, 4.0);
        
         Matrix m2 = new Matrix(m1);
               
         System.out.println(m1);
         System.out.println();
         System.out.println(m2);
        
         MatrixMultiplier multiplier = new SequentialMatrixMultiplier();
         long start = System.nanoTime();
         Matrix result = multiplier.multiply(m2, m2);
         long end = System.nanoTime();
         System.out.println();        
         System.out.println("Sequential: "+ (end-start));
         System.out.println();
         System.out.println(result);
        
         MatrixMultiplier multiplier1 = new ParallelMatrixMultiplier();
         start = System.nanoTime();
         result = multiplier1.multiply(m2, m2);
         end = System.nanoTime();
        
         System.out.println();  
         System.out.println();  
         System.out.println("Parallel: "+ (end-start));
        
        
          
        
         System.out.println();
         System.out.println(result); */
    }
}
