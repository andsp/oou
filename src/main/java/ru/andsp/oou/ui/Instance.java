package ru.andsp.oou.ui;


public class Instance {

    private int id;

    private String path;

    private String host;

    private String port;

    private String user;

    private String pass;

    private String db;


    public Instance() {
    }

    public Instance(int id, String path, String host, String port, String user, String pass, String db) {
        this.id = id;
        this.path = path;
        this.host = host;
        this.port = port;
        this.user = user;
        this.pass = pass;
        this.db = db;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        return !(id > 0);
    }

    @Override
    public String toString() {
        return String.format("%s %s", host, db);
    }
}
