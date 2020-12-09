package sumireko.cards.uncommon;

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
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);


    public PerfectMemory() {
        super(cardInfo, false);

    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applySelf(new PerfectMemoryPower(p));
    }

    @Override
    public AbstractCard makeCopy() {
        return new PerfectMemory();
    }
}