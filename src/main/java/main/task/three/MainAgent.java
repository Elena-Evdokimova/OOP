package main.task.three;

import dao.AgentDao;
import dao.ConnectionBuilderImpl;
import entity.Agent;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class MainAgent {

    private static AgentDao agentDao;

    public static void main(String[] args) {
        getAll();
        getByAdvertisingDate(LocalDate.of(2020, 10, 20));
        createAgent();
        getAll();
    }

    private static void getAll(){
        System.out.println("All agents");
        agentDao = new AgentDao();
        agentDao.setConnectionBuilder(new ConnectionBuilderImpl());
        List<Agent> agents = agentDao.getAll();
        agents.forEach(System.out::println);
    }

    private static void getByAdvertisingDate(LocalDate date){
        agentDao = new AgentDao();
        agentDao.setConnectionBuilder(new ConnectionBuilderImpl());
        Agent agent = agentDao.getByAdvertisingDate(date);
        System.out.println("Get Agent by dependent advertising date");
        System.out.println(agent);
    }

    private static void createAgent(){
        agentDao = new AgentDao();
        agentDao.setConnectionBuilder(new ConnectionBuilderImpl());
        Agent agent = new Agent();
        agent.setAgentPayment(BigDecimal.valueOf(666));
        agentDao.create(agent);
        System.out.println("Agent created");
    }

}
