package technexx.android.ManMachine;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import technexx.android.ManMachine.Database.HighScoresDB;
import technexx.android.ManMachine.Database.ScoresMenu;

import java.util.List;

public class ScoreMenuFragment extends Fragment implements ScoreListAdapter.scoreClickListener{

    private View root;
    List<ScoresMenu> scoreList;
    scoreCallback mScoreCallback;

    private int mPhoneHeight;
    private int mPhoneWidth;

    public interface scoreCallback {
        void onScoreCallback(int id, boolean passedIsDead);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mScoreCallback = (scoreCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement onScoreCallback");
        }
    }

    //boolean in onScoreClick is received from ScoreListAdapter. Separate boolean created in this fragment so onScoreCallBack can use it as an input, which is then taken in by HighScoresMenu which will launch our instance of each high score.
    @Override
    public void onScoreClick(int id, boolean isDead) {
        //Intent in callback since we are launching a new activity.
        mScoreCallback.onScoreCallback(id, isDead);
    }

    private void setPhoneDimensions() {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        mPhoneHeight = metrics.heightPixels;
        mPhoneWidth = metrics.widthPixels;
    }

    //Populating the list of high score entries.
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

        setPhoneDimensions();

        if (mPhoneHeight <=1920) {
            root = inflater.inflate(R.layout.high_score_list, container, false);
        } else {
            root = inflater.inflate(R.layout.high_score_list, container, false);
        }

        HighScoresDB scoresDB = HighScoresDB.getDatabase(getContext());
        scoreList = scoresDB.highScoresDao().sortedScoreList();

        RecyclerView menuRecycler = root.findViewById(R.id.score_recycler_menu);

        ScoreListAdapter adapter1 = new ScoreListAdapter(scoreList,getContext());
        adapter1.setSharedClick(this);

        menuRecycler.setAdapter(adapter1);

        menuRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }
}
