package org.intermine.like.request;

/**
 * Setting and Getting the gene IDs the users wants to search for in the database.
 *
 * @author selma
 *
 */
public class LikeRequest
{
    private Integer[] searchedGenes = null;

    /**
     * a LikeRequest can be empty
     */
    public LikeRequest() {
    }

    /**
     * Set the gene IDs the users wants to search for in the database.
     * @param searchedGenes is a list of InterMine gene IDs.
     */
    public void setIDs(Integer... searchedGenes) {
        this.searchedGenes = searchedGenes;
    }

    /**
     *
     * @return the gene IDs the users wants to search for in the database.
     */
    public Integer[] getIDs() {
        return searchedGenes;
    }

    /**
     * Add ID to id list used to search the matrix at runtime
     *
     * @param id ID of the object to add
     */
    public void addID(Integer id) {
        if (searchedGenes == null) {
            searchedGenes = new Integer[1];
            searchedGenes[0] = id;
        } else {
            Integer[] newIDs = new Integer[searchedGenes.length + 1];
            for (int i = 0; i < searchedGenes.length; i++) {
                newIDs[i] = searchedGenes[i];
            }
            newIDs[searchedGenes.length] = id;
            searchedGenes = newIDs;
        }
    }
}
