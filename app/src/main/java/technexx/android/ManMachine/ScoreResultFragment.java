package technexx.android.ManMachine;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import technexx.android.ManMachine.Database.HighScoresDB;
import technexx.android.ManMachine.Database.ScoreResults;
import technexx.android.ManMachine.Database.ScoresMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScoreResultFragment extends Fragment {

    private View root;

    private String creatureStats;

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

    private int position;

    private int mPhoneHeight;
    private int mPhoneWidth;

    private void setPhoneDimensions() {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        mPhoneHeight = metrics.heightPixels;
        mPhoneWidth = metrics.widthPixels;
    }

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

        setPhoneDimensions();

        if (mPhoneHeight <=1920) {
            root = inflater.inflate(R.layout.score_creature_results, container, false);
        } else {
            root = inflater.inflate(R.layout.score_creature_results_h1920, container, false);
        }

        final SharedPreferences pref = getContext().getSharedPreferences("SharedPref", 0);

        //Receiving row position for high score entry, which should sync up w/ creature stats.
        Bundle args = getArguments();
        position = args.getInt("position");

        final TextView creature_end = root.findViewById(R.id.creature_stats);
        creature_end.setTypeface(null, Typeface.ITALIC);

        final TextView strResult = root.findViewById(R.id.result_str);
        final TextView dexResult = root.findViewById(R.id.result_dex);
        final TextView intResult = root.findViewById(R.id.result_int);
        final TextView loyResult = root.findViewById(R.id.result_obed);
        final TextView beaResult = root.findViewById(R.id.result_bea);
        final TextView sanResult = root.findViewById(R.id.result_san);
        final TextView endResult = root.findViewById(R.id.result_end);
        final TextView wealthResult = root.findViewById(R.id.result_wealth);
        final TextView anonResult = root.findViewById(R.id.result_anon);
        final TextView launchResult = root.findViewById(R.id.result_launch);
        final TextView terResult = root.findViewById(R.id.result_strTake);
        final TextView supResult = root.findViewById(R.id.result_dexTake);
        final TextView endSceneResult = root.findViewById(R.id.result_endScene);
        final TextView totalResult = root.findViewById(R.id.result_total);
        final TextView rankResult = root.findViewById(R.id.rank_score);

        final TextView summary = root.findViewById(R.id.summary);

        final TextView strength = root.findViewById(R.id.strength);
        final TextView dexterity = root.findViewById(R.id.dexterity);
        final TextView intelligence = root.findViewById(R.id.intelligence);
        final TextView loyalty = root.findViewById(R.id.loyalty);
        final TextView beauty = root.findViewById(R.id.beauty);

        final TextView strengthResult = root.findViewById(R.id.resultOne);
        final TextView dexterityResult = root.findViewById(R.id.resultTwo);
        final TextView intelligenceResult = root.findViewById(R.id.resultThree);
        final TextView loyaltyResult = root.findViewById(R.id.resultFour);
        final TextView beautyResult = root.findViewById(R.id.resultFive);

        final TextView quirks = root.findViewById(R.id.quirks);
        final TextView quirk_one = root.findViewById(R.id.quirk_one);
        final TextView quirk_two = root.findViewById(R.id.quirk_two);
        final TextView quirk_three = root.findViewById(R.id.quirk_three);

        final String[] godStrengthArray = getResources().getStringArray(R.array.creation_strength);
        final String[] godDexterityArray = getResources().getStringArray(R.array.creature_dexterity);
        final String[] godIntelligenceArray = getResources().getStringArray(R.array.creation_intelligence);
        final String[] godloyaltyArray = getResources().getStringArray(R.array.creation_loyalty);
        final String[] godBeautyArray = getResources().getStringArray(R.array.creation_beauty);

        final String[] quirkArray = getResources().getStringArray(R.array.quirk_reveal);

        //Async works for values programmatically but does not update the UI in time (i.e. it will update in debug mode where the input is delayed, but not in real time.
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final HighScoresDB scoresDB = HighScoresDB.getDatabase(getContext());

                creatureStats = scoresDB.highScoresDao().creatureStats(position);

                creature_end.setText(creatureStats);

                str = scoresDB.highScoresDao().str(position);
                dex = scoresDB.highScoresDao().dex(position);
                intel = scoresDB.highScoresDao().intel(position);
                obed = scoresDB.highScoresDao().obed(position);
                bea = scoresDB.highScoresDao().bea(position);
                quirkOne = scoresDB.highScoresDao().q1(position);
                quirkTwo = scoresDB.highScoresDao().q2(position);
                quirkThree = scoresDB.highScoresDao().q3(position);

                strVar = scoresDB.highScoresDao().strScore(position);
                dexVar = scoresDB.highScoresDao().dexScore(position);
                intelVar = scoresDB.highScoresDao().intScore(position);
                obedVar = scoresDB.highScoresDao().obedScore(position);
                beaVar = scoresDB.highScoresDao().beaScore(position);
                sanVar = scoresDB.highScoresDao().sanScore(position);
                endVar = scoresDB.highScoresDao().endScore(position);
                wealthVar = scoresDB.highScoresDao().comScore(position);
                anonVar = scoresDB.highScoresDao().anonSCore(position);
                launchVar = scoresDB.highScoresDao().launchSuccess(position);
                terVar = scoresDB.highScoresDao().terScore(position);
                supVar = scoresDB.highScoresDao().supScore(position);
                endSceneVar = scoresDB.highScoresDao().endSceneScore(position);
                totalVar = scoresDB.highScoresDao().totalScore(position);
                summaryVar = scoresDB.highScoresDao().summary(position);
                rankVar = scoresDB.highScoresDao().endRank(position);
            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                strengthResult.setText(godStrengthArray[str]);
                dexterityResult.setText(godDexterityArray[dex]);
                intelligenceResult.setText(godIntelligenceArray[intel]);
                loyaltyResult.setText(godloyaltyArray[obed]);
                beautyResult.setText(godBeautyArray[bea]);

                quirks.setText(R.string.quirks);
                quirk_one.setText(quirkArray[quirkOne]);
                quirk_two.setText(quirkArray[quirkTwo]);
                quirk_three.setText(quirkArray[quirkThree]);

                strengthResult.setTextColor(setColor(str));
                dexterityResult.setTextColor(setColor(dex));
                intelligenceResult.setTextColor(setColor(intel));
                loyaltyResult.setTextColor(setColor(obed));
                beautyResult.setTextColor(setColor(bea));

                setQuirkColor(quirkOne, quirk_one);
                setQuirkColor(quirkTwo, quirk_two);
                setQuirkColor(quirkThree, quirk_three);

                strResult.setText(String.valueOf(strVar));
                dexResult.setText(String.valueOf(dexVar));
                intResult.setText(String.valueOf(intelVar));
                loyResult.setText(String.valueOf(obedVar));
                beaResult.setText(String.valueOf(beaVar));
                sanResult.setText(String.valueOf(sanVar));
                endResult.setText(String.valueOf(endVar));
                wealthResult.setText(String.valueOf(wealthVar));
                anonResult.setText(String.valueOf(anonVar));
                launchResult.setText(String.valueOf(launchVar));
                terResult.setText(String.valueOf(terVar));
                supResult.setText(String.valueOf(supVar));
                endSceneResult.setText(String.valueOf(endSceneVar));
                totalResult.setText(String.valueOf(totalVar));
                rankResult.setText(String.valueOf(rankVar));
                summary.setText(String.valueOf(summaryVar));

                strength.setText(R.string.strength);
                dexterity.setText(R.string.dexterity);
                intelligence.setText(R.string.intelligence);
                loyalty.setText(R.string.loyalty);
                beauty.setText(R.string.beauty);
            }
        }, 20);



        return root;
    }

    public int setColor(int color) {
        switch (color) {
            case 0:
                return getResources().getColor(R.color.Red);
            case 1:
                return getResources().getColor(R.color.Test_Red);
            case 2:
                return getResources().getColor((R.color.Test_Color));
            case 3:
                return getResources().getColor(R.color.Yellow);
            case 4:
                return getResources().getColor(R.color.Light_Green);
            case 5:
                return getResources().getColor(R.color.Test_Green);
        }
        return color;
    }

    private void setQuirkColor(int place, TextView quirk) {
        if (place % 2 == 0) {
            quirk.setTextColor(Color.GREEN);
        } else {
            quirk.setTextColor(Color.RED);
        }
    }
}
