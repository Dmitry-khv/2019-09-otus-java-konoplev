package ru.otus.cachehw.hibernate.api.sessionmanager;

public class SessionManagerException extends RuntimeException {
    public SessionManagerException(String msg) {
        super(msg);
    }
    public SessionManagerException(Exception e) {
        super(e);
    }
}
