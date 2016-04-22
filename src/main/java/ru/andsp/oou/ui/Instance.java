package ru.andsp.oou.ui;


import java.io.Serializable;

public class Instance implements Serializable {

    private String id;

    private String path;

    private String host;

    private String port;

    private String user;

    private String pass;

    private String db;


    public Instance() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public boolean isNew() {
        return (id == null);
    }

    @Override
    public String toString() {
        return String.format("%s %s", host, db);
    }
}
