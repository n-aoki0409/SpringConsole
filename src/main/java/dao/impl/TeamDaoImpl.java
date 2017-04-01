package dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import model.Team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;

import dao.TeamDao;

public class TeamDaoImpl implements TeamDao {

	@Autowired
	private DataSource dataSource;

	private static final String SELECT_TEAMLIST = "SELECT team_id, name FROM team";
	private static final String SELECT_TEAM_BY_TEAM_ID = "SELECT team_id, name FROM team WHERE team_id = ?";

	@Override
	public List<Team> getTeamList() throws DataAccessException {
		return new CustomMappingSqlQuery<Team> () {
			@Override
			protected Team mapRow(ResultSet rs, int rowNum) throws SQLException {
				Team team = new Team();
				team.setId(rs.getInt("team_id"));
				team.setName(rs.getString("name"));
				return team;
			}
		}
		.init(dataSource, SELECT_TEAMLIST)
		.execute();
	}

	@Override
	public Team getTeam(Integer teamId) throws DataAccessException {
		return new CustomMappingSqlQuery<Team> () {
			@Override
			protected Team mapRow(ResultSet rs, int rowNum) throws SQLException {
				Team team = new Team();
				team.setId(rs.getInt("team_id"));
				team.setName(rs.getString("name"));
				return team;
			}
		}
		.init(dataSource, SELECT_TEAM_BY_TEAM_ID,
				new SqlParameter("team_id", Types.INTEGER))
		.findObject(teamId);
	}
}