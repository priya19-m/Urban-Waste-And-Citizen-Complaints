package com.waste.dao;

import com.waste.bean.WasteServiceRow;
import com.waste.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class WasteServiceDAO {
	public int generateServiceRowID() {
		Connection connection = DBUtil.getConnection();
		int newID = -1;
		try {
		    Statement st = connection.createStatement();
		    ResultSet rs=st.executeQuery("SELECT SERVICE_ROW_SEQU.NEXTVAL FROM DUAL");
		    if(rs.next()) {
		    	newID=rs.getInt(1);
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newID;
		}
	public boolean insertServiceRow(WasteServiceRow row) {
		Connection connection = DBUtil.getConnection();
		String query = "INSERT INTO WASTE_SERVICE_TBL VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try{
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, row.getServiceRowID());
			ps.setString(2, row.getCitizenID());
			ps.setString(3, row.getServiceType());
			ps.setString(4, row.getWardCode());
			ps.setString(5, row.getRouteCode());
			ps.setDate(6, row.getScheduledDate());
			ps.setString(7, row.getVehicleNo());
			ps.setString(8, row.getCrewShift());
			ps.setDate(9, row.getComplaintDate());
			ps.setString(10, row.getComplaintType());
			ps.setString(11, row.getComplaintDescription());
			ps.setString(12, row.getPriorityLevel());
			ps.setString(13, row.getComplaintStatus());
			ps.setString(14, row.getClosureRemarks());
			int update = ps.executeUpdate();
			if(update > 0) {
				return true;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;	
	}
	public WasteServiceRow findServiceRow(int serviceRowID) {
		Connection connection = DBUtil.getConnection();
		WasteServiceRow w =null;
		String query = "SELECT * FROM WASTE_SERVICE_TBL WHERE Service_Row_ID=?";
		try {
			PreparedStatement ps= connection.prepareStatement(query);
			ps.setInt(1, serviceRowID);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				w = new WasteServiceRow();
				w.setServiceRowID(rs.getInt("Service_Row_ID"));
				w.setCitizenID(rs.getString("Citizen_ID"));
                w.setServiceType(rs.getString("Service_Type"));
                w.setWardCode(rs.getString("Ward_Code"));
                w.setRouteCode(rs.getString("Route_Code"));
                w.setScheduledDate(rs.getDate("Scheduled_Date"));
                w.setVehicleNo(rs.getString("Vehicle_No"));
                w.setCrewShift(rs.getString("Crew_Shift"));
                w.setComplaintDate(rs.getDate("Complaint_Date"));
                w.setComplaintType(rs.getString("Complaint_Type"));
                w.setComplaintDescription(rs.getString("Complaint_Description"));
                w.setPriorityLevel(rs.getString("Priority_Level"));
                w.setComplaintStatus(rs.getString("Complaint_Status"));
                w.setClosureRemarks(rs.getString("Closure_Remarks"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return w;
	}
	public List<WasteServiceRow> findOpenComplaintsByCitizen(String citizenID){
		Connection connection = DBUtil.getConnection();
		List<WasteServiceRow> list = new ArrayList<>();
		String query = "SELECT * FROM WASTE_SERVICE_TBL WHERE Citizen_ID=? AND Service_Type=?";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, citizenID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				WasteServiceRow w = new WasteServiceRow();
				w.setServiceRowID(rs.getInt("Service_Row_ID"));
				w.setCitizenID(rs.getString("Citizen_ID"));
                w.setServiceType(rs.getString("Service_Type"));
                w.setWardCode(rs.getString("Ward_Code"));
                w.setRouteCode(rs.getString("Route_Code"));
                w.setScheduledDate(rs.getDate("Scheduled_Date"));
                w.setVehicleNo(rs.getString("Vehicle_No"));
                w.setCrewShift(rs.getString("Crew_Shift"));
                w.setComplaintDate(rs.getDate("Complaint_Date"));
                w.setComplaintType(rs.getString("Complaint_Type"));
                w.setComplaintDescription(rs.getString("Complaint_Description"));
                w.setPriorityLevel(rs.getString("Priority_Level"));
                w.setComplaintStatus(rs.getString("Complaint_Status"));
                w.setClosureRemarks(rs.getString("Closure_Remarks"));
                list.add(w);			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<WasteServiceRow> findComplaintsByCitizen(String citizenID){
		Connection connection = DBUtil.getConnection();
		List<WasteServiceRow> list = new ArrayList<>();
		String query="SELECT * FROM WASTE_SERVICE_TBL WHERE Citizen_ID=? AND Complaint_Status IN ('OPEN','IN_PROGRESS')";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, citizenID);
			ResultSet rs =ps.executeQuery();
			if(rs.next()) {
				WasteServiceRow w = new WasteServiceRow();
				w.setServiceRowID(rs.getInt("Serivice_Row_ID"));
				w.setCitizenID(rs.getString("Citizen_ID"));
                w.setComplaintStatus(rs.getString("Complaint_Status"));
                list.add(w);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public boolean updateComplaintStatusAndClosure(int serviceRowID, String newStatus, String closureRemarks) {
		Connection connection = DBUtil.getConnection();
		String query = "UPDATE WASTE_SERVICE_TBL SET Complaint_Status =? , Closure_Remarks=? WHERE Service_Row_ID=? ";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, serviceRowID);
			ps.setString(2, newStatus);
			ps.setString(3, closureRemarks);
			int update = ps.executeUpdate();
			if(update > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
