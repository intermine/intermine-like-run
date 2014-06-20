package org.intermine.like.request;

/**
 * the pairwise rating between two genes
 *
 * @author selma
 *
 */
public class PairwiseRating implements PairwiseRatingInterface
{
    int objectId;
    int rating;
    int[] commonItems;

/**
 * constructor
 */
    public PairwiseRating() {

    }

    /**
     *
     * @param objectId a gene Id
     * @param rating the gene Ids rating
     */
    public PairwiseRating(Integer objectId, Integer rating) {
        this.objectId = objectId;
        this.rating = rating;
    }

    @Override
    public int getObjectId() {
        return objectId;
    }

    @Override
    public int getRating() {
        return rating;
    }
}
