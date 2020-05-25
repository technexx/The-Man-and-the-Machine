package technexx.android.ManMachine;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import technexx.android.ManMachine.Database.HighScoresDB;
import technexx.android.ManMachine.Database.ScoresMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HighScoresMenu extends AppCompatActivity implements ScoreMenuFragment.scoreCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Inflating layout with single FrameLayout, passing in ScoreMenu fragment.
        setContentView(R.layout.highscore_activity);

        FragmentManager fragmentManager = getSupportFragmentManager();
        ScoreMenuFragment scoreMenuFragment = new ScoreMenuFragment();

        //Adding menu fragment for high scores.
        fragmentManager.beginTransaction()
                .add(R.id.high_scores, scoreMenuFragment)
                .commit();
    }

    //Calling back the scoreMenu from a specific high score page.
    @Override
    public void onScoreCallback(int id, boolean isDead) {

        Intent intent = new Intent(this, ScoreResultCombo.class);
        intent.putExtra("position", id);
        intent.putExtra("isDead", isDead);
        startActivity(intent);
    }
}