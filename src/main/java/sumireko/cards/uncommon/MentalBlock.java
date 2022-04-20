package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.powers.MentalBlockPower;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class MentalBlock extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "MentalBlock",
            0,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 3;


    public MentalBlock() {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        block();

        if (!p.hasPower(MentalBlockPower.POWER_ID)) {
            applySelf(new MentalBlockPower(p));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new MentalBlock();
    }
}