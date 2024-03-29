package sumireko.actions.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SelfRestraintAction extends AbstractGameAction {
    public SelfRestraintAction() {

    }

    public void update() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == AbstractCard.CardType.ATTACK)
                c.setCostForTurn(0);
        }

        this.isDone = true;
    }
}
