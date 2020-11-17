package sumireko.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.BurningBlood;
import sumireko.abstracts.BasePower;
import sumireko.interfaces.LockingCardInterface;

import java.util.ArrayList;

import static sumireko.SumirekoMod.makeID;

public class BaggagePower extends BasePower {
    public static final String NAME = "Baggage";
    public static final String POWER_ID = makeID(NAME);
    public static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public BaggagePower(final AbstractCreature owner, int amount)
    {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            for (AbstractCard c : AbstractDungeon.player.hand.group)
            {
                if (!c.isEthereal && c instanceof LockingCardInterface && ((LockingCardInterface) c).isLocked())
                {
                    c.retain = true;
                    c.modifyCostForCombat(this.amount);
                }
            }
        }
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        return super.atDamageGive(damage, type, card);
    }

    public void updateDescription() {
        this.description = descriptions()[0] + amount + descriptions()[1];
    }
}