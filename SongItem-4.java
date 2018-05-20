//import jm.music.*;

import jm.JMC;
import jm.music.data.*;
import jm.util.*;

import jm.util.Play;
import jm.util.Read;
import jm.util.Write;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class SongItem {
	String artistName;
	String songTitle;
	String albumTitle;
	String songFile;
	String coverFile;
	float [] songData;
	
	public SongItem(String artistName, String songTitle, String albumTitle, String songFile, String coverFile) {
		this.artistName = artistName;
		this.songTitle = songTitle;
		this.albumTitle = albumTitle;
		this.songFile = songFile;
		this.coverFile = coverFile;
		songData = Read.audio(songFile);
	}
	
	public void setSongTitle(String title) {
		songTitle = title;
	}
	
	public String getSongTitle() {
		return songTitle;
	}
	
	public String getArtistName() {
		return artistName;
	}
	
	public String getAlbumName() {
		return albumTitle;
	}
	
	public String getSongFile() {
		return this.songFile;
	}
	
	public String getAlbumCoverPath() {
		return this.coverFile;
	}
	
	public void playSong() {
		Play.pauseAudio();
		Play.au(getSongFile(), false);
		//Play.audioFile(getSongFile());
	}
	
	public void stopAudio() {
		Play.pauseAudio();
	}
	/*Public String getSongFile(){
	 * 
	 * 
	 * public void playSong(){
	 * 		play.stopAudio();
	 * 		play.au(getSongFile(), false);
	 * }
	 */
	

}
