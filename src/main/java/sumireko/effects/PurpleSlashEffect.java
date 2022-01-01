package sumireko.effects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.AnimatedSlashEffect;
import sumireko.SumirekoMod;

public class PurpleSlashEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private boolean isVertical;

    public PurpleSlashEffect(float x, float y, boolean isVertical) {
        this.x = x;
        this.y = y;
        this.startingDuration = 0.1F;
        this.duration = this.startingDuration;
        this.isVertical = isVertical;
    }

    public void update() {
        CardCrawlGame.sound.playA("ATTACK_IRON_2", 0.4F);
        CardCrawlGame.sound.playA("ATTACK_HEAVY", 0.1F);
        if (this.isVertical) {
            AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(this.x, this.y - 60.0F * Settings.scale, 0.0F, 400.0F, 0.0F, 5.0F, SumirekoMod.SUMIREKO_COLOR_BRIGHT, SumirekoMod.SUMIREKO_COLOR_DIM));
        } else {
            AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(this.x, this.y - 30.0F * Settings.scale, -500.0F, -500.0F, 135.0F, 4.0F, SumirekoMod.SUMIREKO_COLOR_BRIGHT, SumirekoMod.SUMIREKO_COLOR_DIM));
        }

        this.isDone = true;
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}