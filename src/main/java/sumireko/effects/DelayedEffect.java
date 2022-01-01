package sumireko.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class DelayedEffect extends AbstractGameEffect {
    private AbstractGameEffect effect;

    public DelayedEffect(AbstractGameEffect e, float duration) {
        this.startingDuration = this.duration = duration;

        this.effect = e;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();

        if (this.duration <= 0) {
            AbstractDungeon.effectsQueue.add(effect);
            this.isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void dispose() {

    }
}
