package ticketoffice.persistence.dao.impl.jdbc;

import org.apache.log4j.Logger;
import ticketoffice.persistence.dao.PreparedParameters;
import ticketoffice.persistence.dao.interfaces.AbstractDao;
import ticketoffice.persistence.mapper.Mapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDaoImpl<T> implements AbstractDao<T> {

    protected static Logger LOG = Logger.getLogger(AbstractDaoImpl.class);
    protected Connection connection;
    private Mapper mapper;

    public AbstractDaoImpl(Connection connection, Mapper mapper) {
        this.connection = connection;
        setMapper(mapper);
    }

    protected void setMapper(Mapper mapper){
        mapper.setDao(this);
        this.mapper = mapper;
    }

    public int create(String sql, PreparedParameters parameters) {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            parameters.setParameters(statement);
            statement.execute();
            LOG.info("Create item successful");
            return 1;
        } catch (SQLIntegrityConstraintViolationException e) {
            LOG.error("Create item fails: " + e.getMessage());
//            throw new IllegalArgumentException(e.getMessage());
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    public Optional<T> getById(String sql, PreparedParameters parameters) {
        Optional item = Optional.empty();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            parameters.setParameters(statement);
            ResultSet resultSet = statement.executeQuery();
            item = mapper.extractItem(resultSet);
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
        return item;
    }

    public boolean delete(String sql, PreparedParameters parameters) {
        int deleteItem = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            parameters.setParameters(statement);
            deleteItem = statement.executeUpdate();
            LOG.info(deleteItem + " item was deleted");
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
        return deleteItem == 1;
    }

    public boolean update(String sql, PreparedParameters parameters) {
        int updateItem = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            parameters.setParameters(statement);
            updateItem = statement.executeUpdate();
            LOG.info(updateItem + " Item was updated");
        } catch (SQLIntegrityConstraintViolationException e) {
            LOG.error("Update item fails: " + e.getMessage());
//            throw new IllegalArgumentException(e.getMessage());
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
        return updateItem == 1;
    }

    public List<T> getAll(String sql, PreparedParameters parameters) {
        List<T> resultList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            parameters.setParameters(statement);
            ResultSet resultSet = statement.executeQuery();
            resultList = mapper.extractItemList(resultSet);
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
        return resultList;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
