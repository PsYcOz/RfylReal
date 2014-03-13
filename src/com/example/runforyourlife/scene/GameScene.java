package com.example.runforyourlife.scene;

import java.util.ArrayList;
import java.util.Random;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;
import android.hardware.SensorManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.example.runforyourlife.graphics.Enum_Platform;
import com.example.runforyourlife.graphics.MainCharacter;
import com.example.runforyourlife.graphics.PlatformStructure;
import com.example.runforyourlife.scene.SceneManager.SceneType;

public class GameScene extends BaseScene implements IOnSceneTouchListener 
{
	//---------------------------------------------
    // VARIABLES
    //---------------------------------------------
		//----------Scene
		//time
		private float						timeSecFloat=0f;
		private int						timeSec=0;
		private int						timeMin=0;
		private int						timeHou=0;
		private int						lastUpdateTimeSec=0;
		//PlatFrom
		ArrayList<PlatformStructure> 		platformList;
		private float						lastPlatformPos=0f;
		boolean								firstCall = true;
		int									addDistance = 0;
		//----------Physics
		private PhysicsWorld 				mPhysicsWorld;
		private static final FixtureDef 	FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0.0f, 0.0f);
		//----------MainChar
		private MainCharacter				mainCharacter;
		private boolean					firstTouch = false;
		
	//---------------------------------------------
	// METHODS
	//---------------------------------------------
	private void							createBackground()
	{
		int CAMERA_HEIGHT = 480;
		
		final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(0.0f
																		, new Sprite(0
																						, CAMERA_HEIGHT - resourcesManager.gameTextureRegionBackground.getHeight()
																						, resourcesManager.gameTextureRegionBackground
																						, vbom)));
		setBackground(autoParallaxBackground);
	}
	private void							createPhysics()
	{
		mPhysicsWorld =  new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_JUPITER), false);
		mPhysicsWorld.setContactListener(contactListener());
	    registerUpdateHandler(mPhysicsWorld);
	}
	@Override
	public void 							createScene() 
	{	
		createBackground();
		createPhysics();
		
		setOnSceneTouchListener(this);
		
		platformList=new ArrayList<PlatformStructure>();
		
		//*********************TESTS
		//MainChar
		mainCharacter = new MainCharacter(0,90, vbom, camera, mPhysicsWorld) 
		{			
			@Override
			public void 					onDie() 
			{
				
			}
		};

		mainCharacter._mcharBody = PhysicsFactory.createBoxBody(this.mPhysicsWorld, mainCharacter, BodyType.DynamicBody, FIXTURE_DEF);
		this.attachChild(mainCharacter);
		this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(mainCharacter, mainCharacter._mcharBody, true, true));
		mainCharacter._mcharBody.setFixedRotation(true);
		
		//Platfrom&&Mainloop
		this.registerUpdateHandler(new IUpdateHandler()
		{

			@Override
			public void 					onUpdate(float pSecondsElapsed) 
			{	
				handleClock(pSecondsElapsed);
				
				if(timeSec != lastUpdateTimeSec)
				{
					System.out.println(timeHou+":"+timeMin+":"+timeSec);
					lastUpdateTimeSec=timeSec;
				}
				
				if( needNewPlatform())
				{
					lastUpdateTimeSec = timeSec;
					randomPlatform();
				}
				
				while(existPlatformToErase())
				{
					deletePlatform();
				}
			}
			@Override
			public void reset() {}
		});
		
		
	}
	@Override
	public void 							onBackKeyPressed() 
	{
		SceneManager.getInstance().loadMenuScene(engine);
	}

	@Override
	public SceneType 						getSceneType() 
	{
		return SceneType.SCENE_GAME;
	}

	@Override
	public void 							disposeScene() 
	{
		//camera.setHUD(null);
	    camera.setCenter(400, 240);

	    // TODO code responsible for disposing scene
	    // removing all game scene objects.
	}
	
	//---------------------------------------------
    // ACTION HANDLER
    //---------------------------------------------
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent)
	{
		if (!firstTouch)
	    {
			mainCharacter.setRunning();
	        firstTouch = true;
	    }
	    else
	    {
	    	mainCharacter.jump();
	    }
	    return false;
	}
	
	private ContactListener 				contactListener()
	{
	    ContactListener contactListener = new ContactListener()
	    {

			@Override
			public void 					beginContact(Contact contact) 
			{
				int 						jj =0;
	            int 						footY = 115;
				
	             for(jj=0; jj < platformList.size()   ; jj++)
	             {            	 
	            	 if(platformList.get(jj).get_grass().collidesWith(mainCharacter))
	            	 {
	            		 if(mainCharacter.getY() + footY > platformList.get(jj).get_grass().getY() && mainCharacter.getY() + footY <= platformList.get(jj).get_grass().getY() + 65)
	            		 {
	            			 mainCharacter.increaseFootContacts();
	            			 break;
	            		 }
	            		 
	            	 }
	             } 
	            
	            	 	
	            mainCharacter.setRunning();
			}

			@Override
			public void 					endContact(Contact contact) 
			{
				mainCharacter.decreaseFootContacts();
			}

			@Override
			public void 					preSolve(Contact contact, Manifold oldManifold) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void 					postSolve(Contact contact, ContactImpulse impulse) {
				// TODO Auto-generated method stub
				
			}
	        
	    };
	    return contactListener;
	}

	//---------------------------------------------
    // GAME HANDLER
    //---------------------------------------------
	
	public void							handleClock(float pSecondsElapsed) 
	{
		timeSecFloat += pSecondsElapsed;
		timeSec=(int)timeSecFloat;
		
    	if(timeSec >= 60)
    	{
    		mainCharacter.increaseSpeed();
    		addDistance +=10;
    		
    		timeSecFloat=0f;
    		timeSec=0;
    		timeMin++;
    		
    		if(timeMin == 60)
    		{
    			timeMin=0;
    			timeHou++;
    		}
    	}
	}
	
	//---------------------------------------------
    // PLATFORM HANDLER
    //---------------------------------------------
	boolean									needNewPlatform()
	{
		if(platformList.size() == 0)
			return true;
		
		return platformList.get(platformList.size()-1).get_platform().getX() < this.getChildByIndex(0).getX()+1600;
	}
	
	boolean									existPlatformToErase()
	{
		return platformList.get(0).get_platform().getX() < this.getChildByIndex(0).getX()-1600;
	}
	
	void									deletePlatform()
	{
		platformList.get(0).get_platform().detachSelf();
		platformList.get(0).get_platform().dispose();
		platformList.get(0).get_grass().detachSelf();
		platformList.get(0).get_grass().dispose();
		platformList.remove(0);
	}
	
	public void							randomPlatform()
	{
		PlatformStructure 						pfStruct;
		Body 									pfBody;
		float									xRdm = 0;
		float									yRdm = 0;
		ArrayList<Integer> 						dplatformList;
		int										numPattern;
		Random 									r;
		int 									i1 = 0;
		
		r = new Random();
		numPattern = rouletteWheel();
		dplatformList = getPattern(numPattern);
		
		for(int i=0; i < dplatformList.size();i++)
		{
			
			if(firstCall == false)
			{
				i1 = r.nextInt(210)+ 80 + addDistance;
				
				xRdm = (float)i1;
				lastPlatformPos += xRdm;
				
				i1 = r.nextInt(100);
				
				if(i1>0 && i1<50 && xRdm > 170)
					i1 = -i1;
				
				yRdm = i1;
			}
			else
				firstCall = false;
			
			if(dplatformList.get(i) == 1)
			{
				pfStruct = new PlatformStructure( lastPlatformPos
													, 200.0f + yRdm
													, resourcesManager.gameTextureRegionPlatformSquare1												
													, resourcesManager.gameTextureRegionPlatformGrassSquare1
													, vbom
													, Enum_Platform.SQUARE);
				lastPlatformPos+=690.0f;
			}
			else if(dplatformList.get(i) == 2)
			{
				pfStruct = new PlatformStructure( lastPlatformPos
													, 200.0f  + yRdm
													,resourcesManager.gameTextureRegionPlatformSquare2											
													, resourcesManager.gameTextureRegionPlatformGrassSquare1
													, vbom
													, Enum_Platform.SQUARE);
				lastPlatformPos+=690.0f;
			}
			else if(dplatformList.get(i) == 3)
			{
				pfStruct = new PlatformStructure( lastPlatformPos
													, 200.0f + yRdm
													, resourcesManager.gameTextureRegionPlatformLittleAir
													, resourcesManager.gameTextureRegionPlatformGrassLittleAir
													, vbom
													, Enum_Platform.AIR);
				lastPlatformPos+=190.0f;
			}
			else
			{
				pfStruct = new PlatformStructure( lastPlatformPos
													, 200.0f + yRdm
													,resourcesManager.gameTextureRegionPlatformPillar										
													, resourcesManager.gameTextureRegionPlatformGrassPillar
													, vbom
													, Enum_Platform.PILLAR);
				lastPlatformPos+=185.0f;
			}
			
			pfBody = PhysicsFactory.createBoxBody(mPhysicsWorld, pfStruct.get_platform(), BodyType.StaticBody, FIXTURE_DEF);
			mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(pfStruct.get_platform(), pfBody, true, true));
			this.attachChild(pfStruct.get_platform());
			this.attachChild(pfStruct.get_grass());
			platformList.add(pfStruct);
		}
	}
	
	//1 = Big01, 2 = Big01, 3 = Air, 4 = Pillar
	public int									rouletteWheel()
	{
		Random 									r;
		int 									i1;
		int										pos = 0;
		
		r = new Random();
		i1 = r.nextInt(78) + 1;
		
		while(i1 > 0)
		{
			i1 -= (13-pos);
			pos++;
		}
			
		return pos;
	}
	
	public ArrayList<Integer>					getPattern(int numPattern)
	{
		ArrayList<Integer>						patternTab;
		
		patternTab = new ArrayList<Integer>();
		
		if(numPattern == 1)
		{
			patternTab.add(1);
			patternTab.add(2);
		}
		else if(numPattern == 2)
		{
			patternTab.add(1);
			patternTab.add(4);
			patternTab.add(2);
		}
		else if(numPattern == 3)
		{
			patternTab.add(1);
			patternTab.add(3);
			patternTab.add(2);
		}
		else if(numPattern == 4)
		{
			patternTab.add(1);
			patternTab.add(3);
			patternTab.add(3);
			patternTab.add(2);
		}
		else if(numPattern == 5)
		{
			patternTab.add(1);
			patternTab.add(4);
			patternTab.add(3);
			patternTab.add(2);
		}
		else if(numPattern == 6)
		{
			patternTab.add(3);
			patternTab.add(4);
			patternTab.add(2);
		}
		else if(numPattern == 7)
		{
			patternTab.add(3);
			patternTab.add(4);
			patternTab.add(3);
			patternTab.add(1);
		}
		else if(numPattern == 8)
		{
			patternTab.add(4);
			patternTab.add(3);
			patternTab.add(3);
			patternTab.add(3);
			patternTab.add(2);
		}
		else if(numPattern == 9)
		{
			patternTab.add(3);
			patternTab.add(4);
			patternTab.add(1);
		}
		else if(numPattern == 10)
		{
			patternTab.add(4);
			patternTab.add(3);
			patternTab.add(4);
		}
		else if(numPattern == 11)
		{
			patternTab.add(3);
			patternTab.add(3);
			patternTab.add(4);
			patternTab.add(3);
			patternTab.add(3);
		}
		else if(numPattern == 12)
		{
			patternTab.add(4);
			patternTab.add(3);
			patternTab.add(4);
			patternTab.add(3);
			patternTab.add(4);
		}
		return patternTab;
	}
}
