/*package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.abstracts.LockingCard;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class LuckyCharm extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "LuckyCharm",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);

    private static final int UPG_COST = 0;

    private static final int MAGIC = 0;

    public LuckyCharm() {
        super(cardInfo, false);

        this.selfRetain = true;
        setCostUpgrade(UPG_COST);
        setMagic(MAGIC);
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        this.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.magicNumber));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.magicNumber = ++this.baseMagicNumber;
    }

    @Override
    public AbstractCard makeCopy() {
        return new LuckyCharm();
    }
}*/