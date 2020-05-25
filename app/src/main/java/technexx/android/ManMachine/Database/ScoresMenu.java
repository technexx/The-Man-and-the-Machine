package technexx.android.ManMachine.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

@Entity(tableName = "ScoresMenu")

public class ScoresMenu {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String date;
    private int totalScore;
    private String rank;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public int getTotalScore() {
        return totalScore;
    }
    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
