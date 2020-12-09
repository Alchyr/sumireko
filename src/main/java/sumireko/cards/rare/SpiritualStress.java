package sumireko.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class SpiritualStress extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "SpiritualStress",
            5,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.RARE);
    // attack

    public static final String ID = makeID(cardInfo.cardName);

    private static final int UPG_COST = 8;

    public SpiritualStress() {
        super(cardInfo, false);

        setCostUpgrade(UPG_COST);
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        this.modifyCostForCombat(1);
    }

    @Override
    public void applyPowers() {
        this.baseDamage = this.freeToPlay() ? 0 : this.costForTurn;
        super.applyPowers();

        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        this.baseDamage = this.freeToPlay() ? 0 : this.costForTurn;
        super.calculateCardDamage(mo);
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        damageSingle(m, AbstractGameAction.AttackEffect.FIRE);
        damageSingle(m, AbstractGameAction.AttackEffect.LIGHTNING);
    }

    @Override
    public AbstractCard makeCopy() {
        return new SpiritualStress();
    }
}