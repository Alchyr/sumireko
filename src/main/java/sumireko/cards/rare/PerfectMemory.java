package sumireko.cards.rare;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.powers.PerfectMemoryPower;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class PerfectMemory extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "PerfectMemory",
            0,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.RARE);

    public static final String ID = makeID(cardInfo.cardName);

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public PerfectMemory() {
        super(cardInfo, true);

        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applySelf(new PerfectMemoryPower(p, upgraded ? this.magicNumber : 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new PerfectMemory();
    }
}