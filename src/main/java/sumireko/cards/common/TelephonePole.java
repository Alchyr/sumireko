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


    private static final int DAMAGE = 14;
    private static final int UPG_DAMAGE = 4;


    public TelephonePole() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);

        damageSingle(m, AbstractGameAction.AttackEffect.SMASH);
    }

    @Override
    public AbstractCard makeCopy() {
        return new TelephonePole();
    }
}