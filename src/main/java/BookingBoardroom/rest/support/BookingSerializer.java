package BookingBoardroom.rest.support;

import BookingBoardroom.rest.model.Booking;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Service
public class BookingSerializer extends JsonSerializer<Booking> {

    @Autowired
    @Qualifier("env")
    private Environment env;

    @Override
    public void serialize(Booking booking, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("meetingDurationTime",
                booking.getMeetingStartTime().toLocalTime().format(DateTimeFormatter.ofPattern(env.getProperty("MeetStartEndTimePattern"))) + " " +
                        booking.getMeetingEndTime().toLocalTime().format(DateTimeFormatter.ofPattern(env.getProperty("MeetStartEndTimePattern"))));
        jsonGenerator.writeStringField("meetingDate",
                booking.getMeetingEndTime().toLocalDate().format(DateTimeFormatter.ofPattern(env.getProperty("meetDurTimePattern"))));
        jsonGenerator.writeStringField("employeeID", booking.getEmployeeID());
        jsonGenerator.writeEndObject();
    }
}
