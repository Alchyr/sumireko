package sumireko.actions.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Iterator;

public class EsotericSecretsAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private boolean first = true;

    public EsotericSecretsAction(AbstractCreature source, int amount) {
        this.setValues(source, source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
    }// 19

    public void update() {
        if (first) {
            first = false;
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], Integer.MAX_VALUE - 69, true, true);
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                AbstractCard c;
                for(Iterator<AbstractCard> itr = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator(); itr.hasNext(); AbstractDungeon.player.hand.addToTop(c)) {
                    c = itr.next();

                    AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand, true));
                    AbstractDungeon.actionManager.addToTop(new GainBlockAction(this.source, this.source, this.amount, true));
                }

                AbstractDungeon.handCardSelectScreen.selectedCards.clear();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }

            this.tickDuration();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
        TEXT = uiStrings.TEXT;
    }
}