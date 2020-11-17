package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.abstracts.LockingCard;
import sumireko.powers.NaturalJinxPower;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class NaturalJinx extends LockingCard {
    private final static CardInfo cardInfo = new CardInfo(
            "NaturalJinx",
            0,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int DAMAGE = 1;
    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 2;


    public NaturalJinx() {
        super(cardInfo, false);

        setDamage(DAMAGE);
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);

        damageSingle(m, AbstractGameAction.AttackEffect.POISON);
        applySingle(m, new NaturalJinxPower(m, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new NaturalJinx();
    }
}