package org.intermine.like.test;

import java.util.Map;

import org.intermine.Coordinates;

public class LookupAddRows {

    static Map<Coordinates, Integer> addRows(Map<Coordinates, Integer> addedMat,
            Map<Coordinates, Integer> normMat) {
        if (addedMat == null
                || addedMat.isEmpty()) {
            return normMat;
        }
        else {
            Integer value;
            for (Map.Entry<Coordinates, Integer> newAspect : normMat.entrySet()) {
                Coordinates coordinates = newAspect.getKey();
                value = newAspect.getValue();
                if (!addedMat.containsKey(coordinates)) {
                    addedMat.put(coordinates, value);
                }
                else {
                    if (newAspect.getKey().getValue() != 0) {
                        addedMat.put(coordinates, addedMat.get(coordinates) + value);
                    }
                }
            }
            return addedMat;
        }
    }
}
