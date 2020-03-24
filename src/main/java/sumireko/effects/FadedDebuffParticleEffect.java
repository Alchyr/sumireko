package sumireko.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class FadedDebuffParticleEffect extends AbstractGameEffect {
    private Texture img;
    private static int IMG_NUM = 0;
    private boolean spinClockwise;
    private float x;
    private float y;
    private float scale = 0.0F;

    public FadedDebuffParticleEffect(float x, float y) {
        this.x = x + MathUtils.random(-36.0F, 36.0F) * Settings.scale;// 20
        this.y = y + MathUtils.random(-36.0F, 36.0F) * Settings.scale;// 21
        this.duration = 4.0F;// 22
        this.rotation = MathUtils.random(360.0F);// 23
        this.spinClockwise = MathUtils.randomBoolean();// 24
        if (IMG_NUM == 0) {// 26
            this.renderBehind = true;// 27
            this.img = ImageMaster.DEBUFF_VFX_1;// 28
            ++IMG_NUM;// 29
        } else if (IMG_NUM == 1) {// 30
            this.img = ImageMaster.DEBUFF_VFX_3;// 31
            ++IMG_NUM;// 32
        } else {
            this.img = ImageMaster.DEBUFF_VFX_2;// 34
            IMG_NUM = 0;// 35
        }

        this.color = new Color(1.0F, 1.0F, 1.0F, 0.0F);// 38
    }// 39

    public void update() {
        if (this.spinClockwise) {// 45
            this.rotation += Gdx.graphics.getDeltaTime() * 120.0F;// 46
        } else {
            this.rotation -= Gdx.graphics.getDeltaTime() * 120.0F;// 48
        }

        if (this.duration > 3.0F) {// 52
            this.color.a = Interpolation.fade.apply(0.0F, 0.6F, 1.0F - (this.duration - 3.0F));// 53
        } else if (this.duration <= 1.0F) {// 54
            this.color.a = Interpolation.fade.apply(0.6F, 0.0F, 1.0F - this.duration);// 57
            this.scale = Interpolation.fade.apply(Settings.scale, 0.0F, 1.0F - this.duration);// 58
        }

        if (this.duration > 2.0F) {// 61
            this.scale = Interpolation.bounceOut.apply(0.0F, Settings.scale, 2.0F - (this.duration - 2.0F));// 62
        }

        this.duration -= Gdx.graphics.getDeltaTime();// 65
        if (this.duration < 0.0F) {// 66
            this.isDone = true;// 67
        }

    }// 69

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);// 73
        sb.draw(this.img, this.x - 16.0F, this.y - 16.0F, 16.0F, 16.0F, 32.0F, 32.0F, this.scale, this.scale, this.rotation, 0, 0, 32, 32, false, false);// 74
    }// 75

    public void dispose() {
    }// 79
}