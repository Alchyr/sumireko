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

public class FadedBuffParticleEffect extends AbstractGameEffect {
    private TextureAtlas.AtlasRegion img;
    private float x;
    private float y;
    private float vY;
    private float scale = 0.0F;

    public FadedBuffParticleEffect(float x, float y) {
        this.x = x + MathUtils.random(-25.0F, 25.0F) * Settings.scale;// 21
        this.y = y + MathUtils.random(-20.0F, 10.0F) * Settings.scale;// 22
        this.duration = 0.5F;// 23
        this.rotation = MathUtils.random(-5.0F, 5.0F);// 24
        switch(MathUtils.random(2)) {// 26
            case 0:
                this.img = ImageMaster.vfxAtlas.findRegion("buffVFX1");// 28
                break;// 29
            case 1:
                this.img = ImageMaster.vfxAtlas.findRegion("buffVFX2");// 31
                break;// 32
            default:
                this.img = ImageMaster.vfxAtlas.findRegion("buffVFX3");// 34
        }

        this.renderBehind = MathUtils.randomBoolean();// 38
        this.vY = MathUtils.random(30.0F, 50.0F) * Settings.scale;// 39
        this.color = new Color(1.0F, 1.0F, 1.0F, 0.0F);// 40
        this.scale = MathUtils.random(1.0F, 1.5F) * Settings.scale;// 41
    }// 42

    public void update() {
        this.scale += Gdx.graphics.getDeltaTime() / 2.0F;// 47
        if (this.duration > 0.5F) {// 50
            this.color.a = Interpolation.fade.apply(0.0F, 0.6F, 1.0F - (this.duration - 3.0F));// 52
        } else if (this.duration < 0.5F) {// 53
            this.color.a = Interpolation.fade.apply(0.6F, 0.0F, 1.0F - this.duration * 2.0F);// 55
        }

        this.y += Gdx.graphics.getDeltaTime() * this.vY;// 59
        this.duration -= Gdx.graphics.getDeltaTime();// 61
        if (this.duration < 0.0F) {// 62
            this.isDone = true;// 63
        }

    }// 65

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);// 69
        sb.setColor(this.color);// 70
        sb.draw(this.img, this.x - (float)this.img.packedWidth / 2.0F, this.y - (float)this.img.packedHeight / 2.0F, this.img.offsetX, this.img.offsetY, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale, this.rotation);// 71
        sb.setBlendFunction(770, 771);// 82
    }// 83

    public void dispose() {
    }// 88
}