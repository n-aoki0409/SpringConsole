package dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import model.Player;
import model.Team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

import dao.PlayerDao;

public class PlayerDaoImpl implements PlayerDao {

	@Autowired
	private DataSource dataSource;

	private static final String INSERT_PLAYER = "INSERT INTO player(name, team_id) VALUES(?, ?)";
	private static final String SELECT_PLAYER_BY_PLAYER_ID = "SELECT player_id, player.name AS playerName, "
			+ " team.team_id, team.name AS teamName "
			+ " FROM player, team WHERE player.team_id = team.team_id AND player_id = ?";
	private static final String SELECT_PLAYER_LIST_BY_TEAM_ID = "SELECT player_id, player.name AS playerName, "
			+ " team.team_id, team.name AS teamName "
			+ " FROM player, team WHERE player.team_id = team.team_id AND team.team_id = ?";
	private static final String DELETE_PLAYER = "DELETE FROM player WHERE player_id = ?";
	private static final String UPDATE_PLAYER = "UPDATE player SET name = ?, team_id = ? WHERE player_id = ?";

	@Override
	public void insertPlayer(Player player) throws DataAccessException {
		// 選手の追加
		SqlUpdate sqlUpdate = new SqlUpdate(dataSource, INSERT_PLAYER);
		sqlUpdate.declareParameter(new SqlParameter("name", Types.VARCHAR));
		sqlUpdate.declareParameter(new SqlParameter("team_id", Types.INTEGER));
		sqlUpdate.compile();

		sqlUpdate.update(player.getName(), player.getTeam().getId());
	}

	@Override
	public Player getPlayer(Integer id) throws DataAccessException {
		// 選手の検索
		return new CustomMappingSqlQuery<Player> () {
			@Override
			protected Player mapRow(ResultSet rs, int rowNum) throws SQLException {
				Player player = new Player();
				player.setId(rs.getInt("player_id"));
				player.setName(rs.getString("playerName"));
				Team team = new Team();
				team.setId(rs.getInt("team_id"));
				team.setName(rs.getString("teamName"));
				player.setTeam(team);

				return player;
			}
		}
		.init(dataSource, SELECT_PLAYER_BY_PLAYER_ID,
				new SqlParameter("player_id", Types.INTEGER))
		.findObject(id);
	}

	@Override
	public List<Player> getPlayerList(Integer teamId) throws DataAccessException {
		// 選手一覧の検索
		return new CustomMappingSqlQuery<Player> () {
			@Override
			protected Player mapRow(ResultSet rs, int rowNum) throws SQLException {
				Player player = new Player();
				player.setId(rs.getInt("player_id"));
				player.setName(rs.getString("playerName"));
				Team team = new Team();
				team.setId(rs.getInt("team_id"));
				team.setName(rs.getString("teamName"));
				player.setTeam(team);

				return player;
			}
		}
		.init(dataSource, SELECT_PLAYER_LIST_BY_TEAM_ID,
				new SqlParameter("team_id", Types.INTEGER))
		.execute(teamId);
	}

	@Override
	public void deletePlayer(Player player) throws DataAccessException {
		// 選手の削除
		SqlUpdate sqlUpdate = new SqlUpdate(dataSource, DELETE_PLAYER);
		sqlUpdate.declareParameter(new SqlParameter("player_id", Types.INTEGER));
		sqlUpdate.compile();

		sqlUpdate.update(player.getId());
	}

	@Override
	public void updatePlayer(Player player) throws DataAccessException {
		// 選手の更新
		SqlUpdate sqlUpdate = new SqlUpdate(dataSource, UPDATE_PLAYER);
		sqlUpdate.declareParameter(new SqlParameter("name", Types.VARCHAR));
		sqlUpdate.declareParameter(new SqlParameter("team_id", Types.INTEGER));
		sqlUpdate.declareParameter(new SqlParameter("player_id", Types.INTEGER));
		sqlUpdate.compile();

		sqlUpdate.update(player.getName(), player.getTeam().getId(), player.getId());
	}
}
