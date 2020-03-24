package sumireko.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sumireko.enums.CustomCardTags;
import sumireko.patches.occult.OccultFields;

public class MakeTopCardOccultAction extends AbstractGameAction {
    public MakeTopCardOccultAction()
    {
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.drawPile.isEmpty())
        {
            this.isDone = true;
            return;
        }

        AbstractCard c = AbstractDungeon.player.drawPile.getTopCard();

        if (!c.hasTag(CustomCardTags.FINAL))
        {
            OccultFields.isOccult.set(c, true);
            c.initializeDescription();
        }

        this.isDone = true;
        return;
    }
}
