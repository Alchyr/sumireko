package sumireko.actions.general;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FasterDiscardSpecificCardAction extends AbstractGameAction {
    private AbstractCard targetCard;
    private CardGroup group;

    public FasterDiscardSpecificCardAction(AbstractCard targetCard) {
        this.targetCard = targetCard;
        this.actionType = ActionType.DISCARD;
        this.duration = this.startDuration = Settings.FAST_MODE ? 0.05f : Settings.ACTION_DUR_XFAST;
    }

    public FasterDiscardSpecificCardAction(AbstractCard targetCard, CardGroup group) {
        this.targetCard = targetCard;
        this.group = group;
        this.actionType = ActionType.DISCARD;
        this.duration = this.startDuration = Settings.FAST_MODE ? 0.05f : Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (this.group == null) {
                this.group = AbstractDungeon.player.hand;
            }

            if (this.group.contains(this.targetCard)) {
                this.group.moveToDiscardPile(this.targetCard);
                GameActionManager.incrementDiscard(false);
                this.targetCard.triggerOnManualDiscard();
            }
        }

        this.tickDuration();
    }
}