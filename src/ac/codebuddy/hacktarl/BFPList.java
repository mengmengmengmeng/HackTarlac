package ac.codebuddy.hacktarl;

public class BFPList {
	
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
    public String getFireMarshal() {
        return fire_marshal;
    }
    public String getContactNumber(){
    	return contact_number;
    }
    public String getLandline() {
		return landline;
	}

    public void setBFP(String id, String station, String fire_marshal, String contact_number, String landline) {

        this.id = id;
        this.station = station;
        this.fire_marshal = fire_marshal;
        this.contact_number = contact_number;
        this.landline = landline;
        
    }

}