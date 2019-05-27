package BookingBoardroom.rest.support;

import BookingBoardroom.rest.model.Booking;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class BookingDeserializer extends JsonDeserializer<Booking> {

    @Autowired
    @Qualifier("env")
    private Environment env;

    @Override
    public Booking deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        TreeNode tn = jsonParser.readValueAsTree();

        LocalTime openOffice;
        LocalTime closeOffice;
        LocalDateTime requestSubmissionTime;
        String employeeID;
        LocalDateTime meetingStartTime;
        LocalDateTime meetingEndTime;

        if (tn.get("workTimeOffice") != null) {
            openOffice = LocalTime.parse(tn.get("workTimeOffice").toString().replaceAll("\"", "").split(" ")[0], DateTimeFormatter.ofPattern(env.getProperty("workTimePattern")));
            closeOffice = LocalTime.parse(tn.get("workTimeOffice").toString().replaceAll("\"", "").split(" ")[1], DateTimeFormatter.ofPattern(env.getProperty("workTimePattern")));
        } else {
            throw new JsonParseException(jsonParser, "Element 'workTimeOffice' not found.");
        }

        if (tn.get("requestSubmissionTime") != null) {
            requestSubmissionTime = LocalDateTime.now();
        } else {
            throw new JsonParseException(jsonParser, "Element 'requestSubmissionTime' not found.");
        }

        if (tn.get("employeeID") != null) {
            employeeID = tn.get("employeeID").toString().replaceAll("\"", "");
        } else {
            throw new JsonParseException(jsonParser, "Element 'employeeID' not found.");
        }

        if (tn.get("meetingStartTime") != null) {
            if (tn.get("duration") != null) {
                meetingStartTime = LocalDateTime.parse(tn.get("meetingStartTime").toString().replaceAll("\"", ""), DateTimeFormatter.ofPattern(env.getProperty("meetStartTimePattern")));
                meetingEndTime = meetingStartTime.plusHours(Long.valueOf(tn.get("duration").toString().replaceAll("\"", "")));
            } else {
                throw new JsonParseException(jsonParser, "Element 'duration' not found.");
            }
        } else {
            throw new JsonParseException(jsonParser, "Element 'meetingStartTime' not found.");
        }

        return new Booking(openOffice, closeOffice, requestSubmissionTime, employeeID, meetingStartTime, meetingEndTime);
    }
}
