package sumireko.cards.common;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Sight extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Sight",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.COMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int MAGIC = 3;

    public Sight() {
        super(cardInfo, false);

        setMagic(MAGIC);

        tags.add(CustomCardTags.FINAL);
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        drawCards(this.magicNumber);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Sight();
    }
}