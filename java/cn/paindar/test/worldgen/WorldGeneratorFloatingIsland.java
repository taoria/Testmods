package cn.paindar.test.worldgen;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import java.util.Random;

/**
 * Created by Paindar on 2016/8/19.
 */
public class WorldGeneratorFloatingIsland implements IWorldGenerator
{

    private static boolean exc=false;
    private static float lerpf(float low,float high,float rate)
    {
        return low+(high-low)* MathHelper.sin(rate*1.57f);
    }

    private static float getGray(int gray[][][],int x,int y,int z, int strX,int strY,int strZ,int endX,int endY,int endZ,int maxG)
    {
        if(gray[y][x][z]==maxG)
        {
            if(y!=strY && y!=endY)
                gray[y][x][z]=(int)(maxG*MathHelper.sin(1.57f*(getGray(gray,x,endY,z,strX,strY,strZ,endX,endY,endZ,maxG)*(endY-y)+getGray(gray,x,strY,z,strX,strY,strZ,endX,endY,endZ,maxG)*(y-strY))/((endY-strY)*maxG)));
            else if(z!=strZ && z!=endZ)
                gray[y][x][z]=(int)(maxG*MathHelper.sin(1.57f*(getGray(gray,x,y,endZ,strX,strY,strZ,endX,endY,endZ,maxG)*(endZ-z)+getGray(gray,x,y,strZ,strX,strY,strZ,endX,endY,endZ,maxG)*(z-strZ))/((endZ-strZ)*maxG)));
            else if(x!=strX&& x!=endX)
                gray[y][x][z]=(int)(maxG*MathHelper.sin(1.57f*(getGray(gray,endX,y,z,strX,strY,strZ,endX,endY,endZ,maxG)*(endX-x)+getGray(gray,strX,y,z,strX,strY,strZ,endX,endY,endZ,maxG)*(x-strX))/((endX-strX)*maxG)));
            else
                gray[y][x][z]=maxG-1;
        }
        return gray[y][x][z];
    }
    private static void fill(int gray[][][], int strX,int strY,int strZ,int endX,int endY,int endZ,int maxG)
    {
        for(int x=strX;x<=endX;x++)
        {
            for(int y=strY;y<=endY;y++)
            {
                for (int z=strZ;z<=endZ;z++)
                {
                    if(gray[y][x][z]==maxG)
                    {
                        getGray(gray,x,y,z,strX,strY,strZ,endX,endY,endZ,maxG);
                    }
                }
            }
        }
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        //
        int x=chunkX*16+8,y=255,z=chunkZ*16+8;
        Chunk chunk=world.getChunkFromChunkCoords(chunkX,chunkZ);
        if(chunk.getBiomeGenForWorldCoords(0,0,world.getWorldChunkManager())!= BiomeGenBase.coldTaigaHills || MathHelper.getRandomDoubleInRange(random,0,1f)<=0.2f)
        {
            return;
        }
        for(;y>2;y--)
        {
            Block block =world.getBlock(x,y,z);
            if(!block.isAir(world,x,y,z))
            {
                break;
            }
        }
        if(y<=50||y>150)return;
        y+=40;
        /*


        for(int i=-8;i<=8;i++)
        {
            for(int j=-8;j<=8;j++)
            {
                //double factor=5*Math.pow(2.73,-((i*i)+(j*j))/24);
                int factor=MathHelper.abs_int(i)+MathHelper.abs_int(j);
                int height=random.nextInt(MathHelper.ceiling_double_int(5*MathHelper.cos(3.14f*factor / 32)));
                //int height=MathHelper.ceiling_double_int(factor);
                if(height==0&&factor<=8)height=1;
                for(int k=0;k<height;k++)
                {
                    if(!world.isRemote)
                        world.setBlock(x+i,y-k,z+j,Blocks.stone);
                    //√1/(16-x)
                }

            }
        }*/
        int MAXSIZE=16;
        int grayLvl[][][]=new int[MAXSIZE][MAXSIZE][MAXSIZE];
        for(int ay=0;ay<16;ay++)
        {
            for (int ax = 0; ax < 16; ax ++)
            {
                for (int az = 0; az < 16; az ++)
                {
                    grayLvl[ay][ax][az]=255;
                }
            }
        }
        for(int i=2;i<16;i+=2)
        {
            for(int j=2;j<16;j+=2)
            {
                grayLvl[8][i][j]=(0+(i-8)*(j-8)*4);
                grayLvl[12][i][j]=random.nextInt(256);
                grayLvl[4][i][j]=random.nextInt(256);
            }
        }
        for(int i=2;i<16;i+=2)
        {
            for(int j=2;j<16;j+=2)
            {
                for(int k=4;k<12;k+=2)
                {
                    if(grayLvl[k][i][j]==255)
                    {
                        grayLvl[k][i][j]=(int)(lerpf(grayLvl[k+2][i][j],grayLvl[k-2][i][j],0.5f));
                    }
                }
            }
        }
        /**              10
         *              9
         *            7
         *          5
         *        3
         *      1
         *     0  2  6 10 14 16
         *     2
         *     6
         *     10
         *     14
         *     16
          */

        for(int ay=0;ay<16;ay+=2)
        {
            for(int ax=0;ax<16;ax+=2)
            {
                for(int az=0;az<16;az+=2)
                {
                    int maxX=(ax==14)?15:ax+2,
                            maxY=(ay==14)?15:ay+2,
                            maxZ=(az==14)?15:az+2;
                    fill(grayLvl,ax,ay,az,maxX,maxY,maxZ,255);
                }
            }
        }
        if(exc)
        {
            for (int ay = 0; ay < 16; ay++) {
                for (int ax = 0; ax < 16; ax++) {
                    for (int az =0; az < 16; az++) {
                        if (grayLvl[ay][ax][az] <= 72) {
                            world.setBlock(x + ax - 8, y + ay - 8, z + az - 8, Blocks.stone);
                        }
                        else if(grayLvl[ay][ax][az] <= 144)
                            world.setBlock(x + ax - 8, y + ay - 8, z + az - 8, Blocks.grass);
                        


                    }
                }
            }
            exc=false;
        }
        else
        {
            for (int ax = -7; ax < 7; ax++) {
                for (int az = -7; az < 7; az++) {
                    for(int ay=0;ay>=(ax*ax+az*az)/9.6-5;ay--)
                    {
                        world.setBlock(x + ax , y + ay , z + az , Blocks.stone);
                    }
                }
            }

            exc=true;
        }

        System.out.println("("+x+","+y+","+z+")");
    }
}
