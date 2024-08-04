package hexlet.code.repository;

import hexlet.code.model.UrlCheck;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UrlCheckRepository extends BaseRepository {

    public static void save(UrlCheck urlCheck) throws SQLException {
        var sql = "INSERT INTO url_checks (url_id, status_code, title, h1, description, created_at)"
                    + "VALUES (?, ?, ?, ?, ?, ?)";
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, urlCheck.getUrlId());
            preparedStatement.setInt(2, urlCheck.getStatusCode());
            preparedStatement.setString(3, urlCheck.getTitle());
            preparedStatement.setString(4, urlCheck.getH1());
            preparedStatement.setString(5, urlCheck.getDescription());
            preparedStatement.setTimestamp(6, urlCheck.getCreatedAt());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                urlCheck.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public static Optional<UrlCheck> findLast(Long urlId) throws SQLException {
        var sql = "SELECT * FROM urls WHERE url_id = ? ORDER BY created_at DESC LIMIT 1";
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, urlId);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createFromDB(resultSet));
            }
            return Optional.empty();
        }
    }

    public static List<UrlCheck> getEntities(Long urlId) throws SQLException {
        var sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY created_at DESC";
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, urlId);
            var resultSet = preparedStatement.executeQuery();
            var result = new ArrayList<UrlCheck>();
            while (resultSet.next()) {
                result.add(createFromDB(resultSet));
            }
            return result;
        }
    }

    private static UrlCheck createFromDB(ResultSet resultSet) throws SQLException {
        var id = resultSet.getLong("id");
        var statusCode = resultSet.getInt("status_code");
        var title = resultSet.getString("title");
        var h1 = resultSet.getString("h1");
        var description = resultSet.getString("description");
        var urlId = resultSet.getLong("url_id");
        var createdAt = resultSet.getTimestamp("created_at");
        var urlCheck = new UrlCheck(statusCode, title, h1, description, urlId, createdAt);
        urlCheck.setId(id);
        return urlCheck;
    }
}
