package ticketoffice.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionPool {

    private static Logger LOG = Logger.getLogger(ConnectionPool.class);

    private BasicDataSource basicDataSource;

    private ConnectionPool() {
        getBasicDataSource();
    }

    private static class SingletonConnectionPool {
        private final static ConnectionPool connectionPool = new ConnectionPool();
    }

    public static ConnectionPool getInstance() {
        return SingletonConnectionPool.connectionPool;
    }

    private void getBasicDataSource() {
        basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(ResourceBundle.getBundle("dbconnection").getString("url"));
        basicDataSource.setDriverClassName(ResourceBundle.getBundle("dbconnection").getString("driver"));
        basicDataSource.setUsername(ResourceBundle.getBundle("dbconnection").getString("login"));
        basicDataSource.setPassword(ResourceBundle.getBundle("dbconnection").getString("pass"));
        basicDataSource.setMinIdle(5);
        basicDataSource.setMaxIdle(20);
        basicDataSource.setMaxTotal(20);
        basicDataSource.setInitialSize(5);
        basicDataSource.setMaxOpenPreparedStatements(50);
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = basicDataSource.getConnection();
            LOG.debug("Connection established " + connection);
        } catch (SQLException e) {
            LOG.error("Could not establish connection \n" + e);
        }
        return connection;
    }

    public void printStat() {
        System.out.println("Active: " + basicDataSource.getNumActive() +
                "/" + basicDataSource.getMaxIdle());
    }
}
