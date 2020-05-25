package technexx.android.ManMachine;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class StatsFragment extends Fragment {

    private View root;

    private TextView strengthX;
    private TextView dexterityX;
    private TextView intelligenceX;
    private TextView loyaltyX;
    private TextView beautyX;
    private TextView sanityX;
    private TextView enduranceX;
    private TextView wealthX;
    private TextView anonymityX;
    private TextView fluxX;

    private int mStrength;
    private int mDexterity;
    private int mIntelligence;
    private int mLoyalty;
    private int mBeauty;
    private int mSanity;
    private int mEndurance;
    private int mWealth;
    private int mAnonymity;

    private boolean clearStats;
    private boolean isUpgrading;
    private boolean showPercents;
    private int keptTer;
    private int keptPop;
    private String terChange;
    private String supChange;

    private int newTer;
    private int newSup;

    private String staticStat;
    private boolean subtractVirus;

    private int counter;

    private int upStr; private int upDex; private int upTemp; private int upObed; private int upChar;

    String ebb1;
    String ebb2;
    String ebb3;
    String ebb4;
    String ebb5;

    String manEbb1;
    String manEbb2;
    String manebb3;
    String manEbb4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.stats_fragment, container, false);

        Bundle args = getArguments();

        if (args != null) {
            clearStats = args.getBoolean("clearStats");
            subtractVirus = args.getBoolean("subtractVirus");
        }

        SharedPreferences pref = getContext().getSharedPreferences("SharedPref", 0);
        SharedPreferences.Editor editor = pref.edit();

        boolean showEndStats = pref.getBoolean("showEndStats", false);

        TextView strengthVal = root.findViewById(R.id.strengthVal);
        TextView dexVal = root.findViewById(R.id.dexVal);
        TextView tempVal = root.findViewById(R.id.tempVal);
        TextView obedVal = root.findViewById(R.id.obedVal);
        TextView charVal = root.findViewById(R.id.charVal);
        TextView sanVal = root.findViewById(R.id.sanVal);
        TextView endVal = root.findViewById(R.id.endVal);
        TextView comVal = root.findViewById(R.id.comVal);
        TextView anonVal = root.findViewById(R.id.anonVal);
        TextView fluxVal = root.findViewById(R.id.fluxVal);

        TextView machineHeader = root.findViewById(R.id.machine_header);
        TextView manHeader = root.findViewById(R.id.man_header);
        TextView strength = root.findViewById(R.id.strength);
        TextView dexterity = root.findViewById(R.id.dexterity);
        TextView intelligence = root.findViewById(R.id.intelligence);
        TextView loyalty = root.findViewById(R.id.loyalty);
        TextView beauty = root.findViewById(R.id.beauty);
        TextView sanity = root.findViewById(R.id.sanity);
        TextView endurance = root.findViewById(R.id.endurance);
        TextView wealth = root.findViewById(R.id.wealth);
        TextView anonymity = root.findViewById(R.id.anonymity);
        TextView injections = root.findViewById(R.id.stats_reveal);

        TextView territory = root.findViewById(R.id.territory);
        TextView support = root.findViewById(R.id.support);
        TextView terVal = root.findViewById(R.id.terVal);
        TextView supVal = root.findViewById(R.id.supVal);
        TextView terX = root.findViewById(R.id.terX);
        TextView supX = root.findViewById(R.id.supX);

        TextView virus_machine = root.findViewById(R.id.virus_machine);
        TextView virus_man = root.findViewById(R.id.virus_man);

        virus_machine.setTextColor(getResources().getColor(R.color.Red_Alt));
        virus_man.setTextColor(getResources().getColor(R.color.Red_Alt));
        virus_machine.setText(R.string.infected);
        virus_man.setText(R.string.infected);

        virus_machine.setVisibility(View.INVISIBLE);
        virus_man.setVisibility(View.INVISIBLE);

        if (showEndStats) {

            keptTer = args.getInt("keptTer");
            keptPop = args.getInt("keptPop");
            terChange = args.getString("terChange");
            supChange = args.getString("supChange");

            newTer = args.getInt("newTer");
            newSup = args.getInt("newSup");

            territory.setVisibility(View.VISIBLE);
            support.setVisibility(View.VISIBLE);
            terVal.setVisibility(View.VISIBLE);
            supVal.setVisibility(View.VISIBLE);
            terX.setVisibility(View.VISIBLE);
            supX.setVisibility(View.VISIBLE);

            territory.setText(R.string.end_territory);
            support.setText(R.string.end_support);

            terVal.setText(getString(R.string.stat_percentage, String.valueOf(noZero(keptTer)), getString(R.string.percent_sign)));
            supVal.setText(getString(R.string.stat_percentage, String.valueOf(noZero(keptPop)), getString(R.string.percent_sign)));

            terX.setText(blankIfNull(terChange));
            supX.setText(blankIfNull(supChange));

        } else {
            territory.setVisibility(View.GONE);
            support.setVisibility(View.GONE);
            terVal.setVisibility(View.GONE);
            supVal.setVisibility(View.GONE);
            terX.setVisibility(View.GONE);
            supX.setVisibility(View.GONE);
        }

        if (newTer == 500) {
            terX.setVisibility(View.INVISIBLE);
            supX.setVisibility(View.INVISIBLE);
        }

        if (keptTer == 500) {
            keptTer = 0;
            terVal.setText(getString(R.string.stat_percentage, String.valueOf(0), getString(R.string.percent_sign)));
            terX.setVisibility(View.INVISIBLE);
            supX.setVisibility(View.INVISIBLE);
        }

        strengthX = root.findViewById(R.id.strengthX);
        dexterityX = root.findViewById(R.id.dexterityX);
        intelligenceX = root.findViewById(R.id.intelligenceX);
        loyaltyX = root.findViewById(R.id.loyaltyX);
        beautyX = root.findViewById(R.id.beautyX);
        sanityX = root.findViewById(R.id.sanityX);
        enduranceX = root.findViewById(R.id.enduranceX);
        wealthX = root.findViewById(R.id.wealthX);
        anonymityX = root.findViewById(R.id.anonymityX);
        fluxX = root.findViewById(R.id.fluxX);

        SharedPreferences statPref = getContext().getSharedPreferences("stats", 0);
        SharedPreferences.Editor statEditor = statPref.edit();

        mStrength = statPref.getInt("strength", 100);
        mDexterity = statPref.getInt("dexterity", 100);
        mIntelligence = statPref.getInt("intelligence", 100);
        mLoyalty = statPref.getInt("loyalty", 100);
        mBeauty = statPref.getInt("beauty", 100);
        mSanity = statPref.getInt("sanity", 100);
        mEndurance = statPref.getInt("endurance", 100);
        mWealth = statPref.getInt("wealth", 100);
        mAnonymity = statPref.getInt("anonymity", 100);

        int mFlux = statPref.getInt("injections", 10);

        boolean sanZero = pref.getBoolean("sanZero", false);
        boolean machZero = pref.getBoolean("machineZero", false);

        machineHeader.setText(R.string.machine_header);
        manHeader.setText(R.string.man_header);
        injections.setText(R.string.stats_reveal);
        strength.setText(getString(R.string.strength_creator));
        dexterity.setText(getString(R.string.dexterity_creator));
        intelligence.setText(getString(R.string.intelligence_creator));
        loyalty.setText(getString(R.string.loyalty_creator));
        beauty.setText(getString(R.string.beauty_creator));
        sanity.setText(getString(R.string.sanity));
        endurance.setText(getString(R.string.endurance));
        wealth.setText(getString(R.string.wealth));
        anonymity.setText(getString(R.string.anonymity));


        if (sanZero) {
            manHeader.setText(R.string.insane);
        }

        if (machZero) {
            machineHeader.setText(R.string.broken);
        }


        upStr = pref.getInt("upgradeStr", 0);
        upDex = pref.getInt("upgradeDex", 0);
        upTemp = pref.getInt("upgradeTemp", 0);
        upObed = pref.getInt("upgradeObed", 0);
        upChar = pref.getInt("upgradeChar", 0);

        boolean dealStruck = pref.getBoolean("dealStruck", false);

        boolean upgradeLoss = pref.getBoolean("upgradeLoss", false);

        String dealStat = pref.getString("dealStat", null);

        boolean machineEbb = pref.getBoolean("machineEbb", false);
        boolean manEbb = pref.getBoolean("manEbb", false);

        editor.putString("staticStat", dealStat);
        editor.apply();

        staticStat = pref.getString("staticStat", null);


        int vr1 = new Random().nextInt(4-1) + 1;
        int vr2 = new Random().nextInt(4-1) + 1;
        int vr3 = new Random().nextInt(4-1) + 1;
        int vr4 = new Random().nextInt(4-1) + 1;
        int vr5 = new Random().nextInt(4-1) + 1;

        if (mStrength >0) {
            ebb1 = "(-" + vr1 + ")";
        }
        if (mDexterity >0) {
            ebb2 = "(-" + vr2 + ")";

        }
        if (mIntelligence >0) {
            ebb3 = "(-" + vr3 + ")";

        }
        if (mBeauty >0) {
            ebb4 = "(-" + vr4 + ")";

        }
        if (mLoyalty >0) {
            ebb5 = "(-" + vr5 + ")";

        }

        if (mSanity >0) {
            manEbb1 = "(-" + vr1 + ")";
        }
        if (mEndurance >0) {
            manEbb2 = "(-" + vr2 + ")";
        }
        if (mWealth >0) {
            manebb3 = "(-" + vr3 + ")";
        }
        if (mAnonymity >0) {
            manEbb4 = "(-" + vr4 + ")";
        }

        if (!machineEbb && !manEbb) {
            virus_machine.setVisibility(View.INVISIBLE);
            virus_man.setVisibility(View.INVISIBLE);
        }

        //subtractVirus is used in Game.class callbacks to limit the subtraction, otherwise it would trigger every time this fragment is refreshed.
        if (machineEbb) {
            if (subtractVirus) {
                String staticStat = "null";
                staticStat = pref.getString("staticStat", null);

                strengthX.setText(blankIfNull(ebb1));
                dexterityX.setText(blankIfNull(ebb2));
                intelligenceX.setText(blankIfNull(ebb3));
                beautyX.setText(blankIfNull(ebb5));
                loyaltyX.setText(blankIfNull(ebb4));

                statEditor.putInt("strength",mStrength - vr1 );
                statEditor.putInt("dexterity", mDexterity - vr2);
                statEditor.putInt("intelligence", mIntelligence - vr3);
                statEditor.putInt("loyalty", mLoyalty - vr4);
                statEditor.putInt("beauty", mBeauty -vr5);
                statEditor.apply();
            }
            virus_machine.setVisibility(View.VISIBLE);

            editor.apply();
        }
        if (manEbb) {
            if (subtractVirus) {
                sanityX.setText(blankIfNull(ebb1));
                enduranceX.setText(blankIfNull(ebb2));
                wealthX.setText(blankIfNull(ebb3));
                anonymityX.setText(blankIfNull(ebb4));

                statEditor.putInt("sanity", mSanity - vr1 );
                statEditor.putInt("endurance", mEndurance - vr2);
                statEditor.putInt("wealth", mWealth - vr3);
                statEditor.putInt("anonymity", mAnonymity - vr4);
                statEditor.apply();
            }
            virus_man.setVisibility(View.VISIBLE);
        }

        if (upgradeLoss) {
            String staticStat = "null";
            staticStat = pref.getString("staticStat", null);

            upStr = upgradeLoss(upStr);
            upDex = upgradeLoss(upDex);
            upTemp = upgradeLoss(upTemp);
            upObed = upgradeLoss(upObed);
            upChar = upgradeLoss(upChar);

            List<String> statList = new ArrayList<>();

            if (!staticStat.equals("Strength Infuser")) {
                statList.add("str");
            }
            if (!staticStat.equals("Dexterity Animator")) {
                statList.add("dex");
            }
            if (!staticStat.equals("Intelligence Designer")) {
                statList.add("int");
            }
            if (!staticStat.equals("Beauty Sculptor")) {
                statList.add("bea");
            }
            if (!staticStat.equals("Loyalty Inculcator"))  {
                statList.add("loy");
            }

            Collections.shuffle(statList);

            for (int i=0; i<5-counter; i++){
                if (statList.get(i).equals("str")) {
                    upStr = upgradeLossTwo(upStr);
                }
                if (statList.get(i).equals("dex")) {
                    upDex = upgradeLossTwo(upDex);
                }
                if (statList.get(i).equals("int")) {
                    upTemp = upgradeLossTwo(upTemp);
                }
                if (statList.get(i).equals("loy")) {
                    upObed = upgradeLossTwo(upObed);
                }
                if (statList.get(i).equals("bea")) {
                    upChar = upgradeLossTwo(upChar);
                }
            }

            editor.putInt("upgradeStr", upStr);
            editor.putInt("upgradeDex", upDex);
            editor.putInt("upgradeTemp", upTemp);
            editor.putInt("upgradeObed", upObed);
            editor.putInt("upgradeChar", upChar);
            editor.putBoolean("upgradeLoss", false);
            editor.apply();
        }

        strength.setTextColor(statColor(upStr));
        dexterity.setTextColor(statColor(upDex));
        intelligence.setTextColor(statColor(upTemp));
        beauty.setTextColor(statColor(upChar));
        loyalty.setTextColor(statColor(upObed));


        if (dealStruck) {
            if (staticStat.equals("Strength Infuser")) {
                strength.setTextColor(Color.GRAY);
            }
            if (staticStat.equals("Dexterity Animator")) {
                dexterity.setTextColor(Color.GRAY);
            }
            if (staticStat.equals("Intelligence Designer")) {
                intelligence.setTextColor(Color.GRAY);
            }
            if (staticStat.equals("Loyalty Inculcator")) {
                loyalty.setTextColor(Color.GRAY);
            }
            if (staticStat.equals("Beauty Sculptor")) {
                beauty.setTextColor(Color.GRAY);
            }
            statEditor.apply();
        }

        mStrength = statPref.getInt("strength", 100);
        mDexterity = statPref.getInt("dexterity", 100);
        mIntelligence = statPref.getInt("intelligence", 100);
        mLoyalty = statPref.getInt("loyalty", 100);
        mBeauty = statPref.getInt("beauty", 100);
        mSanity = statPref.getInt("sanity", 100);
        mEndurance = statPref.getInt("endurance", 100);
        mWealth = statPref.getInt("wealth", 100);
        mAnonymity = statPref.getInt("anonymity", 100);

        strengthVal.setText(getString(R.string.stat_percentage, String.valueOf(noZero(mStrength)), getString(R.string.percent_sign)));
        dexVal.setText(getString(R.string.stat_percentage, String.valueOf(noZero(mDexterity)), getString(R.string.percent_sign)));
        tempVal.setText(getString(R.string.stat_percentage, String.valueOf(noZero(mIntelligence)), getString(R.string.percent_sign)));
        obedVal.setText(getString(R.string.stat_percentage, String.valueOf(noZero(mLoyalty)), getString(R.string.percent_sign)));
        charVal.setText(getString(R.string.stat_percentage, String.valueOf(noZero(mBeauty)), getString(R.string.percent_sign)));
        sanVal.setText(getString(R.string.stat_percentage, String.valueOf(noZero(mSanity)), getString(R.string.percent_sign)));
        endVal.setText(getString(R.string.stat_percentage, String.valueOf(noZero(mEndurance)), getString(R.string.percent_sign)));
        comVal.setText(getString(R.string.stat_percentage, String.valueOf(noZero(mWealth)), getString(R.string.percent_sign)));
        anonVal.setText(getString(R.string.stat_percentage, String.valueOf(noZero(mAnonymity)), getString(R.string.percent_sign)));

        fluxVal.setText(String.valueOf(noZero(mFlux)));

        strengthVal.setTextColor(setPercentColor(mStrength));
        dexVal.setTextColor(setPercentColor(mDexterity));
        tempVal.setTextColor(setPercentColor(mIntelligence));
        obedVal.setTextColor(setPercentColor(mLoyalty));
        charVal.setTextColor(setPercentColor(mBeauty));
        sanVal.setTextColor(setPercentColor(mSanity));
        endVal.setTextColor(setPercentColor(mEndurance));
        comVal.setTextColor(setPercentColor(mWealth));
        anonVal.setTextColor(setPercentColor(mAnonymity));


        if (args.getString("strChange") != null) {
            String strChange = args.getString("strChange");
            strengthX.setText(blankIfNull(strChange));
        }

        if (args.getString("dexChange") != null) {
            String dexChange = args.getString("dexChange");
            dexterityX.setText(blankIfNull(dexChange));
        }

        if (args.getString("tempChange") != null) {
            String tempChange = args.getString("tempChange");
            intelligenceX.setText(blankIfNull(tempChange));
        }

        if (args.getString("obedChange") != null) {
            String obedChange = args.getString("obedChange");
            loyaltyX.setText(blankIfNull(obedChange));
        }

        if (args.getString("charChange") != null) {
            String charChange = args.getString("charChange");
            beautyX.setText(blankIfNull(charChange));
        }

        if (args.getString("sanChange") != null) {
            String sanChange = args.getString("sanChange");
            sanityX.setText(blankIfNull(sanChange));
        }

        if (args.getString("endChange") != null) {
            String endChange = args.getString("endChange");
            enduranceX.setText(blankIfNull(endChange));
        }

        if (args.getString("comChange") != null) {
            String comChange = args.getString("comChange");
            wealthX.setText(blankIfNull(comChange));
        }

        if (args.getString("anonChange") != null) {
            String anonChange = args.getString("anonChange");
            anonymityX.setText(blankIfNull(anonChange));
        }

        String injectChange = args.getString("injectChange");
        fluxX.setText(blankIfNull(injectChange));

        if (clearStats) {
            clearStats();
        }
        return root;
    }

    //If s == null, return ""; If s != null, return s;
    public static String blankIfNull(String s) {
        return s == null ? "" : s;
    }

    public void clearStats() {
        strengthX.setText("");
        dexterityX.setText("");
        intelligenceX.setText("");
        loyaltyX.setText("");
        beautyX.setText("");
        sanityX.setText("");
        enduranceX.setText("");
        wealthX.setText("");
        anonymityX.setText("");
    }

    //Sets color of stat based on its value.
    public int setPercentColor (int statValue) {
        int color = 0;
        if (statValue >= 85) {
            color = getResources().getColor(R.color.Dark_Green);
        }
        if (statValue <= 84 && statValue >= 70) {
            color = getResources().getColor(R.color.Light_Green);
        }
        if (statValue <= 69 && statValue >= 55) {
            color = getResources().getColor(R.color.Yellow);
        }
        if (statValue <= 54 && statValue >= 40) {
            color = getResources().getColor(R.color.Gold);
        }
        if (statValue <= 39 && statValue >= 25) {
            color = getResources().getColor(R.color.Orange);
        }
        if (statValue <= 24) {
            color = getResources().getColor(R.color.Red);
        }
        return color;
    }

    //Used to avoid negative stat values.
    public int noZero (int stat) {
        if (stat <0 ) {
            stat = 0;
        }
        return stat;
    }

    public int upgradeLoss(int stat) {
        if (stat == 2) {
            stat = stat -1;
            counter++;
        }
        return stat;
    }

    public int upgradeLossTwo(int stat) {
        if (stat == 1) {
            stat = stat -1;
            counter++;
        }
        return stat;
    }

    public int statColor(int var) {
        int color = 0;
        switch (var) {
            case 0:
                color = getResources().getColor(R.color.White);
                break;
            case 1:
                color = getResources().getColor(R.color.first_up);
                break;
            case 2:
                color = getResources().getColor(R.color.second_up);
                break;
            case 3:
                color = Color.GREEN;
                break;
            case 4:
                color = getResources().getColor(R.color.colorPrimary);
                break;
            case 10:
                color = getResources().getColor(R.color.Dark_Red);

        }
        return color;
    }

    public int convert(int pct) {
        int var = 0;
        if (pct >25 && pct <=50) {
            var = 1;
        }
        if (pct >50 && pct <=75) {
            var = 2;
        }
        if (pct >75) {
            var = 3;
        }
        return var;
    }
}