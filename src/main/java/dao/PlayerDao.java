package dao;

import java.util.List;

import model.Player;

import org.springframework.dao.DataAccessException;

public interface PlayerDao {

	void insertPlayer(Player player) throws DataAccessException;

	List<Player> getPlayerList(Integer teamId) throws DataAccessException;

	void deletePlayer(Player player) throws DataAccessException;

	void updatePlayer(Player player) throws DataAccessException;

	Player getPlayer(Integer id) throws DataAccessException;
}