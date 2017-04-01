package ui;

import model.Player;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import dao.PlayerDao;

public class DeletePlayerUi extends AbstractUi {

	@Autowired
	private PlayerDao playerDao;

	@Override
	public void show() {
		// メニューの表示
		showMenu();
		// コンソールへの入力を取得
		String id = getInputedString();
		// 文字列が入力されているか
		if (StringUtils.isEmpty(id)) {
			return;
			// 数値か
		} else if (UiUtils.isNumeric(id, "選手ID")) {
			// IDで選手を取得
			Player player = this.playerDao.getPlayer(Integer.valueOf(id));
			if (player == null) {
				// 該当する選手が存在しない
				System.out.printf("入力された選手ID「%s」の選手は存在しませんでした。%n", id);
				show();
			} else {
				// 選手を削除
				this.playerDao.deletePlayer(player);
				System.out.printf("選手ID「%s」の選手を削除しました。%n", id);
			}
		} else {
			show();
		}
	}

	protected void showMenu() {
		System.out.println("--------------------");
		System.out.println("『選手名鑑』「選手削除」");
		System.out.println("");
		System.out.println("選手IDを入力し、Enterを押してください。");
		System.out.println("なにも入力せずにEnterを押すとメニューに戻ります。");
	}
}
