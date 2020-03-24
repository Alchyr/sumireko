package sumireko.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import sumireko.util.TextureLoader;

import static sumireko.SumirekoMod.makeImagePath;

public class SealLineEffect extends AbstractGameEffect {
    private static Texture line = TextureLoader.getTexture(makeImagePath("ui/line.png"));
    private static final int LINE_WIDTH = 100;
    private static final int LINE_HEIGHT = 20;
    private static final float LINE_OFFSET_X = 50;
    private static final float LINE_OFFSET_Y = 10;

    private float drawX, drawY, distance;

    public SealLineEffect(float x1, float y1, float x2, float y2, float distance, float angle) {
        drawX = (x2+x1) / 2f - LINE_OFFSET_X;
        drawY = (y2+y1) / 2f - LINE_OFFSET_Y;

        this.distance = distance;
        this.rotation = angle;

        this.color = new Color(1.0f, 1.0f, 1.0f, 0.0f);

        this.duration = this.startingDuration = 0.4f;
    }

    @Override
    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();

        if (this.duration < this.startingDuration / 2.0F) {
            this.color.a = this.duration / (this.startingDuration / 2.0F);
        }
        else //duration greater than half of starting duration
        {
            this.color.a = (this.startingDuration - this.duration) / (this.startingDuration / 2.0F);
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
            this.color.a = 0.0F;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(line, drawX, drawY, LINE_OFFSET_X, LINE_OFFSET_Y, LINE_WIDTH, LINE_HEIGHT,distance / LINE_WIDTH, 1.0f, rotation, 0, 0, LINE_WIDTH, LINE_HEIGHT, false, false);
    }

    @Override
    public void dispose() {
    }
}
