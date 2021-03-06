package ru.otus.jdbc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.dao.UserDao;
import ru.otus.api.dao.UserDaoException;
import ru.otus.api.model.User;
import ru.otus.api.sessionmanager.SessionManager;
import ru.otus.jdbc.DBExecutor;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.sql.Connection;

public class UserDaoJdbc implements UserDao {
    private static Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);

    private final SessionManagerJdbc sessionManagerJdbc;
    private final DBExecutor<User> dbExecutor;

    public UserDaoJdbc(SessionManagerJdbc sessionManagerJdbc, DBExecutor<User> dbExecutor) {
        this.sessionManagerJdbc = sessionManagerJdbc;
        this.dbExecutor = dbExecutor;
    }

    @Override
    public long save(User user) {
        try {
            return dbExecutor.save(getConnection(), user);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public User findById(long id) {
        try {
            return dbExecutor.load(getConnection(), User.class, id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public void update(User user) {
        try {
            dbExecutor.update(getConnection(), user);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManagerJdbc;
    }

    public Connection getConnection() {
        return sessionManagerJdbc.getCurrentSession().getConnection();
    }
}
