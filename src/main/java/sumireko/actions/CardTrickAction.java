package sumireko.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CardTrickAction extends AbstractGameAction {
    public CardTrickAction(int amount)
    {
        this.actionType = ActionType.WAIT;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.hand.isEmpty())
        {
            addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.amount));
        }
        this.isDone = true;
    }
}
