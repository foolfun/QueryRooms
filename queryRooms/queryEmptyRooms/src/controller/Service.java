package controller;


import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Service {
    public static List<Record> decide(int week_day,int day_time,String building,String level) {
        if (week_day==0 && day_time==0 && level==null)
            return querybyNow();//返回当前时刻的空教室
//        else if (week_day==0 || day_time==0 && (level==null || level.equals("")))
//            return null;
        if(level!=null)
            return queryTimeBuilding(week_day,day_time,building,level);
            //返回给定时间，给定楼,层的空教室，tips:楼或层可以为空；若时间没给定，则使用当前时刻
        else
            return querybyTime(week_day,day_time);//返回给定时间的空教室

    }

    public static int getT(LocalTime time, int i) {
        LocalTime times[] =new LocalTime[6];
        times[0] = LocalTime.of(8, 0, 0);
        times[1] =LocalTime.of(10, 10, 0);
        times[2] =LocalTime.of(14, 0, 0);
        times[3] =LocalTime.of(16, 0, 0);
        times[4] =LocalTime.of(18, 30, 0);
        times[5] =LocalTime.of(23, 59, 0);
        if (time.isBefore(times[i])){
            return i;
        }else{
            i++;
            return getT(time,i);
        }
    }

    //辅助的sql代码
    public static List<Record> hasTimeSql(int week_day,int day_time){
        List<Record> re = Db.find("select room from room r,time t,empty_room e where\n" +
                "r.id=e.room_id and t.id=e.time_id and week_day=? and day_time=? order by room",week_day,day_time);
        return re;
    }

    public static List<Record> querybyNow(){
        LocalTime time = LocalTime.now().withNano(0);
        int day_time = getT(time,0);
        System.out.println("day_time="+day_time);
        int week_day=  LocalDate.now().getDayOfWeek().getValue();
        System.out.println("week_day="+week_day+"==========querybyNow");
        return hasTimeSql(week_day,day_time);
    }

    public static List<Record> querybyTime(int week_day,int day_time){
        System.out.println("querybyTime");
        return hasTimeSql(week_day,day_time);
    }

    public static List<Record> queryTimeBuilding(int week_day,int day_time,String building,String level){
        if (week_day==0 && day_time==0){
            LocalTime time = LocalTime.now().withNano(0);
            day_time = getT(time,0);
            System.out.println("day_time="+day_time);
            week_day=  LocalDate.now().getDayOfWeek().getValue();
            System.out.println("week_day="+week_day+"====================queryTimeBuilding");
        }
        List<Record> re =null;
        if (building==null) building="";
        if (building.equals("B东")) building="东";
        if (building.equals("B西")) building="西";
        if (!building.equals("")) {
            re = Db.find("select room from room r, time t, empty_room e where\n"+
                    "r.id=e.room_id and t.id=e.time_id and week_day=? and day_time=? \n" +
                    "and room like ? order by room",week_day,day_time,"%"+building+level+"%");
        }else {
            re =Db.find("select room from room r, time t, empty_room e where\n"+
                    "r.id=e.room_id and t.id=e.time_id and week_day=? and day_time=? \n" +
                    "and room like ? order by room",week_day,day_time,"%"+level+"0"+"%");
        }
        return re;
    }

}