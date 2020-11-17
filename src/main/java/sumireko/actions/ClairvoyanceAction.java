package sumireko.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ClairvoyanceAction extends AbstractGameAction {
    private AbstractCard.CardType type;
    private boolean other = false;

    public ClairvoyanceAction(AbstractCard.CardType type, int block) {
        this.actionType = ActionType.WAIT;
        this.type = type;
        this.amount = block;
    }

    public ClairvoyanceAction(int block) {
        this.actionType = ActionType.WAIT;
        this.amount = block;
        other = true;
    }

    public void update() {
        for (AbstractCard c : DrawCardAction.drawnCards) {
            if (other)
            {
                if (c.type != AbstractCard.CardType.ATTACK && c.type != AbstractCard.CardType.SKILL && c.type != AbstractCard.CardType.POWER)
                {
                    c.superFlash(Color.GOLD);
                    CardCrawlGame.sound.playA("BELL", -0.2f);
                    addToTop(new MakeTempCardInHandAction(c.makeStatEquivalentCopy()));
                    addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.amount));
                    break;
                }
            }
            else if (c.type == type)
            {
                c.superFlash(Color.GOLD);
                CardCrawlGame.sound.playA("BELL", -0.2f);
                addToTop(new MakeTempCardInHandAction(c.makeStatEquivalentCopy()));
                addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.amount));
                break;
            }

            //Failure.
            c.superFlash(Color.RED);
            CardCrawlGame.sound.playA("DEBUFF_1", -0.3f);
            break;
        }

        this.isDone = true;
    }
}
