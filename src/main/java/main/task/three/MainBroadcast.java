package main.task.three;

import dao.BroadcastDao;
import dao.ConnectionBuilderImpl;
import entity.Broadcast;

import java.math.BigDecimal;
import java.util.List;

public class MainBroadcast {

    private static final String NAME = "The Truman Show";
    private static final String NEW_NAME = "Lazy Town";

    private static BroadcastDao broadcastDao;

    public static void main(String[] args) {
        getAll();
        getByName();
        createBroadcast();
        getAll();
    }

    private static void getAll(){
        broadcastDao = new BroadcastDao();
        broadcastDao.setConnectionBuilder(new ConnectionBuilderImpl());
        List<Broadcast> broadcasts = broadcastDao.getAll();
        broadcasts.forEach(System.out::println);
    }

    private static void getByName(){
        System.out.println("Get " + NAME + " broadcast");
        broadcastDao.setConnectionBuilder(new ConnectionBuilderImpl());
        Broadcast broadcast = broadcastDao.getByName(NAME);
        System.out.println(broadcast);
    }

    private static void createBroadcast(){
        System.out.println("creating " + NEW_NAME);
        broadcastDao.setConnectionBuilder(new ConnectionBuilderImpl());
        Broadcast broadcast = new Broadcast();
        broadcast.setBroadcastName(NEW_NAME);
        broadcast.setMinuteCost(BigDecimal.valueOf(50.99));
        broadcast.setBroadcastPriority(40);
        broadcastDao.create(broadcast);
    }

}
