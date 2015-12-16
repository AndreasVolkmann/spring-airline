package com.airline.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.airline.controller.exception.FlightException;
import com.airline.service.AirlineServiceImpl;
import com.airline.service.request.ReservationRequest;
import com.airline.service.response.FlightExceptionInfo;
import com.airline.service.response.InfoResponse;
import com.airline.service.response.ReservationResponse;

/**
 * REST API controller class exposing all airline APIs
 * @author
 *
 */
@Controller
@RequestMapping(value = "/")
public class AirlineController{
	
	private AirlineServiceImpl airlineServiceImpl = null;
	
	@Autowired(required=true)
	@Qualifier(value="airlineServiceImpl")
	public void setAirlineServiceImpl(AirlineServiceImpl airlineServiceImpl){
		this.airlineServiceImpl = airlineServiceImpl;
	}
	
	/**
	 * API to fetch all one way flights
	 * @param from
	 * @param date
	 * @param numTickets
	 * @return
	 * @throws FlightException
	 */
	
	@RequestMapping(value="api/flightinfo/{from}/{date}/{numTickets}",
			method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<InfoResponse> getOneWayFlights(
			@PathVariable String from,@PathVariable String date,@PathVariable Integer numTickets) throws FlightException{
		InfoResponse infoResponse = airlineServiceImpl.getOneWayFlights(from, date, numTickets);
		return new ResponseEntity<InfoResponse>(infoResponse,HttpStatus.OK);
	}
	
	/**
	 * API to fetch all two way flights
	 * @param from
	 * @param to
	 * @param date
	 * @param numTickets
	 * @return
	 * @throws FlightException
	 */
	@RequestMapping(value="api/flightinfo/{from}/{to}/{date}/{numTickets}",
			method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<InfoResponse> getTwoWayFlights(
			@PathVariable String from,@PathVariable String to,@PathVariable String date,@PathVariable Integer numTickets)  throws FlightException{
		InfoResponse infoResponse = airlineServiceImpl.getTwoWayFlights(from, to, date, numTickets);
		return new ResponseEntity<InfoResponse>(infoResponse,HttpStatus.OK);
	}
	
	/**
	 * API to reserve a flight for set of passengars
	 * @param reservationRequest
	 * @return
	 * @throws FlightException
	 */
	@RequestMapping(value = "api/flightreservation", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<ReservationResponse> reserveFlight(
			@RequestBody ReservationRequest reservationRequest)  throws FlightException{
		ReservationResponse reservationResponse  = airlineServiceImpl.reserveFlight(reservationRequest);
		
		return new ResponseEntity<ReservationResponse>(reservationResponse,HttpStatus.OK);

	}
	
	/**
	 * Method to handle the flight exception
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(FlightException.class)
	public @ResponseBody FlightExceptionInfo handleFlightException(HttpServletRequest request, Exception ex){
		FlightExceptionInfo flightExceptionInfo = null;
		if(ex instanceof FlightException){
			FlightException flightException = (FlightException)ex;
			flightExceptionInfo = new FlightExceptionInfo(flightException.getHttpError(),
					flightException.getErrorCode(), flightException.getMessage());
		}else{
			flightExceptionInfo = new FlightExceptionInfo(500, 4, ex.getMessage());
		}
		return flightExceptionInfo;
	}
	
}
