package sumireko.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class TormentEffect extends AbstractGameEffect {
    private final float x, y;
    private int smokeAmount;
    private boolean first = true;

    public TormentEffect(AbstractCreature target) {
        this.x = target.hb.cX;
        this.y = target.hb.cY;
        this.smokeAmount = MathUtils.random(14, 21);

        this.duration = 0.3f;
    }

    @Override
    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();

        if (first) {
            AbstractDungeon.effectsQueue.add(new PurpleSlashEffect(x, y, true));
            first = false;
        }

        if (duration <= 0) {
            int add = MathUtils.random(3, 7);
            while (add > 0 && smokeAmount > 0) {
                --add;
                --smokeAmount;
                AbstractDungeon.effectsQueue.add(new SpookySmokeEffect(x, y + 30.0f * Settings.scale));
            }
            if (smokeAmount <= 0) {
                this.isDone = true;
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
    }

    @Override
    public void dispose() {

    }
}
