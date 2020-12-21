package model;

import com.jfinal.plugin.activerecord.Model;

public class Room extends Model<Room>{
    public static final Room roomDao = new Room();
}
