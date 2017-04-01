package ui;

import model.Player;
import model.Team;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import dao.PlayerDao;
import dao.TeamDao;

public class UpdatePlayerUi extends AbstractUi {

	@Autowired
	private PlayerDao playerDao;

	@Autowired
	private TeamDao teamDao;

	@Override
	public void show() {

		Player player = getPlayer();
		if (player == null) {
			return;
		}

		String name = getName(player);
		if (StringUtils.isNotEmpty(name)) {
			player.setName(name);
		}

		Team team = getTeam(player);
		if (team != null) {
			player.setTeam(team);
		}

		this.playerDao.updatePlayer(player);
		System.out.printf("選手ID「%s」の選手を、選手名「%s」、チーム「%s」に修正しました。%n", player.getId(), player.getName(), player.getTeam().getName());
	}

	protected Player getPlayer() {
		final String playerId = "選手ID";
		// メニューの表示
		showMenu(playerId);
		System.out.println("なにも入力せずにEnterを押すとメニューに戻ります。");
		// コンソールへの入力を取得
		String id = getInputedString();
		// 文字列が入力されているか
		if (StringUtils.isEmpty(id)) {
			return null;
			// 数値か
		} else if (UiUtils.isNumeric(id, playerId)) {
			// IDで選手を検索
			Player player = this.playerDao.getPlayer(Integer.valueOf(id));

			if (player == null) {
				// 該当する選手が存在しない
				System.out.printf("入力された選手ID「%s」の選手は存在しませんでした。%n", id);
				return getPlayer();
			}
			return player;
		}
		return getPlayer();
	}

	protected String getName(Player player) {
		final String playerName = "選手名";
		// メニューを表示
		showMenu(playerName);
		System.out.println("なにも入力せずにEnterを押すと変更しません。");
		System.out.printf("元の値：%s%n", player.getName());
		// コンソールへの入力を取得
		String name = getInputedString();
		// 文字列が入力されているか
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		// 128文字以下か
		if (UiUtils.isSmallLength(name, playerName, 128)) {
			return name;
		}
		return getName(player);
	}

	protected Team getTeam(Player player) {
		final String teamId = "チームID";
		// メニューを表示
		showMenu(teamId);
		System.out.println("なにも入力せずにEnterを押すと変更しません。");
		System.out.printf("元の値：%s %s%n", player.getTeam().getId(), player.getTeam().getName());
		// コンソールへの入力を取得
		String id = getInputedString();
		// 文字列が入力されているか
		if (StringUtils.isEmpty(id)) {
			return null;
		}
		// 数値か
		if (UiUtils.isNumeric(id, teamId)) {
			// IDでチームを検索
			Team team = this.teamDao.getTeam(Integer.valueOf(id));
			if (team == null) {
				// 該当するチームが存在しない
				System.out.printf("入力されたチーム名「%s」のチームは存在しませんでした。%n", id);
				return getTeam(player);
			}
			return team;
		}
		return getTeam(player);
	}

	protected void showMenu(String wanted) {
		System.out.println("--------------------");
		System.out.println("『選手名鑑』「選手修正」");
		System.out.println("");
		System.out.printf("%sを入力し、Enterを押してください。", wanted);
	}
}
