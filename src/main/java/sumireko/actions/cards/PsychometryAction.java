package sumireko.actions.cards;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sumireko.patches.PsychometryTrackerPatch;

import static sumireko.SumirekoMod.makeID;

public class PsychometryAction extends AbstractGameAction {
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString(makeID("Psychometry")).TEXT;

    boolean firstRun;

    public PsychometryAction(int amount)
    {
        this.amount = amount;
        this.actionType = ActionType.CARD_MANIPULATION;

        this.firstRun = true;
    }

    @Override
    public void update() {
        if (firstRun)
        {
            firstRun = false;

            if (!PsychometryTrackerPatch.cardsPlayedLastTurn.isEmpty() && amount > 0)
            {
                if (amount > BaseMod.MAX_HAND_SIZE)
                    amount = BaseMod.MAX_HAND_SIZE;

                CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

                temp.group.addAll(PsychometryTrackerPatch.cardsPlayedLastTurn);

                temp.sortAlphabetically(true);
                temp.sortByRarityPlusStatusCardType(false);

                if (this.amount == 1) {
                    AbstractDungeon.gridSelectScreen.open(temp, this.amount, true, TEXT[0]);
                } else {
                    AbstractDungeon.gridSelectScreen.open(temp, this.amount, true, TEXT[1] + this.amount + TEXT[2]);
                }
            }
            else
            {
                this.isDone = true;
            }
        }
        else
        {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {// 84
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    addToTop(new MakeTempCardInHandAction(c.makeSameInstanceOf(), true, true));
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.isDone = true;
        }
    }
}
