package cn.paindar.test.item;



import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Paindar on 2016/8/17.
 */


public class SeedPackageFull extends Item
{
    public  SeedPackageFull()
    {
        super();
        this.setUnlocalizedName("seedPackageFull");
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    private boolean attemptSustain(World world,int x,int y,int z)
    {
        ItemSeeds itemSeeds=(ItemSeeds)Items.wheat_seeds;
        if (world.getBlock(x,y,z).canSustainPlant(world,x,y,z, ForgeDirection.UP, itemSeeds) && world.isAirBlock(x,y+1,z))
        {
            if(!world.isRemote)
            {
                world.setBlock(x, y + 1, z, Blocks.wheat);
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int dir, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        if (dir != 1)
        {
            return false;
        }

        for(int i=-1;i<=1;i++)
        {
            for(int j=-1;j<=1;j++)
            {
                attemptSustain(world,x+i,y,z+j);
            }
        }
        if(!entityPlayer.capabilities.isCreativeMode)
        {
            itemStack.stackSize--;
            entityPlayer.inventory.addItemStackToInventory(new ItemStack(ItemLoader.seedPackageEmpty, 1));
        }
        return true;
    }


}
