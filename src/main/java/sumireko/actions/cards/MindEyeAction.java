package sumireko.actions.cards;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class MindEyeAction extends AbstractGameAction {
    //uw u copying BetterDrawToHandAction
    private static final String[] TEXT;
    private AbstractPlayer player;
    private int numberOfCards;

    public MindEyeAction(int numberOfCards) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (!this.player.drawPile.isEmpty() && this.numberOfCards > 0) {
                if (this.player.drawPile.size() <= this.numberOfCards) {
                    ArrayList<AbstractCard> cardsToMove = new ArrayList<>(this.player.drawPile.group);

                    for (AbstractCard c : cardsToMove) {
                        if (this.player.hand.size() >= BaseMod.MAX_HAND_SIZE) {
                            this.player.drawPile.moveToDiscardPile(c);
                            c.setCostForTurn(c.costForTurn + 1);
                            this.player.createHandIsFullDialog();
                        } else {
                            this.player.drawPile.moveToHand(c, this.player.drawPile);
                            c.setCostForTurn(c.costForTurn + 1);
                        }
                    }

                    this.isDone = true;
                } else {
                    CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

                    for (AbstractCard c : this.player.drawPile.group) {
                        temp.addToTop(c);
                    }

                    temp.sortAlphabetically(true);
                    temp.sortByRarityPlusStatusCardType(false);
                    if (this.numberOfCards == 1) {
                        AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, TEXT[0], false);
                    } else {
                        AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, TEXT[1] + this.numberOfCards + TEXT[2], false);
                    }

                    this.tickDuration();
                }
            } else {
                this.isDone = true;
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    if (this.player.hand.size() >= BaseMod.MAX_HAND_SIZE) {
                        this.player.drawPile.moveToDiscardPile(c);
                        c.setCostForTurn(c.costForTurn + 1);
                        this.player.createHandIsFullDialog();
                    } else {
                        this.player.drawPile.moveToHand(c, this.player.drawPile);
                        c.setCostForTurn(c.costForTurn + 1);
                    }
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;
    }
}