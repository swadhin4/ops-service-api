package com.ops.api.repo;

import java.util.List;

import com.ops.api.data.model.TicketVO;

public interface IncidentRepo {
	
	public List<TicketVO> findUserSiteTickets(String email, String dbName, String assignedTo, int offset, int rows);
}
