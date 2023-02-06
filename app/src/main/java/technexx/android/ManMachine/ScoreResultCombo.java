package technexx.android.ManMachine;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class ScoreResultCombo extends AppCompatActivity {

    private String creatureName;
    private String terrorNumber;
    private String supportNumber;

    private int str;
    private int dex;
    private int intel;
    private int obed;
    private int bea;
    private int quirkOne;
    private int quirkTwo;
    private int quirkThree;
    private String ending;

    private int strVar;
    private int dexVar;
    private int intelVar;
    private int obedVar;
    private int beaVar;
    private int sanVar;
    private int endVar;
    private int wealthVar;
    private int anonVar;
    private int launchVar;
    private int terVar;
    private int supVar;
    private int endSceneVar;

    private int totalVar;
    private String summaryVar;
    private String rankVar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.score_result_combo);
        setContentView(R.layout.highscore_activity);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        boolean isDead = intent.getBooleanExtra("isDead", false);

        FragmentManager fragmentManager = getSupportFragmentManager();
        ScoreFragment scoreFragment = new ScoreFragment();
        ScoreResultFragment scoreResultFragment = new ScoreResultFragment();
        DeathLossFragment deathLossFragment = new DeathLossFragment();

        if (isDead) {
            fragmentManager.beginTransaction()
                    .add(R.id.high_scores, deathLossFragment)
                    .commit();
        } else {
            Bundle b = new Bundle();
            b.putInt("position", position);
            scoreFragment.setArguments(b);
            scoreResultFragment.setArguments(b);

            //Adding menu fragment for high scores.
            fragmentManager.beginTransaction()
                    .add(R.id.high_scores, scoreResultFragment)
                    .commit();
        }
    }
}