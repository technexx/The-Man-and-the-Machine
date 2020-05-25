package technexx.android.ManMachine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import technexx.android.ManMachine.Database.HighScores;
import technexx.android.ManMachine.Database.HighScoresDB;
import technexx.android.ManMachine.Database.ScoreResults;
import technexx.android.ManMachine.Database.ScoresMenu;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Game extends AppCompatActivity implements SplashFragment.OnGameStart, ResultsFragment.nextTurnCallback, StoryFragment.choiceResultCallback, StoryFragment.nextDayCallback, ResultsFragment.endGameCallback, ResultsFragment.burnFailCallback, ResultsFragment.clearStatsCallback, ResultsFragment.showPercentages, StoryFragment.endGameCallback, StoryFragment.refreshStoryCallback, ResultsFragment.repeatResults{

    private int strength;
    private int dexterity;
    private int intelligence;
    private int loyalty;
    private int beauty;
    private int sanity;
    private int endurance;
    private int wealth;
    private int anonymity;
    private int injection;

    private boolean altStr;
    private boolean altDex;
    private boolean altTemp;
    private boolean altObed;
    private boolean altChar;
    private boolean altSan;
    private boolean altEnd;
    private boolean altCom;
    private boolean altAnon;

    List<HighScores> allScores;

    Handler handler = new Handler();
    Runnable runnable;

    //Todo: (STICKY): For future apps, most of the stuff in onClick should go into private methods, referencing global variables that can then be called in onClick. It will be much easier to track. Also, use the for() loop instead of individual case counters for iterating through Arrays. Again, easier.
    //Todo: (STICKY): If (args != null) works for intermittently sent bundles between activity/fragments.
    //Todo: (STICKY): RecyclerView generally needs wrap content for height. If your database is functional but UI goes blank, look to the layouts. Remember to constrain it to other views you need visible.
    //Todo: (STICKY): To avoid main thread database calls whose dependencies can fail if they're put under locally instantiated aSync threads, use a repository to access the DAO through an aSync method, which can then be called in the main thread from anywhere.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        final SharedPreferences pref = getApplicationContext().getSharedPreferences("SharedPref", 0);
        final SharedPreferences statPref = getApplicationContext().getSharedPreferences("stats", 0);
        SharedPreferences.Editor statEditor = statPref.edit();

        //Makes sure post-delays are not executed once Fragment is gone.
        handler.removeCallbacksAndMessages(runnable);

        FragmentManager fragmentManager = getSupportFragmentManager();
        SplashFragment splashFragment = new SplashFragment();
        ResultsFragment resultsFragment = new ResultsFragment();
        StatsFragment statsFragment = new StatsFragment();


        Intent intent = getIntent();
        int isContinued = intent.getIntExtra("isContinued", 0);

        if (isContinued == 0) {
            resetStats();
            resetExtras();
            fragmentManager.beginTransaction()
                    .add(R.id.story_text, splashFragment)
                    .commit();
        } else {
            //Used to fill arg requirement.
            Bundle b = new Bundle();
            b.putBoolean("clearStats", false);
            statsFragment.setArguments(b);

            statsFragment.setArguments(loadAltStats());
            fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .add(R.id.story_text, resultsFragment)
                    .add(R.id.game_stats, statsFragment)
                    .commit();
        }
    }

    @Override
    public void onRefreshStory() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        StoryFragment storyFragment = new StoryFragment();
        StatsFragment statsFragment = new StatsFragment();

        Bundle bundle = new Bundle();
        bundle.putBoolean("clearStats", true);
        loadAltStats();
        statsFragment.setArguments(loadAltStats());

        statsFragment.setArguments(bundle);


        fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.story_text, storyFragment)
                .add(R.id.game_stats, statsFragment)
                .commit();
    }

    //Using this to change Stats on transition to StoryFragment.
    @Override
    public void onNextTurn() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        StoryFragment storyFragment = new StoryFragment();
        StatsFragment statsFragment = new StatsFragment();

        Bundle bundle = new Bundle();
        bundle.putBoolean("clearStats", true);
        loadAltStats();
        statsFragment.setArguments(loadAltStats());

        statsFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.game_stats, statsFragment)
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.story_text, storyFragment)
                .commit();
    }

    @Override
    public void onBurnFail(int stat, boolean isPos) {
        SharedPreferences statPref = getApplicationContext().getSharedPreferences("stats", 0);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("SharedPref", 0);
        SharedPreferences.Editor editor = pref.edit();

        sanity = statPref.getInt("sanity", 100);
        endurance = statPref.getInt("endurance", 100);
        anonymity = statPref.getInt("anonymity", 100);
        wealth = statPref.getInt("wealth", 100);
        injection = statPref.getInt("injections", 10);

        altAnon = true;
        altSan = true;
        altEnd = true;

        Bundle bundle = new Bundle();
        bundle.putBoolean("altStr", altStr);
        bundle.putBoolean("altDex", altDex);
        bundle.putBoolean("altTemp", altTemp);
        bundle.putBoolean("altObed", altObed);
        bundle.putBoolean("altChar", altChar);
        bundle.putBoolean("altSan", altSan);
        bundle.putBoolean("altEnd", altEnd);
        bundle.putBoolean("altCom", altCom);
        bundle.putBoolean("altAnon", altAnon);

        Random random = new Random();

        int statMod;
        int statModTwo;
        int statModThree;
        int statModFour;
        String statChangeSan;
        String statChangeEnd;
        String statChangeAnon;
        String statChangeWealth;

        if (stat == 1) {
            statMod = random.nextInt(21-9) + 9;
            statMod = numCap(anonymity, statMod);
            anonymity = (anonymity - statMod);
            String statChangeDown = "(-" + statMod + ")";
            bundle.putString("anonChange", statChangeDown);

            if (anonymity <=0) {
                editor.putBoolean("anonZeroIncin", true);
                editor.apply();
            }
        }

        if (stat == 2) {
            statMod = random.nextInt(16-5) + 5;
            statModTwo = random.nextInt(16-5) + 5;
            statMod = numCap(sanity, statMod);
            statModTwo = numCap(endurance, statModTwo);

            sanity = (sanity - statMod);
            endurance = (endurance - statModTwo);
            statChangeSan = "(-" + statMod + ")";
            statChangeEnd = "(-" + statModTwo + ")";
            bundle.putString("sanChange", statChangeSan);
            bundle.putString("endChange", statChangeEnd);

            if (endurance <=0) {
                editor.putBoolean("endZeroIncin", true);
                editor.apply();
            }
        }

        if (stat == 3) {
            statMod = random.nextInt(31-15) + 15;
            statMod = numCap(anonymity, statMod);
            sanity = (sanity - statMod);
            statChangeSan = "(-" + statMod + ")";
            bundle.putString("sanChange", statChangeSan);
        }

        if (stat == 4) {
            statMod = random.nextInt(31-15) + 15;
            statMod = numCap(endurance, statMod);
            endurance = (endurance - statMod);
            statChangeEnd = "(-" + statMod + ")";
            bundle.putString("endChange", statChangeEnd);

            if (endurance <=0) {
                editor.putBoolean("endZeroIncin", true);
                editor.apply();
            }
        }

        if (stat == 5) {
            statMod = random.nextInt(31-15) + 15;
            statMod = numCap(sanity, statMod);
            if (isPos) {
                sanity = (sanity + statMod);
                statChangeSan = "(+" + statMod + ")";
            } else {
                sanity = (sanity - statMod);
                statChangeSan = "(-" + statMod + ")";
            }

            bundle.putString("sanChange", statChangeSan);
        }

        if (stat == 6) {
            statMod = random.nextInt(31-15) + 15;
            statMod = numCap(wealth, statMod);
            if (isPos) {
                wealth = (wealth + statMod);
                statChangeEnd = "(+" + statMod + ")";
            } else {
                wealth = (wealth - statMod);
                statChangeEnd = "(-" + statMod + ")";
            }
            bundle.putString("comChange", statChangeEnd);
        }

        final SharedPreferences.Editor statEditor = statPref.edit();

        if (stat == 7) {
            injection = (injection - 1);
            statEditor.putInt("injections", injection);
            String loseInjection = "(-" + 1 + ")";
            bundle.putString("injectChange", loseInjection);
        }

        statEditor.putInt("wealth", wealth);
        statEditor.putInt("sanity", sanity);
        statEditor.putInt("endurance", endurance);
        statEditor.putInt("anonymity", anonymity);
        statEditor.apply();

        FragmentManager fragmentManager = getSupportFragmentManager();
        StatsFragment statsFragment = new StatsFragment();

        statsFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.game_stats, statsFragment)
                .commit();
    }

    @Override
    public void onFirstDay() {

        final SharedPreferences pref = getApplicationContext().getSharedPreferences("SharedPref", 0);
        final SharedPreferences.Editor editor = pref.edit();

        final SharedPreferences statPref = getApplicationContext().getSharedPreferences("stats", 0);
        SharedPreferences.Editor statEdit = statPref.edit();

        //Resetting stats.
//        statEdit.putInt("strength", 0);
//        statEdit.putInt("dexterity", 0);
//        statEdit.putInt("intelligence", 0);
//        statEdit.putInt("loyalty", 0);
//        statEdit.putInt("beauty", 60);
//        statEdit.putInt("sanity", 5);
//        statEdit.putInt("endurance", 5);
//        statEdit.putInt("wealth", 5);
//        statEdit.putInt("anonymity", 10);
//        statEdit.putInt("injections", 10);
//
//        editor.putInt("upgradeStr", 3);
//        editor.putInt("upgradeDex", 3);
//        editor.putInt("upgradeTemp", 3);
//        editor.putInt("upgradeObed", 3);
//        editor.putInt("upgradeChar", 3);

        editor.apply();
        statEdit.apply();

        FragmentManager fragmentManager = getSupportFragmentManager();
        ResultsFragment resultsFragment = new ResultsFragment();
        StatsFragment statsFragment = new StatsFragment();

        statsFragment.setArguments(loadAltStats());
        Bundle b = new Bundle();
        b.putBoolean("clearStats", false);
        statsFragment.setArguments(b);

        fragmentManager.beginTransaction()
//                .setCustomAnimations(android.R.anim.fade_in, R.anim.fade_in)
                .replace(R.id.story_text, resultsFragment)
                .add(R.id.game_stats, statsFragment)
                .commit();
    }

    @Override
    public void onNextDay() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        ResultsFragment resultsFragment = new ResultsFragment();
        StatsFragment statsFragment = new StatsFragment();

        Bundle b = new Bundle();
        b.putBoolean("clearStats", true);
        statsFragment.setArguments(b);

        fragmentManager.beginTransaction()
                .replace(R.id.game_stats, statsFragment)
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.story_text, resultsFragment)
                .commit();
    }

    @Override
    public void onRepeatResults() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        ResultsFragment resultsFragment = new ResultsFragment();

        fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.story_text, resultsFragment)
                .commit();
    }


    @Override
    public void onChoiceResult(boolean alterStats, String selectedStat, boolean statUp, boolean extraDamage) {

        Random random = new Random();
        int save = random.nextInt(6-1) + 1;

        int statMod;
        if (save == 4) {
            statMod = random.nextInt(12-6) + 6;
        } else {
            statMod = random.nextInt(26-15) + 15;
        }
        if (extraDamage) {
            statMod = random.nextInt(36-25) + 25;
        }
        int endStatMod = random.nextInt(31-20) + 20;

        SharedPreferences statPref = getApplicationContext().getSharedPreferences("stats", 0);

        strength = statPref.getInt("strength", 100);
        dexterity = statPref.getInt("dexterity", 100);
        intelligence = statPref.getInt("intelligence", 100);
        loyalty = statPref.getInt("loyalty", 100);
        beauty = statPref.getInt("beauty", 100);
        sanity = statPref.getInt("sanity", 100);
        endurance = statPref.getInt("endurance", 100);
        wealth = statPref.getInt("wealth", 100);
        anonymity = statPref.getInt("anonymity", 100);

        Bundle b = new Bundle();
        //String passed to show Stat change.
        String statChangeUp = "null";
        String statChangeDown = "null";

        if (selectedStat != null) {

            if (alterStats && !statUp) {
                if (selectedStat.equals("Strength Infuser") && strength >0) {
                    statMod = numCap(strength, statMod);
                    strength = (strength - statMod);
                    statChangeDown = "(-" + statMod + ")";
                    b.putString("strChange", statChangeDown);
                    altStr = true;
                }
                if (selectedStat.equals("Dexterity Animator") && dexterity >0) {
                    statMod = numCap(dexterity, statMod);
                    dexterity = (dexterity - statMod);
                    statChangeDown = "(-" + statMod + ")";
                    b.putString("dexChange", statChangeDown);
                    altDex = true;
                }
                if (selectedStat.equals("Intelligence Designer") && intelligence >0) {
                    statMod = numCap(intelligence, statMod);
                    intelligence = (intelligence - statMod);
                    statChangeDown = "(-" + statMod + ")";
                    b.putString("tempChange", statChangeDown);
                    altTemp = true;
                }
                if (selectedStat.equals("Loyalty Inculcator") && loyalty >0) {
                    statMod = numCap(loyalty, statMod);
                    loyalty = (loyalty - statMod);
                    statChangeDown = "(-" + statMod + ")";
                    b.putString("obedChange", statChangeDown);
                    altObed = true;
                }
                if (selectedStat.equals("Beauty Sculptor") && beauty >0) {
                    statMod = numCap(beauty, statMod);
                    beauty = (beauty - statMod);
                    statChangeDown = "(-" + statMod + ")";
                    b.putString("charChange", statChangeDown);
                    altChar = true;
                }
                if (selectedStat.equals("sanity") && sanity >0) {
                    statMod = numCap(sanity, statMod);
                    sanity = (sanity - statMod);
                    statChangeDown = "(-" + statMod + ")";
                    b.putString("sanChange", statChangeDown);
                    altSan = true;
                }
                if (selectedStat.equals("endurance") && endurance>0) {
                    statMod = numCap(endurance, statMod);
                    endurance = (endurance - statMod);
                    statChangeDown = "(-" + statMod + ")";
                    b.putString("endChange", statChangeDown);
                    altEnd = true;
                }
                if (selectedStat.equals("wealth") && wealth >0) {
                    statMod = numCap(wealth, statMod);
                    wealth = (wealth - statMod);
                    statChangeDown = "(-" + statMod + ")";
                    b.putString("comChange", statChangeDown);
                    altCom = true;
                }
                if (selectedStat.equals("anonymity") && anonymity >0) {
                    statMod = numCap(anonymity, statMod);
                    anonymity = (anonymity - statMod);
                    statChangeDown = "(-" + statMod + ")";
                    b.putString("anonChange", statChangeDown);
                    altAnon = true;
                }
            }

            //Sets Stat increase for Events 5&6 WITH Success roll, otherwise loss (above).
//        if (event >=5 && success) {
            if (alterStats && statUp) {
                if (selectedStat.equals("Strength Infuser")) {
                    strength = (strength + statMod);
                    statChangeUp = "(+" + statMod + ")";
                    b.putString("strChange", statChangeUp);
                    altStr = true;
                }
                if (selectedStat.equals("Dexterity Animator")) {
                    dexterity = (dexterity + statMod);
                    statChangeUp = "(+" + statMod + ")";
                    b.putString("dexChange", statChangeUp);
                    altDex = true;
                }
                if (selectedStat.equals("Intelligence Designer")) {
                    intelligence = (intelligence + statMod);
                    statChangeUp = "(+" + statMod + ")";
                    b.putString("tempChange", statChangeUp);
                    altTemp = true;
                }
                if (selectedStat.equals("Loyalty Inculcator")) {
                    loyalty = (loyalty + statMod);
                    statChangeUp = "(+" + statMod + ")";
                    b.putString("obedChange", statChangeUp);
                    altObed = true;
                }
                if (selectedStat.equals("Beauty Sculptor")) {
                    beauty = (beauty + statMod);
                    statChangeUp = "(+" + statMod + ")";
                    b.putString("charChange", statChangeUp);
                    altChar = true;
                }
                if (selectedStat.equals("sanity")) {
                    sanity = (sanity + statMod);
                    statChangeUp = "(+" + statMod + ")";
                    b.putString("sanChange", statChangeUp);
                    altSan = true;
                }
                if (selectedStat.equals("endurance")) {
                    endurance = (endurance + statMod);
                    statChangeUp = "(+" + statMod + ")";
                    b.putString("endChange", statChangeUp);
                    altEnd = true;
                }
                if (selectedStat.equals("wealth")) {
                    wealth = (wealth + statMod);
                    statChangeUp = "(+" + statMod + ")";
                    b.putString("comChange", statChangeUp);
                    altCom = true;
                }
                if (selectedStat.equals("anonymity")) {
                    anonymity = (anonymity + statMod);
                    statChangeUp = "(+" + statMod + ")";
                    b.putString("anonChange", statChangeUp);
                    altAnon = true;
                }
            }

            if (selectedStat.equals("strWealth")) {
                statMod = numCap(strength, statMod);
                strength = (strength - statMod);
                statChangeDown = "(-" + statMod + ")";
                b.putString("strChange", statChangeDown);
                altStr = true;
            }
            if (selectedStat.equals("dexWealth")) {
                statMod = numCap(dexterity, statMod);
                dexterity = (dexterity - statMod);
                statChangeDown = "(-" + statMod + ")";
                b.putString("dexChange", statChangeDown);
                altDex = true;
            }
            if (selectedStat.equals("intWealth")) {
                statMod = numCap(intelligence, statMod);
                intelligence = (intelligence - statMod);
                statChangeDown = "(-" + statMod + ")";
                b.putString("tempChange", statChangeDown);
                altTemp = true;
            }
            if (selectedStat.equals("beaWealth")) {
                statMod = numCap(beauty, statMod);
                beauty = (beauty - statMod);
                statChangeDown = "(-" + statMod + ")";
                b.putString("charChange", statChangeDown);
                altChar = true;
            }
            if (selectedStat.equals("loyWealth")) {
                statMod = numCap(loyalty, statMod);
                loyalty = (loyalty - statMod);
                statChangeDown = "(-" + statMod + ")";
                b.putString("obedChange", statChangeDown);
                altSan = true;
            }
            if (selectedStat.equals("strWealth") || selectedStat.equals("dexWealth") || selectedStat.equals("intWealth") || selectedStat.equals("beaWealth") || selectedStat.equals("loyWealth")) {
                wealth = (wealth + endStatMod);
                statChangeUp = "(+" + endStatMod + ")";
                b.putString("comChange", statChangeUp);
                altCom = true;
            }

            if (selectedStat.equals("demon")) {
                int newStatMod = random.nextInt(36-15) + 15;
                int statModTwo = random.nextInt(36-15) + 15;
                int statModThree = random.nextInt(36-15) + 15;
                int statModFour = random.nextInt(36-15) + 15;

                if (sanity >0) {
                    statMod = numCap(sanity, newStatMod);
                    sanity = (sanity - newStatMod);
                    String statChangeSan = "(-" + statMod + ")";
                    b.putString("sanChange", statChangeSan);

                }
                if (endurance >0) {
                    statMod = numCap(endurance, statModTwo);
                    endurance = (endurance - statModTwo);
                    String statChangeEnd = "(-" + statMod + ")";
                    b.putString("endChange", statChangeEnd);
                }
                if (wealth >0) {
                    statMod = numCap(wealth, statModThree);
                    wealth = (wealth - statModThree);
                    String statChangeWealth = "(-" + statMod + ")";
                    b.putString("comChange", statChangeWealth);

                }
                if (anonymity >0) {
                    statMod = numCap(anonymity, statModFour);
                    anonymity = (anonymity - statModFour);
                    String statChangeAnon = "(-" + statMod + ")";
                    b.putString("anonChange", statChangeAnon);
                }

            }

            if (selectedStat.equals("demonSan") || selectedStat.equals("demonEnd") || selectedStat.equals("demonWealth") || selectedStat.equals("demonAnon")) {


                if (selectedStat.equals("demonSan")) {
                    sanity = sanity + 500;
                    String statChangeSanity = "(+" + 500 + ")";
                    b.putString("sanChange", statChangeSanity);
                }
                if (selectedStat.equals("demonEnd")) {
                    endurance = (endurance + 500);
                    String statChangeEndurance = "(+" + 500 + ")";
                    b.putString("endChange", statChangeEndurance);
                }
                if (selectedStat.equals("demonWealth")) {
                    wealth = (wealth + 500);
                    String statChangeWealth = "(+" + 500 + ")";
                    b.putString("comChange", statChangeWealth);
                }
                if (selectedStat.equals("demonAnon")) {
                    anonymity = (anonymity + 500);
                    String statChangeAnon = "(+" + 500 + ")";
                    b.putString("anonChange", statChangeAnon);
                }

                if (alterStats) {
                    int newStatMod = random.nextInt(34 - 25) + 25;
                    int statModTwo = random.nextInt(34 - 25) + 25;
                    int statModThree = random.nextInt(34 - 25) + 25;
                    int statModFour = random.nextInt(34 - 25) + 25;
                    int statModFive = random.nextInt(34 - 25) + 25;

                    SharedPreferences pref = getApplicationContext().getSharedPreferences("SharedPref", 0);

                    String staticStat = pref.getString("staticStat", null);

                    List<String> statList = new ArrayList<>();
                    if (strength > 0 && !staticStat.equals("Strength Infuser")) {
                        statList.add("str");
                    }
                    if (dexterity > 0 && !staticStat.equals("Dexterity Animator")) {
                        statList.add("dex");
                    }
                    if (intelligence > 0 && !staticStat.equals("Intelligence Designer")) {
                        statList.add("int");
                    }
                    if (beauty > 0 && !staticStat.equals("Beauty Sculptor")) {
                        statList.add("bea");
                    }
                    if (loyalty > 0 && !staticStat.equals("Loyalty Inculcator"))  {
                        statList.add("loy");
                    }

                    if (statList.size() == 5) {
                        int st1 = random.nextInt(statList.size());
                        statList.remove(st1);
                        int st2 = random.nextInt(statList.size());
                        statList.remove(st2);
                    }

                    if (statList.size() == 4) {
                        int st1 = random.nextInt(statList.size());
                        statList.remove(st1);
                    }

                    if (statList.size() == 2) {
                        newStatMod = random.nextInt(51 - 35) + 35;
                        statModTwo = random.nextInt(51 - 35) + 35;
                        statModThree = random.nextInt(51 - 35) + 35;
                        statModFour = random.nextInt(51 - 35) + 35;
                        statModFive = random.nextInt(51 - 35) + 35;
                    }

                    if (statList.size() == 1) {
                        newStatMod = random.nextInt(100 - 66) + 66;
                        statModTwo = random.nextInt(100 - 66) + 66;
                        statModThree = random.nextInt(100 - 66) + 66;
                        statModFour = random.nextInt(100 - 66) + 66;
                        statModFive = random.nextInt(100 - 66) + 66;
                    }

                    if (statList.contains("str")) {
                        statMod = numCap(strength, newStatMod);
                        strength = (strength - newStatMod);
                        String statChangeStr = "(-" + statMod + ")";
                        b.putString("strChange", statChangeStr);
                    }

                    if (statList.contains("dex")) {
                        statMod = numCap(dexterity, statModTwo);
                        dexterity = (dexterity - statModTwo);
                        String statChangeDex = "(-" + statMod + ")";
                        b.putString("dexChange", statChangeDex);
                    }

                    if (statList.contains("int")) {
                        statMod = numCap(intelligence, statModThree);
                        intelligence = (intelligence - statModThree);
                        String statIntChange = "(-" + statMod + ")";
                        b.putString("tempChange", statIntChange);
                    }

                    if (statList.contains("bea")) {
                        statMod = numCap(beauty, statModFour);
                        beauty = (beauty - statModFour);
                        String statBeaChange = "(-" + statMod + ")";
                        b.putString("charChange", statBeaChange);
                    }

                    if (statList.contains("loy")) {
                        statMod = numCap(loyalty, statModFive);
                        loyalty = (loyalty - statModFive);
                        String statLoyChange = "(-" + statMod + ")";
                        b.putString("obedChange", statLoyChange);
                    }
                }
            }
        }

        SharedPreferences pref = getApplicationContext().getSharedPreferences("SharedPref", 0);
        SharedPreferences.Editor sharedEditor = pref.edit();


        b.putBoolean("altStr", altStr);
        b.putBoolean("altDex", altDex);
        b.putBoolean("altTemp", altTemp);
        b.putBoolean("altObed", altObed);
        b.putBoolean("altChar", altChar);
        b.putBoolean("altSan", altSan);
        b.putBoolean("altEnd", altEnd);
        b.putBoolean("altCom", altCom);
        b.putBoolean("altAnon", altAnon);

        b.putBoolean("clearStats", false);
        b.putBoolean("subtractVirus", true);

        final SharedPreferences.Editor editor = statPref.edit();
        editor.putInt("strength", strength);
        editor.putInt("dexterity", dexterity);
        editor.putInt("intelligence", intelligence);
        editor.putInt("loyalty", loyalty);
        editor.putInt("beauty", beauty);
        editor.putInt("sanity",sanity);
        editor.putInt("endurance", endurance);
        editor.putInt("wealth", wealth);
        editor.putInt("anonymity", anonymity);
        editor.apply();

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final StatsFragment statsFragment = new StatsFragment();

        statsFragment.setArguments(b);
        fragmentManager.beginTransaction()
                .replace(R.id.game_stats, statsFragment)
                .commit();

    }

    @Override
    public void onEndGame() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("SharedPref", 0);
        SharedPreferences.Editor editor = pref.edit();

        //Removes active state of game, which affects Start/Continue buttons.
        editor.putBoolean("isActive", false);
        editor.apply();

        Intent intent = new Intent(Game.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onEndGame(boolean isDead) {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("SharedPref", 0);
        SharedPreferences.Editor editor = pref.edit();

        //Removes active state of game, which affects Start/Continue buttons.
        editor.putBoolean("isActive", false);
        editor.apply();

        //Counts up one each time the db populates a row with JUST the "death" text. This is then subtracted in ScoreResultFragment from the position it pulls its non-death score from, since previous "death" endings only contain null score values.
        int counter = pref.getInt("sync", 0);
        counter++;
        editor.putInt("sync", counter);
        editor.apply();
        Date currentDate = Calendar.getInstance().getTime();
        Format formatter = new SimpleDateFormat("MMMM d, y h:mm a", Locale.US);
        final String date = formatter.format(currentDate);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                HighScoresDB highScoresDB = HighScoresDB.getDatabase(getApplicationContext());
                HighScores highScores = new HighScores();
                ScoresMenu scoresMenu = new ScoresMenu();
                ScoreResults scoreResults = new ScoreResults();

                scoresMenu.setRank(getString(R.string.dead));
                scoresMenu.setDate(date);
                scoresMenu.setTotalScore(0);
                highScoresDB.highScoresDao().insert(scoresMenu);
                highScoresDB.highScoresDao().insert(highScores);
                highScoresDB.highScoresDao().insert(scoreResults);
            }
        });


        Intent intent = new Intent(Game.this, MainActivity.class);
        startActivity(intent);

    }

    private Bundle loadAltStats() {
        Bundle bundle = new Bundle();

        bundle.putBoolean("altStr", altStr);
        bundle.putBoolean("altDex", altDex);
        bundle.putBoolean("altTemp", altTemp);
        bundle.putBoolean("altObed", altObed);
        bundle.putBoolean("altChar", altChar);
        bundle.putBoolean("altSan", altSan);
        bundle.putBoolean("altEnd", altEnd);
        bundle.putBoolean("altCom", altCom);
        bundle.putBoolean("altAnon", altAnon);
        bundle.putBoolean("clearStats", false);

        return bundle;
    }

    @Override
    public void onClearStats() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        StatsFragment statsFragment = new StatsFragment();

        Bundle bundle = new Bundle();

        statsFragment.setArguments(loadAltStats());
        bundle.putBoolean("clearStats", true);
        statsFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.game_stats, statsFragment)
                .commit();
    }

    @Override
    public void onShowPercentages(int keptTer, int keptPop, int newTer, int newSup, boolean terPos, boolean supPos) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        StatsFragment statsFragment = new StatsFragment();

        String terChange = "";
        String supChange = "";

        if (newTer != 1000) {
            if (terPos && newTer >0) {
                terChange = "(+" + newTer + ")";
            } else {
                terChange = "(-" + Math.abs(newTer) + ")";
            }
            if (supPos && newSup >0) {
                supChange = "(+" + newSup + ")";
            } else {
                supChange = "(-" + Math.abs(newSup) + ")";
            }
        }
        if (newTer == 0) {
            terChange = "";
        }
        if (newSup == 0) {
            supChange = "";
        }


        Bundle b = new Bundle();

        b.putInt("keptTer", keptTer);
        b.putInt("keptPop", keptPop);
        b.putString("terChange", terChange);
        b.putString("supChange", supChange);

        b.putInt("newTer", newTer);
        b.putInt("newPop", newSup);
        //Put in just so a "500" value can be passed in to trigger a conditional in Stats.
        statsFragment.setArguments(b);

        fragmentManager.beginTransaction()
                .replace(R.id.game_stats, statsFragment)
                .commit();
    }

    public void resetStats() {
        SharedPreferences statPref = getApplicationContext().getSharedPreferences("stats", 0);
        SharedPreferences.Editor statEdit = statPref.edit();

        //Resetting stats.
        statEdit.putInt("strength", 100);
        statEdit.putInt("dexterity", 100);
        statEdit.putInt("intelligence", 100);
        statEdit.putInt("loyalty", 100);
        statEdit.putInt("beauty", 100);
        statEdit.putInt("sanity", 100);
        statEdit.putInt("endurance", 100);
        statEdit.putInt("wealth", 100);
        statEdit.putInt("anonymity", 100);
        statEdit.putInt("injections", 10);
        statEdit.apply();

        SharedPreferences pref = getApplicationContext().getSharedPreferences("SharedPref", 0);
        SharedPreferences.Editor editor = pref.edit();

        //Resetting days played.
        editor.putInt("daysPlayed", 0);
        editor.putInt("scannerRoll", 0);

        //Resetting upgrade level.
        editor.putInt("upgradeStr", 0);
        editor.putInt("upgradeDex", 0);
        editor.putInt("upgradeTemp", 0);
        editor.putInt("upgradeObed", 0);
        editor.putInt("upgradeChar", 0);

        editor.putBoolean("showEndStats", false);

        editor.apply();
    }

    public void resetExtras() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("SharedPref", 0);
        final SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean("demonSpawned", false);
        editor.putBoolean("demonSpawnedTwo", false);
        editor.putBoolean("virusDone", false);
        editor.putBoolean("machineEbb", false);
        editor.putBoolean("manEbb", false);
        editor.putBoolean("subtractVirus", false);
        editor.putBoolean("dealStruck", false);
        editor.putBoolean("dealStruckTwo", false);
        editor.putString("dealStat", "null");
        editor.putString("staticStat", "null");

        editor.putBoolean("sanZero", false);
        editor.putBoolean("anonZero", false);
        editor.putBoolean("insanePrompt", false);
        editor.putBoolean("stopRefreshSan", false);
        editor.putBoolean("stopRefreshEnd", false);
        editor.putBoolean("stopRefreshAnon", false);
        editor.putBoolean("stopRefreshMan", false);
        editor.putBoolean("stopRefreshMachine", false);

        editor.putInt("endDaysCounter", 0);
        editor.putInt("endDaysLeft", 0);
        editor.putBoolean("endDaysLaunch", false);
        editor.putBoolean("anonZeroIncin", false);
        editor.putBoolean("endZeroIncin", false);

        editor.apply();
    }

    public int numCap(int var1, int var2) {
        int newVar = 0;
        if (var1 < var2) {
            newVar = var1;
            return newVar;
        } else {
            return var2;
        }
    }

}