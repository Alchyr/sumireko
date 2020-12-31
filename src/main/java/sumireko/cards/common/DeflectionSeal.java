package sumireko.cards.common;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FlameBarrierPower;
import sumireko.abstracts.SealCard;
import sumireko.util.CardInfo;
import sumireko.util.PretendMonster;
import sumireko.util.SealIntent;

import java.util.Map;

import static sumireko.SumirekoMod.makeID;

public class DeflectionSeal extends SealCard {
    private final static CardInfo cardInfo = new CardInfo(
            "DeflectionSeal",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.COMMON);
    // skill

    public static final String ID = makeID(cardInfo.cardName);


    private static final int BLOCK = 7;
    private static final int UPG_BLOCK = 2;

    private static final int SEAL = 4;
    private static final int UPG_SEAL = 1;

    public DeflectionSeal() {
        super(cardInfo, false);

        setBlock(BLOCK, UPG_BLOCK);
        setSeal(SEAL, UPG_SEAL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //vfx of frost orb pieces flying outwards?
        //make a new power later
        block();

        super.use(p, m);
    }

    @Override
    public void triggerSealEffect(AbstractMonster target) {
        if (this.sealValue > 0)
            applySelf(new FlameBarrierPower(AbstractDungeon.player, this.sealValue));
    }

    @Override
    public void getIntent(SealIntent i) {
        i.intent = AbstractMonster.Intent.MAGIC;
    }

    @Override
    public AbstractCard makeCopy() {
        return new DeflectionSeal();
    }
}