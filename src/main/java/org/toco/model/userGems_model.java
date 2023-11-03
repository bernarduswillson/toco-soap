package org.toco.model;
import java.sql.*;

import org.toco.core.connector;
import org.toco.entity.userGems_Entity;
public class userGems_model {
    public void insert (userGems_Entity userGems) {
        String sql = "INSERT INTO userGems (user_id, gem) VALUES (?, ?)";
        try (Connection connection = connector.connect() ;
             PreparedStatement command = connection.prepareStatement(sql)) {
            command.setInt(1, userGems.getUser_id());
            command.setInt(2, userGems.getGem());
            command.execute();
        } catch (SQLException exception) {
            throw new RuntimeException("Error when inserting", exception);
        }
    }
    public void update (userGems_Entity userGems){
        String sql = "update userGems set gem = (?) where user_id = (?)";
        try (Connection connection = connector.connect() ;
             PreparedStatement command = connection.prepareStatement(sql)) {
            command.setInt(1, userGems.getGem());
            command.setInt(2, userGems.getUser_id());
            command.execute();
        } catch (SQLException exception) {
            throw new RuntimeException("Error when updating", exception);
        }

    }
    public Integer getUserGems(Integer user_id){
        String sql = "select gem from userGems where user_id = (?)";
        try (Connection connection = connector.connect() ;
             PreparedStatement command = connection.prepareStatement(sql)) {
            command.setInt(1, user_id);
            command.execute();
            ResultSet rs = command.getResultSet();
            if (rs.next()) {
                return rs.getInt(1);
            }
            else{
                return 0;
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Error when getting gem", exception);
        }
    }
//    create a function to check if a user is on the userGems table or not return boolean
    public boolean checkUser(Integer user_id){
        String sql = "select * from userGems where user_id = (?)";
        try (Connection connection = connector.connect() ;
             PreparedStatement command = connection.prepareStatement(sql)) {
            command.setInt(1, user_id);
            command.execute();
            ResultSet rs = command.getResultSet();
            if(rs.next()){
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Error when checking", exception);
        }
    }
}
