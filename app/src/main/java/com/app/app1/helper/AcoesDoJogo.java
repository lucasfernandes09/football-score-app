package com.app.app1.helper;

public class AcoesDoJogo {

    String name;
    String time;
    boolean home;
    String type;

    public AcoesDoJogo(String name, String time, boolean home, String type) {
        this.name = name;
        this.time = time;
        this.home = home;
        this.type = type;
    }

    public AcoesDoJogo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isHome() {
        return home;
    }

    public void setHome(boolean home) {
        this.home = home;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
