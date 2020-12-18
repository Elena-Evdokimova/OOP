package dao;

import entity.Agent;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AgentDao {

    private static final String CREATE = "insert into agent(agent_payment) values (?);";
    private static final String GET_ALL = "select * from agent;";
    //requires advertising_date
    private static final String GET_BY_ADVERTISING_DATE = "select agent_id, agent_payment from agent " +
            "inner join agent_adv_connection on agent_id = agent_to_adv " +
            "inner join advertising on adv_to_agent = advertising_id where advertising_date = ?;";
    //requires agent_payment and advertising_date
    private static final String CREATE_AND_CONNECT_TO_ADVERTISING = "with id_table as(\n" +
            "\tinsert into agent(agent_payment) values (?) returning agent_id\n" +
            ")\n" +
            "insert into agent_adv_connection select agent_id, advertising_id " +
            "from id_table, advertising where advertising_date = ?;";

    private static final String ID = "agent_id";
    private static final String PAYMENT = "agent_payment";

    private ConnectionBuilder connectionBuilder;

    public void setConnectionBuilder(ConnectionBuilder connectionBuilder) {
        this.connectionBuilder = connectionBuilder;
    }

    private Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }

    /**
     * with just payment
     * @param payment agent_payment
     * @param advertisingDate connected advertising date
     * @return row count
     */
    public int createAndConnectToAdvertisingByDate(BigDecimal payment, LocalDate advertisingDate){
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_AND_CONNECT_TO_ADVERTISING)){
            preparedStatement.setBigDecimal(1, payment);
            preparedStatement.setDate(2, Date.valueOf(advertisingDate));
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    /**
     * with agent entity
     * @param agent agent_payment
     * @param advertisingDate connected advertising date
     * @return row count
     */
    public int createAndConnectToAdvertisingByDate(Agent agent, LocalDate advertisingDate){
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_AND_CONNECT_TO_ADVERTISING)){
            preparedStatement.setBigDecimal(1, agent.getAgentPayment());
            preparedStatement.setDate(2, Date.valueOf(advertisingDate));
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public List<Agent> getAll(){
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                List<Agent> agents = new ArrayList<>();
                do {
                    Agent agent = new Agent();
                    agent.setAgentId(resultSet.getLong(ID));
                    agent.setAgentPayment(resultSet.getBigDecimal(PAYMENT));
                    agents.add(agent);
                }while (resultSet.next());
                return agents;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * without advertising connection
     * @param agent entity
     * @return row count
     */
    public int create(Agent agent){
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE)){
            preparedStatement.setBigDecimal(1, agent.getAgentPayment());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public Agent getByAdvertisingDate(LocalDate advertisingDate){
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ADVERTISING_DATE)){
            preparedStatement.setDate(1, Date.valueOf(advertisingDate));
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Agent agent = new Agent();
                agent.setAgentId(resultSet.getLong(ID));
                agent.setAgentPayment(resultSet.getBigDecimal(PAYMENT));
                return agent;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new Agent();
    }

}
