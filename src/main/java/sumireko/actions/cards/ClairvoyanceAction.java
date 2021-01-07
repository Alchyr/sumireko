package sumireko.actions.cards;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sumireko.patches.occult.OccultFields;
import sumireko.powers.ProwessPower;

public class ClairvoyanceAction extends AbstractGameAction {
    private AbstractCard.CardType type;
    private boolean other = false;

    public ClairvoyanceAction(AbstractCard.CardType type, int buff) {
        this.actionType = ActionType.WAIT;
        this.type = type;
        this.amount = buff;
    }

    public ClairvoyanceAction(int buff) {
        this.actionType = ActionType.WAIT;
        this.amount = buff;
        other = true;
    }

    public void update() {
        for (AbstractCard c : DrawCardAction.drawnCards) {
            if (other)
            {
                if (c.type != AbstractCard.CardType.ATTACK && c.type != AbstractCard.CardType.SKILL && c.type != AbstractCard.CardType.POWER)
                {
                    c.superFlash(Color.GOLD.cpy());
                    CardCrawlGame.sound.playA("BELL", -0.2f);
                    //AbstractCard occultCopy = c.makeStatEquivalentCopy();
                    //OccultFields.isOccult.set(occultCopy, true);

                    //addToTop(new MakeTempCardInHandAction(occultCopy, BaseMod.MAX_HAND_SIZE - AbstractDungeon.player.hand.size()));

                    addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, this.amount), this.amount));
                    addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ProwessPower(AbstractDungeon.player, this.amount), this.amount));

                    //addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.amount));
                    break;
                }
            }
            else if (c.type == type)
            {
                c.superFlash(Color.GOLD.cpy());
                CardCrawlGame.sound.playA("BELL", -0.2f);
                //AbstractCard occultCopy = c.makeStatEquivalentCopy();
                //OccultFields.isOccult.set(occultCopy, true);

                //addToTop(new MakeTempCardInHandAction(occultCopy, BaseMod.MAX_HAND_SIZE - AbstractDungeon.player.hand.size()));

                addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, this.amount), this.amount));
                addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ProwessPower(AbstractDungeon.player, this.amount), this.amount));

                //addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.amount));
                break;
            }

            //Failure.
            c.superFlash(Color.RED.cpy());
            CardCrawlGame.sound.playA("DEBUFF_1", -0.3f);
            break;
        }

        this.isDone = true;
    }
}
