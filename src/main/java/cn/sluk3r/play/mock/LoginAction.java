package cn.sluk3r.play.mock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by baiing on 2014/7/11.
 */
public class LoginAction {
    public String login(Connection conn, String name, int pas) {

        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String sql = "select * from user where name =? And pas =?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setInt(2, 2);
            rs = pst.executeQuery();

            if (rs.next()) {
                return "Successful landing. ";
            } else {
                return "Login failed.";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "An exception is thrown. ";
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
