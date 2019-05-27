package BookingBoardroom.rest.controller;

import BookingBoardroom.rest.DAO.BookingDAO;
import BookingBoardroom.rest.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class BookingRestController {

    @Autowired
    private BookingDAO bookingDAO;

    @GetMapping("/bookings")
    public ResponseEntity getBookings() {
        if (bookingDAO.list().isEmpty()) {
            return new ResponseEntity("Bookings not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(bookingDAO.list(), HttpStatus.OK);
    }

    @GetMapping("/bookings/{date}")
    public ResponseEntity getDayTable(@PathVariable String date) {
        Set<Booking> dayTable = bookingDAO.getDayTable(date);
        if (dayTable.isEmpty()) {
            return new ResponseEntity("No bookings found for date " + date, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(dayTable, HttpStatus.OK);
    }

    @PostMapping(value = "/bookings")
    public ResponseEntity createBooking(@RequestBody Booking booking) {
        bookingDAO.create(booking);
        return new ResponseEntity(booking, HttpStatus.OK);
    }
}
