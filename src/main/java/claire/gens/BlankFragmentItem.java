package claire.gens;

import net.minecraft.world.item.Item;

public class BlankFragmentItem extends Item {
    public FragmentType type;

    public BlankFragmentItem(Properties properties) {
        super(properties);
        type = FragmentType.Blank;
    }

    public BlankFragmentItem(Properties properties, FragmentType type1) {
        super(properties);
        type = type1;
    }

}
