package controller;

import com.jfinal.config.*;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.Sqlite3Dialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import model.EmptyRoom;
import model.Room;
import model.Time;

public class RoomConfig extends JFinalConfig {
    @Override
    public void configConstant(Constants me) {
        me.setDevMode(true);
        me.setEncoding("UTF-8");
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/", RoomController.class,"/");

    }

    @Override
    public void configEngine(Engine me) {
        me.setDevMode(true);
    }

    @Override
    public void configPlugin(Plugins me) {
        C3p0Plugin cp = new C3p0Plugin("jdbc:sqlite:d:/db.sqlite3","","","org.sqlite.JDBC");
        ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
        arp.setShowSql(true);
        arp.addMapping("empty_room", EmptyRoom.class);
        arp.addMapping("room", Room.class);
        arp.addMapping("time", Time.class);
        arp.setDialect(new Sqlite3Dialect());
        me.add(cp);
        me.add(arp);
        System.out.println("====================success!!");
    }

    @Override
    public void configInterceptor(Interceptors me) {

    }

    @Override
    public void configHandler(Handlers me) {

    }
}
