package it.uniba.di.swap.lod_recommender.mysql;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: lusio
 * Date: 05/04/13
 * Time: 16.33
 */
public class DBAccess {

    private static Connection conn = null;
    private static PreparedStatement ps = null;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/loddb", "root", "root");
            ps = conn.prepareStatement("INSERT INTO results VALUES (?,?,?,?,?,?,?,?);");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DBAccess() {
    }

    public static boolean insert(int film, int user, int rank, int distance, int profile, String filmName, String distanceName, String profileName) {
        try {
            ps.setInt(1, film);
            ps.setInt(2, user);
            ps.setInt(3, rank);
            ps.setInt(4, distance);
            ps.setInt(5, profile);
            ps.setString(6, filmName);
            ps.setString(7, distanceName);
            ps.setString(8, profileName);
            return ps.execute();
        } catch (MySQLIntegrityConstraintViolationException ex) {
            //CIAOOOOOO
        } catch (SQLException e) {
            System.out.println(ps.toString());
            e.printStackTrace();
        }
        return false;
    }
}
