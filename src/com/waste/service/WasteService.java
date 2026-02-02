package com.waste.service;

import com.waste.util.ActiveComplaintsExistException;
import com.waste.util.ComplaintStatusException;
import com.waste.util.DBUtil;
import com.waste.util.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import com.waste.bean.Citizen;
import com.waste.bean.WasteServiceRow;
import com.waste.dao.*;

public class WasteService {
	private CitizenDAO citizenDAO = new CitizenDAO();
	private WasteServiceDAO wasteserviceDAO = new WasteServiceDAO();
	public Citizen viewCitizenDetails(String citizenID) throws ValidationException{
		if(citizenID == null || citizenID.trim().isEmpty()) {
			throw new ValidationException ();
		}
		return citizenDAO.findCitizen(citizenID);
	}
	
	public List<Citizen> viewAllCitizens(){
		return citizenDAO.viewAllCitizens();
	}
	
	public boolean registerNewCitizen(Citizen citizen) throws ValidationException{
		try {
			if(citizen.getCitizenID()==null || citizen.getCitizenID().trim().isEmpty()) 
				throw new ValidationException();
			if(citizen.getFullName()==null||citizen.getFullName().trim().isEmpty()) 
				throw new ValidationException();
			if(citizen.getHouseOrBuildingNo()==null||citizen.getHouseOrBuildingNo().trim().isEmpty())
				throw new ValidationException();
			if(citizen.getStreetName()==null||citizen.getStreetName().trim().isEmpty())
				throw new ValidationException();
			if(citizen.getWardCode()==null||citizen.getWardCode().trim().isEmpty())
				throw new ValidationException();
			if(citizen.getRouteCode()==null||citizen.getRouteCode().trim().isEmpty())
				throw new ValidationException();
			if(citizen.getMobile()==null||!citizen.getMobile().matches("\\d{10,15}"))
				throw new ValidationException();
			if(citizenDAO.findCitizen(citizen.getCitizenID())!=null)
				throw new ValidationException();
			if(citizen.getStatus()==null|| citizen.getStatus().trim().isEmpty())
				citizen.setStatus("ACTIVE");
			return citizenDAO.insertCitizen(citizen);
		} catch(ValidationException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean logScheduledVisit(String wardCode, String routeCode, Date scheduledDate, String vehicleNo, String crewShift) throws ValidationException{
			if(wardCode==null||wardCode.trim().isEmpty())
				throw new ValidationException();
			if(routeCode==null||routeCode.trim().isEmpty())
				throw new ValidationException();
			if(vehicleNo==null||vehicleNo.trim().isEmpty())
				throw new ValidationException();
			if(crewShift==null||crewShift.trim().isEmpty())
				throw new ValidationException();
			if(scheduledDate==null)
				throw new ValidationException();
			
			Connection connection = null;

		try {
			connection=DBUtil.getConnection();
			
			connection.setAutoCommit(false);
			WasteServiceRow row=new WasteServiceRow();
			row.setServiceType("SCHEDULED_VISIT");
			row.setCitizenID(null);
			row.setWardCode(wardCode);
			row.setRouteCode(routeCode);
			row.setScheduledDate(scheduledDate);
			row.setVehicleNo(vehicleNo);
			row.setCrewShift(crewShift);
			int newID = wasteserviceDAO.generateServiceRowID();
			row.setServiceRowID(newID);
			boolean ok = wasteserviceDAO.insertServiceRow(row);
			if(ok) connection.commit();
			else connection.rollback();
			return ok;
		} catch (Exception e) {
			try {
				if(connection!=null) connection.rollback();
			} catch (Exception x) {
				
			}
		}
		return false;
	}
	public boolean registerComplaint(String citizenID, String complaintType,
			String complaintDescription, String priorityLevel, Date complaintDate, Date relatedScheduledDate) throws ValidationException {
		if(citizenID==null||citizenID.trim().isEmpty()) 
			throw new ValidationException();
		if(complaintType==null||complaintType.trim().isEmpty())
			throw new ValidationException();
		if(complaintDescription==null||complaintDescription.trim().isEmpty())
			throw new ValidationException();
		if(priorityLevel==null||priorityLevel.trim().isEmpty())
			throw new ValidationException();
		if(complaintDate==null)
			throw new ValidationException();
		
			Citizen citizen = citizenDAO.findCitizen(citizenID);
		    if(citizen==null||!"ACTIVE".equalsIgnoreCase(citizen.getStatus()))
		    	return false;
		    if(relatedScheduledDate!=null && complaintDate.before(relatedScheduledDate))
		    	throw new ValidationException();
		    
		    Connection connection = null;
		    try {
		    	connection = DBUtil.getConnection();
		    	connection.setAutoCommit(false);
		    	WasteServiceRow row = new WasteServiceRow();
		    	row.setServiceType("COMPLAINT");
		    	row.setCitizenID(citizenID);
		    	row.setWardCode(citizen.getWardCode());
		    	row.setRouteCode(citizen.getRouteCode());
		    	row.setComplaintDate(complaintDate);
		    	row.setComplaintType(complaintType);
		    	row.setComplaintDescription(complaintDescription);
		    	row.setPriorityLevel(priorityLevel);
		    	row.setComplaintStatus("OPEN");
		    	row.setClosureRemarks(null);
		    	
		    	int newID = wasteserviceDAO.generateServiceRowID();
		    	row.setServiceRowID(newID);
		    	boolean ok = wasteserviceDAO.insertServiceRow(row);
		    	if(ok) connection.commit();
		    	else connection.rollback();
		    	return ok;	
		    } catch(Exception e) {
		    	try {
		    		if(connection!=null) connection.rollback();
		    	} catch(Exception x) {
		    		x.printStackTrace();
		    	}
		    }
		    return false;
	}
	public boolean updateComplaintStatus(int serviceRowID, String newStatus, 
			String closureRemarks)throws ValidationException, ComplaintStatusException{
		Connection connection = null;
		
			if(serviceRowID<0)
				throw new ValidationException();
			if(!newStatus.matches("OPEN|IN_PROGRESS|RESOLVED|CLOSED"))
				throw new ValidationException();
			
		WasteServiceRow wasteServiceRow = wasteserviceDAO.findServiceRow(serviceRowID);
		if(wasteServiceRow==null||!"COMPLAINT".equals(wasteServiceRow.getServiceType()))
			throw new ComplaintStatusException();
	   
		if("CLOSED".equalsIgnoreCase(wasteServiceRow.getComplaintStatus()))
			throw new ComplaintStatusException();
		
		
		try {
			connection = DBUtil.getConnection();
			connection.setAutoCommit(false);
			boolean ok = wasteserviceDAO.updateComplaintStatusAndClosure(serviceRowID, newStatus, closureRemarks);
			if(ok) connection.commit();
			else connection.rollback();
			return ok;
			} catch (Exception e){
				try {
					if(connection!=null) connection.rollback();
				} catch (Exception x) {
					x.printStackTrace();
				}
			}
		return false;
	}
	
	public List<WasteServiceRow> listComplaintsByCitizen(String citizenID){
		return wasteserviceDAO.findComplaintsByCitizen(citizenID);
	}
	
	public boolean deactivateOrRemoveCitizen(String citizenID) throws ValidationException, ActiveComplaintsExistException{
		if(citizenID==null||citizenID.trim().isEmpty())
			throw new ValidationException();
		
		List<WasteServiceRow> openComplaints = wasteserviceDAO.findOpenComplaintsByCitizen(citizenID);
		if(!openComplaints.isEmpty())
			throw new ActiveComplaintsExistException();
		
		return citizenDAO.updateCitizenStatus(citizenID, "INACTIVE");
	}

}



























