/*package sumireko.util.mysteryupgrades;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.actions.general.DamageRandomConditionalEnemyAction;
import sumireko.cards.rare.MysterySeal;
import sumireko.util.MysteryUpgrade;
import sumireko.util.PretendMonster;

import java.util.ArrayList;
import java.util.Map;

public class RandomSealDamageUpgrade extends MysteryUpgrade {
    public RandomSealDamageUpgrade()
    {
        super(-500, true, true);
    }

    @Override
    public void apply(MysterySeal c) {
        c.damageSeal = true;

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
    public void triggerSealEffect(MysterySeal c, AbstractMonster target, ArrayList<AbstractGameAction> actions) {
        if (c.sealValue > 0)
            actions.add(new DamageRandomConditionalEnemyAction((m)->true, new DamageInfo(AbstractDungeon.player, c.sealValue, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.LIGHTNING, false));
    }

    @Override
    public void instantSealEffect(MysterySeal c, PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {
        if (c.sealValue > 0)
        {
            if (pretendMonsters.size() == 1)
            {
                for (Map.Entry<AbstractMonster, PretendMonster> monster : pretendMonsters.entrySet())
                {
                    //this should be the only one.
                    monster.getValue().sealDamage(new DamageInfo(AbstractDungeon.player, c.sealValue, DamageInfo.DamageType.THORNS), c);
                }
            }
        }
    }

    @Override
    public void addSealEffect(StringBuilder description) {
        description.append(TEXT[3]);
    }
}*/