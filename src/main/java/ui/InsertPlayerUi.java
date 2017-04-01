package ui;

import model.Player;
import model.Team;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import dao.PlayerDao;
import dao.TeamDao;

public class InsertPlayerUi extends AbstractUi {

	@Autowired
	private TeamDao teamDao;

	@Autowired
	private PlayerDao playerDao;

	@Override
	public void show() {
		final String playerName = "選手名";
		// メニューの表示
		showMenu(playerName);
		// コンソールへの入力を取得
		String name = getInputedString();
		// 文字列が入力されているか
		if (StringUtils.isEmpty(name)) {
			// メニューへ戻る
			return;
			// 128文字以下か
		} else if (UiUtils.isSmallLength(name, playerName, 128)) {
			// 新しい選手を生成
			Player player = new Player();
			player.setName(name);
			// チームを決定
			showTeamField(player);
		} else {
			show();
		}
	}

	protected void showTeamField(Player player) {
		final String teamId = "チームID";
		// メニューを表示
		showMenu(teamId);
		// コンソールへの入力を取得
		String id = getInputedString();
		// 文字列が入力されているか
		if (StringUtils.isEmpty(id)) {
			return;
			// 数値か
		} else if (UiUtils.isNumeric(id, teamId)) {
			// IDでチームを検索
			Team team = this.teamDao.getTeam(Integer.valueOf(id));
			if (team == null) {
				// 該当するチームは存在しない
				System.out.printf("入力されたチーム名「%s」のチームは存在しませんでした。%n", id);
				showTeamField(player);
			} else {
				// チームを選手にセット
				player.setTeam(team);
				// データベースに選手を登録
				playerDao.insertPlayer(player);
				System.out.printf("チーム「%s」に「%s」選手を追加しました。%n", team.getName(), player.getName());
			}
		}
	}

	protected void showMenu(String wanted) {
		System.out.println("--------------------");
		System.out.println("『選手名鑑』「選手追加」");
		System.out.println("");
		System.out.printf("%sを入力し、Enterを押してください。%n", wanted);
		System.out.println("なにも入力せずにEnterを押すとメニューに戻ります。");
	}
}
