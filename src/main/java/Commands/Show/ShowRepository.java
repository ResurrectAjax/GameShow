package Commands.Show;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShowRepository {
	private List<Show> shows = new ArrayList<Show>();
	
	public void createShow(UUID host, UUID user) {
		shows.add(new Show(this, host, user));
	}
	
	public Show getShowByHost(UUID host) {
		for(Show show : shows) {
			if(show.getHost().equals(host)) return show;
		}
		return null;
	}
	
	public void endShow(Show show) {
		shows.remove(show);
	}
}
