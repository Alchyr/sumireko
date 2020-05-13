package sumireko.abstracts;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.interfaces.LockingCardInterface;
import sumireko.util.CardInfo;

public abstract class LockingCard extends BaseCard implements LockingCardInterface {
    private boolean locked;

    public LockingCard(CardInfo cardInfo, boolean upgradesDescription) {
        super(cardInfo, upgradesDescription);
        locked = false;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (locked)
            return false;
        return super.canUse(p, m);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        lockCard();
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard copy = super.makeStatEquivalentCopy();
        if (copy instanceof LockingCard)
        {
            ((LockingCard) copy).locked = this.locked;
        }
        return copy;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public void lockCard() {
        this.locked = true;

        this.rawDescription = (this.upgraded && this.upgradesDescription) ? cardStrings.EXTENDED_DESCRIPTION[1] : cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public void unlockCard() {
        this.locked = false;

        this.rawDescription = (this.upgraded && this.upgradesDescription) ? cardStrings.UPGRADE_DESCRIPTION : cardStrings.DESCRIPTION;
        this.initializeDescription();
    }
}
