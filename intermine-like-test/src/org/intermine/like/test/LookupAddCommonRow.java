package org.intermine.like.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.intermine.Coordinates;
import org.intermine.like.request.MatrixStore;
import org.intermine.like.run.utils.Methods;

public class LookupAddCommonRow {
    public static Map<Coordinates, ArrayList<Integer>> addCommonRow(Map<Coordinates,
            ArrayList<Integer>> addedCommonMat, Map<Coordinates, ArrayList<Integer>> commonMat) {

        if (addedCommonMat == null
                || addedCommonMat.isEmpty()) {
            return commonMat;
        }
        else {
            ArrayList<Integer> newItems;
            ArrayList<Integer> items;
            for (Map.Entry<Coordinates, ArrayList<Integer>> newAspect : commonMat.entrySet()) {
                Coordinates coordinates = newAspect.getKey();
                newItems = newAspect.getValue();
                if (!addedCommonMat.containsKey(coordinates)) {
                    addedCommonMat.put(coordinates, newItems);
                }
                // If the coordinates have been seen before
                // -> add the common items to the ArrayList
                else {
                    if (newAspect.getKey().getValue() != 0) {
                        items = addedCommonMat.get(coordinates);
                        items.addAll(newItems);
                        addedCommonMat.put(coordinates, items);
                    }
                }
            }
            return addedCommonMat;
        }
    }
}
