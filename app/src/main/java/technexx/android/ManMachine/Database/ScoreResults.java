package technexx.android.ManMachine.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ScoreResults")

public class ScoreResults {
    @PrimaryKey(autoGenerate =  true)
    private int id;

    private int strValue;
    private int dexValue;
    private int intValue;
    private int obedValue;
    private int beaValue;

    private int quirkOne;
    private int quirkTwo;
    private int quirkThree;

    private String creatureStats;

    public ScoreResults() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStrValue() {
        return strValue;
    }

    public void setStrValue(int strValue) {
        this.strValue = strValue;
    }

    public int getDexValue() {
        return dexValue;
    }

    public void setDexValue(int dexValue) {
        this.dexValue = dexValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public int getObedValue() {
        return obedValue;
    }

    public void setObedValue(int obedValue) {
        this.obedValue = obedValue;
    }

    public int getBeaValue() {
        return beaValue;
    }

    public void setBeaValue(int beaValue) {
        this.beaValue = beaValue;
    }

    public int getQuirkOne() {
        return quirkOne;
    }

    public void setQuirkOne(int quirkOne) {
        this.quirkOne = quirkOne;
    }

    public int getQuirkTwo() {
        return quirkTwo;
    }

    public void setQuirkTwo(int quirkTwo) {
        this.quirkTwo = quirkTwo;
    }

    public int getQuirkThree() {
        return quirkThree;
    }

    public void setQuirkThree(int quirkThree) {
        this.quirkThree = quirkThree;
    }

    public String getCreatureStats() {
        return creatureStats;
    }

    public void setCreatureStats(String creatureStats) {
        this.creatureStats = creatureStats;
    }
}
