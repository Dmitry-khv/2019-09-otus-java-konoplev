package ru.otus.jdbc.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.dao.ModelDao;
import ru.otus.api.dao.ModelDaoException;
import ru.otus.api.model.User;
import ru.otus.api.sessionmanager.SessionManager;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Optional;

public class UserDaoJdbc {
  private static Logger logger = LoggerFactory.getLogger(UserDaoJdbc.class);

  private final SessionManagerJdbc sessionManager;
  private final DbExecutor<User> dbExecutor;

  public UserDaoJdbc(SessionManagerJdbc sessionManager, DbExecutor<User> dbExecutor) {
    this.sessionManager = sessionManager;
    this.dbExecutor = dbExecutor;
  }

  public Optional<User> findById(long id) {
    try {
      return dbExecutor.selectRecord(getConnection(), "select id, name from user where id  = ?", id, resultSet -> {
        try {
          if (resultSet.next()) {
//            return new User(resultSet.getLong("id"), resultSet.getString("name"));
          }
        } catch (SQLException e) {
          logger.error(e.getMessage(), e);
        }
        return null;
      });
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return Optional.empty();
  }


  public long saveUser(User user) {
    try {
      return dbExecutor.insertRecord(getConnection(), "insert into user(name) values (?)", Collections.singletonList(user.getName()));
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new ModelDaoException(e);
    }
  }

  public SessionManager getSessionManager() {
    return sessionManager;
  }

  private Connection getConnection() {
    return sessionManager.getCurrentSession().getConnection();
  }
}
