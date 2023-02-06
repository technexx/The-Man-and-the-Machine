package technexx.android.ManMachine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import technexx.android.ManMachine.Database.HighScores;
import technexx.android.ManMachine.Database.HighScoresDB;
import technexx.android.ManMachine.Database.HighScoresDao;
import technexx.android.ManMachine.Database.ScoreResults;
import technexx.android.ManMachine.Database.ScoresMenu;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ResultsFragment extends Fragment {

    private View root;

    private HighScoresDB scoresDB;
    private HighScores scores;
    private ScoresMenu scoresMenu;
    private ScoreResults scoreResults;

    Handler handler;
    Runnable str_runnable;
    Runnable dex_runnable;
    Runnable int_runnable;
    Runnable loy_runnable;
    Runnable bea_runnable;
    Runnable q1_runnable;
    Runnable q2_runnable;
    Runnable q3_runnable;

    private String strengthText;
    private String dexterityText;
    private String intelligenceText;
    private String loyaltyText;
    private String beautyText;

    private int strengthColor;
    private int dexterityColor;
    private int intelligenceColor;
    private int loyaltyColor;
    private int beautyColor;

    private String strInsaneText;
    private String dexInsaneText;
    private String intInsaneText;
    private String beaInsaneText;
    private String loyInsaneText;

    private int strColorInsane;
    private int dexColorInsane;
    private int intColorInsane;
    private int beaColorInsane;
    private int loyColorInsane;

    private String[] godStrengthArray;
    private String[] godDexterityArray;
    private String[] godIntelligenceArray;
    private String[] godloyaltyArray;
    private String[] godBeautyArray;

    //Global variable used to equate random Array choice w/ appropriate color.
    private int creationRoll;

    private TextView strengthResult;
    private TextView dexterityResult;
    private TextView intelligenceResult;
    private TextView loyaltyResult;
    private TextView beautyResult;

    //Passed in from callBack;
    private int upgradeStr;
    private int upgradeDex;
    private int upgradeTemp;
    private int upgradeObed;
    private int upgradeChar;

    private int sanity;
    private int endurance;
    private int anonymity;
    private int wealth;

    private boolean strChance;
    private boolean dexChance;
    private boolean tempChance;
    private boolean obedChance;
    private boolean charChance;

    private boolean incineratorFail;
    private int burnCounter;

    private String firstQuirk = "null";
    private String secondQuirk = "null";
    private String thirdQuirk = "null";

    private int fluxInjections;
    private String[] injectionList;
    private String[] quirkArray;
    private String[] quirkRevealArray;
    private String[] quirkInjectArray;

    private int quirkCounter;
    private boolean isInjectionUsed;
    private boolean spentInjection;

    private TextView quirk_one;
    private TextView quirk_two;
    private TextView quirk_three;

    private int q1Delay;
    private int q2Delay;
    private int q3Delay;
    private int largestDelay;
    private boolean hasNoQuirks;

    private int quirkPlaceOne;
    private int quirkPlaceTwo;
    private int quirkPlaceThree;

    private int daysPlayed;
    private int burnFail;

    private boolean endGame;
    private int endRevealCounter;
    private int altCounter;

    //Used as global variable to assign proper Str/Dex combo for Strings.
    private int combStat;

    private int endDescCounter;

    private List<String> obedReveal;

    private List<String> intObedReveal;

    private List<String> strDexReveal;

    private List<String> finalReveal;
    private ArrayList<String> finalRevealArray;

    private List<String> anonEnd;

    private String finale;

    private List<String> anonReveal;
    private List<String> strLaunch;
    private List<String> dexLaunch;

    private String[] tempEndArray;

    //All values assigned to these come from the strLaunch.
    private int strEndArray;
    private int dexEndArray;

    private boolean lastDitch;
    private boolean launchFailure;

    private List<String> quirkList;
    private boolean qc1 = true;
    private boolean qc2 = true;
    private boolean qc3 = true;
    private boolean qc4 = true;
    private boolean qc5 = true;
    private boolean qc6 = true;
    private boolean qc7 = true;
    private boolean qc8 = true;

    public int qPlace;

    private boolean c1;
    private boolean c2;
    private boolean c3;
    private String quirkStringOne;
    private String quirkStringTwo;
    private String quirkStringThree;

    private int hasVocalist;
    private int hasPractice;
    private int hasBerserk;
    private int hasSwoll;
    private int hasTalker;
    private int hasBuoyant;
    private int hasBiblio;
    private int hasExhibitionist;

    private boolean dealStr;
    private boolean dealDex;
    private boolean dealInt;
    private boolean dealBea;
    private boolean dealLoy;

    //Use roll methods to assign these the resulting creationRoll int, then call on them in the end-game method.
    private int strVar;
    private int dexVar;
    private int intVar;
    private int obedVar;
    private int beauVar;

    private int strPct;
    private int dexPct;
    private int intPct;
    private int loyPct;
    private int beaPct;

    //Used to switch between pos and negative stat changes in final quirk adjustments.
    private boolean posStat;
    private String tempResponse;

    private int rulePct;
    private int popPct;
    private int stealPct;
    private int keptTer;
    private int keptPop;
    private int stealTer;
    private int stealPop;

    private int strWeight;
    private int dexWeight;
    private int intWeight;
    private int beaWeight;

    private boolean percentSaved;

    private String[] rankArray;
    private String[] rankArrayTwo;
    private boolean stopRank;
    private boolean stopHome;
    private boolean stopScore;

    private String[] endWealthArray;
    private String[] endSanityArray;
    private String[] endBribesZeroOneArray;
    private String[] endBribesTwoArray;
    private String[] endBribesThreeArray;
    private String[] endBribesFourArray;
    private String[] endEnduranceArray;
    private String[] endEnduranceSanityArray;
    private String[] endFreeArray;
    private String[] endPrisonArray;

    private String[] namesArray;
    private String creatureName;
    private String[] relativeDescArray;
    private String[] relativesArray;

    private int newTer;
    private int newSupport;

    private String finalWealth;
    private String finalSanity;
    private String finalBribes;
    private String finalEndurance;
    private String finalEnduranceSanity;
    private String finalHome;
    private String endString;
    private String endStringTwo;
    private String endStringLoss;

    int totalScore;

    private int endSceneScore;
    private boolean gameEnd;
    private String endRank;
    private int totalStats;

    private int endDaysLeft;
    private int endDaysCounter;
    private boolean endDaysLaunch;
    private boolean sanZero;
    private String[] zeroSanArray;
    private String[] zeroSanArrayTwo;
    private String[] zeroSanArrayThree;
    private String[] zeroSanArrayFour;
    private String[] zeroSanArrayFive;
    private String[] zeroQuirkArray;

    nextTurnCallback mNextTurn;
    endGameCallback mEndGameCallback;
    burnFailCallback mBurnFailCallback;
    clearStatsCallback mClearStats;
    showPercentages mOnShowPercentages;
    repeatResults mOnRepeatResults;

    private boolean postStr;
    private boolean postDex;
    private boolean postInt;
    private boolean postBea;
    private boolean postLoy;

    private Random random;
    private ArrayList<String> insaneStats;

    private int mPhoneHeight;
    private int mPhoneWidth;

    public interface nextTurnCallback {
        void onNextTurn();
    }

    public interface endGameCallback {
        void onEndGame();
    }

    public interface burnFailCallback {
        void onBurnFail(int stat, boolean isPos);
    }

    public interface clearStatsCallback {
        void onClearStats();
    }

    public interface showPercentages {
        void onShowPercentages(int ter, int sup, int newTer, int newPop, boolean terPos, boolean supPos);
    }

    public interface repeatResults {
        void onRepeatResults();
    }

    //Not attached to context due to delay handler trying to run even though this Fragment has been replaced by StoryFragment.
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mNextTurn = (nextTurnCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement nextTurn Callback");
        }
        try {
            mEndGameCallback = (endGameCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement endGame Callback");
        }
        try {
            mBurnFailCallback = (burnFailCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement burnFail Callback");
        }
        try {
            mClearStats = (clearStatsCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement clearStats Callback");
        }
        try {
            mOnShowPercentages = (showPercentages) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement showPercentages Callback");
        }
        try {
            mOnRepeatResults = (repeatResults) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement onRepeatResults Callback");
        }

    }

    private void setPhoneDimensions() {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        mPhoneHeight = metrics.heightPixels;
        mPhoneWidth = metrics.widthPixels;
    }

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

        setPhoneDimensions();

        if (mPhoneHeight <=1920) {
            root = inflater.inflate(R.layout.results_fragment, container, false);
        } else {
            root = inflater.inflate(R.layout.results_fragment_h1920, container, false);
        }

        //Moves back to main menu on Android "back" button. Also kills the delay handler.
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                stopDelay();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                scoresDB = HighScoresDB.getDatabase(getContext());
                scores = new HighScores();
                scoresMenu = new ScoresMenu();
                scoreResults = new ScoreResults();
            }
        });

        handler = new Handler();

        //May be a layout thing issue (views constrained to one another)
        final ScrollView scrollView = root.findViewById(R.id.results_scrollview);

        final TextView result = root.findViewById(R.id.result);

        final Button nextDay = root.findViewById(R.id.next_day);
        final Button createCreature = root.findViewById(R.id.create_creature);
        final Button confirmCreature = root.findViewById(R.id.confirm_creation);
        final Button cancelCreature = root.findViewById(R.id.backtrack);
        final TextView warning = root.findViewById(R.id.creature_warning);
        final TextView warning_two = root.findViewById(R.id.creature_warning_two);
        final TextView warning_three = root.findViewById(R.id.creature_warning_three);

        final TextView strength = root.findViewById(R.id.strength);
        final TextView dexterity = root.findViewById(R.id.dexterity);
        final TextView intelligence = root.findViewById(R.id.intelligence);
        final TextView loyalty = root.findViewById(R.id.loyalty);
        final TextView beauty = root.findViewById(R.id.beauty);

        strengthResult = root.findViewById(R.id.resultOne);
        dexterityResult = root.findViewById(R.id.resultTwo);
        intelligenceResult = root.findViewById(R.id.resultThree);
        loyaltyResult = root.findViewById(R.id.resultFour);
        beautyResult = root.findViewById(R.id.resultFive);

        final Button use_injection = root.findViewById(R.id.use_injection);
        final TextView injectionResult = root.findViewById(R.id.injection_result);
        final TextView postBurn = root.findViewById(R.id.post_burn);
        final Button postBurnContinue = root.findViewById(R.id.post_burn_continue);

        final Button incinerator_continue = root.findViewById(R.id.incinerator_continue);

        final TextView quirks = root.findViewById(R.id.quirks);
        quirk_one = root.findViewById(R.id.quirk_one);
        quirk_two = root.findViewById(R.id.quirk_two);
        quirk_three = root.findViewById(R.id.quirk_three);

        final TextView upOne = root.findViewById(R.id.upOne);
        final TextView upTwo = root.findViewById(R.id.upTwo);
        final TextView upThree = root.findViewById(R.id.upThree);
        final TextView upFour = root.findViewById(R.id.upFour);
        final TextView upFive = root.findViewById(R.id.upFive);

        upOne.setVisibility(View.INVISIBLE);
        upTwo.setVisibility(View.INVISIBLE);
        upThree.setVisibility(View.INVISIBLE);
        upFour.setVisibility(View.INVISIBLE);
        upFive.setVisibility(View.INVISIBLE);


