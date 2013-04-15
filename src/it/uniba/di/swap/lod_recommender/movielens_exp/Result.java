package it.uniba.di.swap.lod_recommender.movielens_exp;

import it.uniba.di.swap.lod_recommender.Configuration;
import it.uniba.di.swap.lod_recommender.recommendation.Recommendation;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lusio
 * Date: 12/04/13
 * Time: 10.47
 */
public class Result {
    private Configuration configuration;
    private User user;
    private int tp;
    private int tn;
    private int fp;
    private int fn;
    private int tp_T;
    private int tn_T;
    private int fp_T;
    private int fn_T;

    private double sumInverseRank;
    private double sumInverseRank_T;

    private double idealInverseRank;
    private double idealInverseRank_T;


    private double precision;
    private double precision_T;
    private double mrr;
    private double mrr_T;


    //    private double rPrecision;

    private List<Recommendation> positive;
    private List<Recommendation> negative;
    private List<Recommendation> positive_T;
    private List<Recommendation> negative_T;

    public Result(Configuration configuration, User user) {
        this.configuration = configuration;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Result result = (Result) o;

        if (!configuration.equals(result.configuration)) return false;
        return user.equals(result.user);

    }

    @Override
    public int hashCode() {
        int result = configuration.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public User getUser() {
        return user;
    }

    public List<Recommendation> getPositive() {
        return positive;
    }

    public void setPositive(List<Recommendation> positive) {
        this.positive = positive;
    }

    public List<Recommendation> getNegative() {
        return negative;
    }

    public void setNegative(List<Recommendation> negative) {
        this.negative = negative;
    }

    public List<Recommendation> getPositive_T() {
        return positive_T;
    }

    public void setPositive_T(List<Recommendation> positive_T) {
        this.positive_T = positive_T;
    }

    public List<Recommendation> getNegative_T() {
        return negative_T;
    }

    public void setNegative_T(List<Recommendation> negative_T) {
        this.negative_T = negative_T;
    }

    public int getTp() {
        return tp;
    }

    public void setTp(int tp) {
        this.tp = tp;
    }

    public int getTn() {
        return tn;
    }

    public void setTn(int tn) {
        this.tn = tn;
    }

    public int getFp() {
        return fp;
    }

    public void setFp(int fp) {
        this.fp = fp;
    }

    public int getFn() {
        return fn;
    }

    public void setFn(int fn) {
        this.fn = fn;
    }

    public int getTp_T() {
        return tp_T;
    }

    public void setTp_T(int tp_T) {
        this.tp_T = tp_T;
    }

    public int getTn_T() {
        return tn_T;
    }

    public void setTn_T(int tn_T) {
        this.tn_T = tn_T;
    }

    public int getFp_T() {
        return fp_T;
    }

    public void setFp_T(int fp_T) {
        this.fp_T = fp_T;
    }

    public int getFn_T() {
        return fn_T;
    }

    public void setFn_T(int fn_T) {
        this.fn_T = fn_T;
    }

    public double getSumInverseRank() {
        return sumInverseRank;
    }

    public void setSumInverseRank(double sumInverseRank) {
        this.sumInverseRank = sumInverseRank;
    }

    public double getSumInverseRank_T() {
        return sumInverseRank_T;
    }

    public void setSumInverseRank_T(double sumInverseRank_T) {
        this.sumInverseRank_T = sumInverseRank_T;
    }

    public double getIdealInverseRank() {
        return idealInverseRank;
    }

    public void setIdealInverseRank(double idealInverseRank) {
        this.idealInverseRank = idealInverseRank;
    }

    public double getIdealInverseRank_T() {
        return idealInverseRank_T;
    }

    public void setIdealInverseRank_T(double idealInverseRank_T) {
        this.idealInverseRank_T = idealInverseRank_T;
    }

    public double getPrecision() {
        return precision;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
    }

    public double getPrecision_T() {
        return precision_T;
    }

    public void setPrecision_T(double precision_T) {
        this.precision_T = precision_T;
    }

    public double getMrr() {
        return mrr;
    }

    public void setMrr(double mrr) {
        this.mrr = mrr;
    }

    public double getMrr_T() {
        return mrr_T;
    }

    public void setMrr_T(double mrr_T) {
        this.mrr_T = mrr_T;
    }
}