import java.sql.*;

public class DBConnection {
    private static final int QUERY_SIZE = 2_500_000;

    private static Connection connection;

    private static String dbName = "learn";
    private static String dbUser = "root";
    private static String dbPass = "qwerty123456";

    private static StringBuilder insertQuery;

    static {
        insertQuery = new StringBuilder();
    }


    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/" + dbName +
                                "?user=" + dbUser + "&password=" + dbPass +
                                "&useUnicode=true&useJDBCCompliantTimezoneShift=true" +
                                "&useLegacyDatetimeCode=false&serverTimezone=UTC");
                connection.createStatement().execute("DROP TABLE IF EXISTS voter_count, station_work_time");
                connection.createStatement().execute("CREATE TABLE voter_count(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "name TINYTEXT NOT NULL, " +
                        "birthDate DATE NOT NULL, " +
                        "`count` INT NOT NULL, " +
                        "PRIMARY KEY(id)," +
                        "UNIQUE KEY name_date(name(50), birthDate)" +
                        ")");
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void countVoter(String name, String birthDay) throws SQLException {
        birthDay = birthDay.replace('.', '-');
        if (insertQuery.length() < QUERY_SIZE) {
            insertQuery
                    .append(insertQuery.length() == 0 ? "INSERT INTO voter_count(name, birthDate, `count`) VALUES('" : ",('")
                    .append(name)
                    .append("', '")
                    .append(birthDay)
                    .append("', 1)");
        } else {
            executeMultiQuery();
        }
    }

    public static void executeMultiQuery() throws SQLException {
        insertQuery.append("ON DUPLICATE KEY UPDATE `count` = `count` + 1");
        DBConnection.getConnection().createStatement().execute(insertQuery.toString());
        DBConnection.getConnection().commit();
        insertQuery = new StringBuilder();
    }

    public static void printVoterCounts() throws SQLException {
        String sql = "SELECT name, birthDate, `count` FROM voter_count WHERE `count` > 1";
        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
        int i = 0;
        while (rs.next()) {
            System.out.println("\t" + (++i)+ " " + rs.getString("name") + " (" +
                    rs.getString("birthDate") + ") - " + rs.getInt("count"));
        }
        rs.close();
    }
}
