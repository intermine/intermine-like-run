package org.intermine.like.request;

import java.io.IOException;
import java.util.Properties;

import javax.naming.ConfigurationException;

import org.intermine.like.response.LikeResult;
import org.intermine.like.run.RunTime;
import org.intermine.like.run.utils.Methods;

/**
 * Perform the query and print the rows of results.
 *
 * @author selma
 *
 */
public final class LikeService
{

    private MatrixStore store;
    private Properties options;

    /**
     * constructor
     */
    public LikeService() {
    }

    /**
     *
     * @param store interface MatrixStore
     * @param allproperties the properties from the configuration file
     * @return LikeService with the LIKE properties
     */
    public static LikeService getInstance(MatrixStore store, Properties allproperties) {
        LikeService instance = new LikeService();
        instance.store = store;
        instance.options = Methods.getPropertiesStartingWith("query.", allproperties);
        return instance;
    }

    /**
     * Does all the run time calculations: Perform the query and print the rows of results.
     *
     * @param reg delivers the gene IDs the user wants to search for in the database.
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
     *
     * @throws ClassNotFoundException because we need to look for x and it might not be there.
     * @throws IOException if the foo resource is not frobbable.
     * @throws ConfigurationException if matrices are not found in the db.
     */
    public LikeResult search(LikeRequest reg) throws IOException, ClassNotFoundException,
    ConfigurationException {
        Integer[] searchedGenIDs = reg.getIDs();
        LikeResult res = RunTime.calculate(store, options, searchedGenIDs);
        return res;
    }
}
