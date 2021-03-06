package sumireko.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.abstracts.SealCard;
import sumireko.actions.LambdaDrawPileToHandAction;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Liberator extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Liberator",
            0,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.COMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 4;


    public Liberator() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        damageSingle(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        addToBot(new LambdaDrawPileToHandAction(1, (c)->c instanceof SealCard));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Liberator();
    }
}