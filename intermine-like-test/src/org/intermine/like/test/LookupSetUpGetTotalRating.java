package org.intermine.like.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class LookupSetUpGetTotalRating {
    @Test
    public void test() throws IOException, ClassNotFoundException {
        Map<Integer, Map<Integer, Map<Integer, Integer>>> smallSimilarSet =
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
        for (Map.Entry<Integer, Map<Integer, Map<Integer, Integer>>> entry : smallSimilarSet.entrySet()) {
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

        int[][] res = LookupGetTotalRating.getTotalRating(smallSimilarSet);

        System.out.print("\nres:");
        for (int i =0; i < res.length; i++) {
            System.out.print("\n");
            for (int j = 0; j < res[0].length; j++) {
                System.out.print(res[i][j] +  " ");
            }
        }

        int[][] smallResult = new int[2][2];
        smallResult[0][0] = 222;
        smallResult[0][1] = 107;
        smallResult[1][0] = 111;
        smallResult[1][1] = 101;

        System.out.print("\n\nsmallResult:");
        for (int i =0; i < smallResult.length; i++) {
            System.out.print("\n");
            for (int j = 0; j < smallResult[0].length; j++) {
                System.out.print(smallResult[i][j] +  " ");
            }
        }

        assertEquals(res, smallResult);
    }

}
