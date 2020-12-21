package model;

import com.jfinal.plugin.activerecord.Model;

import java.util.List;

public class EmptyRoom extends Model<EmptyRoom> {
    public static final EmptyRoom emptyRoomDao = new EmptyRoom();
}
