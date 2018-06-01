package com.mylocalweather;

public final class Application extends android.app.Application{
	@Override
	public void onCreate() {
		super.onCreate();
		FontsOverride.setDefaultFont(this, "SANS_SERIF", "Compass.ttf");
	}
}