package Utility;

import javafx.scene.media.AudioClip;

public class AudioUtility {

	private static final String backgroundMusicLocation = "resource/Audio/background/City-loop.mp3";
	private static AudioClip backgroundMusic;
	private static AudioClip gameMusic;
	private static AudioClip JumpSFX;
	private static AudioClip ClickSFX;
	private static AudioClip GotHitSFX;
	private static AudioClip GameOverSFX;
	private static AudioClip ActivatedSFX;
	private static AudioClip DeactivatedSFX;
	
	private static int Volume;
	
	static {
		loadResource();
	}

	public static void loadResource() {
		/* fill code */
		backgroundMusic = new AudioClip(ClassLoader.getSystemResource(backgroundMusicLocation).toString());
		JumpSFX = new AudioClip(ClassLoader.getSystemResource("resource/Audio/SFX/Jump.mp3").toString());
		ClickSFX = new AudioClip(ClassLoader.getSystemResource("resource/Audio/SFX/Click.mp3").toString());
		gameMusic = new AudioClip(ClassLoader.getSystemResource("resource/Audio/background/Tertis-loop.mp3").toString());
		GotHitSFX = new AudioClip(ClassLoader.getSystemResource("resource/Audio/SFX/Got_Hit.mp3").toString());
		GameOverSFX = new AudioClip(ClassLoader.getSystemResource("resource/Audio/SFX/Game_Over.mp3").toString());
		ActivatedSFX = new AudioClip(ClassLoader.getSystemResource("resource/Audio/SFX/Activated.mp3").toString());
		DeactivatedSFX = new AudioClip(ClassLoader.getSystemResource("resource/Audio/SFX/Deactivated.mp3").toString());
		Volume = 99;
	}
	
	public static boolean isAnyRuningn() {
		return backgroundMusic.isPlaying() ||  JumpSFX.isPlaying() || ClickSFX.isPlaying() || gameMusic.isPlaying() || GotHitSFX.isPlaying() || GameOverSFX.isPlaying() || ActivatedSFX.isPlaying() || DeactivatedSFX.isPlaying();
	}
	
	public static void stopAll() {
		if(backgroundMusic.isPlaying()) backgroundMusic.stop();
		if(JumpSFX.isPlaying()) JumpSFX.stop();
		if(ClickSFX.isPlaying()) ClickSFX.stop();
		if(gameMusic.isPlaying()) gameMusic.stop();
		if(GotHitSFX.isPlaying()) GotHitSFX.stop();
		if(GameOverSFX.isPlaying()) GameOverSFX.stop();
		if(ActivatedSFX.isPlaying()) ActivatedSFX.stop();
		if(DeactivatedSFX.isPlaying()) DeactivatedSFX.stop();
	}
	
	public static void setVolume(int Volume) {
		if(Volume > 99) Volume = 99;
		else if(Volume < 0) Volume = 0;
		AudioUtility.Volume = Volume;
	}
	
	public static int getVolume() {
		return Volume;
	}
	
	public static void playBackgroundMusic() {
		if(gameMusic.isPlaying()) gameMusic.stop();
		if(!backgroundMusic.isPlaying()) {
			backgroundMusic.play((double)Volume/100.0);
		}
	}
	
	public static void stopBackgroundMusic() {
		if(backgroundMusic.isPlaying()) {
			backgroundMusic.stop();
		}
	}
	
	public static void playGameMusic() {
		if(backgroundMusic.isPlaying()) backgroundMusic.stop();
		if(!gameMusic.isPlaying()) {
			gameMusic.play((double)Volume/100.0);
		}
	}
	
	public static void stopGameMusic() {
		if(gameMusic.isPlaying()) {
			gameMusic.stop();
		}
	}
	
	public static void playJump() {
		if(!JumpSFX.isPlaying()) JumpSFX.play((double)Volume/100.0);
	}
	
	public static void playClick() {
		if(!ClickSFX.isPlaying()) ClickSFX.play((double)Volume/100.0);
	}
	
	public static void playGotHit() {
		if(!GotHitSFX.isPlaying()) GotHitSFX.play((double)Volume/100.0);
	}
	
	public static void playGameOver() {
		if(!GameOverSFX.isPlaying()) GameOverSFX.play((double)Volume/100.0);
	}
	
	public static void playActivated() {
		if(!ActivatedSFX.isPlaying()) ActivatedSFX.play((double)Volume/100.0);
	}
	
	public static void playDeactivated() {
		if(!DeactivatedSFX.isPlaying()) DeactivatedSFX.play((double)Volume/100.0);
	}

//	public static void playSound(String identifier) {
//		/* fill code */
//		if(identifier.equalsIgnoreCase("shoot")) sound_shoot.play();
//		if(identifier.equalsIgnoreCase("collect")) sound_collect.play();
//	}
}
