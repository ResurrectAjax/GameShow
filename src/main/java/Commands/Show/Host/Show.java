package Commands.Show.Host;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Show {
	private UUID host;
	private String word;
	private LinkedHashMap<UUID, String[]> givenLetters = new LinkedHashMap<UUID, String[]>();
	private List<UUID> guessingUsers = new ArrayList<UUID>();
	
	private List<UUID> users = new ArrayList<UUID>();
	
	public Show(UUID host, UUID user) {
		this.host = host;
		addUser(user);
	}
	
	
	
	public List<UUID> getGuessingUsers() {
		return guessingUsers;
	}



	public void addGuessingUser(UUID guessingUser) {
		this.guessingUsers.add(guessingUser);
	}



	public void addUser(UUID user) {
		this.users.add(user);
	}
	
	public void removeUser(UUID user) {
		this.users.remove(user);
		this.guessingUsers.remove(user);
	}
	
	public List<UUID> getUsers() {
		return users;
	}

	public String getWord() {
		return word;
	}

	private void setWord(String word) {
		this.word = word;
		for(UUID user : users) {
			this.givenLetters.put(user, new String[word.length()]);
		}
	}

	public LinkedHashMap<UUID, String[]> getGivenLetters() {
		return givenLetters;
	}

	private void addLetter(UUID uuid, int index) {
		this.getGivenLetters().get(uuid)[index] = String.valueOf(word.charAt(index));
	}
	
	public String giveRandomLetter(UUID uuid) {
		Random random = new Random();
		while(true) {
			int index = random.nextInt(word.length());
			if(this.givenLetters.containsKey(uuid) && this.givenLetters.get(uuid)[index] != null) continue;
			else {
				addLetter(uuid, index);
				if(hasAllLetters(uuid)) return null;
				
				return String.valueOf(word.charAt(index));
			}
		}
	}
	
	public boolean hasAllLetters(UUID uuid) {
		for(int i = 0; i < word.length(); i++) {
			String letter = this.givenLetters.get(uuid)[i];
			if(!String.valueOf(word.charAt(i)).equalsIgnoreCase(letter)) return false;
		}
		return true;
	}

	public UUID getHost() {
		return host;
	}
	
	public boolean start(String word) {
		if(this.users.size() < 1) return false;
		setWord(word);
		return true;
	}
	
	public void reset() {
		this.word = null;
		this.givenLetters.clear();
		this.guessingUsers.clear();
	}
}
