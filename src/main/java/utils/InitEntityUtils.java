package utils;

import entity.Advertising;
import entity.Agent;
import entity.Broadcast;
import entity.Customer;
import entity.MarketingStorage;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InitEntityUtils {

    private static final int DEFAULT_CAPACITY = 3;
    private static final String BROADCAST = "broadcast ";
    private static final int DEFAULT_PRIORITY = 10;
    private static final int DEFAULT_COST = 100;
    private static final String CUSTOMER = "customer ";
    private static final String SOME_NUMBER = "some phone number ";
    private static final String SOME_PERSON = "some person ";
    private static final long RESPONSIBLE_AGENT_ID = 1;
    private static final int DEFAULT_PAYMENT = 50;

    public static MarketingStorage initMarketingStorage(){
        List<Advertising> advertising = initAdvertising();
        List<Agent> agents = initAgents(advertising);
        MarketingStorage marketingStorage = new MarketingStorage();
        marketingStorage.setAdvertisingStorage(advertising);
        marketingStorage.setAgents(agents);
        return marketingStorage;
    }

    private static List<Agent> initAgents(List<Advertising> advertising){
        List<Agent> agents = new ArrayList<>();
        for (int i = 0; i < DEFAULT_CAPACITY; ++i){
            Agent agent = new Agent();
            if(i == RESPONSIBLE_AGENT_ID){
                List<Advertising> dependentAdvertising = getDependentAdvertising(advertising);
                agent.setResponsibleAdvertising(dependentAdvertising);
            }
            BigDecimal payment = BigDecimal.valueOf(DEFAULT_PAYMENT * (i + 1));
            agent.setAgentPayment(payment);
            agents.add(agent);
        }
        return agents;
    }

    private static List<Advertising> getDependentAdvertising(List<Advertising> advertising) {
        List<Advertising> resultAdvertising = new ArrayList<>();
        for (Advertising a : advertising) {
            Long id = a.getResponsibleAgentId();
            if (id != null && id == RESPONSIBLE_AGENT_ID) {
                resultAdvertising.add(a);
            }
        }
        return resultAdvertising;
    }

    private static List<Advertising> initAdvertising(){
        List<Advertising> advertising = new ArrayList<>();
        for (int i = 0; i < DEFAULT_CAPACITY; ++i){
            Advertising initAdvertising = initAdvertisingEntity(i);
            Broadcast broadcast = initBroadcast(i);
            Customer customer = initCustomer(i);
            initAdvertising.setCustomer(customer);
            initAdvertising.setBroadcast(broadcast);
            initAdvertising.setResponsibleBroadcastName(broadcast.getBroadcastName());
            initAdvertising.setResponsibleCustomerName(customer.getCustomerName());
            advertising.add(initAdvertising);
        }
        return advertising;
    }

    private static Broadcast initBroadcast(int index){
        Broadcast broadcast = new Broadcast();
        broadcast.setBroadcastName(BROADCAST + index);
        broadcast.setBroadcastPriority(DEFAULT_PRIORITY * (index + 1));
        BigDecimal cost = BigDecimal.valueOf(DEFAULT_COST * (index + 1));
        broadcast.setMinuteCost(cost);
        return broadcast;
    }

    private static Customer initCustomer(int index){
        Customer customer = new Customer();
        customer.setCustomerName(CUSTOMER + index);
        String bankRequisites = String.format("010%d-020%d-030%d-040%x", index, index, index, index);
        customer.setBankRequisites(bankRequisites);
        customer.setCustomerPhoneNumber(SOME_NUMBER + index);
        customer.setContactPerson(SOME_PERSON + index);
        return customer;
    }

    private static Advertising initAdvertisingEntity(int index){
        Advertising advertising = new Advertising();
        advertising.setAdvertisingId((long) index);
        if(index % 2 == 0) {
            advertising.setResponsibleAgentId(RESPONSIBLE_AGENT_ID);
        }
        advertising.setAdvertisingDate(LocalDate.now());
        advertising.setAdvertisingDuration(index + 1);
        return advertising;
    }

}
