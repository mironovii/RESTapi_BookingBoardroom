<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bookings</title>
</head>

<script src="//ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript">

    var prefix = '/bookings';

    var RestGetAllBookings = function () {
        $.ajax({
            type: 'GET',
            url: prefix,
            dataType: 'json',
            async: true,
            success: function (result) {
                var output = "";
                for (var i in result) {
                    output += i + ". " + result[i].employeeID + " " + result[i].meetingDate + " " + result[i].meetingDurationTime + "\n";
                }
                alert(output);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.status + ' ' + jqXHR.responseText);
            }
        });
    };

    var RestGetBookingsAtDate = function () {

        var form = document.forms.getBookingsAtDate;

        $.ajax({
            type: 'GET',
            url: prefix + '/' + form.elements[0].value,
            dataType: 'json',
            async: true,
            success: function (result) {
                var output = "";
                for (var i in result) {
                    output += i + ". " + result[i].employeeID + " " + result[i].meetingDate + " " + result[i].meetingDurationTime + "\n";
                }
                alert(output);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.status + ' ' + jqXHR.responseText);
            }
        });
    };

    var RestPostBooking = function () {

        var form = document.forms.postBooking;

        $.ajax({
            type: 'POST',
            url: prefix,
            dataType: 'json',
            async: true,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                "workTimeOffice": form.elements[0].value,
                "requestSubmissionTime": "",
                "employeeID": form.elements[1].value,
                "meetingStartTime": form.elements[2].value,
                "duration": form.elements[3].value
            }),
            success: function (result) {
                alert('Your booking request was sent!');
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.status + ' ' + jqXHR.responseText);
            }
        });
    };

    var CheckPostBookingForm = function () {
        var form = document.forms.postBooking;

        if (form.elements[0].value === "" || form.elements[1].value === "" ||
            form.elements[2].value === "" || form.elements[3].value === "") {
            alert('Fill all labels, please.')
            return false;
        }
        return true;
    };

    var CheckDateForm = function () {
        var form = document.forms.getBookingsAtDate;

        if (form.elements[0].value === "") {
            alert('Fill date, please.')
            return false;
        }
        return true;
    };

</script>

<body>
<h3>REST API for booking boardroom.</h3>
<h3>
    ________________________________________________________________________________________________________________________</h3>
<button type="button" onclick="RestGetAllBookings()">Current bookings</button>
<h3>
    ________________________________________________________________________________________________________________________</h3>
<form id="getBookingsAtDate" name="getBookingsAtDate" action="javascript:"
      onsubmit="if(CheckDateForm()) RestGetBookingsAtDate()">
    <label for="date">Date:</label><input id="date" name="date" value="" type="text">
    <input value="Get Bookings" type="submit">
</form>
<h3>
    ________________________________________________________________________________________________________________________</h3>
<form id="postBooking" name="postBooking" action="javascript:" onsubmit="if(CheckPostBookingForm())RestPostBooking()">
    <label for="WorkTimeOffice">workTimeOffice:</label><input id="workTimeOffice" name="workTimeOffice" value=""
                                                              type="text">
    <label for="EmployeeID">employeeID:</label><input id="employeeID" name="employeeID" value="" type="text">
    <label for="MeetingStartTime">meetingStartTime:</label><input id="meetingStartTime" name="meetingStartTime" value=""
                                                                  type="text">
    <label for="Duration">duration:</label><input id="duration" name="duration" value="" type="text">
    <input value="Send" type="submit">
</form>
</body>
</html>
