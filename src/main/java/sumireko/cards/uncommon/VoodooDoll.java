package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.actions.general.DamageWeakestEnemyAction;
import sumireko.interfaces.SleevedCard;
import sumireko.util.CardInfo;

import static sumireko.SumirekoMod.makeID;

public class VoodooDoll extends BaseCard implements SleevedCard {
    private final static CardInfo cardInfo = new CardInfo(
            "VoodooDoll",
            1,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON);

    public static final String ID = makeID(cardInfo.cardName);


    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 1;


    public VoodooDoll() {
        super(cardInfo, true);

        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.shuffleBackIntoDrawPile = true;
    }

    @Override
    public void sleevedOnAttacked(DamageInfo info) {
        if (info.type == DamageInfo.DamageType.NORMAL && info.owner != null && info.owner != AbstractDungeon.player) {
            addToTop(new DamageWeakestEnemyAction(new DamageInfo(AbstractDungeon.player, this.magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.POISON, false));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // :)
    }

    @Override
    public AbstractCard makeCopy() {
        return new VoodooDoll();
    }
}