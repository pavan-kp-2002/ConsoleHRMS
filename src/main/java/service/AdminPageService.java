// src/main/java/service/AdminPageService.java
package service;

import dao.LeaveApprovalDao;
import models.LeaveRequest;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AdminPageService {
    private final LeaveApprovalDao leaveApprovalDao;

    public AdminPageService(Connection connection) {
        this.leaveApprovalDao = new LeaveApprovalDao(connection);
    }

    public boolean processLeaveRequest(int leaveId, boolean approve) throws SQLException {
        String status = approve ? "Approved" : "Rejected";
        boolean updated = leaveApprovalDao.updateLeaveStatus(leaveId, status);

        if (!approve && updated) {
            LeaveRequest req = leaveApprovalDao.getLeaveRequestById(leaveId);
            if (req != null) {
                int days = (int) (req.getEndDate().toEpochDay() - req.getStartDate().toEpochDay() + 1);
                int currentBalance = leaveApprovalDao.getUserLeaveBalance(req.getUserEmail());
                leaveApprovalDao.updateUserLeaveBalance(req.getUserEmail(), currentBalance + days);
            }
        }
        return updated;
    }

    public boolean updateLeaveBalance(String userEmail, int newBalance) throws SQLException {
        return leaveApprovalDao.updateUserLeaveBalance(userEmail, newBalance);
    }

    public List<LeaveRequest> getApprovedLeaveRequests() throws SQLException {
        return leaveApprovalDao.getLeaveRequestsByStatus("Approved");
    }

    public List<LeaveRequest> getRejectedLeaveRequests() throws SQLException {
        return leaveApprovalDao.getLeaveRequestsByStatus("Rejected");
    }

    public List<LeaveRequest> getPendingLeaveRequests() throws SQLException {
        return leaveApprovalDao.getLeaveRequestsByStatus("Pending");
    }
}