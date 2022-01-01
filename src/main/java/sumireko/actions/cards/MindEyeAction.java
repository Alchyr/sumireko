package sumireko.actions.cards;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;

public class MindEyeAction extends AbstractGameAction {
    private boolean first = true;
    private boolean retrieveCard = false;

    public MindEyeAction(int amount) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
    }

    public void update() {
        if (first) {
            if (AbstractDungeon.player.drawPile.isEmpty())
            {
                this.isDone = true;
                return;
            }
            else if (AbstractDungeon.player.drawPile.size() == 1)
            {
                addToTop(new MakeTempCardInHandAction(AbstractDungeon.player.drawPile.getBottomCard()));
                this.isDone = true;
                return;
            }

            CardGroup drawCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            drawCards.group.addAll(AbstractDungeon.player.drawPile.group);

            ArrayList<AbstractCard> choices = new ArrayList<>();

            int i = 0;
            while (i < this.amount && !drawCards.isEmpty())
            {
                choices.add(drawCards.getRandomCard(AbstractDungeon.cardRandomRng));
                ++i;
            }

            AbstractDungeon.cardRewardScreen.customCombatOpen(choices, CardRewardScreen.TEXT[1], false);
            first = false;
        } else {
            if (!this.retrieveCard) {
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                    AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                    if (AbstractDungeon.player.hasPower("MasterRealityPower")) {
                        disCard.upgrade();
                    }

                    disCard.current_x = -1000.0F * Settings.scale;

                    if (this.amount == 1) {
                        if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
                            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                        } else {
                            AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                        }
                    }

                    AbstractDungeon.cardRewardScreen.discoveryCard = null;
                }

                this.retrieveCard = true;
            }

            this.tickDuration();
        }
    }
}