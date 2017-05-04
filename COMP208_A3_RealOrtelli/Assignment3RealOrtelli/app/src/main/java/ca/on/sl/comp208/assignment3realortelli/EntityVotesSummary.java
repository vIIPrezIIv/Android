package ca.on.sl.comp208.assignment3realortelli;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ray on 2017-04-06.
 */

/**
 * POJO Class for EntityVotesSummary
 */
public class EntityVotesSummary {

    @SerializedName("entityName")
    @Expose
    private String entityName;
    @SerializedName("entityID")
    @Expose
    private Integer entityID;
    @SerializedName("totalVotesSum")
    @Expose
    private Integer totalVotesSum;
    @SerializedName("userID")
    @Expose
    private Object userID;
    @SerializedName("userVoteScore")
    @Expose
    private Object userVoteScore;

    @Override
    public String toString() {
        return "EntityVotesSummary{" +
                "entityName='" + entityName + '\'' +
                ", entityID=" + entityID +
                ", totalVotesSum=" + totalVotesSum +
                ", userID=" + userID +
                ", userVoteScore=" + userVoteScore +
                '}';
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Integer getEntityID() {
        return entityID;
    }

    public void setEntityID(Integer entityID) {
        this.entityID = entityID;
    }

    public Integer getTotalVotesSum() {
        return totalVotesSum;
    }

    public void setTotalVotesSum(Integer totalVotesSum) {
        this.totalVotesSum = totalVotesSum;
    }

    public Object getUserID() {
        return userID;
    }

    public void setUserID(Object userID) {
        this.userID = userID;
    }

    public Object getUserVoteScore() {
        return userVoteScore;
    }

    public void setUserVoteScore(Object userVoteScore) {
        this.userVoteScore = userVoteScore;
    }
}
