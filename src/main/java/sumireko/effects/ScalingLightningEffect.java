package sumireko.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.ImpactSparkEffect;

public class ScalingLightningEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private TextureAtlas.AtlasRegion img;

    private float xScale;
    private float yScale;
    private int sparkCount;
    private ScreenShake.ShakeIntensity intensity;
    private ScreenShake.ShakeDur shakeDur;

    public ScalingLightningEffect(float x, float y, float scale) {
        this.img = ImageMaster.vfxAtlas.findRegion("combat/lightning");

        this.x = x - (float)this.img.packedWidth / 2.0F;
        this.y = y;
        this.color = Color.WHITE.cpy();
        this.duration = 0.5F;
        this.startingDuration = 0.5F;

        this.scale = scale;
        xScale = scale;
        yScale = Math.max(1, scale / 1.5f);

        if (scale < 1.5) {
            intensity = ScreenShake.ShakeIntensity.LOW;
        }
        else if (scale < 3) {
            intensity = ScreenShake.ShakeIntensity.MED;
        }
        else {
            intensity = ScreenShake.ShakeIntensity.MED;
        }

        if (scale < 1) {
            shakeDur = ScreenShake.ShakeDur.SHORT;
        }
        else {
            shakeDur = ScreenShake.ShakeDur.MED;
        }

        sparkCount = Math.min(30, (int) (Math.log(scale + 1) * 20));
    }

    public void update() {
        if (this.duration == this.startingDuration) {
            CardCrawlGame.screenShake.shake(intensity, shakeDur, false);

            for(int i = 0; i < sparkCount; ++i) {
                AbstractDungeon.topLevelEffectsQueue.add(new ImpactSparkEffect(this.x + MathUtils.random(-20.0F, 20.0F) * Settings.scale + 150.0F * Settings.scale, this.y + MathUtils.random(-20.0F, 20.0F) * Settings.scale));
            }
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }

        this.color.a = Interpolation.bounceIn.apply(this.duration * 2.0F);
    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, 0.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, xScale, yScale, this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}