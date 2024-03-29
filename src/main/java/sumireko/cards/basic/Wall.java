/*package sumireko.cards.basic;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Wall extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Wall",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.BASIC);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int BLOCK = 6;


    public Wall() {
        super(cardInfo, false);

        setBlock(BLOCK);
        tags.add(CustomCardTags.FINAL);
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        block();
    }

    @Override
    public AbstractCard makeCopy() {
        return new Wall();
    }
}*/