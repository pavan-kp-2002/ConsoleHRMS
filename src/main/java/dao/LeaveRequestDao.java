package dao;

import models.LeaveRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeaveRequestDao {
    private final Connection connection;

    public LeaveRequestDao(Connection connection) {
        this.connection = connection;
    }

    public void applyLeave(LeaveRequest leaveRequest) throws SQLException {

        String sql = "INSERT INTO leave_requests (user_email, start_date, end_date,status, reason) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, leaveRequest.getUserEmail());
            stmt.setDate(2, Date.valueOf(leaveRequest.getStartDate()));
            stmt.setDate(3, Date.valueOf(leaveRequest.getEndDate()));
            stmt.setObject(4, leaveRequest.getStatus(), Types.OTHER);
            stmt.setString(5, leaveRequest.getReason());
            stmt.executeUpdate();
        }
    }

    public void updateLeaveBalance(String userEmail, int newBalance) throws SQLException {
        String sql = "Update users SET leave_balance = ? WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, newBalance);
            stmt.setString(2, userEmail);
            stmt.executeUpdate();
        }
    }

    public List<LeaveRequest> getAppliedLeaves(String userEmail) throws SQLException {
        List<LeaveRequest> requests = new ArrayList<>();
        String sql = "SELECT * FROM leave_requests WHERE user_email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userEmail);
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

    public int getLeaveBalance(String userEmail) throws SQLException {
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
