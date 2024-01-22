package com.limrainfracon.primaykey.generator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectPrimaryKeyGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        Connection connection = session.connection();

        try {
            // Retrieve the next primary key value from your database sequence or any other mechanism
            Long nextPrimaryKeyValue = getNextPrimaryKeyValueFromDatabase(connection);

            // Format the primary key value as "PRJ000001"
            String formattedPrimaryKey = String.format("PRJ%06d", nextPrimaryKeyValue);

            return formattedPrimaryKey;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to generate custom primary key", e);
        }
    }

    // Implement your logic to retrieve the next primary key value from the database
    private Long getNextPrimaryKeyValueFromDatabase(Connection connection) throws SQLException {
        // Query the database for the maximum primary key value
        String query = "SELECT MAX(CAST(SUBSTRING(project_id, 4) AS UNSIGNED)) FROM project";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        Long maxPrimaryKeyValue = 0L;

        if (resultSet.next()) {
            maxPrimaryKeyValue = resultSet.getLong(1);
        }

        // Increment the maximum primary key value by 1
        Long nextPrimaryKeyValue = maxPrimaryKeyValue + 1;

        return nextPrimaryKeyValue;
    }
}
