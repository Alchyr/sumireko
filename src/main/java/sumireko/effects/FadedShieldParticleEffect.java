package sumireko.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class FadedShieldParticleEffect extends AbstractGameEffect {
    private static final int RAW_W = 64;
    private float x;
    private float y;
    private float scale;

    public FadedShieldParticleEffect(float x, float y) {
        this.scale = Settings.scale / 2.0F;// 14
        this.duration = 2.0F;// 17
        this.x = x;// 19
        this.y = y;// 20
        this.renderBehind = true;// 21
        this.color = new Color(1.0F, 1.0F, 1.0F, 0.0F);// 22
    }// 23

    public void update() {
        this.scale += Gdx.graphics.getDeltaTime() * Settings.scale * 1.1F;// 27
        if (this.duration > 1.0F) {// 30
            this.color.a = Interpolation.fade.apply(0.0F, 0.18F, 1.0F - (this.duration - 1.0F));// 31
        } else {
            this.color.a = Interpolation.fade.apply(0.18F, 0.0F, 1.0F - this.duration);// 33
        }

        this.duration -= Gdx.graphics.getDeltaTime();// 36
        if (this.duration < 0.0F) {// 37
            this.isDone = true;// 38
        }

    }// 40

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);// 44
        sb.setColor(this.color);// 45
        sb.draw(ImageMaster.INTENT_DEFEND, this.x - 32.0F, this.y - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, this.scale, this.scale, 0.0F, 0, 0, 64, 64, false, false);// 46
        sb.setBlendFunction(770, 771);// 63
    }// 64

    public void dispose() {
    }// 68
}
