package com.daralmathour.altaefhessan;

public class Item {
	
	private String Mp3Url;
	private String Mp3Name;
	private String Duration;

	public Item(String Mp3Url, String Mp3Name, String Duration) {
		this.Mp3Url = Mp3Url;
		this.Mp3Name = Mp3Name;
		this.Duration = Duration;
	}

	public String getMp3Url() {
		return Mp3Url;
	}

	public void setMp3Url(String mp3Url) {
		Mp3Url = mp3Url;
	}

	public String getMp3Name() {
		return Mp3Name;
	}

	public void setMp3Name(String mp3Name) {
		Mp3Name = mp3Name;
	}

	public String getDuration() {
		return Duration;
	}

	public void setDuration(String duration) {
		Duration = duration;
	}
}
