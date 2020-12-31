package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class IllegalDumping extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "IllegalDumping",
            2,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int DAMAGE = 28;
    private static final int UPG_DAMAGE = 7;

    private static final int MAGIC = 8;

    public IllegalDumping() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //garbage pile
        damageSingle(m, AbstractGameAction.AttackEffect.SMASH);
    }

    @Override
    public AbstractCard makeCopy() {
        return new IllegalDumping();
    }
}