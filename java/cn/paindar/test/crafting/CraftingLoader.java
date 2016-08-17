package cn.paindar.test.crafting;

import cn.paindar.test.item.ItemLoader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * Created by Paindar on 2016/8/17.
 */
public class CraftingLoader
{
    public CraftingLoader(FMLInitializationEvent event)
    {
        GameRegistry.addShapelessRecipe(new ItemStack(ItemLoader.seedPackageEmpty,1),new Object[]
                {
                        Items.leather
                });
        GameRegistry.addShapelessRecipe(new ItemStack(ItemLoader.seedPackageFull,1),new Object[]
                {
                        Items.wheat_seeds,Items.wheat_seeds,Items.wheat_seeds,Items.wheat_seeds,Items.wheat_seeds,ItemLoader.seedPackageEmpty
                });
    }
}
