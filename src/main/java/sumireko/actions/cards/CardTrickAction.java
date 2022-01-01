package sumireko.actions.cards;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class CardTrickAction extends AbstractGameAction {
    public static final String[] TEXT;
    private final AbstractPlayer player;
    private boolean firstRun;

    public CardTrickAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        firstRun = true;
        this.player = AbstractDungeon.player;
    }

    public void update() {
        if (firstRun) {
            firstRun = false;
            if (!this.player.discardPile.isEmpty()) {
                if (this.player.discardPile.size() <= 1) {
                    ArrayList<AbstractCard> cardsToMove = new ArrayList<>(this.player.discardPile.group);

                    for (AbstractCard c : cardsToMove) {
                        if (this.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
                            this.player.hand.addToHand(c);

                            this.player.discardPile.removeCard(c);

                            c.lighten(false);
                            c.applyPowers();
                        }

                        addToTop(new MakeTempCardInDrawPileAction(c, 1, false, false, false));
                    }

                    this.isDone = true;
                } else {
                    AbstractDungeon.gridSelectScreen.open(this.player.discardPile, 1, TEXT[0], false);
                }
            } else {
                this.isDone = true;
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    if (this.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
                        this.player.hand.addToHand(c);

                        this.player.discardPile.removeCard(c);

                        c.lighten(false);
                        c.applyPowers();
                    }

                    c.unhover();

                    addToTop(new MakeTempCardInDrawPileAction(c, 1, false, false, false));
                }

                for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                    c.unhover();
                    c.target_x = CardGroup.DISCARD_PILE_X;
                    c.target_y = 0.0F;
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
            if (this.isDone) {
                for (AbstractCard c : this.player.hand.group) {
                    c.applyPowers();
                }
            }
        }
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;
    }
}