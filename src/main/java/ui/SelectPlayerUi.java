package ui;

import java.util.List;

import model.Player;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import dao.PlayerDao;

public class SelectPlayerUi extends AbstractUiTemplate {

	@Autowired
	private PlayerDao playerDao;

	@Autowired
	private UpdatePlayerUi updatePlayerUi;

	@Autowired
	private DeletePlayerUi deletePlayerUi;

	@Override
	public void show() {
		// ヘッダーを表示
		showHeader();
		// コンソールへの入力を取得
		Integer teamId = getTeamId();
		// 選手一覧を表示
		List<Player> playerList = this.playerDao.getPlayerList(teamId);
		if (playerList.isEmpty()) {
			System.out.printf("チームID%sに所属する選手は存在しません。%n", teamId);
			return;
		}
		showPlayerList(playerList);
		System.out.println("Enterを押してください。");
		getInputedString();
		super.show();
	}

	protected Integer getTeamId() {
		System.out.println("一覧表示するチームのIDを入力し、Enterを押してください。");
		// コンソールへの入力を取得
		String teamId = getInputedString();
		// 数値か
		if (StringUtils.isNotEmpty(teamId) && StringUtils.isNumeric(teamId)) {
			return Integer.valueOf(teamId);
		}
		return getTeamId();
	}

	protected void showHeader() {
		System.out.println("--------------------");
		System.out.println("『選手名鑑』「選手一覧」");
		System.out.println("");
	}

	@Override
	protected void showMenu() {
		showHeader();
		System.out.println("1.選手修正");
		System.out.println("2.選手削除");
		System.out.println("3.メニューに戻る");
		System.out.println("");
		System.out.println("番号を入力し、Enterを押してください。");
	}

	protected void showPlayerList(List<Player> playerList) {
		System.out.println("--------------------");
		System.out.println("『選手名鑑』「選手一覧」");
		// 選手が1人でもいるか
		if (!playerList.isEmpty()) {
			Player player = playerList.get(0);
			System.out.printf("チーム名：%s%n", player.getTeam().getName());
		}
		System.out.println("ID    名前");
		for (Player player : playerList) {
			// 選手IDと選手名の表示
			System.out.printf("%d  %s%n", player.getId(), player.getName());
		}
	}

	@Override
	protected int getMaxMenuNumber() {
		return 3;
	}

	@Override
	protected int getMinMenuNumber() {
		return 1;
	}

	@Override
	protected void execute(int number) {
		switch (number) {
		case 1:
			// 1.選手修正
			updatePlayerUi.show();
			break;
		case 2:
			// 2.選手削除
			deletePlayerUi.show();
			break;
		default:
			return;
		}
	}
}
