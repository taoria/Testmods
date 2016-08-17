package cn.paindar.test.item;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

/**
 * Created by Paindar on 2016/8/17.
 */
public class ItemLoader
{
    public static SeedPackageEmpty seedPackageEmpty = new SeedPackageEmpty();
    public static SeedPackageFull seedPackageFull = new SeedPackageFull();
    public static FFFWand fffWand = new FFFWand();

    public static void Init(FMLPreInitializationEvent event)
    {
        RegisterItem(seedPackageEmpty, "seed_emptyPackage");
        RegisterItem(seedPackageFull, "seed_fullPackage");
        RegisterItem(fffWand, "fffWand");
    }

    private static void RegisterItem(Item item, String name)
    {
        GameRegistry.registerItem(item, name);
        item.setTextureName(GameData.getItemRegistry().getNameForObject(item).toString());
    }
}
