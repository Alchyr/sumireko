/*package sumireko.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.relics.TungstenRod;
import sumireko.abstracts.SealCard;
import sumireko.util.CardInfo;
import sumireko.util.HealthBarRender;
import sumireko.util.PretendMonster;
import sumireko.util.SealIntent;

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


    private static final int DAMAGE = 16;
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
    public HealthBarRender instantSealEffect(PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {
        return new HealthBarRender(AbstractDungeon.player, (AbstractDungeon.player.hasPower(IntangiblePower.POWER_ID) ? Math.min(this.sealValue, 1) : this.sealValue) - (AbstractDungeon.player.hasRelic(TungstenRod.ID) ? 1 : 0));
    }

    @Override
    public void getIntent(SealIntent i) {
        i.amount = -this.sealValue;
        i.intent = AbstractMonster.Intent.MAGIC;
    }

    @Override
    public AbstractCard makeCopy() {
        return new MurderSeal();
    }
}*/