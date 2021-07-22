package Calendar;

import java.time.LocalDate;
import java.util.Comparator;

class SortDate implements Comparator<LocalDate>
{
	/**
	 * Overrides the Collection.sort comparator to specifically compare LocalDates
	 */
    public int compare(LocalDate a, LocalDate b)
    {
    	return (a.compareTo(b));
    }
}
