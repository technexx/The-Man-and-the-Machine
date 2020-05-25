package technexx.android.ManMachine;

import android.annotation.SuppressLint;
import android.app.usage.UsageStats;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import technexx.android.ManMachine.Database.HighScores;
import technexx.android.ManMachine.Database.HighScoresDB;
import technexx.android.ManMachine.Database.ScoreResults;
import technexx.android.ManMachine.Database.ScoresMenu;
import technexx.android.ManMachine.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class StoryFragment extends Fragment {

    private View root;

    private HighScoresDB scoresDB;
    private HighScores scores;
    private ScoresMenu scoresMenu;
    private ScoreResults scoreResults;

    private int strength;
    private int dexterity;
    private int intelligence;
    private int loyalty;
    private int beauty;
    private int sanity;
    private int endurance;
    private int wealth;
    private int anonymity;
    private int fluxInjections;

    private int eventRoll;

    private int eventOne;
    private int eventTwo;
    private int eventThree;
    private String selectedStat;
    private String selectedStatTwo;
    private String selectedStatThree;
    private String selectedStatFour;

    private boolean stopStatRoll;
    private boolean stopStatRollTwo;
    private boolean stopStatRollThree;

    private boolean noManStats;
    private boolean noMachineStats;
    private boolean oneMachineStat;


    choiceResultCallback mChoiceResult;
    nextDayCallback mNextDayCallback;
    endGameCallback mOnEndGameCallback;;
    refreshStoryCallback mRefreshStoryCallback;

    //Remember, global int variables always default to 0, so they do not need to be initialized.
    int scannerRoll;
    int upgradeRoll;
    int entropyRoll;

    private int upgradeStr;
    private int upgradeDex;
    private int upgradeTemp;
    private int upgradeObed;
    private int upgradeChar;

    private boolean isUpgrading;
    private boolean noMoreUpgrades;

    private boolean noRepeatOne;
    private boolean noRepeatTwo;
    private boolean noRepeatThree;
    private boolean noRepeatFive;

    private int lastEvent;
    private int lastEventTwo;
    private boolean demonSpawned;
    private boolean demonSpawnedTwo;
    private boolean machineVirus;
    private boolean virusDone;

    private boolean tookBribe;
    private boolean insanePrompt;

    private int endDaysLeft;

    private int daysPlayed;

    private boolean stopRefreshSan;
    private boolean stopRefreshEnd;
    private boolean stopRefreshAnon;
    private boolean stopRefreshMachine;
    private boolean stopRefreshMan;

    private List<String> postUpgradeList;
    private boolean post1;
    private boolean post2;
    private boolean post3;
    private boolean post4;
    private boolean post5;
    private boolean postUpgradeChosen;
    private boolean postUpgradeDone;

    public interface choiceResultCallback {
        void onChoiceResult(boolean alterStats, String newStat, boolean statUp, boolean extra);
    }

    public interface nextDayCallback {
        void onNextDay();
    }

    public interface endGameCallback {
        void onEndGame(boolean isDead);
    }

    public interface refreshStoryCallback {
        void onRefreshStory();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mChoiceResult = (choiceResultCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement choiceResultCallback");
        }
        try {
            mNextDayCallback = (nextDayCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement nextDayCallback");
        }
        try {
              mOnEndGameCallback = (endGameCallback) context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString() + "Must implement endGameCallback");
        }
        try {
            mRefreshStoryCallback = (refreshStoryCallback) context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString() + "Must implement refreshStoryCallback");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.story_fragment, container, false);

        //Moves back to main menu on Android "back" button.
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }};
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        final Button b1 = root.findViewById(R.id.first_choice);
        final Button b2 = root.findViewById(R.id.second_choice);
        final Button b3 = root.findViewById(R.id.third_choice);
        final Button b4 = root.findViewById(R.id.fourth_choice);
        final Button b5 = root.findViewById(R.id.fifth_choice);
        final Button b7 = root.findViewById(R.id.continue_mission);
        final Button b8 = root.findViewById(R.id.continue_two);
        b7.setText(R.string.next_day);

        final ScrollView scrollView = root.findViewById(R.id.story_scrollView);

        b1.setVisibility(View.VISIBLE);
        b2.setVisibility(View.VISIBLE);
        b3.setVisibility(View.GONE);
        b4.setVisibility(View.GONE);
        b5.setVisibility(View.GONE);
        b8.setVisibility(View.GONE);

        Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        b1.startAnimation(fadeIn);
        b2.startAnimation(fadeIn);
        b3.startAnimation(fadeIn);
        b4.startAnimation(fadeIn);
        b5.startAnimation(fadeIn);
        b7.startAnimation(fadeIn);
        b8.startAnimation(fadeIn);

        //Continue button
        b7.setVisibility(View.GONE);

        final String[] descOne = getResources().getStringArray(R.array.d_1);
        final String[] descTwo = getResources().getStringArray(R.array.d_2);
        final String[] descThree = getResources().getStringArray(R.array.d_3);
        final String[] descFour = getResources().getStringArray(R.array.d_4);
        final String[] descFive = getResources().getStringArray(R.array.d_5);
        final String[] descSix = getResources().getStringArray(R.array.d_6);
        final String[] descSeven = getResources().getStringArray(R.array.d_7);
        final String[] descEight = getResources().getStringArray(R.array.d_8);
        final String[] descNine = getResources().getStringArray(R.array.d_9);
        final String[] descTen = getResources().getStringArray(R.array.d_10);
        final String[] descEleven = getResources().getStringArray(R.array.d_11);
        final String[] descTwelve = getResources().getStringArray(R.array.d_12);
        final String[] descSixteen = getResources().getStringArray(R.array.d_16);
        final String[] descSeventeen = getResources().getStringArray(R.array.d_17);
        final String[] descEighteen = getResources().getStringArray(R.array.d_18);
        final String[] descNineteen = getResources().getStringArray(R.array.d_19);
        final String[] descTwenty = getResources().getStringArray(R.array.d_20);
        final String[] descTwentyOne = getResources().getStringArray(R.array.d_21);
        final String[] descTwentyTwo = getResources().getStringArray(R.array.d_22);
        final String[] descTwentyThree = getResources().getStringArray(R.array.d_23);
        final String[] descTwentyFour = getResources().getStringArray(R.array.d_24);
        final String[] descTwentyFive = getResources().getStringArray(R.array.d_25);
        final String[] descTwentySix = getResources().getStringArray(R.array.d_26);
        final String[] descTwentySeven = getResources().getStringArray(R.array.d_27);
        final String[] descTwentyEight = getResources().getStringArray(R.array.d_28);
        final String[] descTwentyNine = getResources().getStringArray(R.array.d_29);
        final String[] descThirty = getResources().getStringArray(R.array.d_30);
        final String[] descThirtyOne = getResources().getStringArray(R.array.d_31);
        final String[] descThirtyTwo = getResources().getStringArray(R.array.d_32);
        final String[] descThirtyThree = getResources().getStringArray(R.array.d_33);
        final String[] descThirtyFour = getResources().getStringArray(R.array.d_34);
        final String[] descThirtyFive = getResources().getStringArray(R.array.d_35);
        final String[] descThirtySix = getResources().getStringArray(R.array.d_36);
        final String[] descThirtySeven = getResources().getStringArray(R.array.d_37);
        final String[] descThirtyEight = getResources().getStringArray(R.array.d_38);
        final String[] descThirtyNine = getResources().getStringArray(R.array.d_39);
        final String[] descForty = getResources().getStringArray(R.array.d_40);
        final String[] descFortyOne = getResources().getStringArray(R.array.d_41);
        final String[] descFortyTwo = getResources().getStringArray(R.array.d_42);
        final String[] descFortyThree = getResources().getStringArray(R.array.d_43);
        final String[] descFortyFour = getResources().getStringArray(R.array.d_44);
        final String[] descFortyFive = getResources().getStringArray(R.array.d_45);
        final String[] descFortySix = getResources().getStringArray(R.array.d_46);
        final String[] descFortySeven = getResources().getStringArray(R.array.d_47);
        final String[] descFortyEight = getResources().getStringArray(R.array.d_48);
        final String[] descFortyNine = getResources().getStringArray(R.array.d_49);
        final String[] descFifty = getResources().getStringArray(R.array.d_50);
        final String[] descFiftyOne = getResources().getStringArray(R.array.d_51);
        final String[] descFiftyTwo = getResources().getStringArray(R.array.d_52);
        final String[] descFiftyThree = getResources().getStringArray(R.array.d_53);
        final String[] descFiftyFour = getResources().getStringArray(R.array.d_54);
        final String[] descFiftyFive = getResources().getStringArray(R.array.d_55);

        final TextView event = root.findViewById(R.id.plot);
        final TextView resultOne = root.findViewById(R.id.result);

        final SharedPreferences pref = getContext().getSharedPreferences("SharedPref", 0);
        final SharedPreferences.Editor editor = pref.edit();

        final SharedPreferences statPref = getContext().getSharedPreferences("stats", 0);
        final SharedPreferences.Editor statEditor = statPref.edit();

        final Random random = new Random();
        final int genieRoll = random.nextInt(5-1) +1;

        strength = statPref.getInt("strength", 100);
        dexterity = statPref.getInt("dexterity", 100);
        intelligence = statPref.getInt("intelligence", 100);
        loyalty = statPref.getInt("loyalty", 100);
        beauty = statPref.getInt("beauty", 100);
        sanity = statPref.getInt("sanity", 100);
        endurance = statPref.getInt("endurance", 100);
        wealth = statPref.getInt("wealth", 100);
        anonymity = statPref.getInt("anonymity", 100);
        fluxInjections = statPref.getInt("injections", 10);

        //A boolean has been set to true by certain events rolling. Rolls for another event, exclusive of the ones we do not want repeating, are then rolled here and the boolean is set back to false, allowing for the event to be rolled again (just not consecutively).
        noRepeatOne = pref.getBoolean("noRepeatOne", false);
        noRepeatTwo = pref.getBoolean("noRepeatTwo", false);
        noRepeatThree = pref.getBoolean("noRepeatThree", false);
        noRepeatFive = pref.getBoolean("noRepeatFive", false);

        demonSpawned = pref.getBoolean("demonSpawned", false);
        demonSpawnedTwo = pref.getBoolean("demonSpawnedTwo", false);
        virusDone = pref.getBoolean("virusDone", false);
        postUpgradeDone = pref.getBoolean("postUpgradeDone", false);

        stopRefreshSan = pref.getBoolean("stopRefreshSan", false);
        stopRefreshEnd = pref.getBoolean("stopRefreshEnd", false);
        stopRefreshAnon = pref.getBoolean("stopRefreshAnon", false);
        stopRefreshMachine = pref.getBoolean("stopRefreshMachine", false);
        stopRefreshMan = pref.getBoolean("stopRefreshMan", false);

        String staticStat = pref.getString("staticStat", "null");

        endDaysLeft = pref.getInt("endsDaysLeft", 0);

        lastEvent = pref.getInt("lastEvent", 998);
        lastEventTwo = pref.getInt("lastEvent", 999);

        daysPlayed = pref.getInt("daysPlayed", daysPlayed);

        //These need to be instantiated here so it can pass through cases w/ OneMachineStat active.
        selectedStat = "null";
        selectedStatTwo = "null";

        if (daysPlayed <3) {
            eventRoll = random.nextInt(7-1) + 1;
        }
        if (daysPlayed >=3 && daysPlayed <6) {
            eventRoll = random.nextInt(13-1) + 1;
        }
        if (daysPlayed >=6 && daysPlayed <=10) {
            eventRoll = random.nextInt(19-1) + 1;
        }
        if (daysPlayed >10) {
            eventRoll = random.nextInt(26-1) + 1;
        }

        if (noRepeatOne) {
            editor.putBoolean("noRepeatOne", false);
            editor.apply();
            while (eventRoll == 19 || eventOne == 20 || eventOne == 21) {
                eventRoll = new Random().nextInt(26-1) + 1;
            }
        }
        if (noRepeatTwo) {
            editor.putBoolean("noRepeatTwo", false);
            editor.apply();
            while (eventRoll == 22 || eventOne == 23) {
                eventRoll = new Random().nextInt(26 - 1) + 1;
            }
        }

        if (noRepeatTwo) {
            editor.putBoolean("noRepeatThree", false);
            editor.apply();
            while (eventRoll == 24 || eventOne == 25) {
                eventRoll = new Random().nextInt(26 - 1) + 1;
            }
        }

        upgradeStr = pref.getInt("upgradeStr", 0);
        upgradeDex = pref.getInt("upgradeDex", 0);
        upgradeTemp = pref.getInt("upgradeTemp", 0);
        upgradeObed = pref.getInt("upgradeObed", 0);
        upgradeChar = pref.getInt("upgradeChar", 0);

        //Only adding stats to ArrayList if they are more than zero. This way they will not be rolled for and not appear in any events that could lower their value.
        final ArrayList<String> firstStatRoll = new ArrayList<>();
        if (strength > 0) {
            firstStatRoll.add("Strength Infuser");
        }
        if (dexterity > 0){
            firstStatRoll.add("Dexterity Animator");
        }
        if (intelligence > 0) {
            firstStatRoll.add("Intelligence Designer");
        }
        if (loyalty > 0) {
            firstStatRoll.add("Loyalty Inculcator");
        }
        if (beauty > 0)  {
            firstStatRoll.add("Beauty Sculptor");
        }
        //This boolean is used to skip over all machine-related events if they are all at 0.
        if (firstStatRoll.size() == 0) {
            noMachineStats = true;
            editor.putBoolean("noMachineStats", true);
            editor.apply();
        }
        //Active boolean to stop rolling in this array if size is 0 or 1 (will crash).
        if (firstStatRoll.size() <= 1) {
            stopStatRoll = true;
        }

        ArrayList<String> secondStatRoll = new ArrayList<>();
        if (sanity > 0) {
            secondStatRoll.add("sanity");
        }
        if (endurance > 0) {
            secondStatRoll.add("endurance");
        }
        //Active boolean to stop rolling in this array if size is 0 or 1 (will crash).
        if (secondStatRoll.size() <= 1) {
            stopStatRollTwo = true;
        }

        ArrayList<String> thirdStatRoll = new ArrayList<>();
        if (wealth > 0) {
            thirdStatRoll.add("wealth");
        }
        if (anonymity >0) {
            thirdStatRoll.add("anonymity");
        }

        if (upgradeStr >=3 && upgradeDex >=3 && upgradeTemp >=3 && upgradeObed >=3 && upgradeChar >= 3) {
            noMoreUpgrades = true;
        }

        scannerRoll = pref.getInt("scannerRoll", scannerRoll);
        scannerRoll++;
        editor.putInt("scannerRoll", scannerRoll);
        editor.apply();
        if (scannerRoll >=3 && (!noMoreUpgrades) && eventOne !=100) {
            upgradeRoll = random.nextInt(3-1) + 1;
            if (upgradeRoll == 1) {
                eventRoll = 99;
                editor.putInt("scannerRoll", 0);
                editor.apply();
                //Used to ensure other events do not overwrite this when active.
                isUpgrading = true;
            }
        }

        if (staticStat.equals("Strength Infuser")) {
            editor.putInt("upgradeStr", 3);
        }
        if (staticStat.equals("Dexterity Animator")) {
            editor.putInt("upgradeDex", 3);
        }
        if (staticStat.equals("Intelligence Designer")) {
            editor.putInt("upgradeTemp", 3);
        }
        if (staticStat.equals("Loyalty Inculcator")) {
            editor.putInt("upgradeObed", 3);
        }
        if (staticStat.equals("Beauty Sculptor")) {
            editor.putInt("upgradeChar", 3);
        }
        editor.apply();

        //Entropy roll needs to be after upgrade roll, so it overrides it.
        if (daysPlayed >= 20) {
            entropyRoll = pref.getInt("entropyRoll", entropyRoll);
            entropyRoll++;
            editor.putInt("entropyRoll", entropyRoll);
            editor.apply();
            if (entropyRoll >=3) {
                int entropy = new Random().nextInt(3 - 1) + 1;
                if (entropy == 2  && wealth >0) {
                    eventOne = 200;
                    eventRoll = 200;
                    editor.putInt("entropyRoll", 0);
                    editor.apply();
                }
            }
        }

        //Sets up for Upgrade choice. Removes buttons if upgrade is maxed.
        if (eventRoll == 99) {
            b7.setVisibility(View.GONE);

            eventOne = 50;
            event.setText((R.string.upgrade_one));
            if (upgradeStr <3 && !staticStat.equals("Strength Infuser")) {
                b1.setText((R.string.strength_upgrade));
            } else {
                b1.setVisibility(View.GONE);
            }
            if (upgradeDex <3 && !staticStat.equals("Dexterity Animator")) {
                b2.setText((R.string.dexterity_upgrade));
            } else {
                b2.setVisibility(View.GONE);
            }
            if (upgradeTemp <3 && !staticStat.equals("Intelligence Designer")) {
                b3.setText((R.string.intelligence_upgrade));
                b3.setVisibility(View.VISIBLE);
            }
            if (upgradeChar <3 && !staticStat.equals("Beauty Sculptor")) {
                b4.setText((R.string.beauty_upgrade));
                b4.setVisibility(View.VISIBLE);
            }
            if (upgradeObed <3 && !staticStat.equals("Loyalty Inculcator")) {
                b5.setText((R.string.loyalty_upgrade));
                b5.setVisibility(View.VISIBLE);
            }
        }

        if (!stopStatRoll) {
            //Needed to populate array list.
            selectedStat = firstStatRoll.get(random.nextInt(firstStatRoll.size()));
            selectedStatTwo = firstStatRoll.get(random.nextInt(firstStatRoll.size()));
            //Keeps rolling until values are different.
            while (selectedStat.equals(selectedStatTwo)) {
                selectedStat = firstStatRoll.get(random.nextInt(firstStatRoll.size()));
                selectedStatTwo = firstStatRoll.get(random.nextInt(firstStatRoll.size()));
            }
        }

        //With one item in the array (e.g. san is 0), only item will always be the same thus infinite loop crash.
        if (!stopStatRollTwo) {
            //Needed to populate array list.
            selectedStatThree = secondStatRoll.get(random.nextInt(secondStatRoll.size()));
            selectedStatFour = secondStatRoll.get(random.nextInt(secondStatRoll.size()));
            while (selectedStatThree.equals(selectedStatFour)) {
                selectedStatThree = secondStatRoll.get(random.nextInt(secondStatRoll.size()));
                selectedStatFour = secondStatRoll.get(random.nextInt(secondStatRoll.size()));
            }
        }

        insanePrompt = pref.getBoolean("insanePrompt", false);

        if (wealth <=0 && anonymity >0 && endurance >0 && !noMachineStats) {
            eventRoll = 104;
            eventOne = 104;
            event.setText(R.string.wealth_zero);
            b1.setText(R.string.str_wealth);
            b2.setText(R.string.dex_wealth);
            if (strength <=0) {
                b1.setVisibility(View.GONE);
            }
            if (dexterity <=0) {
                b2.setVisibility(View.GONE);
            }
            if (intelligence >0) {
                b3.setVisibility(View.VISIBLE);
                b3.setText(R.string.int_wealth);
            }
            if (beauty >0) {
                b4.setVisibility(View.VISIBLE);
                b4.setText(R.string.bea_wealth);
            }
            if (loyalty >0) {
                b5.setVisibility(View.VISIBLE);
                b5.setText(R.string.loy_wealth);
            }
        }

        if (!insanePrompt && sanity <=0 && anonymity >0) {
            eventRoll = 102;
            eventOne = 102;
            event.setText(R.string.zero_san);
            editor.putBoolean("insanePrompt", true);
            editor.putBoolean("sanZero", true);
            editor.apply();
            b1.setVisibility(View.GONE);
            b2.setVisibility(View.GONE);
            b3.setVisibility(View.GONE);
            b4.setVisibility(View.GONE);
            b5.setVisibility(View.GONE);
            b8.setVisibility(View.VISIBLE);
        }

        if (anonymity <=0 && endurance >0) {
            eventRoll = 103;
            eventOne = 103;
            editor.putBoolean("anonZero", true);
            editor.apply();
            event.setText(R.string.zero_anon);
            b1.setText(R.string.anon_zero_yes);
            b2.setText(R.string.anon_zero_no);
            b3.setVisibility(View.GONE);
            b4.setVisibility(View.GONE);
            b5.setVisibility(View.GONE);
        }

        if (endurance <=0 && sanity >0) {
            event.setText(R.string.zero_endurance);
            eventRoll = 101;
            eventOne = 101;
            b1.setVisibility(View.VISIBLE);
            b2.setVisibility(View.VISIBLE);
            b3.setVisibility(View.VISIBLE);
            b1.setText(R.string.zero_endOne);
            b2.setText(R.string.zero_endTwo);
            b3.setText(R.string.zero_endThree);
            b4.setVisibility(View.GONE);
            b5.setVisibility(View.GONE);
        }

        //All machine components at 0. Ending game.
        if (noMachineStats && endurance >0 && anonymity >0) {
            eventOne = 105;
            eventRoll = 105;
            event.setText(R.string.machine_gone);
            b1.setVisibility(View.GONE);
            b2.setVisibility(View.GONE);
            b3.setVisibility(View.GONE);
            b4.setVisibility(View.GONE);
            b5.setVisibility(View.GONE);
            b8.setVisibility(View.VISIBLE);
        }

        //Sanity and Endurance both at 0. Ending game. Must execute before any (while) method or else the dual null arrays will infinite loop/crash.
        if (secondStatRoll.size() == 0) {
            eventOne = 100;
            eventRoll = 100;
            event.setText(R.string.no_man_stat_left);
            b1.setVisibility(View.GONE);
            b2.setVisibility(View.GONE);
            b3.setVisibility(View.GONE);
            b4.setVisibility(View.GONE);
            b5.setVisibility(View.GONE);
            b8.setVisibility(View.VISIBLE);
        }

        if (firstStatRoll.size() == 1) {
            oneMachineStat = true;
            selectedStat = firstStatRoll.get(0);
        }

        if (eventRoll <= 6 && (!noMachineStats) && (!noManStats) && !isUpgrading) {
            eventOne = random.nextInt(9-1) + 1;
            b1.setVisibility(View.VISIBLE);
            b2.setVisibility(View.VISIBLE);
            b7.setVisibility(View.GONE);

            while ( (eventOne == 3 && anonymity <=0) || (eventOne == 4 && endurance <=0) || (eventOne == 8 && sanity <=0) || lastEvent == eventOne || (oneMachineStat && eventOne == 2) ) {
                eventOne = random.nextInt(9-1) + 1;
            }
            switch (eventOne) {
                case 1:
                    event.setText(getString(R.string.choice_one, descOne[randomTen()], descTwo[randomTen()], descThree[randomTen()], descFour[randomTen()], selectedStat, descFive[randomTen()], selectedStatTwo));
                    b1.setText(getString(R.string.choice_one_yes));
                    b2.setText(getString(R.string.choice_one_no));
                    break;
                case 2:
                    event.setText(getString(R.string.choice_two, descOne[randomTen()], descTwo[randomTen()], descThree[randomTen()], descFour[randomTen()], selectedStat, selectedStatTwo, descFive[randomTen()]));

                    List<String> list = new ArrayList<>();
                    if (selectedStat.equals("Strength Infuser") || selectedStatTwo.equals("Strength Infuser")) {
                        list.add("Strength Infuser");
                    }
                    if (selectedStat.equals("Dexterity Animator") || selectedStatTwo.equals("Dexterity Animator")) {
                        list.add("Dexterity Animator");
                    }
                    if (selectedStat.equals("Intelligence Designer") || selectedStatTwo.equals("Intelligence Designer")) {
                        list.add("Intelligence Designer");
                    }
                    if (selectedStat.equals("Beauty Sculptor") || selectedStatTwo.equals("Beauty Sculptor")) {
                        list.add("Beauty Sculptor");
                    }
                    if (selectedStat.equals("Loyalty Inculcator") || selectedStatTwo.equals("Loyalty Inculcator")) {
                        list.add("Loyalty Inculcator");
                    }

                    selectedStat = list.get(0);
                    selectedStatTwo = list.get(1);

                    b1.setText(getString(R.string.choice_two_yes, selectedStat));
                    b2.setText(getString(R.string.choice_two_yes, selectedStatTwo));
                    break;
                    //Switching opt-out to anon.
                case 3:
                    event.setText(getString(R.string.choice_three, descSix[randomTen()], descSeven[randomTen()], selectedStat, descFour[randomTen()]));
                    b1.setText(getString(R.string.choice_three_yes));
                    b2.setText(getString(R.string.choice_one_no));
                    break;
                    //Switching opt-out to com.
                case 4:
                    event.setText(getString(R.string.choice_four, descFortyTwo[randomTen()], descFortyOne[randomTen()], descSeven[randomTen()], descThirtyOne[randomTen()], descThirtyTwo[randomTen()]));
                    b1.setText(getString(R.string.choice_four_yes));
                    b2.setText(getString(R.string.choice_four_no));
                    break;
                case 5:
                    event.setText(getString(R.string.choice_five));
                    //Reveals extra choice buttons for picking which scanner will take damage.
                    b1.setText(getString(R.string.strength_fail));
                    b2.setText(getString(R.string.dexterity_fail));
                    b3.setText(getString(R.string.intelligence_fail));
                    b4.setText(getString(R.string.beauty_fail));
                    b5.setText(getString(R.string.loyalty_fail));
                    //Removes str/dex from damage if at 0, since b1/b2 are visible by default. Only shoes b3/b4/b5 for other stats if above 0.
                    if (strength <=0) {
                        b1.setVisibility(View.GONE);
                    }
                    if (dexterity <=0) {
                        b2.setVisibility(View.GONE);
                    }
                    if (intelligence >0) {
                        b3.setVisibility(View.VISIBLE);
                    }
                    if (beauty >0) {
                        b4.setVisibility(View.VISIBLE);
                    }
                    if (loyalty >0) {
                        b5.setVisibility(View.VISIBLE);
                    }
                    break;
                case 6:
                    event.setText(getString(R.string.choice_six, descThirtySeven[randomTen()], descThirtySeven[randomTen()], selectedStat));
                    b1.setText(getString(R.string.choice_six_yes));
                    b2.setText(getString(R.string.choice_six_no));
                    break;
                case 7:
                    event.setText(getString(R.string.choice_seven, selectedStat));
                    b1.setText(getString(R.string.choice_seven_yes));
                    b2.setText(getString(R.string.choice_seven_no));
                    break;
                case 8:
                    event.setText(getString(R.string.choice_eight, descFortyFour[randomTen()]));
                    b1.setText(getString(R.string.choice_eight_yes));
                    b2.setText(getString(R.string.choice_eight_no));
            }
            editor.putInt("lastEvent", eventOne);
            editor.apply();
            if (oneMachineStat) {
                b1.setVisibility(View.GONE);
                b2.setVisibility(View.GONE);
                b3.setVisibility(View.GONE);
                b4.setVisibility(View.GONE);
                b5.setVisibility(View.GONE);
                b8.setVisibility(View.VISIBLE);
                event.setText(getString(R.string.one_machine_stat_left, selectedStat));
                mChoiceResult.onChoiceResult(true, selectedStat, false, false);
            }
        }

        if ( (eventRoll >6 && eventRoll <=12) && (!noMachineStats) && (!noManStats) && !isUpgrading) {
            eventOne = random.nextInt(19-10) + 10;

            //Keeps rolling for events if their stat is zero AND is selected as a potential loss.
            while ( ((sanity <= 0 || endurance <= 0) && (eventOne == 10 || eventOne == 15) || (wealth <= 0 && eventOne == 11) || ( (anonymity <=0 || endurance <=0) && eventOne == 12)) || (sanity <=0 || wealth <=0) && eventOne == 14 || (wealth <=0 || anonymity <=0) && eventOne == 16 || (wealth <=0 || endurance <=0) && eventOne == 17 || lastEvent == eventOne) {
                eventOne = random.nextInt(19-10) + 10;
            }
            b1.setVisibility(View.VISIBLE);
            b2.setVisibility(View.VISIBLE);
            b7.setVisibility(View.GONE);

            switch (eventOne) {
                case 10:
                    b1.setText(getString(R.string.choice_ten_yes));
                    b2.setText(getString(R.string.choice_ten_no));
                    event.setText(getString(R.string.choice_ten, descSixteen[randomTen()], descSeventeen[randomTen()]));
                    break;
                case 11:
                    b1.setText(getString(R.string.choice_eleven_yes));
                    b2.setText(getString(R.string.choice_eleven_no));
                    event.setText(getString(R.string.choice_eleven, descSeven[randomTen()], descTwentyTwo[randomTen()]));
                    break;
                case 12:
                    b1.setText(getString(R.string.choice_twelve_yes));
                    b2.setText(getString(R.string.choice_twelve_no));
                    event.setText(getString(R.string.choice_twelve, descTwelve[randomTen()]));
                    break;
                case 13:
                    event.setText(getString(R.string.choice_thirteen, descTwentyNine[randomTen()], descThirty[randomTen()], descThirtyOne[randomTen()], selectedStat, descThirtyTwo[randomTen()]));
                    b1.setText(getString(R.string.choice_thirteen_yes));
                    b2.setText(getString(R.string.choice_thirteen_no));
                    break;
                case 14:
                    event.setText(getString(R.string.choice_fourteen, descThirtyThree[randomTen()]));
                    b1.setText(getString(R.string.choice_fourteen_yes));
                    b2.setText(getString(R.string.choice_fourteen_no));
                    break;
                case 15:
                    String relative = descThirtyFour[randomTen()];
                    event.setText(getString(R.string.choice_fifteen, relative, descThirtyFive[randomTen()], relative));
                    b1.setText(getString(R.string.choice_fifteen_yes));
                    b2.setText(getString(R.string.choice_fifteen_no));
                    break;
                case 16:
                    event.setText(getString(R.string.choice_sixteen, descTwentyNine[randomTen()], descFortyFour[randomTen()]));
                    b1.setText(getString(R.string.choice_sixteen_yes));
                    b2.setText(getString(R.string.choice_sixteen_no));
                    break;
                case 17:
                    event.setText(getString(R.string.choice_seventeen, descFortyFive[randomTen()], descFortySix[randomTen()]));
                    b1.setText(getString(R.string.choice_seventeen_yes));
                    b2.setText(getString(R.string.choice_seventeen_no));
                    break;
                case 18:
                    event.setText(getString(R.string.choice_eighteen, descSix[randomTen()], descEight[randomTen()], descSeven[randomTen()], selectedStat, descForty[randomTen()]));
                    b1.setText(getString(R.string.choice_eighteen_yes));
                    b2.setText(getString(R.string.choice_one_no));
            }
            editor.putInt("lastEvent", eventOne);
            editor.apply();
            if (oneMachineStat && (eventOne == 13 || eventOne == 18) ) {
                b1.setVisibility(View.GONE);
                b2.setVisibility(View.GONE);
                b3.setVisibility(View.GONE);
                b4.setVisibility(View.GONE);
                b5.setVisibility(View.GONE);
                b8.setVisibility(View.VISIBLE);
                event.setText(getString(R.string.one_machine_stat_left, selectedStat));
                mChoiceResult.onChoiceResult(true, selectedStat, false, false);
            }
        }

        if ( eventRoll >12 && eventRoll <=18) {
            if (!noMachineStats && !isUpgrading) {
                eventOne = random.nextInt(36-30) + 30;
                while (eventOne == 32 && endurance <=0 ||eventOne == 35 && wealth <=0 || lastEvent == eventOne || (oneMachineStat && eventOne == 34) ) {
                    eventOne = random.nextInt(36-30) + 30;
                }
                switch (eventOne) {
                    case 30:
                        event.setText(getString(R.string.extra_eight, descFortyEight[randomTen()], selectedStat));
                        b1.setText(R.string.extra_eight_yes);
                        b2.setText(R.string.extra_eight_no);
                        break;
                    case 31:
                        if (genieRoll == 1) {
                            event.setText(getString(R.string.extra_nine_good));
                        } else {
                            event.setText(getString(R.string.extra_nine_bad));
                        }
                        if (strength <=0) {
                            b1.setVisibility(View.GONE);
                        }
                        if (dexterity <=0) {
                            b2.setVisibility(View.GONE);
                        }
                        if (intelligence >0) {
                            b3.setVisibility(View.VISIBLE);
                        }
                        if (beauty >0) {
                            b4.setVisibility(View.VISIBLE);
                        }
                        if (loyalty >0) {
                            b5.setVisibility(View.VISIBLE);
                        }
                        b1.setText(getString(R.string.extra_twelve_yes, "Strength Infuser"));
                        b2.setText(getString(R.string.extra_twelve_yes, "Dexterity Animator"));
                        b3.setText(getString(R.string.extra_twelve_yes, "Intelligence Designer"));
                        b4.setText(getString(R.string.extra_twelve_yes, "Beauty Sculptor"));
                        b5.setText(getString(R.string.extra_twelve_yes, "Loyalty Inculcator"));
                        break;
                    case 32:
                        event.setText(getString(R.string.extra_ten));
                        b1.setText(R.string.extra_ten_yes);
                        b2.setText(R.string.extra_ten_no);
                        break;
                    case 33:
                        event.setText(getString(R.string.extra_eleven, descFiftyTwo[randomTen()], selectedStat));
                        b1.setText(R.string.extra_eleven_yes);
                        b2.setText(R.string.extra_eleven_no);
                        break;
                    case 34:
                        List<String> list = new ArrayList<>();
                        if (selectedStat.equals("Strength Infuser") || selectedStatTwo.equals("Strength Infuser")) {
                            list.add("Strength Infuser");
                        }
                        if (selectedStat.equals("Dexterity Animator") || selectedStatTwo.equals("Dexterity Animator")) {
                            list.add("Dexterity Animator");
                        }
                        if (selectedStat.equals("Intelligence Designer") || selectedStatTwo.equals("Intelligence Designer")) {
                            list.add("Intelligence Designer");
                        }
                        if (selectedStat.equals("Beauty Sculptor") || selectedStatTwo.equals("Beauty Sculptor")) {
                            list.add("Beauty Sculptor");
                        }
                        if (selectedStat.equals("Loyalty Inculcator") || selectedStatTwo.equals("Loyalty Inculcator")) {
                            list.add("Loyalty Inculcator");
                        }
                        selectedStat = list.get(0);
                        selectedStatTwo = list.get(1);

                        event.setText(getString(R.string.extra_twelve, selectedStat, selectedStatTwo));
                        b1.setText(getString(R.string.extra_twelve_yes, selectedStat));
                        b2.setText(getString(R.string.extra_twelve_no, selectedStatTwo));
                        break;
                    case 35:
                        event.setText(getString(R.string.extra_thirteen, descFiftyOne[randomTen()]));
                        b1.setText(R.string.extra_thirteen_yes);
                        b2.setText(R.string.extra_thirteen_no);
                }
                editor.putInt("lastEvent", eventOne);
                editor.apply();
                if (oneMachineStat) {
                    b1.setVisibility(View.GONE);
                    b2.setVisibility(View.GONE);
                    b3.setVisibility(View.GONE);
                    b4.setVisibility(View.GONE);
                    b5.setVisibility(View.GONE);
                    b8.setVisibility(View.VISIBLE);
                    event.setText(getString(R.string.one_machine_stat_left, selectedStat));
                    mChoiceResult.onChoiceResult(true, selectedStat, false, false);
                }
            }
        }

        // Careful w/ calling onNextDay for Stats - since that will launch another instance of ResultsFragment which will loop it, since that Fragment leads directly to (!noMachineStats && !isUpgrading).
        if (eventRoll == 19 || eventRoll == 20 || eventRoll == 21) {
            if (!noMachineStats && !isUpgrading) {
                editor.putBoolean("noRepeatOne", true);
                editor.apply();

                ArrayList<String> holder = new ArrayList<>();
                if (endurance >0) {
                    holder.add("endurance");
                }
                if (anonymity >0) {
                    holder.add("anonymity");
                }
                if (sanity >0) {
                    holder.add("sanity");
                }

                eventOne = random.nextInt(24 - 20) + 20;

                if (holder.size() >=1) {
                    while ((endurance <= 0 && eventOne == 21) || (anonymity <= 0 && eventOne == 22) || (sanity <= 0 && eventOne == 23) || eventOne == lastEvent) {
                        eventOne = random.nextInt(24 - 20) + 20;
                    }
                } else {
                    eventOne = 20;
                }
                b1.setVisibility(View.VISIBLE);
                b2.setVisibility(View.VISIBLE);
                b3.setVisibility(View.GONE);
                b4.setVisibility(View.GONE);
                b5.setVisibility(View.GONE);
                b7.setVisibility(View.GONE);
                switch (eventOne) {
                    case 20:
                        b1.setText(getString(R.string.choice_twenty_yes));
                        b2.setText(getString(R.string.choice_twenty_no));
                        event.setText(getString(R.string.choice_twenty, descEighteen[randomTen()], descNineteen[randomTen()]));
                        break;
                    case 21:
                        b1.setText(getString(R.string.choice_twentyOne_yes));
                        b2.setText(getString(R.string.choice_twentyOne_no));
                        event.setText(getString(R.string.choice_twentyOne, descTwentyThree[randomTen()], descTwentyFour[randomTen()]));
                        break;
                    case 22:
                        b1.setText(getString(R.string.choice_twentyTwo_yes));
                        b2.setText(getString(R.string.choice_twentyTwo_no));
                        event.setText(getString(R.string.choice_twentyTwo, descTwentyFive[randomTen()], descTwentySix[randomTen()], descEleven[randomTen()], descFortyThree[randomTen()]));
                        break;
                    case 23:
                        b1.setText(getString(R.string.choice_twentyThree_yes));
                        b2.setText(getString(R.string.choice_twentyThree_no));
                        String item = descTwentySeven[randomTen()];
                        event.setText(getString(R.string.choice_twentyThree, item, item, descTwentyEight[randomTen()]));
                }
                editor.putInt("lastEvent", eventOne);
                editor.apply();
                if (oneMachineStat && eventOne == 20) {
                    b1.setVisibility(View.GONE);
                    b2.setVisibility(View.GONE);
                    b3.setVisibility(View.GONE);
                    b4.setVisibility(View.GONE);
                    b5.setVisibility(View.GONE);
                    b8.setVisibility(View.VISIBLE);
                    event.setText(getString(R.string.one_machine_stat_left, selectedStat));
                    mChoiceResult.onChoiceResult(true, selectedStat, false, false);
                }
            }
        }

        if (eventRoll == 22 || eventRoll == 23) {
            //These are non-choice events.
            if (!noMachineStats && !isUpgrading) {
                editor.putBoolean("noRepeatTwo", true);
                editor.apply();

                b1.setVisibility(View.GONE);
                b2.setVisibility(View.GONE);
                b8.setVisibility(View.VISIBLE);
                int roll = random.nextInt(5 - 1) + 1;

                //Keeps rolling for events if anonymity or wealth are zero AND are selected as a potential loss.
                while ((anonymity <= 0 && roll == 3) || (wealth <= 0 && roll == 4) || roll == lastEvent || roll == 2 && (sanity <= 0 || endurance <= 0)) {
                    roll = random.nextInt(5 - 1) + 1;
                }
                switch (roll) {
                    case 1:
                        event.setText(getString(R.string.event_one, descNine[randomTen()], descTen[randomTen()], descTwelve[randomTen()], selectedStat));
                        mChoiceResult.onChoiceResult(true, selectedStat, false, false);
                        break;
                    case 2:
                        event.setText(getString(R.string.event_two, selectedStatThree));
                        mChoiceResult.onChoiceResult(true, selectedStatThree, false, false);
                        break;
                    case 3:
                        event.setText(getString(R.string.event_three));
                        mChoiceResult.onChoiceResult(true, "anonymity", false, false);
                        break;
                    case 4:
                        event.setText(getString(R.string.event_four, descThirtySeven[randomTen()], descThirtyEight[randomTen()], descThirtyNine[randomTen()]));
                        mChoiceResult.onChoiceResult(true, "wealth", false, false);
                }

                editor.putInt("lastEvent", eventOne);
                editor.apply();
                if (oneMachineStat && roll == 1) {
                    b1.setVisibility(View.GONE);
                    b2.setVisibility(View.GONE);
                    b3.setVisibility(View.GONE);
                    b4.setVisibility(View.GONE);
                    b5.setVisibility(View.GONE);
                    b8.setVisibility(View.VISIBLE);
                    event.setText(getString(R.string.one_machine_stat_left, selectedStat));
                    mChoiceResult.onChoiceResult(true, selectedStat, false, false);
                }
            }
        }

            if ( (eventRoll == 24 || eventRoll == 25) && !noMachineStats && !isUpgrading) {
                editor.putBoolean("noRepeatThree", true);
                editor.apply();

                int upStr = pref.getInt("upgradeStr", 0);
                int upDex = pref.getInt("upgradeDex", 0);
                int upTemp = pref.getInt("upgradeTemp", 0);
                int upObed = pref.getInt("upgradeObed", 0);
                int upChar = pref.getInt("upgradeChar", 0);

                List<String> checkList = new ArrayList<>();
                if (sanity >0) {
                    checkList.add("sanity");
                }
                if (endurance >0) {
                    checkList.add("endurance");
                }
                if (wealth >0) {
                    checkList.add("wealth");
                }
                if (anonymity >0) {
                    checkList.add("anonymity");
                }

                List<String> statList = new ArrayList<>();
                if (strength >0){
                    statList.add("str");
                }
                if (dexterity >0) {
                    statList.add("dex");
                }
                if (intelligence >0) {
                    statList.add("int");
                }
                if (loyalty >0) {
                    statList.add("loy");
                }
                if (beauty >0) {
                    statList.add("bea");
                }

                int demonRoll = random.nextInt(4-1) + 1;
                boolean machineVirus = random.nextBoolean();

                ArrayList<Integer> demons = new ArrayList<>();
                if (upStr + upDex + upTemp + upObed + upChar >4 && statList.size() >=1 && checkList.size() >1) {
                    if (!demonSpawned) {
                        demons.add(1);
                    }
                    if (!demonSpawnedTwo) {
                        demons.add(2);
                    }
                    if (!virusDone) {
                        demons.add(3);
                    }
                }

                int rollOne = 0;
                int usedRoll = 0;

                if (demons.size() == 0) {
                    demonRoll = 5;
                } else {
                    rollOne = random.nextInt(demons.size());
                    usedRoll = demons.get(rollOne);
                }
                if (demonRoll == 1) {
                    switch (usedRoll) {
                        case 1:
                            eventOne = 60;
                            event.setText(getString(R.string.extra_five));
                            b1.setText(getString(R.string.extra_five_yes));
                            b2.setText(getString(R.string.extra_five_no));
                            demonSpawned = true;
                            editor.putBoolean("demonSpawned", true);
                            editor.apply();
                            break;
                        case 2:
                            eventOne = 61;
                            event.setText(getString(R.string.extra_six));
                            b1.setText(getString(R.string.extra_four_yes));
                            b2.setText(getString(R.string.extra_four_no));
                            demonSpawnedTwo = true;
                            editor.putBoolean("demonSpawnedTwo", true);
                            editor.apply();
                            break;
                        case 3:
                            eventOne = 62;
                            if (machineVirus) {
                                event.setText(getString(R.string.extra_seven, "your machine"));
                                editor.putBoolean("machineVirus", true);
                            } else {
                                event.setText(getString(R.string.extra_seven, "your soul"));
                                editor.putBoolean("machineVirus", false);
                            }
                            b1.setText(getString(R.string.extra_seven_yes));
                            b2.setText(getString(R.string.extra_seven_no));
                            virusDone = true;
                            editor.putBoolean("virusDone", true);
                            editor.apply();
                    }
                } else {
                    eventOne = random.nextInt(28 - 24) + 24;
                    while ( (eventOne == 24 && endurance <=0) || (eventOne == 25 && sanity <=0) || (eventOne == 26 && (endurance <=0 || sanity <=0 ) ) || (eventOne == 27 && anonymity <=0) ) {
                        eventOne = random.nextInt(28 - 24) + 24;
                    }
                    switch (eventOne) {
                        case 24:
                            event.setText(getString(R.string.extra_one, descFiftyFive[randomTen()]));
                            b1.setText(getString(R.string.extra_one_yes));
                            b2.setText(getString(R.string.extra_one_no));
                            break;
                        case 25:
                            event.setText(getString(R.string.extra_two, descFortySeven[randomTen()]));
                            b1.setText(getString(R.string.extra_two_yes));
                            b2.setText(getString(R.string.extra_two_no));
                            break;
                        case 26:
                            event.setText(getString(R.string.extra_three));
                            b1.setText(getString(R.string.extra_three_yes));
                            b2.setText(getString(R.string.extra_three_no));
                            break;
                        case 27:
                            event.setText(getString(R.string.extra_four));
                            b1.setText(getString(R.string.extra_eighteen_yes));
                            b2.setText(getString(R.string.extra_eighteen_no));
                    }
                }
                if (oneMachineStat) {
                    b1.setVisibility(View.GONE);
                    b2.setVisibility(View.GONE);
                    b3.setVisibility(View.GONE);
                    b4.setVisibility(View.GONE);
                    b5.setVisibility(View.GONE);
                    b8.setVisibility(View.VISIBLE);
                    event.setText(getString(R.string.one_machine_stat_left, selectedStat));
                    mChoiceResult.onChoiceResult(true, selectedStat, false, false);
                }
            }

        //Entropy event. Overridden by upgrades and critical stat lows. SelectedStats should not roll null since the (while) method will always run if this one is not overridden.
        if (eventOne == 200 && !noMachineStats) {
            int roll = random.nextInt(5-1) + 1;
            switch (roll) {
                case 1:
                    event.setText(getString(R.string.entropy, selectedStat));
                    mChoiceResult.onChoiceResult(true, selectedStat, false, false);
                    break;
                case 2:
                    event.setText(getString(R.string.entropy, selectedStatThree));
                    mChoiceResult.onChoiceResult(true, selectedStatThree, false, false);
                    break;
                case 3:
                    event.setText(getString(R.string.wealthy_entropy));
                    mChoiceResult.onChoiceResult(true, "wealth", false, false);
                    break;
                case 4:
                    event.setText(getString(R.string.anon_entropy));
                    mChoiceResult.onChoiceResult(true, "anonymity", false, false);
            }
            b1.setVisibility(View.GONE);
            b2.setVisibility(View.GONE);
            b8.setVisibility(View.VISIBLE);
            editor.putBoolean("", true);
            editor.apply();
        }

        //Roll for whether user choice is successful. For some events, they can avoid damage. NOT related to Callbacks.
        final boolean success = random.nextBoolean();
        final int rollHard = random.nextInt(3-1) + 1;

        //Switch/Case references the random Array roll input received from our onNextTurn() Callback, so they should always match the arguments received.
        //Only has eventOne cases that contain choices.
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultOne.setVisibility(View.VISIBLE);
                Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
                resultOne.startAnimation(fadeIn);
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });

                b1.setVisibility(View.GONE);
                b2.setVisibility(View.GONE);
                b7.setVisibility(View.VISIBLE);

                switch (eventOne) {
                    case 1:
                        mChoiceResult.onChoiceResult(true, selectedStatTwo, false, false);
                        resultOne.setText(getString(R.string.result_one_yes, selectedStatTwo));
                        break;
                    case 2:
                        mChoiceResult.onChoiceResult(true, selectedStat, false, false);
                        resultOne.setText(getString(R.string.result_two_yes, selectedStat));
                        break;
                    case 3:
                        mChoiceResult.onChoiceResult(true, "anonymity", false, false);
                        resultOne.setText(getString(R.string.result_three_yes));
                        break;
                    case 4:
                        if (success) {
                            resultOne.setText(getString(R.string.result_four_success));
                            mChoiceResult.onChoiceResult(false, selectedStat, false, false);
                        } else {
                            resultOne.setText(getString(R.string.result_four_failure, selectedStat));
                            mChoiceResult.onChoiceResult(true, selectedStat, false, false);
                        }
                        break;
                    case 5:
                        //5-choice scanner damage event, ditto in b2-b5 below.
                        selectedStat = "Strength Infuser";
                        mChoiceResult.onChoiceResult(true, selectedStat, false, false);
                        resultOne.setText(getString(R.string.result_five));
                        //Removes extra choice buttons, since they are unnecessary for most events.
                        b1.setVisibility(View.GONE);
                        b2.setVisibility(View.GONE);
                        b3.setVisibility(View.GONE);
                        b4.setVisibility(View.GONE);
                        b5.setVisibility(View.GONE);
                        break;
                    case 6:
                        if (success) {
                            resultOne.setText(getString(R.string.result_six_yes_success));
                            mChoiceResult.onChoiceResult(false, selectedStat, false, false);
                        } else {
                            resultOne.setText(getString(R.string.result_six_yes_failure, selectedStatTwo));
                            mChoiceResult.onChoiceResult(true, selectedStatTwo, false, true);
                        }
                        break;
                    case 7:
                        if (success) {
                            resultOne.setText(getString(R.string.result_seven_yes_success));
                            mChoiceResult.onChoiceResult(false, selectedStat, false, false);
                        } else {
                            resultOne.setText(getString(R.string.result_seven_yes_failure));
                            mChoiceResult.onChoiceResult(true, selectedStat, false, true);
                        }
                        break;
                    case 8:
                        if (success) {
                            resultOne.setText(getString(R.string.result_eight_yes_success));
                            mChoiceResult.onChoiceResult(false, selectedStat, false, false);
                        } else {
                            resultOne.setText(getString(R.string.result_eight_yes_failure, selectedStat));
                            mChoiceResult.onChoiceResult(true, selectedStat, false, true);
                        }
                        break;
                    case 10:
                        if (success) {
                            resultOne.setText(getString(R.string.result_ten_yes_success, "sanity"));
                            mChoiceResult.onChoiceResult(true, "sanity", true, false);
                        } else {
                            resultOne.setText(getString(R.string.result_ten_yes_failure));
                            mChoiceResult.onChoiceResult(true, "sanity", false, true);
                        }
                        break;
                    case 11:
                        if (success) {
                            resultOne.setText(getString(R.string.result_eleven_yes_success));
                            mChoiceResult.onChoiceResult(false, "wealth", true, false);
                        } else {
                            resultOne.setText(getString(R.string.result_eleven_yes_failure));
                            mChoiceResult.onChoiceResult(true, "wealth", false, true);
                        } break;
                    case 12:
                        if (success) {
                            resultOne.setText(getString(R.string.result_twelve_yes_success));
                            mChoiceResult.onChoiceResult(false, "anonymity", false, false);
                        } else {
                            resultOne.setText(getString(R.string.result_twelve_yes_failure));
                            mChoiceResult.onChoiceResult(true, "endurance", false, true);
                        }
                        break;
                    case 13:
                        if (success) {
                            resultOne.setText(getString(R.string.result_thirteen_yes_success));
                            mChoiceResult.onChoiceResult(false, selectedStat, false, false);
                        } else {
                            resultOne.setText(getString(R.string.result_thirteen_yes_failure, selectedStat));
                            mChoiceResult.onChoiceResult(true, selectedStat, false, false);
                        }
                        break;
                    case 14:
                        if (success) {
                            resultOne.setText(getString(R.string.result_fourteen_yes_success, descThirtySix[randomTen()]));
                            mChoiceResult.onChoiceResult(false, "sanity", false, false);
                        } else {
                            resultOne.setText(getString(R.string.result_fourteen_yes_failure));
                            mChoiceResult.onChoiceResult(true, "sanity", false, false);
                        }
                        break;
                    case 15:
                        if (success) {
                            resultOne.setText(getString(R.string.result_fifteen_yes_success, descTwenty[randomTen()]));
                            mChoiceResult.onChoiceResult(false, "endurance", false, false);
                        } else {
                            resultOne.setText(getString(R.string.result_fifteen_yes_failure));
                            mChoiceResult.onChoiceResult(true, "endurance", false, true);
                        }
                        break;
                    case 16:
                        resultOne.setText(getString(R.string.result_sixteen_yes));
                        mChoiceResult.onChoiceResult(true, "wealth", false, false);
                        break;
                    case 17:
                        if (success) {
                            resultOne.setText(getString(R.string.result_seventeen_yes_success));
                            mChoiceResult.onChoiceResult(false, "wealth", false, false);
                        } else {
                            resultOne.setText(getString(R.string.result_seventeen_yes));
                            mChoiceResult.onChoiceResult(true, "wealth", false, true);
                        }
                        break;
                    case 18:
                        if (success) {
                            resultOne.setText(getString(R.string.result_eighteen_yes_success));
                            mChoiceResult.onChoiceResult(false, "wealth", true, false);
                        } else {
                            if (rollHard == 1) {
                                resultOne.setText(getString(R.string.result_eighteen_yes_failure));
                                mChoiceResult.onChoiceResult(true, "wealth", false, false);
                            } else {
                                resultOne.setText(getString(R.string.result_eighteen_yes_failure_2, descForty[randomTen()]));
                                mChoiceResult.onChoiceResult(true, "wealth", false, true);
                            }
                        }
                        break;
                    case 20:
                        if (success) {
                            resultOne.setText(getString(R.string.result_twenty_yes_success, selectedStat));
                            mChoiceResult.onChoiceResult(true, selectedStat, true, false);
                        } else {
                            resultOne.setText(getString(R.string.result_twenty_yes_failure, selectedStat));
                            mChoiceResult.onChoiceResult(true, selectedStat, false, true);
                        }
                        break;
                    case 21:
                        if (success) {
                            resultOne.setText(getString(R.string.result_twentyOne_yes_success));
                            mChoiceResult.onChoiceResult(true, "endurance", true, false);
                        } else {
                            resultOne.setText(getString(R.string.result_twentyOne_yes_failure));
                            mChoiceResult.onChoiceResult(true, "endurance", false, true);
                        }
                        break;
                    case 22:
                        if (success) {
                            resultOne.setText(getString(R.string.result_twentyTwo_yes_success));
                            mChoiceResult.onChoiceResult(true, "anonymity", true, false);
                        } else {
                            resultOne.setText(getString(R.string.result_twentyTwo_yes_failure));
                            mChoiceResult.onChoiceResult(true, "anonymity", false, true);
                        }
                        break;
                    case 23:
                        if (success) {
                            resultOne.setText(getString(R.string.result_twentyThree_yes_success));
                            mChoiceResult.onChoiceResult(true, "sanity", true, false);
                        } else {
                            resultOne.setText(getString(R.string.result_twentyThree_yes_failure));
                            mChoiceResult.onChoiceResult(true, "sanity", false, true);
                        }
                        break;
                    case 24:
                        if (success) {
                            resultOne.setText(getString(R.string.result_extraOne_3));
                            mChoiceResult.onChoiceResult(true, "wealth", true, true);
                        } else {
                            resultOne.setText(getString(R.string.result_extraOne_1));
                            mChoiceResult.onChoiceResult(true, "endurance", false, false);
                        }
                        break;
                    case 25:
                        if (success) {
                            resultOne.setText(getString(R.string.result_extraTwo_3));
                            mChoiceResult.onChoiceResult(true, "endurance", true, false);
                        } else {
                            resultOne.setText(getString(R.string.result_extraTwo_1));
                            mChoiceResult.onChoiceResult(true, "sanity", false, true);
                    }
                        break;
                    case 26:
                        if (success) {
                            resultOne.setText(getString(R.string.result_extraThree_3));
                            mChoiceResult.onChoiceResult(true, "sanity", true, false);
                        } else {
                            resultOne.setText(getString(R.string.result_extraThree_1));
                            mChoiceResult.onChoiceResult(true, "endurance", false, true);
                        }
                        break;
                    case 27:
                        if (success) {
                            resultOne.setText(getString(R.string.result_extraFour_3));
                            mChoiceResult.onChoiceResult(true, "anonymity", true, false);
                        } else {
                            resultOne.setText(getString(R.string.result_extraFour_1));
                            mChoiceResult.onChoiceResult(true, "anonymity", false, true);
                        }
                        break;
                    case 30:
                        if (success) {
                            resultOne.setText(getString(R.string.result_extra_eight_success, descFortyNine[randomTen()], selectedStat));
                            mChoiceResult.onChoiceResult(false, selectedStat, false, false);
                        } else {
                            resultOne.setText(getString(R.string.result_extra_eight_failure, descFifty[randomTen()], selectedStat));
                            mChoiceResult.onChoiceResult(true, selectedStat, false, true);
                        }
                        break;
                    case 31:
                        if (genieRoll == 1) {
                            if (success) {
                                resultOne.setText(getString(R.string.result_extra_good_nine, "Strength Infuser"));
                                mChoiceResult.onChoiceResult(true, "Strength Infuser", true, true);
                        }  else {
                                resultOne.setText(getString(R.string.result_extra_good_nine_failure, selectedStat));
                            }
                    } else {
                            resultOne.setText(getString(R.string.result_extra_bad_nine, "Strength Infuser"));
                            mChoiceResult.onChoiceResult(true, "Strength Infuser", false, false);
                        }
                        b3.setVisibility(View.GONE);
                        b4.setVisibility(View.GONE);
                        b5.setVisibility(View.GONE);
                        break;
                    case 32:
                        boolean firstWins = random.nextBoolean();
                        String winner;
                        if (firstWins) {
                            winner = selectedStat;
                        } else {
                            winner = selectedStatTwo;
                        }
                        resultOne.setText(getString(R.string.result_extra_ten_yes, selectedStat, selectedStatTwo, winner));
                        mChoiceResult.onChoiceResult(true, winner, false, false);
                        break;
                    case 33:
                        resultOne.setText(getString(R.string.result_extra_eleven_yes, selectedStat, selectedStatTwo));
                        mChoiceResult.onChoiceResult(true, selectedStatTwo, false, true);
                        break;
                    case 34:
                        resultOne.setText(getString(R.string.result_extra_twelve_yes, selectedStat));
                        mChoiceResult.onChoiceResult(true, selectedStat, false, true);
                        break;
                    case 35:
                        if (success) {
                            resultOne.setText(getString(R.string.result_extra_thirteen_yes_success));
                            mChoiceResult.onChoiceResult(true, "wealth", true, false);
                        } else {
                            resultOne.setText(getString(R.string.result_extra_thirteen_yes_failure));
                            mChoiceResult.onChoiceResult(true, "wealth", false, true);
                        }
                        break;
                    case 40:
                        resultOne.setText(getString(R.string.no_machine_loss_one_result_san));
                        mChoiceResult.onChoiceResult(true, "sanity", false, false); break;
                    case 41:
                        resultOne.setText(getString(R.string.no_machine_loss_two_result_com));
                        mChoiceResult.onChoiceResult(true, "wealth", false, false);
                        break;
                        //Used for upgrades. eventOne set to 0 in eventRoll if/else above.
                    case 50:
                        selectedStat = "Strength Infuser";
                        resultOne.setText(getString(R.string.upgrade_complete, descTwenty[randomTen()], descTwentyOne[randomTen()], selectedStat));
                        editor.putInt("upgradeStr", upgradeStr + 1);
                        editor.apply();
                        mChoiceResult.onChoiceResult(false, selectedStat, false, false);
                        b7.setVisibility(View.VISIBLE);
                        b3.setVisibility(View.GONE);
                        b4.setVisibility(View.GONE);
                        b5.setVisibility(View.GONE);
                        break;
                    case 60:
                        selectedStat = firstStatRoll.get(random.nextInt(firstStatRoll.size()));
                        resultOne.setText(getString(R.string.result_extra_five_yes, selectedStat));
                        mChoiceResult.onChoiceResult(true, "demon", false, false);
                        editor.putBoolean("dealStruck", true);
                        editor.putString("dealStat", selectedStat);
                        editor.apply();
                        break;
                    case 61:
                        //Success is just to roll for what demon will take.
                        editor.putBoolean("upgradeLoss", true);
                        editor.apply();
                        ArrayList<String> holder  = new ArrayList<>();
                        holder.add("sanity");
                        holder.add("endurance");
                        holder.add("wealth");
                        holder.add("anonymity");
                        Collections.shuffle(holder);

                        String result = holder.get(0);
                        if (result.equals("sanity")) {
                            resultOne.setText(getString(R.string.end_two, getString(R.string.result_extra_six_yes), getString(R.string.result_extra_san)));
                            if (!success) {
                                mChoiceResult.onChoiceResult(false, "demonSan", false, false);
                            } else {
                                mChoiceResult.onChoiceResult(true, "demonSan", false, false);
                            }
                        }
                        if (result.equals("endurance")){
                            resultOne.setText(getString(R.string.end_two, getString(R.string.result_extra_six_yes), getString(R.string.result_extra_end)));
                            if (!success) {
                                mChoiceResult.onChoiceResult(false, "demonEnd", false, false);
                            } else {
                                mChoiceResult.onChoiceResult(true, "demonEnd", false, false);
                            }
                        }
                        if (result.equals("wealth")) {
                            resultOne.setText(getString(R.string.end_two, getString(R.string.result_extra_six_yes), getString(R.string.result_extra_wealth)));
                            if (!success) {

                                mChoiceResult.onChoiceResult(false, "demonWealth", false, false);
                            } else {

                                mChoiceResult.onChoiceResult(true, "demonWealth", false, false);
                            }
                        }
                        if (result.equals("anonymity")) {
                            resultOne.setText(getString(R.string.end_two, getString(R.string.result_extra_six_yes), getString(R.string.result_extra_anon)));
                            if (!success) {
                                mChoiceResult.onChoiceResult(false, "demonAnon", false, false);
                            } else {
                                mChoiceResult.onChoiceResult(true, "demonAnon", false, false);
                            }
                        }

                        break;
                    case 62:
                        resultOne.setText(R.string.result_extra_seven_yes);
                        mChoiceResult.onChoiceResult(true, "demon", false, false);
                        break;
                    case 101:
                        editor.putInt("endDaysLeft", 1);
                        editor.apply();
                        resultOne.setText(R.string.zero_endOneResult);
                        b3.setVisibility(View.GONE);
                        break;
                    case 103:
                        int roll = random.nextInt(5-1) + 1;
                        if (wealth <10) {
                            resultOne.setText(R.string.anon_bribe_broke);
                            editor.putBoolean("stopRefreshAnon", true);
                            editor.apply();
                            tookBribe = false;
                        } else {
                            if (roll == 1) {
                                resultOne.setText(R.string.anon_bribe_failure);
                                editor.putBoolean("stopRefreshAnon", true);
                                editor.apply();
                                tookBribe = false;
                            } else {
                                resultOne.setText(R.string.anon_bribe_success);
                                mChoiceResult.onChoiceResult(true, "wealth", false, true);
                                tookBribe = true;
                            }
                        }
                        break;
                    case 104:
                        resultOne.setText(R.string.wealth_zero_result);
                        mChoiceResult.onChoiceResult(true, "strWealth", false, false);
                        b3.setVisibility(View.GONE);
                        b4.setVisibility(View.GONE);
                        b5.setVisibility(View.GONE);
                        break;
                    case 150:
                        postUpgradeList.add("str");
                        b1.setVisibility(View.VISIBLE);
                        b2.setVisibility(View.VISIBLE);
                        b3.setVisibility(View.VISIBLE);
                        b4.setVisibility(View.VISIBLE);
                        b5.setVisibility(View.VISIBLE);
                        b7.setVisibility(View.GONE);
                        b8.setVisibility(View.GONE);
                        if (!post1) {
                            b1.getBackground().setColorFilter(getResources().getColor(R.color.Grey), PorterDuff.Mode.MULTIPLY);
                            post1 = true;
                            return;
                        }
                        if (post1) {
                            b1.getBackground().setColorFilter(getResources().getColor(R.color.colorBlack), PorterDuff.Mode.MULTIPLY);
                            post1 = false;
                        }
                }

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StringFormatMatches")
            @Override
            public void onClick(View v) {
                Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
                resultOne.startAnimation(fadeIn);
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
                b1.setVisibility(View.GONE);
                b2.setVisibility(View.GONE);
                b7.setVisibility(View.VISIBLE);
                switch (eventOne) {
                    case 1:
                        resultOne.setText(getString(R.string.result_five));
                        //Usual Callback pass, modified only for certain events (if/else above).
                        mChoiceResult.onChoiceResult(true, selectedStat, false, false);
                        break;
                    case 2:
                        resultOne.setText(getString(R.string.result_two_yes, selectedStatTwo));
                        mChoiceResult.onChoiceResult(true, selectedStatTwo, false, false);
                        break;
                    case 3:
                        resultOne.setText(getString(R.string.result_three_no, selectedStat, descFiftyThree[randomTen()], descFiftyFour[randomTen()]));
                        //Usual Callback pass, modified only for certain events (if/else above).
                        mChoiceResult.onChoiceResult(true, selectedStat, false, false);
                        break;
                    case 4:
                        resultOne.setText(getString(R.string.result_four_no));
                        mChoiceResult.onChoiceResult(true, "endurance", false, false);
                        break;
                    case 5:
                        resultOne.setText(getString(R.string.result_five));
                        selectedStat = "Dexterity Animator";
                        b1.setVisibility(View.GONE);
                        b2.setVisibility(View.GONE);
                        b7.setVisibility(View.VISIBLE);
                        b3.setVisibility(View.GONE);
                        b4.setVisibility(View.GONE);
                        b5.setVisibility(View.GONE);
                        mChoiceResult.onChoiceResult(true, selectedStat, false, false);
                        break;
                    case 6:
                        resultOne.setText(getString(R.string.result_six_no));
                        mChoiceResult.onChoiceResult(true, selectedStat, false, false);
                        break;
                    case 7:
                        resultOne.setText(getString(R.string.result_seven_no));
                        mChoiceResult.onChoiceResult(true, selectedStat, false, false);
                        break;
                    case 8:
                        resultOne.setText(getString(R.string.result_eight_no));
                        mChoiceResult.onChoiceResult(true, "sanity", false, false);
                        break;
                    case 10:
                        resultOne.setText(getString(R.string.result_ten_no));
                        mChoiceResult.onChoiceResult(false, "sanity", false, false);
                        break;
                    case 11:
                        resultOne.setText(getString(R.string.result_eleven_no));
                        mChoiceResult.onChoiceResult(true, "wealth", false, false);
                        break;
                    case 12:
                        if (success) {
                            resultOne.setText(getString(R.string.end_two_par, getString(R.string.result_twelve_no), getString(R.string.result_twelve_no_good)));
                            mChoiceResult.onChoiceResult(false, "anonymity", false, false);
                        } else {
                            resultOne.setText(getString(R.string.end_two_par, getString(R.string.result_twelve_no), getString(R.string.result_twelve_no_bad)));
                            mChoiceResult.onChoiceResult(true, "anonymity", false, false);
                        }
                        break;
                    case 13:
                        resultOne.setText(getString(R.string.result_thirteen_no));
                        mChoiceResult.onChoiceResult(true, selectedStat, false, false);
                        break;
                    case 14:
                        resultOne.setText(getString(R.string.result_fourteen_no, descThirtySix[randomTen()]));
                        mChoiceResult.onChoiceResult(true, "wealth", false, false);
                        break;
                    case 15:
                        resultOne.setText(getString(R.string.result_fifteen_no));
                        mChoiceResult.onChoiceResult(true, "sanity", false, false);
                        break;
                    case 16:
                        resultOne.setText(getString(R.string.result_sixteen_no));
                        mChoiceResult.onChoiceResult(true, "anonymity", false, false);
                        break;
                    case 17:
                        resultOne.setText(getString(R.string.result_seventeen_no));
                        mChoiceResult.onChoiceResult(true, "endurance", false, false);
                        break;
                    case 18:
                        resultOne.setText(getString(R.string.result_eighteen_no));
                        //Usual Callback pass, modified only for certain events (if/else above).
                        mChoiceResult.onChoiceResult(true, selectedStat, false, false);
                        break;
                    case 20:
                        resultOne.setText(getString(R.string.result_twenty_no));
                        break;
                    case 21:
                        resultOne.setText(getString(R.string.result_twentyOne_no)); break;
                    case 22:
                        resultOne.setText(getString(R.string.result_twentyTwo_no)); break;
                    case 23:
                        resultOne.setText(getString(R.string.result_twentyThree_no)); break;
                    case 24: case 25: case 26: case 27:
                        resultOne.setText(getString(R.string.result_extra_no)); break;
                    case 30:
                        resultOne.setText(getString(R.string.result_extra_eight_no, selectedStatTwo));
                        mChoiceResult.onChoiceResult(true, selectedStatTwo, false, false);
                        break;
                    case 31:
                        if (genieRoll == 1) {
                            if (success) {
                                resultOne.setText(getString(R.string.result_extra_good_nine, "Dexterity Animator"));
                                mChoiceResult.onChoiceResult(true, "Dexterity Animator", true, true);
                            }  else {
                                resultOne.setText(getString(R.string.result_extra_good_nine_failure, selectedStat));
                            }
                        } else {
                            resultOne.setText(getString(R.string.result_extra_bad_nine, "Dexterity Animator"));
                            mChoiceResult.onChoiceResult(true, "Dexterity Animator", false, false);
                        }
                        b3.setVisibility(View.GONE);
                        b4.setVisibility(View.GONE);
                        b5.setVisibility(View.GONE);
                        break;
                    case 32:
                        resultOne.setText(getString(R.string.result_extra_ten_no));
                        mChoiceResult.onChoiceResult(true, "endurance", false, true);
                        break;
                    case 33:
                        resultOne.setText(getString(R.string.result_extra_eleven_no, selectedStat));
                        mChoiceResult.onChoiceResult(true, selectedStat, false, false);
                        break;
                    case 34:
                        resultOne.setText(getString(R.string.result_extra_twelve_no, selectedStatTwo));
                        mChoiceResult.onChoiceResult(true, selectedStatTwo, false, false);
                        break;
                    case 35:
                        resultOne.setText(getString(R.string.result_extra_thirteen_no));
                        break;
                    case 40:
                        resultOne.setText(getString(R.string.no_machine_loss_one_result_end));
                        mChoiceResult.onChoiceResult(true, "endurance",  false, false); break;
                    case 41:
                        resultOne.setText(getString(R.string.no_machine_loss_one_result_anon));
                        mChoiceResult.onChoiceResult(true, "anonymity", false, false); break;
                    case 50:
                        selectedStat = "Dexterity Animator";
                        resultOne.setText(getString(R.string.upgrade_complete, descTwenty[randomTen()], descTwentyOne[randomTen()], selectedStat));
                        editor.putInt("upgradeDex", upgradeDex + 1);
                        editor.apply();
                        mChoiceResult.onChoiceResult(false, selectedStat, false, false);
                        b1.setVisibility(View.GONE);
                        b2.setVisibility(View.GONE);
                        b7.setVisibility(View.VISIBLE);
                        b3.setVisibility(View.GONE);
                        b4.setVisibility(View.GONE);
                        b5.setVisibility(View.GONE);
                        break;
                    case 60:
                        resultOne.setText(getString(R.string.result_extra_five_no));
                        break;
                    case 61:
                        resultOne.setText(R.string.result_extra_six_no);
                        break;
                    case 62:
                        resultOne.setText(R.string.result_extra_seven_no);
                        boolean machineVirus = pref.getBoolean("machineVirus", false);
                        if (machineVirus) {
                            editor.putBoolean("machineEbb", true);
                        } else {
                            editor.putBoolean("manEbb", true);
                        }
                        editor.apply();
                        mChoiceResult.onChoiceResult(false, selectedStat, false, false);
                        break;
                    case 101:
                        editor.putInt("endDaysLeft", 2);
                        editor.apply();
                        resultOne.setText(R.string.zero_endTwoResult);
                        b3.setVisibility(View.GONE);
                        break;
                    case 103:
                        resultOne.setText(R.string.amon_zero_noBribe);
                        break;
                    case 104:
                        resultOne.setText(R.string.wealth_zero_result);
                        mChoiceResult.onChoiceResult(true, "dexWealth", false, false);
                        b3.setVisibility(View.GONE);
                        b4.setVisibility(View.GONE);
                        b5.setVisibility(View.GONE);
                        break;
                    case 150:
                        postUpgradeList.add("dex");
                        b1.setVisibility(View.VISIBLE);
                        b2.setVisibility(View.VISIBLE);
                        b3.setVisibility(View.VISIBLE);
                        b4.setVisibility(View.VISIBLE);
                        b5.setVisibility(View.VISIBLE);
                        b7.setVisibility(View.GONE);
                        b8.setVisibility(View.GONE);
                        if (!post2) {
                            b2.getBackground().setColorFilter(getResources().getColor(R.color.Grey), PorterDuff.Mode.MULTIPLY);
                            post2 = true;
                            return;
                        }
                        if (post2) {
                            b2.getBackground().setColorFilter(getResources().getColor(R.color.colorBlack), PorterDuff.Mode.MULTIPLY);
                            post2 = false;
                        }
                }
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
                resultOne.startAnimation(fadeIn);
                selectedStat = "Intelligence Designer";
                b1.setVisibility(View.GONE);
                b2.setVisibility(View.GONE);
                b7.setVisibility(View.VISIBLE);
                b3.setVisibility(View.GONE);
                b4.setVisibility(View.GONE);
                b5.setVisibility(View.GONE);
                b8.setVisibility(View.GONE);
                //Button used only for 5-choice stat loss and 5-choice stat upgrade. eventOne as 50 is upgrade. Ditto b4/b5.
                if (eventOne == 150) {
                    postUpgradeList.add("int");
                    b1.setVisibility(View.VISIBLE);
                    b2.setVisibility(View.VISIBLE);
                    b3.setVisibility(View.VISIBLE);
                    b4.setVisibility(View.VISIBLE);
                    b5.setVisibility(View.VISIBLE);
                    b7.setVisibility(View.GONE);
                    b8.setVisibility(View.GONE);
                    if (!post3) {
                        b3.getBackground().setColorFilter(getResources().getColor(R.color.Grey), PorterDuff.Mode.MULTIPLY);
                        post3 = true;
                        return;
                    }
                    if (post3) {
                        b3.getBackground().setColorFilter(getResources().getColor(R.color.colorBlack), PorterDuff.Mode.MULTIPLY);
                        post3 = false;
                    }
                } else if (eventOne == 50) {
                    resultOne.setText(getString(R.string.upgrade_complete, descTwenty[randomTen()], descTwentyOne[randomTen()], selectedStat));
                    editor.putInt("upgradeTemp", upgradeTemp + 1);
                    editor.apply();
                    mChoiceResult.onChoiceResult(false, selectedStat, false, false);
                } else if (eventOne == 101) {
                    editor.putInt("endDaysLeft", 3);
                    editor.apply();
                    resultOne.setText(R.string.zero_endTThreeResult);
                } else if (eventOne == 31) {
                    if (genieRoll == 1) {
                        if (success) {
                            resultOne.setText(getString(R.string.result_extra_good_nine, "Intelligence Designer"));
                            mChoiceResult.onChoiceResult(true, "Intelligence Designer", true, true);
                        }  else {
                            resultOne.setText(getString(R.string.result_extra_good_nine_failure, selectedStat));
                        }
                    } else {
                        resultOne.setText(getString(R.string.result_extra_bad_nine, "Intelligence Designer"));
                        mChoiceResult.onChoiceResult(true, "Intelligence Designer", false, false);
                    }
                } else if (eventOne == 104){
                    resultOne.setText(R.string.wealth_zero_result);
                    mChoiceResult.onChoiceResult(true, "intWealth", false, false);
                } else {
                    resultOne.setText(getString(R.string.result_five));
                    mChoiceResult.onChoiceResult(true, selectedStat, false, false);
                    }
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
                resultOne.startAnimation(fadeIn);
                selectedStat = "Beauty Sculptor";
                b1.setVisibility(View.GONE);
                b2.setVisibility(View.GONE);
                b7.setVisibility(View.VISIBLE);
                b3.setVisibility(View.GONE);
                b4.setVisibility(View.GONE);
                b5.setVisibility(View.GONE);
                b8.setVisibility(View.GONE);
                if (eventOne == 150) {
                    postUpgradeList.add("bea");
                    b1.setVisibility(View.VISIBLE);
                    b2.setVisibility(View.VISIBLE);
                    b3.setVisibility(View.VISIBLE);
                    b4.setVisibility(View.VISIBLE);
                    b5.setVisibility(View.VISIBLE);
                    b7.setVisibility(View.GONE);
                    b8.setVisibility(View.GONE);
                    if (!post4) {
                        b4.getBackground().setColorFilter(getResources().getColor(R.color.Grey), PorterDuff.Mode.MULTIPLY);
                        post4 = true;
                        return;
                    }
                    if (post4) {
                        b4.getBackground().setColorFilter(getResources().getColor(R.color.colorBlack), PorterDuff.Mode.MULTIPLY);
                        post4 = false;
                    }
                } else if (eventOne == 31) {
                    if (genieRoll == 1) {
                        if (success) {
                            resultOne.setText(getString(R.string.result_extra_good_nine, "Beauty Sculptor"));
                            mChoiceResult.onChoiceResult(true, "Beauty Sculptor Inculcator", true, true);
                        }  else {
                            resultOne.setText(getString(R.string.result_extra_good_nine_failure, selectedStat));
                        }
                    } else {
                        resultOne.setText(getString(R.string.result_extra_bad_nine, "Beauty Sculptor"));
                        mChoiceResult.onChoiceResult(true, "Beauty Sculptor", false, false);
                    }
                } else if (eventOne == 104) {
                    resultOne.setText(R.string.wealth_zero_result);
                    mChoiceResult.onChoiceResult(true, "beaWealth", false, false);
                } else if (eventOne != 50 ) {
                    resultOne.setText(getString(R.string.result_five));
                    mChoiceResult.onChoiceResult(true, selectedStat, false, false);
                } else {
                    selectedStat = "Beauty Sculptor";
                    resultOne.setText(getString(R.string.upgrade_complete, descTwenty[randomTen()], descTwentyOne[randomTen()], selectedStat));
                    editor.putInt("upgradeChar", upgradeChar + 1);
                    editor.apply();
                    mChoiceResult.onChoiceResult(false, selectedStat, false, false);
                }
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
                resultOne.startAnimation(fadeIn);
                selectedStat = "Loyalty Inculcator";
                b1.setVisibility(View.GONE);
                b2.setVisibility(View.GONE);
                b7.setVisibility(View.VISIBLE);
                b3.setVisibility(View.GONE);
                b4.setVisibility(View.GONE);
                b5.setVisibility(View.GONE);
                b8.setVisibility(View.GONE);
                if (eventOne == 150) {
                    postUpgradeList.add("loy");
                    b1.setVisibility(View.VISIBLE);
                    b2.setVisibility(View.VISIBLE);
                    b3.setVisibility(View.VISIBLE);
                    b4.setVisibility(View.VISIBLE);
                    b5.setVisibility(View.VISIBLE);
                    b7.setVisibility(View.GONE);
                    b8.setVisibility(View.GONE);
                    if (!post5) {
                        b5.getBackground().setColorFilter(getResources().getColor(R.color.Grey), PorterDuff.Mode.MULTIPLY);
                        post5 = true;
                        return;
                    }
                    if (post5) {
                        b5.getBackground().setColorFilter(getResources().getColor(R.color.colorBlack), PorterDuff.Mode.MULTIPLY);
                        post5 = false;
                    }
                } else if (eventOne == 31) {
                    if (genieRoll == 1) {
                        if (success) {
                            resultOne.setText(getString(R.string.result_extra_good_nine, "Loyalty Inculcator"));
                            mChoiceResult.onChoiceResult(true, "Loyalty Inculcator", true, true);
                        }  else {
                            resultOne.setText(getString(R.string.result_extra_good_nine_failure, selectedStat));
                        }
                    } else {
                        resultOne.setText(getString(R.string.result_extra_bad_nine, "Loyalty Inculcator"));
                        mChoiceResult.onChoiceResult(true, "Loyalty Inculcator", false, false);
                    }
                } else if (eventOne == 104) {
                    resultOne.setText(R.string.wealth_zero_result);
                    mChoiceResult.onChoiceResult(true, "loyWealth", false, false);
                } else if (eventOne != 50) {
                    resultOne.setText(getString(R.string.result_five));
                    mChoiceResult.onChoiceResult(true, selectedStat, false, false);
                } else {
                    selectedStat = "Loyalty Inculcator";
                    resultOne.setText(getString(R.string.upgrade_complete, descTwenty[randomTen()], descTwentyOne[randomTen()], selectedStat));
                    editor.putInt("upgradeObed", upgradeObed + 1);
                    editor.apply();
                    mChoiceResult.onChoiceResult(false, selectedStat, false, false);
                }
            }
        });


        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean anonZero = pref.getBoolean("anonZero", false);
                sanity = statPref.getInt("sanity", 100);
                endurance = statPref.getInt("endurance", 100);
                wealth = statPref.getInt("wealth", 100);
                anonymity = statPref.getInt("anonymity", 100);

                stopRefreshSan = pref.getBoolean("stopRefreshSan", false);
                stopRefreshEnd = pref.getBoolean("stopRefreshEnd", false);
                stopRefreshAnon = pref.getBoolean("stopRefreshAnon", false);
                stopRefreshMachine = pref.getBoolean("stopRefreshMachine", false);
                stopRefreshMan = pref.getBoolean("stopRefreshMan", false);
                final boolean anonZeroIncin = pref.getBoolean("anonZeroIncin", false);
                final boolean endZeroIncin = pref.getBoolean("endZeroIncin", false);
                //These WORK. They are meant to trigger on 0 AFTER the StoryFragment has been inflated. Since testing w/ 0 on startup has StoryFragment inflated AFTER, we see an "extra" return instance.
                if (sanity <=0 && endurance <=0 && !stopRefreshMan && !endZeroIncin) {
                    editor.putBoolean("stopRefreshMan", true);
                    editor.apply();
                    mRefreshStoryCallback.onRefreshStory();
                    return;
                }
                if (noMachineStats && !stopRefreshMachine) {
                    editor.putBoolean("stopRefreshMachine", true);
                    editor.apply();
                    mRefreshStoryCallback.onRefreshStory();
                    return;
                }

                if (sanity <=0 && endurance >0 && !stopRefreshSan) {
                    editor.putBoolean("stopRefreshSan", true);
                    editor.apply();
                    mRefreshStoryCallback.onRefreshStory();
                    return;
                }

                if (endurance <=0 && sanity >0 && !stopRefreshEnd && !endZeroIncin) {
                    editor.putBoolean("stopRefreshEnd", true);
                    editor.apply();
                    mRefreshStoryCallback.onRefreshStory();
                    return;
                }

                if (anonymity <=0 && endurance >0 && !stopRefreshAnon && !anonZeroIncin) {
                    editor.putBoolean("stopRefreshAnon", true);
                    editor.apply();
                    mRefreshStoryCallback.onRefreshStory();
                    return;
                }

                if (!anonZero && eventOne != 100) {
                    if (eventOne == 50) {
                        mRefreshStoryCallback.onRefreshStory();
                    } else {
                        mNextDayCallback.onNextDay();
                    }
                } else if (eventOne !=100) {
                    if (tookBribe) {
                        mNextDayCallback.onNextDay();
                    } else {
                        mOnEndGameCallback.onEndGame(true);
                    }
                } else {
                    mNextDayCallback.onNextDay();
                }
                resultOne.setVisibility(View.VISIBLE);
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sanity = statPref.getInt("sanity", 100);
                endurance = statPref.getInt("endurance", 100);
                wealth = statPref.getInt("wealth", 100);
                anonymity = statPref.getInt("anonymity", 100);

                strength = statPref.getInt("strength", 100);
                dexterity = statPref.getInt("dexterity", 100);
                intelligence = statPref.getInt("intelligence", 100);
                loyalty = statPref.getInt("loyalty", 100);
                beauty = statPref.getInt("beauty", 100);

                stopRefreshSan = pref.getBoolean("stopRefreshSan", false);
                stopRefreshEnd = pref.getBoolean("stopRefreshEnd", false);
                stopRefreshAnon = pref.getBoolean("stopRefreshAnon", false);
                stopRefreshMachine = pref.getBoolean("stopRefreshMachine", false);
                stopRefreshMan = pref.getBoolean("stopRefreshMan", false);
                final boolean anonZeroIncin = pref.getBoolean("anonZeroIncin", false);
                final boolean endZeroIncin = pref.getBoolean("endZeroIncin", false);

                if (sanity <=0 && endurance <=0 && !stopRefreshMan && !endZeroIncin) {
                    editor.putBoolean("stopRefreshMan", true);
                    editor.apply();
                    mRefreshStoryCallback.onRefreshStory();
                    return;
                }
                if (strength <=0 && dexterity <=0 && intelligence <=0 && beauty <=0 && loyalty <=0 && !stopRefreshMachine) {
                    editor.putBoolean("stopRefreshMachine", true);
                    editor.apply();
                    mRefreshStoryCallback.onRefreshStory();
                    return;
                }

                if (sanity <=0 && endurance >0 && !stopRefreshSan) {
                    editor.putBoolean("stopRefreshSan", true);
                    editor.apply();
                    mRefreshStoryCallback.onRefreshStory();
                    return;
                }

                if (endurance <=0 && sanity >0 && !stopRefreshEnd && !endZeroIncin) {
                    editor.putBoolean("stopRefreshEnd", true);
                    editor.apply();
                    mRefreshStoryCallback.onRefreshStory();
                    return;
                }

                if (anonymity <=0 && endurance >0 && !stopRefreshAnon && !anonZeroIncin) {
                    editor.putBoolean("stopRefreshAnon", true);
                    editor.apply();
                    mRefreshStoryCallback.onRefreshStory();
                    return;
                }

                if (eventOne != 105 && eventOne != 100) {
                    resultOne.setVisibility(View.VISIBLE);
                    mNextDayCallback.onNextDay();
                }

                if (eventOne == 100 || eventOne == 105) {
                    mOnEndGameCallback.onEndGame(true);
                }
            }
        });

        return root;
    }

    //Random roll just for 0-10, used as a shortcut for all scouting String Arrays.
    public int randomTen() {
        Random random = new Random();
        return random.nextInt(10);
    }

}
