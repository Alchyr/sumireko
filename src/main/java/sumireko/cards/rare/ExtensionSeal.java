package sumireko.cards.rare;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.LockingCard;
import sumireko.abstracts.SealCard;
import sumireko.util.CardInfo;
import sumireko.util.PretendMonster;

import java.util.Map;

import static sumireko.SumirekoMod.makeID;

public class ExtensionSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "ExtensionSeal",
            2,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.RARE);
    // skill

    public boolean locked;

    public static final String ID = makeID(cardInfo.cardName);

    private static final int SEAL = 2;

    public ExtensionSeal() {
        super(cardInfo, true);

        setSeal(SEAL);
        isEthereal = true;
        locked = false;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.isEthereal = false;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (locked)
            return false;
        return super.canUse(p, m);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.locked = true;

        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();

        super.use(p, m);
    }

    @Override
    public void triggerSealEffect(AbstractMonster target) {

    }

    @Override
    public void instantSealEffect(PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {
    }

    @Override
    public void applyFinalBaseAdjacencyEffect(SealCard c) {
        c.sealValue *= this.baseSealValue;
        if (c.sealValue != c.baseSealValue)
            c.isSealModified = true;
    }

    @Override
    public void applyFinalAdjacencyEffect(SealCard c) {
        c.sealValue *= this.sealValue;
        if (c.sealValue != c.baseSealValue)
            c.isSealModified = true;
    }

    @Override
    public AbstractCard makeCopy() {
        return new ExtensionSeal();
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard copy = super.makeStatEquivalentCopy();
        if (copy instanceof ExtensionSeal)
        {
            ((ExtensionSeal) copy).locked = this.locked;
        }
        return copy;
    }
}