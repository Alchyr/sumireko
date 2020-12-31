package sumireko.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;


public class MentalImagingAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static final float DURATION_PER_CARD = 0.25F;
    private AbstractPlayer p;

    private boolean upgraded;

    public MentalImagingAction(boolean upgraded) {
        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.DRAW;
        this.duration = DURATION_PER_CARD;
        this.p = AbstractDungeon.player;

        this.upgraded = upgraded;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.group.size() == 1) {
                AbstractCard c = p.hand.getBottomCard().makeStatEquivalentCopy();
                if (upgraded && c.canUpgrade())
                    c.upgrade();

                this.addToTop(new MakeTempCardInHandAction(c));
                this.isDone = true;
                return;
            }

            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, upgraded);
                this.tickDuration();
                return;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                p.hand.addToTop(c);

                c = c.makeStatEquivalentCopy();
                if (upgraded && c.canUpgrade())
                    c.upgrade();

                this.addToTop(new MakeTempCardInHandAction(c));
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }

        this.tickDuration();
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("DualWieldAction");
        TEXT = uiStrings.TEXT;
    }
}