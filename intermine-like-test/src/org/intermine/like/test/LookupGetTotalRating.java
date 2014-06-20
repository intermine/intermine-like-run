package org.intermine.like.test;

import java.util.Map;

public class LookupGetTotalRating {
    public static int[][] getTotalRating(
            Map<Integer, Map<Integer, Map<Integer, Integer>>> similarSet) {
        int[][] totalRatingSet = new int[similarSet.size()][2];
        int count = 0;
        for (Map.Entry<Integer, Map<Integer, Map<Integer, Integer>>>
        entry : similarSet.entrySet()) {
            // Save the gene ID
            totalRatingSet[count][0] = entry.getKey();
            Map<Integer, Map<Integer, Integer>> tmp = entry.getValue();
            for (Map.Entry<Integer, Map<Integer, Integer>> entry2 : tmp.entrySet()) {
                // Save the corresponding total rating
                totalRatingSet[count][1] = entry2.getKey();
            }
            count += 1;
        }
        return totalRatingSet;
    }
}
