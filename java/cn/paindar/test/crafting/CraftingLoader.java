package cn.paindar.test.crafting;

import cn.paindar.test.item.ItemLoader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * Created by Paindar on 2016/8/17.
 */
public class CraftingLoader
{
    public CraftingLoader()
    {
        GameRegistry.addShapelessRecipe(new ItemStack(ItemLoader.seedPackage,1),new Object[]
                {
                        Items.leather
                });
    }
}
