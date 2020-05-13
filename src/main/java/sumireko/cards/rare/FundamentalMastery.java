/*package sumireko.cards.rare;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.enums.CustomCardTags;
import sumireko.powers.FundamentalPower;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class FundamentalMastery extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "FundamentalMastery",
            3,
            CardType.POWER,
            CardTarget.SELF,
            CardRarity.RARE);
    // power

    public static final String ID = makeID(cardInfo.cardName);


    public FundamentalMastery() {
        super(cardInfo, false);

        this.tags.add(CustomCardTags.FINAL);
}

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applySelf(new FundamentalPower(p));
    }

    @Override
    public AbstractCard makeCopy() {
        return new FundamentalMastery();
    }
}*/