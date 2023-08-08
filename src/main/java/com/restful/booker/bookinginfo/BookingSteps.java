package com.restful.booker.bookinginfo;

import com.restful.booker.constants.EndPoints;
import com.restful.booker.model.BookingPojo;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

/**
 * Created by Jay
 */
public class BookingSteps {

    @Step("Creating token for booking")
    public ValidatableResponse createToken(String username, String password) {
        BookingPojo authPojo = BookingPojo.getAuthPojo(username, password);
        return SerenityRest.given()
                .header("Content-Type", "application/json")
                .when()
                .body(authPojo)
                .post(EndPoints.CREATE_TOKEN)
                .then().log().all().statusCode(200);
    }

    @Step("Creating booking with firstname : {0}, lastname : {1}, totalprice : {2}, depositpaid : {3}, additionalneeds : {4}")
    public ValidatableResponse createBooking(String firstname, String lastname, int totalprice, boolean depositpaid, String checkin, String checkout, String additionalneeds) {
        BookingPojo bookingPojo = BookingPojo.getBookingPojo(firstname, lastname, totalprice, depositpaid, checkin, checkout, additionalneeds);
        return SerenityRest.given()
                .header("Content-Type", "application/json")
                .when()
                .body(bookingPojo)
                .post(EndPoints.ALL_BOOKING)
                .then().log().all().statusCode(200);
    }

    @Step("Getting the booking information with firstName : {0}")
    public ValidatableResponse getBookingByFirstName(int id) {
        return SerenityRest.given()
                .pathParam("id", id)
                .when()
                .get(EndPoints.GET_SINGLE_BOOKING_BY_ID)
                .then().log().all()
                .statusCode(200);

    }

    @Step("Updating booking information with id : {0} ")
    public ValidatableResponse updatBooking(int id,String token) {
        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname("prime");
        bookingPojo.setTotalprice(235);
        return SerenityRest.given()
                .header("Content-Type", "application/json")
                .header("Cookie", "token="+token)
                .header("Accept","application/json")
                .pathParam("id", id)
                .when()
                .body(bookingPojo)
                .patch(EndPoints.UPDATE_BOOKING_BY_ID)
                .then().log().all()
                .statusCode(200);
    }

    @Step("Deleting booking information with bookingId : {0}")
    public ValidatableResponse deleteBooking(int id,String token) {
        return SerenityRest.given()
                .header("Content-Type", "application/json")
                .header("Cookie", "token="+token)
                .pathParam("id", id)
                .when()
                .delete(EndPoints.DELETE_BOOKING_BY_ID)
                .then().log().all()
                .statusCode(201);
    }

    @Step("Getting booking information with bookingId : {0}")
    public ValidatableResponse getStudentById(int id,String token) {
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Cookie", "token="+token)
                .pathParam("id", id)
                .when()
                .get(EndPoints.GET_SINGLE_BOOKING_BY_ID)
                .then().log().all()
                .statusCode(404);
    }

}
