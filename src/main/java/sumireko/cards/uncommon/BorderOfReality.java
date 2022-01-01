/*package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.SumirekoMod;
import sumireko.abstracts.BaseCard;
import sumireko.enums.CustomCardTags;
import sumireko.patches.occult.OccultFields;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class BorderOfReality extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "BorderOfReality",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.UNCOMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    private static final int BLOCK = 12;
    private static final int UPG_BLOCK = 3;

    private boolean reducedForCombat = false;
    private int originalBlock;

    public BorderOfReality() {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
        originalBlock = baseBlock;
    }

    @Override
    public void applyPowers() {
        if (!reducedForCombat) //for the sake of fucky stuff like ring of chaos.
        {
            originalBlock = baseBlock;
            reducedForCombat = true;
        }

        baseBlock = originalBlock - SumirekoMod.occultPlayedThisCombat;

        super.applyPowers();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        block();
    }

    @Override
    public AbstractCard makeCopy() {
        return new BorderOfReality();
    }
}*/