package cn.paindar.test.item;

import com.google.common.collect.Sets;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * Created by Paindar on 2016/8/17.
 */
public class FFFWand extends ItemTool
{
    private Set<EntityPlayer> list=Sets.newHashSet();

    public FFFWand()
    {
        super(6f,ToolMaterial.WOOD, Sets.newHashSet());
        setMaxDamage(4095);
        this.setCreativeTab(CreativeTabs.tabTools);
        this.setUnlocalizedName("fffWand");
    }

    @Override
    public boolean hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase source)
    {
        itemStack.damageItem(1, source);
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        double dis=Math.random()*15, rad=Math.random()*6.28;
        int iCount=5;
        EntityLightningBolt lightning = new EntityLightningBolt(world, player.posX+dis*Math.cos(rad), player.posY,player.posZ+dis*Math.sin(rad));
        player.worldObj.addWeatherEffect(lightning);
        List list = world.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(player.posX-15D, player.posY-15D, player.posZ-15D, player.posX+15D, player.posY+15D, player.posZ+15D));
        for(Iterator iterator = list.iterator(); iterator.hasNext();)
        {
            EntityLiving entity = (EntityLiving)iterator.next();
            if(entity.equals(player))
            {
                continue;
            }
            entity.setFire(5);
            if(!player.capabilities.isCreativeMode)
            {
                iCount++;

                if(itemStack.getItemDamage()<=0)
                {
                    break;
                }
            }
        }
        itemStack.damageItem(iCount,player);
        return itemStack;
    }

    @SubscribeEvent
    public void OnPlayerDamaged(LivingHurtEvent event)
    {

    }
}
