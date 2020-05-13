package sumireko.util.mysteryupgrades;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sumireko.actions.LoseStrengthThisTurnAction;
import sumireko.cards.rare.MysterySeal;
import sumireko.util.MysteryUpgrade;
import sumireko.util.PretendMonster;

import java.util.Map;

import static sumireko.abstracts.SealCard.pretendApplyPower;

public class StrengthDownSealUpgrade extends MysteryUpgrade {
    public StrengthDownSealUpgrade()
    {
        super(-100, true, true);
    }

    @Override
    public void apply(MysterySeal c) {
        if (c.target == AbstractCard.CardTarget.SELF)
        {
            c.target = AbstractCard.CardTarget.ALL;
        }
        else if (c.target != AbstractCard.CardTarget.SELF_AND_ENEMY && c.target != AbstractCard.CardTarget.ENEMY)
        {
            c.target = AbstractCard.CardTarget.ALL_ENEMY;
        }

        c.baseSealValue += SEAL_UPGRADE;
        c.sealValue += SEAL_UPGRADE;
        c.upgradedSeal = true;
    }

    @Override
    public void triggerSealEffect(MysterySeal c, AbstractMonster target) {
        if (c.sealValue > 0)
        {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                addToBot(new LoseStrengthThisTurnAction(mo, AbstractDungeon.player, c.sealValue));
            }
        }
    }

    @Override
    public void instantSealEffect(MysterySeal c, PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {
        if (c.sealValue > 0)
        {
            for (Map.Entry<AbstractMonster, PretendMonster> m : pretendMonsters.entrySet()) {
                pretendApplyPower(m.getValue(), new StrengthPower(m.getValue(), -c.sealValue), -c.sealValue);
            }
        }
    }

    @Override
    public void addSealEffect(StringBuilder description) {
        description.append(TEXT[6]);
    }
}