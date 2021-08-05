package Calendar;
/**
 * @author Sung Jun Bok
 * @version 1.0 8/5/2021
 */
import java.util.Comparator;

/**
 * A class that sorts the events
 */

class SortTime implements Comparator<Event>
{
    /**
     * Overrides the Collection.sort comparator to compare Event times
     * @param a - the first event
     * @param b - the second event
     * @return - returns 0 if times are equal, 1 if the second event is before the first, and -1 if the second event is after the first
     */

    public int compare(Event a, Event b)
    {
        if(a.getTime().getst().isBefore(b.getTime().getst()))
        {
        	return -1; // a < b
        }
        else if(b.getTime().getst().isBefore(a.getTime().getst()))
        {
        	return 1; // b < a
        }
        else
        {
        	return 0; // Times are equal
        }
    }
}