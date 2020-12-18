package dao;

import entity.Broadcast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BroadcastDao {

    private static final String CREATE = "insert into broadcast(broadcast_name, broadcast_priority, minute_cost) values (?, ?, ?);";
    private static final String DELETE_BY_NAME = "delete from broadcast where broadcast_name = ?;";
    private static final String GET_BY_NAME = "select * from broadcast where broadcast_name = ?;";
    private static final String GET_ALL = "select * from broadcast;";

    private static final String NAME = "broadcast_name";
    private static final String PRIORITY = "broadcast_priority";
    private static final String COST = "minute_cost";

    private ConnectionBuilder connectionBuilder;

    public void setConnectionBuilder(ConnectionBuilder connectionBuilder) {
        this.connectionBuilder = connectionBuilder;
    }

    private Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }

    public List<Broadcast> getAll(){
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                List<Broadcast> broadcasts = new ArrayList<>();
                do {
                    Broadcast broadcast = new Broadcast();
                    broadcast.setBroadcastName(resultSet.getString(NAME));
                    broadcast.setBroadcastPriority(resultSet.getInt(PRIORITY));
                    broadcast.setMinuteCost(resultSet.getBigDecimal(COST));
                    broadcasts.add(broadcast);
                }while (resultSet.next());
                return broadcasts;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    public Broadcast getByName(String name){
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_NAME)){
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Broadcast broadcast = new Broadcast();
                broadcast.setBroadcastName(resultSet.getString(NAME));
                broadcast.setBroadcastPriority(resultSet.getInt(PRIORITY));
                broadcast.setMinuteCost(resultSet.getBigDecimal(COST));
                return broadcast;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new Broadcast();
    }

    public int delete(String name){
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_NAME)){
            preparedStatement.setString(1, name);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public int create(Broadcast broadcast){
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE)){
            int counter = 1;
            preparedStatement.setString(counter++, broadcast.getBroadcastName());
            preparedStatement.setInt(counter++, broadcast.getBroadcastPriority());
            preparedStatement.setBigDecimal(counter, broadcast.getMinuteCost());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

}
