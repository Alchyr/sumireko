package sumireko.actions.cards;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import sumireko.enums.CustomCardTags;
import sumireko.patches.occult.OccultFields;

import java.util.ArrayList;
import java.util.Iterator;

public class TelephotographyAction extends AbstractGameAction {
    private boolean first = true;
    private boolean retrieveCard = false;
    private ArrayList<AbstractCard> cardChoices;

    public TelephotographyAction(ArrayList<AbstractCard> cardChoices) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.cardChoices = cardChoices;
        this.amount = 1;
    }

    public void update() {
        if (first) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(cardChoices, CardRewardScreen.TEXT[1], false);
            first = false;
        } else {
            if (!this.retrieveCard) {
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                    AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                    if (AbstractDungeon.player.hasPower("MasterRealityPower")) {
                        disCard.upgrade();
                    }

                    if (!disCard.hasTag(CustomCardTags.FINAL))
                    {
                        OccultFields.isOccult.set(disCard, true);
                        disCard.initializeDescription();
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