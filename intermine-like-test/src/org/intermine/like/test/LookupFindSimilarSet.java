package org.intermine.like.test;

import java.util.HashMap;
import java.util.Map;

import org.intermine.Coordinates;
import org.intermine.like.request.MatrixStore;

public class LookupFindSimilarSet {
    // The gene ID is always in column and row zero
    private static final int SUBJECT_ID_COLUMN = 0;

    public static Map<Integer, Map<Integer, Map<Integer, Integer>>> findSimilarSet(Map<Coordinates, Integer> addedMat) {
        // <resultId, <totalRating, <searchedId, pairwiseRating>>>
        Map<Integer, Map<Integer, Map<Integer, Integer>>> similarSet =
                new HashMap<Integer, Map<Integer, Map<Integer, Integer>>>();

        // load list of all gene Ids
//////////// only for the tests!
//        Map<Coordinates, Integer> allGeneIds = store.getSimilarityMatrix("0", "ALL");
        Map<Coordinates, Integer> allGeneIds = new HashMap<Coordinates, Integer>();
        allGeneIds.put(new Coordinates(0, 1), 111);
        allGeneIds.put(new Coordinates(0, 2), 222);
        allGeneIds.put(new Coordinates(0, 3), 333);
////////////

        for (Map.Entry<Coordinates, Integer> entry : addedMat.entrySet()) {
            int xCoordinate = entry.getKey().getKey();
            int yCoordinate = entry.getKey().getValue();

            if (yCoordinate != SUBJECT_ID_COLUMN) {
                Map<Integer, Integer> pairwiseRating = new HashMap<Integer, Integer>();
                Map<Integer, Map<Integer, Integer>> totalRating =
                        new HashMap<Integer, Map<Integer , Integer>>();
                int addRatings;
                addRatings = entry.getValue();
                if (similarSet.containsKey(allGeneIds.get(new Coordinates(0, yCoordinate)))) {
                    Map<Integer, Map<Integer, Integer>> currentRatings =
                            similarSet.get(allGeneIds.get(new Coordinates(0, yCoordinate)));
                    int currentRating = 0;
                    for (Map.Entry<Integer, Map<Integer, Integer>> currentRatingTmp
                            : currentRatings.entrySet()) {
                        currentRating = currentRatingTmp.getKey();
                        pairwiseRating = currentRatingTmp.getValue();
                    }
                    addRatings += currentRating;
                    similarSet.remove(allGeneIds.get(new Coordinates(0, yCoordinate)));
                }
                pairwiseRating.put(addedMat.get(new Coordinates(xCoordinate, 0)), entry.getValue());
                totalRating.put(addRatings, pairwiseRating);
                similarSet.put(allGeneIds.get(new Coordinates(0, yCoordinate)), totalRating);
            }
        }
        return similarSet;
    }
}
