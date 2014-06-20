package org.intermine.like.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.naming.ConfigurationException;

import org.intermine.like.request.LikeRequest;
import org.intermine.like.request.LikeService;
import org.intermine.like.response.LikeResult;

/**
 * To test the RunTime.
 *
 * @author selma
 *
 */
public final class TestClass
{

    private TestClass() {
        // Don't.
    }

    /**
     *
     * @param args args
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws ConfigurationException if a matrix is not found in the db.
     */
    public static void main(String[] args)
        throws ConfigurationException, IOException, ClassNotFoundException {

        LikeService s = new LikeService();
        LikeRequest reg = new LikeRequest();
        reg.setIDs(1007396, 1204707, 1035230);
        LikeResult result = s.search(reg);

        int[][] totalRatingSet = result.getTotalRatingSet();
        Map<Integer, Map<Integer, Map<Integer, Integer>>> similarSet = result.getsimilarGenes();

        System.out.print("\ntotalRatingSet:\n");
        for (int i = 0; i < totalRatingSet.length; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print(totalRatingSet[i][j] + " ");
            }
            System.out.print("\n");
        }

        System.out.print("\nsimilarSet:\n");
        for (int i = 0; i < totalRatingSet.length; i++) {
            Map<Integer, Map<Integer, Integer>> tmp = similarSet.get(totalRatingSet[i][0]);
            for (Map.Entry<Integer, Map<Integer, Integer>> entry2 : tmp.entrySet()) {
                System.out.print(entry2.getKey() + " because: ");
                Map<Integer, Integer> tmp2 = entry2.getValue();
                for (Map.Entry<Integer, Integer> entry3 : tmp2.entrySet()) {
                    System.out.print(entry3.getKey() + " with rating " + entry3.getValue() + "; ");
                }
            }
            System.out.print("\n");
        }

        Map<Integer, ArrayList<Integer>> result1 = result.getCommonItems(1038571);
        System.out.print("\nklick on 1038571: \n");
        for (Map.Entry<Integer, ArrayList<Integer>> entry : result1.entrySet()) {
            System.out.print(entry.getKey() + " " + entry.getValue() + "\n");
        }

//        Map<Integer, ArrayList<Integer>> result2 = result.getCommonItems(1112303);
//        System.out.print("\nklick on 1112303: \n");
//        for (Map.Entry<Integer, ArrayList<Integer>> entry : result2.entrySet()) {
//            System.out.print(entry.getKey() + " " + entry.getValue() + "\n");
//        }
//        Map<Integer, ArrayList<Integer>> result3 = result.getCommonItems(1069318);
//        System.out.print("\nklick on 1069318: \n");
//        for (Map.Entry<Integer, ArrayList<Integer>> entry : result3.entrySet()) {
//            System.out.print(entry.getKey() + " " + entry.getValue() + "\n");
//        }
//        Map<Integer, ArrayList<Integer>> result4 = result.getCommonItems(1204707);
//        System.out.print("\nklick on 1204707: \n");
//        for (Map.Entry<Integer, ArrayList<Integer>> entry : result4.entrySet()) {
//            System.out.print(entry.getKey() + " " + entry.getValue() + "\n");
//        }
//        Map<Integer, ArrayList<Integer>> result5 = result.getCommonItems(1007396);
//        System.out.print("\nklick on 1007396: \n");
//        for (Map.Entry<Integer, ArrayList<Integer>> entry : result5.entrySet()) {
//            System.out.print(entry.getKey() + " " + entry.getValue() + "\n");
//        }
    }

}
