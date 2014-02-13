package com.example.runforyourlife.scene;

import java.util.Random;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
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
		//PlatFrom
		private float						lastPlatformPos=0f;
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
		setBackground(new Background(Color.BLACK));
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
		
		//*********************TESTS
		for(int ji=0;ji<100;ji++)
			randomPlatform();
		
		this.registerUpdateHandler(new IUpdateHandler()
		{

			@Override
			public void 					onUpdate(float pSecondsElapsed) 
			{
				handleClock(pSecondsElapsed);
				
				if( timeSec%2 == 0)
				{
					
				}
			}
			@Override
			public void reset() {}
		});
		
		//MainChar
		mainCharacter = new MainCharacter(50,50, vbom, camera, mPhysicsWorld) 
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
	             mainCharacter.increaseFootContacts();
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
	
	public void							randomPlatform()
	{
		final PlatformStructure 			pfStruct;
		final Body 							pfBody;
		Random 								r;
		int 								i1;
		r = new Random();
    	i1=r.nextInt(4) +1;
    	
    	if(i1==1)
    	{
    		pfStruct = new PlatformStructure( lastPlatformPos
					, 200.0f
					, resourcesManager.gameTextureRegionPlatformSquare1												
					, resourcesManager.gameTextureRegionPlatformGrassSquare1
					, vbom
					, Enum_Platform.SQUARE);
    		lastPlatformPos+=800.0f;
    	}
    	else if(i1==2)
    	{
    		pfStruct = new PlatformStructure( lastPlatformPos
					, 100.0f
					, resourcesManager.gameTextureRegionPlatformLittleAir
					, resourcesManager.gameTextureRegionPlatformGrassLittleAir
					, vbom
					, Enum_Platform.AIR);
    		lastPlatformPos+=200.0f;
    	}
    	else if(i1==3)
    	{
    		pfStruct = new PlatformStructure( lastPlatformPos
					, 200.0f
					, resourcesManager.gameTextureRegionPlatformSquare2											
					, resourcesManager.gameTextureRegionPlatformGrassSquare1
					, vbom
					, Enum_Platform.SQUARE);
    		lastPlatformPos+=800.0f;
    	}
    	else
    	{
    		pfStruct = new PlatformStructure( lastPlatformPos
					, 200.0f
					, resourcesManager.gameTextureRegionPlatformPillar										
					, resourcesManager.gameTextureRegionPlatformGrassPillar
					, vbom
					, Enum_Platform.PILLAR);
    		lastPlatformPos+=300.0f;
    	}
		
		pfBody = PhysicsFactory.createBoxBody(mPhysicsWorld, pfStruct.get_platform(), BodyType.StaticBody, FIXTURE_DEF);
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(pfStruct.get_platform(), pfBody, true, true));
		this.attachChild(pfStruct.get_platform());
		this.attachChild(pfStruct.get_grass());		
	}
}
