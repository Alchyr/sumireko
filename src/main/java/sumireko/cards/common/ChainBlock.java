/*package sumireko.cards.common;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import sumireko.abstracts.BaseCard;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class ChainBlock extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "ChainBlock",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.COMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int BLOCK = 9;

    public ChainBlock() {
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
        AbstractCard copy = new ChainBlock();

        if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
        {
            copy.modifyCostForCombat(-999999);
        }

        return copy;
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard copy = super.makeStatEquivalentCopy();

        if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT)
        {
            copy.modifyCostForCombat(-999999);
        }

        return copy;
    }
}*/