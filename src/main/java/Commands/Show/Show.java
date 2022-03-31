package Commands.Show;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Show {
	private ShowRepository showRepo;
	
	private UUID host;
	private String word;
	private HashMap<UUID, String[]> givenLetters = new HashMap<UUID, String[]>();
	
	private List<UUID> users = new ArrayList<UUID>();
	
	public Show(ShowRepository showRepo, UUID host, UUID user) {
		this.showRepo = showRepo;
		this.host = host;
	}
	
	public void addUser(UUID user) {
		this.users.add(user);
	}
	
	public void removeUser(UUID user) {
		this.users.remove(user);
	}
	
	public List<UUID> getUsers() {
		return users;
	}

	public String getWord() {
		return word;
	}

	private void setWord(String word) {
		this.word = word;
		for(UUID uuid : this.givenLetters.keySet()) {
			this.givenLetters.put(uuid, new String[word.length()]);
		}
	}

	public HashMap<UUID, String[]> getGivenLetters() {
		return givenLetters;
	}

	public void addLetter(UUID uuid, String letter) {
		String[] letters = this.givenLetters.get(uuid);
		
		int count = 0;
		for(String lett : letters) {
			if(lett == null) letters[count] = letter;
			count++;
			continue;
		}
		this.givenLetters.put(uuid, letters);
	}

	public UUID getHost() {
		return host;
	}
	
	public boolean start(String word) {
		if(this.users.size() < 2) return false;
		setWord(word);
		return true;
	}
	
	public void stop() {
		showRepo.endShow(this);
	}
}
