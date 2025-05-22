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

    public void applyLeave(String userEmail, LocalDate startDate, LocalDate endDate, String reason) throws Exception {
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setUserEmail(userEmail);
        leaveRequest.setStartDate(startDate);
        leaveRequest.setEndDate(endDate);
        leaveRequest.setStatus("Pending");
        leaveRequest.setReason(reason);
        leaveRequestDao.applyLeave(leaveRequest);
    }

    public List<LeaveRequest> getAppliedLeaves(String userEmail) throws Exception {
        return leaveRequestDao.getAppliedLeaves(userEmail);
    }
}
