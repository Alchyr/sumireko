package sumireko.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.actions.cards.GirdersDamageDownAction;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class IronGirder extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "IronGirders",
            0,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.COMMON);
    // attack

    public static final String ID = makeID(cardInfo.cardName);

    private static final int DAMAGE = 9;
    private static final int UPG_DAMAGE = 2;
    //private static final int DEBUFF = 2;


    public IronGirder() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        //setMagic(DEBUFF);

        this.shuffleBackIntoDrawPile = true;
    }

    @Override
    protected void upgradeName() {
        ++this.timesUpgraded;
        this.upgraded = true;
        if (timesUpgraded >= 1) {
            this.name = cardStrings.EXTENDED_DESCRIPTION[0] + "+";
            if (timesUpgraded > 1) { //who using infinite journal
                this.name += this.timesUpgraded;
            }
        }
        else { //somehow times upgraded was less than 0?
            this.name = cardStrings.NAME;
        }
        this.initializeTitle();
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
        return new IronGirder();
    }
}