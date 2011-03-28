package edu.berkeley.myberkeley.caldav;

import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VToDo;

import java.util.Comparator;

public class CalendarSearchCriteria {

    public enum MODE {
        REQUIRED,
        UNREQUIRED,
        ALL_UNARCHIVED,
        ALL_ARCHIVED
    }

    public enum TYPE {
        VEVENT,
        VTODO
    }

    public enum SORT {

        DATE_ASC(new DateComparator(true)),
        DATE_DESC(new DateComparator(false));

        private final Comparator<CalendarWrapper> comparator;

        SORT(Comparator<CalendarWrapper> comparator) {
            this.comparator = comparator;
        }

        public Comparator<CalendarWrapper> getComparator() {
            return comparator;
        }

        private static class DateComparator implements Comparator<CalendarWrapper> {

            private final boolean ascending;

            private DateComparator(boolean ascending) {
                this.ascending = ascending;
            }

            public int compare(CalendarWrapper a, CalendarWrapper b) {
                int result = 0;
                Component compA = a.getCalendar().getComponent(Component.VTODO);
                Component compB = b.getCalendar().getComponent(Component.VTODO);
                if (compA != null && compB != null && compA instanceof VToDo && compB instanceof VToDo) {
                    result = ((VToDo) compA).getDue().getDate().compareTo(((VToDo) compB).getDue().getDate());
                }
                compA = a.getCalendar().getComponent(Component.VEVENT);
                compB = b.getCalendar().getComponent(Component.VEVENT);
                if (compA != null && compB != null && compA instanceof VEvent && compB instanceof VEvent) {
                    result = ((VEvent) compA).getStartDate().getDate().compareTo(((VEvent) compB).getStartDate().getDate());
                }
                if (this.ascending) {
                    return result;
                }
                return -1 * result;
            }
        }
    }

    private TYPE type;

    private MODE mode;

    private SORT sort = SORT.DATE_ASC;

    private Date start;

    private Date end;

    public CalendarSearchCriteria(TYPE type, Date start, Date end, MODE mode) {
        this.type = type;
        this.start = start;
        this.end = end;
        this.mode = mode;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public MODE getMode() {
        return mode;
    }

    public void setMode(MODE mode) {
        this.mode = mode;
    }

    public SORT getSort() {
        return sort;
    }

    public void setSort(SORT sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "CalendarSearchCriteria{" +
                "type=" + type +
                ", mode=" + mode +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

}

