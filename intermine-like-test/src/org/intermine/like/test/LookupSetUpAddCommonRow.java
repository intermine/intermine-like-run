package org.intermine.like.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.intermine.Coordinates;
import org.junit.Test;

public class LookupSetUpAddCommonRow {
    @Test
    public void test() throws IOException, ClassNotFoundException {

//        File file1 = new File("addedCommonMat" + 0);
//        FileInputStream f1 = new FileInputStream(file1);
//        ObjectInputStream s1 = new ObjectInputStream(f1);
//        Map<Coordinates,ArrayList<Integer>> addedMat = (Map<Coordinates,ArrayList<Integer>>)s1.readObject();
//        s1.close();
//
//        File file2 = new File("CommonItems" + 1);
//        FileInputStream f2 = new FileInputStream(file2);
//        ObjectInputStream s2 = new ObjectInputStream(f2);
//        Map<Coordinates,ArrayList<Integer>> matrix = (Map<Coordinates,ArrayList<Integer>>)s2.readObject();
//        s2.close();

        // do calculations on server for bigMatrix

        Map<Coordinates,ArrayList<Integer>>  smallAddedMat = new HashMap<Coordinates,ArrayList<Integer>> (){{
//            put(new Coordinates(0,1),new ArrayList<Integer>(){{add(111);}});
//            put(new Coordinates(0,2),new ArrayList<Integer>(){{add(222);}});
//            put(new Coordinates(0,3),new ArrayList<Integer>(){{add(333);}});
            put(new Coordinates(1,0),new ArrayList<Integer>(){{add(111);}});
            put(new Coordinates(2,0),new ArrayList<Integer>(){{add(222);}});
            put(new Coordinates(3,0),new ArrayList<Integer>(){{add(333);}});
            put(new Coordinates(1,1),new ArrayList<Integer>(){{add(2);add(1);}});
            put(new Coordinates(1,2),new ArrayList<Integer>(){{add(1);}});
            put(new Coordinates(2,1),new ArrayList<Integer>(){{add(1);}});
            put(new Coordinates(2,2),new ArrayList<Integer>(){{add(1);}});
            put(new Coordinates(3,3),new ArrayList<Integer>(){{add(3);}});
        }};

        Map<Coordinates,ArrayList<Integer>>  smallMatrix = new HashMap<Coordinates,ArrayList<Integer>> (){{
//            put(new Coordinates(0,1),new ArrayList<Integer>(){{add(111);}});
//            put(new Coordinates(0,2),new ArrayList<Integer>(){{add(222);}});
//            put(new Coordinates(0,3),new ArrayList<Integer>(){{add(333);}});
            put(new Coordinates(1,0),new ArrayList<Integer>(){{add(111);}});
            put(new Coordinates(2,0),new ArrayList<Integer>(){{add(222);}});
            put(new Coordinates(3,0),new ArrayList<Integer>(){{add(333);}});
            put(new Coordinates(1,1),new ArrayList<Integer>(){{add(8);}});
            put(new Coordinates(2,3),new ArrayList<Integer>(){{add(7);}});
            put(new Coordinates(2,2),new ArrayList<Integer>(){{add(6);}});
            put(new Coordinates(3,3),new ArrayList<Integer>(){{add(5);}});
        }};

        System.out.print("\naddedMat: \n");
        for ( int k = 0; k < 4; k++ ) {
            for ( int j = 0; j < 4; j++ ) {
                ArrayList<Integer> val = smallAddedMat.get(new Coordinates(k,j));
                System.out.print(val + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\nmatrix: \n");
        for ( int k = 0; k < 4; k++ ) {
            for ( int j = 0; j < 4; j++ ) {
                ArrayList<Integer> val = smallMatrix.get(new Coordinates(k,j));
                System.out.print(val + " ");
            }
            System.out.print("\n");
        }

    //    long t1 = System.currentTimeMillis();
        Map<Coordinates,ArrayList<Integer>> res = LookupAddCommonRow.addCommonRow(smallAddedMat,smallMatrix);
    //    long t2 = System.currentTimeMillis();
    //    System.out.print((t2-t1) + "ms for CommonItems1 calculations" + "\n");
//        addedMat = new HashMap<Coordinates,ArrayList<Integer>>();
//        matrix = new HashMap<Coordinates,ArrayList<Integer>>();

        System.out.print("\nres: \n");
        for ( int k = 0; k < 4; k++ ) {
            for ( int j = 0; j < 4; j++ ) {
                ArrayList<Integer> val = res.get(new Coordinates(k,j));
                System.out.print(val + " ");
            }
            System.out.print("\n");
        }

        Map<Coordinates,ArrayList<Integer>> smallResult = new HashMap<Coordinates,ArrayList<Integer>>(){{
//            put(new Coordinates(0,1),new ArrayList<Integer>(){{add(111);}});
//            put(new Coordinates(0,2),new ArrayList<Integer>(){{add(222);}});
//            put(new Coordinates(0,3),new ArrayList<Integer>(){{add(333);}});
            put(new Coordinates(1,0),new ArrayList<Integer>(){{add(111);}});
            put(new Coordinates(2,0),new ArrayList<Integer>(){{add(222);}});
            put(new Coordinates(3,0),new ArrayList<Integer>(){{add(333);}});
            put(new Coordinates(1,1),new ArrayList<Integer>(){{add(2);add(1);add(8);}});
            put(new Coordinates(1,2),new ArrayList<Integer>(){{add(1);}});
            put(new Coordinates(2,1),new ArrayList<Integer>(){{add(1);}});
            put(new Coordinates(2,2),new ArrayList<Integer>(){{add(1);add(6);}});
            put(new Coordinates(2,3),new ArrayList<Integer>(){{add(7);}});
            put(new Coordinates(3,3),new ArrayList<Integer>(){{add(3);add(5);}});
        }};

        System.out.print("\nsmallResult: \n");
        for ( int k = 0; k < 4; k++ ) {
            for ( int j = 0; j < 4; j++ ) {
                ArrayList<Integer> val = smallResult.get(new Coordinates(k,j));
                System.out.print(val + " ");
            }
            System.out.print("\n");
        }

//        File file = new File("addedCommonMat" + 1);
//        FileInputStream f = new FileInputStream(file);
//        ObjectInputStream s = new ObjectInputStream(f);
//        Map<Coordinates,ArrayList<Integer>> result = (Map<Coordinates,ArrayList<Integer>>)s.readObject();
//        s.close();

        assertEquals(res, smallResult);
    }
}
