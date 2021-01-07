package sumireko.actions.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

public class TeleportationAction extends AbstractGameAction {
    private final int threshold;

    public TeleportationAction(int threshold, int block)
    {
        this.actionType = ActionType.EXHAUST;
        this.threshold = threshold;
        this.amount = block;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.discardPile.size() >= threshold)
        {
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new IntangiblePlayerPower(AbstractDungeon.player, 1), 1));
        }
        else
        {
            addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.amount));
        }

        AbstractCard c;
        while (!AbstractDungeon.player.discardPile.isEmpty())
        {
            c = AbstractDungeon.player.discardPile.getBottomCard();
            AbstractDungeon.player.discardPile.moveToExhaustPile(c);
            c.exhaustOnUseOnce = false;
            c.freeToPlayOnce = false;
        }
        CardCrawlGame.dungeon.checkForPactAchievement();

        this.isDone = true;
    }
}
