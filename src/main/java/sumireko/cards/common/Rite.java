package sumireko.cards.common;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.actions.RiteAction;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Rite extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Rite",
            0,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY,
            CardRarity.COMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int DAMAGE = 3;

    public Rite() {
        super(cardInfo, false);

        setDamage(DAMAGE);
        tags.add(CustomCardTags.FINAL);
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new RiteAction(this));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Rite();
    }
}