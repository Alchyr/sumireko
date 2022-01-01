package sumireko.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.abstracts.LockingCard;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class TelephonePole extends LockingCard {
    private final static CardInfo cardInfo = new CardInfo(
            "TelephonePole",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.COMMON);
    // attack

    public static final String ID = makeID(cardInfo.cardName);


    private static final int DAMAGE = 11;
    private static final int UPG_DAMAGE = 3;

    private static final int DEBUFF = 2;

    private static final int BLOCK = 4;
    private static final int UPG_BLOCK = 1;

    public TelephonePole() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(DEBUFF);
        //setBlock(BLOCK, UPG_BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);

        //block();
        damageSingle(m, AbstractGameAction.AttackEffect.SMASH);
        applySingle(m, getVuln(m, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new TelephonePole();
    }
}