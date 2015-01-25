package ac.codebuddy.hacktarl;

public class PNPList {
	
	private String id;
    private String station;
	private String fire_marshal;
    private String contact_number;
	private String landline;
    
    public String getId() {
        return id;
    }
    public String getStation() {
        return station;
    }
    public String getContactNumber(){
    	return contact_number;
    }
    public String getLandline() {
		return landline;
	}

    public void setPNP(String id, String station, String contact_number, String landline) {

        this.id = id;
        this.station = station;
        this.contact_number = contact_number;
        this.landline = landline;
        
    }

}