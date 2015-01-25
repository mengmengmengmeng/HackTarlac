package ac.codebuddy.hacktarl;

public class ClinicList {
	private String id;
    private String name;
	private String rhu_code;
	private String address;
	private String contact;
    private String created_at;
    private String updated_at;
    
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getRhuCode() {
        return rhu_code;
    }
    public String getAddress(){
    	return address;
    }
    public String getContact() {
		return contact;
	}
    public String getCreatedAt() {
        return created_at;
    }
    public String getUpdatedAt() {
        return updated_at;
    }
    public void setClinics(String id, String name, String rhu_code, String address, String contact, String created_at, String updated_at) {

        this.id = id;
        this.name = name;
        this.rhu_code = created_at;
        this.address = address;
        this.contact = contact;
        this.created_at = created_at;
        this.updated_at = updated_at;
        
    }

}