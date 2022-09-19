package sumireko.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sumireko.abstracts.BaseRelic;
import sumireko.actions.cards.MakeCardInHandOccultAction;
import sumireko.patches.occult.OccultFields;

import static sumireko.SumirekoMod.makeID;

public class OccultBall extends BaseRelic {
    public static final String ID = makeID("OccultBall");
    private static final String IMG = "OccultBall";

    boolean active = false;

    public OccultBall()
    {
        super(ID, IMG, RelicTier.STARTER, LandingSound.MAGICAL);
        //this.counter = 3;
    }

    @Override
    public void atBattleStart() {
        active = false;
        stopPulse();
    }

    @Override
    public void onVictory() {
        active = false;
        stopPulse();
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (OccultFields.isOccult.get(targetCard)) {
            active = false;
            stopPulse();
        }
    }

    @Override
    public void atTurnStartPostDraw() {
        if (active) {
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new AbstractGameAction() {
                @Override
                public void update() { //:)
                    addToBot(new MakeCardInHandOccultAction(true));
                    this.isDone = true;
                }
            });
        }

        active = true;
        this.beginLongPulse();
    }
}