//        upFive.setVisibility(View.VISIBLE);
//        upFive.setText(R.string.endDowngrade);
//        upFive.setTextColor(getResources().getColor(R.color.Red_Alt));
//        upFour.setVisibility(View.VISIBLE);
//        upFour.setText(R.string.endDowngrade);
//        upFour.setTextColor(getResources().getColor(R.color.Red_Alt));
//        upThree.setVisibility(View.VISIBLE);
//        upThree.setText(R.string.endDowngrade);
//        upThree.setTextColor(getResources().getColor(R.color.Red_Alt));
//        upTwo.setVisibility(View.VISIBLE);
//        upTwo.setText(R.string.endDowngrade);
//        upTwo.setTextColor(getResources().getColor(R.color.Red_Alt));
//        upOne.setVisibility(View.VISIBLE);
//        upOne.setText(R.string.endDowngrade);
//        upOne.setTextColor(getResources().getColor(R.color.Red_Alt));

        use_injection.setText(R.string.use_injection);
        quirks.setText(R.string.quirks);

        postBurnContinue.setVisibility(View.GONE);
        incinerator_continue.setVisibility(View.GONE);
        incinerator_continue.setText(R.string.continue_game);
        quirk_one.setVisibility(View.GONE);
        quirk_two.setVisibility(View.GONE);
        quirk_three.setVisibility(View.GONE);

        warning_two.setVisibility(View.GONE);
        warning_three.setVisibility(View.GONE);

        obedReveal = Arrays.asList("blank", getString(R.string.z_4), getString(R.string.z_3), getString(R.string.z_2), getString(R.string.z_1));

        intObedReveal = Arrays.asList(getString(R.string.z_17_4), getString(R.string.z_17_3), getString(R.string.z_17_2), getString(R.string.z_17), getString(R.string.z_16), getString(R.string.z_15), getString(R.string.z_14), getString(R.string.z_13), getString(R.string.z_12), getString(R.string.z_11), getString(R.string.z_10), getString(R.string.z_9), getString(R.string.z_8), getString(R.string.z_7), getString(R.string.z_6), getString(R.string.z_5));

        strDexReveal = Arrays.asList(getString(R.string.z_36), getString(R.string.z_35), getString(R.string.z_34), getString(R.string.z_33), getString(R.string.z_32), getString(R.string.z_31), getString(R.string.z_30), getString(R.string.z_29), getString(R.string.z_28), getString(R.string.z_27), getString(R.string.z_26), getString(R.string.z_25), getString(R.string.z_24), getString(R.string.z_23), getString(R.string.z_22), getString(R.string.z_21));

        anonReveal = Arrays.asList(getString(R.string.oppose_1), getString(R.string.oppose_2), getString(R.string.oppose_3), getString(R.string.oppose_4), getString(R.string.oppose_5), getString(R.string.oppose_6), getString(R.string.oppose_7));

        anonEnd = Arrays.asList(getString(R.string.anon_1), getString(R.string.anon_2), getString(R.string.anon_3), getString(R.string.anon_4), getString(R.string.anon_5), getString(R.string.anon_6), getString(R.string.anon_7));

        strLaunch = Arrays.asList(getString(R.string.strEnd1_1), getString(R.string.strEnd1_2), getString(R.string.strEnd1_3), getString(R.string.strEnd2_1), getString(R.string.strEnd2_2), getString(R.string.strEnd2_3), getString(R.string.strEnd3_1), getString(R.string.strEnd3_2), getString(R.string.strEnd3_3), getString(R.string.strEnd3_4), getString(R.string.strEnd3_5), getString(R.string.strEnd3_6), getString(R.string.strEnd4_1), getString(R.string.strEnd4_2), getString(R.string.strEnd4_3), getString(R.string.strEnd4_4), getString(R.string.strEnd4_5), getString(R.string.strEnd4_6), getString(R.string.strEnd5_1), getString(R.string.strEnd5_2), getString(R.string.strEnd5_3), getString(R.string.strEnd5_4), getString(R.string.strEnd5_5), getString(R.string.strEnd5_6), getString(R.string.strEnd6_1), getString(R.string.strEnd6_2), getString(R.string.strEnd6_3), getString(R.string.strEnd6_4), getString(R.string.strEnd6_5), getString(R.string.strEnd6_6), getString(R.string.strEnd7_1), getString(R.string.strEnd7_2), getString(R.string.strEnd7_3), getString(R.string.strong_unopposed));

        dexLaunch = Arrays.asList(getString(R.string.dex_End1), getString(R.string.dex_End2), getString(R.string.dex_End3), getString(R.string.dex_End4), getString(R.string.dex_End5), getString(R.string.dex_End6));

        tempEndArray = getResources().getStringArray(R.array.temp_desc);
        quirkArray = getResources().getStringArray(R.array.quirks);

        endWealthArray = getResources().getStringArray(R.array.end_wealth);
        endSanityArray = getResources().getStringArray(R.array.end_sanity);
        endBribesZeroOneArray = getResources().getStringArray(R.array.bribes_zero_one);
        endBribesTwoArray = getResources().getStringArray(R.array.bribes_two);
        endBribesThreeArray = getResources().getStringArray(R.array.bribes_three);
        endBribesFourArray = getResources().getStringArray(R.array.bribes_four);
        endEnduranceArray = getResources().getStringArray(R.array.end_endurance);
        endEnduranceSanityArray = getResources().getStringArray(R.array.endurance_sanity);
        endFreeArray = getResources().getStringArray(R.array.end_home_good);
        endPrisonArray = getResources().getStringArray(R.array.end_home_bad);

        rankArray = getResources().getStringArray(R.array.end_ranks);
        rankArrayTwo = getResources().getStringArray(R.array.end_ranks_two);

        namesArray = getResources().getStringArray(R.array.creature_names);
        relativeDescArray = getResources().getStringArray(R.array.relatives_desc);
        relativesArray = getResources().getStringArray(R.array.relatives);

        nextDay.setText(R.string.destroy_creature);
        createCreature.setText(R.string.create_creature);
        postBurnContinue.setText(R.string.continue_game);
        strength.setText(R.string.strength);
        dexterity.setText(R.string.dexterity);
        intelligence.setText(R.string.intelligence);
        loyalty.setText(R.string.loyalty);
        beauty.setText(R.string.beauty);

        warning.setVisibility(View.GONE);
        confirmCreature.setVisibility(View.GONE);
        cancelCreature.setVisibility(View.GONE);
        postBurn.setVisibility(View.GONE);
        postBurnContinue.setVisibility(View.GONE);

        godStrengthArray = getResources().getStringArray(R.array.creation_strength);
        godDexterityArray = getResources().getStringArray(R.array.creature_dexterity);
        godIntelligenceArray = getResources().getStringArray(R.array.creation_intelligence);
        godloyaltyArray = getResources().getStringArray(R.array.creation_loyalty);
        godBeautyArray = getResources().getStringArray(R.array.creation_beauty);

        final String[] descEleven = getResources().getStringArray(R.array.d_11);
        final String[] descTwelve = getResources().getStringArray(R.array.d_12);
        final String[] descThirteen = getResources().getStringArray(R.array.d_13);
        final String[] descFourteen = getResources().getStringArray(R.array.d_14);
        final String[] descFifteen = getResources().getStringArray(R.array.d_15);

        zeroSanArray = getResources().getStringArray(R.array.no_sanity);
        zeroSanArrayTwo = getResources().getStringArray(R.array.no_sanity_two);
        zeroSanArrayThree = getResources().getStringArray(R.array.no_sanity_three);
        zeroSanArrayFour = getResources().getStringArray(R.array.no_sanity_four);
        zeroSanArrayFive = getResources().getStringArray(R.array.no_sanity_five);
        zeroQuirkArray = getResources().getStringArray(R.array.no_sanity_quirks);

        result.setText(getString(R.string.daily, descEleven[(randomTen())], descTwelve[(randomTen())], descThirteen[(randomTen())], descFourteen[(randomTen())], descFifteen[(randomTen())]));

