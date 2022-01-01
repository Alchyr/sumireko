package sumireko.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sumireko.abstracts.BaseCard;
import sumireko.actions.cards.GirdersDamageDownAction;
import sumireko.actions.general.IncreaseCostAction;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class IronGirders extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "IronGirders",
            0,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.COMMON);
    // attack

    public static final String ID = makeID(cardInfo.cardName);

    //private static final int BLOCK = 4;
    //private static final int UPG_BLOCK = 1;
    private static final int DAMAGE = 9 ;
    private static final int UPG_DAMAGE = 2;
    //private static final int DEBUFF = 2;


    public IronGirders() {
        super(cardInfo, false);

        //setBlock(BLOCK, UPG_BLOCK);
        setDamage(DAMAGE, UPG_DAMAGE);
        setInnate(true);
        //setMagic(DEBUFF);

        this.shuffleBackIntoDrawPile = true;
    }

    //Some jank so that if you get deep dream and 1 strength it doesn't go infinite damage
    @Override
    public void applyPowers() {
        super.applyPowers();

        if (baseDamage < 0)
            isDamageModified = true;

        if (damage < 0)
            damage = 0;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);

        if (baseDamage < 0)
            isDamageModified = true;

        if (damage < 0)
            damage = 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //block();

        //damageSingle(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY, true);
        damageSingle(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);

        /*applySingle(m, new StrengthPower(m, -this.magicNumber), true);
        if (!m.hasPower(ArtifactPower.POWER_ID))
            applySingle(m, new GainStrengthPower(m, this.magicNumber), true);*/


        //addToBot(new IncreaseCostAction(this, 1));
        addToBot(new GirdersDamageDownAction(this.uuid));
    }

    @Override
    public AbstractCard makeCopy() {
        return new IronGirders();
    }
}