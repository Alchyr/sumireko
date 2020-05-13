package sumireko.util.mysteryupgrades;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.cards.rare.MysterySeal;
import sumireko.util.MysteryUpgrade;

public class DamageUpgrade extends MysteryUpgrade {
    private static final int DAMAGE_UPGRADE = 8;

    public DamageUpgrade()
    {
        super(-69, false);
    }

    @Override
    public void apply(MysterySeal c) {
        if (c.target == AbstractCard.CardTarget.SELF || c.target == AbstractCard.CardTarget.ALL)
        {
            c.target = AbstractCard.CardTarget.SELF_AND_ENEMY;
        }
        else if (c.target != AbstractCard.CardTarget.SELF_AND_ENEMY)
        {
            c.target = AbstractCard.CardTarget.ENEMY;
        }

        c.type = AbstractCard.CardType.ATTACK;

        c.baseDamage += DAMAGE_UPGRADE;
        c.upgradedDamage = true;
    }

    @Override
    public void use(MysterySeal c, AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, c.damage, c.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public void addNormalEffect(StringBuilder description) {
        description.insert(0, TEXT[0]);
    }
}
