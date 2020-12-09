package sumireko.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.EquilibriumPower;
import com.megacrit.cardcrawl.relics.RunicPyramid;
import sumireko.abstracts.BasePower;
import sumireko.actions.HandSelectAction;

import static sumireko.SumirekoMod.makeID;

public class SixthSensePower extends BasePower {
    public static final String NAME = "SixthSense";
    public static final String POWER_ID = makeID(NAME);
    public static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public SixthSensePower(final AbstractCreature owner, int amount)
    {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer && !AbstractDungeon.player.hand.isEmpty() && !AbstractDungeon.player.hasRelic(RunicPyramid.ID) && !AbstractDungeon.player.hasPower(EquilibriumPower.POWER_ID)) {// 36 37
            this.addToBot(new HandSelectAction(amount, (c) -> (!c.selfRetain && !c.retain && !c.isEthereal),
                    (cards) -> {
                        for (AbstractCard c : cards)
                            c.retain = true;
                    }, null, descriptions()[1],
                    true, true, true
            ));
        }
    }

    public void updateDescription() {
        this.description = descriptions()[0];
    }
}