//        keptTer = 60; keptPop = 60;
//        homeScore();
//        result.setText((strLaunch.get(23)));

        strengthResult.setText(R.string.building_creature);
        dexterityResult.setText(R.string.building_creature);
        intelligenceResult.setText(R.string.building_creature);
        loyaltyResult.setText(R.string.building_creature);
        beautyResult.setText(R.string.building_creature);

        strengthResult.setTextColor(getResources().getColor(R.color.Light_Gold));
        dexterityResult.setTextColor(getResources().getColor(R.color.Light_Gold));
        intelligenceResult.setTextColor(getResources().getColor(R.color.Light_Gold));
        loyaltyResult.setTextColor(getResources().getColor(R.color.Light_Gold));
        beautyResult.setTextColor(getResources().getColor(R.color.Light_Gold));

        final SharedPreferences pref = getContext().getSharedPreferences("SharedPref", 0);
        final SharedPreferences.Editor editor = pref.edit();

        final SharedPreferences statPref = getContext().getSharedPreferences("stats", 0);
        final SharedPreferences.Editor statEditor = statPref.edit();

        int strScan = statPref.getInt("strength", 100);
        int dexScan = statPref.getInt("dexterity", 100);
        final int tempScan = statPref.getInt("intelligence", 100);
        int obedScan = statPref.getInt("loyalty", 100);
        int charScan = statPref.getInt("beauty", 100);
        sanity = statPref.getInt("sanity", 100);
        endurance = statPref.getInt("endurance", 100);
        wealth = statPref.getInt("wealth", 100);
        anonymity = statPref.getInt("anonymity", 100);
        fluxInjections = statPref.getInt("injections", 10);

        burnCounter = pref.getInt("burnCounter", 0);

        postStr = pref.getBoolean("postStr", false);
        postDex = pref.getBoolean("postDex", false);
        postInt = pref.getBoolean("postInt", false);
        postBea = pref.getBoolean("postBea", false);
        postLoy = pref.getBoolean("postLoy", false);

        final boolean anonZero = pref.getBoolean("anonZero", false);

        if (fluxInjections < 1) {
            use_injection.setVisibility(View.GONE);
        }

        random = new Random();

        //Sets scans as known/unknown based on health of stat.
        int strRoll = random.nextInt(101 - 1);
        final int dexRoll = random.nextInt(101 - 1);
        final int tempRoll = random.nextInt(101 - 1);
        final int obedRoll = random.nextInt(101 - 1);
        int charRoll = random.nextInt(101 - 1);

        boolean dealStruck = pref.getBoolean("dealStruck", false);
        String dealStat = pref.getString("dealStat", null);

        endDaysLeft = pref.getInt("endDaysLeft", 0);
        endDaysCounter = pref.getInt("endDaysCounter", 0);
        endDaysLaunch = pref.getBoolean("endDaysLaunch", false);

        sanZero = pref.getBoolean("sanZero", false);


        if (endDaysLeft == 1) {
            result.setText(R.string.zero_endLaunch);
            editor.putBoolean("endDaysLaunch", true);
            editor.apply();
            createCreature.setVisibility(View.GONE);
            nextDay.setVisibility(View.GONE);
            confirmCreature.setVisibility(View.VISIBLE);
        }

        if (endDaysLeft == 2) {
            if (endDaysCounter == 0) {
                result.setText(R.string.zero_endWait);
                nextDay.setText(R.string.zero_end_nextDay);
                createCreature.setVisibility(View.INVISIBLE);
            }
            if (endDaysCounter == 1) {
                result.setText(R.string.zero_endLaunch);
                editor.putBoolean("endDaysLaunch", true);
                editor.apply();
                createCreature.setVisibility(View.GONE);
                nextDay.setVisibility(View.GONE);
                confirmCreature.setVisibility(View.VISIBLE);
            }
        }

        if (endDaysLeft == 3) {
            if (endDaysCounter == 0) {
                result.setText(R.string.zero_endWait);
                nextDay.setText(R.string.zero_end_nextDay);
                createCreature.setVisibility(View.INVISIBLE);
            }
            if (endDaysCounter == 1) {
                result.setText(R.string.zero_endWaitTwo);
                createCreature.setVisibility(View.INVISIBLE);
                nextDay.setText(R.string.zero_end_nextDay);
            }
            if (endDaysCounter == 2) {
                result.setText(R.string.zero_endLaunch);
                editor.putBoolean("endDaysLaunch", true);
                editor.apply();
                createCreature.setVisibility(View.GONE);
                nextDay.setVisibility(View.GONE);
                confirmCreature.setVisibility(View.VISIBLE);
            }
        }

        if (endDaysLeft > 0) {
            use_injection.setVisibility(View.GONE);
            editor.putInt("endDaysCounter", endDaysCounter + 1);
            editor.apply();
        }


        if (dealStruck) {
            if (dealStat.equals("Strength Infuser")) {
                dealStr = true;
                strChance = true;
                strVar = 5;
            }
            if (dealStat.equals("Dexterity Animator")) {
                dealDex = true;
                dexChance = true;
                dexVar = 5;
            }
            if (dealStat.equals("Intelligence Designer")) {
                dealInt = true;
                tempChance = true;
                intVar = 5;
            }
            if (dealStat.equals("Loyalty Inculcator")) {
                dealLoy = true;
                obedChance = true;
                obedVar = 5;
            }
            if (dealStat.equals("Beauty Sculptor")) {
                dealBea = true;
                charChance = true;
                beauVar = 5;
            }
        }

        insaneStats = new ArrayList<>();
        if (!dealStr) {
            insaneStats.add("str");
        }
        if (!dealDex) {
            insaneStats.add("dex");
        }
        if (!dealInt) {
            insaneStats.add("int");
        }
        if (!dealBea) {
            insaneStats.add("bea");
        }
        if (!dealLoy) {
            insaneStats.add("loy");
        }

        int st1 = random.nextInt(insaneStats.size());
        insaneStats.remove(st1);
        int st2 = random.nextInt(insaneStats.size());
        insaneStats.remove(st2);

        final Bundle c = new Bundle();

        if (strScan >= strRoll) {
            strChance = true;
        }

        if (dexScan >= dexRoll) {
            dexChance = true;
        }

        if (tempScan >= tempRoll) {
            tempChance = true;
        }

        if (obedScan >= obedRoll) {
            obedChance = true;
        }

        if (charScan >= charRoll) {
            charChance = true;
        }

        quirkRevealArray = getResources().getStringArray(R.array.quirk_reveal);
        quirkInjectArray = getResources().getStringArray(R.array.quirk_injection);

        final int numberOfQuirks = new Random().nextInt(4);

        while ((firstQuirk.equals(secondQuirk)) || (firstQuirk.equals(thirdQuirk)) ||
                (secondQuirk.equals(thirdQuirk))) {
            firstQuirk = quirkArray[new Random().nextInt(8)];
            secondQuirk = quirkArray[new Random().nextInt(8)];
            thirdQuirk = quirkArray[new Random().nextInt(8)];
        }

        //Uses global variables (quirkPlaces) to hold single instances of the quirkRevealArray int that has been selected by the quirkRoll method, which we are then passing on to the setQuirkColor method which is called in the postBurnContinue onClick when it is used to cycle through revealed quirks.
        quirkPlaceOne = quirkRoll(firstQuirk);
        quirkPlaceTwo = quirkRoll(secondQuirk);
        quirkPlaceThree = quirkRoll(thirdQuirk);

        if (sanZero) {
            ArrayList<String> list = new ArrayList<>();
            list.add("q1");
            list.add("q2");
            list.add("q3");
            int sub = random.nextInt(list.size());

            if (numberOfQuirks == 3) {
                list.remove(sub);
            } else {
                list.remove(sub);
                int sub2 = random.nextInt(list.size());
                list.remove(sub2);
            }

            if (list.contains("q1")) {
                firstQuirk = zeroQuirkArray[new Random().nextInt(8)];
            }
            if (list.contains("q2")) {
                secondQuirk = zeroQuirkArray[new Random().nextInt(8)];
            }
            if (list.contains("q3")) {
                thirdQuirk = zeroQuirkArray[new Random().nextInt(8)];
            }
            while ((firstQuirk.equals(secondQuirk)) || (firstQuirk.equals(thirdQuirk)) ||
                    (secondQuirk.equals(thirdQuirk))) {
                if (list.contains("q1")) {
                    firstQuirk = zeroQuirkArray[new Random().nextInt(8)];
                }
                if (list.contains("q2")) {
                    secondQuirk = zeroQuirkArray[new Random().nextInt(8)];
                }
                if (list.contains("q3")) {
                    thirdQuirk = zeroQuirkArray[new Random().nextInt(8)];
                }
            }
        }

        injectionList = new String[]{};

        switch (numberOfQuirks) {
            case 0:
                quirk_one.setVisibility(View.VISIBLE);
                break;
            case 1:
                quirk_one.setVisibility(View.VISIBLE);
                injectionList = new String[]{quirkRevealArray[quirkPlaceOne]};
                break;
            case 2:
                quirk_one.setVisibility(View.VISIBLE);
                quirk_two.setVisibility(View.VISIBLE);
                injectionList = new String[]{quirkRevealArray[quirkPlaceOne], quirkRevealArray[quirkPlaceTwo]};
                break;
            case 3:
                quirk_one.setVisibility(View.VISIBLE);
                quirk_two.setVisibility(View.VISIBLE);
                quirk_three.setVisibility(View.VISIBLE);
                injectionList = new String[]{quirkRevealArray[quirkPlaceOne], quirkRevealArray[quirkPlaceTwo], quirkRevealArray[quirkPlaceThree]};
        }

        switch (numberOfQuirks) {
            case 0:
                quirk_one.setText(R.string.none);
                hasNoQuirks = true;
                break;
            case 1:
                q1_runnable = new Runnable() {
                    @Override
                    public void run() {
                        quirk_one.setText(firstQuirk);
                    }
                };
                break;

            case 2:
                q1_runnable = new Runnable() {
                    @Override
                    public void run() {
                        quirk_one.setText(firstQuirk);
                    }
                };
                q2_runnable = new Runnable() {
                    @Override
                    public void run() {
                        quirk_two.setText(secondQuirk);
                    }
                };
                break;

            case 3:
                q1_runnable = new Runnable() {
                    @Override
                    public void run() {
                        quirk_one.setText(firstQuirk);
                    }
                };
                q2_runnable = new Runnable() {
                    @Override
                    public void run() {
                        quirk_two.setText(secondQuirk);
                    }
                };
                q3_runnable = new Runnable() {
                    @Override
                    public void run() {
                        quirk_three.setText(thirdQuirk);

                    }
                };
        }

        //Executes handler on quirk Runnables (above).
        quirkDelays();

        fluxInjections = statPref.getInt("injections", 10);

        //Keeps track of how many turns played. After a certain number, entropic events begin to take place.
        daysPlayed = pref.getInt("daysPlayed", 0);
        daysPlayed = (daysPlayed + 1);
        editor.putInt("daysPlayed", daysPlayed);
        editor.apply();

        if (daysPlayed > 5) {
            burnCounter = burnCounter + 1;
            editor.putInt("burnCounter", burnCounter);
            editor.apply();
            if (burnCounter > 4 && !anonZero)  {
                burnFail = new Random().nextInt(11 - 1) + 1;
                if (burnFail == 10) {
                    editor.putInt("burnCounter", 0);
                    editor.apply();
                }
            }
        }

        //next_day is set to not exit fragment if there is a burn fail, and we want to exit w/ the events triggered by zero anonymity.
        if (anonymity <=0) {
            burnFail = 0;
        }

        //Retrieves the current upgrade status of each scanner.
        upgradeStr = pref.getInt("upgradeStr", 0);
        upgradeDex = pref.getInt("upgradeDex", 0);
        upgradeTemp = pref.getInt("upgradeTemp", 0);
        upgradeObed = pref.getInt("upgradeObed", 0);
        upgradeChar = pref.getInt("upgradeChar", 0);

        rollStr();;
        rollDex();
        rollInt();
        rollLoy();
        rollBea();


        if (!dealStr && strInsaneText == null) {
            if (strChance) {
                str_runnable = new Runnable() {
                    @Override
                    public void run() {
                        strengthResult.setText(strengthText);
                        strengthResult.setTextColor(setColor(strengthColor));
                    }
                };
            } else {
                str_runnable = new Runnable() {
                    @Override
                    public void run() {
                        strengthResult.setText(R.string.unknown_stat);
                        strengthResult.setTextColor(getResources().getColor(R.color.Dark_Red));
                    }
                };
            }
        } else if (!dealStr){
            str_runnable = new Runnable() {
                @Override
                public void run() {
                    strengthResult.setText(strInsaneText);
                    strengthResult.setTextColor(setColor(strColorInsane));
                }
            };
        }

        if (!dealDex && dexInsaneText == null) {
            if (dexChance) {
                dex_runnable = new Runnable() {
                    @Override
                    public void run() {
                        dexterityResult.setText(dexterityText);
                        dexterityResult.setTextColor(setColor(dexterityColor));
                    }
                };
            } else {
                dex_runnable = new Runnable() {
                    @Override
                    public void run() {
                        dexterityResult.setText(R.string.unknown_stat);
                        dexterityResult.setTextColor(getResources().getColor(R.color.Dark_Red));
                    }
                };
            }
        } else if (!dealDex) {
            dex_runnable = new Runnable() {
                @Override
                public void run() {
                    dexterityResult.setText(dexInsaneText);
                    dexterityResult.setTextColor(setColor(dexColorInsane));
                }
            };
        }

        if (!dealInt && intInsaneText == null) {
            if (tempChance) {
                int_runnable = new Runnable() {
                    @Override
                    public void run() {
                        intelligenceResult.setText(intelligenceText);
                        intelligenceResult.setTextColor(setColor(intelligenceColor));
                    }
                };
            } else {
                int_runnable = new Runnable() {
                    @Override
                    public void run() {
                        intelligenceResult.setText(R.string.unknown_stat);
                        intelligenceResult.setTextColor(getResources().getColor(R.color.Dark_Red));
                    }
                };
            }
        } else if (!dealInt){
            int_runnable = new Runnable() {
                @Override
                public void run() {
                    intelligenceResult.setText(intInsaneText);
                    intelligenceResult.setTextColor(setColor(intColorInsane));
                }
            };
        }

        if (!dealLoy && loyInsaneText == null) {
            if (obedChance) {
                loy_runnable = new Runnable() {
                    @Override
                    public void run() {
                        loyaltyResult.setText(loyaltyText);
                        loyaltyResult.setTextColor(setColor(loyaltyColor));
                    }
                };
            } else {
                loy_runnable = new Runnable() {
                    @Override
                    public void run() {
                        loyaltyResult.setText(R.string.unknown_stat);
                        loyaltyResult.setTextColor(getResources().getColor(R.color.Dark_Red));
                    }
                };
            }
        } else if (!dealLoy){
            loy_runnable = new Runnable() {
                @Override
                public void run() {
                    loyaltyResult.setText(loyInsaneText);
                    loyaltyResult.setTextColor(setColor(loyColorInsane));
                }
            };
        }

        if (!dealBea && beaInsaneText == null) {
            if (charChance) {
                bea_runnable = new Runnable() {
                    @Override
                    public void run() {
                        beautyResult.setText(beautyText);
                        beautyResult.setTextColor(setColor(beautyColor));
                    }
                };
            } else {
                bea_runnable = new Runnable() {
                    @Override
                    public void run() {
                        beautyResult.setText(R.string.unknown_stat);
                        beautyResult.setTextColor(getResources().getColor(R.color.Dark_Red));
                    }
                };
            }
        } else if (!dealBea) {
            bea_runnable = new Runnable() {
                @Override
                public void run() {
                    beautyResult.setText(beaInsaneText);
                    beautyResult.setTextColor(setColor(beaColorInsane));
                }
            };
        }

        //Executes handler on stat Runnables (above).
        statDelays();

        use_injection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
                injectionResult.startAnimation(fadeIn);
                //Used in postBurn/Continue button. Defaults to false in global declaration.
                isInjectionUsed = true;
                mBurnFailCallback.onBurnFail(7, false);

                use_injection.setVisibility(View.GONE);
                nextDay.setVisibility(View.GONE);
                createCreature.setVisibility(View.GONE);
                injectionResult.setVisibility(View.VISIBLE);

                //Setting TextViews for description of quirks.
                injectionResult.setText(R.string.injection_result);
                //"Continue" Button

                //Revealing stats only if not already revealed. Rolling w/ out upgrades always.
                if (!sanZero) {
                    //stopDelay used to prevent "Unknown" from overriding revealed stat.
                    stopDelay();
                    if (injectionList.length == 1) {
                        quirk_one.setText(firstQuirk);
                    }
                    if (injectionList.length == 2) {
                        quirk_one.setText(firstQuirk);
                        quirk_two.setText(secondQuirk);
                    }
                    if (injectionList.length == 3) {
                        quirk_one.setText(firstQuirk);
                        quirk_two.setText(secondQuirk);
                        quirk_three.setText(thirdQuirk);
                    }

                    injectionResult.setText(R.string.injection_result);
                    if (!strChance || postStr) {
                        strengthText = godStrengthArray[randomize()];
                        strengthResult.setText(strengthText);
                        strengthResult.setTextColor(setColor(creationRoll));
                        strVar = creationRoll;
                        strChance = true;
                        strengthColor = creationRoll;
                    } else {
                        strengthResult.setText(strengthText);
                        strengthResult.setTextColor(setColor(strengthColor));
                    }
                    if (!dexChance || postDex) {
                        dexterityText = godDexterityArray[randomize()];
                        dexterityResult.setText(dexterityText);
                        dexterityResult.setTextColor(setColor(creationRoll));
                        dexVar = creationRoll;
                        dexChance = true;
                        dexterityColor = creationRoll;
                    } else {
                        dexterityResult.setText(dexterityText);
                        dexterityResult.setTextColor(setColor(dexterityColor));
                    }
                    if (!tempChance || postInt) {
                        intelligenceText = godIntelligenceArray[randomize()];
                        intelligenceResult.setText(intelligenceText);
                        intelligenceResult.setTextColor(setColor(creationRoll));
                        intVar = creationRoll;
                        tempChance = true;
                        intelligenceColor = creationRoll;
                    } else {
                        intelligenceResult.setText(intelligenceText);
                        intelligenceResult.setTextColor(setColor(intelligenceColor));
                    }
                    if (!charChance || postBea) {
                        beautyText = godBeautyArray[randomize()];
                        beautyResult.setText(beautyText);
                        beautyResult.setTextColor(setColor(creationRoll));
                        beauVar = creationRoll;
                        charChance = true;
                        beautyColor = creationRoll;
                    } else {
                        beautyResult.setText(beautyText);
                        beautyResult.setTextColor(setColor(beautyColor));
                    }
                    if (!obedChance || postLoy) {
                        loyaltyText = godloyaltyArray[randomize()];
                        loyaltyResult.setText(loyaltyText);
                        loyaltyResult.setTextColor(setColor(creationRoll));
                        obedVar = creationRoll;
                        obedChance = true;
                        loyaltyColor = creationRoll;
                    } else {
                        loyaltyResult.setText(loyaltyText);
                        loyaltyResult.setTextColor(setColor(loyaltyColor));
                    }
                } else {
                    injectionResult.setText(R.string.zero_san_injection);
                }
                postBurnContinue.setVisibility(View.VISIBLE);
            }
        });

        nextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (endDaysLeft > 0 && !endDaysLaunch) {
                    stopDelay();
                    mOnRepeatResults.onRepeatResults();
                }
                if (burnFail != 10 && endDaysLeft <= 0) {
                    stopDelay();
                    mNextTurn.onNextTurn();
                }
                if (burnFail == 10 && anonymity > 0 && endurance > 0 && endDaysLeft <= 0) {
                    //Used to prevent dual button (confirm creature/vent incin) from triggering latter.
                    incineratorFail = true;
                    Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
                    warning.startAnimation(fadeIn);

                    warning.setText(R.string.bad_fire);
                    warning.setVisibility(View.VISIBLE);

                    confirmCreature.setText(R.string.fire_choice_one);
                    cancelCreature.setText(R.string.fire_choice_two);
                    confirmCreature.setVisibility(View.VISIBLE);
                    cancelCreature.setVisibility(View.VISIBLE);

                    nextDay.setVisibility(View.GONE);
                    createCreature.setVisibility(View.GONE);
                    use_injection.setVisibility(View.INVISIBLE);
                    injectionResult.setVisibility(View.GONE);
                    result.setVisibility(View.GONE);
                }
            }
        });

        createCreature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                keptPop = 100; keptTer = 100;
//                warning.setText(endRank());

                Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
