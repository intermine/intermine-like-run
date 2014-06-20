package org.intermine.like.response;

import java.util.ArrayList;
import java.util.Map;

import org.intermine.like.request.PairwiseRating;

/**
 * for easy access to the complex results.
 *
 * @author selma
 *
 */
public interface LikeResultInterface
{
    /**
    *
    * @param totalRatingSet contains the result genes and their total ratings ordered from
    * highest to lowest
    */
    void setTotalRatingSet(int[][] totalRatingSet);
    /**
    *
    * @param mostSimilarGenes contains the same information like totalRatingSet plus information
    * about pairwise similarities between searched and result genes:
    * "Map<resultGeneID, Map<totalRating< Map<searchedGeneID, pairwiseRating>>>"
    */
    void setMostsimilarGenes(Map<Integer, Map<Integer, Map<Integer, Integer>>> mostSimilarGenes);
    /**
    *
    * @param commonItems contains the common items (e.g. pathway IDs), which the result genes
    * have in common with the searched genes (pairwise):
    * "Map<resultGeneID, Map<searchedGeneID, ListOfCommonItems>>"
    */
    void setCommonItems(Map<Integer, Map<Integer, ArrayList<Integer>>> commonItems);

    /**
     *
     * @return the result genes and their total ratings ordered from
     * highest to lowest
     */
    int[][] getTotalRatingSet();
    /**
    *
    * @return contains the same information like totalRatingSet plus information
    * about pairwise similarities between searched and result genes:
    * "Map<resultGeneID, Map<totalRating< Map<searchedGeneID, pairwiseRating>>>"
    */
    Map<Integer, Map<Integer, Map<Integer, Integer>>> getsimilarGenes();
    /**
    *
    * @return contains the common items (e.g. pathway IDs), which the result genes
    * have in common with the searched genes (pairwise):
    * "Map<resultGeneID, Map<searchedGeneID, ListOfCommonItems>>"
    */
    Map<Integer, Map<Integer, ArrayList<Integer>>> getCommonItems();

    /**
     *
     * @return list of the rated Ids
     */
    int[] getRatedIds();

    /**
     *
     * @param resId result gene id
     * @return the total rating for redId
     */
    int getRatingFor(int resId);

    /**
     *
     * @param resultId result gene id
     * @return all pairwise ratings to resultId
     */
    ArrayList<PairwiseRating> getPairwiseRatings(int resultId);

    /**
    *
    * @param resultId one specific gene ID of the result genes.
    * @return all searched genes, which are similar to the resultGene and their common items.
    */
    Map<Integer, ArrayList<Integer>> getCommonItems(Integer resultId);
}
