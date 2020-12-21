package controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RoomController extends Controller{
    public void index(){
        render("index.html");
    }
    public void query(){
        int week_day = getParaToInt("week_day");
        int day_time = getParaToInt("day_time");
        String building = getPara("building");
        String level = getPara("level");
        String direction = getPara("direction");
        System.out.println("level:"+level+"=====direction"+direction);
        List<Record> re = Service.decide(week_day,day_time,building,level);
        System.out.println(re);
        if(re== null) {
            render("index.html");
            return;
        }
        String date =null;
        if (week_day!=0 && day_time!=0){
            date = "星期"+String.valueOf(week_day)+"第"+String.valueOf(day_time)+"大节";
        }else {
            date="当前";
        }
        if (building!=null && !building.equals("")) date=date+building+"楼";
        if (direction!=null && !direction.equals("")) date=date+direction;
        if (level!=null && !level.equals("")) date=date+level+"层";
        int i;
        String p1 = ".*A.*";
        String p3 = ".*C.*";
        String p4 = ".*D.*";
        ArrayList a = new ArrayList();
        ArrayList b = new ArrayList();
        ArrayList c = new ArrayList();
        ArrayList d = new ArrayList();
        for(i=0;i<re.size();i++){
            String room = re.get(i).getStr("room").toString();
            boolean isMatchA = Pattern.matches(p1, room);
            boolean isMatchC = Pattern.matches(p3, room);
            boolean isMatchD = Pattern.matches(p4, room);
//            System.out.println(isMatchA);
            if (isMatchA) a.add(room);
            else if (isMatchD) d.add(room);
            else if (isMatchC) c.add(room);
            else b.add(room);
        }
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
        setAttr("lista",a);
        setAttr("listb",b);
        setAttr("listc",c);
        setAttr("listd",d);

        setAttr("date",date);
        setAttr("week_day",week_day);
        setAttr("day_time",day_time);
        render("result.html");
    }
}
