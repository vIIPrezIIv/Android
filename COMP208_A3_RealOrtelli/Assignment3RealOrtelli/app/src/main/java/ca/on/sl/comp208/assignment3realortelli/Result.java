package ca.on.sl.comp208.assignment3realortelli;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ray on 2017-04-06.
 */

/**
 * POJO Class for Result
 */
public class Result {

    @SerializedName("generatorID")
    @Expose
    private Integer generatorID;
    @SerializedName("imageID")
    @Expose
    private Integer imageID;
    @SerializedName("displayName")
    @Expose
    private String displayName;
    @SerializedName("urlName")
    @Expose
    private String urlName;
    @SerializedName("totalVotesScore")
    @Expose
    private Integer totalVotesScore;
    @SerializedName("instancesCount")
    @Expose
    private Integer instancesCount;
    @SerializedName("ranking")
    @Expose
    private Integer ranking;
    @SerializedName("entityVotesSummary")
    @Expose
    private EntityVotesSummary entityVotesSummary;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    @Override
    public String toString() {
        return "Result{" +
                "generatorID=" + generatorID +
                ", imageID=" + imageID +
                ", displayName='" + displayName + '\'' +
                ", urlName='" + urlName + '\'' +
                ", totalVotesScore=" + totalVotesScore +
                ", instancesCount=" + instancesCount +
                ", ranking=" + ranking +
                ", entityVotesSummary=" + entityVotesSummary +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    public Integer getGeneratorID() {
        return generatorID;
    }

    public void setGeneratorID(Integer generatorID) {
        this.generatorID = generatorID;
    }

    public Integer getImageID() {
        return imageID;
    }

    public void setImageID(Integer imageID) {
        this.imageID = imageID;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public Integer getTotalVotesScore() {
        return totalVotesScore;
    }

    public void setTotalVotesScore(Integer totalVotesScore) {
        this.totalVotesScore = totalVotesScore;
    }

    public Integer getInstancesCount() {
        return instancesCount;
    }

    public void setInstancesCount(Integer instancesCount) {
        this.instancesCount = instancesCount;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public EntityVotesSummary getEntityVotesSummary() {
        return entityVotesSummary;
    }

    public void setEntityVotesSummary(EntityVotesSummary entityVotesSummary) {
        this.entityVotesSummary = entityVotesSummary;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
