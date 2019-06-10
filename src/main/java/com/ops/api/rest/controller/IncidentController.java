package com.ops.api.rest.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ops.api.data.model.RestResponse;
import com.ops.api.data.model.TicketVO;
import com.ops.api.service.TicketService;

@Controller
@RequestMapping(value = "/incident")
public class IncidentController {
	private static final Logger logger = LoggerFactory.getLogger(IncidentController.class);
	
	@Autowired
	TicketService ticketService;
	
	@RequestMapping(value = "/v1/list/{assignedTo}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RestResponse> listAllTickets(@RequestParam(value="user") String user,
			@PathVariable(value = "assignedTo") final String assignedTo) {
		RestResponse response = new RestResponse();
		ResponseEntity<RestResponse> responseEntity = new ResponseEntity<RestResponse>(HttpStatus.NO_CONTENT);
		try {
			List<TicketVO> tickets = ticketService.getAllCustomerTickets(user, assignedTo);
			if(!tickets.isEmpty()){
				response.setStatusCode(200);
				response.setObject(tickets);
				responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.OK);
			}
		} catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("User authorization failed");
			responseEntity = new ResponseEntity<RestResponse>(response,HttpStatus.NON_AUTHORITATIVE_INFORMATION);
			logger.info("Exception in getting ticket list response", e);
		}

		return responseEntity;
	}
}
