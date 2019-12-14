package com.ousy.formlayoutmanager.entity;

public class Monster {

    /**
     * name : 混沌战士
     * attribute : 地
     * lv : 8
     * atk : 3000
     * def : 2500
     * race : 战士
     * type1 : 仪式
     * type2 : 效果
     */

    private String name;
    private String attribute;
    private String lv;
    private String atk;
    private String def;
    private String race;
    private String type1;
    private String type2;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getLv() {
        return lv;
    }

    public void setLv(String lv) {
        this.lv = lv;
    }

    public String getAtk() {
        return atk;
    }

    public void setAtk(String atk) {
        this.atk = atk;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }
}
