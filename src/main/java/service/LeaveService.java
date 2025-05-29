package service;

import dao.LeaveRequestDao;
import models.LeaveRequest;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class LeaveService {
    private final LeaveRequestDao leaveRequestDao;

    public LeaveService(Connection conn) {
        this.leaveRequestDao = new LeaveRequestDao(conn);
    }

    public void applyLeave(String userEmail, LocalDate startDate, LocalDate endDate, String reason) throws IllegalArgumentException, Exception {
        int daysRequested = (int) (endDate.toEpochDay() - startDate.toEpochDay() + 1);
        int balance = getLeaveBalance(userEmail);
        if (daysRequested > balance) {
            throw new IllegalArgumentException("Insufficient leave balance. Contact your Manager.");
        }
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setUserEmail(userEmail);
        leaveRequest.setStartDate(startDate);
        leaveRequest.setEndDate(endDate);
        leaveRequest.setStatus("Pending");
        leaveRequest.setReason(reason);
        leaveRequestDao.applyLeave(leaveRequest);
        leaveRequestDao.updateLeaveBalance(userEmail, balance - daysRequested);
    }

    public List<LeaveRequest> getAppliedLeaves(String userEmail) throws Exception {
        return leaveRequestDao.getAppliedLeaves(userEmail);
    }

    public int getLeaveBalance(String userEmail) throws Exception {
        return leaveRequestDao.getLeaveBalance(userEmail);
    }
}
