package org.intermine.like.run.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.intermine.Coordinates;
import org.intermine.like.request.MatrixStore;

/**
 * Methods() prepares the pre-calculated matrices regarding to the users request.
 * It is used from the RunTime.java.
 *
 * @author selma
 *
 */
public final class Methods
{

    // The gene ID is always in column and row zero
    private static final int SUBJECT_ID_COLUMN = 0;
    private static final int SUBJECT_ID_ROW = 0;

    private Methods() {
        // Don't.
    }

    /**
     * Returns all Properties in props that begin with str
     *
     * Given properties foo.bar => 1, foo.baz => 2, bar => 3, and a prefix of "foo",
     * produces the properties foo.bar => 1, foo.baz => 2
     *
     * @param str the String that the returned properties should start with
     * @param props the Properties to search through
     * @return a Properties object containing the subset of props
     */
    public static Properties getPropertiesStartingWith(String str, Properties props) {
        if (str == null) {
            throw new NullPointerException("str cannot be null, props param: " + props);
        }
        if (props == null) {
            throw new NullPointerException("props cannot be null, str param: " + str);
        }

        Properties subset = new Properties();
        Enumeration<Object> propertyEnum = props.keys();
        while (propertyEnum.hasMoreElements()) {
            String propertyName = (String) propertyEnum.nextElement();
            if (propertyName.startsWith(str)) {
                subset.put(propertyName, props.get(propertyName));
            }
        }
        return subset;
    }

    /**
     * reads the property file.
     *
     * @param prop the cofiguration file
     * @return matrix containing the configuration file information: which aspects shall be
     * calculated and how shall they be calculated
     */
    public static Map<Coordinates, String> getProperties(Properties prop) {
        Map<Coordinates, String> views = new HashMap<Coordinates, String>();

        int countViews = 0;
        // Get all the information out of the configuration file
        for (int i = 0; i < prop.size() / 4; i++) {
            if (prop.getProperty("query." + i + ".required") != null
                    && "yes".equals(prop.getProperty("query." + i + ".required"))) {
                views.put(new Coordinates(countViews, 0),
                        prop.getProperty("query." + i + ".number"));
                views.put(new Coordinates(countViews, 1), prop.getProperty("query." + i + ".id"));
                views.put(new Coordinates(countViews, 2),
                        prop.getProperty("query." + i + ".constraint"));
                views.put(new Coordinates(countViews, 3), prop.getProperty("query." + i + ".type"));
                countViews += 1;
            }
        }
        return views;
    }

    /**
     * Calculates a matrix of all searched genes in all required aspects of the similarity ratings.
     *
     * @param store from the interface MatrixStore to get access to the methods of SimilarityService
     * @param views the aspects for the configuration file
     * @param searchedGenIDs the user's genes
     * gene IDs. The rest contains the similarity ratings for one single aspect.
     * @return sparse matrix containing similarity ratings of all (and only of) the searched genes
     */
    public static Map<Coordinates, Integer> addMatrices(MatrixStore store,
            Map<Coordinates, String> views, Integer[] searchedGenIDs) {
        Map<Coordinates, Integer> addedMat = new HashMap<Coordinates, Integer>();
        for (int j = 0; j < views.size() / 4; j++) {
            String aspectNumber = views.get(new Coordinates(j, 0));
            for (int i = 0; i < searchedGenIDs.length; i++) {
                // Read in file
                String geneId = Integer.toString(searchedGenIDs[i]);
                Map<Coordinates, Integer> normMat = store.getSimilarityMatrix(aspectNumber, geneId);
                // adding of the Rows
                addedMat = addRows(addedMat, normMat);
            }
        }
        return addedMat;
    }

