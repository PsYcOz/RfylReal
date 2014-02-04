package com.example.runforyourlife.scene;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

import android.hardware.SensorManager;
import android.view.Gravity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
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
		mPhysicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, 17), false); 
		mPhysicsWorld.setContactListener(contactListener());
	    registerUpdateHandler(mPhysicsWorld);
	}
	@Override
	public void 							createScene() 
	{
		final PlatformStructure 			pfStruct;
		final Body 							pfBody;
		final PlatformStructure 			pfStruct2;
		final Body 							pfBody2;
		
		createBackground();
		createPhysics();
		
		setOnSceneTouchListener(this);
		
		//*********************TESTS
		pfStruct = new PlatformStructure( 0.0f
										, 200.0f
										, resourcesManager.gameTextureRegionPlatformSquare1
										, resourcesManager.gameTextureRegionPlatformGrassSquare1
										, vbom
										, Enum_Platform.SQUARE);
		pfBody = PhysicsFactory.createBoxBody(mPhysicsWorld, pfStruct.get_platform(), BodyType.StaticBody, FIXTURE_DEF);
		
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(pfStruct.get_platform(), pfBody, true, true));
		this.attachChild(pfStruct.get_platform());
		this.attachChild(pfStruct.get_grass());
		
		pfStruct2 = new PlatformStructure( 800.0f
				, 100.0f
				, resourcesManager.gameTextureRegionPlatformLittleAir
				, resourcesManager.gameTextureRegionPlatformGrassLittleAir
				, vbom
				, Enum_Platform.AIR);
		pfBody2 = PhysicsFactory.createBoxBody(mPhysicsWorld, pfStruct2.get_platform(), BodyType.StaticBody, FIXTURE_DEF);
		
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(pfStruct2.get_platform(), pfBody2, true, true));
		this.attachChild(pfStruct2.get_platform());
		this.attachChild(pfStruct2.get_grass());
		
		mainCharacter = new MainCharacter(50,50, vbom, camera, mPhysicsWorld) 
		{			
			@Override
			public void 					onDie() {
				// TODO Auto-generated method stub
				
			}
		};
		this.attachChild(mainCharacter);
		
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
				final Fixture x1 = contact.getFixtureA();
	            final Fixture x2 = contact.getFixtureB();

	            if (x1.getBody().getUserData() != null && x2.getBody().getUserData() != null)
	            {
	                if (x2.getBody().getUserData().equals("player"))
	                {
	                	mainCharacter.increaseFootContacts();
	                }
	            }
			}

			@Override
			public void 					endContact(Contact contact) 
			{
				final Fixture x1 = contact.getFixtureA();
	            final Fixture x2 = contact.getFixtureB();

	            if (x1.getBody().getUserData() != null && x2.getBody().getUserData() != null)
	            {
	                if (x2.getBody().getUserData().equals("player"))
	                {
	                	mainCharacter.decreaseFootContacts();
	                }
	            }
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

}
