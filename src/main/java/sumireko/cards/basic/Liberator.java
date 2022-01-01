package sumireko.cards.basic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.LockingCard;
import sumireko.abstracts.SealCard;
import sumireko.actions.general.LambdaDrawPileToHandAction;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Liberator extends LockingCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Liberator",
            0,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.BASIC);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 3;


    public Liberator() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        //setExhaust(true, false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);

        damageSingle(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        addToBot(new LambdaDrawPileToHandAction(1, (c)->c instanceof SealCard));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Liberator();
    }
}