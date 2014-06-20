package org.intermine.like.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.intermine.like.request.PairwiseRating;

public class LookupResult {

    private int[][] totalRatingSet;
    private Map<Integer, Map<Integer, Map<Integer, Integer>>> similarGenes;
    private Map<Integer, Map<Integer, ArrayList<Integer>>> commonItems;

    /**
     * A LikeResult can be empty at first and then filled with setters.
     */
    public LookupResult() {
    }

    /**
     *
     * @param totalRatingSet contains the result genes and their total ratings ordered from
     * highest to lowest
     * @param mostSimilarGenes contains the same information like totalRatingSet plus information
     * about pairwise similarities between searched and result genes:
     * "Map<resultGeneID, Map<totalRating< Map<searchedGeneID, pairwiseRating>>>"
     * @param commonItems contains the common items (e.g. pathway IDs), which the result genes
     * have in common with the searched genes (pairwise):
     * "Map<resultGeneID, Map<searchedGeneID, ListOfCommonItems>>"
     */
    public LookupResult(int[][] totalRatingSet,
            Map<Integer, Map<Integer, Map<Integer, Integer>>> mostSimilarGenes,
            Map<Integer, Map<Integer, ArrayList<Integer>>> commonItems) {
        this.totalRatingSet = totalRatingSet;
        this.similarGenes = mostSimilarGenes;
        this.commonItems = commonItems;
    }

    /**
     *
     * @param totalRatingSet contains the result genes and their total ratings ordered from
     * highest to lowest
     */
    public void setTotalRatingSet(int[][] totalRatingSet) {
        this.totalRatingSet = totalRatingSet;
    }

    /**
     *
     * @param mostSimilarGenes contains the same information like totalRatingSet plus information
     * about pairwise similarities between searched and result genes:
     * "Map<resultGeneID, Map<totalRating< Map<searchedGeneID, pairwiseRating>>>"
     */
    public void setMostsimilarGenes(
            Map<Integer, Map<Integer, Map<Integer, Integer>>> mostSimilarGenes) {
        this.similarGenes = mostSimilarGenes;
    }

    /**
     *
     * @param commonItems contains the common items (e.g. pathway IDs), which the result genes
     * have in common with the searched genes (pairwise):
     * "Map<resultGeneID, Map<searchedGeneID, ListOfCommonItems>>"
     */
    public void setCommonItems(Map<Integer, Map<Integer, ArrayList<Integer>>> commonItems) {
        this.commonItems = commonItems;
    }

    /**
     *
     * @return the result genes and their total ratings ordered from
     * highest to lowest
     */
    public int[][] getTotalRatingSet() {
        return totalRatingSet;
    }

    /**
     *
     * @return contains the same information like totalRatingSet plus information
     * about pairwise similarities between searched and result genes:
     * "Map<resultGeneID, Map<totalRating< Map<searchedGeneID, pairwiseRating>>>"
     */
    public Map<Integer, Map<Integer, Map<Integer, Integer>>> getsimilarGenes() {
        return similarGenes;
    }

    /**
     *
     * @return contains the common items (e.g. pathway IDs), which the result genes
     * have in common with the searched genes (pairwise):
     * "Map<resultGeneID, Map<searchedGeneID, ListOfCommonItems>>"
     */
    public Map<Integer, Map<Integer, ArrayList<Integer>>> getCommonItems() {
        return commonItems;
    }

    public int[] getRatedIds() {
        int[] ratedIds = new int[totalRatingSet.length];
        for (int i = 0; i < totalRatingSet.length; i++) {
            ratedIds[i] = totalRatingSet[i][0];
        }
        return ratedIds;
    }

    public int getRatingFor(int resultId) {
        int rating = 0;
        for (int i = 0; i < totalRatingSet.length; i++) {
            if (totalRatingSet[i][0] == resultId) {
                rating = totalRatingSet[i][1];
                break;
            }
        }
        return rating;
    }

    public ArrayList<PairwiseRating> getPairwiseRatings(int resultId) {
        ArrayList<PairwiseRating> pairwiseRatings = new ArrayList<PairwiseRating>();

        for (Map.Entry<Integer, Map<Integer, Map<Integer, Integer>>>
        entry : similarGenes.entrySet()) {
            if (entry.getKey() == resultId) {
                Map<Integer, Map<Integer, Integer>> val = entry.getValue();
                for (Map.Entry<Integer, Map<Integer, Integer>> entry2 : val.entrySet()) {
                    Map<Integer, Integer> ratings = entry2.getValue();
                    for (Map.Entry<Integer, Integer> entry3 : ratings.entrySet()) {
                        PairwiseRating singleRating =
                                new PairwiseRating(entry3.getKey(), entry3.getValue());
                        pairwiseRatings.add(singleRating);
                    }
                }
            }
        }
        return pairwiseRatings;
    }

    /**
    *
    * @param resultId one specific gene ID of the result genes.
    * @return all searched genes, which are similar to the resultGene and their common items.
    */
    public Map<Integer, ArrayList<Integer>> getCommonItems(Integer resultId) {
        Map<Integer, ArrayList<Integer>> why = new HashMap<Integer, ArrayList<Integer>>();

        for (Map.Entry<Integer, Map<Integer, ArrayList<Integer>>> entry : commonItems.entrySet()) {
            if (entry.getKey().equals(resultId)) {
                why = entry.getValue();
            }
        }
        return why;
    }
}
