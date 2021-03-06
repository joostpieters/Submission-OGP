package hillbillies.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import ogp.framework.util.Util;

/**
 * A class of Units that can rest, work, move and fight in a gameworld.
 * 
 * @invar  	The position of each Unit must be a valid position for any
 *         	Unit.
 *        	| isValidPosition(getPosition())
 * @invar 	The agility of each Unit must be a valid agility for any Unit. 
 * 		  	| isValidAgility(getAgility())
 * @invar	The toughness of each Unit must be a valid toughness for any Unit. 
 * 		  	| isValidToughness(getToughness())
 * @invar 	The strength of each Unit must be a valid strength for any Unit.
 * 		  	| isValidStrength(getStrength())
 * @invar 	The weight of each Unit must be a valid weight for any Unit.
 * 		  	| isValidWeight(getWeight())
 * @invar	The maximum amount of hitpoints for a Unit must be a valid amount, 
 * 			based on its weight and toughness
 * 			| getMaxHitpoints() == Math.ceil(200*getWeight()/100*getToughness()/100)
 * @invar	The maximum amount of stamina for a Unit must be a valid amount,
 * 			based on its weight and toughness
 * 			| getMaxStamina() == Math.ceil(200*getWeight()/100*getToughness()/100)
 * @invar  	The hitpoints of each Unit must be a valid hitpoints for that
 *         	Unit.
 *       	| isValidHitpoints(getHitpoints())
 * @invar  	The stamina of each Unit must be a valid stamina for that
 *         	Unit.
 *       	| isValidStamina(getStamina())
 * @invar  	The orientation of each Unit must be a valid orientation for any
 *         	Unit.
 *       	| isValidOrientation(getOrientation())
 * @invar 	The status of each Unit must be a valid status for any Unit.
 * 		  	| isValidStatus(getStatus())
 * @invar  	The name of each Unit must be a valid name for any
 *         	Unit.
 *        	| isValidName(getName())
 * @invar  	The nearTarget of each Unit must be a valid nearTarget for any
 *         	Unit.
 *       	| canHaveAsNearTarget(getNearTarget())
 * @invar  	The distantTarget of each Unit must be a valid distantTarget for any
 *         	Unit.
 *       	| canHaveAsDistantTarget(getDistantTarget())
 * @invar  	The TimeUntilRest of each Unit must be a valid TimeUntilRest for any
 *         	Unit.
 *       	| isValidTimeUntilRest(getTimeUntilRest())
 * @invar  	The ActivityTime of each Unit must be a valid ActivityTime for any
 *         	Unit.
 *       	| isValidActivityTime(getActivityTime())
 * @invar   The enemy of each Unit must be a valid enemy for any
 *         	Unit.
 *       	| canHaveAsEnemy(getEnemy())
 * @invar  	The progress of each Unit must be a valid progress for any
 *         	Unit.     
 *         	| isValidProgress(getProgress())
 * @invar	The ProgressStamina of each Unit must be a valid ProgressStamina for any Unit
 * 			| isValidProgressStamina(getProgressStamina())
 * @invar	A Unit can only be sprinting when it is moving
 * 			| (!getSprinting() || isMoving())
 * @invar	The Exp of each Unit must be a valid Exp for any Unit.
 * 			| isValidExp(getExp())
 * @invar	The FallPosition of each Unit must be a valid FallPosition for any Unit
 * 			| isValidFallPosition(getFallPosition())
 * @invar	The World of each Unit must be a valid World for that Unit.
 * 			| canHaveAsWorld(getWorld())
 * @invar	The GameObject of each Unit must be a valid GameObject for any Unit.
 * 			| canHaveAsGameObject(getGameObject())
 * @invar  	The path of a Unit must be a valid path for that
 *         	Unit.
 *       	| canHaveAsPath(getPath())
 * @invar	The Faction of each Unit must be a valid Faction for that Unit
 * 			| canHaveAsFaction(getFaction())
 * @invar	The Task of each Unit must always be a valid Task for any Unit
 * 			| isValidTask(getTask())
 * @author Sander Declercq
 * @author Bram Belpaire
 *
 */
public class Unit extends GameObject {

	/**
	 * Initialize a new Unit with given parameters
	 * 
	 * @param  position
	 *         The position for this new Unit.
	 * @param agility
	 *            The agility of the new unit
	 * @param strength
	 *            The strength of the new unit
	 * @param weight
	 *            The weight of the new unit
	 * @param name
	 *            The name of the new unit
	 * @param toughness
	 *            The toughness of the new unit
	 * @param defaultbehavior
	 * 			  Boolean reflecting whether the Unit's default behavior should be enabled
	 * 
	 * @post 	If the given agility lies between the minimum and maximum initial
	 *       	agility, the agility of this Unit equals the given agility.
	 *       	| if (agility >= MIN_INITIAL_AGILITY) && (agility <= MAX_INITIAL_AGILITY)
	 *       	| then new.getAgility() == agility
	 * @post 	If the given agility is larger than the maximum initial agility,
	 *       	the agility of this Unit equals the maximum initial agility.
	 *       	| if (agility > MAX_INITIAL_AGILITY)
	 *       	| then new.getAgility() == MAX_INITIAL_AGILITY
	 * @post 	If the given agility is smaller than the minimum initial agility,
	 *       	the agility of this Unit equals the minimum initial agility.
	 *       	| if (agility < MIN_INITIAL_AGILITY)
	 *       	| then new.getAgility() == MIN_INITIAL_AGILITY
	 *       
	 * @post 	If the given strength lies between the minimum and maximum initial
	 *       	strength, the strength of this Unit equals the given strength.
	 *       	| if (strength >= MIN_INITIAL_STRENGTH) && (strength <= MAX_INITIAL_STRENGTH)
	 *       	| then new.getStrength() == strength
	 * @post 	If the given strength is larger than the maximum initial strength,
	 *       	the strength of this Unit equals the maximum initial strength.
	 *       	| if (strength > MAX_INITIAL_STRENGTH)
	 *       	| then new.getStrength() == MAX_INITIAL_STRENGTH
	 * @post 	If the given strength is smaller than the minimum initial strength,
	 *       	the strength of this Unit equals the minimum initial strength. | if
	 *       	(strength < MIN_INITIAL_STRENGTH) | then new.getStrength() ==
	 *       	MIN_INITIAL_STRENGTH
	 *       
	 * @post 	If the given toughness lies between the minimum and maximum initial
	 *       	toughness, the toughness of this Unit equals the given toughness.
	 *       	| if (toughness >= MIN_INITIAL_TOUGHNESS) && (toughness <= MAX_INITIAL_TOUGHNESS)
	 *       	| then new.getToughness() == toughness
	 * @post 	If the given toughness is larger than the maximum initial toughness,
	 *       	the toughness of this Unit equals the maximum initial toughness.
	 *       	| if (toughness > MAX_INITIAL_TOUGHNESS)
	 *       	| then new.getToughness() == MAX_INITIAL_TOUGHNESS
	 * @post 	If the given toughness is smaller than the minimum initial toughness,
	 *       	the toughness of this Unit equals the minimum initial toughness.
	 *       	| if (toughness < MIN_INITIAL_TOUGHNESS)
	 *       	| then new.getToughness() == MIN_INITIAL_TOUGHNESS
	 *       
	 * @post 	If the given weight lies between the minimum and maximum initial
	 *       	weight, the weight of this Unit equals the given weight. 
	 *       	| if (weight >= new.getMinInitialWeight()) && (weight <= MAX_INITIAL_WEIGHT)
	 *       	| then new.getWeight() == weight
	 * @post 	If the given weight is larger than the maximum initial weight,
	 *       	the weight of this Unit equals the maximum initial weight. 
	 *       	| if (weight > MAX_INITIAL_WEIGHT)
	 *       	| then new.getWeight() == MAX_INITIAL_WEIGHT
	 * @post 	If the given weight is smaller than the minimum initial weight,
	 *       	the weight of this Unit equals the minimum initial weight. 
	 *       	| if (weight < new.getMinInitialWeight())
	 *       	| then new.getWeight() == new.getMinInitialWeight()
	 * @post 	The amount of hitpoints equals the maximum amount of hitpoints for this Unit.
	 * 			| new.getHitpoints() == new.getMaxHitpoints()
	 * @post 	The amount of stamina equals the maximum amount of stamina for this Unit.
	 *         	| new.getStamina() == new.getMaxStamina()
	 * @post 	The speed of the Unit equals the zero vector
	 * 			| new.getSpeed() == new Vector(0,0,0)
	 * @post 	The nearTarget of this new Unit equals null
	 *       	| new.getNearTarget() == null
	 * @post 	The distantTarget of this new Unit equals null
	 *       	| new.getDistantTarget == null
	 * @post 	The TimeUntilRest of this new Unit equals the standard interval between two rests.
	 *       	| new.getTimeUntilRest() == RESTING_INTERVAL
	 * @post	The orientation of this new Unit equals Pi.
	 * 			| new.getOrientation() == Math.PI
	 * @post	The new Unit is not sprinting
	 * 			| new.getSprinting() == false
	 * @post	The new Unit's progress to recovering a hitpoint or stamina point equals zero
	 * 			| new.getProgress() == 0
	 * @post	The new Unit's progress to losing a stamina point equals zero
	 * 			| new.getProgresstamina() == 0
	 * @post	The new Unit's current enemy equals null
	 * 			| new.getEnemy() == null
	 * @post	The new Unit's defaultbehaviorboolean equals the given defaultbehavior
	 * 			| new.getdefaultbehaviorboolean() == defaultbehavior
	 *    
	 * @effect 	The position of this new Unit is set to
	 *         	the given position.
	 *       	| this.setPosition(position)   
	 * @post	This Unit is not part of a World
	 * 			| new.getWorld() == null
	 * @effect 	The name of the new unit is set to the given name 
	 * 		   	| this.setName(name)
	 * @post 	The maximum amount of hitpoints for this Unit is set according to its stats. 
	 * 			| new.getmaxHitpoints() == Math.ceil(new.getToughness()*new.getWeight()/50.0)
	 * @post 	The maximum amount of stamina for this Unit is set according to its stats. 
	 * 			| new.getmaxStamina() == Math.ceil(new.getToughness()*new.getWeight()/50.0)
	 * @effect	The ActivityTime of the Unit is set to zero.
	 * 			| this.setActivityTime(0)
	 * @effect	The path of this new Unit is set to the an empty list.
	 * 			| this.setPath(new ArrayList<Vector>());
	 * 
	 */
	public Unit(Vector position, int agility, int strength, int weight, String name, int toughness, boolean defaultbehavior)
			throws IllegalArgumentException {
		super(position);
		this.setSpeed(new Vector(0,0,0));
		this.setNearTarget(null);
		this.setDistantTarget(null);

		if (agility < MIN_INITIAL_AGILITY)
			agility = MIN_INITIAL_AGILITY;
		else if (agility > MAX_INITIAL_AGILITY)
			agility = MAX_INITIAL_AGILITY;
		this.agility = agility;

		if (strength < MIN_INITIAL_STRENGTH)
			strength = MIN_INITIAL_STRENGTH;
		else if (strength > MAX_INITIAL_STRENGTH)
			strength = MAX_INITIAL_STRENGTH;
		this.strength = strength;

		if (toughness < MIN_INITIAL_TOUGHNESS)
			toughness = MIN_INITIAL_TOUGHNESS;
		else if (toughness > MAX_INITIAL_TOUGHNESS)
			toughness = MAX_INITIAL_TOUGHNESS;
		this.toughness = toughness;

		if (weight < this.getMinWeight())
			weight = this.getMinWeight();
		if (weight > MAX_INITIAL_WEIGHT)
			weight = MAX_INITIAL_WEIGHT;
		this.setWeight(weight);

		this.setName(name);
		this.setMaxHitpoints();
		this.setMaxStamina();
		this.setStamina(this.getmaxStamina());
		this.setHitpoints(this.getmaxHitpoints());
		this.setStatus(Status.IDLE);
		this.setTimeUntilRest(RESTING_INTERVAL);
		this.orientation = Math.PI/2;
		this.sprinting=false;
		this.defaultBehaviorBoolean=defaultbehavior;
		this.enemy=null;
		this.progress=0;
		this.progressstamina=0;
		this.setActivityTime(0);
		this.setFallPosition(0);
		this.setExp(0);
	}

	/**
	 * Create a new Unit with random properties in the given World.
	 * @param world
	 * 			The World in which to create this new Unit
	 * @param enableDefaultBehavior
	 * 			A boolean reflecting whether this new Unit's default behaviour should be enabled.
	 * @effect This new Unit is create with random attributes and with a position in the middle
	 * 		   of the cube at the origin of the world
	 * 		   | this(new Vector(CUBELENGTH/2, CUBELENGTH/2, CUBELENGTH/2),
	 *		   | new Random().nextInt(MAX_INITIAL_AGILITY-MIN_INITIAL_AGILITY + 1) + MIN_INITIAL_AGILITY,
	 *		   | new Random().nextInt(MAX_INITIAL_STRENGTH-MIN_INITIAL_STRENGTH + 1) + MIN_INITIAL_STRENGTH,
	 *		   | new Random().nextInt(MAX_INITIAL_WEIGHT-MIN_INITIAL_WEIGHT + 1) + MIN_INITIAL_WEIGHT,
	 *		   | "John",
	 *		   | new Random().nextInt(MAX_INITIAL_TOUGHNESS-MIN_INITIAL_TOUGHNESS + 1) + MIN_INITIAL_TOUGHNESS,
	 *		   | enableDefaultBehavior)
	 * @effect This Unit is added to the given World
	 * 		 | world.addGameObject(this)
	 */
	public Unit(World world, boolean enableDefaultBehavior){
		this(new Vector(CUBELENGTH/2, CUBELENGTH/2, CUBELENGTH/2),
				new Random().nextInt(MAX_INITIAL_AGILITY-MIN_INITIAL_AGILITY + 1) + MIN_INITIAL_AGILITY,
				new Random().nextInt(MAX_INITIAL_STRENGTH-MIN_INITIAL_STRENGTH + 1) + MIN_INITIAL_STRENGTH,
				new Random().nextInt(MAX_INITIAL_WEIGHT-MIN_INITIAL_WEIGHT + 1) + MIN_INITIAL_WEIGHT,
				"John",
				new Random().nextInt(MAX_INITIAL_TOUGHNESS-MIN_INITIAL_TOUGHNESS + 1) + MIN_INITIAL_TOUGHNESS,
				enableDefaultBehavior);
		world.addGameObject(this);
	}

	/**
	 * Return the value of the progress this unit has made to losing a single point of stamina.
	 */
	private double getProgressstamina() {
		return this.progressstamina;
	}
	
	/**
	 * Check whether the given ProgressStamina is a valid ProgressStamina for any Unit
	 * @param progressStamina
	 * 			the ProgressStamina to check
	 * @return true if the given ProgressStamina is larger than zero
	 * 		 | result == progressStamina > 0
	 */
	public static boolean isValidProgressStamina(double progressStamina){
		return progressStamina > 0;
	}

	/**
	 * Set the progressstamina of this Unit to the given progressstamina.
	 * 
	 * @param  	progressstamina
	 *         	The new progressstamina for this Unit.
	 * @post   	The progressstamina of this new Unit is equal to
	 *         	the given progressstamina.
	 *       	| new.progressstamina() == progressstamina
	 * @throws 	IllegalArgumentException
	 *         	The given progressstamina is not a valid progressstamina for any
	 *         	Unit.
	 *       	| ! isValidProgressstamina(getProgressstamina())
	 */      
	private void setProgressstamina(double progressstamina) throws IllegalArgumentException{
		if (isValidProgress(progressstamina)) {
			this.progressstamina = progressstamina;
		}
		else {
			throw new IllegalArgumentException("cannot be a negative number");
		}
	}

	/**
	 * Variable registering the progress of this Unit to losing a single point of stamina.
	 */
	private double progressstamina;

	/**
	 * returns the progress to recovering the next point of health or stamina
	 */
	private double getProgress() {
		return this.progress;
	}

