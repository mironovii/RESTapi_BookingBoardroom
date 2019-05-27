package BookingBoardroom.rest.support;

import BookingBoardroom.rest.model.Booking;

import java.util.Comparator;

public class CompByDateOfMeeting implements Comparator<Booking> {
    @Override
    public int compare(Booking o1, Booking o2) {
        if (o1.getMeetingStartTime().isBefore(o2.getMeetingStartTime())) return -1;
        else if (o1.getMeetingStartTime().isEqual(o2.getMeetingStartTime()) &
                o1.getMeetingEndTime().isEqual(o2.getMeetingEndTime()) &
                o1.getRequestSubmissionTime().isEqual(o2.getRequestSubmissionTime())) return 0;
        return 1;
    }
}
