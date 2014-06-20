package org.intermine.like.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.intermine.like.request.PairwiseRating;
import org.junit.Test;

public class LookupSetUpResult
{
    @Test
    public void test() throws IOException, ClassNotFoundException {

        // Searched genes are 555, 666, 777 and 888

        int[][] totalRatingSet = new int[2][2];
        totalRatingSet[0][0] = 222; totalRatingSet [0][1] = 99;
        totalRatingSet[1][0] = 111; totalRatingSet [1][1] = 80;

        System.out.print("\ntotalRatingSet: \n");
        for (int i = 0; i < totalRatingSet.length; i++) {
            System.out.print(totalRatingSet[i][0] + " " + totalRatingSet[i][1] + "\n");
        }


        Map<Integer, Map<Integer, Map<Integer, Integer>>> mostSimilarGenes =
                new HashMap<Integer, Map<Integer, Map<Integer, Integer>>>() { {
                    put (222, new HashMap<Integer, Map<Integer, Integer>>() { {
                        put(99, new HashMap<Integer, Integer>() { {
                            put(555, 66);
                            put(666, 33); } });
                        } });
                    put(111, new HashMap<Integer, Map<Integer, Integer>>() { {
                        put(80, new HashMap<Integer, Integer>() { {
                            put(666, 20);
                            put(777, 40);
                            put(888, 20); } });
                        } });
        } };

        System.out.print("\nmostSimilarGenes: \n");
        for (Map.Entry<Integer, Map<Integer, Map<Integer, Integer>>> entry : mostSimilarGenes.entrySet()) {
            System.out.print(entry.getKey() + " ");
            Map<Integer, Map<Integer, Integer>> val2 = entry.getValue();
            for (Map.Entry<Integer, Map<Integer, Integer>> entry2 : val2.entrySet()) {
                Map<Integer, Integer> val3 = entry2.getValue();
                for (Map.Entry<Integer, Integer> entry3 : val3.entrySet()) {
                    System.out.print(entry3.getKey() + " " + entry3.getValue() + " ");
                }
            }
            System.out.print("\n");
        }

        Map<Integer, Map<Integer, ArrayList<Integer>>> commonItems =
                new HashMap<Integer, Map<Integer, ArrayList<Integer>>> () { {
            put(222, new HashMap<Integer, ArrayList<Integer>>() { {
                    put(222, new ArrayList<Integer>() { { add(1); add(2); add(3); } });
                    put(555, new ArrayList<Integer>() { { add(1); add(2); } });
                    put(666, new ArrayList<Integer>() { { add(1); } }); } });
            put(111, new HashMap<Integer, ArrayList<Integer>>() { {
                    put(111, new ArrayList<Integer>() { { add(4); add(5); add(6); add(7); add(8);} });
                    put(666, new ArrayList<Integer>() { { add(4); } });
                    put(777, new ArrayList<Integer>() { { add(5); add(7); } });
                    put(888, new ArrayList<Integer>() { { add(5); } }); } });
        } };

        System.out.print("\ncommonItems: \n");
        for (Map.Entry<Integer, Map<Integer, ArrayList<Integer>>> entry : commonItems.entrySet()) {
            System.out.print(entry.getKey() + " ");
            Map<Integer, ArrayList<Integer>> val2 = entry.getValue();
            for (Map.Entry<Integer, ArrayList<Integer>> entry2 : val2.entrySet()) {
                System.out.print(entry2.getKey() + " " + entry2.getValue() + " ");
            }
            System.out.print("\n");
        }


        LookupResult lookupResult = new LookupResult(totalRatingSet, mostSimilarGenes, commonItems);

// getCommonItems(int resultId)
        Map<Integer, ArrayList<Integer>> res = lookupResult.getCommonItems(222);

        System.out.print("\nres (calculated result for gene 222): \n");
        for (Map.Entry<Integer, ArrayList<Integer>> entry2 : res.entrySet()) {
            System.out.print(entry2.getKey() + " " + entry2.getValue() + " ");
        }

        Map<Integer, ArrayList<Integer>> result = new HashMap<Integer, ArrayList<Integer>> () { {
            put(222, new ArrayList<Integer>() { { add(1); add(2); add(3); } });
            put(555, new ArrayList<Integer>() { { add(1); add(2); } });
            put(666, new ArrayList<Integer>() { { add(1); } });
        } };

        System.out.print("\nresult (correct result for gene 222): \n");
        for (Map.Entry<Integer, ArrayList<Integer>> entry2 : result.entrySet()) {
            System.out.print(entry2.getKey() + " " + entry2.getValue() + " ");
        }

        assertEquals(res, result);

// getRatedIds()
        int[] ratedIds = lookupResult.getRatedIds();
        int[] ratedIdsShall = new int[2];
        ratedIdsShall[0] = 222;
        ratedIdsShall[1] = 111;

        System.out.print("\n\nratedIds: \n");
        for (int i = 0; i < ratedIds.length; i++) {
            System.out.print(ratedIds[i] + " ");
        }
        System.out.print("\nratedIdsShall: \n");
        for (int i = 0; i < ratedIdsShall.length; i++) {
            System.out.print(ratedIdsShall[i] + " ");
        }

        assertEquals(ratedIds, ratedIdsShall);

// int getRatingFor(int resultId)
        int ratingFor222 = lookupResult.getRatingFor(222);
        int ratingFor222Shall = 99;

        System.out.print("\n\nratingFor222: \n" + ratingFor222);
        System.out.print("\nratingFor222Shall: \n" + ratingFor222Shall);

        assertEquals(ratingFor222, ratingFor222Shall);

// ArrayList<PairwiseRating> getPairwiseRatings(int resultId)
        ArrayList<PairwiseRating> pairwiseRatings222 = lookupResult.getPairwiseRatings(222);
        ArrayList<PairwiseRating> pairwiseRatings222Shall = new ArrayList<PairwiseRating>() { {
            add(new PairwiseRating(666, 33));
            add(new PairwiseRating(555, 66));
        } };

        System.out.print("\n\npairwiseRatings222: \n");
        for (int i = 0; i < pairwiseRatings222.size(); i++) {
            PairwiseRating val = pairwiseRatings222.get(i);
            System.out.print(val.getObjectId() + " " + val.getRating() + " ");
        }
        System.out.print("\npairwiseRatings222Shall: \n");
        for (int i = 0; i < pairwiseRatings222Shall.size(); i++) {
            PairwiseRating val = pairwiseRatings222Shall.get(i);
            System.out.print(val.getObjectId() + " " + val.getRating() + " ");
        }

        assertEquals(pairwiseRatings222, pairwiseRatings222Shall);
    }
}
