package com.ops.api.repo.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.ops.api.data.model.TicketVO;
import com.ops.api.ds.config.ConnectionManager;
import com.ops.api.exception.OpsDatabaseException;
import com.ops.api.repo.IncidentRepo;
import com.ops.api.util.AppUtil;
import com.ops.api.util.QueryUtils;

@Repository
public class IncidentRepoImpl implements IncidentRepo {
	private final static Logger LOGGER = LoggerFactory.getLogger(IncidentRepoImpl.class);

	@Autowired
	@Qualifier("templateOpsTenantSQL")
	private JdbcTemplate templateOpsTenantSQL;

	@Autowired
	@Qualifier("opsTenantDataSource")
	DataSource opsTenantDataSource;

	
	@Autowired
	private QueryUtils queryUtils;

	@Autowired
	ConnectionManager connectionManager;

	public List<TicketVO> findUserSiteTickets(String email, String dbName, String assignedTo, int offset, int rows) {
		LOGGER.info("Inside IncidentRepoImpl... findUserSiteTickets");
		List<TicketVO> ticketList = null;
		try {
			JdbcTemplate customerJdbcTemplate = ConnectionManager.getInstance(dbName).getJdbcTemplate();
			ticketList = customerJdbcTemplate.query(queryUtils.getIncidentListQuery(), new Object[] { email },
					new ResultSetExtractor<List<TicketVO>>() {
						@Override
						public List<TicketVO> extractData(ResultSet rs) throws SQLException, DataAccessException {
							List<TicketVO> ticketList = new ArrayList<TicketVO>();
							while (rs.next()) {
								TicketVO ticketVO = new TicketVO();
								ticketVO.setTicketId(rs.getLong("ticketId"));
								ticketVO.setTicketNumber(rs.getString("ticketNumber"));
								ticketVO.setDescription(rs.getString("ticketDesc"));
								ticketVO.setSiteId(rs.getLong("siteId"));
								ticketVO.setSiteName(rs.getString("siteName"));
								ticketVO.setCategoryId(rs.getLong("ticketCategoryId"));
								ticketVO.setCategoryName(rs.getString("ticketCategory"));
								ticketVO.setStatusId(rs.getLong("statusId"));
								ticketVO.setStatus(rs.getString("status"));
								ticketVO.setAssignedTo(rs.getLong("assignedToSP"));
								ticketVO.setAssignedSP(rs.getString("spName"));
								ticketVO.setPriorityDescription(rs.getString("priority"));
								ticketVO.setTicketStartTime(AppUtil.getFormatedDate(rs.getString("issueStartTime")));
								ticketVO.setSla(AppUtil.getFormatedDate(rs.getString("slaDueDate")));
								ticketVO.setCreatedOn(AppUtil.getFormatedDate(rs.getString("createdOn")));
								ticketList.add(ticketVO);
							}
							return ticketList;
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
			throw new OpsDatabaseException("Unable to fetch the ticket list");
		}

		LOGGER.info("Exit IncidentRepoImpl... findUserSiteTickets");
		return ticketList == null ? Collections.emptyList() : ticketList;
	}

}
