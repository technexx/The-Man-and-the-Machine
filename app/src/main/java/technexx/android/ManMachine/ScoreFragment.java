package technexx.android.ManMachine;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import technexx.android.ManMachine.Database.HighScores;
import technexx.android.ManMachine.Database.HighScoresDB;

import java.util.List;

public class ScoreFragment extends Fragment{

    List<technexx.android.ManMachine.Database.HighScores> allScores;

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        //Separate RecyclerView for ScoreMenu because we are a bad planner and this is easier.
        View root = inflater.inflate(R.layout.high_score_list, container, false);

        //Referencing specific row. Int position is passed in via Callback from Adapter.
        Bundle args = getArguments();
        final int position = args.getInt("position");


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                HighScoresDB scoresDB = HighScoresDB.getDatabase(getContext());
                allScores = scoresDB.highScoresDao().loadScores(position);
            }
        });

        return root;
    }

}
