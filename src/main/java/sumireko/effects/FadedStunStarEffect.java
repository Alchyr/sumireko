package sumireko.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class FadedStunStarEffect extends AbstractGameEffect {
    private TextureAtlas.AtlasRegion img;
    private float x;
    private float y;
    private float vX;
    private float vY;
    private float scale;

    public FadedStunStarEffect(float x, float y) {
        this.duration = 2.0F;
        this.img = ImageMaster.TINY_STAR;
        this.x = x - (float)this.img.packedWidth / 2.0F;
        this.y = y - (float)this.img.packedWidth / 2.0F;
        this.vX = 128.0F * Settings.scale;
        this.color = new Color(1.0F, 0.9F, 0.3F, 0.0F);
        this.renderBehind = false;
        this.scale = Settings.scale;
        this.rotation = MathUtils.random(360.0F);
    }

    public void update() {
        this.vX = MathUtils.cos(3.1415927F * this.duration);
        this.vY = MathUtils.cos(3.1415927F * this.duration * 2.0F);
        this.rotation -= Gdx.graphics.getDeltaTime() * 60.0F;
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        } else if (this.duration < 1.0F) {
            this.color.a = Interpolation.pow5Out.apply(this.duration) * 0.6f;
            this.color.r = Interpolation.pow2Out.apply(this.duration);
            this.color.g = Interpolation.pow2Out.apply(this.duration) * 0.9F;
            this.color.b = Interpolation.pow2Out.apply(this.duration) * 0.3F;
        } else if (this.duration > 1.0F) {
            this.color.a = Interpolation.pow5Out.apply(1.0F - (this.duration - 1.0F)) * 0.6f;
            this.color.r = Interpolation.pow4Out.apply(1.0F - (this.duration - 1.0F));
            this.color.g = Interpolation.pow4Out.apply(1.0F - (this.duration - 1.0F)) * 0.9F;
            this.color.b = Interpolation.pow4Out.apply(1.0F - (this.duration - 1.0F)) * 0.3F;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(this.img, this.x - this.vX * 30.0F * Settings.scale, this.y - this.vY * 5.0F * Settings.scale, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale, this.rotation);
    }

    public void dispose() {
    }
}