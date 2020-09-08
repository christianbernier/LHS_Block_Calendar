
public class CalendarEvent {
	private String dtend, uid, dtstamp, description, status, sequence, summary, lastModified, created, dtstart, location;

	public CalendarEvent(String dtend, String uid, String dtstamp, String description, String status, String sequence, String summary, String lastModified, String created, String dtstart) {
		this.dtend = dtend;
		this.uid = uid;
		this.dtstamp = dtstamp;
		this.description = description;
		this.status = status;
		this.sequence = sequence;
		this.summary = summary;
		this.lastModified = lastModified;
		this.created = created;
		this.dtstart = dtstart;
		location = "";
	}
	
	public void printEvent() {
		System.out.println("\n" + summary);
		System.out.println("Starts: " + dtstart);
		System.out.println("Ends: " + dtend);
		System.out.println("At: " + location);
	}
	
	public String getSummary() {
		return summary;
	}
	
	public void setSummary(String s) {
		summary = s;
	}
	
	public void setLocation(String s) {
		location = s;
	}
	
	public String[] fileEvent() {
		String[] event = new String[15];
		event[0] = "BEGIN:VEVENT";
		event[1] = "TRANSP:OPAQUE";
		event[2] = "DTEND:" + dtend;
		event[3] = "UID:" + uid;
		event[4] = "DTSTAMP:" + dtstamp;
		event[5] = "DESCRIPTION:" + description;
		event[6] = "STATUS:" + status;
		event[7] = "SEQUENCE:" + sequence;
		event[8] = "X-APPLE-TRAVEL-ADVISORY-BEHAVIOR:AUTOMATIC";
		event[9] = "SUMMARY:" + summary;
		event[10] = "LOCATION:" + location;
		event[11] = "LAST-MODIFIED:" + lastModified;
		event[12] = "CREATED:" + created;
		event[13] = "DTSTART:" + dtstart;
		event[14] = "END:VEVENT";
		
		return event;
	}
}
