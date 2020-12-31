package sumireko.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.actions.IncreaseCostAction;
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

    private static final int BLOCK = 4;
    private static final int UPG_BLOCK = 1;
    private static final int DAMAGE = 3;
    private static final int UPG_DAMAGE = 1;


    public IronGirders() {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        block();

        damageSingle(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        damageSingle(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);

        addToBot(new IncreaseCostAction(this, 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new IronGirders();
    }
}