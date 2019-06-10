package com.ops.api.service.impl;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ops.api.data.model.LoginUser;
import com.ops.api.data.model.TicketVO;
import com.ops.api.repo.IncidentRepo;
import com.ops.api.repo.UserClientRepo;
import com.ops.api.service.TicketService;

@Service("ticketService")
public class TicketServiceImpl implements TicketService {

	private final static Logger LOGGER = LoggerFactory.getLogger(TicketServiceImpl.class);

	@Autowired
	UserClientRepo userClientRepo;

	@Autowired
	IncidentRepo incidentRepo;

	@Override
	public List<TicketVO> getAllCustomerTickets(String username, final String assignedTo) throws Exception {
		LOGGER.info("Inside TicketServiceImpl - getAllCustomerTickets");
		LoginUser user = userClientRepo.getUserDetails(username);
		List<TicketVO> customerTicketList = incidentRepo.findUserSiteTickets(user.getUsername(), user.getDbName(),
				assignedTo, 0, 10);
		LOGGER.info("Total incidents pulled :"+ customerTicketList.size());
		LOGGER.info("Exit TicketServiceImpl - getAllCustomerTickets");
		return customerTicketList == null ? Collections.emptyList() : customerTicketList;
	}

}
