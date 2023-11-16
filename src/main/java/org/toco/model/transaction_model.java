package org.toco.model;
import java.sql.*;

import org.toco.core.connector;
import org.toco.entity.transaction_entity;

public class transaction_model {
//    insert a transaction
    public void insert (transaction_entity transaction_entity) {
        String sqltrans = "INSERT INTO transaction (user_id, amount, image, status) VALUES (?, ?, ?, ?)";
        try (Connection connection = connector.connect() ;
             PreparedStatement command = connection.prepareStatement(sqltrans)) {
            command.setInt(1, transaction_entity.getUser_id());
            command.setInt(2, transaction_entity.getAmount());
            command.setString(3, transaction_entity.getImage());
            command.setString(4, transaction_entity.getStatus());
            command.execute();
        } catch (SQLException exception) {
            throw new RuntimeException("Error when inserting", exception);
        }
    }
//    GET TRANSACTION a user does return in transaction_entity array
    public transaction_entity[] getTransaction(Integer user_id){
        String sql = "select * from transaction where user_id = (?) order by tid desc";
        try (Connection connection = connector.connect() ;
             PreparedStatement command = connection.prepareStatement(sql)) {
            command.setInt(1, user_id);
            command.execute();
            ResultSet rs = command.getResultSet();
            transaction_entity[] transactions = new transaction_entity[getTransactionCount(user_id)];
            int i = 0;
            while(rs.next()){
                transactions[i] = new transaction_entity(rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6));
                i++;
            }
            return transactions;
        } catch (SQLException exception) {
            throw new RuntimeException("Error when getting transaction", exception);
        }
    }

//    get transaction count by a user
    public Integer getTransactionCount(Integer user_id){
        String sql = "select count(*) from transaction where user_id = (?)";
        try (Connection connection = connector.connect() ;
             PreparedStatement command = connection.prepareStatement(sql)) {
            command.setInt(1, user_id);
            command.execute();
            ResultSet rs = command.getResultSet();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException exception) {
            throw new RuntimeException("Error when getting transaction", exception);
        }
    }

}
