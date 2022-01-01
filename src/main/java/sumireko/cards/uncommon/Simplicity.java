/*package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.enums.CustomCardTags;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Simplicity extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Simplicity",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);

    private static final int PER_ENERGY = 4;

    public Simplicity() {
        super(cardInfo, false);

        setMagic(0);
        tags.add(CustomCardTags.FINAL);
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        this.magicNumber = this.baseMagicNumber = 0;

        int count = 0;
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
        {
            if (!c.upgraded)
            {
                ++count;
                if (count >= PER_ENERGY)
                {
                    count = 0;
                    this.magicNumber = ++this.baseMagicNumber;
                }
            }
        }

        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainEnergyAction(this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Simplicity();
    }
}*/