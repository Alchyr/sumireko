package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
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


    private static final int BLOCK = 14;

    private boolean reducedForCombat = false;
    private int originalBlock;

    public BorderOfReality() {
        super(cardInfo, false);

        setBlock(BLOCK);
        originalBlock = baseBlock;

        this.tags.add(CustomCardTags.FINAL);
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void applyPowers() { //for the sake of fucky stuff like ring of chaos.
        if (!reducedForCombat)
        {
            originalBlock = baseBlock;
            reducedForCombat = true;
        }

        baseBlock = originalBlock;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat)
        {
            if (OccultFields.isOccult.get(c))
                --baseBlock;
        }

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
}