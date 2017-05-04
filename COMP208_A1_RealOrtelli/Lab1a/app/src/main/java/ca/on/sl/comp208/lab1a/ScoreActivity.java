package ca.on.sl.comp208.lab1a;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public class ScoreActivity extends AppCompatActivity {

    /**
     * Displays 5 scores passed by MainAcvtivity, by taking them from prefs and iterating through a loop displaying through a textView.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();

        Set<String> scores = prefs.getStringSet("fiveScores", null);
        ArrayList<String> scoreList = new ArrayList<String>(scores);

        Collections.sort(scoreList);

        TextView editTopScores = (TextView) findViewById(R.id.textView7);

        for(Iterator<String> ctr = scoreList.iterator(); ctr.hasNext(); ) {
            editTopScores.append(ctr.next() + '\n');
        }
    }
}
