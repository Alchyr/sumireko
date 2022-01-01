package sumireko.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.StarBounceEffect;

public class RepelEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private int amt;

    public RepelEffect(int damage, float x, float y) {
        this.amt = damage / 2;
        this.x = x;
        this.y = y;
        if (this.amt > 25) {
            this.amt = 25;
        }
    }

    public void update() {
        for(int i = 0; i < this.amt; ++i) {
            AbstractDungeon.effectsQueue.add(new DarkBounceEffect(this.x, this.y));
        }

        this.isDone = true;
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
