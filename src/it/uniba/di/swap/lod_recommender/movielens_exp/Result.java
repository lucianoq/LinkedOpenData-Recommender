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
    private double precisionAtK;
    private double precisionAtKInTest;
    private double rPrecision;
    private double mrr;
    private int tp;
    private int tn;
    private int fp;
    private int fn;
    private int tpInTest;
    private int tnInTest;
    private int fpInTest;
    private int fnInTest;
    private List<Recommendation> positive;
    private List<Recommendation> negative;
    private List<Recommendation> positiveInTest;
    private List<Recommendation> negativeInTest;

    public Result(Configuration configuration, User user) {
        this.configuration = configuration;
        this.user = user;
    }

    public double getPrecisionAtK() {
        return precisionAtK;
    }

    public void setPrecisionAtK(double precisionAtK) {
        this.precisionAtK = precisionAtK;
    }

    public double getPrecisionAtKInTest() {
        return precisionAtKInTest;
    }

    public void setPrecisionAtKInTest(double precisionAtKInTest) {
        this.precisionAtKInTest = precisionAtKInTest;
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

    public List<Recommendation> getPositiveInTest() {
        return positiveInTest;
    }

    public void setPositiveInTest(List<Recommendation> positiveInTest) {
        this.positiveInTest = positiveInTest;
    }

    public List<Recommendation> getNegativeInTest() {
        return negativeInTest;
    }

    public void setNegativeInTest(List<Recommendation> negativeInTest) {
        this.negativeInTest = negativeInTest;
    }

    public double getrPrecision() {
        return rPrecision;
    }

    public void setrPrecision(double rPrecision) {
        this.rPrecision = rPrecision;
    }

    public double getMrr() {
        return mrr;
    }

    public void setMrr(double mrr) {
        this.mrr = mrr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Result result = (Result) o;

        if (!configuration.equals(result.configuration)) return false;
        if (!user.equals(result.user)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = configuration.hashCode();
        result = 31 * result + user.hashCode();
        return result;
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

    public int getTpInTest() {
        return tpInTest;
    }

    public void setTpInTest(int tpInTest) {
        this.tpInTest = tpInTest;
    }

    public int getTnInTest() {
        return tnInTest;
    }

    public void setTnInTest(int tnInTest) {
        this.tnInTest = tnInTest;
    }

    public int getFpInTest() {
        return fpInTest;
    }

    public void setFpInTest(int fpInTest) {
        this.fpInTest = fpInTest;
    }

    public int getFnInTest() {
        return fnInTest;
    }

    public void setFnInTest(int fnInTest) {
        this.fnInTest = fnInTest;
    }

    @Override
    public String toString() {
        return "Result{" +
                "configuration=" + configuration +
                ", user=" + user +
                ", precisionAtK=" + precisionAtK +
                ", precisionAtKInTest=" + precisionAtKInTest +
                ", rPrecision=" + rPrecision +
                ", mrr=" + mrr +
                '}';
    }
}