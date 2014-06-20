package org.intermine.like.request;

/**
 * To get the pairwise ratings from a result.
 *
 * @author selma
 *
 */
public interface PairwiseRatingInterface
{
    /**
    *
    * @return the object Id of a pairwise rating
    */
    int getObjectId(); // one of input values

    /**
     *
     * @return the rating for the pairwise rating
     */
    int getRating();
}
