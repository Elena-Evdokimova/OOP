package dao;

import entity.Advertising;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdvertisingDao {

    private static final String CREATE = "insert into advertising(r_broadcast_name, r_customer_name, advertising_date, advertising_duration) values (?, ?, ?, ?);";
    private static final String GET_ALL = "select * from advertising;";
    private static final String GET_BY_BROAD_CAST = "select * from advertising where r_broadcast_name = ?;";
    private static final String GET_BY_CUSTOMER = "select * from advertising where r_customer_name = ?;";
    //requires agent_id
    private static final String GET_BY_AGENT = "select distinct advertising_id, r_broadcast_name, r_customer_name, advertising_date, advertising_duration " +
            "from agent inner join agent_adv_connection on ? = agent_adv_connection.agent_to_adv\n" +
            "inner join advertising on agent_adv_connection.adv_to_agent = advertising.advertising_id;\n";

    private static final String ID = "advertising_id";
    private static final String RESPONSIBLE_BROADCAST = "r_broadcast_name";
    private static final String RESPONSIBLE_CUSTOMER = "r_customer_name";
    private static final String DATE = "advertising_date";
    private static final String DURATION = "advertising_duration";

    private ConnectionBuilder connectionBuilder;

    public void setConnectionBuilder(ConnectionBuilder connectionBuilder) {
        this.connectionBuilder = connectionBuilder;
    }

    private Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }

    public List<Advertising> getByCustomer(String customerName){
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_CUSTOMER)){
            preparedStatement.setString(1, customerName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                List<Advertising> resultAdvertising = new ArrayList<>();
                do {
                    Advertising advertising = new Advertising();
                    advertising.setAdvertisingId(resultSet.getLong(ID));
                    advertising.setResponsibleCustomerName(resultSet.getString(RESPONSIBLE_CUSTOMER));
                    advertising.setResponsibleBroadcastName(resultSet.getString(RESPONSIBLE_BROADCAST));
                    advertising.setAdvertisingDate(resultSet.getDate(DATE).toLocalDate());
                    advertising.setAdvertisingDuration(resultSet.getInt(DURATION));
                    resultAdvertising.add(advertising);
                }while (resultSet.next());
                return resultAdvertising;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<Advertising> getByBroadcast(String broadcastName){
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_BROAD_CAST)){
            preparedStatement.setString(1, broadcastName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                List<Advertising> resultAdvertising = new ArrayList<>();
                do {
                    Advertising advertising = new Advertising();
                    advertising.setAdvertisingId(resultSet.getLong(ID));
                    advertising.setResponsibleCustomerName(resultSet.getString(RESPONSIBLE_CUSTOMER));
                    advertising.setResponsibleBroadcastName(resultSet.getString(RESPONSIBLE_BROADCAST));
                    advertising.setAdvertisingDate(resultSet.getDate(DATE).toLocalDate());
                    advertising.setAdvertisingDuration(resultSet.getInt(DURATION));
                    resultAdvertising.add(advertising);
                }while (resultSet.next());
                return resultAdvertising;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    public Advertising getByAgentId(Long id){
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_AGENT)){
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Advertising advertising = new Advertising();
                advertising.setAdvertisingId(resultSet.getLong(ID));
                advertising.setResponsibleCustomerName(resultSet.getString(RESPONSIBLE_CUSTOMER));
                advertising.setResponsibleBroadcastName(resultSet.getString(RESPONSIBLE_BROADCAST));
                advertising.setAdvertisingDate(resultSet.getDate(DATE).toLocalDate());
                advertising.setAdvertisingDuration(resultSet.getInt(DURATION));
                advertising.setResponsibleAgentId(id);
                return advertising;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new Advertising();
    }

    public List<Advertising> getAll(){
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                List<Advertising> advertising = new ArrayList<>();
                do {
                    Advertising advertisingEntity = new Advertising();
                    advertisingEntity.setAdvertisingId(resultSet.getLong(ID));
                    advertisingEntity.setResponsibleCustomerName(resultSet.getString(RESPONSIBLE_CUSTOMER));
                    advertisingEntity.setResponsibleBroadcastName(resultSet.getString(RESPONSIBLE_BROADCAST));
                    advertisingEntity.setAdvertisingDate(resultSet.getDate(DATE).toLocalDate());
                    advertisingEntity.setAdvertisingDuration(resultSet.getInt(DURATION));
                    advertising.add(advertisingEntity);
                }while (resultSet.next());
                return advertising;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    public int create(Advertising advertising){
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE)){
            int counter = 1;
            preparedStatement.setString(counter++, advertising.getResponsibleBroadcastName());
            preparedStatement.setString(counter++, advertising.getResponsibleCustomerName());
            preparedStatement.setDate(counter++, Date.valueOf(advertising.getAdvertisingDate()));
            preparedStatement.setInt(counter, advertising.getAdvertisingDuration());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

}
