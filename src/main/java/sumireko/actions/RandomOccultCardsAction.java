package sumireko.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.HandCheckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sumireko.enums.CustomCardTags;
import sumireko.patches.occult.OccultFields;

import java.util.ArrayList;

public class RandomOccultCardsAction extends AbstractGameAction {
    public RandomOccultCardsAction(int amount)
    {
        this.amount = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.hand.isEmpty())
        {
            this.isDone = true;
            return;
        }

        if (AbstractDungeon.player.hand.size() <= this.amount)
        {
            for (AbstractCard c : AbstractDungeon.player.hand.group)
            {
                if (!c.hasTag(CustomCardTags.FINAL) && !OccultFields.isOccult.get(c))
                {
                    OccultFields.isOccult.set(c, true);
                    c.superFlash(Color.VIOLET);
                    c.initializeDescription();
                }
            }
            addToTop(new HandCheckAction());
        }
        else if (AbstractDungeon.player.hand.size() > this.amount)
        {
            ArrayList<AbstractCard> cards = new ArrayList<>(AbstractDungeon.player.hand.group);

            for (int i = 0; i < this.amount; ++i)
            {
                if (cards.isEmpty())
                    break;

                AbstractCard c = cards.remove(AbstractDungeon.cardRandomRng.random(cards.size() - 1));

                if (c.hasTag(CustomCardTags.FINAL) || OccultFields.isOccult.get(c))
                {
                    --i;
                    //this one doesn't count
                }
                else
                {
                    OccultFields.isOccult.set(c, true);
                    c.superFlash(Color.VIOLET);
                    c.initializeDescription();
                }
            }

            addToTop(new HandCheckAction());
        }


        this.isDone = true;
    }
}
