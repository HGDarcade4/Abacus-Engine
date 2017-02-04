package qfta;

import abacus.graphics.GameFont;
import abacus.graphics.Renderer;

public class FadePressKey {

	private String message;
	private FadeTimer fade;
	
	public FadePressKey(String message) {
		this.message = message;
		this.fade = new FadeTimer(10, 30, 10, 30, 0);
	}
	
	public void render(Renderer renderer, GameFont font) {
        int x = (int) (renderer.getWidth()/2 - font.getWidth(this.message)/2);
        font.setAlpha(this.fade.getAlpha());
        font.draw(this.message, x, 24);
	}
	
	public void updateFadeTimer() {
		this.fade.update();
	}
	
	public void resetFadeTimer() {
		this.fade.reset();
	}
	
	public boolean isDoneFadeTimer() {
		return this.fade.isDone();
	}
}
