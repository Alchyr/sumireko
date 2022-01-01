package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.powers.ProwessDownPower;
import sumireko.powers.ProwessPower;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Forcefield extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Forcefield",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int BLOCK = 8;
    private static final int UPG_BLOCK = 1;

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;


    public Forcefield() {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        block();


        applySelf(new ProwessPower(p, this.magicNumber));
        applySelf(new ProwessDownPower(p, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Forcefield();
    }
}