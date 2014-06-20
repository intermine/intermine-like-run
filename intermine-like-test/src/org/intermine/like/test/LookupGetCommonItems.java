package org.intermine.like.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.intermine.Coordinates;
import org.intermine.like.request.MatrixStore;

public class LookupGetCommonItems {
    // The gene ID is always in column and row zero
    private static final int SUBJECT_ID_COLUMN = 0;
    private static final int SUBJECT_ID_ROW = 0;

    public static Map<Integer, Map<Integer, ArrayList<Integer>>> getCommonItems(
            Map<Coordinates, ArrayList<Integer>> commonMat) {

        // <resultId, <searchedId, commonItems>>
        Map<Integer, Map<Integer, ArrayList<Integer>>> commonItemsAll =
              new HashMap<Integer, Map<Integer, ArrayList<Integer>>>();

        // load list of all gene Ids
////////////only for the tests!
//        Map<Coordinates, Integer> allGeneIds = store.getSimilarityMatrix("0", "ALL");
        Map<Coordinates, Integer> allGeneIds = new HashMap<Coordinates, Integer>();
        allGeneIds.put(new Coordinates(0, 1), 111);
        allGeneIds.put(new Coordinates(0, 2), 222);
        allGeneIds.put(new Coordinates(0, 3), 333);
////////////

        for (Map.Entry<Coordinates, ArrayList<Integer>> entry : commonMat.entrySet()) {
            int xCoordinate = entry.getKey().getKey();
            int yCoordinate = entry.getKey().getValue();

            if (yCoordinate != SUBJECT_ID_COLUMN) {
                ArrayList<Integer> value = entry.getValue();
                Map<Integer, ArrayList<Integer>> commonItems;
                if (!commonItemsAll.containsKey(allGeneIds.get(new Coordinates(SUBJECT_ID_ROW, yCoordinate)))) {
                    commonItems = new HashMap<Integer, ArrayList<Integer>>();
                }
                else {
                    commonItems = commonItemsAll.get(allGeneIds.get(new Coordinates(
                            SUBJECT_ID_ROW, yCoordinate)));
                }
                commonItems.put(commonMat.get(new Coordinates(xCoordinate, SUBJECT_ID_COLUMN)).get(0), value);
                commonItemsAll.put(allGeneIds.get(new Coordinates(SUBJECT_ID_ROW, yCoordinate)), commonItems);
            }
        }
        return commonItemsAll;
    }
}
