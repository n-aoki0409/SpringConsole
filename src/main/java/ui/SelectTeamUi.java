package ui;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import model.Team;
import dao.TeamDao;

public class SelectTeamUi extends AbstractUi {

	@Autowired
	private TeamDao teamDao;

	@Override
	public void show() {
		// チーム一覧の表示
		showTeamList(this.teamDao.getTeamList());
		System.out.println("Enterを押してください。");
		getInputedString();
	}

	protected void showTeamList(List<Team> teamList) {
		System.out.println("--------------------");
		System.out.println("『選手名鑑』「チーム一覧」");
		System.out.println("ID    名前");
		for (Team team : teamList) {
			// チームIDとチーム名の表示
			System.out.printf("%d  %s%n", team.getId(), team.getName());
		}
	}
}
