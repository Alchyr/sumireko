package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.effects.TormentEffect;
import sumireko.powers.TormentPower;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class Torment extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Torment",
            2,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int DAMAGE = 14;
    private static final int UPG_DAMAGE = 5;

    private static final int MAGIC = 1;

    public Torment() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new TormentEffect(m), 0.2f));
        damageSingle(m, AbstractGameAction.AttackEffect.NONE);
        applySingle(m, new TormentPower(m, p, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Torment();
    }
}