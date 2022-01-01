package sumireko.util.mysteryupgrades;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.cards.rare.MysterySeal;
import sumireko.util.MysteryUpgrade;

public class DamageAllUpgrade extends MysteryUpgrade {
    private static final int DAMAGE_UPGRADE = 6;

    public DamageAllUpgrade()
    {
        super(0, false);
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

        c.type = AbstractCard.CardType.ATTACK;

        c.baseAllDamage += DAMAGE_UPGRADE;
        c.allDamage = c.baseAllDamage;
        c.upgradedAllDamage = true;
    }

    @Override
    public void use(MysterySeal c, AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p, c.multiDamage, c.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public void addNormalEffect(StringBuilder description) {
        description.insert(0, TEXT[2]);
    }
}
