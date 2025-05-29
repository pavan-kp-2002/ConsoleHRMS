package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import models.LeaveRequest;

public class LeaveApprovalDao {
    private final Connection connection;

    public LeaveApprovalDao(Connection connection) {
        this.connection = connection;
    }

    public boolean updateLeaveStatus(int leaveId, String status) throws SQLException {
        String sql = "UPDATE leave_requests SET status = ?::leave_status WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, leaveId);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateUserLeaveBalance(String userEmail, int newBalance) throws SQLException {
        if (!userExists(userEmail)) {
            return false;
        }
        String sql = "UPDATE users SET leave_balance = ? WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, newBalance);
            stmt.setString(2, userEmail);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean userExists(String userEmail) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userEmail);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public List<LeaveRequest> getLeaveRequestsByStatus(String status) throws SQLException {
        List<LeaveRequest> requests = new ArrayList<>();
        String sql = "SELECT * FROM leave_requests WHERE status = ?::leave_status";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    LeaveRequest req = new LeaveRequest();
                    req.setId(rs.getInt("id"));
                    req.setUserEmail(rs.getString("user_email"));
                    req.setStartDate(rs.getDate("start_date").toLocalDate());
                    req.setEndDate(rs.getDate("end_date").toLocalDate());
                    req.setStatus(rs.getString("status"));
                    req.setReason(rs.getString("reason"));
                    requests.add(req);
                }
            }
        }
        return requests;
    }

    public LeaveRequest getLeaveRequestById(int leaveId) throws SQLException {
        String sql = "SELECT * FROM leave_requests WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, leaveId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    LeaveRequest req = new LeaveRequest();
                    req.setId(rs.getInt("id"));
                    req.setUserEmail(rs.getString("user_email"));
                    req.setStartDate(rs.getDate("start_date").toLocalDate());
                    req.setEndDate(rs.getDate("end_date").toLocalDate());
                    req.setStatus(rs.getString("status"));
                    req.setReason(rs.getString("reason"));
                    return req;
                }
            }
        }
        return null;
    }

    public int getUserLeaveBalance(String userEmail) throws SQLException {
        String sql = "SELECT leave_balance FROM users WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userEmail);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("leave_balance");
                }
            }
        }
        return 0;
    }
}