package BookingBoardroom.rest.DAO;

import BookingBoardroom.rest.model.Booking;
import BookingBoardroom.rest.support.CompByDateOfMeeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.TreeSet;

@Component
public class BookingDAO {

    @Autowired
    @Qualifier("env")
    private Environment env;

    //Sort Bookings by DateOfMeeting
    private static Set<Booking> approvedBookingsTable = new TreeSet<>(new CompByDateOfMeeting());

    public Set<Booking> list() {
        return approvedBookingsTable;
    }

    public void create(Booking booking) {
        if (booking != null) {
            BookingProcessing(booking);
        }
    }

    public Set<Booking> getDayTable(String date) {
        Set<Booking> dayTable = new TreeSet<>(new CompByDateOfMeeting());
        LocalDate r_date = LocalDate.parse(date, DateTimeFormatter.ofPattern(env.getProperty("meetDurTimePattern")));
        for (Booking b : approvedBookingsTable) {
            if (r_date.isEqual(b.getMeetingStartTime().toLocalDate())) {
                dayTable.add(b);
            }
        }
        return dayTable;
    }

    private void BookingProcessing(Booking booking) {
        if (isBookingInWorkTime(booking) & isBookingNotIntersect(booking)) {
            approvedBookingsTable.add(booking);
        }
    }

    private static boolean isBookingInWorkTime(Booking booking) {

        LocalTime startMeeting = booking.getMeetingStartTime().toLocalTime();
        LocalTime endMeeting = booking.getMeetingEndTime().toLocalTime();
        LocalTime openOffice = booking.getOpenOffice();
        LocalTime closeOffice = booking.getCloseOffice();

        return ((startMeeting.isAfter(openOffice) || startMeeting.equals(openOffice)) &
                (startMeeting.isBefore(closeOffice) || startMeeting.equals(closeOffice)) &
                (endMeeting.isBefore(closeOffice) || endMeeting.equals(closeOffice)));
    }

    private static boolean isBookingNotIntersect(Booking booking) {
        if (approvedBookingsTable.size() == 0) {
            return true;
        } else {
            int count = 0;
            for (Booking b : approvedBookingsTable) {
                if (count > 0) return false;
                LocalDateTime startCreatedMeeting = b.getMeetingStartTime();
                LocalDateTime endCreatedMeeting = b.getMeetingEndTime();
                LocalDateTime currentStartMeeting = booking.getMeetingStartTime();
                LocalDateTime currentEndMeeting = booking.getMeetingEndTime();

                if ((startCreatedMeeting.isAfter(currentStartMeeting) || startCreatedMeeting.isEqual(currentStartMeeting)) &
                        (startCreatedMeeting.isBefore(currentEndMeeting) || startCreatedMeeting.isEqual(currentEndMeeting)) &
                        (endCreatedMeeting.isBefore(currentEndMeeting) || endCreatedMeeting.isEqual(currentEndMeeting))) {
                    count++;
                }
            }
            return count == 0;
        }
    }
}
