package org.intermine.like.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

import org.intermine.Coordinates;
import org.junit.Test;

public class LookupSetUpFindSimilarSet {
    @Test
    public void test() throws IOException, ClassNotFoundException {

//        File file1 = new File("addedMat" + 1);
//        FileInputStream f1 = new FileInputStream(file1);
//        ObjectInputStream s1 = new ObjectInputStream(f1);
//        Map<Coordinates,Integer> matrix = (Map<Coordinates,Integer>)s1.readObject();
//        s1.close();

//        Integer[] testSet = {1032942,1455341,1173946};

        // do calculations on server for bigMatrix

        Map<Coordinates,Integer> smallMatrix = new HashMap<Coordinates,Integer>(){{
//            put(new Coordinates(0,1),111);
//            put(new Coordinates(0,2),222);
//            put(new Coordinates(0,3),333);
            put(new Coordinates(1,0),111);
            put(new Coordinates(2,0),222);
            put(new Coordinates(3,0),333);
            put(new Coordinates(1,1),45);
            put(new Coordinates(2,2),105);
//            put(new Coordinates(2,3),110);
            put(new Coordinates(3,1),56);
            put(new Coordinates(3,2),2);
//            put(new Coordinates(3,3),66);
        }};

        Integer[] smallTestSet = {333};

        System.out.print("\nmatrix: \n");
        for ( int k = 0; k < 4; k++ ) {
            for ( int j = 0; j < 4; j++ ) {
                Integer val = smallMatrix.get(new Coordinates(k,j));
                System.out.print(val + " ");
            }
            System.out.print("\n");
        }

    //    long t1 = System.currentTimeMillis();
        Map<Integer, Map<Integer, Map<Integer, Integer>>>  res =
                LookupFindSimilarSet.findSimilarSet(smallMatrix);
    //    long t2 = System.currentTimeMillis();
    //    System.out.print((t2-t1) + "ms for CommonItems1 calculations" + "\n");

        System.out.print("\nres (calculated result): ");
        for (Map.Entry<Integer, Map<Integer, Map<Integer, Integer>>> entry : res.entrySet()) {
            System.out.print("\n" + entry.getKey() + " because of: ");
            Map<Integer, Map<Integer, Integer>> val = entry.getValue();
            for (Map.Entry<Integer, Map<Integer, Integer>> entry2 : val.entrySet()) {
                System.out.print("total: " + entry2.getKey() + ", pairwise: ");
                Map<Integer, Integer> val2 = entry2.getValue();
                for (Map.Entry<Integer, Integer> entry3 : val2.entrySet()) {
                    System.out.print(entry3.getKey() + " with " + entry3.getValue() + ", ");
                }
            }
        }
        System.out.print("\n");

        Map<Integer, Map<Integer, Map<Integer, Integer>>> smallResult =
                new HashMap<Integer, Map<Integer, Map<Integer, Integer>>>() { {
                    put (222, new HashMap<Integer, Map<Integer, Integer>>() { {
                        put(107, new HashMap<Integer, Integer>() { {
                            put(333, 2);
                            put(222, 105); } });
                        } });
                    put(111, new HashMap<Integer, Map<Integer, Integer>>() { {
                        put(101, new HashMap<Integer, Integer>() { {
                            put(111, 45);
                            put(333, 56); } });
                        } });
        } };

        System.out.print("\nresult: ");
        for (Map.Entry<Integer, Map<Integer, Map<Integer, Integer>>> entry : smallResult.entrySet()) {
            System.out.print("\n" + entry.getKey() + " because of: ");
            Map<Integer, Map<Integer, Integer>> val = entry.getValue();
            for (Map.Entry<Integer, Map<Integer, Integer>> entry2 : val.entrySet()) {
                System.out.print("total: " + entry2.getKey() + ", pairwise: ");
                Map<Integer, Integer> val2 = entry2.getValue();
                for (Map.Entry<Integer, Integer> entry3 : val2.entrySet()) {
                    System.out.print(entry3.getKey() + " with " + entry3.getValue() + ", ");
                }
            }
        }
        System.out.print("\n");

//        File file = new File("similarSet");
//        FileInputStream f = new FileInputStream(file);
//        ObjectInputStream s = new ObjectInputStream(f);
//        Integer[][] result = (Integer[][])s.readObject();
//        s.close();

        assertEquals(res, smallResult);
    }
}
