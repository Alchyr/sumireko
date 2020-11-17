package sumireko.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class SealPreviewGlow extends AbstractGameEffect {
    private final float x, y;
    private final TextureAtlas.AtlasRegion img;
    private float scale, size;

    public SealPreviewGlow(float x, float y, float size, Color gColor) {
        this.x = x;
        this.y = y;
        this.img = ImageMaster.CARD_SKILL_BG_SILHOUETTE;

        this.size = size * Settings.scale;
        this.duration = 1.5F;
        this.color = gColor.cpy();
    }

    public void update() {
        this.scale = (1.0F + Interpolation.pow2Out.apply(0.03F, 0.11F, 1.3F - this.duration)) * size;
        this.color.a = this.duration / 2.0F;
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
            this.duration = 0.0F;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(this.img, x + this.img.offsetX - (float)this.img.originalWidth / 2.0F, y + this.img.offsetY - (float)this.img.originalHeight / 2.0F, (float)this.img.originalWidth / 2.0F - this.img.offsetX, (float)this.img.originalHeight / 2.0F - this.img.offsetY, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale, 0);
    }

    public void dispose() {
    }
}