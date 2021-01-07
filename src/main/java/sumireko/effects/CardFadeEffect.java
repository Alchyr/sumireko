package sumireko.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class CardFadeEffect extends AbstractGameEffect {
    private static final float RATE_START = 200 * Settings.scale;
    private static final float RATE_END = 50 * Settings.scale;

    private final AbstractCard card;
    private float rate;

    public CardFadeEffect(AbstractCard card) {
        this.card = card;
        this.startingDuration = 1.5F;
        this.duration = this.startingDuration;
        CardCrawlGame.sound.play("CARD_BURN");

        this.rate = RATE_START;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();// 71
        if (this.duration < 0.5F && !this.card.fadingOut) {
            this.card.fadingOut = true;
        }

        this.card.current_y = this.card.target_y = this.card.current_y + rate * Gdx.graphics.getDeltaTime();

        this.rate = Interpolation.linear.apply(RATE_END, RATE_START, duration / startingDuration);

        this.card.update();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        if (!this.isDone) {
            this.card.render(sb);
        }
    }

    public void dispose() {
    }
}
