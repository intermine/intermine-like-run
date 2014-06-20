package org.intermine.like.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;
import java.io.PrintStream;

import org.intermine.Coordinates;
import org.junit.Test;

public class LookupSetUpAddRows {
    @Test
    public void test() throws IOException, ClassNotFoundException {

//        File file1 = new File("addedMat" + 0);
//        FileInputStream f1 = new FileInputStream(file1);
//        ObjectInputStream s1 = new ObjectInputStream(f1);
//        Map<Coordinates, Integer> addedMat = (Map<Coordinates, Integer>)s1.readObject();
//        s1.close();
//
//        File file2 = new File("SimilarityMatrix" + 1);
//        FileInputStream f2 = new FileInputStream(file2);
//        ObjectInputStream s2 = new ObjectInputStream(f2);
//        Map<Coordinates, Integer> matrix = (Map<Coordinates, Integer>)s2.readObject();
//        s2.close();

        // do calculations on server for bigMatrix

        Map<Coordinates,Integer> smallAddedMat = new HashMap<Coordinates,Integer>(){{
            put(new Coordinates(2,0),222); put(new Coordinates(2,2),50); put(new Coordinates(2,3),98) ;
        }};

        Map<Coordinates,Integer> smallMatrix = new HashMap<Coordinates,Integer>(){{
            put(new Coordinates(2,0),222); put(new Coordinates(2,1),55); put(new Coordinates(2,3),12) ;
        }};

        System.out.print("\naddedMat: \n");
        for ( int k = 0; k < 4; k++ ) {
            for ( int j = 0; j < 4; j++ ) {
                Integer val = smallAddedMat.get(new Coordinates(k,j));
                System.out.print(val + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\nmatrix: \n");
        for ( int k = 0; k < 4; k++ ) {
            for ( int j = 0; j < 4; j++ ) {
                Integer val = smallMatrix.get(new Coordinates(k,j));
                System.out.print(val + " ");
            }
            System.out.print("\n");
        }

    //    long t1 = System.currentTimeMillis();
        Map<Coordinates,Integer> res = LookupAddRows.addRows(smallAddedMat,smallMatrix);
    //    long t2 = System.currentTimeMillis();
    //    System.out.print((t2-t1) + "ms for CommonItems1 calculations" + "\n");

        System.out.print("\nres: \n");
        for ( int k = 0; k < 4; k++ ) {
            for ( int j = 0; j < 4; j++ ) {
                Integer val = res.get(new Coordinates(k,j));
                System.out.print(val + " ");
            }
            System.out.print("\n");
        }

        Map<Coordinates,Integer> smallResult = new HashMap<Coordinates,Integer>(){{
//            put(new Coordinates(0,1),111);
//            put(new Coordinates(0,2),222);
//            put(new Coordinates(0,3),333);
//            put(new Coordinates(1,0),111);
            put(new Coordinates(2,0),222);
//            put(new Coordinates(3,0),333);
//            put(new Coordinates(1,1),45);
            put(new Coordinates(2,1),55);
            put(new Coordinates(2,2),50);
            put(new Coordinates(2,3),110);
//            put(new Coordinates(3,1),56);
//            put(new Coordinates(3,2),2);
//            put(new Coordinates(3,3),66);
        }};

        System.out.print("\nsmallResult: \n");
        for ( int k = 0; k < 4; k++ ) {
            for ( int j = 0; j < 4; j++ ) {
                Integer val = smallResult.get(new Coordinates(k,j));
                System.out.print(val + " ");
            }
            System.out.print("\n");
        }

//        File file = new File("addedMat" + 1);
//        FileInputStream f = new FileInputStream(file);
//        ObjectInputStream s = new ObjectInputStream(f);
//        Map<Coordinates,Integer> result = (Map<Coordinates,Integer>)s.readObject();
//        s.close();

        assertEquals(res, smallResult);
    }
}
