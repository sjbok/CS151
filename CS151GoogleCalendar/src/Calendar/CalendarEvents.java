package Calendar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class CalendarEvents {
    private HashMap<LocalDate, ArrayList<Event>> hash;
    /**
     * Takes events of specific date and adds into HashMap. HashMap is then sorted by time
     */

    CalendarEvents(){
        hash = new HashMap<LocalDate, ArrayList<Event>>();
    }

    public void add(Event e) {
        ArrayList<Event> events = new ArrayList<Event>(); // Declare new event
        if (hash.containsKey(e.getDate())) // Checking if hash already has events on that date
        {
            hash.get(e.getDate()).add(e); // Hash adds more events on that date
        } else {
            events.add(e); // New event
            hash.put(e.getDate(), events); // Puts new event into HashMap with new key
        }
        ArrayList<Event> test = hash.get(e.getDate()); // Declare new event list containing all events from HashMap on that day
    }

    public void getEvents(){
        // add functionality to get events for viewBy
    }
}