    /**
     * This method is called for each aspect and each gene once. At the second call it has it's own
     * result as input. This is to merge all required aspects and searched genes.
     *
     * @param addedMat the former rows
     * @param normMat a new row
     * @return matrix containing the information of both inputs
     */
    private static Map<Coordinates, Integer> addRows(Map<Coordinates, Integer> addedMat,
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

    /**
     * Getting the pairwise and total similarity ratings.
     *
     * @param store interface MatrixStore
     * @param addedMat a matrix. The first row and column contain the gene IDs. The
     * rest contains the similarity ratings added together for all aspects.
     * @return a complex data structure containing the result genes, their total ratings and
     * their pairwise ratings to the searched genes
     */
    public static Map<Integer, Map<Integer, Map<Integer, Integer>>> findSimilarSet(
            MatrixStore store, Map<Coordinates, Integer> addedMat) {
        // <resultId, <totalRating, <searchedId, pairwiseRating>>>
        Map<Integer, Map<Integer, Map<Integer, Integer>>> similarSet =
                new HashMap<Integer, Map<Integer, Map<Integer, Integer>>>();

        // load list of all gene Ids
        Map<Coordinates, Integer> allGeneIds = store.getSimilarityMatrix("0", "ALL");

        for (Map.Entry<Coordinates, Integer> entry : addedMat.entrySet()) {
            int xCoordinate = entry.getKey().getKey();
            int yCoordinate = entry.getKey().getValue();

            if (yCoordinate != SUBJECT_ID_COLUMN) {
                Map<Integer, Integer> pairwiseRating = new HashMap<Integer, Integer>();
                Map<Integer, Map<Integer, Integer>> totalRating =
                        new HashMap<Integer, Map<Integer , Integer>>();
                int addRatings;
                addRatings = entry.getValue();
                if (similarSet.containsKey(allGeneIds.get(new Coordinates(0, yCoordinate)))) {
                    Map<Integer, Map<Integer, Integer>> currentRatings =
                            similarSet.get(allGeneIds.get(new Coordinates(0, yCoordinate)));
                    int currentRating = 0;
                    for (Map.Entry<Integer, Map<Integer, Integer>> currentRatingTmp
                            : currentRatings.entrySet()) {
                        currentRating = currentRatingTmp.getKey();
                        pairwiseRating = currentRatingTmp.getValue();
                    }
                    addRatings += currentRating;
                    similarSet.remove(allGeneIds.get(new Coordinates(0, yCoordinate)));
                }
                pairwiseRating.put(addedMat.get(new Coordinates(xCoordinate, 0)), entry.getValue());
                totalRating.put(addRatings, pairwiseRating);
                similarSet.put(allGeneIds.get(new Coordinates(0, yCoordinate)), totalRating);
            }
        }
        return similarSet;
    }

    /**
     * Extracts the information about the result genes with their similar ratings out of the
     * similarSet. The 2D Integer can be ordered from highest to lowest by rating.
     *
     * @param similarSet contains the result genes and their total ratings as well as information
     * about pairwise similarities between searched and result genes:
     * "Map<resultGeneID, Map<totalRating< Map<searchedGeneID, pairwiseRating>>>"
     * @return 2D Integer containing the result gene IDs and their total ratings
     */
    public static int[][] getTotalRating(
            Map<Integer, Map<Integer, Map<Integer, Integer>>> similarSet) {
        int[][] totalRatingSet = new int[similarSet.size()][2];
        int count = 0;
        for (Map.Entry<Integer, Map<Integer, Map<Integer, Integer>>>
        entry : similarSet.entrySet()) {
            // Save the gene ID
            totalRatingSet[count][0] = entry.getKey();
            Map<Integer, Map<Integer, Integer>> tmp = entry.getValue();
            for (Map.Entry<Integer, Map<Integer, Integer>> entry2 : tmp.entrySet()) {
                // Save the corresponding total rating
                totalRatingSet[count][1] = entry2.getKey();
            }
            count += 1;
        }
        return totalRatingSet;
    }

    /**
     * Orders a 2D Integer from highest to lowest in the second column.
     *
     * @param mat2Columns 2D Integer where the first column contains gene IDs and the second the
     * corresponding total rating.
     * @return the ordered input from highest to lowest
     */
    public static int[][] order(int[][] mat2Columns) {
        Arrays.sort(mat2Columns, new Comparator<int[]>() {
            @Override
            public int compare(final int[] entry1, final int[] entry2) {
                final Integer time1 = entry1[1];
                final Integer time2 = entry2[1];
                return time2.compareTo(time1);
            }
        });
        return mat2Columns;
    }

    /**
     * Calculates a matrix of all searched genes in all required aspects of the common items.
     *
     * @param store interface MatrixStore
     * @param views the information from the property file
     * @param searchedGenIDs the gene Ids a user searched for
     * @return sparse matrix containing similarity ratings of all (and only of) the searched genes
     */
    public static Map<Coordinates, ArrayList<Integer>> addCommonMat(MatrixStore store,
            Map<Coordinates, String> views, Integer[] searchedGenIDs) {
        Map<Coordinates, ArrayList<Integer>> addedCommonMat =
                new HashMap<Coordinates, ArrayList<Integer>>();
        for (int j = 0; j < views.size() / 4; j++) {
            if ("category".equals(views.get(new Coordinates(j, 3)))) {
                String aspectNumber = views.get(new Coordinates(j, 0));
                for (int i = 0; i < searchedGenIDs.length; i++) {
                    // Read in row
                    String geneId = Integer.toString(searchedGenIDs[i]);
                    Map<Coordinates, ArrayList<Integer>> commonMat =
                            store.getCommonItemsMatrix(aspectNumber, geneId);
                    // Merge the rows
                    addedCommonMat = Methods.addCommonRow(addedCommonMat, commonMat);
                }
            }
        }
        return addedCommonMat;
    }

    /**
     * This method is called for each aspect and each gene once. At the second call it has it's own
     * result as input. This is to merge all required aspects and searched genes.
     *
     * @param addedCommonMat its own output from the iteration before (is empty for the first
     * iteration)
     * @param commonMat a rectangular pre-calculated matrix. The first row and column contain the
     * gene IDs. The rest contains Lists of common items
     * @return matrix containing the information of both inputs
     */
    public static Map<Coordinates, ArrayList<Integer>> addCommonRow(Map<Coordinates,
            ArrayList<Integer>> addedCommonMat, Map<Coordinates, ArrayList<Integer>> commonMat) {

        if (addedCommonMat == null
                || addedCommonMat.isEmpty()) {
            return commonMat;
        }
        else {
            ArrayList<Integer> newItems;
            ArrayList<Integer> items;
            for (Map.Entry<Coordinates, ArrayList<Integer>> newAspect : commonMat.entrySet()) {
                Coordinates coordinates = newAspect.getKey();
                newItems = newAspect.getValue();
                if (!addedCommonMat.containsKey(coordinates)) {
                    addedCommonMat.put(coordinates, newItems);
                }
                // If the coordinates have been seen before
                // -> add the common items to the ArrayList
                else {
                    if (newAspect.getKey().getValue() != 0) {
                        items = addedCommonMat.get(coordinates);
                        items.addAll(newItems);
                        addedCommonMat.put(coordinates, items);
                    }
                }
            }
            return addedCommonMat;
        }
    }

    /**
     * Calculates a Map of the common items (e.g. pathway IDs), that the result genes have in
     * common with the searched genes (pairwise)
     *
     * @param store interface MatrixStore
     * @param commonMat a rectangular matrix. The first row and column contain the gene IDs. The
     * rest contains Lists of common items.
     * @return matrix containing all common items of the similar genes to the users genes (pairwise)
     * "Map<resultGeneID, Map<searchedGeneID, ListOfCommonItems>>"
     */
    public static Map<Integer, Map<Integer, ArrayList<Integer>>> getCommonItems(MatrixStore store,
            Map<Coordinates, ArrayList<Integer>> commonMat) {

        // <resultId, <searchedId, commonItems>>
        Map<Integer, Map<Integer, ArrayList<Integer>>> commonItemsAll =
              new HashMap<Integer, Map<Integer, ArrayList<Integer>>>();

        // load list of all gene Ids
        Map<Coordinates, Integer> allGeneIds = store.getSimilarityMatrix("0", "ALL");

        for (Map.Entry<Coordinates, ArrayList<Integer>> entry : commonMat.entrySet()) {
            int xCoordinate = entry.getKey().getKey();
            int yCoordinate = entry.getKey().getValue();

            if (yCoordinate != SUBJECT_ID_COLUMN) {
                ArrayList<Integer> value = entry.getValue();
                Map<Integer, ArrayList<Integer>> commonItems;
                if (!commonItemsAll.containsKey(allGeneIds.get(new Coordinates(SUBJECT_ID_ROW, yCoordinate)))) {
                    commonItems = new HashMap<Integer, ArrayList<Integer>>();
                }
                else {
                    commonItems = commonItemsAll.get(allGeneIds.get(new Coordinates(
                            SUBJECT_ID_ROW, yCoordinate)));
                }
                commonItems.put(commonMat.get(new Coordinates(xCoordinate, SUBJECT_ID_COLUMN)).get(0), value);
                commonItemsAll.put(allGeneIds.get(new Coordinates(SUBJECT_ID_ROW, yCoordinate)), commonItems);
            }
        }
        return commonItemsAll;
    }

}
