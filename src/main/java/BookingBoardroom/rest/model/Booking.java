package BookingBoardroom.rest.model;

import BookingBoardroom.rest.support.BookingDeserializer;
import BookingBoardroom.rest.support.BookingSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.time.LocalTime;

@JsonDeserialize(using = BookingDeserializer.class)
@JsonSerialize(using = BookingSerializer.class)
public class Booking {

    private LocalTime openOffice;
    private LocalTime closeOffice;
    private LocalDateTime requestSubmissionTime;
    private String employeeID;
    private LocalDateTime meetingStartTime;
    private LocalDateTime meetingEndTime;

    public Booking(LocalTime openOffice, LocalTime closeOffice, LocalDateTime requestSubmissionTime, String employeeID, LocalDateTime meetingStartTime, LocalDateTime meetingEndTime) {
        this.openOffice = openOffice;
        this.closeOffice = closeOffice;
        this.requestSubmissionTime = requestSubmissionTime;
        this.employeeID = employeeID;
        this.meetingStartTime = meetingStartTime;
        this.meetingEndTime = meetingEndTime;
    }

    public Booking() {
    }

    public LocalTime getOpenOffice() {
        return openOffice;
    }

    public void setOpenOffice(LocalTime openOffice) {
        this.openOffice = openOffice;
    }

    public LocalTime getCloseOffice() {
        return closeOffice;
    }

    public void setCloseOffice(LocalTime closeOffice) {
        this.closeOffice = closeOffice;
    }

    public LocalDateTime getRequestSubmissionTime() {
        return requestSubmissionTime;
    }

    public void setRequestSubmissionTime(LocalDateTime requestSubmissionTime) {
        this.requestSubmissionTime = requestSubmissionTime;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public LocalDateTime getMeetingStartTime() {
        return meetingStartTime;
    }

    public void setMeetingStartTime(LocalDateTime meetingStartTime) {
        this.meetingStartTime = meetingStartTime;
    }

    public LocalDateTime getMeetingEndTime() {
        return meetingEndTime;
    }

    public void setMeetingEndTime(LocalDateTime meetingEndTime) {
        this.meetingEndTime = meetingEndTime;
    }
}
