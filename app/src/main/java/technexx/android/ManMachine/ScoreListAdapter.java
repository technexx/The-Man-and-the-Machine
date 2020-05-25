package technexx.android.ManMachine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import technexx.android.ManMachine.Database.ScoresMenu;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ScoreListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<ScoresMenu> scoreList;
    scoreClickListener mScoreClickListener;
    Context mContext;
    int counter;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_SCORE = 1;

    public interface scoreClickListener {
        void onScoreClick(int id, boolean isDead);
    }

    public void setSharedClick(scoreClickListener mScoreClickListener) {
        this.mScoreClickListener = mScoreClickListener;
    }

    public ScoreListAdapter(List<ScoresMenu> scoreList, Context context) {
        this.scoreList = scoreList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();


        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(context).inflate(R.layout.header, parent, false);
            return new HeaderHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.score_menu, parent, false);
            return new ScoreHolder(view);
        }
    }

    private ScoresMenu getScore (int position) {
        return scoreList.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final SharedPreferences pref = mContext.getSharedPreferences("SharedPref", 0);
        final SharedPreferences.Editor editor = pref.edit();

        if (holder instanceof HeaderHolder) {
            HeaderHolder headerHolder = (HeaderHolder)holder;
            headerHolder.header.setText(R.string.highscore_heading);
            headerHolder.date_header.setText(R.string.date_header);
            headerHolder.rank_header.setText(R.string.rank_header);
            headerHolder.score_header.setText(R.string.score_header);
        } else if (holder instanceof ScoreHolder){
            ScoreHolder scoreHolder = (ScoreHolder)holder;

            ScoresMenu newScore = getScore(position -1);
            scoreHolder.total.setText(String.valueOf(newScore.getTotalScore()));
            scoreHolder.date.setText(newScore.getDate());
            scoreHolder.rank.setText(newScore.getRank());
            scoreHolder.number.setText(String.valueOf(position));

            if ((newScore.getRank().contains("Dead"))) {
                final int i = scoreList.get(position -1).getId();
                scoreHolder.total.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mScoreClickListener.onScoreClick(i, true);
                    }
                });
            } else {
                final int i = scoreList.get(position -1).getId();
                scoreHolder.total.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mScoreClickListener.onScoreClick(i, false);
                    }
                });
            }

            //Each fragment instance of a high score is inflated in the ScoreResultsCombo class, which is launched via HighScoresMenu which calls back to ScoresMenuFragment, whose instance is launched via the onClick callback below.
            //So, Adapter -> ScoresMenuFragment -> HighScoresMenu -> ScoreResultsCombo.

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_SCORE;
        }
    }

    @Override
    public int getItemCount() {
        return scoreList.size() +1;
    }

    public class ScoreHolder extends RecyclerView.ViewHolder{

        public TextView number;
        public TextView date;
        public TextView total;
        public TextView rank;

        public ScoreHolder(@NonNull View itemView) {
            super(itemView);
            total = itemView.findViewById(R.id.score);
            date = itemView.findViewById(R.id.date);
            rank = itemView.findViewById(R.id.rank_score);
            number = itemView.findViewById(R.id.score_number);
        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {

        public TextView header;
        public TextView date_header;
        public TextView rank_header;
        public TextView score_header;

        public HeaderHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header_text);
            date_header = itemView.findViewById(R.id.date_heading);
            rank_header = itemView.findViewById(R.id.rank_heading);
            score_header = itemView.findViewById(R.id.score_heading);
        }
    }
}