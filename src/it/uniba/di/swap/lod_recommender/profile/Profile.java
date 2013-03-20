package it.uniba.di.swap.lod_recommender.profile;

/**
 * Created with IntelliJ IDEA.
 * User: lusio
 * Date: 20/03/13
 * Time: 14.41
 */
public abstract class Profile {
    private int maxVote = 5;
    private int minVote = 1;
    private int granularityVote = 1;
    private int mediumVote = 3;

    public int getMaxVote() {
        return maxVote;
    }

    public void setMaxVote(int maxVote) {
        this.maxVote = maxVote;
    }

    public int getMinVote() {
        return minVote;
    }

    public void setMinVote(int minVote) {
        this.minVote = minVote;
    }

    public int getGranularityVote() {
        return granularityVote;
    }

    public void setGranularityVote(int granularityVote) {
        this.granularityVote = granularityVote;
    }

    public int getMediumVote() {
        return mediumVote;
    }

    public void setMediumVote(int mediumVote) {
        this.mediumVote = mediumVote;
    }
}
