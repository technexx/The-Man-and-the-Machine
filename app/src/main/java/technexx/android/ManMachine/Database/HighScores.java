package technexx.android.ManMachine.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "HighScores")

public class HighScores {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int strScore;
    private int dexScore;
    private int intScore;
    private int obedScore;
    private int beaScore;
    private int sanScore;
    private int endScore;
    private int comScore;
    private int anonScore;
    private int launchSuccess;
    private int terScore;
    private int supScore;
    private int endSceneScore;

    private int totalScore;
    private String summary;
    private String endRank;

    public HighScores() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getStrScore() {
        return strScore;
    }
    public void setStrScore(int strScore) {
        this.strScore = strScore;
    }

    public int getDexScore() {
        return dexScore;
    }
    public void setDexScore(int dexScore) {
        this.dexScore = dexScore;
    }

    public int getIntScore() {
        return intScore;
    }
    public void setIntScore(int intScore) {
        this.intScore = intScore;
    }

    public int getObedScore() {
        return obedScore;
    }
    public void setObedScore(int obedScore) {
        this.obedScore = obedScore;
    }

    public int getBeaScore() {
        return beaScore;
    }
    public void setBeaScore(int beaScore) {
        this.beaScore = beaScore;
    }

    public int getSanScore() {
        return sanScore;
    }
    public void setSanScore(int sanScore) {
        this.sanScore = sanScore;
    }

    public int getEndScore() {
        return endScore;
    }
    public void setEndScore(int endScore) {
        this.endScore = endScore;
    }

    public int getComScore() {
        return comScore;
    }
    public void setComScore(int comScore) {
        this.comScore = comScore;
    }

    public int getAnonScore() {
        return anonScore;
    }
    public void setAnonScore(int anonScore) {
        this.anonScore = anonScore;
    }

    public int getLaunchSuccess() {
        return launchSuccess;
    }
    public void setLaunchSuccess (int launchSuccess) {
        this.launchSuccess = launchSuccess;
    }

    public int getTerScore() {
        return terScore;
    }
    public void setTerScore(int terScore) {
        this.terScore = terScore;
    }

    public int getSupScore() {
        return supScore;
    }
    public void setSupScore(int supScore) {
        this.supScore = supScore;
    }

    public int getEndSceneScore() {
        return endSceneScore;
    }
    public void setEndSceneScore(int endSceneScore) {
        this.endSceneScore = endSceneScore;
    }

    public int getTotalScore(){
        return totalScore;
    }
    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getEndRank() {
        return endRank;
    }

    public void setEndRank(String endRank) {
        this.endRank = endRank;
    }
}
