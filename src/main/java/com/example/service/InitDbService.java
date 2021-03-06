package com.example.service;

import com.creang.puddle.MiniConnectionPoolManager;
import com.creang.puddle.Util;
import com.example.db.ConnectionPoolHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class InitDbService {

    private final static Logger LOGGER = Logger.getLogger(InitDbService.class.getName());
    private ConnectionPoolHelper connectionPoolHelper = ConnectionPoolHelper.getInstance();
    private MiniConnectionPoolManager poolManager = connectionPoolHelper.getMiniConnectionPoolManager();

    public void initDb() {

        Connection conn = null;
        Statement stmnt = null;
        PreparedStatement prepStmntRace = null;
        PreparedStatement prepStmntParticipant = null;
        ResultSet rs = null;

        try {

            conn = poolManager.getValidConnection(3);
            stmnt = conn.createStatement();

            rs = stmnt.executeQuery("select count(0) from sqlite_master where type='table' and name='racecard'");

            if (rs.getInt(1) == 0) { //table does not exist, so create and populate it.

                stmnt.executeUpdate("create table racecard(Id integer primary key autoincrement not null, SortOrder integer not null, EventDesc text not null, unique(SortOrder))");
                stmnt.executeUpdate("create table if not exists race(Id integer primary key autoincrement not null, HeatNumber integer not null, RaceCardId integer, foreign key (RaceCardId) references RaceCard(Id))");
                stmnt.executeUpdate("create table if not exists participant(Id integer primary key autoincrement not null, Lane integer not null, StartNumber integer not null, FirstName text not null, LastName text not null, Country text not null check(length(Country) = 3), SB2017 real not null, PB real not null, RaceId integer, unique(StartNumber), foreign key (RaceId) references Race(Id))");

                //insert some data
                List<Integer> raceCardIds = new ArrayList<>();
                stmnt.executeUpdate("insert into racecard(SortOrder, EventDesc) values (2, 'Semi-Final 100 Meters Men')");
                raceCardIds.add(stmnt.getGeneratedKeys().getInt(1));
                stmnt.executeUpdate("insert into racecard(SortOrder, EventDesc) values (1, 'Semi-Final 100 Meters Women')");
                raceCardIds.add(stmnt.getGeneratedKeys().getInt(1));

                prepStmntRace = conn.prepareStatement("insert into race(HeatNumber, RaceCardId) values (?,?)");
                prepStmntParticipant = conn.prepareStatement("insert into participant (Lane, StartNumber, FirstName, LastName, Country, SB2017, PB, RaceId) values (?,?,?,?,?,?,?,?)");

                int raceCounter = 1;
                for (Integer raceCardId : raceCardIds) {
                    for (int heatNumber = 1; heatNumber <= 3; heatNumber++) {

                        prepStmntRace.setInt(1, heatNumber);
                        prepStmntRace.setInt(2, raceCardId);
                        prepStmntRace.executeUpdate();
                        int raceId = stmnt.getGeneratedKeys().getInt(1);

                        if (raceCounter == 1) {
                            populateStatementAndAddToBatch(prepStmntParticipant, 2, 1281, "Akani", "Simbine", "RSA", 9.92, 9.89, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 3, 1103, "Aska", "Cambridge", "JPN", 10.08, 10.08, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 4, 953, "James", "Dasaolu", "GBR", 10.06, 9.91, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 5, 1083, "Julian", "Forte", "JAM", 9.99, 9.99, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 6, 1395, "Justin", "Gatlin", "USA", 9.95, 9.74, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 7, 790, "Ben Youssef", "Meité", "CIV", 9.98, 9.96, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 8, 1153, "Kukyoung", "Kim", "KOR", 10.07, 10.07, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 9, 784, "Zhenye", "Xie", "CHN", 10.09, 10.08, raceId);
                        }
                        if (raceCounter == 2) {
                            populateStatementAndAddToBatch(prepStmntParticipant, 2, 1165, "Emmanuel", "Matadi", "LBR", 10.18, 10.14, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 3, 1299, "Alex", "Wilson", "SUI", 10.11, 10.11, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 4, 1074, "Yohan", "Blake", "JAM", 9.90, 9.69, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 5, 1118, "Abdul Hakim", "Sani Brown", "JPN", 10.05, 10.05, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 6, 778, "Bingtian", "Su", "CHN", 10.03, 9.99, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 7, 1347, "Jak Ali", "Harvey", "TUR", 10.10, 9.92, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 8, 1376, "Christopher", "Belcher", "USA", 9.93, 9.93, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 9, 976, "Reece", "Prescod", "GBR", 10.03, 10.03, raceId);
                        }
                        if (raceCounter == 3) {
                            populateStatementAndAddToBatch(prepStmntParticipant, 2, 1120, "Shuhei", "Tada", "JPN", 10.08, 10.08, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 3, 1343, "Emre Zafer", "Barnes", "TUR", 10.17, 10.12, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 4, 1386, "Christian", "Coleman", "USA", 9.82, 9.82, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 5, 736, "Andrew", "Fisher", "BRN", 10.12, 9.94, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 6, 1075, "Usain", "Bolt", "JAM", 9.95, 9.58, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 7, 982, "Chijindu", "Ujah", "GBR", 9.98, 9.96, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 8, 943, "Jimmy", "Vicaut", "FRA", 9.97, 9.86, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 9, 621, "Cejhae", "Greene", "ANT", 10.05, 10.01, raceId);
                        }
                        if (raceCounter == 4) {
                            populateStatementAndAddToBatch(prepStmntParticipant, 2, 4261, "Kelly-Ann", "Baptiste", "TTO", 10.88, 10.84, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 3, 4025, "Simone", "Facey", "JAM", 11.04, 10.95, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 4, 3901, "Daryll", "Neita", "GBR", 11.14, 11.14, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 5, 4373, "Deajah", "Stevens", "USA", 11.00, 11.00, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 6, 4132, "Dafne", "Schippers", "NED", 10.95, 10.81, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 7, 3772, "Marie-Josée", "Ta Lou", "CIV", 10.90, 10.86, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 8, 3713, "Ivet", "Lalova-Collio", "BUL", 11.26, 10.77, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 9, 4034, "Jura", "Levy", "JAM", 11.06, 11.06, raceId);
                        }
                        if (raceCounter == 5) {
                            populateStatementAndAddToBatch(prepStmntParticipant, 2, 4181, "Ewa", "Swoboda", "POL", 11.24, 11.12, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 3, 3889, "Desiree", "Henry", "GBR", 11.09, 11.06, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 4, 4046, "Elaine", "Thompson", "JAM", 10.71, 10.70, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 5, 3725, "Crystal", "Emmanuel", "CAN", 11.14, 11.14, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 6, 3696, "Rosangela", "Santos", "BRA", 11.04, 11.04, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 7, 4234, "Mujinga", "Kambundji", "SUI", 11.07, 11.07, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 8, 4378, "Ariana", "Washington", "USA", 11.06, 11.01, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 9, 4204, "Carina", "Horn", "RSA", 11.10, 11.06, raceId);
                        }
                        if (raceCounter == 6) {
                            populateStatementAndAddToBatch(prepStmntParticipant, 2, 3905, "Asha", "Philip", "GBR", 11.14, 11.10, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 3, 4235, "Salomé", "Kora", "SUI", 11.27, 11.27, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 4, 4317, "Tori", "Bowie", "USA", 10.90, 10.78, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 5, 4149, "Blessing", "Okagbare-Ighoteguonor", "NGR", 10.99, 10.79, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 6, 3946, "Gina", "Lückenkemper", "GER", 10.95, 10.95, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 7, 3771, "Murielle", "Ahouré", "CIV", 10.83, 10.78, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 8, 4038, "Natasha", "Morrison", "JAM", 11.09, 10.96, raceId);
                            populateStatementAndAddToBatch(prepStmntParticipant, 9, 4260, "Michelle-Lee", "Ahye", "TTO", 10.82, 10.82, raceId);
                        }
                        raceCounter++;
                    }
                }
                conn.setAutoCommit(false);
                prepStmntParticipant.executeBatch();
                conn.commit();
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            LOGGER.severe(e.getMessage());
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(stmnt);
            Util.closeStatement(prepStmntRace);
            Util.closeStatement(prepStmntParticipant);
            poolManager.release(conn);
        }
    }

    private void populateStatementAndAddToBatch(PreparedStatement prepParticipantStmnt, int lane, int startNumber, String firstName, String lastName, String country, double sb2017, double pb, int raceId) throws SQLException {
        prepParticipantStmnt.setInt(1, lane);
        prepParticipantStmnt.setInt(2, startNumber);
        prepParticipantStmnt.setString(3, firstName);
        prepParticipantStmnt.setString(4, lastName);
        prepParticipantStmnt.setString(5, country);
        prepParticipantStmnt.setDouble(6, sb2017);
        prepParticipantStmnt.setDouble(7, pb);
        prepParticipantStmnt.setInt(8, raceId);
        prepParticipantStmnt.addBatch();
    }
}
