package main.task.three;

import dao.AdvertisingDao;
import dao.ConnectionBuilderImpl;
import entity.Advertising;

import java.time.LocalDate;
import java.util.List;

public class MainAdvertising {

    private static final long AGENT_ID = 2;
    private static final String BROADCAST_ONE = "Hot news";
    private static final String BROADCAST_TWO = "The Truman Show";
    private static final String CUSTOMER = "some";
    private static final String C_NAME = "Z-corp";
    private static final String B_NAME = "Lazy Town";


    private static AdvertisingDao advertisingDao;

    public static void main(String[] args) {
        getAll();
        getByAgentId();
        getByBroadcast(BROADCAST_ONE);
        getByBroadcast(BROADCAST_TWO);
        getByCustomer();
        createAdvertising();
        getAll();
    }

    private static void createAdvertising(){
        System.out.println("Creating new advertising");
        advertisingDao.setConnectionBuilder(new ConnectionBuilderImpl());
        Advertising advertising = new Advertising();
        advertising.setResponsibleBroadcastName(B_NAME);
        advertising.setResponsibleCustomerName(C_NAME);
        advertising.setAdvertisingDate(LocalDate.now());
        advertising.setAdvertisingDuration(1);
        advertisingDao.create(advertising);
    }

    private static void getAll(){
        advertisingDao = new AdvertisingDao();
        advertisingDao.setConnectionBuilder(new ConnectionBuilderImpl());
        List<Advertising> advertising = advertisingDao.getAll();
        advertising.forEach(System.out::println);
    }

    private static void getByCustomer(){
        System.out.println("Get advertising with customer: " + CUSTOMER);
        advertisingDao.setConnectionBuilder(new ConnectionBuilderImpl());
        List<Advertising> advertising = advertisingDao.getByCustomer(CUSTOMER);
        advertising.forEach(System.out::println);
    }

    private static void getByAgentId(){
        System.out.println("Make join advertising to agent and get dependent advertising with agent_id: " + AGENT_ID);
        advertisingDao.setConnectionBuilder(new ConnectionBuilderImpl());
        Advertising advertising = advertisingDao.getByAgentId(AGENT_ID);
        System.out.println(advertising);
    }

    private static void getByBroadcast(String name){
        System.out.println("Get advertising connected with broadcast: " + name);
        advertisingDao.setConnectionBuilder(new ConnectionBuilderImpl());
        List<Advertising> advertising = advertisingDao.getByBroadcast(name);
        advertising.forEach(System.out::println);
    }

}
