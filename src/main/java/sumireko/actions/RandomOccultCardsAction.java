package sumireko.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.HandCheckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sumireko.enums.CustomCardTags;
import sumireko.patches.occult.OccultFields;

import java.util.ArrayList;
import java.util.function.Predicate;

public class RandomOccultCardsAction extends AbstractGameAction {
    private Predicate<AbstractCard> condition;

    public RandomOccultCardsAction(int amount)
    {
        this(amount, (c)->true);
    }
    public RandomOccultCardsAction(int amount, Predicate<AbstractCard> condition)
    {
        this.condition = condition;
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

        ArrayList<AbstractCard> validCards = new ArrayList<>(AbstractDungeon.player.hand.group);

        validCards.removeIf((c)->!condition.test(c) || c.hasTag(CustomCardTags.FINAL) || OccultFields.isOccult.get(c));

        if (validCards.isEmpty())
        {
            this.isDone = true;
            return;
        }

        if (validCards.size() <= this.amount)
        {
            for (AbstractCard c : validCards)
            {
                OccultFields.isOccult.set(c, true);
                c.superFlash(Color.VIOLET);
                c.initializeDescription();
            }
        }
        else
        {
            ArrayList<AbstractCard> cards = new ArrayList<>(validCards);

            for (int i = 0; i < this.amount; ++i)
            {
                if (cards.isEmpty())
                    break;

                AbstractCard c = cards.remove(AbstractDungeon.cardRandomRng.random(cards.size() - 1));

                OccultFields.isOccult.set(c, true);
                c.superFlash(Color.VIOLET);
                c.initializeDescription();
            }
        }

        addToTop(new HandCheckAction());

        this.isDone = true;
    }
}
