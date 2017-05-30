package ca.on.sl.comp208.assignment3realortelli;

import android.net.Uri;

/**
 * Created by Ray on 2017-04-06.
 */

public final class MemeContract {

    /**
     * Variables, declarations for URI's and URL's.
     * APIKEY: 7CE86E81-2B0C-408C-ADA4-702B0EBC7AE3
     */
    public static final String AUTHORITY = "sl.on.ca.comp208";
    public static final String SCHEME = "content";
    public static final String APIKEY = "7CE86E81-2B0C-408C-ADA4-702B0EBC7AE3";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME).authority(AUTHORITY).build();
    public static final String[] COLUMN_RESULT = {"displayName", "ranking", "imageUrl", "totalVotesSum", "entityID"};
    public static final String URLPOPULAR = "http://version1.api.memegenerator.net/Generators_Select_ByPopular?pageIndex=0&pageSize=10&days=&apiKey=" + MemeContract.APIKEY;
    public static final String URLNEW = "http://version1.api.memegenerator.net/Generators_Select_ByNew?pageIndex=0&pageSize=10&apiKey=" + MemeContract.APIKEY;
    public static final String URLVOTES = "http://version1.api.memegenerator.net/Vote?entityName=Generator&entityID=";
    public static final String VOTESEND = "&voteScore=";
    public static final String VOTELIKE = "1&apiKey=" + MemeContract.APIKEY;
    public static final String VOTEDISLIKE = "-1&apiKey=" + MemeContract.APIKEY;


}