//                warning.startAnimation(fadeIn);

                warning.setText(R.string.warning_dialogue);
                confirmCreature.setText(R.string.confirm_creature);
                cancelCreature.setText(R.string.cancel_creature);
                nextDay.setVisibility(View.GONE);
                createCreature.setVisibility(View.GONE);
                use_injection.setVisibility(View.GONE);
                postBurn.setVisibility(View.GONE);
                injectionResult.setVisibility(View.GONE);
                warning.setVisibility(View.VISIBLE);
                confirmCreature.setVisibility(View.VISIBLE);
                cancelCreature.setVisibility(View.VISIBLE);

                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }
        });

        //Used for vent on failed incinerator.
        confirmCreature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopDelay();
                Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
                warning.startAnimation(fadeIn);

                endDaysLaunch = pref.getBoolean("endDaysLaunch", false);
                if (endDaysLaunch) {
                    nextDay.setVisibility(View.GONE);
                    createCreature.setVisibility(View.GONE);
                    use_injection.setVisibility(View.GONE);
                    postBurn.setVisibility(View.GONE);
                    injectionResult.setVisibility(View.GONE);
                    warning.setVisibility(View.VISIBLE);
                }

                confirmCreature.setVisibility(View.GONE);
                cancelCreature.setVisibility(View.GONE);
                incinerator_continue.setVisibility(View.VISIBLE);

                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });

                //To vent incinerator, causing loss of anonymity.
                if (incineratorFail && endDaysLeft == 0) {
                    //Sets a different TextView and a roll for san/end if anonymity is at 0.
                    if (anonymity <= 0) {
                        warning.setText(getString(R.string.fire_one_result_two));
                        if (sanity > 0 && endurance > 0) {
                            mBurnFailCallback.onBurnFail(2, false);
                        }
                        if (sanity > 0) {
                            mBurnFailCallback.onBurnFail(3, false);
                        }
                        if (endurance > 0) {
                            mBurnFailCallback.onBurnFail(4, false);
                        }
                    } else {
                        warning.setText(getString(R.string.fire_one_result));
                        mBurnFailCallback.onBurnFail(1, false);
                    }
                } else {
                    //Stops handler if we launch creature before quirks/stats load.
                    stopDelay();
                    //End Game
                    creatureName = (namesArray[random.nextInt(50)]);
                    int rr = random.nextInt(15);
                    warning.setText(getString(R.string.final_preface, creatureName, relativeDescArray[rr], relativesArray[rr]));
                    endGame = true;

                    if (!strChance) {
                        strengthResult.setText(godStrengthArray[randomize()]);
                        strengthResult.setTextColor(setColor(creationRoll));
                        strengthResult.startAnimation(fadeIn);
                        strVar = creationRoll;
                    }
                    if (!dexChance) {
                        dexterityResult.setText(godDexterityArray[randomize()]);
                        dexterityResult.setTextColor(setColor(creationRoll));
                        dexterityResult.startAnimation(fadeIn);
                        dexVar = creationRoll;
                    }
                    if (!tempChance) {
                        intelligenceResult.setText(godIntelligenceArray[randomize()]);
                        intelligenceResult.setTextColor(setColor(creationRoll));
                        intelligenceResult.startAnimation(fadeIn);
                        intVar = creationRoll;
                    }
                    if (!charChance) {
                        beautyResult.setText(godBeautyArray[randomize()]);
                        beautyResult.setTextColor(setColor(creationRoll));
                        beautyResult.startAnimation(fadeIn);
                        beauVar = creationRoll;
                    }
                    if (!obedChance) {
                        loyaltyResult.setText(godloyaltyArray[randomize()]);
                        loyaltyResult.setTextColor(setColor(creationRoll));
                        loyaltyResult.startAnimation(fadeIn);
                        obedVar = creationRoll;
                    }

                    if (strChance) {
                        strengthResult.setText(strengthText);
                        strengthResult.setTextColor(setColor(strengthColor));
                    }
                    if (dexChance) {
                        dexterityResult.setText(dexterityText);
                        dexterityResult.setTextColor(setColor(dexterityColor));
                    }
                    if (tempChance) {
                        intelligenceResult.setText(intelligenceText);
                        intelligenceResult.setTextColor(setColor(intelligenceColor));
                    }
                    if (obedChance) {
                        loyaltyResult.setText(loyaltyText);
                        loyaltyResult.setTextColor(setColor(loyaltyColor));
                    }
                    if (charChance) {
                        beautyResult.setText(beautyText);
                        beautyResult.setTextColor(setColor(beautyColor));
                    }

                    //Necessary to prevent crashing.
                    if (injectionList.length != 0) {
                        switch (injectionList.length) {
                            case 1:
                                setQuirkColor(quirkPlaceOne, quirk_one);
                                quirk_one.setText(injectionList[0]);
                                break;
                            case 2:
                                setQuirkColor(quirkPlaceOne, quirk_one);
                                quirk_one.setText(injectionList[0]);
                                setQuirkColor(quirkPlaceTwo, quirk_two);
                                quirk_two.setText(injectionList[1]);
                                break;
                            case 3:
                                setQuirkColor(quirkPlaceOne, quirk_one);
                                quirk_one.setText(injectionList[0]);
                                setQuirkColor(quirkPlaceTwo, quirk_two);
                                quirk_two.setText(injectionList[1]);
                                setQuirkColor(quirkPlaceThree, quirk_three);
                                quirk_three.setText(injectionList[2]);
                        }
                    }
                }
            }

        });

        //Used for "dirty hands" on failed incinerator.
        cancelCreature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastDitch) {
                    altCounter++;
                    warning.setText(getString(R.string.end_obed_decline));
                    incinerator_continue.setVisibility(View.VISIBLE);
                    confirmCreature.setVisibility(View.GONE);
                    cancelCreature.setVisibility(View.GONE);
                } else {
                    if (incineratorFail) {
                        if (sanity > 0 && endurance > 0) {
                            mBurnFailCallback.onBurnFail(2, false);
                        }
                        if (sanity > 0 && endurance <= 0) {
                            mBurnFailCallback.onBurnFail(3, false);
                        }
                        if (endurance > 0 && sanity <= 0) {
                            mBurnFailCallback.onBurnFail(4, false);
                        }
                        Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
                        warning.startAnimation(fadeIn);
                        warning.setText(R.string.fire_two_result);
                        confirmCreature.setVisibility(View.GONE);
                        cancelCreature.setVisibility(View.GONE);
                        incinerator_continue.setVisibility(View.VISIBLE);
                    } else {
                        confirmCreature.setVisibility(View.GONE);
                        cancelCreature.setVisibility(View.GONE);
                        nextDay.setVisibility(View.VISIBLE);
                        createCreature.setVisibility(View.VISIBLE);
                        postBurnContinue.setVisibility(View.GONE);
                        warning.setVisibility(View.GONE);
                        if (!spentInjection && fluxInjections >= 1) {
                            use_injection.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });

        incinerator_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopDelay();
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });

                if (!incineratorFail && !gameEnd) {
                    Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
                    warning.startAnimation(fadeIn);
                }
                //This is our STANDARD continue button after daily events.
                if (!endGame) {
                    mNextTurn.onNextTurn();

                } else {
                    assignFinalDescription();
                    //Converting String[] to ArrayList to access values.
                    quirkList = Arrays.asList(injectionList);

                    //Checks if there are any quirks.
                    if (quirkList.size() > 0) {
                        if (quirkList.contains("Sonorous vocalist")) {
                            hasVocalist = 1;
                        }
                        if (quirkList.contains("Screeching vocalist")) {
                            hasVocalist = 2;
                        }
                        if (quirkList.contains("Soothing practitioner")) {
                            hasPractice = 1;
                        }
                        if (quirkList.contains("Unnerving practitioner")) {
                            hasPractice = 2;
                        }
                        if (quirkList.contains("Controlled berserk")) {
                            hasBerserk = 1;
                        }
                        if (quirkList.contains("Unhinged berserk")) {
                            hasBerserk = 2;
                        }
                        if (quirkList.contains("Buff swoll")) {
                            hasSwoll = 1;
                        }
                        if (quirkList.contains("Glamour muscle swoll")) {
                            hasSwoll = 2;
                        }
                        if (quirkList.contains("Sentimentally avaricious")) {
                            hasTalker = 1;
                        }
                        if (quirkList.contains("Voraciously avaricious")) {
                            hasTalker = 2;
                        }
                        if (quirkList.contains("Flexibly buoyant")) {
                            hasBuoyant = 1;
                        }
                        if (quirkList.contains("Manically buoyant")) {
                            hasBuoyant = 2;
                        }
                        if (quirkList.contains("Erudite bibliophile")) {
                            hasBiblio = 1;
                        }
                        if (quirkList.contains("Tabloid bibliophile")) {
                            hasBiblio = 2;
                        }
                        if (quirkList.contains("Ethical exhibitionist")) {
                            hasExhibitionist = 1;
                        }
                        if (quirkList.contains("Narcissistic exhibitionist")) {
                            hasExhibitionist = 2;
                        }
                    }

                    if (endGame && endRevealCounter == 0) {
                        if (hasBerserk > 0 && qc1) {
                            quirkOrder("Controlled berserk", "Unhinged berserk");
                            if (c1 && qPlace == 0 || (c2 && qPlace == 1) || (c3 && qPlace == 2)) {
                                if (hasBerserk == 2 && dexVar == 0) {
                                    warning.setText(getString(R.string.end_two, getString(R.string.berserk_bad), getString(R.string.dex_capLow)));
                                }
                                if (hasBerserk == 2 && dexVar > 0) {
                                    dexVar = quirkMods(dexVar, false, dexterityResult, godDexterityArray);
                                    warning.setText(R.string.berserk_bad);
                                    upTwo.setVisibility(View.VISIBLE);
                                    upTwo.setText(R.string.endDowngrade);
                                    upTwo.setTextColor(getResources().getColor(R.color.Red_Alt));
                                }
                                if (hasBerserk == 1 && strVar == 5) {
                                    warning.setText(getString(R.string.end_two, getString(R.string.berserk_good), getString(R.string.str_capHigh)));
                                }
                                if (hasBerserk == 1 && strVar < 5) {
                                    strVar = quirkMods(strVar, true, strengthResult, godStrengthArray);
                                    warning.setText(R.string.berserk_good);
                                    upOne.setVisibility(View.VISIBLE);
                                    upOne.setText(R.string.endUpgrade);
                                    upOne.setTextColor(getResources().getColor(R.color.Dark_Green));
                                }


                                //Takes care of second launch w/ appearance order too.
                                qc1 = false;
                                qPlace++;
                                return;
                            }
                        }
                        if (hasSwoll > 0 && qc2) {
                            quirkOrder("Glamour muscle swoll", "Buff swoll");
                            if (c1 && qPlace == 0 || (c2 && qPlace == 1) || (c3 && qPlace == 2)) {
                                if (hasSwoll == 2 && dexVar == 0) {
                                    warning.setText(getString(R.string.end_two, getString(R.string.swoll_bad), getString(R.string.dex_capLow)));
                                }
                                if (hasSwoll == 2 && dexVar > 0) {
                                    dexVar = quirkMods(dexVar, false, dexterityResult, godDexterityArray);
                                    warning.setText(R.string.swoll_bad);
                                    upTwo.setVisibility(View.VISIBLE);
                                    upTwo.setText(R.string.endDowngrade);
                                    upTwo.setTextColor(getResources().getColor(R.color.Red_Alt));
                                }
                                if (hasSwoll == 1 && strVar == 5) {
                                    warning.setText(getString(R.string.end_two, getString(R.string.swoll_good), getString(R.string.str_capHigh)));
                                }
                                if (hasSwoll == 1 && strVar < 5) {
                                    strVar = quirkMods(strVar, true, strengthResult, godStrengthArray);
                                    warning.setText(R.string.swoll_good);
                                    upOne.setVisibility(View.VISIBLE);
                                    upOne.setText(R.string.endUpgrade);
                                    upOne.setTextColor(getResources().getColor(R.color.Dark_Green));
                                }
                                qc2 = false;
                                qPlace++;
                                return;
                            }
                        }
                        if (hasBuoyant > 0 && qc3) {
                            quirkOrder("Flexibly buoyant", "Manically buoyant");
                            if (c1 && qPlace == 0 || (c2 && qPlace == 1) || (c3 && qPlace == 2)) {
                                if (hasBuoyant == 2 && dexVar == 0) {
                                    warning.setText(getString(R.string.end_two, getString(R.string.buoyant_bad), getString(R.string.dex_capLow)));
                                }
                                if (hasBuoyant == 2 && dexVar > 0) {
                                    dexVar = quirkMods(dexVar, false, dexterityResult, godDexterityArray);
                                    warning.setText(R.string.buoyant_bad);
                                    upTwo.setVisibility(View.VISIBLE);
                                    upTwo.setText(R.string.endDowngrade);
                                    upTwo.setTextColor(getResources().getColor(R.color.Red_Alt));
                                }
                                if (hasBuoyant == 1 && dexVar == 5) {
                                    warning.setText(getString(R.string.end_two, getString(R.string.buoyant_good), getString(R.string.dex_capHigh)));
                                }
                                if (hasBuoyant == 1 && dexVar < 5) {
                                    warning.setText(R.string.buoyant_good);
                                    dexVar = quirkMods(dexVar, true, dexterityResult, godDexterityArray);
                                    upTwo.setVisibility(View.VISIBLE);
                                    upTwo.setText(R.string.endUpgrade);
                                    upTwo.setTextColor(getResources().getColor(R.color.Dark_Green));
                                }
                                qc3 = false;
                                qPlace++;
                                return;
                            }
                        }
                        if (hasBiblio > 0 && qc4) {
                            quirkOrder("Erudite bibliophile", "Tabloid bibliophile");
                            if (c1 && qPlace == 0 || (c2 && qPlace == 1) || (c3 && qPlace == 2)) {
                                if (hasBiblio == 2 && intVar == 0) {
                                    warning.setText(getString(R.string.end_two, getString(R.string.biblio_bad), getString(R.string.int_capLow)));
                                }
                                if (hasBiblio == 2 && intVar > 0) {
                                    warning.setText(R.string.biblio_bad);
                                    intVar = quirkMods(intVar, false, intelligenceResult, godIntelligenceArray);
                                    upThree.setVisibility(View.VISIBLE);
                                    upThree.setText(R.string.endDowngrade);
                                    upThree.setTextColor(getResources().getColor(R.color.Red_Alt));
                                }
                                if (hasBiblio == 1 && intVar == 5) {
                                    warning.setText(getString(R.string.end_two, getString(R.string.biblio_good), getString(R.string.int_capHigh)));
                                }
                                if (hasBiblio == 1 && intVar < 5) {
                                    warning.setText(R.string.biblio_good);
                                    intVar = quirkMods(intVar, true, intelligenceResult, godIntelligenceArray);
                                    upThree.setVisibility(View.VISIBLE);
                                    upThree.setText(R.string.endUpgrade);
                                    upThree.setTextColor(getResources().getColor(R.color.Dark_Green));
                                }
                                qc4 = false;
                                qPlace++;
                                return;
                            }
                        }
                        if (hasVocalist > 0 && qc5) {
                            quirkOrder("Sonorous vocalist", "Screeching vocalist");
                            if (c1 && qPlace == 0 || (c2 && qPlace == 1) || (c3 && qPlace == 2)) {
                                if (hasVocalist == 2 && sanity <= 0) {
                                    warning.setText(getString(R.string.end_two, getString(R.string.vocalist_bad), getString(R.string.san_cap)));
                                    mBurnFailCallback.onBurnFail(5, false);
                                }
                                if (hasVocalist == 2 && sanity > 0) {
                                    warning.setText(R.string.vocalist_bad);
                                    mBurnFailCallback.onBurnFail(5, false);
                                }
                                if (hasVocalist == 1) {
                                    warning.setText(R.string.vocalist_good);
                                    mBurnFailCallback.onBurnFail(5, true);
                                }
                                qc5 = false;
                                qPlace++;
                                return;
                            }
                        }
                        if (hasPractice > 0 && qc6) {
                            quirkOrder("Soothing practitioner", "Unnerving practitioner");
                            if (c1 && qPlace == 0 || (c2 && qPlace == 1) || (c3 && qPlace == 2)) {
                                if (hasPractice == 2 && sanity <= 0) {
                                    warning.setText(getString(R.string.end_two, getString(R.string.reiki_bad), getString(R.string.san_cap)));
                                    mBurnFailCallback.onBurnFail(5, false);
                                }
                                if (hasPractice == 2 && sanity > 0) {
                                    warning.setText(R.string.reiki_bad);
                                    mBurnFailCallback.onBurnFail(5, false);
                                }
                                if (hasPractice == 1) {
                                    warning.setText(R.string.reiki_good);
                                    mBurnFailCallback.onBurnFail(5, true);
                                }
                                qc6 = false;
                                qPlace++;
                                return;
                            }

                        }
                        if (hasTalker > 0 && qc7) {
                            quirkOrder("Sentimentally avaricious", "Voraciously avaricious");
                            if (c1 && qPlace == 0 || (c2 && qPlace == 1) || (c3 && qPlace == 2)) {
                                if (hasTalker == 2 && obedVar <= 0) {
                                    warning.setText(getString(R.string.end_two, getString(R.string.talker_bad), getString(R.string.loy_capLow)));
                                }
                                if (hasTalker == 2 && obedVar > 0) {
                                    obedVar = quirkMods(obedVar, false, loyaltyResult, godloyaltyArray);
                                    warning.setText(R.string.talker_bad);
                                    upFive.setVisibility(View.VISIBLE);
                                    upFive.setText(R.string.endDowngrade);
                                    upFive.setTextColor(getResources().getColor(R.color.Red_Alt));
                                }
                                if (hasTalker == 1 && obedVar == 5) {
                                    warning.setText(getString(R.string.end_two, getString(R.string.talker_good), getString(R.string.loy_capHigh)));
                                }
                                if (hasTalker == 1 && obedVar < 5) {
                                    warning.setText(R.string.talker_good);
                                    obedVar = quirkMods(obedVar, true, loyaltyResult, godloyaltyArray);
                                    upFive.setVisibility(View.VISIBLE);
                                    upFive.setText(R.string.endUpgrade);
                                    upFive.setTextColor(getResources().getColor(R.color.Dark_Green));
                                }
                                qc7 = false;
                                qPlace++;
                                return;
                            }
                        }
                        if (hasExhibitionist > 0 && qc8) {
                            quirkOrder("Ethical exhibitionist", "Narcissistic exhibitionist");
                            if (c1 && qPlace == 0 || (c2 && qPlace == 1) || (c3 && qPlace == 2)) {
                                if (hasExhibitionist == 2 && obedVar <= 0) {
                                    warning.setText(getString(R.string.end_two, getString(R.string.exhibit_bad), getString(R.string.loy_capLow)));
                                }
                                if (hasExhibitionist == 2 && obedVar > 0) {
                                    obedVar = quirkMods(obedVar, false, loyaltyResult, godloyaltyArray);
                                    warning.setText(R.string.exhibit_bad);
                                    upFive.setVisibility(View.VISIBLE);
                                    upFive.setText(R.string.endDowngrade);
                                    upFive.setTextColor(getResources().getColor(R.color.Red_Alt));
                                }
                                if (hasExhibitionist == 1 && obedVar == 5) {
                                    warning.setText(getString(R.string.end_two, getString(R.string.exhibit_good), getString(R.string.loy_capHigh)));
                                }
                                if (hasExhibitionist == 1 && obedVar < 5) {
                                    warning.setText(R.string.exhibit_good);
                                    obedVar = quirkMods(obedVar, true, loyaltyResult, godloyaltyArray);
                                    upFive.setVisibility(View.VISIBLE);
                                    upFive.setText(R.string.endUpgrade);
                                    upFive.setTextColor(getResources().getColor(R.color.Dark_Green));
                                }
                                qc8 = false;
                                qPlace++;
                                return;
                            }
                        }
                    }

                    //Calculating end game numbers.
                    if (!percentSaved) {
                        //Setting percentages of domination here, post-quirk mods, to be called below.
                        strWeight = weightPct(strVar);
                        dexWeight = weightPct(dexVar);
                        intWeight = weightPct(intVar);
                        beaWeight = weightPct(beauVar);

                        strPct = setVarPercent(strVar);
                        dexPct = setVarPercent(dexVar);
                        intPct = setVarPercent(intVar);
                        loyPct = setVarPercent(obedVar);
                        beaPct = setVarPercent(beauVar);

                        rulePct = (strPct + dexPct) / (strWeight + dexWeight);
                        popPct = (intPct + beaPct) / (intWeight + beaWeight);

                        stealPct = setVarPercent(reverseIndex(obedVar));

                        stealTer = (rulePct * stealPct) / 100;
                        stealPop = (popPct * stealPct) / 100;

                        keptTer = rulePct - stealTer;
                        keptPop = popPct - stealPop;
                        percentSaved = true;
                    }

                    if (endDescCounter == 0) {
                        int abTer = Math.abs(stealTer);
                        int abSup = Math.abs(stealPop);
                        scrollView.post(new Runnable() {
                            @Override
                            public void run() {
                                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                            }
                        });

                        //Clearing any stat changes from quirk reveal.
                        mClearStats.onClearStats();
                        switch (altCounter) {
                            case 0:
                                warning.setText(R.string.transition);
                                altCounter++;
                                break;
                            case 1:
                                warning.setText(finalRevealArray.get(0));
                                altCounter++;
                                break;
                            case 2:
                                endStats();
                                if (!launchFailure) {
                                    warning.setText(getString(R.string.end_arrays, finalRevealArray.get(1), finalRevealArray.get(2), finalRevealArray.get(3), finalRevealArray.get(4), getString(R.string.end_two, strLaunch.get(strEndArray), dexLaunch.get(dexEndArray))));
                                } else {
                                    //No strEndArray because it involves creature leaving nest.
                                    warning.setText(getString(R.string.end_arrays, finalRevealArray.get(1), finalRevealArray.get(2), finalRevealArray.get(3), finalRevealArray.get(4), strLaunch.get(strEndArray)));
                                }
                                altCounter++;
                                break;
                            case 3:
                                //End of game loss.
                                if (launchFailure) {
                                    warning.setText(R.string.launch_failure);
                                } else {
                                    //Launch success continues game here.
                                    setEnd(warning);
                                }
                                altCounter++;
                                break;
                            case 4:
                                if (!launchFailure) {
                                    editor.putBoolean("showEndStats", true);
                                    editor.apply();
                                    warning.setText(getString(R.string.summary_one, rulePct + "%", popPct + "%"));
                                    mOnShowPercentages.onShowPercentages(rulePct, popPct, 500, 0, true, true);
                                    altCounter++;
                                } else {
                                    keptTer = 0;
                                    keptPop = 0;
                                    mOnShowPercentages.onShowPercentages(500, keptPop, 0, 0, true, true);
                                    setEndLossArrays();
                                    warning.setText(endStringLoss);
                                    altCounter++;
                                    altCounter++;
                                    altCounter++;
                                    altCounter++;
                                }
                                break;
                            case 5:
                                if (obedVar == 5) {
                                    warning.setText(tempEndArray[0]);
                                } else {
                                    warning.setText(getString(R.string.summary_two, tempEndArray[reverseIndex(obedVar)], abTer + "%", abSup + "%"));
                                }
                                mOnShowPercentages.onShowPercentages(keptTer, keptPop, stealTer, stealPop, false, false);
                                altCounter++;
                                break;
                            case 6:
                                setEndArrays();
                                warning.setText(endString);
                                if (newTer >= 0 && newSupport <= 0) {
                                    mOnShowPercentages.onShowPercentages(keptTer, keptPop, newTer, newSupport, true, false);
                                }
                                if (newTer <= 0 && newSupport >= 0) {
                                    mOnShowPercentages.onShowPercentages(keptTer, keptPop, newTer, newSupport, false, true);
                                }
                                if (newTer <= 0 && newSupport <= 0) {
                                    mOnShowPercentages.onShowPercentages(keptTer, keptPop, newTer, newSupport, false, false);
                                }
                                if (newTer >= 0 && newSupport >= 0) {
                                    mOnShowPercentages.onShowPercentages(keptTer, keptPop, newTer, newSupport, true, true);
                                }
                                gameEnd = true;
                                altCounter++;
                                break;
                            case 7:
                                mOnShowPercentages.onShowPercentages(keptTer, keptPop, 1000, newSupport, false, false);
                                warning.setText(endStringTwo);
                                altCounter++;
                                break;
                            case 8:
                                setScores();
                                warning.clearAnimation();
                                mEndGameCallback.onEndGame();
                        }
                    }
                }
            }
        });

        //Used to continue after (A)Failed incinerator and (b)Final quirk reveal.
        postBurnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
                if (isInjectionUsed && !sanZero) {
                    Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
                    switch (injectionList.length) {
                        case 0:
                            if (quirkCounter == 0) {
                                postBurnContinue.setVisibility(View.GONE);
                                nextDay.setVisibility(View.VISIBLE);
                                createCreature.setVisibility(View.VISIBLE);
                                injectionResult.setVisibility(View.GONE);
                            }
                            spentInjection = true;
                            break;
                        case 1:
                            if (quirkCounter == 0) {
                                setQuirkColor(quirkPlaceOne, quirk_one);
                                quirk_one.setText(injectionList[0]);
                                injectionResult.setText(quirkInjectArray[quirkPlaceOne]);
                                quirk_one.startAnimation(fadeIn);
                            }
                            if (quirkCounter == 1) {
                                postBurnContinue.setVisibility(View.GONE);
                                nextDay.setVisibility(View.VISIBLE);
                                createCreature.setVisibility(View.VISIBLE);
                                injectionResult.setVisibility(View.GONE);

                            }
                            quirkCounter++;
                            spentInjection = true;
                            break;
                        case 2:
                            if (quirkCounter == 0) {
                                setQuirkColor(quirkPlaceOne, quirk_one);
                                quirk_one.setText(injectionList[0]);
                                injectionResult.setText(quirkInjectArray[quirkPlaceOne]);
                                quirk_one.startAnimation(fadeIn);
                                ;
                                injectionResult.startAnimation(fadeIn);
                            }
                            if (quirkCounter == 1) {
                                setQuirkColor(quirkPlaceTwo, quirk_two);
                                quirk_two.setText(injectionList[1]);
                                injectionResult.setText(quirkInjectArray[quirkPlaceTwo]);
                                quirk_two.startAnimation(fadeIn);
                                injectionResult.startAnimation(fadeIn);
                            }
                            if (quirkCounter == 2) {
                                postBurnContinue.setVisibility(View.GONE);
                                nextDay.setVisibility(View.VISIBLE);
                                createCreature.setVisibility(View.VISIBLE);
                                injectionResult.setVisibility(View.GONE);
                            }
                            quirkCounter++;
                            spentInjection = true;
                            break;
                        case 3:
                            if (quirkCounter == 0) {
                                setQuirkColor(quirkPlaceOne, quirk_one);
                                quirk_one.setText(injectionList[0]);
                                injectionResult.setText(quirkInjectArray[quirkPlaceOne]);
                                quirk_one.startAnimation(fadeIn);
                                injectionResult.startAnimation(fadeIn);
                            }
                            if (quirkCounter == 1) {
                                setQuirkColor(quirkPlaceTwo, quirk_two);
                                quirk_two.setText(injectionList[1]);
                                injectionResult.setText(quirkInjectArray[quirkPlaceTwo]);
                                quirk_two.startAnimation(fadeIn);
                                injectionResult.startAnimation(fadeIn);
                            }
                            if (quirkCounter == 2) {
                                setQuirkColor(quirkPlaceThree, quirk_three);
                                quirk_three.setText(injectionList[2]);
                                injectionResult.setText(quirkInjectArray[quirkPlaceThree]);
                                quirk_three.startAnimation(fadeIn);
                                injectionResult.startAnimation(fadeIn);
                            }
                            if (quirkCounter == 3) {
                                postBurnContinue.setVisibility(View.GONE);
                                nextDay.setVisibility(View.VISIBLE);
                                createCreature.setVisibility(View.VISIBLE);
                                injectionResult.setVisibility(View.GONE);
                            }
                            quirkCounter++;
                            spentInjection = true;
                    }
                } else if (isInjectionUsed) {
                    nextDay.setVisibility(View.VISIBLE);
                    createCreature.setVisibility(View.VISIBLE);
                    injectionResult.setVisibility(View.GONE);
                    postBurnContinue.setVisibility(View.GONE);
                } else {
                    mNextTurn.onNextTurn();

                }
            }
        });
        return root;
    }

    public void statDelays() {
        handler.postDelayed(str_runnable, randomizeDelay(3000, 500));
        handler.postDelayed(dex_runnable, randomizeDelay(3000, 500));
        handler.postDelayed(int_runnable, randomizeDelay(3000, 500));
        handler.postDelayed(loy_runnable, randomizeDelay(3000, 500));
        handler.postDelayed(bea_runnable, randomizeDelay(3000, 500));
    }

    //Assigns global random delays for all quirk reveals and stores the largest for use in disabling action buttons.
    public void quirkDelays() {
        q1Delay = randomizeDelay(3000, 500);
        q2Delay = randomizeDelay(3000, 500);
        q3Delay = randomizeDelay(3000, 500);
        handler.postDelayed(q1_runnable, q1Delay);
        handler.postDelayed(q2_runnable, q2Delay);
        handler.postDelayed(q3_runnable, q3Delay);

        if (!hasNoQuirks) {
            largestDelay = Collections.max(Arrays.asList(q1Delay, q2Delay, q3Delay));
        }
    }

    //If this is not interrupting as it should, check its instantiation as a global variable.
    public void stopDelay() {
        handler.removeCallbacks(str_runnable);
        handler.removeCallbacks(dex_runnable);
        handler.removeCallbacks(int_runnable);
        handler.removeCallbacks(loy_runnable);
        handler.removeCallbacks(bea_runnable);

        handler.removeCallbacks(q1_runnable);
        handler.removeCallbacks(q2_runnable);
        handler.removeCallbacks(q3_runnable);

        handler.removeCallbacksAndMessages(null);
    }

    public void rollStr() {
        strengthText = (statText(upgradeStr, godStrengthArray));
        strengthColor = creationRoll;
        strVar = creationRoll;
        if (sanZero && insaneStats.contains("str")) {
            strInsaneText = (statText(upgradeStr, zeroSanArray));
            strColorInsane = setRandomColor();
        }
        if (dealStr) {
            strengthText = godStrengthArray[5];
            strengthColor = setColor(5);
            strengthResult.setText(strengthText);
            strengthResult.setTextColor(strengthColor);
            strVar = 5;
        }
    }

    public void rollDex() {
        dexterityText = (statText(upgradeDex, godDexterityArray));
        dexterityColor = creationRoll;
        dexVar = creationRoll;
        if (sanZero && insaneStats.contains("dex")) {
            dexInsaneText = (statText(upgradeDex, zeroSanArrayTwo));
            dexColorInsane = setRandomColor();
        }
        if (dealDex) {
            dexterityText = godDexterityArray[5];
            dexterityColor = setColor(5);
            dexterityResult.setText(dexterityText);
            dexterityResult.setTextColor(dexterityColor);
            dexVar = 5;
        }
    }

    public void rollInt() {
        intelligenceText = statText(upgradeTemp, godIntelligenceArray);
        intelligenceColor = creationRoll;
        intVar = creationRoll;
        if (sanZero && insaneStats.contains("int")) {
            intInsaneText = (statText(upgradeTemp, zeroSanArrayThree));
            intColorInsane = setRandomColor();
        }
        if (dealInt) {
            intelligenceText = godIntelligenceArray[5];
            intelligenceColor = setColor(5);
            intelligenceResult.setText(intelligenceText);
            intelligenceResult.setTextColor(intelligenceColor);
            intVar = 5;
        }
    }

    public void rollLoy() {
        loyaltyText = statText(upgradeObed, godloyaltyArray);
        loyaltyColor = creationRoll;
        obedVar = creationRoll;
        if (sanZero && insaneStats.contains("loy")) {
            loyInsaneText = (statText(upgradeObed, zeroSanArrayFour));
            loyColorInsane = setRandomColor();
        }
        if (dealLoy) {
            loyaltyText = godloyaltyArray[5];
            loyaltyColor = setColor(5);
            loyaltyResult.setText(loyaltyText);
            loyaltyResult.setTextColor(loyaltyColor);
            obedVar = 5;
        }
    }

    public void rollBea() {
        beautyText = statText(upgradeChar, godBeautyArray);
        beautyColor = creationRoll;
        beauVar = creationRoll;;
        if (sanZero && insaneStats.contains("bea")) {
            beaInsaneText = (statText(upgradeChar, zeroSanArrayFive));
            beaColorInsane = setRandomColor();
        }
        if (dealBea) {
            beautyText = godBeautyArray[5];
            beautyColor = setColor(5);
            beautyResult.setText(beautyText);
            beautyResult.setTextColor(beautyColor);
            beauVar = 5;
        }
    }


    // Using Shared Preferences for Upgrades. Storing 0/1/2 in five different int variables, to be updated upon upgrade and used in if/else statements above, in conjunction w/ the three separate upgrade methods below.
    //Odds for creature's stats. Modifiable via double roll.
    public int randomize() {
        Random random = new Random();
        double roll = random.nextDouble();
        if (roll < .7) {
            creationRoll = random.nextInt(2);
        }
        if (roll >= .7 && roll < .8) {
            creationRoll = 2;
        }
        if (roll >= .8 && roll < .9) {
            creationRoll = 3;
        }
        if (roll >= .9) {
            creationRoll = random.nextInt(6 - 4) + 4;
        }
        return creationRoll;
    }

    public int randomizeTwo() {
        Random random = new Random();
        double roll = random.nextDouble();
        if (roll < .6) {
            creationRoll = 1;
        }
        if (roll >= .6 && roll < .8) {
            creationRoll = 2;
        }
        if (roll >= .8 && roll < .9) {
            creationRoll = random.nextInt(5 - 3) + 3;
        }
        if (roll >= .9) {
            creationRoll = 5;
        }
        return creationRoll;
    }

    public int randomizeThree() {
        Random random = new Random();
        double roll = random.nextDouble();
        if (roll < .5) {
            creationRoll = 2;
        }
        if (roll >= .5 && roll < .8) {
            creationRoll = 3;
        }
        if (roll >= .8 && roll < .9) {
            creationRoll = 4;
        }
        if (roll >= .9) {
            creationRoll = 5;
        }
        return creationRoll;
    }

    public int randomizeFour() {
        Random random = new Random();
        double roll = random.nextDouble();
        if (roll < .6) {
            creationRoll = 3;
        }
        if (roll >= .6 && roll < .8) {
            creationRoll = 4;
        }
        if (roll >= .8) {
            creationRoll = 5;
        }
        return creationRoll;
    }

    public int randomizeFive() {
        Random random = new Random();
        double roll = random.nextDouble();
        creationRoll = 5;
        return creationRoll;
    }

    public int randomizeTen() {
        Random random = new Random();
        double roll = random.nextDouble();
        creationRoll = 0;
        return creationRoll;
    }

    //Random roll for given min/max bounds. Also matches creationRoll variable to Array variable, used to set corresponding colors in setColor() method.
    public int randomizeDelay(int max, int min) {
        Random random = new Random();
        return random.nextInt(max - min);
    }

    //Random roll just for 0-10, used as a shortcut for all scouting String Arrays.
    public int randomTen() {
        Random random = new Random();
        return random.nextInt(10);
    }

    //Used to take the rolled result of a creature stat (e.g. loyalty) and tie it to the appropriate color for its rank.
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

    public int setRandomColor() {
        int color = random.nextInt(11 - 1) + 1;
        switch (color) {
            case 1:
                return getResources().getColor(R.color.random_1);
            case 2:
                return getResources().getColor(R.color.random_2);
            case 3:
                return getResources().getColor(R.color.random_3);
            case 4:
                return getResources().getColor(R.color.random_4);
            case 5:
                return getResources().getColor(R.color.random_5);
            case 6:
                return getResources().getColor(R.color.random_6);
            case 7:
                return getResources().getColor(R.color.random_7);
            case 8:
                return getResources().getColor(R.color.random_8);
            case 9:
                return getResources().getColor(R.color.random_9);
            case 10:
                return getResources().getColor(R.color.random_10);
        }
        return color;
    }

    //Method for updating stats w/ random reveal delay, implementing higher base-roll upgrades.
    public String statText(int statUpgrade, final String[] statArray) {
        String test = "null";

        if (statUpgrade == 1) {
            test = (statArray[randomizeTwo()]);
        }
        if (statUpgrade == 2) {
            test = (statArray[randomizeThree()]);
        }
        if (statUpgrade == 3) {
            test = (statArray[randomizeFour()]);
        }
        if (statUpgrade == 0) {
            test = (statArray[randomize()]);
        }
        if (statUpgrade == 4) {
            test = (statArray[randomizeFive()]);
        }
        if (statUpgrade == 10) {
            test = (statArray[randomizeTen()]);
        }

        return test;
    }

    //Method to take in quirk from Array and roll a good/bad from a separate Array.
    public int quirkRoll(String quirk) {
        int baseQuirk = Arrays.asList(quirkArray).indexOf(quirk);
        int baseQuirkHigh = (baseQuirk * 2) + 2;
        int baseQuirkLow = baseQuirk * 2;
        return new Random().nextInt(baseQuirkHigh - baseQuirkLow) + baseQuirkLow;
    }

    //Method to set color for relevant quirk in revealed Array.
    private void setQuirkColor(int place, TextView quirk) {
        if (place % 2 == 0) {
            quirk.setTextColor(Color.GREEN);
        } else {
            quirk.setTextColor(Color.RED);
        }
    }

    private void assignFinalDescription() {

        int strDex = dualStats(sCon(dexVar), sCon(strVar));
        int intObed = dualStats(sCon(intVar), sCon(obedVar));
        finalReveal = Arrays.asList(
                anonEnd.get(percentToIntConvert(anonymity)),
                obedReveal.get(sCon(obedVar)),
                intObedReveal.get(intObed),
                strDexReveal.get(strDex),
                anonReveal.get(percentToIntConvert(anonymity))
        );
        finalRevealArray = new ArrayList<>();
        finalRevealArray.addAll(finalReveal);
    }

    //Converts percentage score to int category variable.
    private int statPcts(int percentage) {
        int statCat = 0;
        if (percentage >= 75 && percentage < 90) {
            statCat = 1;
        }
        if (percentage >= 60 && percentage < 75) {
            statCat = 2;
        }
        if (percentage >= 40 && percentage < 60) {
            statCat = 3;
        }
        if (percentage >= 25 && percentage < 40) {
            statCat = 4;
        }
        if (percentage < 25) {
            statCat = 5;
        }
        return statCat;
    }

    private int percentToIntConvert(int percentage) {
        int statCat = 0;
        if (percentage >= 80 && percentage < 90) {
            statCat = 1;
        }
        if (percentage >= 70 && percentage < 80) {
            statCat = 2;
        }
        if (percentage >= 60 && percentage < 70) {
            statCat = 3;
        }
        if (percentage >= 50 && percentage < 60) {
            statCat = 4;
        }
        if (percentage >= 25 && percentage < 50) {
            statCat = 5;
        }
        if (percentage < 25) {
            statCat = 6;
        }
        return statCat;
    }

    private int statConvert(int percentage) {
        int statCat = 0;
        if (percentage >= 80) {
            statCat = 1;
        }
        if (percentage >= 70 && percentage < 80) {
            statCat = 2;
        }
        if (percentage >= 50 && percentage < 70) {
            statCat = 3;
        }
        if (percentage < 50) {
            statCat = 4;
        }
        return statCat;
    }

    private int reverseIndex(int index) {
        int newIndex = 0;
        switch (index) {
            case 0:
                newIndex = 5;
                break;
            case 1:
                newIndex = 4;
                break;
            case 2:
                newIndex = 3;
                break;
            case 3:
                newIndex = 2;
                break;
            case 4:
                newIndex = 1;
                break;
            case 5:
                newIndex = 0;
        }
        return newIndex;
    }

    private int sCon(int stat) {
        int var = 0;
        switch (stat) {
            case 0:
            case 1:
                var = 1;
                break;
            case 2:
                var = 2;
            case 3:
                var = 3;
                break;
            case 4:
            case 5:
                var = 4;
        }
        return var;
    }

    private int endStatCon(int stat) {
        int var = 0;
        switch (stat) {
            case 0:
            case 1:
                var = 4;
                break;
            case 2:
                var = 3;
                break;
            case 3:
                var = 2;
                break;
            case 4:
            case 5:
                var = 1;
        }
        return var;
    }

    private int dualStats(int s1, int s2) {
        switch (s1) {
            case 1:
                switch (s2) {
                    case 1:
                        combStat = 0;
                        break;
                    case 2:
                        combStat = 1;
                        break;
                    case 3:
                        combStat = 2;
                        break;
                    case 4:
                        combStat = 3;
                }
                break;
            case 2:
                switch (s2) {
                    case 1:
                        combStat = 4;
                        break;
                    case 2:
                        combStat = 5;
                        break;
                    case 3:
                        combStat = 6;
                        break;
                    case 4:
                        combStat = 7;
                }
                break;
            case 3:
                switch (s2) {
                    case 1:
                        combStat = 8;
                        break;
                    case 2:
                        combStat = 9;
                        break;
                    case 3:
                        combStat = 10;
                        break;
                    case 4:
                        combStat = 11;
                }
                break;
            case 4:
                switch (s2) {
                    case 1:
                        combStat = 12;
                        break;
                    case 2:
                        combStat = 13;
                        break;
                    case 3:
                        combStat = 14;
                        break;
                    case 4:
                        combStat = 15;
                }
                break;
        }
        return combStat;
    }

    private int setHighScores(int stat) {
        int score = 0;
        switch (stat) {
            case 1:
                score = 20;
                break;
            case 2:
                score = 50;
                break;
            case 3:
                score = 100;
                break;
            case 4:
                score = 200;
                break;
            case 5:
                score = 300;
        }
        return score;
    }


    public int quirkMods(int var, boolean isPos, TextView textView, String[] statArray) {
        if (isPos && var < 5) {
            var = var + 1;
        }
        if (!isPos && var > 0) {
            var = var - 1;
        }
        textView.setText(statArray[var]);
        textView.setTextColor(setColor(var));
        return var;
    }

    private void quirkOrder(String quirkName, String quirkNameTwo) {
        switch (injectionList.length) {
            case 1:
                quirkStringOne = quirkList.get(0);
                if (quirkStringOne.equals(quirkName) || quirkStringOne.equals(quirkNameTwo)) {
                    c1 = true;
                }
                break;
            case 2:
                quirkStringOne = quirkList.get(0);
                quirkStringTwo = quirkList.get(1);
                if (quirkStringOne.equals(quirkName) || quirkStringOne.equals(quirkNameTwo)) {
                    c1 = true;
                }
                if (quirkStringTwo.equals(quirkName) || quirkStringTwo.equals(quirkNameTwo)) {
                    c2 = true;
                }
                break;
            case 3:
                quirkStringOne = quirkList.get(0);
                quirkStringTwo = quirkList.get(1);
                quirkStringThree = quirkList.get(2);
                if (quirkStringOne.equals(quirkName) || quirkStringOne.equals(quirkNameTwo)) {
                    c1 = true;
                }
                if (quirkStringTwo.equals(quirkName) || quirkStringTwo.equals(quirkNameTwo)) {
                    c2 = true;
                }
                if (quirkStringThree.equals(quirkName) || quirkStringThree.equals(quirkNameTwo)) {
                    c3 = true;
                }
        }
    }

    public int weightPct(int var) {
        int weight = 1;
        switch (var) {
            case 0:
                weight = 3;
                break;
            case 1:
            case 2:
                weight = 2;
        }
        return weight;
    }

    //0-5 from worst to best.
    public int setVarPercent(int var) {
        Random random = new Random();
        int percent = 0;
        switch (var) {
            case 5:
                percent = random.nextInt(91 - 80) + 80;
                break;
            case 4:
                percent = random.nextInt(81 - 60) + 60;
                break;
            case 3:
                percent = random.nextInt(61 - 40) + 40;
                break;
            case 2:
                percent = random.nextInt(41 - 20) + 20;
                break;
            case 1:
                percent = random.nextInt(21 - 10) + 10;
        }
        return percent;
    }

    public String endRank() {
        String rank = "null";
        double pct = 100;
        int total = (keptTer + keptPop) / 2;

        for (int i = 0; i < rankArray.length && !stopRank; i++) {
            pct = pct - 5;
            if (total >= pct) {
                rank = (rankArray[i]);
                stopRank = true;
                endRank = rank;
            }
        }
        return rank;
    }

    public void setEndArrays() {
        String terModString = "null";
        String supModString = "null";
        Random random = new Random();

        finalWealth = endWealthArray[statPcts(wealth)];
        finalSanity = endSanityArray[statPcts(sanity)];
        switch (statPcts(wealth)) {
            case 0:
            case 1:
                finalBribes = endBribesZeroOneArray[statPcts(sanity)];
                break;
            case 2:
                finalBribes = endBribesTwoArray[statPcts(sanity)];
                break;
            case 3:
                finalBribes = endBribesThreeArray[statPcts(sanity)];
                break;
            case 4:
                finalBribes = endBribesFourArray[statPcts(sanity)];
                break;
            case 5:
                finalBribes = getString(R.string.no_bribes);
        }
        finalEndurance = endEnduranceArray[statPcts(endurance)];
        finalEnduranceSanity = endEnduranceSanityArray[statPcts(sanity)];

        if (statPcts(wealth) == 5) {
            newSupport = 0 - (random.nextInt(31 - 20) + 20);
        } else {
            switch (statPcts(wealth) + statPcts(sanity)) {
                case 0:
                    newSupport = random.nextInt(26 - 10) + 10;
                    break;
                case 1:
                    newSupport = random.nextInt(16 - 7) + 7;
                    break;
                case 2: case 3:
                    newSupport = random.nextInt(11 - 5) + 5;
                    break;
                case 4: case 5:
                    newSupport = 0 - (random.nextInt(11 - 5) + 5);
                    break;
                case 6: case 7:
                    newSupport = 0 - (random.nextInt(21 - 10) + 10);
                    break;
                case 8: case 9:
                    newSupport = 0 - (random.nextInt(26 - 15) + 15);
                    break;
                case 10:
                    newSupport = 0 - (random.nextInt(31 - 20) + 20);
            }
        }

        switch (statPcts(endurance) + statPcts(sanity)) {
            case 0:
                newTer = random.nextInt(26 - 10) + 10;
                break;
            case 1:
                newTer = random.nextInt(16 - 7) + 7;
                break;
            case 2: case 3:
                newTer = random.nextInt(11 - 5) + 5;
                break;
            case 4: case 5:
                newTer = 0 - (random.nextInt(11 - 5) + 5);
                break;
            case 6: case 7:
                newTer = 0 - (random.nextInt(21 - 10) + 10);
                break;
            case 8: case 9:
                newTer = 0 - (random.nextInt(26 - 15) + 15);
                break;
            case 10:
                newTer = 0 - (random.nextInt(31 - 20) + 20);
        }

        if (keptTer + newTer < 0) {
            newTer = 0 - keptTer;
        }
        if (keptPop + newSupport < 0) {
            newSupport = 0 - keptPop;
        }
        if (keptTer + newTer > 100) {
            newTer = 100 - keptTer;
        }
        if (keptPop + newSupport > 100) {
            newSupport = 100 - keptPop;
        }

        if (newTer > 0) {
            terModString = getString(R.string.increase_ter, String.valueOf(newTer));
        } else {
            terModString = getString(R.string.decrease_ter, String.valueOf(Math.abs(newTer)));
        }
        if (newSupport > 0) {
            supModString = getString(R.string.increase_sup, String.valueOf(newSupport));
        } else {
            supModString = getString(R.string.decrease_sup, String.valueOf(Math.abs(newSupport)));
        }

        keptTer = keptTer + newTer;
        keptPop = keptPop + newSupport;

        homeConv();
        homeScore();

        if (statPcts(wealth) >= 4 && statPcts(sanity) >= 4 && statPcts(endurance) >= 3) {
            endString = finalEndurance + " " + terModString + "\n\n" + finalWealth + ", " + getString(R.string.no_wealth_insanity) + " " + supModString;
            endStringTwo = finalHome + "\n\n" + getString(R.string.final_rank) + " " + endRank() + ".";

        } else if (statPcts(wealth) >= 4 && statPcts(sanity) >= 4 && statPcts(endurance) < 3) {
            endString = finalEndurance + " " + finalEnduranceSanity + " " + terModString + "\n\n" + finalWealth + ", " + getString(R.string.no_wealth_insanity) + " " + supModString;
            endStringTwo = finalHome + "\n\n" + getString(R.string.final_rank) + " " + endRank() + ".";

        } else if (statPcts(wealth) >= 4 && statPcts(sanity) < 4 && statPcts(endurance) >= 3) {
            endString = finalEndurance + " " + terModString + "\n\n" + finalWealth + " " + finalBribes + " " + supModString;
            endStringTwo = finalHome + "\n\n" + getString(R.string.final_rank) + " " + endRank() + ".";

        } else if (statPcts(wealth) >= 4 && statPcts(sanity) < 4 && statPcts(endurance) < 3) {
            endString = finalEndurance + " " + finalEnduranceSanity + " " + terModString + "\n\n" + finalWealth + " " + finalBribes + " " + supModString;
            endStringTwo = finalHome + "\n\n" + getString(R.string.final_rank) + " " + endRank() + ".";

        } else if (statPcts(wealth) < 4 && statPcts(endurance) >= 3) {
            endString = finalEndurance + " " + terModString + "\n\n" + finalWealth + " " + finalSanity + " " + finalBribes + " " + supModString;
            endStringTwo = finalHome + "\n\n" + getString(R.string.final_rank) + " " + endRank() + ".";
        } else {
            endString = finalEndurance + " " + finalEnduranceSanity + " " + terModString + "\n\n" + finalWealth + " " + finalSanity + " " + finalBribes + " " + supModString;
            endStringTwo = finalHome + "\n\n" + getString(R.string.final_rank) + " " + endRank() + ".";
        }
    }

    public void setEndLossArrays() {

        switch (statPcts(wealth)) {
            case 0: case 1:
                finalBribes = endBribesZeroOneArray[statPcts(sanity)]; break;
            case 2:
                finalBribes = endBribesTwoArray[statPcts(sanity)]; break;
            case 3:
                finalBribes = endBribesThreeArray[statPcts(sanity)]; break;
            case 4:
                finalBribes = endBribesFourArray[statPcts(sanity)]; break;
            case 5:
                finalBribes = getString(R.string.no_bribes);
        }

        homeConvertLoss();

        if (statPcts(wealth) >=4 && statPcts(sanity) <4) {
            endStringLoss = finalWealth + ". " + "\n\n" + finalHome + "\n\n" + getString(R.string.final_rank) + " " + endRank + ".";
        } else if (statPcts(wealth) >=4 && statPcts(sanity) >=4){
            endStringLoss = finalWealth + ", " + getString(R.string.no_wealth_insanity) + "\n\n" + finalHome + "\n\n" + getString(R.string.final_rank) + " " + endRank + ".";
        } else {
            endStringLoss = finalWealth + ", " + finalSanity + " " + finalBribes + "\n\n" + finalHome + "\n\n" + getString(R.string.final_rank) + " " + endRank + ".";
        }
    }

    private void homeScore() {
        int score = 10;
        int mod = 5;
        int total = (keptTer + keptPop) / 2;
        int pct = 0;
        while (pct < total) {
            pct = pct +4;
            mod = mod + 5;
            score = (score + 50) +  mod;
        }
        endSceneScore = score -10;
    }

    private void homeConv() {
        double pct = 100;
        int score = 2000;
        int total = (keptTer + keptPop) / 2;
        for (int i=0; i<endFreeArray.length && !stopHome; i++) {
            score = score - 100;
            pct = pct -5;
            if (total >= pct) {
                finalHome = endFreeArray[i];
                endRank = rankArray[i];
                stopHome = true;
            }
        }
    }

    private void homeConvertLoss() {
        int combo = statPcts(wealth) + statPcts(sanity);

        finalWealth = endWealthArray[statPcts(wealth)];
        finalSanity = endSanityArray[statPcts(sanity)];

        switch (combo) {
            case 0: case 1:
                finalHome = endPrisonArray[0];
                endRank = rankArrayTwo[0];
                break;
            case 2: case 3:
                finalHome = endPrisonArray[1];
                endRank = rankArrayTwo[1];
                break;
            case 4:
                finalHome = endPrisonArray[2];
                endRank = rankArrayTwo[2];
                break;
            case 5: case 6:
                finalHome = endPrisonArray[3];
                endRank = rankArrayTwo[3];
                break;
            case 7: case 8:
                finalHome = endPrisonArray[4];
                endRank = rankArrayTwo[4];
                break;
            case 9: case 10:
                finalHome = endPrisonArray[5];
                endRank = rankArrayTwo[5];
        }
    }

    private void endStats() {
        int dex = dexVar;
        int combo = strVar + dexVar + intVar + beauVar;
        int anon = statPcts(anonymity);
        dexEndArray = reverseIndex(dex);

        switch (anon) {
            case 0:
               switch (combo) {
                   case 0: case 1: case 2: case 3:
                       strEndArray = 5;
                       launchFailure = true;
                       break;
                   default: strEndArray = 33;
               }
            break;
            case 1:
                switch (combo) {
                    case 0: case 1: case 2: case 3: case 4: case 5:
                        strEndArray = 11;
                        launchFailure = true;
                        break;
                    case 6: case 7: case 8:
                        strEndArray = 10;
                        break;
                    case 9: case 10: case 11:
                        strEndArray = 9;
                        break;
                    case 12: case 13: case 14:
                        strEndArray = 8;
                        break;
                    case 15: case 16: case 76:
                        strEndArray = 7;
                        break;
                    case 18: case 19: case 20:
                        strEndArray = 6;
                }
                break;
            case 2:
                switch (combo) {
                    case 0: case 1: case 2: case 3: case 4:
                        strEndArray = 17;
                        launchFailure = true;
                        break;
                    case 5: case 6: case 7:
                        strEndArray = 15;
                        launchFailure = true;
                        break;
                    case 8: case 9: case 10: case 11: case 12:
                        strEndArray = 14;
                        break;
                    case 13: case 14: case 15: case 16:
                        strEndArray = 13;
                        break;
                    case 17: case 18: case 19: case 20:
                        strEndArray = 12;
                }
                break;
            case 3:
                switch (combo) {
                    case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 7: case 8: case 9:
                        strEndArray = 23;
                        launchFailure = true;
                        break;
                    case 10: case 11: case 12: case 13: case 14: case 15: case 16:
                        strEndArray = 20;
                        break;
                    case 17: case 18:
                        strEndArray = 19;
                        break;
                    case 19: case 20:
                        strEndArray = 18;
                }
                break;
            case 4:
                switch (combo) {
                    case 0: case 1: case 2: case 3: case 4: case 5:
                        strEndArray = 29;
                        launchFailure = true;
                        break;
                    case 6: case 7: case 8: case 9: case 10: case 11:
                        strEndArray = 28;
                        launchFailure = true;
                        break;
                    case 12: case 13: case 14: case 15: case 16:
                        strEndArray = 25;
                        break;
                    case 17: case 18: case 19: case 20:
                        strEndArray = 24;
                }
                break;
            case 5:
                switch (combo) {
                    case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 7: case 8: case 9: case 10: case 11:
                        strEndArray = 32;
                        launchFailure = true;
                        break;
                    case 12: case 13:
                        strEndArray = 31;
                        launchFailure = true;
                        break;
                    case 14: case 15: case 16: case 17: case 18: case 19: case 20:
                        strEndArray = 30;
                }
        }
    }

    public void setEnd(TextView result) {
        int str = endStatCon(strVar);
        int dex = endStatCon(dexVar);
        int intel = endStatCon(intVar);
        int bea = endStatCon(beauVar);

        int altStr = reverseIndex(strVar);
        int altDex = reverseIndex(dexVar);
        int altIntel = reverseIndex(intVar);
        int altBea = reverseIndex(beauVar);


        switch (str) {
            case 1:
                switch (dex) {
                    case 1:
                        if (intel == 1 && bea == 1) {
                            result.setText(R.string.s_2);
                        }
                        if (intel == 1 && (bea == 2 || bea == 3)) {
                            result.setText(R.string.s_2_2);
                        }
                        if (intel == 1 && bea == 4) {
                            result.setText(R.string.s_4);
                        }
                        if ((intel == 2 || intel == 3) && bea <= 3) {
                            result.setText(R.string.s_2_3);
                        }
                        if ((intel == 2 || intel == 3) && bea == 4) {
                            result.setText(R.string.s_2_4);
                        }
                        if (intel == 4 && bea <= 3) {
                            result.setText(R.string.s_3);
                        }
                        if (intel == 4 && bea == 4) {
                            result.setText(R.string.s_6);
                        }
                        break;
                    case 2:
                    case 3:
                        if ((intel == 1) && bea == 1) {
                            result.setText(R.string.s_53_2);
                        }
                        if (intel == 1 && (bea == 2 || bea ==3) ) {
                            result.setText(R.string.s_53);
                        }
                        if ((intel == 1) && bea == 4) {
                            result.setText(R.string.s_54);
                        }
                        if ((intel == 2 || intel == 3) && bea <= 3) {
                            result.setText(R.string.s_10);
                        }
                        if ((intel == 2 || intel == 3) && bea == 4) {
                            result.setText(R.string.s_13);
                        }
                        if (intel == 4 && bea <= 3) {
                            result.setText(R.string.s_11);
                        }
                        if (intel == 4 && bea == 4) {
                            result.setText(R.string.s_11_2);

                        }
                        break;
                    case 4:
                        if ((intel == 1) && bea == 1) {
                            result.setText(R.string.s_55_2);
                        }
                        if (intel == 1 && (bea == 2 || bea ==3) ) {
                            result.setText(R.string.s_55);
                        }
                        if ((intel == 1) && bea == 4) {
                            result.setText(R.string.s_56);
                        }
                        if ((intel == 2 || intel == 3) && bea <= 3) {
                            result.setText(R.string.s_18);
                        }
                        if ((intel == 2 || intel == 3) && bea == 4) {
                            result.setText(R.string.s_19);
                        }
                        if (intel == 4 && bea <= 3) {
                            result.setText(R.string.s_20);
                        }
                        if (intel == 4 && bea == 4) {
                            result.setText(R.string.s_21);
                        }

                }
                break;
            //Strength second/third tiers.
            case 2:
            case 3:
                switch (dex) {
                    case 1:
                        if ((intel == 1) && bea <= 3) {
                            result.setText(R.string.s_57);
                        }
                        if ((intel == 1) && bea == 4) {
                            result.setText(R.string.s_58);

                        }
                        if ((intel == 2 || intel == 3) && bea <= 3) {
                            result.setText(R.string.s_14);

                        }
                        if ((intel == 2 || intel == 3) && bea == 4) {
                            result.setText(R.string.s_15);

                        }
                        if (intel == 4 && bea <= 3) {
                            result.setText(R.string.s_17);

                        }
                        if (intel == 4 && bea == 4) {
                            result.setText(R.string.s_16);
                        }
                        break;
                    case 2:
                    case 3:
                        if (intel == 1 & bea == 1) {
                            result.setText(R.string.s_26);
                        }
                        if (intel == 1 && (bea == 2 || bea ==3)) {
                            result.setText(R.string.s_26_2);
                        }
                        if (intel == 1 && bea == 4) {
                            result.setText(R.string.s_27);
                        }
                        if ((intel == 2 || intel == 3) && bea == 1) {
                            result.setText(R.string.s_28);

                        }
                        if ((intel == 2 || intel == 3) && (bea == 2 || bea == 3)) {
                            result.setText(R.string.s_30);
                        }
                        if ((intel == 2 || intel == 3) && bea == 4) {
                            result.setText(R.string.s_31);

                        }
                        if (intel == 4 && bea == 1) {
                            result.setText(R.string.s_29);

                        }
                        if (intel == 4 && (bea == 2 || bea == 3)) {
                            result.setText(R.string.s_32);
                        }
                        if (intel == 4 && bea == 4) {
                            result.setText(R.string.s_33);
                        }
                        break;
                    case 4:
                        if (intel == 1 && bea == 1) {
                            result.setText(R.string.s_70);
                        }
                        if (intel == 1 && (bea == 2 || bea == 3)) {
                            result.setText(R.string.s_71);
                        }
                        if (intel == 1 && bea == 4) {
                            result.setText(R.string.s_72);
                        }
                        if ((intel == 2 || intel == 3) && bea == 1) {
                            result.setText(R.string.s_73);
                        }
                        if ((intel == 2 || intel == 3) && (bea == 2 || bea == 3)) {
                            result.setText(R.string.s_74);
                        }
                        if ((intel == 2 || intel == 3) && bea == 4) {
                            result.setText(R.string.s_75);
                        }
                        if (intel == 4 && bea == 1) {
                            result.setText(R.string.s_76);
                        }
                        if (intel == 4 && (bea == 2 || bea == 3)) {
                            result.setText(R.string.s_77);
                        }
                        if (intel == 4 && bea == 4) {
                            result.setText(R.string.s_78);

                        }
                }
                break;
            case 4:
                switch (dex) {
                    case 1:
                        if ((intel == 1) && bea <= 3) {
                            result.setText(R.string.s_59);
                        }
                        if ((intel == 1) && bea == 4) {
                            result.setText(R.string.s_60);
                        }
                        if ((intel == 2 || intel == 3) && bea <= 3) {
                            result.setText(R.string.s_22);
                        }
                        if ((intel == 2 || intel == 3) && bea == 4) {
                            result.setText(R.string.s_23);
                        }
                        if (intel == 4 && bea <= 3) {
                            result.setText(R.string.s_24);
                        }
                        if (intel == 4 && bea == 4) {
                            result.setText(R.string.s_25);

                        }
                        break;
                    case 2:
                    case 3:
                        if ((intel == 1) && bea <= 3) {
                            result.setText(R.string.s_61);
                        }
                        if ((intel == 1) && bea == 4) {
                            result.setText(R.string.s_62);
                        }
                        if ((intel == 2 || intel == 3) && bea <= 3) {
                            result.setText(R.string.s_34);
                        }
                        if ((intel == 2 || intel == 3) && bea == 4) {
                            result.setText(R.string.s_79);
                        }
                        if (intel == 4 && bea == 1) {
                            result.setText(R.string.s_80);
                        }
                        if (intel == 4 && (bea == 2 || bea == 3)) {
                            result.setText(R.string.s_36);
                        }
                        if (intel == 4 && bea == 4) {
                            result.setText(R.string.s_81);
                        }
                        break;
                    case 4:
                        if (bea == 1 && intel >1) {
                            result.setText(getString(R.string.end_two_par, getString(R.string.s_52), getString(R.string.s_52_2)));
                        }
                        if (intel == 1 && bea >1) {
                            result.setText(getString(R.string.end_two_par, getString(R.string.s_52), getString(R.string.s_52_3)));                        }
                        if (intel == 1 && bea == 1) {
                            result.setText(getString(R.string.end_two_par, getString(R.string.s_52), getString(R.string.s_52_4)));                        }
                        if (intel >1 && bea >1) {
                            result.setText(R.string.s_52);
                        }
                }
        }
    }

    private void setScores() {
        int strScore;
        int dexScore;
        int intScore;
        int obedScore;
        int beaScore;
        int sanScore;
        int endScore;
        int wealthScore;
        int anonScore;
        int launchSuccess;
        int terScore;
        int supScore;
        double rawTerror;
        double rawSupport;
        String creatureStats;
        String terNumber;
        String supNumber;

        Date currentDate = Calendar.getInstance().getTime();
        Format formatter = new SimpleDateFormat("MMMM d, y h:mm a", Locale.US);
        String date = formatter.format(currentDate);

        strScore = setHighScores(strVar);
        dexScore = setHighScores(dexVar);
        intScore = setHighScores(intVar);
        obedScore = setHighScores(obedVar);
        beaScore = setHighScores(beauVar);
        sanScore = setHighScores(reverseIndex(statPcts(sanity)));
        endScore = setHighScores(reverseIndex(statPcts(endurance)));
        wealthScore = setHighScores(reverseIndex(statPcts(wealth)));
        anonScore = setHighScores(reverseIndex(statPcts(anonymity)));
        double tempVar = 6.5 + random.nextDouble();
        double tempVarTwo = 6.5 + random.nextDouble();
        rawTerror =  tempVar - ( ( (100-keptTer) * tempVar ) /100);
        rawSupport =  tempVarTwo - ( ( (100-keptPop) * tempVarTwo ) /100);

        if (!launchFailure) {
            if (rawTerror >=1) {
                String terConvert = String.format("%.2f", rawTerror);
                terNumber = terConvert + " " + "billion";
            } else {
                rawTerror = rawTerror * 1000;
                terNumber = (int) rawTerror + " " + "million";
            }
            if (rawSupport >=1) {
                String supConvert = String.format("%.2f", rawSupport);
                supNumber = supConvert + " " + "billion";
            } else {
                rawSupport = rawSupport * 1000;
                supNumber = (int) rawSupport + " " + "million";
            }
            creatureStats = getString(R.string.creature_stats, creatureName, terNumber, supNumber);
        } else {
            creatureStats = getString(R.string.creature_stats_two, creatureName);
        }


        terScore = keptTer * 10;
        supScore = keptPop * 10;

        if (!launchFailure) {
            launchSuccess = 500;
        } else {
            launchSuccess = 0;
        }

        totalScore = (strScore + dexScore + intScore + obedScore + beaScore + sanScore + endScore + wealthScore + anonScore + launchSuccess + terScore + supScore + endSceneScore);

        scoreResults.setStrValue(strVar);
        scoreResults.setDexValue(dexVar);
        scoreResults.setIntValue(intVar);
        scoreResults.setObedValue(obedVar);
        scoreResults.setBeaValue(beauVar);
        scoreResults.setQuirkOne(quirkPlaceOne);
        scoreResults.setQuirkTwo(quirkPlaceTwo);
        scoreResults.setQuirkTwo(quirkPlaceThree);
        scoreResults.setCreatureStats(creatureStats);

        scores.setStrScore(strScore);
        scores.setDexScore(dexScore);
        scores.setIntScore(intScore);
        scores.setObedScore(obedScore);
        scores.setBeaScore(beaScore);
        scores.setSanScore(sanScore);
        scores.setEndScore(endScore);
        scores.setComScore(wealthScore);
        scores.setAnonScore(anonScore);
        scores.setEndRank(endRank);

        scores.setLaunchSuccess(launchSuccess);
        scores.setTerScore(terScore);
        scores.setSupScore(supScore);
        scores.setEndSceneScore(endSceneScore);
        scores.setTotalScore(totalScore);
        scores.setSummary(finalHome);

        scoresMenu.setDate(date);
        scoresMenu.setTotalScore(totalScore);
        scoresMenu.setRank(endRank);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                scoresDB.highScoresDao().insert(scores);
                scoresDB.highScoresDao().insert(scoresMenu);
                scoresDB.highScoresDao().insert(scoreResults);



            }
        });
    }


    }
