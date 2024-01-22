package com.limrainfracon.primaykey.generator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class TransportClientsPKGenerator implements IdentifierGenerator {

	 @Override
	    public Serializable generate(SharedSessionContractImplementor session, Object object) {
	        Connection connection = session.connection();
	        try {
	            Long nextPrimaryKeyValue = getNextPrimaryKeyValueFromDatabase(connection);
	            String formattedPrimaryKey = String.format("TRC%06d", nextPrimaryKeyValue);
	            return formattedPrimaryKey;
	        } catch (SQLException e) {
	            throw new RuntimeException("Failed to generate custom primary key", e);
	        }
	    }

	    private Long getNextPrimaryKeyValueFromDatabase(Connection connection) throws SQLException {
	        String query = "SELECT MAX(CAST(SUBSTRING(trans_client_id, 4) AS UNSIGNED)) FROM transport_clients";
	        PreparedStatement statement = connection.prepareStatement(query);
	        ResultSet resultSet = statement.executeQuery();
	        Long maxPrimaryKeyValue = 0L;
	        if (resultSet.next()) {
	            maxPrimaryKeyValue = resultSet.getLong(1);
	        }
	        Long nextPrimaryKeyValue = maxPrimaryKeyValue + 1;
	        return nextPrimaryKeyValue;
	    }
}
