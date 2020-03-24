package sumireko.cards.common;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import sumireko.abstracts.BaseCard;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Dream extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Dream",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.COMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    private static final int BLOCK = 7;
    private static final int UPG_BLOCK = 3;

    private static final int MAGIC = 2;

    public Dream() {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        block();
        applySelf(new DrawCardNextTurnPower(p, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Dream();
    }
}