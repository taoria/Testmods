/*
 _______________#########_______________________
______________############_____________________
______________#############____________________
_____________##__###########___________________
____________###__######_#####__________________
____________###_#######___####_________________
___________###__##########_####________________
__________####__###########_####_______________
________#####___###########__#####_____________
_______######___###_########___#####___________
_______#####___###___########___######_________
______######___###__###########___######_______
_____######___####_##############__######______
____#######__#####################_#######_____
____#######__##############################____
___#######__######_#################_#######___
___#######__######_######_#########___######___
___#######____##__######___######_____######___
___#######________######____#####_____#####____
____######________#####_____#####_____####_____
_____#####________####______#####_____###______
______#####______;###________###______#________
________##_______####________####______________
          ワールドイズマイン！

 */
package cn.paindar.test.common;

import cn.paindar.test.crafting.CraftingLoader;
import cn.paindar.test.item.ItemLoader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = test.MODID, name = test.NAME, version = test.VERSION, acceptedMinecraftVersions = "1.7.10")
public class test
{
    public static final String MODID = "test";
    public static final String NAME = "test";
    public static final String VERSION = "2.3.3";
    //特に意味はない ——to Steins Gate
    @Mod.Instance(test.MODID)
    public static test instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ItemLoader.Init(event);

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        CraftingLoader.Init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }


}