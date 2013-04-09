package it.uniba.di.swap.lod_recommender.movielens_exp.mysql;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import it.uniba.di.swap.lod_recommender.graph.Film;
import it.uniba.di.swap.lod_recommender.movielens_exp.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBAccess {

    private static Connection conn = null;
    private static PreparedStatement ps = null;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/loddb", "root", "root");
            conn.setAutoCommit(false);

            Statement s = conn.createStatement();
            s.execute("DELETE FROM results;");
            conn.commit();
            s.close();

            ps = conn.prepareStatement("INSERT INTO results VALUES (?,?,?,?,?,?,?,?);");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DBAccess() {
    }

    public static void insert(int film, int user, int rank, int distance, int profile, String filmName, String distanceName, String profileName) {
        try {
            ps.setInt(1, film);
            ps.setInt(2, user);
            ps.setInt(3, rank);
            ps.setInt(4, distance);
            ps.setInt(5, profile);
            ps.setString(6, filmName);
            ps.setString(7, distanceName);
            ps.setString(8, profileName);
            ps.addBatch();
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("[WARNING] Chiave primaria duplicata.");
        } catch (SQLException e) {
            System.out.println(ps.toString());
            e.printStackTrace();
        }
    }

    public static void commit() {
        try {
            ps.executeBatch();
            conn.commit();
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("[WARNING] Chiave primaria duplicata.");
        } catch (BatchUpdateException bee) {
            System.out.println("[WARNING] Chiave primaria duplicata.");
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static void close() {
        try {
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
