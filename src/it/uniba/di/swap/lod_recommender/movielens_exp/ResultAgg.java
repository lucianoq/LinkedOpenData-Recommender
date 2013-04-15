package it.uniba.di.swap.lod_recommender.movielens_exp;

import it.uniba.di.swap.lod_recommender.Configuration;

/**
 * Created with IntelliJ IDEA.
 * User: lusio
 * Date: 15/04/13
 * Time: 11.56
 */
public class ResultAgg {
    private Configuration configuration;
    private double microPrecision;
    private double macroPrecision;
    private double microPrecision_T;
    private double macroPrecision_T;
    private double microMRR;
    private double macroMRR;
    private double microMRR_T;
    private double macroMRR_T;

    public ResultAgg(Configuration configuration) {
        this.configuration = configuration;
    }

    public double getMicroMRR_T() {
        return microMRR_T;
    }

    public void setMicroMRR_T(double microMRR_T) {
        this.microMRR_T = microMRR_T;
    }

    public double getMacroMRR_T() {
        return macroMRR_T;
    }

    public void setMacroMRR_T(double macroMRR_T) {
        this.macroMRR_T = macroMRR_T;
    }

    public double getMicroPrecision() {
        return microPrecision;
    }

    public void setMicroPrecision(double microPrecision) {
        this.microPrecision = microPrecision;
    }

    public double getMacroPrecision() {
        return macroPrecision;
    }

    public void setMacroPrecision(double macroPrecision) {
        this.macroPrecision = macroPrecision;
    }

    public double getMicroPrecision_T() {
        return microPrecision_T;
    }

    public void setMicroPrecision_T(double microPrecision_T) {
        this.microPrecision_T = microPrecision_T;
    }

    public double getMacroPrecision_T() {
        return macroPrecision_T;
    }

    public void setMacroPrecision_T(double macroPrecision_T) {
        this.macroPrecision_T = macroPrecision_T;
    }

    public double getMicroMRR() {
        return microMRR;
    }

    public void setMicroMRR(double microMRR) {
        this.microMRR = microMRR;
    }

    public double getMacroMRR() {
        return macroMRR;
    }

    public void setMacroMRR(double macroMRR) {
        this.macroMRR = macroMRR;
    }
}
