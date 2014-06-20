package org.intermine.like.request;

import java.util.ArrayList;
import java.util.Map;

import org.intermine.Coordinates;

/**
 * Loading objects (rows) from the database.
 *
 * @author selma
 *
 */
public interface MatrixStore
{

    /**
    *
    * @param aspectNumber current aspect
    * @param geneId current searched gene Id
    * @return the row corresponding to the inputs. contains the similarity ratings for the gene
    * geneId for the aspect aspectNumber.
    */
    Map<Coordinates, Integer> getSimilarityMatrix(String aspectNumber, String geneId);

    /**
     *
     * @param aspectNumber current aspect
     * @param geneId current searched gene Id
     * @return the row corresponding to the inputs. contains the common items for the gene
    * geneId for the aspect aspectNumber.
     */
    Map<Coordinates, ArrayList<Integer>> getCommonItemsMatrix(String aspectNumber, String geneId);
}
