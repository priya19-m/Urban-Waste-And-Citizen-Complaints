package com.waste.dao;

import com.waste.bean.Citizen;
import com.waste.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CitizenDAO {
	private Citizen mapCitizen(ResultSet rs) throws Exception {
        Citizen citizen = new Citizen();
        citizen.setCitizenID(rs.getString("Citizen_ID"));
        citizen.setFullName(rs.getString("Full_Name"));
        citizen.setHouseOrBuildingNo(rs.getString("House_No"));
        citizen.setStreetName(rs.getString("Street_Name"));
        citizen.setAreaOrLocality(rs.getString("Area"));
        citizen.setWardCode(rs.getString("Ward_Code"));
        citizen.setRouteCode(rs.getString("Route_Code"));
        citizen.setMobile(rs.getString("Mobile"));
        citizen.setEmail(rs.getString("Email"));
        citizen.setStatus(rs.getString("Status"));
        return citizen;
    }
	public Citizen findCitizen(String citizenID) {
		Citizen citizen = null;
		Connection connection = DBUtil.getConnection();
		String query = "SELECT * FROM Citizen_TBL WHERE Citizen_ID=?";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, citizenID);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				citizen = mapCitizen(rs);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return citizen;
	}
	public List<Citizen> viewAllCitizens(){
		List<Citizen> list = new ArrayList<Citizen>();
		Connection connection = DBUtil.getConnection();
		String query = "SELECT * FROM Citizen_TBL ORDER BY Citizen_ID";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				list.add(mapCitizen(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public boolean insertCitizen(Citizen citizen) {
		Connection connection = DBUtil.getConnection();
		String query = "INSERT INTO Citizen_TBL VALUES (?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, citizen.getCitizenID());
			ps.setString(2, citizen.getFullName());
			ps.setString(3, citizen.getHouseOrBuildingNo());
			ps.setString(4, citizen.getStreetName());
			ps.setString(5, citizen.getAreaOrLocality());
			ps.setString(6, citizen.getWardCode());
			ps.setString(7, citizen.getRouteCode());
			ps.setString(8, citizen.getMobile());
			ps.setString(9, citizen.getEmail());
			ps.setString(10, citizen.getStatus());
			int row = ps.executeUpdate();
			if(row > 0) {
				return true;
			}
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean updateCitizenStatus(String citizenID, String status) {
		Connection connection = DBUtil.getConnection();
		String query = "UPDATE Citizen_TBL SET Status = ? WHERE Citizen_ID=?";
		try {
			PreparedStatement ps=connection.prepareStatement(query);
			ps.setString(1, citizenID);
			ps.setString(2, status);
			int update = ps.executeUpdate();
			if(update > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean deleteCitizen(String citizenID) {
		Connection connection = DBUtil.getConnection();
		String query = "DELETE FROM Citizen_TBL WHERE Citizen_ID=?";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, citizenID);
			int rows = ps.executeUpdate();
			if(rows > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
    

}
