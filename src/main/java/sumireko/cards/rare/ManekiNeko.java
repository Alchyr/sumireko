package sumireko.cards.rare;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.enums.CustomCardTags;
import sumireko.interfaces.SleevedCard;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class ManekiNeko extends BaseCard implements SleevedCard {
    private final static CardInfo cardInfo = new CardInfo(
            "ManekiNeko",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.RARE);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int BLOCK = 2;
    private static final int UPG_BLOCK = 4;

    private static final int MAGIC = 1;

    public ManekiNeko() {
        super(cardInfo, true);

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(MAGIC);
        tags.add(CustomCardTags.UNPLAYABLE);

        selfRetain = true;
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!upgraded) {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;
        }
        else {
            return super.canUse(p, m);
        }
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.target = CardTarget.SELF;
        tags.remove(CustomCardTags.UNPLAYABLE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            block();
        }
    }

    @Override
    public void sleevedPlayCard(UseCardAction a, AbstractCard played, AbstractCreature target) {
        addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ManekiNeko();
    }
}