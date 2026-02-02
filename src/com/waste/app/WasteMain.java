package com.waste.app;

import com.waste.bean.Citizen;
import com.waste.service.WasteService;
import com.waste.util.ValidationException;

public class WasteMain { 
private static WasteService service = new WasteService(); 
public static void main(String[] args) { 
java.util.Scanner sc = new java.util.Scanner(System.in); 
System.out.println("--- Urban Waste Management Console ---"); 
// DEMO 1: Register a Citizen 
try { 
Citizen citizen = new Citizen(); 
citizen.setCitizenID("CT2003"); 
citizen.setFullName("Joseph Vijay"); 
citizen.setHouseOrBuildingNo("Flat 10C"); 
citizen.setStreetName("Sunset Residency Road"); 
citizen.setAreaOrLocality("Anna Nagar"); 
citizen.setWardCode("WARD15"); 
citizen.setRouteCode("RT08"); 
citizen.setMobile("9998887772"); 
citizen.setEmail("joseph.vijay@example.com"); 
citizen.setStatus("INACTIVE"); 
boolean ok = service.registerNewCitizen(citizen); 
System.out.println(ok ? "CITIZEN REGISTERED" : 
"CITIZEN REGISTRATION FAILED"); 
} catch (ValidationException e) { 
System.out.println("Validation Error: " + 
e.toString()); 
} catch (Exception e) { 
System.out.println("System Error: " + 
e.getMessage()); 
} 
// DEMO 2: Register a Complaint 
try { 
java.sql.Date today = new 
java.sql.Date(System.currentTimeMillis()); 
java.sql.Date scheduled = today; 
boolean ok = service.registerComplaint( 
"CT2001", 
"Missed Pickup", 
"Garbage not collected as per schedule", 
"HIGH", 
today, 
scheduled 
); 
System.out.println(ok ? "COMPLAINT REGISTERED" : 
"COMPLAINT REGISTRATION FAILED"); 
} catch (ValidationException e) { 
System.out.println("Validation Error: " + 
e.toString()); 
} catch (Exception e) { 
System.out.println("System Error: " + 
e.getMessage()); 
} 
sc.close(); 
} 
}