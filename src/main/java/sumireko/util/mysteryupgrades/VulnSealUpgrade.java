package sumireko.util.mysteryupgrades;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.cards.rare.MysterySeal;
import sumireko.util.MysteryUpgrade;
import sumireko.util.PretendMonster;

import java.util.ArrayList;
import java.util.Map;

import static sumireko.abstracts.SealCard.pretendApplyPower;

public class VulnSealUpgrade extends MysteryUpgrade {
    public VulnSealUpgrade()
    {
        super(-200, true, true);
    }

    @Override
    public void apply(MysterySeal c) {
        c.debuffSeal = true;

        if (c.target == AbstractCard.CardTarget.SELF || c.target == AbstractCard.CardTarget.ALL)
        {
            c.target = AbstractCard.CardTarget.SELF_AND_ENEMY;
        }
        else if (c.target != AbstractCard.CardTarget.SELF_AND_ENEMY)
        {
            c.target = AbstractCard.CardTarget.ENEMY;
        }

        c.baseSealValue += SEAL_UPGRADE;
        c.sealValue += SEAL_UPGRADE;
        c.upgradedSeal = true;
    }

    @Override
    public void triggerSealEffect(MysterySeal c, AbstractMonster target, ArrayList<AbstractGameAction> actions) {
        if (c.sealValue > 0)
            actions.add(new ApplyPowerAction(target, AbstractDungeon.player, getVuln(target, c.sealValue), c.sealValue, true));
    }

    @Override
    public void instantSealEffect(MysterySeal c, PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {
        if (target != null && c.sealValue > 0)
            pretendApplyPower(target, getVuln(target, c.sealValue), c.sealValue);
    }

    @Override
    public void addSealEffect(StringBuilder description) {
        description.append(TEXT[5]);
    }
}