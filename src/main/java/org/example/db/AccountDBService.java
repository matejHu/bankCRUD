package org.example.db;

import org.example.Entities.Account;
import org.example.Entities.TransactionType;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.slf4j.LoggerFactory.getLogger;

public class AccountDBService {
    private static final String READ_ACCOUNT_BY_ID = "SELECT id, name, balance FROM account WHERE id = ?";
    private static final String UPDATE_ACCOUNT_BALANCE_PLUS = "UPDATE account SET balance = balance + ? WHERE id = ?";
    private static final String UPDATE_ACCOUNT_BALANCE_MINUS = "UPDATE account SET balance = balance - ? WHERE id = ?";
    private static final Logger logger = getLogger(AccountDBService.class);

    public Account readAccountById(int accountId) {
        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_ACCOUNT_BY_ID)) {

            statement.setInt(1, accountId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Account(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("balance")
                );
            } else {
                logger.warn("No account found with id " + accountId);
                return null;
            }
        } catch (SQLException e) {
            logger.error("Error while reading account with id " + accountId, e);
            return null;
        }
    }

    public Account updateAccountById(int accountId, int amount, TransactionType type) {
        String updateQuery = UPDATE_ACCOUNT_BALANCE_PLUS;
        if (type == TransactionType.WITHDRAWAL) {
            updateQuery = UPDATE_ACCOUNT_BALANCE_MINUS;
        }

        try (Connection connection = HikariCPDataSource.getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
             PreparedStatement selectStatement = connection.prepareStatement("SELECT id, name, balance FROM account WHERE id = ?")) {

            selectStatement.setInt(1, accountId);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                int currentBalance = resultSet.getInt("balance");
                if (type == TransactionType.WITHDRAWAL && currentBalance < amount) {
                    logger.warn("Insufficient funds for account id " + accountId);
                    return null;
                }

                updateStatement.setInt(1, amount);
                updateStatement.setInt(2, accountId);
                int rowsUpdated = updateStatement.executeUpdate();

                if (rowsUpdated == 1) {
                    resultSet = selectStatement.executeQuery();
                    if (resultSet.next()) {
                        return new Account(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getInt("balance")
                        );
                    } else {
                        logger.warn("No account found with id " + accountId);
                        return null;
                    }
                } else {
                    logger.warn("No account found with id " + accountId);
                    return null;
                }
            } else {
                logger.warn("No account found with id " + accountId);
                return null;
            }
        } catch (SQLException e) {
            logger.error("Error while updating account with id " + accountId, e);
            return null;
        }
    }
}
