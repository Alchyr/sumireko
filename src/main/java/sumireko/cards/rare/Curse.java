package sumireko.cards.rare;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sumireko.abstracts.LockingCard;
import sumireko.effects.CurseEffect;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Curse extends LockingCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Curse",
            1,
            CardType.SKILL,
            CardTarget.ENEMY,
            CardRarity.RARE);

    public static final String ID = makeID(cardInfo.cardName);

    private static final int UPG_COST = 0;

    private static final int MAGIC = 2;

    public Curse() {
        super(cardInfo, false);

        setCostUpgrade(UPG_COST);
        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);

        if (m != null) {
            this.addToBot(new VFXAction(new CurseEffect(m), Settings.FAST_MODE ? 0.4f : 0.8f));
            this.addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, -this.magicNumber), -this.magicNumber));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Curse();
    }
}