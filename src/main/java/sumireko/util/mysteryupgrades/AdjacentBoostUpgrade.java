package sumireko.util.mysteryupgrades;

import sumireko.abstracts.SealCard;
import sumireko.cards.rare.MysterySeal;
import sumireko.enums.CustomCardTags;
import sumireko.util.MysteryUpgrade;

public class AdjacentBoostUpgrade extends MysteryUpgrade {
    public AdjacentBoostUpgrade()
    {
        super(-10, true, true);
    }

    @Override
    public void apply(MysterySeal c) {
        c.buffSeal = true;

        c.baseSealValue += SEAL_UPGRADE;
        c.sealValue += SEAL_UPGRADE;
        c.upgradedSeal = true;

        if (!c.tags.contains(CustomCardTags.BUFF_SEAL))
            c.tags.add(CustomCardTags.BUFF_SEAL);
    }

    @Override
    public void applyBaseAdjacencyEffect(MysterySeal source, SealCard c) {
        c.modifySealValue(source.baseSealValue);
    }

    @Override
    public void applyAdjacencyEffect(MysterySeal source, SealCard c) {
        c.modifySealValue(source.sealValue);
    }

    @Override
    public void addSealEffect(StringBuilder description) {
        description.append(TEXT[7]);
    }
}
