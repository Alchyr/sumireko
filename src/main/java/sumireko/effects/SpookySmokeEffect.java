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

public class SpookySmokeEffect extends AbstractGameEffect {
    private float x, y, sx, sy, tx, ty, scaleMult;
    private TextureAtlas.AtlasRegion img;

    public SpookySmokeEffect(float x, float y) {
        this.color = new Color(MathUtils.random(0.4f, 0.8f), MathUtils.random(0.0f, 0.5f), MathUtils.random(0.5f, 0.9f), 0.5f);
        this.duration = this.startingDuration = MathUtils.random(1.0f, 3.0f);

        this.x = this.sx = x + MathUtils.random(-10.0f, 10.0f);
        this.y = this.sy = y + MathUtils.random(-10.0f, 10.0f);

        this.tx = this.x + MathUtils.random(40.0f, 150.0f) * Settings.scale * (MathUtils.randomBoolean() ? 1 : -1);
        this.ty = this.y + MathUtils.random(8.0f, 50.0f) * Settings.scale * (MathUtils.randomBoolean() ? 1 : -1);

        this.scale = 0;
        this.scaleMult = MathUtils.random(0.5f, 1.3f);
        this.rotation = MathUtils.random(360.0F);

        if (MathUtils.randomBoolean(0.35f)) {
            this.img = ImageMaster.EXHAUST_S;
        }
        else {
            this.img = ImageMaster.EXHAUST_L;
        }
    }

    @Override
    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        float progress = 1 - (duration / startingDuration);
        this.scale = scaleMult;
        if (progress < 0.1f) {
            this.color.a = progress / 0.2f;
            this.scale = this.color.a * scaleMult;
        }
        else if (progress < 0.6f) {
            this.color.a = 0.5f;
        }
        else {
            this.color.a = (1 - progress) / 0.8f;
        }

        this.x = Interpolation.circleOut.apply(sx, tx, progress);
        this.y = Interpolation.circleOut.apply(sy, ty, progress);

        if (this.duration <= 0)
            this.isDone = true;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale, this.rotation);
    }

    @Override
    public void dispose() {

    }
}
