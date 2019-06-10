package com.ops.api.service;

import java.util.List;

import com.ops.api.data.model.LoginUser;
import com.ops.api.data.model.TicketVO;


public interface TicketService {

	public List<TicketVO> getAllCustomerTickets(String username, String assignedTo) throws Exception;
	
	
}
