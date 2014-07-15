package cn.sluk3r.test.collection.mock;



import   static org.easymock.EasyMock.createControl;
import   static org.easymock.EasyMock.expect;
import   static org.junit.Assert.assertEquals;

import cn.sluk3r.play.mock.LoginAction;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by baiing on 2014/7/11.
 */
public class LoginActionTest {
    private LoginAction login;

    @Before
    public   void init () {
        login = new LoginAction();
    }

    @After
    public   void destroy () {
        login = null;
    }

    @Test
    public   void login () throws SQLException {
        String name = "admin";
        int pas = 123;

        // Create Mock Objects
        IMocksControl control = createControl(); // create multiple Mock objects when by IMocksControl management

        Connection conn = control.createMock (Connection.class);
        PreparedStatement pst = control.createMock (PreparedStatement.class);
        ResultSet rs = control.createMock (ResultSet.class);

        // Record set Mock Object expected behavior and output
        // Mock objects need to be performed must be recorded, such as pst.setInt (2 pas), rs.close ()
        String sql = "select * from user where name =? And pas =?";
        expect (conn.prepareStatement (sql)).andReturn (pst). times (1);

        pst.setString (1, name);
        pst.setInt (2, pas);

        expect (pst.executeQuery ()).andReturn (rs);
        expect (rs.next ()).andReturn(true);

        rs.close ();
        pst.close ();

        // Recording is completed, switch the replay state
        control.replay ();

        // The actual method is invoked
        String res = login.login (conn, name, pas);
        String expected = "successful landing.";
        assertEquals (expected, res);

        // Verification
        control.verify ();
    }
}
