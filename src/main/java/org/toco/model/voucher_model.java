package org.toco.model;

import java.sql.*;

import org.toco.core.connector;
import org.toco.entity.voucher_entity;

public class voucher_model {
    public void insert(voucher_entity voucher_entity) {
        String sql = "INSERT INTO voucher_record (code, user_id, amount) VALUES (?, ?, ?)";
        try (Connection connection = connector.connect();
                PreparedStatement command = connection.prepareStatement(sql)) {
            command.setString(1, voucher_entity.getCode());
            command.setInt(2, voucher_entity.getUser_id());
            command.setInt(3, voucher_entity.getAmount());
            command.execute();
        } catch (SQLException exception) {
            throw new RuntimeException("Error when inserting", exception);
        }
    }

    public voucher_entity[] getAllVouchers() {
        String sql = "SELECT * FROM voucher_record";
        try (Connection connection = connector.connect();
                PreparedStatement command = connection.prepareStatement(sql)) {
            ResultSet result = command.executeQuery();
            voucher_entity[] voucher_entity = new voucher_entity[getAllCount()];
            int i = 0;
            while (result.next()) {
                voucher_entity[i] = new voucher_entity(result.getString("code"), result.getInt("user_id"),
                        result.getInt("amount"), result.getString("timestamp"));
                i++;
            }
            return voucher_entity;
        } catch (SQLException exception) {
            throw new RuntimeException("Error when getting", exception);
        }
    }

    public voucher_entity[] getSpecifiedVoucher(String code) {
        String sql = "SELECT * FROM voucher_record WHERE code = ?";
        try (Connection connection = connector.connect();
                PreparedStatement command = connection.prepareStatement(sql)) {
            command.setString(1, code);
            ResultSet result = command.executeQuery();
            voucher_entity[] voucher_entity = new voucher_entity[getSpecifiedCount(code)];
            int i = 0;
            while (result.next()) {
                voucher_entity[i] = new voucher_entity(result.getString("code"), result.getInt("user_id"),
                        result.getInt("amount"), result.getString("timestamp"));
                i++;
            }
            return voucher_entity;
        } catch (SQLException exception) {
            throw new RuntimeException("Error when getting", exception);
        }
    }

    public Integer getSpecifiedCount(String code) {
        String sql = "SELECT COUNT(*) FROM voucher_record WHERE code = ?";
        try (Connection connection = connector.connect();
                PreparedStatement command = connection.prepareStatement(sql)) {
            command.setString(1, code);
            ResultSet result = command.executeQuery();
            if (result.next()) {
                return result.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Error when counting", exception);
        }
    }

    public Integer getAllCount() {
        String sql = "SELECT COUNT(*) FROM voucher_record";
        try (Connection connection = connector.connect();
                PreparedStatement command = connection.prepareStatement(sql)) {
            ResultSet result = command.executeQuery();
            if (result.next()) {
                return result.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Error when counting", exception);
        }
    }

    public boolean isVoucherAlreadyRedeemed(String voucherCode, int userId) {
        String sql = "SELECT COUNT(*) FROM voucher_record WHERE code = ? AND user_id = ?";
        try (Connection connection = connector.connect();
                PreparedStatement command = connection.prepareStatement(sql)) {
            command.setString(1, voucherCode);
            command.setInt(2, userId);
            ResultSet result = command.executeQuery();
            if (result.next()) {
                return result.getInt(1) > 0;
            } else {
                return false;
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Error when checking voucher redemption", exception);
        }
    }

}