	/**
	 * Check whether the given progress is a valid progress for
	 * any Unit.
	 *  
	 * @param  	progress
	 *         	The progress to check.
	 * @return 
	 * 			returns true if the progress is a non-negative number
	 *       	| result == (progress >= 0)		
	 */
	public boolean isValidProgress(double progress) {
		if (progress>=0){
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Set the progress of this Unit to the given progress.
	 * 
	 * @param  	progress
	 *         	The new progress for this Unit.
	 * @post   	The progress of this new Unit is equal to
	 *         	the given progress.
	 *       	| new.progress() == progress
	 * @throws 	IllegalArgumentException
	 *         	The given progress is not a valid progress for any
	 *         	Unit.
	 *       	| ! isValidProgress(getProgress())
	 */      
	private void setProgress(double progress) {
		if (isValidProgress(progress)) {
			this.progress = progress;
		}
		else {
			throw new IllegalArgumentException("progress cannot be a negative number");
		}
	}

	/**
	 * Variable registering the progress of this Unit to recovering a single point of hp or stamina.
	 */
	private double progress;

	/**
	 * returns the Unit this Unit is currently attacking.
	 */
	@Basic @Raw
	public Unit getEnemy(){
		return this.enemy;
	}

	/**
	 * Set the enemy of this Unit to the given enemy.
	 * 
	 * @param  	enemy
	 *         	The new enemy for this Unit.
	 * @post   	The enemy of this new Unit is equal to
	 *         	the given enemy.
	 *       	| new.getEnemy() == enemy
	 * @throws 	IllegalArgumentException
	 *         	The given enemy is not a valid enemy for any
	 *         	Unit.
	 *       	| ! isValidEnemy(getEnemy())
	 */
	@Raw
	private void setEnemy(Unit enemy)throws IllegalArgumentException{
		if (canHaveAsEnemy(enemy)){
			this.enemy=enemy;
		}
		else {
			throw new IllegalArgumentException("input is not a valid enemy");
		}
	}

	/**
	 * Check whether the given enemy is a valid enemy for
	 * this Unit.
	 *  
	 * @param  enemy
	 *         The enemy to check.
	 * @return 
	 * 			returns true if the enemy is either null or next to the unit, and returns false if the unit refers to himself
	 *       | result == (enemy == null)|| (this.isAdjacentPosition(enemy.getPosition())(enemy!=this)	
	 */
	@Raw
	private boolean canHaveAsEnemy(Unit enemy) {

		if (enemy==null) {
			return true;
		}
		if (enemy.isTerminated()) {
			return false;
		}
		if( enemy.getFaction()==this.getFaction())
			return false;
		if (enemy.isFalling()) {
			return false;
		}
		else if (this.isAdjacentPosition(enemy.getPosition())) {
			return true;
		} 
		else {
			return false;
		}
	}

	/**
	 * Variable registering this Unit's current enemy.
	 */
	private Unit enemy;

	/**
	 * Return whether the unit is sprinting or not
	 */
	@Basic @Raw
	public boolean getSprinting(){
		return this.sprinting;
	}

	/**
	 * Set the sprinting boolean of this Unit to the given sprinting boolean.
	 * 
	 * @param  	sprinting
	 *         	The new sprinting boolean for this Unit.
	 * @post   	The sprinting boolean of this new Unit is equal to
	 *         	the given sprinting boolean.
	 *       	| new.getsprinting() == sprinting
	 * @post	If the given sprinting boolean equals true, the Unit's speed Vector is multiplied by two.
	 * 			| new.getSpeed() == this.getSpeed().scalarMultiply(2)
	 * @post	If the given sprinting boolean equals false, the Unit's speed is divided by two.
	 * 			| new.getSpeed() == this.getSpeed().scalarMultiply(0.5)
	 * @throws 	IllegalStateException
	 *         	The Unit cannot start or stop sprinting because it is not moving.
	 *       	| (! this.getStatus==Status.MOVINGADJACENT) && (! this.getStatus==Status.MOVINGDISTANT)
	 *       	The unit cannot start sprinting because it is out of stamina
	 *       	| this.getStamina == 0
	 */
	public void setSprinting(boolean sprinting)throws IllegalStateException {
		if (this.getStatus() != Status.MOVINGADJACENT && this.getStatus() != Status.MOVINGDISTANT) {
			throw new IllegalStateException("The Unit cannot sprint when it is not moving");
		}
		if (sprinting){
			if (this.getStamina() == 0)
				throw new IllegalStateException("The Unit cannot sprint when it is out of stamina");
			this.setSpeed(this.getSpeed().scalarMultiply(2));
		}
		else
			this.setSpeed(this.getSpeed().scalarMultiply(0.5));
		this.sprinting = sprinting;
	}

	/**
	 * Variable registering whether the Unit is currently sprinting.
	 */
	private boolean sprinting;

	/**
	 * Check whether the given position lies in a cube
	 * that is equal or adjacent to the cube currently occupied by the Unit.
	 * @param 	position
	 * 			The position vector to be checked
	 * @return	true if the given position is effective and
	 * 			the difference between the respective floored coordinates does not exceed 1.
	 * 			| if (position != null)
	 * 			| then result == (Math.abs(this.getPosition().getCubeX() - Math.floor(position.getX())) <= 1) &&
	 * 			|					(Math.abs(this.getPosition().getCubeY() - Math.floor(position.getY())) <= 1) &&
	 * 			|					(Math.abs(this.getPosition().getCubeZ() - Math.floor(position.getZ())) <= 1)
	 */
	public boolean isAdjacentPosition(Vector position){
		if (position == null)
			return false;
		if (Math.abs(this.getPosition().getCubeX() - Math.floor(position.getX())) > 1)
			return false;
		if (Math.abs(this.getPosition().getCubeY() - Math.floor(position.getY())) > 1)
			return false;
		if (Math.abs(this.getPosition().getCubeZ() - Math.floor(position.getZ())) > 1)
			return false;
		return true;
	}

	/**
	 * Variable registering the length of a cube.
	 */
	public static final double CUBELENGTH = 1;

	/**
	 * Return the nearTarget of this Unit.
	 */
	@Basic @Raw
	public Vector getNearTarget() {
		return this.nearTarget;
	}

	/**
	 * Check whether the given nearTarget is a valid nearTarget for
	 * any Unit.
	 *  
	 * @param  	nearTarget
	 *         	The nearTarget to check.
	 * @return 	true if the given nearTarget equals null or if the given nearTarget is a position where a Unit can stand,
	 * 			lies within a cube adjacent to the Unit's current position
	 * 			and is not equal to the Unit's current position
	 *       	| result == (target == null) ||
	 *       	|				(! target.equals(this.getPosition())) &&
	 *       	|				(this.getWorld.unitCanStandAt(target)) &&
	 *       	|				(this.isAdjacentPosition(target))
	 */
	public boolean canHaveAsNearTarget(Vector target) {
		if (target == null)
			return true;
		if (target.equals(this.getPosition()))
			return false;
		return (this.getWorld().unitCanStandAt(target) &&
				this.isAdjacentPosition(target));
	}

	/**
	 * Set the nearTarget of this Unit to the given nearTarget.
	 * 
	 * @param  	target
	 *         	The new nearTarget for this Unit.
	 * @post   	The nearTarget of this new Unit is equal to
	 *         	the given nearTarget.
	 *       	| new.getNearTarget() == target
	 * @throws 	IllegalArgumentException
	 *         	The given nearTarget is not a valid nearTarget for this
	 *         	Unit.
	 *       	| ! canHaveAsNearTarget(getNearTarget())
	 */
	@Raw
	private void setNearTarget(Vector target) 
			throws IllegalArgumentException {
		if (! canHaveAsNearTarget(target))
			throw new IllegalArgumentException();
		this.nearTarget = target;
	}

	/**
	 * Variable registering the nearTarget of this Unit.
	 */
	private Vector nearTarget;

	/**
	 * Return the distantTarget of this Unit.
	 */
	@Basic @Raw
	public Vector getDistantTarget() {
		return this.distantTarget;
	}

	/**
	 * Check whether the given distantTarget is a valid distantTarget for
	 * this Unit.
	 *  
	 * @param  	distantTarget
	 *         	The distantTarget to check.
	 * @return 	true if the given distantTarget equals null or 
	 * 			the given distantTarget lies within the gameworld
	 * 			and is not equal to the Unit's current position
	 *       	| result == (target == null) ||
	 *       	|				((this.getWorld().unitCanStandAt(target)) &&
	 *       	|				(!this.getPosition().equals(target)))
	 */
	public boolean canHaveAsDistantTarget(Vector target) {
		if (target == null)
			return true;
		return (this.getWorld().unitCanStandAt(target) && !this.getPosition().equals(target));
	}

	/**
	 * Set the distantTarget of this Unit to the given distantTarget.
	 * 
	 * @param  target
	 *         The new distantTarget for this Unit.
	 * @post   The distantTarget of this new Unit is equal to
	 *         the given distantTarget.
	 *       | new.getDistantTarget() == target
	 * @throws IllegalArgumentException
	 *         The given distantTarget is not a valid distantTarget for this
	 *         Unit.
	 *       | ! canHaveAsDistantTarget(getDistantTarget())
	 */
	@Raw
	private void setDistantTarget(Vector target) 
			throws IllegalArgumentException {
		if (! canHaveAsDistantTarget(target))
			throw new IllegalArgumentException("The Unit cannot move to this position");
		this.distantTarget = target;
	}

	/**
	 * Variable registering the distantTarget of this Unit.
	 */
	private Vector distantTarget;

	/**
	 * Return the agility of this Unit.
	 */
	@Basic
	@Raw
	public int getAgility() {
		return this.agility;
	}

	/**
	 * Check whether the given agility is a valid agility for any Unit.
	 * 
	 * @param agility
	 *            The agility to check.
	 * @return | result == (agility >= MIN_AGILITY) && (agility <=
	 *         MAX_AGILITY)
	 */
	public static boolean isValidAgility(int agility) {
		return (agility >= MIN_AGILITY) && (agility <= MAX_AGILITY);
	}

	/**
	 * Variable registering the smallest legal value for a Unit's agility
	 */
	public static final int MIN_AGILITY = 1;

	/**
	 * Variable registering the largest legal value for a Unit's agility
	 */
	public static int MAX_AGILITY = 200;

	/**
	 * Variable registering the smallest legal value for a new Unit's agility
	 */
	public static final int MIN_INITIAL_AGILITY = 25;

	/**
	 * Variable registering the largest legal value for a new Unit's agility
	 */
	public static final int MAX_INITIAL_AGILITY = 100;

	/**
	 * Set the agility of the Unit to the given agility
	 * 
	 * @param 	newAgility
	 *            The new agility to be set for this Unit
	 * @post 	If the given agility lies between the minimum and maximum legal
	 *       	agility, the Unit's agility equals the given agility 
	 *       	| if (newAgility >= MIN_AGILITY) && (newAgility <= MAX_AGILITY)
	 *       	| then new.getAgility() == newAgility
	 * @post 	If the given agility is larger than the maximum legal agility, the
	 *       	Unit's agility equals the maximum legal agility 
	 *       	| if (newAgility > MAX_AGILITY)
	 *       	| then new.getAgility() == MAX_AGILITY
	 * @post 	If the given agility is smaller than the minimum legal agility, the
	 *       	Unit's agility equals the minimum legal agility 
	 *       	| if (newAgility < MIN_AGILITY)
	 *       	| then new.getAgility() == MIN_AGILITY
	 * @post	If the Unit's weight is smaller than the minimum weight determined by its new
	 * 			agility, the Unit's weight is set to its minimum weight
	 * 			| if (this.getWeight() < new.getMinWeight())
	 * 			| then new.getWeight() == new.getMinWeight()
	 */
	public void setAgility(int newAgility) {
		if (newAgility < MIN_AGILITY) {
			newAgility = MIN_AGILITY;
		} else if (newAgility > MAX_AGILITY) {
			newAgility = MAX_AGILITY;
		}
		this.agility = newAgility;
		if (this.getMinWeight() > this.getWeight())
			this.setWeight(this.getMinWeight());
	}

	/**
	 * Variable registering the agility of this Unit.
	 */
	private int agility;

	/**
	 * Return the toughness of this Unit.
	 */
	@Basic
	@Raw
	public int getToughness() {
		return this.toughness;
	}

	/**
	 * Check whether the given toughness is a valid toughness for any Unit.
	 * 
	 * @param 	toughness
	 *            The toughness to check.
	 * @return 	| result == (toughness >= MIN_TOUGHNESS) && (toughness <= MAX_TOUGHNESS)
	 */
	public static boolean isValidToughness(int toughness) {
		return (toughness >= MIN_TOUGHNESS) && (toughness <= MAX_TOUGHNESS);
	}

	/**
	 * Variable registering the smallest legal value for a Unit's toughness
	 */
	public static final int MIN_TOUGHNESS = 1;

	/**
	 * Variable registering the largest legal value for a Unit's toughness
	 */
	public static final int MAX_TOUGHNESS = 200;

	/**
	 * Variable registering the smallest legal value for a new Unit's toughness
	 */
	public static final int MIN_INITIAL_TOUGHNESS = 25;

	/**
	 * Variable registering the largest legal value for a new Unit's toughness
	 */
	public static final int MAX_INITIAL_TOUGHNESS = 100;

	/**
	 * Set the toughness of the Unit to the given toughness and
	 * update the Unit's maximum hitpoints and stamina
	 * 
	 * @param 	newToughness
	 *            The new toughness to be set for this Unit
	 * @post 	If the given toughness lies between the minimum and maximum legal
	 *       	toughness, the Unit's toughness equals the given toughness
	 *       	| if (newToughness >= MIN_TOUGHNESS) && (newToughness <= MAX_TOUGHNESS)
	 *       	| then new.getToughness() == newToughness
	 * @post 	If the given toughness is larger than the maximum legal toughness,
	 *       	the Unit's toughness equals the maximum legal toughness
	 *       	| if (newToughness > MAX_TOUGHNESS)
	 *       	| then new.getToughness() == MAX_TOUGHNESS
	 * @post 	If the given toughness is smaller than the minimum legal toughness,
	 *       	the Unit's toughness equals the minimum legal toughness
	 *       	| if (newToughness < MIN_TOUGHNESS)
	 *       	| then new.getToughness() == MIN_TOUGHNESS
	 * @effect	The Unit's maximum hitpoints are updated according to its new toughness
	 * 			| this.setMaxHitpoints()
	 * @post	If the Unit's old hitpoints exceed its new maximum amount of hitpoints,
	 * 			its amount of hitpoints equals its maximum amount of hitpoints
	 * 			| if (this.getHitpoints() > new.getmaxHitpoints())
	 * 			| then new.getHitpoints() == new.getmaxHitpoints()
	 * @effect	The Unit's maximum stamina is updated according to its new toughness
	 * 			| this.setMaxStamina()
	 * @post	If the Unit's old stamina exceeds its new maximum amount of stamina,
	 * 			its amount of stamina equals its maximum amount of stamina
	 * 			| if (this.getStamina() > new.getmaxStamina())
	 * 			| then new.getStamina() == new.getmaxStamina()
	 */
	public void setToughness(int newToughness) {
		if (newToughness < MIN_TOUGHNESS) {
			newToughness = MIN_TOUGHNESS;
		} else if (newToughness > MAX_TOUGHNESS) {
			newToughness = MAX_TOUGHNESS;
		}
		this.toughness = newToughness;
		this.setMaxHitpoints();
		this.setMaxStamina();
		if (this.getHitpoints() > this.getmaxHitpoints())
			this.setHitpoints(this.getmaxHitpoints());
		if  (this.getStamina() > this.getmaxStamina())
			this.setStamina(this.getmaxStamina());
	}

	/**
	 * Variable registering the toughness of this Unit.
	 */
	private int toughness;

	/**
	 * Return the strength of this Unit.
	 */
	@Basic
	@Raw
	public int getStrength() {
		return this.strength;
	}

	/**
	 * Check whether the given strength is a valid strength for any Unit.
	 * 
	 * @param 	strength
	 *            The strength to check.
	 * @return 	| result == (strength >= MIN_STRENGTH) && (strength <= MAX_STRENGTH)
	 */
	public static boolean isValidStrength(int strength) {
		return (strength >= MIN_STRENGTH) && (strength <= MAX_STRENGTH);
	}

	/**
	 * Variable registering the smallest legal value for a Unit's strength
	 */
	public static final int MIN_STRENGTH = 1;

	/**
	 * Variable registering the largest legal value for a Unit's strength
	 */
	public static final int MAX_STRENGTH = 200;

	/**
	 * Variable registering the smallest legal value for a new Unit's strength
	 */
	public static final int MIN_INITIAL_STRENGTH = 25;

	/**
	 * Variable registering the largest legal value for a new Unit's strength
	 */
	public static final int MAX_INITIAL_STRENGTH = 100;

	/**
	 * Set the strength of the Unit to the given strength
	 * 
	 * @param	newStrength
	 *            The new strength to be set for this Unit
	 * @post 	If the given strength lies between the minimum and maximum legal
	 *       	strength, the Unit's strength equals the given strength
	 *       	| if (newStrength >= MIN_STRENGTH) && (newStrength <= MAX_STRENGTH)
	 *       	| then new.getStrength() == newStrength
	 * @post 	If the given strength is larger than the maximum legal strength,
	 *       	the Unit's strength equals the maximum legal strength
	 *       	| if (newStrength > MAX_STRENGTH)
	 *       	| then new.getStrength() == MAX_STRENGTH
	 * @post 	If the given strength is smaller than the minimum legal strength,
	 *       	the Unit's strength equals the minimum legal strength
	 *       	| if (newStrength < MIN_STRENGTH)
	 *       	| then new.getStrength() == MIN_STRENGTH
	 * @post	If the Unit's weight is smaller than the minimum weight determined by its new
	 * 			strength, the Unit's weight is set to its minimum weight
	 * 			| if (this.getWeight() < new.getMinWeight())
	 * 			| then new.getWeight() == new.getMinWeight()
	 */
	public void setStrength(int newStrength) {
		if (newStrength < MIN_STRENGTH) {
			newStrength = MIN_STRENGTH;
		} else if (newStrength > MAX_STRENGTH) {
			newStrength = MAX_STRENGTH;
		}
		this.strength = newStrength;
		if (this.getMinWeight() > this.getWeight())
			this.setWeight(this.getMinWeight());
	}

	/**
	 * Variable registering the strength of this Unit.
	 */
	private int strength;

	/**
	 *  
	 * @return the weigh of the unit and the object it may be carrying
	 */

	public int getTotalWeight(){
		return this.getWeight()+this.weightGameObject();
	}
	/**
	 * Check whether the given weight is a valid weight for this Unit.
	 * 
	 * @param 	weight
	 *            The weight to check.
	 * @return 	true if the given weight lies between the minimum weight for this Unit and
	 * 			the maximum weight for any Unit.
	 * 			| result == (weight >= this.getMinWeight()) && (weight <= MAX_WEIGHT)
	 */
	@Override
	public boolean canHaveAsWeight(int weight) {
		return (weight >= this.getMinWeight()) && (weight <= MAX_WEIGHT);
	}

	//	protected void setWeight(int weight){
	//		if (this.canHaveAsWeight(weight))
	//			super.setWeight(weight);
	//		else if (weight < this.getMinWeight())
	//			super.setWeight(getMinWeight());
	//		else
	//			super.setWeight(200);
	//		
	//	}

	/**
	 * Returns the smallest legal value for this Unit's weight
	 * 
	 * @return 	The smallest possible weight for this Unit.
	 * 			| result == Math.ceil(this.getAgility()/2.0 + this.getStrength()/2.0)
	 */
	@Basic
	public int getMinWeight() {
		double temp = this.getAgility() / 2.0 + this.getStrength() / 2.0;
		return (int) Math.ceil(temp);
	}

	/**
	 * Variable registering the largest legal value for a Unit's weight
	 */
	public static final int MAX_WEIGHT = 200;

	/**
	 * Variable registering the smallest legal value for a new Unit's weight
	 */
	public static final int MIN_INITIAL_WEIGHT = 25;

	/**
	 * Variable registering the largest legal value for a new Unit's weight
	 */
	public static final int MAX_INITIAL_WEIGHT = 100;

	/**
	 * Set the weight of the Unit to the given weight
	 * 
	 * @param 	newWeight
	 *            The new weight to be set for this Unit
	 * @post 	If the given weight lies between the minimum and maximum legal
	 *       	weight, the Unit's weight equals the given weight
	 *       	| if (newWeight >= this.getMinWeight()) && (newWeight <= MAX_WEIGHT)
	 *       	| then new.getWeight() == newWeight
	 * @post 	If the given weight is larger than the maximum legal weight, the
	 *       	Unit's weight equals the maximum legal weight
	 *       	| if (newWeight > MAX_WEIGHT)
	 *       	| then new.getWeight() == MAX_WEIGHT
	 * @post 	If the given weight is smaller than the minimum legal weight, the
	 *       	Unit's weight equals the minimum legal weight
	 *       	| if (newWeight < this.getMinWeight())
	 *       	| then new.getWeight() == this.getMinWeight()
	 * @effect	The Unit's maximum hitpoints are updated according to its new weight
	 * 			| this.setMaxHitpoints()
	 * @post	If the Unit's old hitpoints exceed its new maximum amount of hitpoints,
	 * 			its amount of hitpoints equals its maximum amount of hitpoints
	 * 			| if (this.getHitpoints() > new.getmaxHitpoints())
	 * 			| then new.getHitpoints() == new.getmaxHitpoints()
	 * @effect	The Unit's maximum stamina is updated according to its new weight
	 * 			| this.setMaxStamina()
	 * @post	If the Unit's old stamina exceeds its new maximum amount of stamina,
	 * 			its amount of stamina equals its maximum amount of stamina
	 * 			| if (this.getStamina() > new.getmaxStamina())
	 * 			| then new.getStamina() == new.getmaxStamina()
	 */
	@Override
	public void setWeight(int newWeight) {
		if (newWeight < this.getMinWeight()) {
			newWeight = this.getMinWeight();
		} else if (newWeight > MAX_WEIGHT) {
			newWeight = MAX_WEIGHT;
		}
		super.setWeight(newWeight);
		this.setMaxHitpoints();
		this.setMaxStamina();
		if (this.getHitpoints() > this.getmaxHitpoints())
			this.setHitpoints(this.getmaxHitpoints());
		if  (this.getStamina() > this.getmaxStamina())
			this.setStamina(this.getmaxStamina());
	}

	/**
	 * Return the name of this Unit.
	 */
	@Basic @Raw
	public String getName() {
		return this.name;
	}

	/**
	 * Check whether the given name is a valid name for
	 * any Unit.
	 *  
	 * @param  	name
	 *         	The name to check.
	 * @return 	true if the name has more than one character and
	 * 			all characters in the name are letters, quotes or spaces
	 *       	| result == (Character.isUpperCase(name.charAt(0))) &&
	 *       	|			 (name.length() >= 2) &&
	 *       	| 			 for each char in name.toCharArray(): (Character.isUpperCase(char)) ||
	 *       	|									(Character.isLowerCase(char) ||
	 *       	|									(char == ' ') ||
	 *       	|									(char == '"') ||
	 *       	|									(char == '\'')
	 *       
	 */
	private static boolean isValidName(String name) {
		if (!(name.length() >= 2) || !(Character.isUpperCase(name.charAt(0)))) 
			return false;
		else {
			for (char ch: name.toCharArray()) {
				if (!(Character.isUpperCase(ch)) && !(ch == ' ') && !(ch == '"')
						&& !(Character.isLowerCase(ch)) && !(ch == '\''))
					return false;
			}
		}
		return true;
	}

	/**
	 * Set the name of this Unit to the given name.
	 * 
	 * @param  	name
	 *         	The new name for this Unit.
	 * @post   	The name of this new Unit is equal to
	 *         	the given name.
	 *       	| new.getName() == name
	 * @throws 	IllegalArgumentException
	 *         	The given name is not a valid name for any
	 *         	Unit.
	 *       	| ! isValidName(getName())
	 */
	@Raw
	public void setName(String name) 
			throws IllegalArgumentException {
		if (! isValidName(name))
			throw new IllegalArgumentException();
		this.name = name;
	}

	/**
	 * Variable registering the name of this Unit.
	 */
	private String name;

	/**
	 * Return the maximum amount of stamina points for this Unit.
	 */
	@Basic @Raw
	public int getmaxHitpoints() {
		return this.maxHitpoints;
	}

	/**
	 * Set the maximum amount of hitpoints for this Unit
	 * @post	The maximum amount of hitpoints has been set according to the Unit's stats.
	 * 			| new.getmaxHitpoints() == Math.ceil(this.getToughness()*this.getWeight()/50.0)
	 */
	@Raw
	private void setMaxHitpoints() {
		double temp = (double) this.getToughness() * (double) this.getWeight() / 50.0;
		this.maxHitpoints = (int) Math.ceil(temp);
	}

	/**
	 * Variable registering the maximum amount of hitpoints for this Unit.
	 */
	private int maxHitpoints;

	/**
	 * Return the hitpoints of this Unit.
	 */
	@Basic @Raw
	public int getHitpoints() {
		return this.hitpoints;
	}

	/**
	 * Check whether the given hitpoints are valid hitpoints for
	 * this Unit.
	 *  
	 * @param  	hitpoints
	 *         	The hitpoints to check.
	 * @return 	true if the hitpoints are 
	 * 			smaller than or equal to the maximum amount of hitpoints for this Unit.
	 *       	| (hitpoints <= this.getmaxHitpoints())
	 */
	public static boolean isValidHitpoints(int hitpoints, int maxhitpoints) {
		return (hitpoints <= maxhitpoints) && (hitpoints >= 0);
	}

	/**
	 * Set the hitpoints of this Unit to the given hitpoints.
	 * 
	 * @param  	hitpoints
	 *         	The new hitpoints for this Unit.
	 * @pre    	The given hitpoints must be valid hitpoints for this
	 *         	Unit.
	 *       	| isValidHitpoints(hitpoints)
	 * @post   	If the given hitpoints are greater than zero, 
	 * 			the hitpoints of this Unit are equal to the given hitpoints.
	 * 			Otherwise, the Unit is terminated.
	 * 			| if (hitpoints > 0)
	 *       	| then new.getHitpoints() == hitpoints
	 *       	| else new.isTerminated()
	 */
	@Raw
	private void setHitpoints(int hitpoints) {
		assert isValidHitpoints(hitpoints, this.getmaxHitpoints());
		this.hitpoints = hitpoints;
		if (hitpoints == 0)
			this.terminate();
	}

	/**
	 * Variable registering the hitpoints of this Unit.
	 */
	private int hitpoints;

	/**
	 * Return the maximum amount of stamina points for this Unit.
	 */
	@Basic @Raw
	public int getmaxStamina() {
		return this.maxStamina;
	}

	/**
	 * Set the maximum amount of stamina points for this Unit
	 * @post	The maximum amount of stamina has been set according to the Unit's stats.
	 * 			| new.getmaxStamina() == Math.ceil(this.getToughness()*this.getWeight()/50.0)
	 */
	@Raw
	private void setMaxStamina() {
		double temp = (double) this.getToughness() * (double) this.getWeight() / 50.0;
		this.maxStamina = (int) Math.ceil(temp);
	}

	/**
	 * Variable registering the maximum amount of stamina points for this Unit.
	 */
	private int maxStamina;

	/**
	 * Return the stamina of this Unit.
	 */
	@Basic @Raw
	public int getStamina() {
		return this.stamina;
	}

	/**
	 * Check whether the given stamina points are valid stamina points for
	 * this Unit.
	 *  
	 * @param  	stamina
	 *         	The stamina points to check.
	 * @return 	true if the stamina points are greater than or equal to zero and
	 * 			smaller than or equal to the maximum amount of stamina points for this Unit.
	 *       	| result == (stamina >= 0) && (stamina <= this.getmaxStamina())
	 */
	public static boolean isValidStamina(int stamina, int maxStamina) {
		return (stamina >= 0) && (stamina <= maxStamina);
	}

	/**
	 * Set the stamina points of this Unit to the given stamina points.
	 * 
	 * @param  	stamina
	 *         	The new stamina points for this Unit.
	 * @pre    	The given stamina points must be valid stamina points for this
	 *         	Unit.
	 *       	| isValidStamina(stamina)
	 * @post   	The stamina points of this Unit are equal to the given
	 *         	stamina points.
	 *       	| new.getStamina() == stamina
	 */
	@Raw
	private void setStamina(int stamina) {
		assert isValidStamina(stamina, this.getmaxStamina());
		this.stamina = stamina;
	}

	/**
	 * Variable registering the stamina of this Unit.
	 */
	private int stamina;

	/**
	 * Return the orientation of this Unit.
	 */
	@Basic @Raw
	public double getOrientation() {
		return this.orientation;
	}

	/**
	 * Check whether the given orientation is a valid orientation for
	 * any Unit.
	 *  
	 * @param  	orientation
	 *         	The orientation to check.
	 * @return 	true if the given orientation is larger than or equal to zero
	 * 			and smaller than 2*Pi
	 *       	| result == (orientation >= 0) && (orientation <= 2*Math.PI)
	 */
	public static boolean isValidOrientation(double orientation) {
		return (orientation >= 0) && (orientation <= 2*Math.PI);
	}

	/**
	 * Set the orientation of this Unit to the given orientation.
	 * 
	 * @param  	orientation
	 *         	The new orientation for this Unit.
	 * @post   	If the given orientation is a valid orientation for any Unit,
	 *         	the orientation of this new Unit is equal to the given
	 *         	orientation.
	 *       	| if (isValidOrientation(orientation))
	 *       	|   then new.getOrientation() == orientation
	 * @post	If the given orientation is larger than or equal to 2*Math.PI,
	 * 			the orientation of the Unit equals the angle between zero and 2*Math.PI
	 * 			corresponding to the given orientation.
	 * 			| if (orientation >= 2*Math.PI)
	 * 			| then new.getOrientation() == orientation % (2*Math.PI)
	 * @post	If the given orientation is smaller than zero,
	 * 			the orientation equals the angle between zero and 2*Math.PI
	 * 			corresponding to the given orientation
	 * 			| if (orientation < 0)
	 * 			| then new.getOrientation() == 2*Math.PI-(Math.abs(orientation) % (2*Math.PI))
	 */
	@Raw
	private void setOrientation(double orientation) {
		if (isValidOrientation(orientation))
			this.orientation = orientation;
		else if (orientation < 0){
			while (orientation <= -2*Math.PI)
				orientation += 2*Math.PI;
			this.orientation = 2*Math.PI + orientation;
		}
		else if (orientation >= 2*Math.PI){
			while (orientation >= 2*Math.PI)
				orientation -= 2*Math.PI;
			this.orientation = orientation;
		}
	}

	/**
	 * Variable registering the orientation of this Unit.
	 */
	private double orientation;

	/**
	 * Check whether this Unit can be interrupted by an activity represented by the given Status
	 * @param status
	 * 			The Status representing the potential interrupting activity
	 * @return 
	 * 			| if (this.getStatus() == Status.FALLING)
	 * 			| then result == false
	 * 			| if (this.getStatus() == Status.ATTACKING)
	 * 			| then result == (status == Status.FALLING)
	 * 			| if (this.getStatus() == Status.RESTING)
	 * 			| then result == (this.hasRestedEnough() || status == Status.ATTACKING || status == Status.FALLING)
	 * 			| if (this.getStatus() == Status.WORKING || this.getStatus() == Status.IDLE)
	 * 			| then result == true
	 * 			| if (this.getStatus() == Status.MOVINGADJACENT || this.getStatus() == Status.MOVINGDISTANT)
	 * 			| then result == (status == Status.FALLING || status == Status.ATTACKING)
	 */
	public boolean canBeInterruptedBy(Status status) {
		Status oldStatus = this.getStatus();
		if (oldStatus==Status.FALLING) {
			return false;
		}
		if (oldStatus==Status.ATTACKING) {
			return status == Status.FALLING;
		}
		if (oldStatus==Status.RESTING) {
			if (hasRestedEnough()) {
				return true;
			}
			if (status==Status.ATTACKING||status==Status.FALLING) {
				return true;
			}
			else {
				return false;
			}
		}
		if (oldStatus==Status.WORKING) {
			return true;
		}
		if (oldStatus==Status.IDLE) {
			return true;
		}
		if (oldStatus==Status.MOVINGADJACENT) {
			if (status==Status.FALLING || status == Status.ATTACKING) {
				return true;
			} else
				return false;
		}

		if (oldStatus==Status.MOVINGDISTANT) {
			if (status==Status.FALLING || status == Status.ATTACKING) {
				return true;
			} else
				return false;
		}
		return true;
	}

	/**
	 * checks whether the Unit is moving or not
	 * @return 
	 *		|result==((this.getStatus()==Status.MOVINGADJACENT)||(this.getStatus() == Status.MOVINGDISTANT))
	 */
	public boolean isMoving(){
		if (this.getStatus()==Status.MOVINGADJACENT || this.getStatus() == Status.MOVINGDISTANT){
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * checks whether the Unit is IDLE or not
	 * @return 
	 *		|result==(this.getStatus()==Status.IDLE)
	 */
	public boolean isIdle(){
		if (this.getStatus()==Status.IDLE){
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * checks whether the unit is resting or not
	 * @return 
	 *		|result==(this.getStatus()==Status.RESTING)
	 */
	public boolean isResting(){
		if (this.getStatus()==Status.RESTING){
			return true;
		}
		else {
			return false;
		}

	}
	/**
	 * checks whether the unit is attacking or not
	 *@return 
	 *		|result==(this.getStatus()==Status.ATTACKING)
	 */
	public boolean isAttacking() {
		if (this.getStatus()==Status.ATTACKING){
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * checks whether the unit is working or not
	 * @return true if this Unit's status is Status.WORKING
	 * 			| result == (this.getStatus()==Status.WORKING)
	 */
	public boolean isWorking() {
		if (this.getStatus()==Status.WORKING){
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Check whether the given status is a valid status for any Unit.
	 * 
	 * @param status
	 *            The status to check.
	 * @return | result == (status == Status.WORKING) || (status ==
	 *         | Status.RESTING) || (status == Status.MOVINGADJACENT) ||
	 *         | (status == Status.IDLE) || (status == Status.MOVINGDISTANT)
	 */
	@Override
	protected boolean isValidStatus(Status status) {
		return (status == Status.WORKING) || (status == Status.RESTING) || (status == Status.MOVINGADJACENT)
				|| (status == Status.MOVINGDISTANT) || (status == Status.IDLE)||(status==Status.ATTACKING)||(status==Status.FALLING);
	}

	/**
	 * Set the status of this Unit to the given status.
	 * 
	 * @param status
	 *            The new status for this Unit.
	 * @post 	The status of this new Unit is equal to the given status. 
	 * 			| new.getStatus() == status
	 * @effect	If the given status equals Status.IDLE, this Unit's nearTarget, distantTarget and enemy
	 * 			are set to null, and its speed is set to the zero vector
	 * 			| if (status == Status.IDLE)
	 * 			| then this.setNearTarget(null)
	 * 			| 		this.setDistantTarget(null)
	 * 			|		this.setSpeed(new Vector(0,0,0)
	 * @effect	If the given status equals Status.IDLE and the Unit is currently moving,
	 * 			its sprinting is disabled
	 * 			| if (this.isMoving())
	 * 			| then this.setSprinting(false)
	 * @throws IllegalArgumentException
	 *             The given status is not a valid status for any Unit. 
	 *             | !isValidStatus(getStatus())
	 */
	@Raw @Override
	protected void setStatus(Status status) throws IllegalArgumentException {
		if (!isValidStatus(status))
			throw new IllegalArgumentException("This is an invalid status for a Unit");
		if (status == Status.IDLE){
			if (this.isMoving())
				this.setSprinting(false);
			this.setNearTarget(null);
			this.setDistantTarget(null);
			this.setSpeed(new Vector(0,0,0));
			this.setEnemy(null);
		}
		super.setStatus(status);
	}

	/**
	 * Return the TimeUntilRest of this Unit.
	 */
	@Basic @Raw
	private double getTimeUntilRest() {
		return this.timeUntilRest;
	}

	/**
	 * Check whether the given TimeUntilRest is a valid TimeUntilRest for
	 * any Unit.
	 *  
	 * @param  TimeUntilRest
	 *         The TimeUntilRest to check.
	 * @return 
	 *       | result == (time > 0) && (time <= RESTING_INTERVAL)
	 */
	private static boolean isValidTimeUntilRest(double time) {
		return (time >= 0) && (time <= RESTING_INTERVAL);
	}

	/**
	 * Set the TimeUntilRest of this Unit to the given TimeUntilRest.
	 * 
	 * @param  	time
	 *         	The new TimeUntilRest for this Unit.
	 * @post   	The TimeUntilRest of this new Unit is equal to
	 *         	the given TimeUntilRest.
	 *       	| new.getTimeUntilRest() == time
	 * @throws 	IllegalArgumentException
	 *         	The given TimeUntilRest is not a valid TimeUntilRest for any
	 *         	Unit.
	 *       	| ! isValidTimeUntilRest(getTimeUntilRest())
	 */
	@Raw
	private void setTimeUntilRest(double time) 
			throws IllegalArgumentException {
		if (! isValidTimeUntilRest(time))
			throw new IllegalArgumentException();
		this.timeUntilRest = time;
	}

	/**
	 * Variable registering the TimeUntilRest of this Unit.
	 */
	private double timeUntilRest;

	/**
	 * Variable registering the interval between two obligatory rests for any Unit.
	 */
	public static final double RESTING_INTERVAL = 180;

	/**
	 * Return the ActivityTime of this Unit.
	 */
	@Basic @Raw
	private double getActivityTime() {
		return this.ActivityTime;
	}

	/**
	 * Check whether the given ActivityTime is a valid ActivityTime for
	 * any Unit.
	 *  
	 * @param  ActivityTime
	 *         The ActivityTime to check.
	 * @return 
	 *       | result == (ActivityTime >= 0)
	 */
	private static boolean isValidActivityTime(double ActivityTime) {
		return (ActivityTime >= 0);
	}

	/**
	 * Set the ActivityTime of this Unit to the given ActivityTime.
	 * 
	 * @param  ActivityTime
	 *         The new ActivityTime for this Unit.
	 * @post   The ActivityTime of this new Unit is equal to
	 *         the given ActivityTime.
	 *       | new.getActivityTime() == ActivityTime
	 * @throws IllegalArgumentException
	 *         The given ActivityTime is not a valid ActivityTime for any
	 *         Unit.
	 *       | ! isValidActivityTime(getActivityTime())
	 */
	@Raw
	private void setActivityTime(double ActivityTime) 
			throws IllegalArgumentException {
		if (! isValidActivityTime(ActivityTime))
			throw new IllegalArgumentException();
		this.ActivityTime = ActivityTime;
	}

	/**
	 * Variable registering the ActivityTime of this Unit.
	 */
	private double ActivityTime;

	/**
	 * initiates the unit to attack another Unit
	 * @param other
	 * 		The other Unit to be attacked by this Unit.
	 * @effect	If this Unit is moving to a distant target, its speed is set to zero
	 * 			| if (this.getStatus() == Status.MOVINGDISTANT)
	 * 			| then this.setSpeed(new Vector(0,0,0))
	 * @effect	If this Unit is moving to a distant target and sprinting, its sprinting is disabled
	 * 			| if (this.getStatus() == Status.MOVINGDISTANT && this.getSprinting())
	 * 			| then this.setSprinting(false)
	 * @effect  updates the positions of both units, if the unit was not already attacking
	 * 			it will set the activitytime to 0
	 * 			|this.updatePosition(other)
	 * 			|other.updatePosition(this)
	 * 			|this.setStatus(Status.ATTACKING)
	 * 			|this.setEnemy(other)
	 * 			|if (!this.isAttacking()) 
	 * 			|then this.setActivityTime(0)
	 * 			|if(!other.isAttacking())
	 * 			|then other.setStatus(Status.DEFENDING)
	 * 
	 * @throws IllegalArgumentException
	 * 			The other Unit cannot be attacked by this Unit.
	 * 			|if ((!isValidEnemy(other))
	 * @throws IllegalStateException
	 * 			This Unit is currently moving to a neighboring cube
	 * 			| (this.getStatus() == Status.MOVINGADJACENT)
	 */
	public void startAttack(Unit other) throws IllegalArgumentException,IllegalStateException{
		if (other == this)
			throw new IllegalArgumentException("A Unit cannot attack itself!");
		if (!canHaveAsEnemy(other))
			throw new IllegalArgumentException("This Unit is not a valid target");
		if (this.getStatus() == Status.MOVINGADJACENT)
			throw new IllegalStateException("Movement to a neighbouring cube cannot be interrupted");
		if (this.getStatus() == Status.MOVINGDISTANT){
			this.setSpeed(new Vector(0,0,0));
			if (this.getSprinting())
				this.setSprinting(false);
		}
		if (!this.isAttacking()) {
			this.setActivityTime(0);
		}
		this.setStatus(Status.ATTACKING);
		this.setEnemy(other);
	}

	/**
	 * prepares the attack and when the attack is finished, instantiates an immediate response of the defender
	 * @param time
	 * 			The amount of time the attack is advanced 
	 * @effect	If the Unit had a distantTarget before executing the attack, it resumes
	 * 			movement toward this target after executing the attack
	 * 			| if (this.getDistantTarget() != null)
	 * 			| then this.moveTo((int)Math.floor(this.getDistantTarget().getX()),
	 * 			|					(int)Math.floor(this.getDistantTarget().getY()),
	 * 			| 					(int)Math.floor(this.getDistantTarget().getZ()))
	 * @effect
	 * 			updates the activitytime, if it goes over 1 it will trigger the immediate defense response from the defender
	 * 			|new.getActivityTime==getActivityTime()+time
	 * 			|if (new.getActivityTime()>=1)
	 * 			|thenother.defend(this)
	 *			|this.setStatus(Status.IDLE)
	 *			|this.setEnemy(null)
	 * @effect	If the attack is executed and the Unit was executing a Task and that Task is finished,
	 * 			that Task is terminated
	 * 		  |	if (this.getActivityTime()>=1 && this.hasTask() && this.getTask().isFinished())
	 * 		  | then this.getTask().terminate()
	 */
	private void attack(Unit other, double time) {

		if (!canHaveAsEnemy(other)) {
			setActivityTime(0);
			setEnemy(null);
			setStatus(Status.IDLE);
			if (this.hasTask() && this.getTask().isFinished())
				this.getTask().terminate();
			return;
		}

		this.updatePosition(other);
		this.setActivityTime(this.getActivityTime()+time);
		if (this.getActivityTime()>=1){
			other.defend(this);
			other.updatePosition(this);
			if (this.hasTask() && this.getTask().isFinished())
				this.getTask().terminate();
			if (this.getDistantTarget() != null){
				Vector target = this.getDistantTarget();
				try {
					this.moveTo((int)Math.floor(target.getX()) , (int)Math.floor(target.getY()), (int)Math.floor(target.getZ()));
				} catch (IllegalArgumentException | PathfindingException e) {
					this.setDistantTarget(null);
					this.setStatus(Status.IDLE);
				}
			} else
				this.setStatus(Status.IDLE);
			this.setEnemy(null);
		}
	}

	/**
	 * the unit tries to dodge, parry and if that fails, takes damage
	 * @post
	 * 		this unit will  set its Status to IDLE 
	 * 		|  new.getStatus() == Status.IDLE
	 * @effect
	 * 		if the unit was moving before it got attacked it will continue moving
	 * 		|if (this.getDistantTarget() != null)
	 * 		| then Vector new.target = this.getDistantTarget();
	 *		| this.moveTo((int)Math.floor(target.getX()) , (int)Math.floor(target.getY()), (int)Math.floor(target.getZ()))
	 * @effect
	 * 		if the unit dodges it will take no damage but it will have to dodge to another position
	 * 		|if (Math.random() <= 0.20 * this.getAgility() / attacker.getAgility())
	 * 		|then this.dodge
	 * 		|new.getHitpoints() == this.getHitpoints()
	 * @post if the unit parries it takes no damage
	 * 		| if (Math.random() <= 0.25 * (this.getStrength() + this.getAgility())
	 * 		| then new.getHitpoints() == this.getHitpoints()
	 * @post if the unit fails to parry or dodge it takes damage
	 * 		|new.gethipoints()==this.getHitpoints()-attacker.getStrength()/10
	 * 		|if(attacker.getStrength()/10==0)
	 * 		|then new.gethipoints()==this.getHitpoints()-1
	 *@effect if the unit dodges or parries it will get 20 exp thus 2 levelups
	 *		|this.setExp(this.getexp+20)
	 *		|this.getOldStrength+this.getOldtoughnes+this.getOldAgility+this.getnewStrength+2
	 *		|>=this.getnewtoughnes+this.getnewAgility>=this.getOldStrength+this.getOldtoughnes+this.getOldAgility
	 * 		
	 */
	private void defend(Unit attacker){
		if (Math.random() <= 0.20 * this.getAgility() / attacker.getAgility()) {
			this.dodge();
			this.setExp(this.getExp()+20);
			if (hasTask()) {
				this.task.removeFromUnit();
				this.setStatus(Status.IDLE);
			}

		} else if (Math.random() <= 0.25 * (this.getStrength() + this.getAgility())
				/ (attacker.getStrength() + attacker.getAgility())) {
			this.setExp(this.getExp()+20);
		} else {
			if (attacker.getStrength()/10==0) {
				this.setHitpoints(this.getHitpoints()-1);
			}
			if (this.getHitpoints()-attacker.getStrength()/10>0) {
				setHitpoints(this.getHitpoints()-attacker.getStrength()/10);
			}
			else {
				this.setHitpoints(0);
			}
			attacker.setExp(attacker.getExp()+20);
		}

		this.setStatus(Status.IDLE);


		if (this.getDistantTarget() != null){
			Vector target = this.getDistantTarget();
			try {
				this.moveTo((int)Math.floor(target.getX()) , (int)Math.floor(target.getY()), (int)Math.floor(target.getZ()));
			} catch (IllegalArgumentException | PathfindingException e) {
				this.setDistantTarget(null);
				this.setStatus(Status.IDLE);
			}
		}			
	}
	
	/**
	 * sets this Unit's position to a valid position
	 * @effect
	 * 			|if(isValidPosition(selectDodgePosition(new.position))
	 * 			|then setPosition(selectDodgePosition(new.position))
	 */
	private void dodge() {
		List<Integer> dataList = new ArrayList<Integer>();
		for (int i = 0; i < 9; i++) {
			dataList.add(i);
		}
		Collections.shuffle(dataList);
		int j;
		Random random=new Random();
		for (int i = 0; i < dataList.size(); i++) {
			j = dataList.get(i);
			float randomnumber = random.nextFloat();
			Vector position = selectDodgePosition(j,randomnumber);
			if (isValidPosition(position)) {
				setPosition(position);
				i=dataList.size();
			}
		}
	}
	
	/**
	 * @param int i
	 * 
	 * @throws IllegalArgumentException
	 * 		if the integer provided is not part of the interval [0,8], then this 
	 * 		method shall throw an IllegalArgumentException
	 * 		|if(i !=(0 up to and including 8))
	 * 
	 * @ return
	 * 		returns the vector of a Position with the x and y position not being 
	 * 		further than 1 of the Units exact position on the same level of the z-plane
	 * 
	 * 		as the unit is currently standing
	 * 		|if (i==0 up to and including 8)
	 * 		|result==new Vector.newposition
	 */
	private Vector selectDodgePosition(int randomnumber,float distance)throws IllegalArgumentException{


		if (randomnumber==0) {
			return new Vector(this.getPosition().getX()-distance, this.getPosition().getY(), this.getPosition().getZ());

		}
		if (randomnumber==1) {
			return new Vector(this.getPosition().getX()-distance, this.getPosition().getY()-distance, this.getPosition().getZ());

		}
		if (randomnumber==2) {
			return new Vector(this.getPosition().getX()-distance, this.getPosition().getY()+distance, this.getPosition().getZ());

		}
		if (randomnumber==3) {
			return new Vector(this.getPosition().getX()+distance, this.getPosition().getY()-distance, this.getPosition().getZ());

		}
		if (randomnumber==4) {
			return new Vector(this.getPosition().getX()+distance, this.getPosition().getY()+distance, this.getPosition().getZ());

		}
		if (randomnumber==5) {
			return new Vector(this.getPosition().getX()+distance, this.getPosition().getY(), this.getPosition().getZ());

		}
		if (randomnumber==6) {
			return new Vector(this.getPosition().getX(), this.getPosition().getY()+distance, this.getPosition().getZ());

		}
		if (randomnumber==7) {
			return new Vector(this.getPosition().getX(), this.getPosition().getY()-distance, this.getPosition().getZ());

		}
		if(randomnumber==8) {
			return new Vector(this.getPosition().getCubeX()+distance, this.getPosition().getCubeY()+distance, this.getPosition().getCubeZ());
		}
		else {
			throw new IllegalArgumentException("cannot use this integer");
		}
	}

	/**
	 * the Unit updates his position against the unit he is fighting
	 * @param other
	 * 			The Unit to turn this Unit towards.
	 * @post This Unit's orientation is updated so that it is facing the given Unit 
	 * 		|new.getorientation==Math.atan2(other.getPosition().getY()-this.getPosition().getY(), other.getPosition().getX()-this.getPosition().getX())
	 */
	private void updatePosition(Unit other){
		this.orientation=Math.atan2(other.getPosition().getY()-this.getPosition().getY(), other.getPosition().getX()-this.getPosition().getX());
	}

	/**
	 * Return the base speed of this Unit, depending on its stats. 
	 * @return	The Unit's base speed
	 * 			| result == 1.5*(this.getStrength() + this.getAgility())/(200.0*this.getTotalWeight()/100.0)
	 */
	private double calculateBaseSpeed() {
		double baseSpeed = 1.5 * (this.getStrength() + this.getAgility()) / (200 * this.getTotalWeight() / 100);
		return baseSpeed;
	}

	/**
	 * tells the unit to start working
	 * @effect 
	 * 		|this.settingInitialResttimeOk()
	 * 		|this.setStatus(Status.WORKING)
	 * 		|this.setActivityTime(calculatingWorkTime())
	 * @throws IllegalStateException
	 * 		The Unit is currently conducting an activity that cannot be interrupted by a work order.
	 * 		| ! this.canBeInterruptedBy(Status.WORKING)
	 * @throws IllegalArgumentException
	 * 			The given position is not an adjacent position to this Unit
	 * 		  | !isAdjacentPosition(new Vector(x, y, z))
	 */
	public void WorkAt(int x, int y, int z)throws IllegalStateException, IllegalArgumentException {
		if (! this.canBeInterruptedBy(Status.WORKING))
			throw new IllegalStateException("This Unit cannot start working at this time");
		if (!isAdjacentPosition(new Vector(x, y, z))) {
			throw new IllegalArgumentException("out of reach");
		}
		if (this.hasGameObject() && this.getWorld().isSolidGround(x, y, z))
			throw new IllegalArgumentException("A Unit cannot put down an object at a solid cube");
		if (this.hasGameObject() || this.getWorld().containsGameObject(x, y, z) || this.getWorld().isSolidGround(x, y, z)){
			this.settingInitialResttimeOk();
			this.setWorkposition(x, y, z);
			this.setToWork();
		} else {
			throw new IllegalArgumentException("No work to be done here");
		}

	}
	
	/**
	 * Initiate a work order
	 * @post	This Unit is working
	 * 		  | new.isWorking()
	 * @effect	This Unit's ActivityTime is set to the time it needs to complete a work order
	 * 		  | this.setActivityTime(calculatingWorkTime()
	 * @effect	If this Unit's WorkPosition is not equal to this Unit's position,
	 * 			its orientation is updated accordingly
	 * 		  | if (!this.getPosition().getCubePosition().equals(this.getWorkposition().getCubePosition()))
	 * 		  |	then this.setOrientation(Math.atan2(this.getWorkposition().getY()+World.CUBELENGTH/2-this.getPosition().getY(),this.getWorkposition().getX()+World.CUBELENGTH/2-this.getPosition().getX()))
	 */
	public void setToWork(){
		this.setStatus(Status.WORKING);
		this.setActivityTime(calculatingWorkTime());
		if (!this.getPosition().getCubePosition().equals(this.getWorkposition().getCubePosition()))
			this.setOrientation(Math.atan2(this.getWorkposition().getY()+World.CUBELENGTH/2-this.getPosition().getY(),this.getWorkposition().getX()+World.CUBELENGTH/2-this.getPosition().getX()));
	}
	
	/**
	 * updates the time the unit has to spend working
	 * @post
	 *		| if (this.getActivityTime() - time <= 0)
	 *		|then new.getActivityTime() == 0, new.getStatus() == Status.IDLE
	 *		|else  new.getActivityTime() == this.getActivityTime()-time
	 * @effect If this Unit's work is completed and 
	 * 		   this Unit was carrying a GameObject, that object is dropped at the Unit's Workposition
	 * 		   and the Unit gains 10 experience points.
	 * 			| if (this.getActivityTime() - time <= 0 && this.hasGameObject())
	 * 			| then this.setActivityTime(0) && this.setStatus(Status.IDLE) &&
	 * 			|		this.dopObjectAt(this.getWorkposition()) &&
	 * 			| 		this.setExp(this.getExp() + 10)
	 * @effect If this Unit's work is completed and
	 * 		   the Unit's Workposition is a Workshop and both a Log and a Boulder are present at that position,
	 * 		   this Unit gains one point of Toughness and one point of weight and a Boulder and a Log at the Unit's
	 * 		   Workposition are terminated and the Unit gains 10 experience points.
	 *			| if (this.getActivityTime() - time <= 0 && this.getWorld().getCubeType(this.getWorkposition()) == 3
	 *			|			&& this.getWorld().containsLogAndBoulder(this.getWorkposition()))
	 *			| then this.setActivityTime(0) && this.setStatus(Status.IDLE) &&
	 *			|		this.setWeight(this.getWeight() + 1) &&
	 *			|		this.setToughness(this.getToughness + 1) &&
	 *			|		for some Log in this.getWorld.getGameObjectsAt(this.getWorkposition()):
	 *			|					Log.terminate() && 
	 *			|		for some Boulder in this.getWorld.getGameObjectsAt(this.getWorkposition()):
	 *			|					Boulder.terminate() &&
	 *			| 		this.setExp(this.getExp() + 10)
	 * @effect If this Unit's work is completed and a GameObject is present at the Unit's Workposition,
	 *		   the Unit picks up that GameObject and gains 10 experience points.
	 *			| if (this.getActivityTime() - time <= 0 && this.getWorld().containsGameObject(this.getWorkposition()))
	 *			| then this.setActivityTime(0) && this.setStatus(Status.IDLE) &&
	 *			|		this.pickUpObject(this.getWorkposition()) &&
	 *			|		this.setExp(this.getExp()+10)
	 * @effect If this Unit's work is completed and its Workposition is a solid cube, that cube collapses
	 *		   and the Unit gains 10 experience points.
	 *			| if (this.getActivityTime() - time <= 0 && this.getWorld().isSolidGround(this.getWorkposition()))
	 *			| then this.setActivityTime(0) && this.setStatus(Status.IDLE) &&
	 *			|		this.getWorld().caveIn(this.getWorkposition().getCubeX(), this.getWorkposition().getCubeY(), this.getWorkposition().getCubeZ())
	 *			|		&& this.setExp(this.getExp() + 10)
	 * @effect	If this Unit's work is complete and this Unit was executing a Task and that Task is finished,
	 * 			this Unit's task is terminated
	 * 			| if (this.getActivityTime() - time <= 0 && this.hasTask() && this.getTask().isFinished())
	 * 			| then this.getTask().terminate()
	 */
	private void advanceWork(double time){
		if (this.getActivityTime() - time <= 0){
			this.setActivityTime(0);
			this.setStatus(Status.IDLE);
			if (this.hasTask() && this.getTask().isFinished())
				this.getTask().terminate();
			if (this.hasGameObject()){
				this.dropObjectAt(this.getWorkposition());
				this.setExp(this.getExp()+10);
				return;
			}
			if (this.getWorld().getCubeType(this.getWorkposition()) == 3
					&& this.getWorld().containsLogAndBoulder(this.getWorkposition())){
				this.setWeight(this.getWeight() + 1);
				this.setToughness(this.getToughness() + 1);
				boolean foundBoulder = false;
				boolean foundLog = false;
				Set<GameObject> objects = this.getWorld().getInanimateObjectsAt(this.getWorkposition());
				for (GameObject object:objects){
					if (object instanceof Boulder && !foundBoulder){
						object.terminate();
						foundBoulder = true;
					} else if (object instanceof Log && !foundLog){
						object.terminate();
						foundLog = true;
					}
					if (foundLog && foundBoulder){
						this.setExp(this.getExp()+10);
						return;
					}
				}
			}
			if (this.getWorld().containsInanimateObject(this.getWorkposition())){
				this.pickUpObject(this.getWorkposition());
				this.setExp(this.getExp()+10);
				return;
			}
			if (this.getWorld().isSolidGround(this.getWorkposition())){
				this.getWorld().caveIn(this.getWorkposition().getCubeX(), this.getWorkposition().getCubeY(), this.getWorkposition().getCubeZ());
				this.setExp(this.getExp()+10);
				return;
			}
		} else
			this.setActivityTime(this.getActivityTime()-time);
	}

	/**
	 * Pick up an object at the given position. The Unit always picks up a Boulder when one is present,
	 * otherwise it picks up a Log.
	 * @param position
	 * 			The position from which to pick up an object.
	 * @effect picks up a gameobject, first checking for possible boulders, then for possible logs
	 * 		|if(cube(x,y,z)contains boulder)
	 * 		|	SetGameObject(boulderobject)
	 * 		|else
	 * 		|	SetGameObject(LogObject)
	 */
	private void pickUpObject(Vector position){
		for (GameObject object : this.getWorld().getInanimateObjectsAt(position)) {
			if (object instanceof Boulder){
				setGameObject((InanimateObject) object);
				return;
			}
		}
		for (GameObject object : this.getWorld().getGameObjects()) {
			if (object instanceof Log){
				setGameObject((InanimateObject) object);
				return;
			}
		}
	}

	/**
	 * Make the Unit drop its object at the given position
	 * @effect drops the object the unit is carrying in the Units world
	 * 			| this.setGameObject(null)
	 * 			| this.getGameObject().addToWorld(getWorld())
	 * 			| oldObject.setPosition(position)
	 */
	private void dropObjectAt(Vector position) {
		position = position.getCubePosition().add(new Vector(CUBELENGTH/2,CUBELENGTH/2,CUBELENGTH/2));
		GameObject oldObject = this.getGameObject();
		this.setGameObject(null);
		this.getWorld().addGameObject(oldObject);
		oldObject.setPosition(position);
	}
	
	/**
	 *
	 * @return  position where workat will take place
	 */
	@Basic @Raw
	public Vector getWorkposition() {
		return this.workposition;
	}
	
	/**
	 * Check whether the given position is a valid WorkPosition for this Unit
	 * @param position
	 * 			The position to check
	 * @return true if the given position is inside this Unit's game World
	 * 		 | result == this.getWorld().isInsideWorld(position)
	 */
	@Basic @Raw
	public boolean canHaveAsWorkPosition(Vector position){
		return this.getWorld().isInsideWorld(position);
	}

	/**
	 * Set the Unit's Workposition to the given position
	 * @param x
	 * 			The x-coordinate of the Unit's new Workposition
	 * @param y
	 * 			The y-coordinate of the Unit's new Workposition
	 * @param z
	 * 			The z-coordinate of the Unit's new Workposition
	 * @post The Unit's Workposition is a Vector with the given coordinates
	 * 		 as its coordinates
	 * 			| new.getWorkposition() == new Vector(x,y,z)
	 * @throws	IllegalArgumentException
	 * 			The given position is not inside this Unit's World
	 * 			| !this.canHaveAsWorkPosition(new Vector(x,y,z))
	 */
	@Raw
	private void setWorkposition(int x, int y, int z) throws IllegalArgumentException {
		if (!this.canHaveAsWorkPosition(new Vector(x,y,z)))
			throw new IllegalArgumentException();
		this.workposition = new Vector(x, y, z);
	}

	/**
	 *
	 * variable for the position where workat will take place
	 */
	private Vector workposition;

	/**
	 * Initiate movement to a neighbouring cube.
	 * @param dx
	 * 			The targeted displacement in the x-direction
	 * @param dy
	 * 			The targeted displacement in the y-direction
	 * @param dz
	 * 			The targeted displacement in the z-direction
	 * @post	If the Unit is not yet moving, 
	 * 			The Unit's nearTarget is set to the given target
	 * 			| if (this.getStatus() != Status.MOVINGDISTANT && this.getStatus() != Status.MOVINGADJACENT)
	 * 			| then new.getNearTarget() == new Vector(this.getPosition().getCubeX() + dx + CUBELENGTH/2,
	 *			|							this.getPosition().getCubeY() + dy + CUBELENGTH/2,
	 *			|							this.getPosition().getCubeZ() + dz + CUBELENGTH/2)
	 * @post	If the Unit is not yet moving, the Unit's status
	 * 			is set to Status.MOVINGADJACENT
	 * 			| if (this.getStatus() != Status.MOVINGDISTANT && this.getStatus() != Status.MOVINGADJACENT)
	 * 			| then new.getStatus() == Status.MOVINGADJACENT
	 * @effect	If the Unit is not yet moving,
	 * 			the Unit's speed is set according to the targeted position.
	 * 			| if (this.getStatus() != Status.MOVINGDISTANT && this.getStatus() != Status.MOVINGADJACENT)
	 * 			| then this.setupSpeed()
	 * @effect	If the Unit was resting and had recovered at least one hitpoint before this order was issued,
	 * 			the time until its next obligatory rest is reset.
	 * 			| if (this.isresting() && this.hasRestedEnough())
	 * 			| then this.settingInitialResttimeOk()
	 * @throws IllegalArgumentException
	 * 			The target position is not a valid nearTarget
	 * 			| (!this.canHaveAsNearTarget(new Vector(this.getPosition().getCubeX() + dx + CUBELENGTH/2,
	 *			| this.getPosition().getCubeY() + dy + CUBELENGTH/2,
	 *			| this.getPosition().getCubeZ() + dz + CUBELENGTH/2)))
	 * @throws IllegalStateException
	 * 			The Unit is currently conducting an activity that cannot be interrupted by movement
	 * 			| (!this.canBeInterruptedBy(Status.MOVINGADJACENT))
	 */
	public void moveToAdjacent(int dx, int dy, int dz) throws IllegalArgumentException, IllegalStateException{
		if (! this.canBeInterruptedBy(Status.MOVINGADJACENT))
			throw new IllegalStateException("The Unit cannot start moving at this time");
		Vector target = new Vector(this.getPosition().getCubeX() + dx + CUBELENGTH/2,
				this.getPosition().getCubeY() + dy + CUBELENGTH/2,
				this.getPosition().getCubeZ() + dz + CUBELENGTH/2);
		if ( ! this.canHaveAsNearTarget(target))
			throw new IllegalArgumentException("The Unit cannot move to this position");
		if (this.isResting())
			this.settingInitialResttimeOk();
		if (this.getStatus() == Status.MOVINGADJACENT || this.getStatus() == Status.MOVINGDISTANT)
			return;
		this.setStatus(Status.MOVINGADJACENT);
		this.setNearTarget(target);
		this.setupSpeed();
	}

	/**
	 * Sets the Unit's speed and orientation according to the Unit's current nearTarget
	 * 
	 * @post	If the Unit is currently one cubelength above its nearTarget and is not sprinting,
	 * 			the magnitude of its speed is set to 1.2 times its basespeed
	 * 			| if (Util.fuzzyEquals(this.getPosition.getZ() - this.getNearTarget().getZ(),CUBELENGTH)) && (! this.getSprinting())
	 * 			| then new.getSpeed().norm() == 1.2*this.calculateBaseSpeed()
	 * @post	If the Unit is currently one cubelength below its nearTarget and is not sprinting,
	 * 			the magnitude of its speed is set to 0.5 times its basespeed
	 * 			| if (Util.fuzzyEquals(this.getPosition.getZ() - this.getNearTarget().getZ(),-CUBELENGTH)) && (! this.getSprinting())
	 * 			| then new.getSpeed().norm() == 0.5*this.calculateBaseSpeed()
	 * @post	If the Unit is not one cubelength above or below its nearTarget and is not sprinting,
	 * 			the magnitude of its speed is set to its basespeed
	 * 			| if (!Util.fuzzyEquals(this.getPosition.getZ() - this.getNearTarget().getZ(),CUBELENGTH)) &&
	 * 			|		(!Util.fuzzyEquals(this.getPosition.getZ() - this.getNearTarget().getZ(),-CUBELENGTH)) &&
	 * 			|		(! this.getSprinting())
	 * 			| then new.getSpeed().norm() == this.calculateBaseSpeed()
	 * @post	If the Unit is currently one cubelength above its nearTarget and is sprinting,
	 * 			the magnitude of its speed is set to 2.4 times its basespeed
	 * 			| if (Util.fuzzyEquals(this.getPosition.getZ() - this.getNearTarget().getZ(),CUBELENGTH)) && (this.getSprinting())
	 * 			| then new.getSpeed().norm() == 2.4*this.calculateBaseSpeed()
	 * @post	If the Unit is currently one cubelength below its nearTarget and is sprinting,
	 * 			the magnitude of its speed is set to its basespeed
	 * 			| if (Util.fuzzyEquals(this.getPosition.getZ() - this.getNearTarget().getZ(),-CUBELENGTH)) && (this.getSprinting())
	 * 			| then new.getSpeed().norm() == this.calculateBaseSpeed()
	 * @post	If the Unit is not one cubelength above or below its nearTarget and is sprinting,
	 * 			the magnitude of its speed is set to twice its basespeed
	 * 			| if (!Util.fuzzyEquals(this.getPosition.getZ() - this.getNearTarget().getZ(),CUBELENGTH)) &&
	 * 			|		(!Util.fuzzyEquals(this.getPosition.getZ() - this.getNearTarget().getZ(),-CUBELENGTH)) &&
	 * 			|		(this.getSprinting())
	 * 			| then new.getSpeed().norm() == 2*this.calculateBaseSpeed()
	 * @post	The direction of the Unit's speed is set to point in the direction of its nearTarget
	 * 			| new.getSpeed().normalize().getX() == 
	 * 			| this.getNearTarget().getX() - this.getPosition().getX()/Math.sqrt(Math.pow((this.getPosition().getX() - target.getX()), 2) 
	 *			| + Math.pow((this.getPosition().getY() - target.getY()), 2)
	 *			| + Math.pow((this.getPosition().getZ() - target.getZ()), 2))
	 * 			| new.getSpeed().normalize().getY() == 
	 * 			| this.getNearTarget().getY() - this.getPosition().getY()/Math.sqrt(Math.pow((this.getPosition().getX() - target.getX()), 2) 
	 *			| + Math.pow((this.getPosition().getY() - target.getY()), 2)
	 *			| + Math.pow((this.getPosition().getZ() - target.getZ()), 2))
	 * 			| new.getSpeed().normalize().getZ() == 
	 * 			| this.getNearTarget().getZ() - this.getPosition().getZ()/Math.sqrt(Math.pow((this.getPosition().getX() - target.getX()), 2) 
	 *			| + Math.pow((this.getPosition().getY() - target.getY()), 2)
	 *			| + Math.pow((this.getPosition().getZ() - target.getZ()), 2))
	 * @post	| The Unit's orientation is set so it faces in the direction of its speed
	 * 			| new.getOrientation() == Math.atan2(new.getSpeed().getY(),new.getSpeed().getX())
	 */
	private void setupSpeed() {
		Vector target = this.getNearTarget();
		double speed = this.calculateBaseSpeed();
		if (Util.fuzzyEquals(this.getPosition().getZ() - target.getZ(), -Unit.CUBELENGTH))
			speed = 0.5*speed;
		else if (Util.fuzzyEquals(this.getPosition().getZ() - target.getZ(), Unit.CUBELENGTH))
			speed = 1.2*speed;
		if (this.getSprinting())
			speed = 2*speed;
		double d = Math.sqrt(Math.pow((this.getPosition().getX() - target.getX()), 2) 
				+ Math.pow((this.getPosition().getY() - target.getY()), 2)
				+ Math.pow((this.getPosition().getZ() - target.getZ()), 2));
		double speed_x = speed*(target.getX() - this.getPosition().getX())/d;
		double speed_y = speed*(target.getY() - this.getPosition().getY())/d;
		double speed_z = speed*(target.getZ() - this.getPosition().getZ())/d;
		this.setSpeed(new Vector(speed_x, speed_y, speed_z));
		this.setOrientation(Math.atan2(speed_y, speed_x));
	}

	/**
	 * Initiate movement towards a distant position on the map
	 * @param cubeX
	 * 			The x-coordinate of the target cube
	 * @param cubeY
	 * 			The y-coordinate of the target cube
	 * @param cubeZ
	 * 			The z-coordinate of the target cube
	 * @post	The Unit's distantTarget is set to the center of the given cube.
	 * 			| new.getDistantTarget() == new Vector(cubeX + CUBELENGTH/2, cubeY + CUBELENGTH/2, cubeZ + CUBELENGTH/2)
	 * @post	The Unit's status is set to Status.MOVINGDISTANT
	 * 			| new.getStatus() == Status.MOVINGDISTANT
	 * @effect	A path to the given cube is calculated and set
	 * 			| this.findPath(cubeX, cubeY, cubeZ)
	 * @effect	If the Unit is not moving, movement to the next cube on the path of to its distantTarget is initiated
	 * 			| if (!this.ismoving())
	 * 			| then this.moveToNextCube()
	 * @effect	If the Unit was resting and had recovered at least one hitpoint before this order was issued,
	 * 			the time until its next obligatory rest is reset.
	 * 			| if (this.isresting() && this.hasRestedEnough())
	 * 			| then this.settingInitialResttimeOk()
	 * @throws 	IllegalArgumentException
	 * 			The Unit cannot stand at the given position
	 * 			| (!this.getWorld().unitCanStandAt(new Vector(cubeX + CUBELENGTH/2, cubeY + CUBELENGTH/2, cubeZ + CUBELENGTH/2)))
	 * @throws	IllegalStateException
	 * 			The Unit is conducting an activity that cannot be interrupted by movement
	 * 			| (!this.canBeInterruptedBy(Status.MOVINGDISTANT))
	 * @throws	PathfindingException
	 * 			There is no path from the Unit's current position to the given position
	 */
	public void moveTo(int cubeX, int cubeY, int cubeZ)
			throws IllegalArgumentException, IllegalStateException, PathfindingException{
		if (!this.canBeInterruptedBy(Status.MOVINGDISTANT))
			throw new IllegalStateException("This Unit cannot start moving at this time");
		Vector target = new Vector(cubeX + CUBELENGTH/2, cubeY + CUBELENGTH/2, cubeZ + CUBELENGTH/2);
		if (! this.getWorld().unitCanStandAt(target))
			throw new IllegalArgumentException();
		if (this.getPosition().getCubePosition().equals(new Vector(cubeX,cubeY,cubeZ)))
			return;
		if (this.isResting())
			this.settingInitialResttimeOk();
		if (this.isMoving()){
			Vector oldDistantTarget = this.getDistantTarget();
			try{
				this.setDistantTarget(target);
				this.findPath(cubeX, cubeY, cubeZ);
				this.setStatus(Status.MOVINGDISTANT);
			} catch (PathfindingException e){
				this.setDistantTarget(oldDistantTarget);
			}
		} else {
			try {
				this.setDistantTarget(target);
				this.findPath(cubeX, cubeY, cubeZ);
				this.setStatus(Status.MOVINGDISTANT);
				this.moveToNextCube();
			} catch (PathfindingException e) {
				this.setDistantTarget(null);
				throw e;
			}
		}
	}

	/**
	 * Initiate movement to the given target cube.
	 * @param target
	 * 			The target cube to start moving to.
	 * @effect	If possible, movement to the given cube is initiated.
	 * 			| this.moveTo(target.getCubeX(), target.getCubeY(), target.getCubeZ())
	 */
	private void moveTo(Vector target)
			throws IllegalArgumentException,IllegalStateException,PathfindingException{
		this.moveTo(target.getCubeX(), target.getCubeY(), target.getCubeZ());
	}

	/**
	 * Make the Unit move to the next cube on its path to its distantTarget
	 * @effect	Movement to the adjacent cube nearest to the distantTarget is initiated
	 * 			| this.setNearTarget(this.getPath().remove(0))
	 * 			| this.setupSpeed()
	 */
	private void moveToNextCube(){
		this.setNearTarget(this.getPath().remove(0));
		this.setupSpeed();
	}

	/**
	 * Calculate the time needed to complete a task
	 * @return	The time needed to complete a task. 
	 * 			| result == 500/this.getStrength
	 */
	private double calculatingWorkTime() {
		return 500 / (double) this.getStrength();
	}

	/**
	 * Advances the gametime for this Unit by the given time
	 * @param time
	 * 			The time to advance the gametime with.
	 * @effect	if the Unit is not falling and is in a position where it should start to fall, it will start to fall
	 * @effect 	if the Unit is falling, its position is updated
	 * @effect	The time until the Unit's next obligatory rest is reduced by the given time.
	 * @effect	If the Unit is currently idle and its default behavior is enabled,
	 * 			it chooses a random activity to conduct.
	 * @effect	If the Unit is currently moving, its position is updated according to its speed and the given time
	 * 			and its stamina is reduced if it is sprinting.
	 * @effect	If the Unit is currently resting, its hitpoints or stamina points are restored according
	 * 			to its current amount of hitpoints and stamina and the given time.
	 * @effect	If the Unit is currently working, the time until its work is finished is updated.
	 * @effect	If the Unit is currently attacking another Unit, its attack is advanced by the given time.
	 * @throws IllegalArgumentException
	 * 			The given time is an illegal time.
	 * 			| (time < 0) || (time > 0.2)
	 */
	@Override
	public void advanceTime(double time) throws IllegalArgumentException{
		if (!isTerminated()) {
			if (time<0||time>0.2)
				throw new IllegalArgumentException();
			if (this.getTimeUntilRest() - time <= 0)
				this.setTimeUntilRest(0);
			else
				this.setTimeUntilRest(this.getTimeUntilRest()-time);
			if (this.hasToFall() && !isFalling()) {
				this.startFall();
			}
			this.hasToRest();
			Status status = this.getStatus();


			if (status==Status.FALLING) {
				this.fall(time);
				return;
			}
			if (this.hasTask() && this.getStatus() == Status.IDLE){
				this.getTask().advanceTask(time);
				return;
			}
			if (status == Status.IDLE){
				if (getdefaultbehaviorboolean()){
					defaultbehavior();
				}

			}


			if (status == Status.MOVINGADJACENT || status == Status.MOVINGDISTANT)
				this.move(time);
			if (status == Status.RESTING)
				this.restoreHPST(time);
			if (status == Status.WORKING)
				this.advanceWork(time);
			if (status == Status.ATTACKING)
				this.attack(getEnemy(), time);
		}

	}

	/**
	 * Update the Unit's position according to its speed and reduce its stamina if it is sprinting.
	 * @param time
	 * 			The time used to calculate the new position and stamina for this Unit.
	 * @post	If the Unit is not arriving at or surpassing its target position,
	 * 			its speed times the given time is added to its position
	 * @post	If the Unit is arriving at or surpassing its target position,
	 * 			its position is set to its target position, its target position is set to null
	 * 			and its status is set to idle.
	 * @post	If the Unit is currently sprinting, its stamina points are reduced.
	 * @post	If the Unit runs out of stamina, its sprinting is disabled
	 * @post	If this Unit arrives at its target position, was executing a Task
	 * 			and that Task is finished, its Task is terminated
	 */
	private void move(double time) {
		if (this.getSprinting()){
			this.setProgressstamina(this.getProgressstamina()+time*10);
			if (this.getProgressstamina()>=1){
				this.setProgressstamina(this.getProgressstamina()-1);
				this.setStamina(this.getStamina()-1);
			}
			if (this.getStamina() == 0)
				this.setSprinting(false);
		}
		Vector displacement = this.getSpeed().scalarMultiply(time);
		Vector new_pos = this.getPosition().add(displacement);
		if ((this.getNearTarget().liesBetween(this.getPosition(), new_pos)) ||
				(this.getNearTarget().equals(new_pos))){
			this.setExp(this.getExp() + 1);
			this.setPosition(this.getNearTarget());
			if (this.getPath().size() > 0){
				if (!isValidPath(this.getPath())){
					try {
						this.findPath(this.getDistantTarget());
						this.moveToNextCube();
					} catch (IllegalArgumentException|PathfindingException e){
						this.setDistantTarget(null);
						this.setSprinting(false);
						this.setStatus(Status.IDLE);
					}
				} else {
					this.moveToNextCube();
				}
			} else{
				this.setStatus(Status.IDLE);
				if (this.hasTask() && this.getTask().isFinished())
					this.getTask().terminate();					
			}				
		} else
			this.setPosition(new_pos);
	}

	/**
	 * Calculate the time a Unit needs to recover one hitpoint.
	 * @return	The time a Unit needs to recover one hitpoint
	 * 			| result == 40.0/this.getToughness
	 */
	private double calculateMinRestTime(){
		return 40.0/this.getToughness();
	}

	/**
	 * method to initiate resting
	 * @post	If the Unit is not yet resting, the Unit's status
	 * 			is set to Status.Resting
	 * 			| if (this.getStatus() != Status.Resting)
	 * 			| then new.getStatus() == Status.Resting
	 * @effect	If the Unit was moving to a distant target, its speed is set to the zero vector
	 * 			| if (this.getStatus() == Status.MOVINGDISTANT)
	 * 			| then this.setSpeed(new Vector(0,0,0))
	 * @effect	If the Unit was moving to a distant target and sprinting, its sprinting is disabled
	 * 			| if (this.getStatus() == Status.MOVINGDISTANT && this.getSprinting())
	 * 			| then this.setSprinting(false)
	 * @post	If the Unit is not yet resting,
	 * 			the Unit's progress and activityTime are set to 0
	 * 			| if (this.getStatus() != Status.resting)
	 * 			| then new.getProgress=0, new.getActivityTime=0
	 * @throws 	IllegalStateException
	 * 			The Unit is currently conducting an activity that cannot be interrupted by resting
	 * 			| (!this.canBeInterruptedBy(Status.RESTING))
	 */
	public void resting() throws IllegalStateException {
		if (! this.canBeInterruptedBy(Status.RESTING))
			throw new IllegalStateException("This Unit cannot start resting at this moment");
		if (this.getStatus() == Status.MOVINGDISTANT){
			this.setPath(new ArrayList<Vector>());
			this.setSpeed(new Vector(0,0,0));
			if (this.getSprinting())
				this.setSprinting(false);
		}
		if (!isResting()) {
			this.setStatus(Status.RESTING);
			this.setProgress(0);
			this.setActivityTime(0);
		}

	}
	/**
	 * Restores hitpoints if possible, otherwise staminapoints, or stops after the initialresttime
	 * has passed
	 * @post	If the Unit did not have his maximumhitpoints and progress did not go above 1
	 * 			| if (this.getHitpoints() != this.getmaxhitpoints)
	 * 			| then (new.getProgress() == this.getprogress)
	 * 			|new.getActivityTime==this.getActivityTime()+time
	 * @post	If the Unit did not have his maximumhitpoints and progress did  go above 1
	 * 			| if (this.getHitpoints() != this.getmaxhitpoints())
	 * 			| then (new.getProgress() == this.getprogress()-1)
	 * 			|new.getHitpoints()==this.getHitpoints+1
	 * 			|if(new.getHitpoints()==this.getMaxhitpoints())
	 * 			|then new.getprogress()==this.getProgress()*2
	 * 			|new.getActivityTime==this.getActivityTime()+time
	 * @post	If the Unit did not have maximum stamina but did have the maximum amount of
	 * 			 hitpoints, and progress did not go above 1
	 * 			| if (this.getStamina() != this.getmaxStamina)
	 * 			| then (new.getProgress() == this.getprogress)
	 * 			|new.getActivityTime==this.getActivityTime()+time
	 * @post	
	 * 				If the Unit did not have his maximumStamina and progress did  go above 1
	 * 			| if (this.getStamina() != this.getmaxStamina())
	 * 			| then (new.getProgress() == this.getprogress()-1)
	 * 			|new.getStamina()==this.getStamina()+1
	 * 			|new.getActivityTime==this.getActivityTime()+time
	 * @effect	If unit has the maximum amount of stamina and the maximum amount of hitpoints 
	 * 				and the minRestTime has already passed, it will either set its status to Idle
	 * 			or continue moving to a target it was going to
	 * 			|if (hasRestedEnough())
	 * 			|new.getStatus==Status.IDLE
	 * 			|new.getActivityTime==this.getActivityTime()+time
	 * 			|then if (this.getDistantTarget() != null)
	 * 			| 		then this.moveTo((int)Math.floor(target.getX()) , (int)Math.floor(target.getY()), (int)Math.floor(target.getZ()));
	 * 
	 */
	private void restoreHPST(double time) {
		if (this.getHitpoints()<this.getmaxHitpoints()){
			this.setProgress(progress+calculateHPRestore(time));
			if (this.getProgress()>1) {
				this.setProgress(this.getProgress()-1);
				this.setHitpoints(this.getHitpoints()+1);
				if (this.getHitpoints()==getmaxHitpoints()) {
					this.setProgress(progress*2);
				}
			}
		}
		else if (this.getStamina()<this.getmaxStamina()) {
			this.setProgress(progress+calculateSTRestore(time));
			if (this.getProgress()>1) {
				this.setProgress(this.getProgress()-1);
				this.setStamina((this.getStamina()+1));
			}
		}
		else  {
			if (hasRestedEnough()) {
				this.setStatus(Status.IDLE);
				if (this.getDistantTarget() != null){
					try {
						Vector target = this.getDistantTarget();
						this.moveTo((int)Math.floor(target.getX()) , (int)Math.floor(target.getY()), (int)Math.floor(target.getZ()));
					} catch (IllegalArgumentException | PathfindingException e) {
						this.setDistantTarget(null);
						this.setStatus(Status.IDLE);
					}
				}
				this.setTimeUntilRest(RESTING_INTERVAL);
			}

		}
		setActivityTime(this.getActivityTime()+time);
	} 

	/**
	 * sets the timeuntilRest To the resting interval if possible
	 * @post
	 * 		|if (this.isresting())
	 * 		|new.getTimeUntilRest==RESTING_INTERVAL
	 */
	private void settingInitialResttimeOk() {
		if (this.isResting()){
			this.setTimeUntilRest(RESTING_INTERVAL);
		}
	}

	/**
	 * returns a boolean to make sure its minRestTime has already passed if the Unit is resting
	 * 	 @return
	 * 			|result==(isresting())&&(this.getactivityTime<this.calculateMinRestTime)
	 * 
	 * */
	private boolean hasRestedEnough(){
		if ((isResting()) && (this.getActivityTime()<this.calculateMinRestTime())) {
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * calculate the time it takes to restore 1 point of hitpoints
	 * @return
	 * 			|result==this.getToughness()*time/40
	 */
	private double calculateHPRestore(double time){
		return this.getToughness()*time/40;
	}

	/**
	 * calculate the time it takes to restore 1 point of stamina
	 * @return
	 * 		|result==this.getToughness()*time/20
	 */
	private double calculateSTRestore(double time){
		return this.getToughness()*time/20;
	}

	/**
	 * Make the Unit rest if it can and has to
	 * @effect If this Unit's time between two mandatory rests has passed
	 * 			and this Unit can start resting, it starts resting
	 * 		|if (this.getTimeUntilRest()<=0)&&(!isAttacking)&&(!isresting)&&(!ismoving)
	 * 		|then this.resting()
	 */
	private void hasToRest(){
		if ((this.getTimeUntilRest()<=0)&&(!isAttacking())&&(!isResting())&&(!isMoving())&&(!isFalling())){
			this.resting();
			if(hasTask())
				this.task.removeFromUnit();
		}
	}

	/**
	 * if a Unit is idle and it has defaultbehavior enabled, it will choose an activity at random
	 * @post	if the Scheduler of the Unit's faction has a Task that is valid for execution, it
	 * 			will assign the Task to this Unit 
	 * @post	if no attackableUnits are in range and no tasks are available then 
	 * 			With a chance of one in three, movement to a random cube in the gameworld is initiated.
	 * 			With a chance of one in two, sprinting will be enabled.
	 * 			With a chance of one in three, the Unit starts working.
	 * 			With a chance of one in three, the Unit starts resting.
	 * @post 	if an attackable Unit is in range and no tasks are available then
	 * 			With a chance of one in four, movement to a random cube in the gameworld is initiated.
	 * 			With a chance of one in two, sprinting will be enabled.
	 * 			With a chance of one in four, the Unit starts working.
	 * 			With a chance of one in four, the Unit starts resting.
	 * 			With a chance of one in four, the Unit starts attacking.
	 */
	private void defaultbehavior(){

		if(this.getStatus()==Status.IDLE){
			if (!this.hasTask()) {
				try{
					this.getFaction().getScheduler().assignTaskToUnit(this, this.getFaction().getScheduler().getTopPriorityTask());
				}
				catch (NoSuchElementException e) {
					if (!possibleattack()) {
						defaultNoAttack();
					}
					else{
						defaultWithAttack();
					}
				}

			}



		}
	}

	/**
	 * Check whether there is a Unit in this Unit's game World that can be attacked by this Unit
	 * @return true if there is a adjacentUnit that is possible to attack
	 * 			| result== for some Unit in this.getWorld().getUnits():
	 * 			| 				(this.canHaveAsEnemy(Unit))
	 */
	private boolean possibleattack() {
		for (Unit unit : this.getWorld().getUnits()) {

			if (canHaveAsEnemy(unit)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * will choose an activity at random
	 * @post	
	 * 			With a chance of one in three, movement to a random cube in the gameworld is initiated.
	 * 			With a chance of one in two, sprinting will be enabled.
	 * 			With a chance of one in three, the Unit starts working.
	 * 			With a chance of one in three, the Unit starts resting.
	 */
	private void defaultNoAttack(){
		Random randomgenerator= new Random();
		int randomnumber=randomgenerator.nextInt(3);
		if (randomnumber==0){
			List <Vector>newlist= this.getWorld().getStandablePositions();
			Collections.shuffle(newlist);
			for (Vector vector : newlist) {
				if (this.getPosition().getDistanceTo(vector) < 20){
					try {
						this.moveTo(vector);
						int randomnumber1=randomgenerator.nextInt(2);
						if (randomnumber1==1) {
							try {
								setSprinting(true);
							} catch (IllegalStateException e){

							}
						}
						return;
					} catch (PathfindingException | IllegalArgumentException e) {
						continue;
					}
				}
			}
		}

		if (randomnumber==1){
			if (hasGameObject()) {
				WorkAt(this.getPosition().getCubeX(), this.getPosition().getCubeY(), this.getPosition().getCubeZ());
			}
			else {
				if (this.getWorld().containsInanimateObject(this.getPosition())) {	
					int randomnumber1=randomgenerator.nextInt(2);
					if (randomnumber1==1) {
						List<Vector> newlist=new ArrayList<>();
						newlist.addAll(this.getWorld().getDirectlyAdjacentPositions(this.getPosition()));
						Collections.shuffle(newlist);
						for (Vector vector : newlist) {
							try {
								WorkAt(vector.getCubeX(), vector.getCubeY(), vector.getCubeZ());
							} catch (IllegalArgumentException e){
								continue;
							}
						}
					}
					else {
						WorkAt(this.getPosition().getCubeX(), this.getPosition().getCubeY(), this.getPosition().getCubeZ());
					}
				}
				else {
					List<Vector> newlist=new ArrayList<>();
					newlist.addAll(this.getWorld().getDirectlyAdjacentPositions(this.getPosition())) ;
					Collections.shuffle(newlist);
					for (Vector vector : newlist){
						try {
							WorkAt(vector.getCubeX(), vector.getCubeY(), vector.getCubeZ());
						} catch (IllegalArgumentException e) {
							continue;
						}

					}
				}
			}
		}
		if (randomnumber==2){
			this.resting();}
	}




	/**
	 * will choose an activity at random
	 * @post 	
	 * 			With a chance of one in four, movement to a random cube in the gameworld is initiated.
	 * 			With a chance of one in two, sprinting will be enabled.
	 * 			With a chance of one in four, the Unit starts working.
	 * 			With a chance of one in four, the Unit starts resting.
	 * 			With a chance of one in four, the Unit starts attacking.
	 */
	private void defaultWithAttack(){
		Random randomgenerator= new Random();
		int randomnumber=randomgenerator.nextInt(4);
		if (randomnumber==0){
			List <Vector>newlist= this.getWorld().getStandablePositions();
			Collections.shuffle(newlist);
			for (Vector vector : newlist) {
				if (this.getPosition().getDistanceTo(vector) < 20){
					try {
						this.moveTo(vector);
						int randomnumber1=randomgenerator.nextInt(2);
						if (randomnumber1==1) {
							try {
								setSprinting(true);
							} catch (IllegalStateException e){

							}
						}
						return;
					} catch (PathfindingException | IllegalArgumentException e) {
						continue;
					}
				}
			}
		}

		if (randomnumber==1){
			if (hasGameObject()) {
				WorkAt(this.getPosition().getCubeX(), this.getPosition().getCubeY(), this.getPosition().getCubeZ());
			}
			else {
				if (this.getWorld().containsInanimateObject(this.getPosition())) {	
					int randomnumber1=randomgenerator.nextInt(2);
					if (randomnumber1==1) {
						List<Vector> newlist=new ArrayList<>();
						newlist.addAll(this.getWorld().getDirectlyAdjacentPositions(this.getPosition()));
						Collections.shuffle(newlist);
						for (Vector vector : newlist) {
							try {
								WorkAt(vector.getCubeX(), vector.getCubeY(), vector.getCubeZ());
							} catch (IllegalArgumentException e){
								continue;
							}
						}
					}
					else {
						WorkAt(this.getPosition().getCubeX(), this.getPosition().getCubeY(), this.getPosition().getCubeZ());
					}
				}
				else {
					List<Vector> newlist=new ArrayList<>();
					newlist.addAll(this.getWorld().getDirectlyAdjacentPositions(this.getPosition())) ;
					Collections.shuffle(newlist);
					for (Vector vector : newlist){
						try {
							WorkAt(vector.getCubeX(), vector.getCubeY(), vector.getCubeZ());
						} catch (IllegalArgumentException e) {
							continue;
						}

					}
				}
			}
		}
		if (randomnumber==2){
			this.resting();}

		if (randomnumber==3) {
			List <Unit>newlist= new ArrayList<Unit>();
			newlist.addAll(this.getWorld().getUnits());
			Collections.shuffle(newlist);
			Unit unittoattack=null;
			for (Unit unit : newlist) {
				if (canHaveAsEnemy(unit)) {
					unittoattack=unit;
				}
			}
			this.startAttack(unittoattack);
		}
	}

	/**
	 * Return the defaultbehaviorboolean of this Unit.
	 */
	@Basic @Raw
	public boolean getdefaultbehaviorboolean() {
		return this.defaultBehaviorBoolean;
	}

	/**
	 * Set this Unit's defaultBehaviorBoolean to the given boolean
	 * @param  defaultBehaviorBoolean
	 *         The new defaultBehaviorBoolean for this Unit.
	 * @post   The defaultBehaviorBoolean of this new Unit is equal to
	 *         the given defaultBehaviorBoolean.
	 *       | new.getdefaultBehaviorBoolean() == defaultBehaviorBoolean
	 * @throws IllegalStateException
	 * 			The Unit is currently not idle and the given boolean is true
	 * 		  | this.getStatus != Status.IDLE && defaultBehaviorBoolean
	 */
	public void setDefaultBehaviorBoolean(boolean defaultBehaviorBoolean) throws IllegalStateException {
		if (this.getStatus() != Status.IDLE && defaultBehaviorBoolean)
			throw new IllegalStateException("The default behavior cannot be enabled while the Unit is doing something");
		this.defaultBehaviorBoolean = defaultBehaviorBoolean;
	}

	/**
	 * Variable registering whether the Unit's default behavior is currently enabled
	 */
	private boolean defaultBehaviorBoolean;

	/**
	 * 
	 * @return the position from where the unit started to fall
	 */
	private double getFallPosition() {
		return this.fallPosition;
	}

	/**
	 * Check whether the given FallPosition is a valid FallPosition for any Unit
	 * @param fallPosition
	 * 			The FallPosition to check
	 * @return true if and only if the given FallPosition is larger than or equal to 0.
	 * 			| result == (fallPosition >= 0)
	 */
	public static boolean isValidFallPosition(double fallPosition){
		return (fallPosition >= 0);
	}

	/**
	 * Set the fallPosition of this Unit to the given fallPosition.
	 * 
	 * @param  	fallPosition
	 *         	The new fallPosition for this Unit.
	 * @pre    	The given fallPosition must be valid fallPosition for this
	 *         	Unit.
	 *       	| (isValidFallPosition(fallPosition))
	 * @post   	The fallPosition of this Unit are equal to the given
	 *         	fallPosition.
	 *       	| new.getFallPosition() == fallPosition
	 */
	private void setFallPosition(double fallPosition)throws IllegalArgumentException {
		assert (isValidFallPosition(fallPosition));
		this.fallPosition = fallPosition;
	}
	
	/**
	 * variable registering the position from where a unit started falling
	 */
	private double fallPosition;

	/**
	 * Check whether this Unit is currently falling
	 * @return true if the status of this unit is the falling status
	 * 		 | result==(this.getstatus==Status.FALLING)
	 */
	public boolean isFalling(){
		if (this.getStatus()==Status.FALLING){
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Check whether this Unit has to start falling
	 * @return returns true if the unit is in a position where one has to fall
	 * 			| result == (for each position in this.getWorld().getDirectlyAdjacentPositions(this.getPosition()):
	 * 			|				! isSolidGround(position)) &&
	 * 			|			this.getPosition().getCubeZ() != 0
	 */
	@Override
	public boolean hasToFall(){
		if ((this.getPosition().getCubeZ()==0)||this.getWorld().CheckadjacentValidPositions(this.getPosition().getCubeX(),this.getPosition().getCubeY(), this.getPosition().getCubeZ()).size()!=0) {
			return false;
		}
		return true;
	}

	/**
	 * method to initiate falling
	 * @pre		This Unit has to start falling
	 * 		   	| this.hasToFall() && !this.isFalling()
	 * @post	This Unit's status is FALLING		
	 * 			| new.getStatus() == Status.FALLING
	 * @post	units speed is set to the FALLSPEED
	 * 			| new.getSpeed() == FALLSPEED
	 * @post	the Unit is standing in the middle of the cube
	 * 			|new.getPosition() == new Vector(this.getPosition().getCubeX()+CUBELENGTH/2,this.getPosition().getCubeY()+CUBELENGTH/2,this.getPosition().getZ());
	 * @post	Sets the Fallposition the cube it is in			
	 * 			| new.getFallposition() == this.getPosition().getcubeZ()
	 * @effect	If this Unit was executing a Task, that Task is interrupted
	 * 			| if (this.hasTask())
	 * 			| then this.getTask().removeFromUnit();
	 */
	@Override
	void startFall() {
		assert (this.hasToFall() && !this.isFalling());
		this.setStatus(Status.FALLING);
		this.setSpeed(FALLSPEED);
		this.setPosition(new Vector(this.getPosition().getCubeX()+CUBELENGTH/2,this.getPosition().getCubeY()+CUBELENGTH/2,this.getPosition().getZ()));
		this.setFallPosition(this.getPosition().getCubeZ());
		if (hasTask()) {
			this.getTask().removeFromUnit();
		}
	}
	
	/**
	 * Update the Unit's position as it is falling
	 * @param time
	 * 			The time used to calculate the new position for this Unit.
	 * @post	If the Unit is not arriving at or surpassing a valid position,
	 * 			its speed times the given time is added to its position
	 * @post	If the Unit is arriving at or surpassing a valid position,
	 * 			its position is set to the valid position, its Fallposition is set to 0
	 * 			and its status is set to idle.
	 * @post	If the Unit would lose enough health to die, it will be terminated.
	 * @post	If the unit survives, it will lose ten times the difference between
	 * 			its Fallposition and the z-coordinate of its new cube in hitpoints
	 */
	@Override
	protected void fall(double time) {
		Vector displacement = this.getSpeed().scalarMultiply(time);
		Vector new_pos = this.getPosition().add(displacement);
		if ((this.getPosition().getCubeZ()==0)||this.getWorld().isSolidGround( this.getPosition().getCubeX(), this.getPosition().getCubeY(), this.getPosition().getCubeZ()-1) ){
			this.setPosition(new Vector(this.getPosition().getCubeX()+CUBELENGTH/2, this.getPosition().getCubeY()+CUBELENGTH/2, this.getPosition().getCubeZ()+CUBELENGTH/2));
			this.setSpeed(new Vector(0,0,0));
			this.setStatus(Status.IDLE);
			if (this.getHitpoints()-10*((int)this.getFallPosition()-this.getPosition().getCubeZ())>0){
				this.setHitpoints(this.getHitpoints()-10*((int)this.getFallPosition()-this.getPosition().getCubeZ()));
				this.setFallPosition(0);
			}
			else {
				setHitpoints(0);
			}
		}

		else{
			this.setPosition(new_pos);
		}

	}
	/**
	 * @return the experience points of this unit
	 * 		
	 */
	public int getExp() {
		return this.exp;
	}
	
	/**
	 * Set this Unit's amount of exp to the given amount of exp
	 * @param exp 
	 * 		the exp to set
	 * @post This Unit's of exp equals the given amount of exp modulo 10
	 * 		| new.getExp() == exp % 10
	 * @effect	This Unit levels up if it needs to
	 * 		| this.levelUp()
	 * 
	 */
	private void setExp(int exp) {
		if (isValidExp(exp)) {
			this.exp = exp;
		}
		else {
			this.exp=this.getExp();

		}
		this.levelUp();
	}

	/**
	 * @post the exp of this unit is be lower than 10
	 * 		|new.getExp() < 10
	 * @post the units attributes will be the same or higher than they were before the method
	 * 		|new.getStrength() >= this.getStrength()
	 * 		|new.getAgility() >= this.getAgility()
	 * 		|new.getToughness >= this.getToughness()
	 */
	private void levelUp() {
		while (this.getExp()>=10) {
			Random random= new Random();
			int randomnumber=random.nextInt(3);
			if (randomnumber==0)
				this.setAgility(this.getAgility()+1);
			if (randomnumber==1)
				this.setStrength(this.getStrength()+1);
			if (randomnumber==2)
				this.setToughness(this.getToughness()+1);
			this.setExp(this.getExp()-10);
		}
	}

	/**
	 * Check whether the given amount of exp is a valid amount of exp for any Unit
	 * @param exp
	 * 			The amount of exp to be checked
	 * @return returns true if the exp is non-negative
	 * 			| result == (exp>=0)
	 */
	public static boolean isValidExp(int exp) {
		if (exp<0) {
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * variable registering the experience points this unit has
	 */
	private int exp;
	
	/**
	 * Check whether this Unit can be terminated
	 * @return true if this Unit's hitpoints have reached zero
	 * 			| result == (this.getHitpoints() == 0)
	 */
	@Override
	public boolean canBeTerminated(){
		return (this.getHitpoints() == 0);
	}

	/**
	 * Terminate this Unit
	 * @pre		This Unit can be terminated
	 * 			| (this.canBeTerminated())
	 * @post	This Unit has been terminated
	 * 			| new.isTerminated() == true
	 * @post	This Unit is idle
	 * 			| new.getStatus() == Status.IDLE
	 * @post	This Unit's default behaviour is disabled
	 * 			| new.getdefaultbehaviorboolean() == false
	 * @post	This Unit's NearTarget is the null reference
	 * 			| new.getNearTarget() == null
	 * @post	This Unit's DistantTarget is the null reference
	 * 			| new.getDistantTarget() == null
	 * @post	This Unit's speed is the null vector
	 * 			| new.getSpeed() == new Vector(0,0,0)
	 * @post	This Unit's path is empty
	 * 			| new.getPath().size() == 0
	 * @post	This Unit's Enemy is the null reference
	 * 			| new.getEnemy() == null
	 * @post	This Unit has been removed from its faction
	 * 			| (new this).getFaction() == null
	 * 			| (new this.getFaction()).hasAsUnit(this) == false
	 * @effect	This Unit is removed from its World
	 * 			| this.getWorld().removeGameObject(this)
	 * @effect	if this Unit was carrying a gameObject, it will be dropped at this Unit's current position.
	 * 			| if (this.hasGameObject())
	 * 			| then this.dropObjectAt(this.getPosition())
	 * @effect	If this Unit was executing a Task, that Task is removed from this Unit
	 * 			| if (this.hasTask())
	 * 			| then this.getTask().removeFromUnit()
	 */
	@Override
	void terminate(){
		assert (this.canBeTerminated());
		this.setDefaultBehaviorBoolean(false);
		if (hasGameObject()) {
			dropObjectAt(this.getPosition());
		}
		super.terminate();
		this.setDistantTarget(null);
		this.setNearTarget(null);
		this.setSpeed(new Vector(0,0,0));
		this.setEnemy(null);
		this.getPath().clear();
		this.removeFromFaction();
		if (hasTask()) {
			this.getTask().removeFromUnit();

		}
	}

	/**
	 * Return the faction this Unit belongs to.
	 */
	public Faction getFaction() {
		return this.faction;
	}

	/**
	 * Check whether the given faction is a valid faction for this Unit
	 * @param faction
	 * 			The faction to check
	 * @return	If this Unit is not terminated, true if the given faction is not the null reference
	 * 			and is not terminated
	 * 			Else, true if the given faction is the null reference
	 * 			| if (!this.isTerminated())
	 * 			| then result == (faction != null)
	 * 			| else result == (faction == null)
	 * 
	 */
	public boolean canHaveAsFaction(Faction faction) {
		if (this.isTerminated())
			return (faction == null);
		else
			return (faction != null);
	}

	/**
	 * Add this Unit to the given faction
	 * @param faction
	 * 			The faction to add this Unit to
	 * @post	If the given Faction has less than 50 Units,
	 * 			this Unit has been added to the given faction
	 * 			| if (faction.getNbUnits() < 50)
	 * 			| then 	(new this).getFaction() == faction &&
	 * 			| 		(new faction).hasAsUnit(this)
	 * @throws IllegalArgumentException
	 * 			The given faction is not a valid faction
	 * 			| ! canHaveAsFaction(faction)
	 */
	private void addToFaction(Faction faction) throws IllegalArgumentException{
		if (! canHaveAsFaction(faction))
			throw new IllegalArgumentException("This is an invalid faction");
		if (faction.getNbUnits() >= 50)
			return;
		this.faction = faction;
		faction.addUnit(this);
	}

	/**
	 * Remove this Unit from its current faction
	 * @pre		This Unit has died and must be terminated
	 * 			| (this.isTerminated() == 0)
	 * @post	This Unit has been removed from its faction
	 * 			| (new this).getFaction() == null
	 * 			| !(new this.getFaction()).hasAsUnit(this)
	 */
	private void removeFromFaction(){
		assert (this.isTerminated());
		Faction oldFaction = this.getFaction();
		this.faction = null;
		oldFaction.removeUnit(this);
	}

	/**
	 * Variable registering the Faction this Unit belongs to.
	 */
	private Faction faction;

	/**
	 * Return a boolean reflecting whether the given World is a valid World for this Unit
	 * @param world
	 * 			The World to be checked
	 * @return	If this Unit has been terminated, true if the given World is the null reference.
	 * 			Else, true if the World is not the null reference.
	 * 			| if (this.isTerminated())
	 * 			| then result == (world == null)
	 * 			
	 */
	@Override
	public boolean canHaveAsWorld(World world) {
		if (this.isTerminated())
			return (world == null);
		else
			return (world != null);
	}

	/**
	 * Adds this Unit to the given World
	 * @param world
	 * 			The World to add this Unit to.
	 * @pre		The given World is effective and already has this Unit as one of its Units.
	 * 			| (world != null) && (world.hasAsUnit(this))
	 * @pre		This Unit can have the given World as its World
	 * 			| canHaveAsWorld(world)
	 * @post	This Unit occupies a position it can stand on in the given World
	 * 			| (new this).canStandAt((new this).getPosition())
	 * @post	This Unit has been added to the given World
	 * 			| (new world).hasAsUnit(this)
	 * 			| (new this).getWorld() == world
	 * @post	This Unit is in a faction in the given World
	 * 			| (new world).hasAsFaction((new this).getFaction())
	 * 			| && (new this).getFaction().hasAsUnit(this)
	 * @throws IllegalStateException
	 * 			The Unit is already part of a World
	 * 			| this.getWorld() != null
	 */
	@Raw@Override
	void addToWorld(@Raw World world) throws IllegalStateException{
		assert (world != null) && (world.hasAsUnit(this)) && this.canHaveAsWorld(world);
		this.setWorld(world);
		if (!world.unitCanSpawnAt(this.getPosition())){
			int index = new Random().nextInt(world.getSpawnablePositions().size());
			Vector startPos = world.getSpawnablePositions().get(index);
			this.setPosition(new Vector(startPos.getCubeX() + CUBELENGTH/2,
					startPos.getCubeY() + CUBELENGTH/2,
					startPos.getCubeZ() + CUBELENGTH/2));
		}
		if (world.getActiveFactions().size() < 5){
			Faction faction = new Faction(world);
			this.addToFaction(faction);
		} else {
			int nbUnits = Integer.MAX_VALUE;
			Faction smallestFaction = null;
			for (Faction faction:world.getActiveFactions()){
				if (faction.getNbUnits() < nbUnits){
					smallestFaction = faction;
					nbUnits = faction.getNbUnits();
				}
			}
			this.addToFaction(smallestFaction);
		}
	}

	/**
	 * Remove this Unit from its current World
	 * @pre		This Unit has died and must be terminated
	 * 			| (this.isTerminated())
	 * @post	This Unit has been removed from its world
	 * 			| (new this).getWorld() == null
	 * 			| !(new this.getWorld()).hasAsUnit(this)
	 */
	@Override
	void removeFromWorld(){
		assert (this.isTerminated() && !this.getWorld().hasAsGameObject(this));
		World oldWorld = this.getWorld();
		this.setWorld(null);
		for(Unit unit:oldWorld.getUnits()){
			if (unit.getEnemy()==this) {
				unit.setStatus(Status.IDLE);
			}
		}
	}

	/**
	 * 
	 * @return the gameobject this unit is carrying, if any
	 * 		
	 */
	@Basic @Raw
	private GameObject getGameObject () {
		return this.gameObject;
	}

	/**
	 * Check whether this Unit is currently carrying a Boulder
	 * @return true if the gameobject of the unit is a Boulder
	 * 			| result==(this.getgameObect instanceof Boulder)
	 */
	public boolean isCarryingBoulder() {
		return this.getGameObject() instanceof Boulder;
	}
	/**
	 * Check whether this Unit is currently carrying a Log
	 * @return true if the gameobject of the unit is a Log
	 * 			| result==(this.getgameObect instanceof Log)
	 */
	public boolean isCarryingLog() {
		return this.getGameObject() instanceof Log;
	}

	/**
	 * Check whether this Unit is currently carrying a GameObject.
	 * @return true if the gameobject of the unit is not null
	 * 		result==(this.getgameObect!=null)
	 */
	private boolean hasGameObject() {
		return (this.getGameObject() != null);
	}

	/**
	 * Returns the weight of the GameObject currently being carried by this Unit.
	 * @return the weight of the gameobject the unit is carrying, if not carrying any object, returns 0
	 * 			| if (! this.hasGameObject())
	 * 			| then result==0
	 * 			| else
	 * 			| result==this.getgameobject.getWeight
	 */
	private int weightGameObject() {
		if (! this.hasGameObject())
			return 0;
		else {
			return this.gameObject.getWeight();
		}
	}

	/**
	 * Check whether the given GameObject is a valid GameObject for this Unit
	 * @param object
	 * 			The GameObject to be checked
	 * @return if this Unit has been terminated, true if and only if the given object is the null reference.
	 * 		   if this Unit has not been terminated, true if the given object is the null refererence
	 * 		   or has not been terminated.
	 * 		 	| if (this.isTerminated())
	 * 			| then result == (object == null)
	 * 			| else result == (object == null || !object.isTerminated())
	 * 			
	 */
	public boolean canHaveAsGameObject(GameObject object){
		if (this.isTerminated()){
			return object == null;
		}
		return (object == null || !object.isTerminated());
	}

	/**
	 * Set the Gameobject of the Unit to the given Gameobject
	 * 
	 * @param	gObject
	 *            The new Gameobject to be set for this Unit
	 * @post 	If the given Gameobject is null and the Unit has a Gameobject, the units gameobject will be set to null
	 * 	        | if (gObject ==null)&& this.hasgameObject()
	 *       	| then new.getgameobjec() == null
	 * @effect	If the Unit has no gameobject yet it will pickup the gobject
	 *       	the Unit's strength equals the maximum legal strength
	 *       	| if this.hasnogameobject
	 *       	| then new.getgameobjec() == gObject
	 *       	| gObject.removeFromWorld()
	 * 
	 */
	@Raw
	void setGameObject(InanimateObject gObject) {
		if (!hasGameObject()) {
			this.gameObject=gObject;
			gObject.getWorld().removeGameObject(gObject);
		}
		if (hasGameObject()&&gObject==null) {
			this.gameObject=null;
		}
	}

	/**
	 * Variable registering the gameObject this Unit is currently carrying.
	 */
	private InanimateObject gameObject=null;

	/**
	 * Find a path to a given cube in the gameworld
	 * @param x
	 * 			The x-coordinate of the target cube
	 * @param y
	 * 			The y-coordinate of the target cube
	 * @param z
	 * 			The z-coordinate of the target cube
	 * @post	This Unit's Path has been set to a non-empty and valid path
	 * 			| new.getPath().size != 0 && isValidPath(new.getPath())
	 * @throws IllegalArgumentException
	 * 			The given cube is not a position where a Unit can stand
	 * 			or the Unit is already at the given cube
	 * 			| ! this.getWorld().unitCanStandAt(x,y,z) ||
	 * 			| this.getPosition().getCubePosition().equals(new Vector(x,y,z))
	 * @throws PathfindingException
	 * 			No path could be found to the given target position
	 */
	private void findPath(int x, int y, int z) throws IllegalArgumentException, PathfindingException{
		if (!this.getWorld().unitCanStandAt(x, y, z) || (this.getPosition().getCubePosition().equals(new Vector(x,y,z))))
			throw new IllegalArgumentException("The Unit cannot move to this position!");
		Heap<Node> open = new Heap<>();
		List<Node> closed = new ArrayList<>();
		Node end = new Node(new Vector(x,y,z),Integer.MAX_VALUE,0);
		Node start = new Node(this.getPosition());
		open.add(start);
		boolean finished = false;
		while (!finished){
			Node current = open.pop();
			closed.add(current);
			if (current.equals(end)){
				finished = true;
				setPath(closed,start,end);
				return;
			}
			for (Node neighbour:current.getNeighbouringNodes()){
				if (!this.getWorld().unitCanStandAt(neighbour.getCubeCoordinates()) ||
						closed.contains(neighbour))
					continue;
				neighbour.setGCost(current.getGCost()+Node.calculateDistance(current, neighbour));
				neighbour.setHCost(Node.calculateDistance(neighbour, end));
				neighbour.setParent(current);
				if (!open.contains(neighbour)){
					open.add(neighbour);
				}
				else {
					int index = open.getIndex(neighbour);
					if (neighbour.compareTo(open.get(index)) < 0){
						open.replace(index, neighbour);
					}
				}
			}
			if (open.size() == 0) //No path could be found
				throw new PathfindingException(this.getPosition(), new Vector(x,y,z),
						"There is no path from this Unit's position to the given position");
		}
	}

	private void findPath(Vector target) throws IllegalArgumentException{
		findPath(target.getCubeX(),target.getCubeY(),target.getCubeZ());
	}

	/**
	 * Return the path of this Unit.
	 */
	@Basic @Raw
	private List<Vector> getPath() {
		return this.path;
	}

	/**
	 * Check whether the given path is a valid path for
	 * any Unit.
	 *  
	 * @param  path
	 *         The path to check.
	 * @return false if the given path is the null reference
	 * 			true if the given path is empty or when it is possible for a Unit to stand at
	 * 		   every position in the path and the last position of the path equals the Unit's DistantTarget
	 * 		 | if (path == null)
	 * 		 | then result == false
	 *       | else result == (path.size() == 0) ||
	 *       |				((for each vector in path: this.getWorld.unitCanStandAt(vector)) && 
	 *       |				path.get(path.size()-1).equals(this.getDistantTarget()))
	 */
	public boolean isValidPath(List<Vector> path) {
		if (path == null)
			return false;
		if (path.size() == 0)
			return true;
		for (Vector vector:path){
			if(!this.getWorld().unitCanStandAt(vector))
				return false;
		}
		return path.get(path.size()-1).equals(this.getDistantTarget());
	}

	private void setPath(List<Node> closed, Node start, Node end) {
		List<Vector> result = new ArrayList<>();
		Node current = closed.get(closed.indexOf(end));
		while (!current.equals(start)){
			result.add(0, current.getCubeCoordinates().add(new Vector(CUBELENGTH/2,CUBELENGTH/2,CUBELENGTH/2)));
			current = current.getParent();
		}
		this.setPath(result);
	}	

	/**
	 * Set the path of this Unit to the given path.
	 * 
	 * @param  path
	 *         The new path for this Unit.
	 * @pre    The given path must be a valid path for any
	 *         Unit.
	 *       | isValidPath(path)
	 * @post   If the Unit is currently at the center of a cube, the path of this Unit is equal to the given
	 *         path. Otherwise, the Unit's current cube is added at the first position in the list.
	 *         	| if (this.getPosition().equals(this.getPosition().getCubePosition().add(new Vector(CUBELENGTH/2,CUBELENGTH/2,CUBELENGTH/2))))
	 *         	| then new.getPath() == path
	 *         	| else new.getPath().get(0) == this.getPosition().getCubePosition().add(new Vector(CUBELENGTH/2,CUBELENGTH/2,CUBELENGTH/2))
	 */
	@Raw
	private void setPath(List<Vector> path) {
		assert isValidPath(path);
		if (!this.getPosition().equals(this.getPosition().getCubePosition().add(new Vector(CUBELENGTH/2,CUBELENGTH/2,CUBELENGTH/2))))
			path.add(0, this.getPosition().getCubePosition().add(new Vector(CUBELENGTH/2,CUBELENGTH/2,CUBELENGTH/2)));
		this.path = path;
	}

	/**
	 * Variable registering the path of this Unit.
	 */
	private List<Vector> path = new ArrayList<>();

	/**
	 * Return this Unit's current Task
	 */
	@Basic @Raw
	public Task getTask() {
		return this.task;
	}

	/**
	 * Check whether this Unit currently has a Task
	 * @return true if this Unit's task is not null
	 * 		 | result==this.task!==null
	 */
	public boolean hasTask(){
		return this.task!=null;
	}

	/**
	 * Check whether the given Task is a valid Task for this Unit
	 * @param task
	 * 			The Task to check
	 * @return true if the given Task is null or if the given Task is not terminated
	 * 		 | result == (task == null || !task.isTerminated())
	 */
	@Raw
	public static boolean isValidTask(Task task){
		return (task == null || !task.isTerminated());
	}

	/**
	 * Assign the given Task to this Unit.
	 * @param task
	 * 			The Task to be assigned to this Unit.
	 * @pre	  The given Task is effective and already references this Unit.
	 * 		| (task != null && task.getUnit() == this)
	 * @post  This Unit references the given Task.
	 * 		| new.getTask() == task
	 */
	public void assignTask(Task task){
		assert (task != null && task.getUnit() == this);
		this.setTask(task);
	}
	
	/**
	 * Removes this Unit's current Task
	 * @pre	  This Unit references a task and this Unit's Task no longer
	 * 		  references a Unit
	 * 		 | (this.getTask() != null && this.getTask().getUnit() == null)
	 * @post  This Unit no longer references a Task
	 * 		 | new.getTask() == null
	 */
	public void removeTask(){
		assert (this.getTask() != null && this.getTask().getUnit() == null);
		this.setTask(null);
	}

	/**
	 * Set this Unit's Task to the given Task
	 * @param task
	 * @post	This Unit's task equals the given Task
	 * 		  | new.getTask() == task
	 * @throws	IllegalArgumentException
	 * 			The given Task is an invalid Task for any Unit
	 * 		  | ! isValidTask(task)
	 */
	private void setTask(Task task) throws IllegalArgumentException {
		if (! isValidTask(task))
			throw new IllegalArgumentException();
		this.task = task;
	}

	/**
	 * variable registering the Task of the Unit
	 */
	private Task task=null;
}
