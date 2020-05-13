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


    private static final int MAGIC = 5;
    private static final int UPG_MAGIC = 2;

    private static final int SEAL = 5;
    private static final int UPG_SEAL = 2;

    public DeflectionSeal() {
        super(cardInfo, false);

        setMagic(MAGIC, UPG_MAGIC);
        setSeal(SEAL, UPG_SEAL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //vfx of frost orb pieces flying outwards?
        //make a new power later
        applySelf(new FlameBarrierPower(p, this.magicNumber));

        super.use(p, m);
    }

    @Override
    public void triggerSealEffect(AbstractMonster target) {
        if (this.sealValue > 0)
            addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.sealValue));
    }

    @Override
    public void instantSealEffect(PretendMonster target, Map<AbstractMonster, PretendMonster> pretendMonsters) {

    }

    @Override
    public AbstractCard makeCopy() {
        return new DeflectionSeal();
    }
}