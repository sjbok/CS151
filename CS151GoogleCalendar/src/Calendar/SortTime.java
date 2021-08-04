package Calendar;

import java.util.Comparator;

class SortTime implements Comparator<Event>
{
	/**
	 * Overrides the Collection.sort comparator to specifically compare Event times
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