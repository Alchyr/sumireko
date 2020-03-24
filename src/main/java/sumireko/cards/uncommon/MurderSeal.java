package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sumireko.abstracts.BaseCard;
import sumireko.abstracts.SealCard;
import sumireko.util.CardInfo;
import sumireko.util.PretendMonster;

import java.util.Map;

import static sumireko.SumirekoMod.makeID;

public class MurderSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "MurderSeal",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.UNCOMMON);
    // attack

    public static final String ID = makeID(cardInfo.cardName);


    private static final int DAMAGE = 14;
    private static final int UPG_DAMAGE = 4;

    private static final int SEAL = 2;

    public MurderSeal() {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        setSeal(SEAL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        damageSingle(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
        super.use(p, m);
    }

    @Override
    public void triggerSealEffect(AbstractMonster target) {
        addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, this.sealValue));
    }

    @Override
    public void instantSealEffect(PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {

    }

    @Override
    public AbstractCard makeCopy() {
        return new MurderSeal();
    }
}