import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LHSBlockCalGenerator {
	static List<CalendarEvent> events;
	static int[] lunches = {3, 2, 2, 3, 2, 2};
	static String homeroom = "Room 616";

	public static void main(String[] args) {
		events = new ArrayList<CalendarEvent>();
		
		importBlankCal();
		
		removeExcessLunchBlocks();
		removeDayEvents();
		
		replaceAllBlocksLetter("A", "Math", "Room 819");
		replaceAllBlocksLetter("B", "Chemistry", "Room 400");
		replaceAllBlocksLetter("C", "Computer Programming", "Room 703");
		replaceAllBlocksLetter("D", "History", "Room 240");
		replaceAllBlocksLetter("F", "English", "Room 220");
		replaceAllBlocksLetter("H", "Spanish", "Room 512");
		replaceAllBlocks("E1", "Counselor Seminar", "Room 625");
		replaceAllBlocks("E2", "Total Body Fitness", "Gym");
		replaceAllBlocks("E4", "Total Body Fitness", "Gym");
		replaceAllBlocks("G1", "Chemistry", "Room 400");
		replaceAllBlocks("G3", "Chemistry", "Room 400");
		replaceAllBlocks("I-block", "I Block", "");
		replaceAllBlocks("Homeroom", "Homeroom", homeroom);
		
		removeBlock("E3");
		removeBlock("G2");
		removeBlock("G4");
		
		printToFile("Schedule.ics");
	}
	
	public static void importBlankCal() {
		Scanner input = null;
		try {
			input = new Scanner(new File("LHS Blocks.ics"));
			
			String dtend = "", uid = "", dtstamp = "", description = "", status = "", sequence = "", summary = "", lastModified = "", created = "", dtstart = "";
			
			while(input.hasNextLine()) {
				String line = input.nextLine();
				String[] values = line.split(":");
				String type = values[0];
				String value = "";
				
				if(values.length > 1) {
					value = values[1];
				}
				
				switch(type) {
					case "DTEND": dtend = value; break;
					case "UID": uid = value; break;
					case "DTSTAMP": dtstamp = value; break;
					case "DESCRIPTION": description = value; break;
					case "STATUS": status = value; break;
					case "SEQUENCE": sequence = value; break;
					case "SUMMARY": summary = value; break;
					case "LAST-MODIFIED": lastModified = value; break;
					case "CREATED": created = value; break;
					case "DTSTART": dtstart = value; break;
				}
				
				if(line.equals("END:VEVENT")) {
					CalendarEvent event = new CalendarEvent(dtend, uid, dtstamp, description, status, sequence, summary, lastModified, created, dtstart);
					events.add(event);
				}
			}
			
		} catch(FileNotFoundException e) {
			System.out.println("File not found");
		}
	}

	public static void removeExcessLunchBlocks() {
		//Day 1
		switch(lunches[0]) {
			case 1: removeBlock("D1");  removeBlock("E1");  break;
			case 2: removeBlock("D$1"); removeBlock("E1");  break;
			case 3: removeBlock("D$1"); removeBlock("E$1"); break;
		}
		
		//Day 2
		switch(lunches[1]) {
			case 1: removeBlock("G1");  removeBlock("H1");  break;
			case 2: removeBlock("G$1"); removeBlock("H1");  break;
			case 3: removeBlock("G$1"); removeBlock("H$1"); break;
		}
		
		//Day 3
		switch(lunches[2]) {
			case 1: removeBlock("G2");  removeBlock("H2");  break;
			case 2: removeBlock("G$2"); removeBlock("H2");  break;
			case 3: removeBlock("G$2"); removeBlock("H$2"); break;
		}
		
		//Day 4
		switch(lunches[3]) {
			case 1: removeBlock("D3");  removeBlock("E3");  break;
			case 2: removeBlock("D$3"); removeBlock("E3");  break;
			case 3: removeBlock("D$3"); removeBlock("E$3"); break;
		}
		
		//Day 5
		switch(lunches[4]) {
			case 1: removeBlock("G3");  removeBlock("H3");  break;
			case 2: removeBlock("G$3"); removeBlock("H3");  break;
			case 3: removeBlock("G$3"); removeBlock("H$3"); break;
		}
		
		//Day 6
		switch(lunches[5]) {
			case 1: removeBlock("G4");  removeBlock("H4");  break;
			case 2: removeBlock("G$4"); removeBlock("H4");  break;
			case 3: removeBlock("G$4"); removeBlock("H$4"); break;
		}
		

	}
	
	public static void removeDayEvents() {
		for(int i = 1; i < 7; i++) {
			removeBlock("Day " + i);
			removeBlock("Day " + i + " Half Day");
		}
	}
	
	public static void removeBlock(String block) {
		for(int i = 0; i < events.size(); i++) {
			if(events.get(i).getSummary().equals(block)) {
				events.remove(i--);
			}
		}
	}
	
	public static void replaceAllBlocksLetter(String block, String newClass, String location) {
		for(int i = 1; i < 5; i++) {
			replaceAllBlocks(block + i, newClass, location);
		}
	}
	
	public static void replaceAllBlocks(String block, String newClass, String location) {
		
		for(CalendarEvent event : events) {
			if(event.getSummary().replace("$", "").equals(block.replace("$", ""))) {
				event.setSummary(newClass);
				event.setLocation(location);
			}
		}
	}

	public static void printToFile(String filename) {
		File outFile = new File(filename);
		PrintWriter output = null;
		try {
			output = new PrintWriter(outFile);
		} catch(FileNotFoundException ex) {
			System.out.println("Cannot create file.");
			System.exit(2);
		}
		
		String[] beginning = {"BEGIN:VCALENDAR"
				,"METHOD:PUBLISH"
				,"VERSION:2.0"
				,"X-WR-CALNAME:LHS Blocks"
				,"PRODID:-//Apple Inc.//Mac OS X 10.15.2//EN"
				,"X-WR-CALDESC:lexingtonma.org_8nols65bal3ge9ns4upskn7kso@group.calendar.g"
				," oogle.com"
				,"X-WR-TIMEZONE:America/New_York"
				,"CALSCALE:GREGORIAN"
				,"BEGIN:VTIMEZONE"
				,"TZID:America/New_York"
				,"BEGIN:DAYLIGHT"
				,"TZOFFSETFROM:-0500"
				,"RRULE:FREQ=YEARLY;BYMONTH=3;BYDAY=2SU"
				,"DTSTART:20070311T020000"
				,"TZNAME:EDT"
				,"TZOFFSETTO:-0400"
				,"END:DAYLIGHT"
				,"BEGIN:STANDARD"
				,"TZOFFSETFROM:-0400"
				,"RRULE:FREQ=YEARLY;BYMONTH=11;BYDAY=1SU"
				,"DTSTART:20071104T020000"
				,"TZNAME:EST"
				,"TZOFFSETTO:-0500"
				,"END:STANDARD"
				,"END:VTIMEZONE"};
		
		for(String s : beginning) {
			output.println(s);
		}
		for(CalendarEvent e : events) {
			String[] eventDetails = e.fileEvent();
			
			for(String s : eventDetails) {
				output.println(s);
			}
		}
		
		output.print("END:VCALENDAR");
		
		output.close();
		
	}
	
}
