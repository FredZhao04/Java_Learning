package model;

import java.io.Serializable;

/**
 * Created by fred on 2018/6/10.
 */
public class ResultSet implements Serializable{
    private boolean flag;
    private String description;

    public ResultSet() {
    }

    public ResultSet(boolean flag, String description) {
        this.flag = flag;
        this.description = description;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ResultSet{" +
                "flag=" + flag +
                ", description='" + description + '\'' +
                '}';
    }
}
