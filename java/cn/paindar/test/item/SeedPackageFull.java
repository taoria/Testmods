package cn.paindar.test.item;

import cn.lambdalib.util.mc.BlockSelectors;
import cn.lambdalib.util.mc.IBlockSelector;
import cn.lambdalib.util.mc.Raytrace;
import net.minecraft.block.Block;
import net.minecraft.command.CommandDefaultGameMode;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import tv.twitch.chat.ChatMessage;

/**
 * Created by Paindar on 2016/8/17.
 */
class FilterFmLand implements IBlockSelector
{
    @Override
    public boolean accepts(World world, int i, int i1, int i2, Block block)
    {
        return block== Blocks.farmland?true:false;
    }
}

public class SeedPackageFull extends Item
{
    private static float DISTANCE=15f;
    private static FilterFmLand fliterFmLand=new FilterFmLand();
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
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
        entityPlayer.addChatMessage(new ChatComponentText(itemStack.getDisplayName()));
        Vec3 start=Vec3.createVectorHelper(entityPlayer.posX,entityPlayer.posY+entityPlayer.getEyeHeight(),entityPlayer.posZ),
            looking=entityPlayer.getLookVec(),
            end=Vec3.createVectorHelper(entityPlayer.posX+looking.xCoord*DISTANCE,entityPlayer.posY+looking.yCoord*DISTANCE,entityPlayer.posZ+looking.zCoord*DISTANCE);
        MovingObjectPosition mop= Raytrace.rayTraceBlocks(world,start,end,fliterFmLand);
        if(mop!=null)
        {
            for(int i=-1;i<=1;i++)
            {
                for(int j=-1;j<=1;j++)
                {
                    attemptSustain(world,mop.blockX+i,mop.blockY,mop.blockZ+j);
                }

            }
            if(!entityPlayer.capabilities.isCreativeMode)
            {
                itemStack.stackSize--;
                entityPlayer.inventory.addItemStackToInventory(new ItemStack(ItemLoader.seedPackageEmpty, 1));
            }

        }
        return itemStack;
    }


}
