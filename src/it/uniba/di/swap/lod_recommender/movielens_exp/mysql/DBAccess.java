package it.uniba.di.swap.lod_recommender.movielens_exp.mysql;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.*;

public class DBAccess {

    public static final int RECOMMENDATION = 0;
    public static final int RESULTS = 1;
    public static final int RESULTS_AGG = 2;
    private static Connection conn = null;
    private static PreparedStatement psRec = null;
    private static PreparedStatement psRes = null;
    private static PreparedStatement psAgg = null;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/loddb", "root", "root");
            conn.setAutoCommit(false);

            Statement s = conn.createStatement();
            s.execute("TRUNCATE TABLE RESULTS;");
            s.execute("TRUNCATE TABLE RECOMMENDATIONS;");
            s.execute("TRUNCATE TABLE RESULTS_AGG;");
            conn.commit();
            s.close();

            psRec = conn.prepareStatement("INSERT INTO RECOMMENDATIONS VALUES (?,?,?,?,?);");
            psRes = conn.prepareStatement("INSERT INTO RESULTS VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
            psAgg = conn.prepareStatement("INSERT INTO RESULTS_AGG VALUES (?,?,?,?,?,?,?,?,?);");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private DBAccess() {
    }

    public static void insertREC(int distance, int profile, int user, int film, int rank) {
        try {
            psRec.setInt(1, distance);
            psRec.setInt(2, profile);
            psRec.setInt(3, user);
            psRec.setInt(4, film);
            psRec.setInt(5, rank);
            psRec.addBatch();
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("[WARNING] Chiave primaria duplicata.");
        } catch (SQLException e) {
            System.out.println(psRec.toString());
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void insertRES(int distance,
                                 int profile,
                                 int k,
                                 int user,
                                 int tp,
                                 int tn,
                                 int fp,
                                 int fn,
                                 int tp_T,
                                 int tn_T,
                                 int fp_T,
                                 int fn_T,
                                 double sumInverseRank,
                                 double sumInverseRank_T,
                                 double idealInverseRank,
                                 double idealInverseRank_T
                                 ) {
        try {
            psRes.setInt(1, distance);
            psRes.setInt(2, profile);
            psRes.setInt(3, k);
            psRes.setInt(4, user);
            psRes.setInt(5, tp);
            psRes.setInt(6, tn);
            psRes.setInt(7, fp);
            psRes.setInt(8, fn);
            psRes.setInt(9, tp_T);
            psRes.setInt(10, tn_T);
            psRes.setInt(11, fp_T);
            psRes.setInt(12, fn_T);
            psRes.setDouble(13, sumInverseRank);
            psRes.setDouble(14, sumInverseRank_T);
            psRes.setDouble(15, idealInverseRank);
            psRes.setDouble(16, idealInverseRank_T);
            psRes.setDouble(17, ( (double) tp ) / (tp+fp) );
            psRes.setDouble(18, ( (double) tp_T ) / (tp_T+fp_T) );
            psRes.setDouble(19, sumInverseRank / idealInverseRank );
            psRes.setDouble(20, sumInverseRank_T / idealInverseRank_T );
            psRes.addBatch();
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("[WARNING] Chiave primaria duplicata.");
        } catch (SQLException e) {
            System.out.println(psRes.toString());
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void insertAGG(int distance,
                                 int profile,
                                 int k,
                                 double microPrec,
                                 double macroPrec,
                                 double microPrec_T,
                                 double macroPrec_T,
                                 double microMRR,
                                 double macroMRR,
                                 double microMRR_T,
                                 double macroMRR_T) {
        try {
            psAgg.setInt(1, distance);
            psAgg.setInt(2, profile);
            psAgg.setInt(3, k);
            psAgg.setDouble(4, microPrec);
            psAgg.setDouble(5, macroPrec);
            psAgg.setDouble(6, microPrec_T);
            psAgg.setDouble(7, macroPrec_T);
            psAgg.setDouble(8, microMRR);
            psAgg.setDouble(9, macroMRR);
            psAgg.setDouble(10, microMRR_T);
            psAgg.setDouble(11, macroMRR_T);
            psAgg.addBatch();
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("[WARNING] Chiave primaria duplicata.");
        } catch (SQLException e) {
            System.out.println(psAgg.toString());
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void commit(int type) {
        try {
            switch (type) {
                case RECOMMENDATION:
                    psRec.executeBatch();
                    conn.commit();
                    break;
                case RESULTS:
                    psRes.executeBatch();
                    conn.commit();
                    break;
                case RESULTS_AGG:
                    psAgg.executeBatch();
                    conn.commit();
                    break;
            }
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("[WARNING] Chiave primaria duplicata.");
        } catch (BatchUpdateException bee) {
            System.out.println("[WARNING] Chiave primaria duplicata.");
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static void close(int type) {
        try {
            switch (type) {
                case RECOMMENDATION:
                    psRec.close();
                    conn.close();
                    break;
                case RESULTS:
                    psRes.close();
                    conn.close();
                    break;
                case RESULTS_AGG:
                    psAgg.close();
                    conn.close();
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
