package util;

import java.io.File;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;

public class Mp3Info {
	
	String mp3FileName;
	Map<String,Object> properties;
	String track;
	boolean copyright;
	int framesLength;
	String album;
	String date;
	String genre;
	String artist;
	String title;
	String composer;
	String encoding;
	double fps;
	Duration duration;
	int samplingFrequency;
	boolean vbr;
	boolean original;
	public Mp3Info(String mp3FileName) {
		super();
		this.mp3FileName = mp3FileName;
		try {
            File file = new File(mp3FileName);
            AudioFileFormat baseFileFormat = AudioSystem.getAudioFileFormat(file);
            properties = baseFileFormat.properties();
//            for(Object o : properties.keySet())
//            {
//            	System.out.println("property name : " + o.toString() + " : " 
//            			+ properties.get(o.toString()).toString());
//            	
//            }
            
            track = properties.get("mp3.id3tag.track").toString();
        	copyright = (Boolean)properties.get("mp3.copyright");
        	framesLength = (Integer)properties.get("mp3.length.frames");
        	album = properties.get("album").toString();
        	date = properties.get("date").toString();
        	genre = properties.get("mp3.id3tag.genre").toString();
        	artist = properties.get("author").toString();
        	title = properties.get("title").toString();
        	composer = properties.get("mp3.id3tag.composer").toString();
        	encoding = properties.get("mp3.version.encoding").toString();
        	fps = (Float)properties.get("mp3.framerate.fps");
            duration = new Duration((Long)properties.get("duration"));
            samplingFrequency = (Integer)properties.get("mp3.frequency.hz");
        	vbr = (Boolean)properties.get("mp3.vbr");
        	original = (Boolean)properties.get("mp3.original");
         }
        catch (Exception e) {
            System.out.println(e);
        }
		
		
		
		
	}
	public Mp3Info(File currFile) {
		try {
            File file = currFile;
            this.mp3FileName = currFile.getAbsolutePath() + currFile.getName();
            System.out.println("getting info for file : " + this.mp3FileName);
            AudioFileFormat baseFileFormat = AudioSystem.getAudioFileFormat(file);
            properties = baseFileFormat.properties();
//            for(Object o : properties.keySet())
//            {
//            	System.out.println("property name : " + o.toString() + " : " 
//            			+ properties.get(o.toString()).toString());
//            	
//            }
            
            track = properties.get("mp3.id3tag.track").toString();
        	copyright = (Boolean)properties.get("mp3.copyright");
        	framesLength = (Integer)properties.get("mp3.length.frames");
        	album = properties.get("album").toString();
        	date = properties.get("date").toString();
        	genre = properties.get("mp3.id3tag.genre").toString();
        	artist = properties.get("author").toString();
        	title = properties.get("title").toString();
        	composer = properties.get("mp3.id3tag.composer").toString();
        	encoding = properties.get("mp3.version.encoding").toString();
        	fps = (Float)properties.get("mp3.framerate.fps");
            duration = new Duration((Long)properties.get("duration"));
            samplingFrequency = (Integer)properties.get("mp3.frequency.hz");
        	vbr = (Boolean)properties.get("mp3.vbr");
        	original = (Boolean)properties.get("mp3.original");
         }
        catch (Exception e) {
            System.out.println(e);
        }
	}
	public String getMp3FileName() {
		return mp3FileName;
	}
	public Map<String,Object> getProperties() {
		return properties;
	}
	public String getTrack() {
		return track;
	}
	public boolean isCopyright() {
		return copyright;
	}
	public int getFramesLength() {
		return framesLength;
	}
	public String getAlbum() {
		return album;
	}
	public String getDate() {
		return date;
	}
	public String getGenre() {
		return genre;
	}
	public String getArtist() {
		return artist;
	}
	public String getTitle() {
		return title;
	}
	public String getComposer() {
		return composer;
	}
	public String getEncoding() {
		return encoding;
	}
	public double getFps() {
		return fps;
	}
	public Duration getDuration() {
		return duration;
	}
	public int getSamplingFrequency() {
		return samplingFrequency;
	}
	public boolean isVbr() {
		return vbr;
	}
	public boolean isOriginal() {
		return original;
	}
	@Override
	public String toString() {
		try{
		return "Mp3Info \nmp3FileName = " + mp3FileName + "\n properties = "
				+ properties + "\n track = " + track + "\n copyright = " + copyright
				+ "\n framesLength = " + framesLength + "\n album = " + album
				+ "\n date = " + date + "\n genre = " + genre + "\n artist = "  + artist
				+ "\n title = " + title + "\n composer = " + composer + "\n encoding = "
				+ encoding + "\n fps = " + fps + "\n duration = " + (duration !=null ?duration.getHumanReadableForm():"null")
				+ "\n samplingFrequency = " + samplingFrequency + "\n vbr = " + vbr
				+ "\n original = " + original + "\n";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	

}
