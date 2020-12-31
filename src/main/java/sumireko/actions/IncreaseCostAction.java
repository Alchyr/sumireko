package sumireko.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;


public class IncreaseCostAction extends AbstractGameAction {
    private AbstractCard c;

    public IncreaseCostAction(AbstractCard c, int amount) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.FAST_MODE ? Settings.ACTION_DUR_XFAST : Settings.ACTION_DUR_FAST;
        this.c = c;

        this.amount = amount;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (canIncrease(c)) {
                c.cost += this.amount;
                c.costForTurn += this.amount;
                c.isCostModified = true;
                c.superFlash(Color.SLATE.cpy());
            }
        }

        this.tickDuration();
    }

    private boolean canIncrease(AbstractCard c) {
        return c.costForTurn >= 0 || c.cost >= 0;
    }
}