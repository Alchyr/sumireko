package sumireko.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sumireko.abstracts.SealCard;

public class MaterialismAction extends AbstractGameAction {
    public MaterialismAction()
    {
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        int count = 0;

        for (AbstractCard c : AbstractDungeon.player.hand.group)
        {
            if (c instanceof SealCard)
                ++count;
        }
        this.addToTop(new DrawCardAction(this.target, count * 2));

        for (AbstractCard c : AbstractDungeon.player.hand.group)
        {
            if (c instanceof SealCard)
                addToTop(new FasterDiscardSpecificCardAction(c));
        }

        this.isDone = true;
    }
}
