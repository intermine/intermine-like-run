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

public class LookupSetUpGetCommonItems {
    @Test
    public void test() throws IOException, ClassNotFoundException {

//        File file1 = new File("addedCommonMat" + 1);
//        FileInputStream f1 = new FileInputStream(file1);
//        ObjectInputStream s1 = new ObjectInputStream(f1);
//        Map<Coordinates,ArrayList<Integer>> matrix = (Map<Coordinates,ArrayList<Integer>>)s1.readObject();
//        s1.close();
//
//        Integer[] testSet = {1032942,1455341,1173946};

        // do calculations on server for bigMatrix

        Map<Coordinates,ArrayList<Integer>> smallMatrix = new HashMap<Coordinates,ArrayList<Integer>>(){{
//            put(new Coordinates(0,1),new ArrayList<Integer>(){{add(111);}});
//            put(new Coordinates(0,2),new ArrayList<Integer>(){{add(222);}});
//            put(new Coordinates(0,3),new ArrayList<Integer>(){{add(333);}});
            put(new Coordinates(1,0),new ArrayList<Integer>(){{add(111);}});
            put(new Coordinates(2,0),new ArrayList<Integer>(){{add(222);}});
            put(new Coordinates(3,0),new ArrayList<Integer>(){{add(333);}});
            put(new Coordinates(1,1),new ArrayList<Integer>(){{add(2);add(1);add(8);}});
            put(new Coordinates(1,2),new ArrayList<Integer>(){{add(3);}});
            put(new Coordinates(2,1),new ArrayList<Integer>(){{add(1);}});
            put(new Coordinates(2,2),new ArrayList<Integer>(){{add(1);add(6);}});
//            put(new Coordinates(2,3),new ArrayList<Integer>(){{add(7);}});
//            put(new Coordinates(3,3),new ArrayList<Integer>(){{add(3);add(5);}});
        }};

//        Integer[] smallTestSet = {111};

        System.out.print("\nsmallMatrix: \n");
        for ( int k = 0; k < 4; k++ ) {
            for ( int j = 0; j < 4; j++ ) {
                ArrayList<Integer> val = smallMatrix.get(new Coordinates(k,j));
                System.out.print(val + " ");
            }
            System.out.print("\n");
        }

    //    long t1 = System.currentTimeMillis();
        Map<Integer,Map<Integer,ArrayList<Integer>>> res = LookupGetCommonItems.getCommonItems(smallMatrix);
    //    long t2 = System.currentTimeMillis();
    //    System.out.print((t2-t1) + "ms for CommonItems1 calculations" + "\n");

        System.out.print("\nres (calculated result): \n");
        for (Map.Entry<Integer,Map<Integer,ArrayList<Integer>>> entry : res.entrySet()) {
            System.out.print(entry.getKey() + " ");
            Map<Integer,ArrayList<Integer>> val2 = entry.getValue();
            for (Map.Entry<Integer,ArrayList<Integer>> entry2 : val2.entrySet()){
                System.out.print(entry2.getKey() + " " + entry2.getValue() + " ");
            }
            System.out.print("\n");
        }

        Map<Integer,Map<Integer,ArrayList<Integer>>> smallResult = new HashMap<Integer,Map<Integer,ArrayList<Integer>>> (){{
            put(222,new HashMap<Integer,ArrayList<Integer>>(){{
                put(222, new ArrayList<Integer>(){{add(1);add(6);}});
                put(111, new ArrayList<Integer>(){{add(3);}});}});
            put(111, new HashMap<Integer,ArrayList<Integer>>(){{
                put(222, new ArrayList<Integer>(){{add(1);}});
                put(111, new ArrayList<Integer>(){{add(2);add(1);add(8);}});}});
        }};

        System.out.print("\nresult (correct result): \n");
        for (Map.Entry<Integer,Map<Integer,ArrayList<Integer>>> entry : smallResult.entrySet()) {
            System.out.print(entry.getKey() + " ");
            Map<Integer,ArrayList<Integer>> val2 = entry.getValue();
            for (Map.Entry<Integer,ArrayList<Integer>> entry2 : val2.entrySet()){
                System.out.print(entry2.getKey() + " " + entry2.getValue() + " ");
            }
            System.out.print("\n");
        }

//        File file = new File("commonItems");
//        FileInputStream f = new FileInputStream(file);
//        ObjectInputStream s = new ObjectInputStream(f);
//        Map<Integer,Map<Integer,ArrayList<Integer>>> result = (Map<Integer,Map<Integer,ArrayList<Integer>>>)s.readObject();
//        s.close();

        assertEquals(res, smallResult);
    }
}
