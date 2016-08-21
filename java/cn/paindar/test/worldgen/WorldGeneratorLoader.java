package cn.paindar.test.worldgen;

import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Created by Paindar on 2016/8/19.
 */
public class WorldGeneratorLoader
{
    static WorldGeneratorFloatingIsland worldGeneratorFloatingIsland=new WorldGeneratorFloatingIsland();
    public static void init()
    {
        GameRegistry.registerWorldGenerator(worldGeneratorFloatingIsland,1);
    }
}
