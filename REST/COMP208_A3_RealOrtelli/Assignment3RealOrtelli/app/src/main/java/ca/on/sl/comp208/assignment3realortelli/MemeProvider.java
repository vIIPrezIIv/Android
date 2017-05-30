package ca.on.sl.comp208.assignment3realortelli;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;


import java.util.List;

import static android.content.UriMatcher.NO_MATCH;

public class MemeProvider extends ContentProvider {

    /**
     * Variables and static URI's
     */
    private static final int ONLY_POPULAR = 1;
    private static final int ONLY_NEW = 2;
    private static final int LIKE = 3;
    private static final int DISLIKE = 4;
    private static final UriMatcher uris = new UriMatcher(NO_MATCH);

    static{
        uris.addURI(MemeContract.AUTHORITY, "Generators_Select_ByPopular?pageIndex=0&pageSize=10&days=&apiKey=" + MemeContract.APIKEY, ONLY_POPULAR);
        uris.addURI(MemeContract.AUTHORITY, "Generators_Select_ByNew?pageIndex=0&pageSize=10&apiKey=" + MemeContract.APIKEY, ONLY_NEW);
        uris.addURI(MemeContract.AUTHORITY, "Vote?entityName=Instance&entityID=72628355&voteScore=" + MemeContract.VOTELIKE + MemeContract.APIKEY, LIKE);
        uris.addURI(MemeContract.AUTHORITY, "Vote?entityName=Instance&entityID=72628355&voteScore=" + MemeContract.VOTEDISLIKE + MemeContract.APIKEY, DISLIKE);
    }

    public MemeProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        return null;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    /**
     * query, Handles four GET's.
     * ONLY_POPULAR, retrieves 10 popular memes
     * ONLY_NEW, retireves 10 newly created memes
     * LIKE, votes "like" on a click meme
     * DISLIKE, votes "dislike" on a click meme
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        Gson gson = new Gson();
        MatrixCursor mc = null;
        String result;
        MemeByPopular meme;
        List<Result> resultList;
        String[] columnNames_Result = MemeContract.COLUMN_RESULT;
        MatrixCursor.RowBuilder rb;

        switch(MainActivity.buttonClicked)
        {
            case ONLY_POPULAR:

                result = NetworkUtls.getNetworkData(MemeContract.URLPOPULAR);

                Log.i("Unpopular Request: ", result);

                meme = gson.fromJson(result, MemeByPopular.class);

                if(meme == null)
                {
                    return null;
                }

                resultList = meme.getResult();

                mc = new MatrixCursor(columnNames_Result);

                for(int row = 0; row < resultList.size(); row++)
                {
                    rb = mc.newRow();
                    rb.add(resultList.get(row).getDisplayName());
                    rb.add(resultList.get(row).getRanking());
                    rb.add(resultList.get(row).getImageUrl());
                    rb.add(resultList.get(row).getEntityVotesSummary().getTotalVotesSum());
                    rb.add(resultList.get(row).getGeneratorID());
                }

                break;

            case ONLY_NEW:

                result = NetworkUtls.getNetworkData(MemeContract.URLNEW);

                Log.i("New Request: ", result);

                meme = gson.fromJson(result, MemeByPopular.class);

                if(meme == null)
                {
                    return null;
                }

                resultList = meme.getResult();

                mc = new MatrixCursor(columnNames_Result);

                for(int row = 0; row < resultList.size(); row++)
                {
                    rb = mc.newRow();
                    rb.add(resultList.get(row).getDisplayName());
                    rb.add(resultList.get(row).getRanking());
                    rb.add(resultList.get(row).getImageUrl());
                    rb.add(resultList.get(row).getEntityVotesSummary().getTotalVotesSum());
                    rb.add(resultList.get(row).getGeneratorID());
                }

                break;

            case LIKE:

                result = NetworkUtls.getNetworkData(MemeContract.URLVOTES + MainActivity.entityID + MemeContract.VOTESEND + MemeContract.VOTELIKE);
                Log.i("Like Request: ", result);
                break;

            case DISLIKE:

                result = NetworkUtls.getNetworkData(MemeContract.URLVOTES + MainActivity.entityID + MemeContract.VOTESEND + MemeContract.VOTEDISLIKE);
                Log.i("Disliked Request: ", result);
                break;

            default:
                break;
        }

        return mc;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        return 0;
    }
}
