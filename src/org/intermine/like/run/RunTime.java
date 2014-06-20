package org.intermine.like.run;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.intermine.Coordinates;
import org.intermine.like.request.MatrixStore;
import org.intermine.like.response.LikeResult;
import org.intermine.like.run.utils.Methods;

/**
 * Perform the query and print the rows of results.
 *
 * @author selma
 *
 */
public final class RunTime
{

    private RunTime() {
        // Don't.
    }

    /**
     *
     * @param store interface MatrixStore
     * @param options properties from the configuration file
     * @param searchedGeneIDs command line arguments
     * @return a list of the similar genes with their ratings. There is the total rating and
     * the pairwise ratings. E.g.:
     * searched gene IDs: 111, 222, 333
     * similar gene IDs:  total rating:  pairwise ratings:
     *         999              9         4 from 111; 3 from 222; 2 from 333
     *         888              8         4 from 111; 4 from 222;
     *         777              7         7 from 222;
     *         666              6         4 from 222; 2 from 333;
     *         000              0         has nothing in common with any of the searched genes.
     *
     * The list is ordered from highest to lowest regarding to the total rating.
     * This answers the question: How similar are the searched and the result genes?
     * Also the result contains the pairwise common item IDs.
     * This answers the question: Why are the searched and the result genes similar?
     */
    public static LikeResult calculate(MatrixStore store, Properties options,
            Integer[] searchedGeneIDs) {
        long t1 = System.currentTimeMillis();
        // read properties
        Map<Coordinates, String> views = Methods.getProperties(options);
        long t2 = System.currentTimeMillis();
        System.out.print((t2 - t1) + "ms to read the property file" + "\n");

        // Add similarity matrices
        Map<Coordinates, Integer> addedMat = Methods.addMatrices(store, views, searchedGeneIDs);

        long t6 = System.currentTimeMillis();
        Map<Integer, Map<Integer, Map<Integer, Integer>>> similarSet =
                Methods.findSimilarSet(store, addedMat);

//        File similarityMatrix = new File("similarSet");
//        FileOutputStream f = new FileOutputStream(similarityMatrix);
//        ObjectOutputStream s = new ObjectOutputStream(f);
//        s.writeObject(similarSet);
//        s.close();

        int[][] totalRatingSet = Methods.getTotalRating(similarSet);

        totalRatingSet = Methods.order(totalRatingSet);

//        File order1 = new File("order");
//        FileOutputStream f1 = new FileOutputStream(order1);
//        ObjectOutputStream s1 = new ObjectOutputStream(f1);
//        s1.writeObject(similarSet);
//        s1.close();

        long t7 = System.currentTimeMillis();
        System.out.print((t7 - t6) + "ms to find the most similar genes to the searchedGenIDs\n");
        addedMat = new HashMap<Coordinates, Integer>();

        long t9 = System.currentTimeMillis();

        Map<Coordinates, ArrayList<Integer>> addedCommonMat =
                Methods.addCommonMat(store, views, searchedGeneIDs);

        Map<Integer, Map<Integer, ArrayList<Integer>>> commonItems =
                Methods.getCommonItems(store, addedCommonMat);
        long t10 = System.currentTimeMillis();
        System.out.print((t10 - t9) + "ms to find the common items " + "\n");

//        File similarityMatrix3 = new File("commonItems");
//        FileOutputStream f3 = new FileOutputStream(similarityMatrix3);
//        ObjectOutputStream s3 = new ObjectOutputStream(f3);
//        s3.writeObject(commonItems);
//        s3.close();

        long t11 = System.currentTimeMillis();
        System.out.print("\n-> " + (t11 - t1) + "ms for the run time calculations" + "\n");

// Output //
        LikeResult result = new LikeResult(totalRatingSet, similarSet, commonItems);

//        Integer[][] resultGenes = result.getMostSimilarGenes();
//        File similarityMatrix2 = new File("resultGenes");
//        FileOutputStream f2 = new FileOutputStream(similarityMatrix2);
//        ObjectOutputStream s2 = new ObjectOutputStream(f2);
//        s2.writeObject(resultGenes);
//        s2.close();
//
//        Map<Integer, Map<Integer, ArrayList<Integer>>> resultItems = result.getCommonItems();
//        File similarityMatrix3 = new File("resultItems");
//        FileOutputStream f3 = new FileOutputStream(similarityMatrix3);
//        ObjectOutputStream s3 = new ObjectOutputStream(f3);
//        s3.writeObject(resultItems);
//        s3.close();

        return result;
    }
}
